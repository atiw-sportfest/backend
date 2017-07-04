package de.atiw.sportfest.backend.resource;


import java.sql.SQLException;
import java.util.ArrayList;
import javax.annotation.Resource;
import javax.sql.DataSource;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
    public Response getAlleDiziplinen() throws Exception{
    	try {

            return Response.ok(Disziplin.getAll(db.getConnection())).build();

		} catch (Exception e) {
			throw e;
		}
    }
    
    @GET
    @Path("/{did}")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response getDiziplin(@PathParam("did") String did) throws Exception{

	    try {

            return Response.ok(Disziplin.getOne(db.getConnection(), did)).build();
            
        } catch (Exception e) {

        	throw e;

		}
    }
    
    
    @PUT
    @Secured({ Role.admin })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Disziplin putDisziplin(Disziplin disziplin) throws SQLException, BadRequestException {

        return Disziplin.create(db.getConnection(), disziplin);
    }
    
    @POST
    @Secured({ Role.admin })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Disziplin editDisziplin(Disziplin disziplin) throws Exception {
			return Disziplin.edit(db.getConnection(), disziplin);
    }
    
    @POST
    @Secured({ Role.admin })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/{did}")
	public Disziplin editDisziplin(@PathParam("did") String did, Disziplin disziplin) throws SQLException, BadRequestException, NotFoundException {
        return Disziplin.edit(db.getConnection(), did, disziplin);
    }

    @DELETE
    @Path("/{did}")
    @Secured({ Role.admin })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response deleteDisziplin(@PathParam("did") String did) throws Exception{

    	try {

			Disziplin.delete(db.getConnection(), did);
			return Response.ok().build();

    	} catch (Exception e) {

        	throw e;

		}
    }
}
