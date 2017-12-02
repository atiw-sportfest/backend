package de.atiw.sportfest.backend.error;

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
