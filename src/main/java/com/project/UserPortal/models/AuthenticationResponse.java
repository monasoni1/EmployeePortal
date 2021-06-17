package com.project.UserPortal.models;

public class AuthenticationResponse
{
    public final String jwt;

    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}
