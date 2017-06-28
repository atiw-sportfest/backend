package de.atiw.sportfest.backend.error;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AppExceptionMapper implements ExceptionMapper<Exception> {

	public Response toResponse(Exception ex) {
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex).type(MediaType.APPLICATION_JSON).build();
	}

}
