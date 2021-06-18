package com.project.UserPortal.Config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.naming.AuthenticationException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Component
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint
{

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse response, org.springframework.security.core.AuthenticationException exception) throws IOException, ServletException
    {
        final String expired = (String) httpServletRequest.getAttribute("expired");
        System.out.println(expired);
        if (expired!=null){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,expired);
        }else{
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Invalid Login details");
        }
//        response.setStatus(403);
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//
//        String message;
//        if (exception.getCause() != null) {
//            message = exception.getCause().getMessage();
//        } else {
//            message = exception.getMessage();
//        }
//        byte[] body = new ObjectMapper().writeValueAsBytes(Collections.singletonMap("error", message));
//        response.getOutputStream().write(body);
    }
}
