package de.atiw.sportfest.backend.auth;

import io.jsonwebtoken.Claims;

import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Resource;

import javax.annotation.Priority;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import de.atiw.sportfest.backend.ExceptionResponse;
import de.atiw.sportfest.backend.error.ErrorEntity;

@Secured
@Provider
@Priority(Priorities.AUTHORIZATION)
public class AuthorizationFilter implements ContainerRequestFilter {

    @Context
    private ResourceInfo resourceInfo;

    @Resource(name="jwt_secret")
    private String secret;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        // Get the resource class which matches with the requested URL
        // Extract the roles declared by it
        Class<?> resourceClass = resourceInfo.getResourceClass();
        List<Role> classRoles = extractRoles(resourceClass);

        // Get the resource method which matches with the requested URL
        // Extract the roles declared by it
        Method resourceMethod = resourceInfo.getResourceMethod();
        List<Role> methodRoles = extractRoles(resourceMethod);

        Claims claims = null;

        try {

            claims = new TokenParser(requestContext, secret).verify().getClaims();

            // Check if the user is allowed to execute the method
            // The method annotations override the class annotations
            checkPermissions( methodRoles.isEmpty() ? classRoles : methodRoles, claims);

        } catch (Exception e) {

            if(e instanceof AuthorizationException)
                requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build());
            else
                requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).entity(e).type(MediaType.APPLICATION_JSON).build());

        }
    }

    // Extract the roles from the annotated element
    private List<Role> extractRoles(AnnotatedElement annotatedElement) {
        if (annotatedElement == null) {
            return new ArrayList<Role>();
        } else {
            Secured secured = annotatedElement.getAnnotation(Secured.class);
            if (secured == null) {
                return new ArrayList<Role>();
            } else {
                Role[] allowedRoles = secured.value();
                return Arrays.asList(allowedRoles);
            }
        }
    }

    private void checkPermissions(List<Role> allowedRoles, Claims claims) throws Exception {

        Role role;

        if(claims.get("role", String.class) == null || claims.get("username", String.class) == null)
            throw new AuthorizationException("You must be logged in to do this.");

        if(!allowedRoles.isEmpty() && !allowedRoles.contains(Role.valueOf(claims.get("role", String.class))))
            throw new AuthorizationException("You are missing a role that is required to do this.");
    }
}

class AuthorizationException extends Exception {

    private static final long serialVersionUID = 1L;

    public AuthorizationException(String msg){
        super(msg);
    }

}
