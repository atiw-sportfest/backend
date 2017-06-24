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
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.atiw.sportfest.backend.ExceptionResponse;
import de.atiw.sportfest.backend.auth.Role;
import de.atiw.sportfest.backend.auth.Secured;
import de.atiw.sportfest.backend.resource.jaxb.Disziplin;

@Path("/disziplin")
public class DisziplinResource {

	ArrayList<Disziplin> dis = new ArrayList<Disziplin>(); 
	
	@Resource(name="jdbc/sportfest")
    DataSource db;
	
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response getAlleDiziplinen(){
        Response response = null; 
		Connection connection = null;
    	try {		
    		connection = db.getConnection();
    		ArrayList<Disziplin> returner = new ArrayList<Disziplin>();
			ResultSet rs = Disziplin.getRSgetAll(connection);
			while(rs.next()){
				returner.add(new Disziplin(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getBoolean(6), rs.getBoolean(7)));
			}
			response = Response.ok(returner).build();
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
    @Path("/{did}")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response getDiziplin(@PathParam("did") String did){
        Response response = null; 
		Connection connection = null;
	    try{
	    	connection = db.getConnection();
			ResultSet rs = Disziplin.getRSgetOne(connection, did);
			if(rs.next()){
				response = Response.ok(new Disziplin(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getBoolean(6), rs.getBoolean(7))).build();
			}else {
				response = Response.status(Response.Status.NOT_FOUND).build();
			}
    	}
    	catch (Exception e) {
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
	public Response putDisziplin(Disziplin disziplin){
		Response response = null;
		Connection connection = null;
    	try {
    		connection = db.getConnection();
			Disziplin.getRSput(connection, disziplin);
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
    
    @POST
    @Secured({ Role.admin })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response editDisziplin(Disziplin disziplin){
		Response response = null;
		Connection connection = null;
    	try {
    		connection = db.getConnection();
			Disziplin.getRSpost(connection, disziplin);
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
    @Path("/{did}")
    @Secured({ Role.admin })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response deleteDisziplin(@PathParam("did") String did){
		Response response = null;
		Connection connection = null;
    	try {
    		connection = db.getConnection();
			Disziplin.getRSdelete(connection, did);
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
