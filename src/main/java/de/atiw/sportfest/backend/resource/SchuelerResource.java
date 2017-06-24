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
import de.atiw.sportfest.backend.resource.jaxb.Schueler;

@Path("/schueler")
public class SchuelerResource{

	@Resource(name="jdbc/sportfest")
    DataSource db;
	
	@GET
	@Path("/{sid}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response getSchueler(@PathParam("sid") String sid){
		Response response = null;
		Connection connection = null;
		try{
    		connection = db.getConnection();
			ResultSet rs = Schueler.getRSgetOne(connection, sid);
			if(rs.next()){
				response = Response.ok(new Schueler(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5))).build();
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
	public Response getSchueler(){
		Response response = null;
		Connection connection = null;
    	try {
    		ArrayList<Schueler> returner = new ArrayList<Schueler>();
    		connection = db.getConnection();
			ResultSet rs = Schueler.getRSgetAll(connection);
			while(rs.next()){
				returner.add(new Schueler(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5)));
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
	
	@GET
    @Path("/klasse/{kid}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getSchuelerEinerKlasse(@PathParam("kid") String kid){
		Response response = null;
		Connection connection = null;
    	try {
    		ArrayList<Schueler> returner = new ArrayList<Schueler>();
    		connection = db.getConnection();
			ResultSet rs = Schueler.getRSgetAllOfKlasse(connection, kid);
			while(rs.next()){
				returner.add(new Schueler(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5)));
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
	
	@GET
    @Path("/klasse/{kid}/disziplin/{did}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getSchuelerEinerKlasseUndDisziplin(@PathParam("kid") String kid, @PathParam("did") String did){
		Response response = null;
		Connection connection = null;
    	try {
    		ArrayList<Schueler> returner = new ArrayList<Schueler>();
    		connection = db.getConnection();
			ResultSet rs = Schueler.getRSgetAllOfKlasseAndDisziplin(connection, kid, did);
			while(rs.next()){
				returner.add(new Schueler(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5)));
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
	public Response putSchueler(Schueler schueler){
		Response response = null;
		Connection connection = null;
    	try {
    		connection = db.getConnection();
    		Schueler.getRSput(connection, schueler);
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
    @Path("/{sid}")
    @Secured({ Role.admin })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response deleteSchueler(@PathParam("sid") String sid){
		Response response = null;
		Connection connection = null;
    	try {
    		connection = db.getConnection();
    		Schueler.getRSdelete(connection, sid);
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
