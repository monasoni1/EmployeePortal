package com.project.UserPortal.Scheduler;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@EnableScheduling
@Configuration
public class SchedulerwithAnnotation
{
    //@Scheduled(fixedRate = 4000)
    public void executeTask1()
    {
        System.out.println(Thread.currentThread().getName()+"Task 1 at"+new Date());
        try
        {
            Thread.sleep(4000);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }

    }
   // @Scheduled(fixedRate  =1000)
    public void executeTask2()
    {
        System.out.println(Thread.currentThread().getName()+"Task 2 at "+new Date());
    }
}
