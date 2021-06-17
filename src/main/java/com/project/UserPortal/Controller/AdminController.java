package com.project.UserPortal.Controller;

import com.project.UserPortal.Security.JwtUtilsToken;
import com.project.UserPortal.models.AuthenticationRequest;
import com.project.UserPortal.models.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/api/noauth")
public class AdminController
{
    @Autowired
    private AuthenticationManager  authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtilsToken jwtUtilsToken;
    @GetMapping(path="/api/admin")
    public String getAdmin()
    {
        return "got the admin";
    }

    @PostMapping(path = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));

        }
        catch(BadCredentialsException e)
        {
            throw new Exception("Invalid credentials",e);
        }
        final UserDetails userDetails=userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        final String jwt= jwtUtilsToken.generateJwtToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
