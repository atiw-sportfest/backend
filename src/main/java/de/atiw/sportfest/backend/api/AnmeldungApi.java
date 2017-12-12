package de.atiw.sportfest.backend.api;

import java.util.List;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;

import de.atiw.sportfest.backend.model.Anmeldung;
import io.swagger.annotations.*;
import javax.validation.constraints.*;
import javax.ws.rs.*;

@Path("/anmeldung")

@Api(description = "the anmeldung API")




@Stateless
@Lock(LockType.READ)
public class AnmeldungApi  {

    @PersistenceContext
    EntityManager em;

    @DELETE
    @Path("/{aid}")
    
    
    @ApiOperation(value = "Anmeldung löschen", notes = "", response = void.class, tags={ "Anmeldung",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 204, message = "Anmeldung gelöscht", response = void.class) })
    public Response anmeldungAidDelete(@PathParam("aid") @ApiParam("Anmeldungs-ID") Long aid) throws NotFoundException {
        em.remove(anmeldungAidGet(aid)); // AidGet throws NotFoundException
        return Response.noContent().build();
    }

    @GET
    @Path("/{aid}")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Anmeldung anzeigen", notes = "", response = Anmeldung.class, tags={ "Anmeldung",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Anmeldung", response = Anmeldung.class) })
    public Anmeldung anmeldungAidGet(@PathParam("aid") @ApiParam("Anmeldungs-ID") Long aid) throws NotFoundException {

        Anmeldung a = em.find(Anmeldung.class, aid);

        if(a == null)
            throw new NotFoundException();

        return a;
    }

    @GET
    
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Anmeldungen anzeigen", notes = "", response = Anmeldung.class, responseContainer = "List", tags={ "Anmeldung" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Anmeldungen", response = Anmeldung.class, responseContainer = "List") })
    public List<Anmeldung> anmeldungGet() {
        return em.createNamedQuery("anmeldung.list", Anmeldung.class).getResultList();
    }
}

