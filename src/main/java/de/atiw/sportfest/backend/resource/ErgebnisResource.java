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
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.atiw.sportfest.backend.ExceptionResponse;
import de.atiw.sportfest.backend.auth.Role;
import de.atiw.sportfest.backend.auth.Secured;
import de.atiw.sportfest.backend.resource.jaxb.Disziplin;
import de.atiw.sportfest.backend.resource.jaxb.Ergebnis;

@Path("/ergebnis")
public class ErgebnisResource {
	
	@Resource(name="jdbc/sportfest")
    DataSource db;
	
	@GET
    public Response getErgebnisse(){
		Response response = null; 
		Connection connection = null;
    	try {		
    		connection = db.getConnection();
			response = Response.ok(Ergebnis.getAll(connection)).build();
		} catch (SQLException e) {
			e.printStackTrace();
			response = ExceptionResponse.internalServerError(e);
		}finally{
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
      	return response;
    }

	@GET
	@Path("/{eid}")
    public Response getErgebnis(@PathParam("eid") String eid){
		Response response = null; 
		Connection connection = null;
    	try {		
    		connection = db.getConnection();
			response = Response.ok(Ergebnis.getOne(connection, eid)).build();
		} catch (SQLException e) {
			e.printStackTrace();
			response = ExceptionResponse.internalServerError(e);
		}finally{
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
      	return response;
    }
	
		
	@POST
    @Secured({ Role.admin })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response postErgenis(Ergebnis ergebnis){
		Response response = null;
		Connection connection = null;
    	try {
    		connection = db.getConnection();
			Ergebnis.post(connection, ergebnis);
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
	
	@PUT
    @Secured({ Role.admin })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	 public Response putErgebnis(Ergebnis ergebnis){
		Response response = null;
		Connection connection = null;
    	try {
    		connection = db.getConnection();
			Ergebnis.put(connection, ergebnis);
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
	
	@DELETE
    @Secured({ Role.admin })
	@Path("/{eid}")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	 public Response deleteErgebnis( @PathParam("eid") String eid){
		Response response = null;
		Connection connection = null;
    	try {
    		connection = db.getConnection();
			Ergebnis.delete(connection, eid);
			connection.close();
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
