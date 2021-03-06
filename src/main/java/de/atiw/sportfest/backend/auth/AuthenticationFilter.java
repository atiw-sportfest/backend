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

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import de.atiw.sportfest.backend.error.ExceptionResponse;
import de.atiw.sportfest.backend.auth.Secured;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    @Context
    HttpServletRequest hsr;

    @Override
    public void filter(ContainerRequestContext requestContext) throws NotAuthorizedException {

        Claims claims = null;

        try {

            // Validate the token
            claims = new TokenParser(requestContext).verify().getClaims(); // throws exception if invalid

        } catch(TokenParser.TokenException e){
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build());
            return;
        } catch (Exception e){
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity(e).type(MediaType.APPLICATION_JSON).build());
            return;
        }

        if(claims == null)
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("Your claims are empty.").type(MediaType.TEXT_PLAIN).build());

        else if(!claims.getAudience().equals(hsr.getRemoteAddr()))
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity(String.format("You (%s) are not the audience of this token (%s).",
                            hsr.getRemoteAddr(), claims.getAudience())).type(MediaType.TEXT_PLAIN).build());
    }
}

class AuthenticationException extends Exception {

    private static final long serialVersionUID = 1L;

    public AuthenticationException(String msg){
        super(msg);
    }

}
