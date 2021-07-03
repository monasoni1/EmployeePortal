package com.project.UserPortal.Scheduler;

import org.junit.internal.runners.statements.RunAfters;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import java.util.Date;
import java.util.concurrent.ScheduledFuture;

@Configuration
public class SchedulerWithMultipleThread  implements SchedulingConfigurer
{
    TaskScheduler taskScheduler;
    private ScheduledFuture<?> job1;
    private ScheduledFuture<?> job2;
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar)
    {
        ThreadPoolTaskScheduler threadPoolTaskScheduler=new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(10);
        threadPoolTaskScheduler.setThreadNamePrefix("Scheduler-Thread");
        threadPoolTaskScheduler.initialize();
        job1(threadPoolTaskScheduler);
        job2(threadPoolTaskScheduler);
        this.taskScheduler=threadPoolTaskScheduler;// this will be used in later part of the article during refreshing the cron expression dynamically
        taskRegistrar.setTaskScheduler(threadPoolTaskScheduler);
    }

    private void job1(TaskScheduler scheduler)
    {
        job1=scheduler.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " The Task1 executed at " + new Date());
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }, new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                String cronExp = "0/5 * * * * ?";// Can be pulled from a db .
                return new CronTrigger(cronExp).nextExecutionTime(triggerContext);
            }
        });
    }
    private void job2(TaskScheduler scheduler){
        job2=scheduler.schedule(new Runnable(){
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+" The Task2 executed at "+ new Date());
            }
        }, new Trigger(){
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                String cronExp="0/1 * * * * ?";//Can be pulled from a db . This will run every minute
                return new CronTrigger(cronExp).nextExecutionTime(triggerContext);
            }

        });
    }
}
