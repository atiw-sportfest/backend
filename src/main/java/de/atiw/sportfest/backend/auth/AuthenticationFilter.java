package de.atiw.sportfest.backend.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Priority;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import de.atiw.sportfest.backend.ExceptionResponse;
import de.atiw.sportfest.backend.auth.Secured;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        try {

            // Validate the token
            new TokenParser(requestContext).verify(); // throws exception if invalid

        } catch (Exception e){
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity(new ExceptionResponse(e)).build());
        }
    }
}

class AuthenticationException extends Exception {

    private static final long serialVersionUID = 1L;

    public AuthenticationException(String msg){
        super(msg);
    }

}
