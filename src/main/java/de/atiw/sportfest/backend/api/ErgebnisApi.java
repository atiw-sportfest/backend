package de.atiw.sportfest.backend.api;

import java.util.List;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;

import de.atiw.sportfest.backend.model.Ergebnis;
import io.swagger.annotations.*;
import javax.validation.constraints.*;
import javax.ws.rs.*;

@Path("/ergebnis")

@Api(description = "the ergebnis API")




@Stateless
@Lock(LockType.READ)
public class ErgebnisApi  {

    @PersistenceContext
    EntityManager em;

    @DELETE
    @Path("/{eid}")
    
    
    @ApiOperation(value = "Ergebnis löschen", notes = "", response = void.class, tags={ "Ergebnis",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 204, message = "Ergebnis gelöscht", response = void.class) })
    public Response ergebnisEidDelete(@PathParam("eid") @ApiParam("Ergebnis-ID") Long eid) {
        em.remove(ergebnisEidGet(eid));
        return Response.ok().build();
    }

    @GET
    @Path("/{eid}")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Ergebnis anzeigen", notes = "", response = Ergebnis.class, tags={ "Ergebnis",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Ergebnis", response = Ergebnis.class) })
    public Ergebnis ergebnisEidGet(@PathParam("eid") @ApiParam("Ergebnis-ID") Long eid) {
        Ergebnis e = em.find(Ergebnis.class, eid);

        if(e == null)
            throw new NotFoundException();

        return e;
    }

    @GET
    
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Ergebnisse abrufen", notes = "", response = Ergebnis.class, responseContainer = "List", tags={ "Ergebnis",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Ergebnisse", response = Ergebnis.class, responseContainer = "List") })
    public List<Ergebnis> ergebnisGet() {
        return em.createNamedQuery("ergebnis.list", Ergebnis.class).getResultList();
    }

    @GET
    @Path("/versus/{vid}")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Ergebnisse eines Versus anzeigen", notes = "", response = Ergebnis.class, responseContainer = "List", tags={ "Ergebnis" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Ergebnisse", response = Ergebnis.class, responseContainer = "List") })
    public Response ergebnisVersusVidGet(@PathParam("vid") @ApiParam("Versus-ID") Long vid) {
        return Response.ok().entity("magic!").build();
    }
}

