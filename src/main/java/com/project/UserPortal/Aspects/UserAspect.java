package com.project.UserPortal.Aspects;

import com.project.UserPortal.Domain.User;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserAspect
{
    @Before(value="execution (* com.project.UserPortal.Service.UserService.*(..)) and args(user)")
    public void beforeAdvise(User user)
    {
        System.out.println(user.getName());
        System.out.println("in aspect");
    }
}
