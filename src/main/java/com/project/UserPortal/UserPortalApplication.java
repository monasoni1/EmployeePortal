package com.project.UserPortal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableWebSecurity

//@ComponentScan(basePackages = {"com.project.UserPortal,com.project.UserPortal.Mapper"})
public class UserPortalApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserPortalApplication.class, args);
	}

}
