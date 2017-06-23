package de.atiw.sportfest.backend.auth;

import io.jsonwebtoken.Claims;

import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Priority;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import de.atiw.sportfest.backend.ExceptionResponse;

@Secured
@Provider
@Priority(Priorities.AUTHORIZATION)
public class AuthorizationFilter implements ContainerRequestFilter {

    @Context
    private ResourceInfo resourceInfo;

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

            claims = new TokenParser(requestContext).verify().getClaims();

            // Check if the user is allowed to execute the method
            // The method annotations override the class annotations
            checkPermissions( methodRoles.isEmpty() ? classRoles : methodRoles, claims);

        } catch (Exception e) {

            requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).entity(new ExceptionResponse(e)).build());

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
        if(!allowedRoles.isEmpty() && !allowedRoles.contains(Role.valueOf(claims.get("role", String.class))))
            throw new AuthorizationException("Missing role for this action.");
    }
}

class AuthorizationException extends Exception {

    private static final long serialVersionUID = 1L;

    public AuthorizationException(String msg){
        super(msg);
    }

}
