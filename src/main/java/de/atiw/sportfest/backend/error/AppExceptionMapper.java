package de.atiw.sportfest.backend.error;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AppExceptionMapper implements ExceptionMapper<Exception> {

	public Response toResponse(Exception ex) {

        if(ex instanceof WebApplicationException)
            return fromWebAppEx((WebApplicationException) ex);
        else
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ExceptionResponse(ex)).type(MediaType.APPLICATION_JSON).build();
	}

    public static Response fromWebAppEx(WebApplicationException ex){
        return Response.fromResponse(ex.getResponse()).entity(new ExceptionResponse(ex)).type(MediaType.APPLICATION_JSON).build();
    }


}
