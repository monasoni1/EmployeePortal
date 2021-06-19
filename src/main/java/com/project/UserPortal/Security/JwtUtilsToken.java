package com.project.UserPortal.Security;

import com.project.UserPortal.Exceptions.UnauthorizedException;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
@Component
public class JwtUtilsToken
{

    private static final Logger logger = LoggerFactory.getLogger(JwtUtilsToken.class);
    private String jwtSecretKey="Secret";

//    public String extractUsername(String token)
//    {
//        return extractClaim(token , Claims :: getSubject);
//    }
//    public Date extractExpiration(String token)
//    {
//        return extractClaim(token, Claims :: getExpiration);
//    }
//    public <T> T extractClaim(String token, Function<Claims ,T > claimsResolver)
//    {
//        final Claims claims=extractAllClaims(token);
//        return claimsResolver.apply(claims);
//    }
//    public Claims extractAllClaims(String token)
//    {
//        return Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(token).getBody();
//    }
//    public boolean isTokenExpired(String token)
//    {
//        return extractExpiration(token).before(new Date());
  //  }
    public String generateJwtToken(UserDetails userDetails)
    {
        //Map<String, Object> claims = new HashMap<>();
        return createToken(userDetails.getUsername());
    }
    public String createToken(String username)
    {
        return Jwts.builder().setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*10))
                .signWith(SignatureAlgorithm.HS256, jwtSecretKey).compact();

    }
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(token).getBody().getSubject();
    }
//    public boolean validateToken(String token, UserDetails userDetails)
//    {
//        final String username=extractUsername(token);
//            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  //  }

    public boolean validateToken(String authToken, HttpServletRequest request) {
        try {
            Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            request.setAttribute("expired",e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}
