package de.atiw.sportfest.backend.resource;


import java.sql.SQLException;
import java.util.ArrayList;
import javax.annotation.Resource;
import javax.print.attribute.standard.RequestingUserName;
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
import de.atiw.sportfest.backend.resource.jaxb.Disziplin.NotFoundException;
import de.atiw.sportfest.backend.resource.jaxb.Leistung;

@Path("/leistung")
public class LeistungResource {

	ArrayList<Leistung> dis = new ArrayList<Leistung>(); 
	
	@Resource(name="jdbc/sportfest")
    DataSource db;
	
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response getAlleDiziplinen(){
    	try {
    		return Response.ok(Leistung.getAll(db.getConnection(),true)).build();
		} catch (SQLException | NotFoundException e) {
			return ExceptionResponse.internalServerError(e);
	    } 
    }
    
    @GET
    @Path("/{lid}")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response getDiziplin(@PathParam("lid") String lid){
    	try {
            return Response.ok(Leistung.getOne(db.getConnection(), lid ,true)).build();
    	} catch (Exception e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
    	}
		
    }
    
    
    @PUT
   // @Secured({ Role.admin })
    @Path("/{isTeamleistung}")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response putDisziplin(@PathParam("isTeamleistung") String isTeamleistung, Leistung leistung){
    	try {
	    	if(isTeamleistung.equals("1"))
	    		Leistung.putKlassenleistung(db.getConnection(), leistung);
	    	else if (isTeamleistung.equals("2")) 
	    		Leistung.putSchuelerleistung(db.getConnection(), leistung);
            return Response.ok().build();
    	} catch (SQLException e) {
    		return ExceptionResponse.internalServerError(e);
		}
    }
    
    @POST
   // @Secured({ Role.admin })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response editDisziplin(Leistung leistung){
    	try {
			Leistung.post(db.getConnection(), leistung);
			return Response.ok().build();
    	} catch (SQLException e) {
    		return ExceptionResponse.internalServerError(e);
		}

    }
    
    
    @DELETE
    @Path("/{lid}")
  //  @Secured({ Role.admin })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response deleteDisziplin(@PathParam("lid") String lid){

    	try {

			Leistung.delete(db.getConnection(), lid);
			return Response.ok().build();

    	} catch (SQLException e) {

    		return ExceptionResponse.internalServerError(e);

		}
    }
}
