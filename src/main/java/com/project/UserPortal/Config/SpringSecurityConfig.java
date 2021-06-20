package com.project.UserPortal.Config;

import com.project.UserPortal.DTO.ProjectInfoDTO;
import com.project.UserPortal.Domain.Department;
import com.project.UserPortal.Filters.JwtRequestFilter;
import com.project.UserPortal.Mapper.*;
import com.project.UserPortal.Service.MyUserDetailService;
import javassist.bytecode.ExceptionsAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter
{

    @Autowired
    private MyUserDetailService myUserDetailService;
    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.cors().and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers("/authenticate").permitAll()
                .antMatchers("/api/**").permitAll()
                .anyRequest().authenticated();
                http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception
    {
        return super.authenticationManagerBean();
    }
    @Bean
    EmployeeMapper employeeMapper() {
        return new EmployeeMapperImpl();
    }
    @Bean
    DepartmentMapper departmentMapper() {
        return new DepartmentMapperImpl();
    }
    @Bean ProjectMapper projectMapper() {
        return new ProjectMapperImpl();
    }
//    @Bean
//    EmployeeInfoMap employeeInfo()
//    {
//        return new EmployeeInfoMapImpl();
//    }
    //    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().withUser("user").password("password").roles("ADMIN");
//        auth.inMemoryAuthentication().withUser("user2").password("password").roles("User");
//        auth.inMemoryAuthentication().withUser("user1").password("password").roles("ADMIN");
//    }

    //Security for all API
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable();
//        http.authorizeRequests().anyRequest().fullyAuthenticated().and().httpBasic();
//    }
    //security based on URL
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable();
//        http.authorizeRequests().antMatchers("/api/Users/**").fullyAuthenticated().and().httpBasic();
//    }
//    security based on role
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable();
//        http.authorizeRequests().antMatchers("/api/Users/**").hasAnyRole("User").anyRequest().fullyAuthenticated().and().httpBasic();
//    }
    @Bean
    public static NoOpPasswordEncoder encoder()
    {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }
}
