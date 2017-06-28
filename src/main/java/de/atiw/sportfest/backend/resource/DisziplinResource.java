package de.atiw.sportfest.backend.resource;


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
	public Response putDisziplin(Disziplin disziplin) throws Exception{

    	try {

			Disziplin.create(db.getConnection(), disziplin);
            return Response.ok().build();

    	} catch (Exception e) {

        	throw e;

		}
    }
    
    @POST
    @Secured({ Role.admin })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response editDisziplin(Disziplin disziplin) throws Exception{

        //if(disziplin.getDid() == 0) // by default, AUTO_INCREMENT starts at 1
        //    return Response.status(Response.Status.BAD_REQUEST).entity("Missing ID in body!").build();

    	try {

			Disziplin.edit(db.getConnection(), disziplin);
			return Response.ok().build();

    	} catch (Exception e) {

        	throw e;

		}

    }
    
    @POST
    @Secured({ Role.admin })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/{did}")
	public Response editDisziplin(@PathParam("did") String did, Disziplin disziplin) throws Exception{

    	try {

			Disziplin.edit(db.getConnection(), did, disziplin);
			return Response.ok().build();

    	} catch (Exception e) {

        	throw e;

		}
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
