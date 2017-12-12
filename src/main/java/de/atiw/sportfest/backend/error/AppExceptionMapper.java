package de.atiw.sportfest.backend.error;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
class WebAppExceptionMapper implements ExceptionMapper<WebApplicationException> {

    public Response toResponse(WebApplicationException ex){
        return Response.fromResponse(ex.getResponse()).entity(new ExceptionResponse(ex)).type(MediaType.APPLICATION_JSON).build();
    }
}

@Provider
class ViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException cve) {
        return Response.status(400).entity(new ValidationExceptionMessage(cve)).build();
    }

    class ValidationExceptionMessage {

        class Violation {
            Object object;
            String message;

            Violation(Object o, String s){
                object = o;
                message = s;
            }
        }

        List<Violation> violations = new ArrayList<>();
        String message;

        ValidationExceptionMessage(ConstraintViolationException cve){

            for(ConstraintViolation<?> cv : cve.getConstraintViolations())
                violations.add(new Violation(cv.getRootBean(), String.format("%s %s", cv.getPropertyPath(), cv.getMessage())));

            message = cve.getMessage();
        }
    }

}
