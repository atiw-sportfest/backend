package de.atiw.sportfest.backend.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.HttpHeaders;

public class TokenParser {

    private String token;
    private Claims claims;

    public TokenParser(String token){
        this.token = token;
    }

    public TokenParser(ContainerRequestContext requestContext) throws Exception {

        // Get the HTTP Authorization header from the request
        String authorizationHeader =
            requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        // Check if the HTTP Authorization header is present and formatted correctly
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new TokenException("Authorization header must be provided!");
        }

        // Extract the token from the HTTP Authorization header
        this.token = authorizationHeader.substring("Bearer".length()).trim();

    }

    public TokenParser verify() throws Exception {

        claims = Jwts.parser().setSigningKey("secret".getBytes()).parseClaimsJws(token).getBody(); // throws exception if invalid

        if(claims.getExpiration() == null)
            throw new TokenException("Token is missing 'exp'!");

        return this;
    }

    public Claims getClaims(){
        return claims;
    }

    public class TokenException extends Exception {

        private static final long serialVersionUID = 1L;

        public TokenException(String msg){
            super(msg);
        }

    }
}
