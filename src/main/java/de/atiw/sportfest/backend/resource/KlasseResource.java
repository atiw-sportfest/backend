package de.atiw.sportfest.backend.resource;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.atiw.sportfest.backend.ExceptionResponse;
import de.atiw.sportfest.backend.auth.Role;
import de.atiw.sportfest.backend.auth.Secured;
import de.atiw.sportfest.backend.resource.jaxb.Klasse;

@Path("/klasse")
public class KlasseResource {

	@Resource(name="jdbc/sportfest")
    DataSource db;
	
	@GET
	@Path("/{kid}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response getKlasse(@PathParam("kid") String kid){
		Response response = null;
		Connection connection = null;
		try{
    		connection = db.getConnection();
			ResultSet rs = Klasse.getRSgetOne(connection, kid);
			if(rs.next()){
				response = Response.ok(new Klasse(rs.getInt(1), rs.getString(2))).build();
			}else{ 
				response = Response.status(Response.Status.NOT_FOUND).build();
			}
    	} catch (Exception e) {
			response = ExceptionResponse.internalServerError(e);
		}finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
    	return response;			
    }
	
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getKlassen(){
		Response response = null;
		Connection connection = null;
    	try {
    		ArrayList<Klasse> returner = new ArrayList<Klasse>();
    		connection = db.getConnection();
			ResultSet rs = Klasse.getRSgetAll(connection);
			while(rs.next()){
				returner.add(new Klasse(rs.getInt(1), rs.getString(2)));
			}
			response = Response.ok(returner).build();
		} catch (Exception e) {
			response = ExceptionResponse.internalServerError(e);
		}finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
    	return response;
	}
	
	@PUT
    @Secured({ Role.admin })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response putKlasse(Klasse klasse){
		Response response = null;
		Connection connection = null;
    	try {
    		connection = db.getConnection();
			Klasse.getRSput(connection, klasse);
			response = Response.ok().build();
    	} catch (SQLException e) {
    		response = ExceptionResponse.internalServerError(e);
		}finally {
			try {
				connection.close();
			} catch (SQLException e) {
	    		response = ExceptionResponse.internalServerError(e);
			}
		}
    	return response;
    }
    
    @DELETE
    @Path("/{kid}")
    @Secured({ Role.admin })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response deleteKlasse(@PathParam("kid") String kid){
		Response response = null;
		Connection connection = null;
    	try {
    		connection = db.getConnection();
			Klasse.getRSdelete(connection, kid);
			response = Response.ok().build();
    	} catch (SQLException e) {
    		response = ExceptionResponse.internalServerError(e);
		}finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
    	return response;
    }
	
}