package de.atiw.sportfest.backend.resource;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.print.attribute.standard.RequestingUserName;
import javax.sql.DataSource;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
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
    public List<Leistung> getAlleLeistungen() throws SQLException {
        return Leistung.getAll(db.getConnection(), true);
    }
    
    @GET
    @Path("/{lid:\\d+}")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Leistung getLeistung(@PathParam("lid") String lid) throws SQLException, NotFoundException {
            return Leistung.getOne(db.getConnection(), lid ,true);
    }
    
    
    @PUT
    @Secured({ Role.admin, Role.schiedsrichter })
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Leistung createLeistung(Leistung leistung) throws SQLException, InternalServerErrorException {
        return Leistung.create(db.getConnection(), leistung, true);
    }
    
    @POST
    @Secured({ Role.admin, Role.schiedsrichter })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/{lid:\\d+}")
	public Leistung editLeistung(@PathParam("lid") String lid, Leistung leistung) throws SQLException, NotFoundException {
        return Leistung.edit(db.getConnection(), lid, leistung, true);
    }
    
    
    @DELETE
    @Path("/{lid:\\d+}")
    @Secured({ Role.admin, Role.schiedsrichter })
	public void deleteLeistung(@PathParam("lid") String lid) throws SQLException {
        Leistung.delete(db.getConnection(), lid, true);
    }
}
