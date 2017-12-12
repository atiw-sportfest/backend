package de.atiw.sportfest.backend.api;

import java.util.List;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;

import de.atiw.sportfest.backend.model.Typ;
import io.swagger.annotations.*;
import javax.validation.constraints.*;
import javax.ws.rs.*;

@Path("/typ")

@Api(description = "the typ API")




@Stateless
@Lock(LockType.READ)
public class TypApi  {

    @PersistenceContext
    EntityManager em;

    @GET
    
    
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "Typen auflisten", response = Typ.class, responseContainer = "List", tags={ "Meta",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Typen", response = Typ.class, responseContainer = "List") })
    public List<Typ> typGet() {
        return em.createNamedQuery("typ.list", Typ.class).getResultList();
    }

    @POST
    
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "Typ anlegen", response = Typ.class, tags={ "Meta",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Typ", response = Typ.class) })
    public Typ typPost(Typ typ) {
        return em.merge(typ);
    }

    @DELETE
    @Path("/{typid}")
    
    
    @ApiOperation(value = "", notes = "Typ löschen", response = void.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 204, message = "Typ gelöscht", response = void.class) })
    public Response typTypidDelete(@PathParam("typid") @ApiParam("Typ-ID") Long typid) {
        em.remove(typTypidGet(typid));
        return Response.noContent().build();
    }

    @GET
    @Path("/{typid}")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "Typ abrufen", response = Typ.class, tags={ "Meta",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Typ", response = Typ.class) })
    public Typ typTypidGet(@PathParam("typid") @ApiParam("Typ-ID") Long typid) {
        Typ t = em.find(Typ.class, typid);

        if(t == null)
            throw new NotFoundException();

        return t;
    }

    @POST
    @Path("/{typid}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "Typ ändern", response = Typ.class, tags={ "Meta" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Typ", response = Typ.class) })
    public Typ typTypidPost(@PathParam("typid") @ApiParam("Typ-ID") Long typid,Typ typ) {
        typ.id(typid);
        return em.merge(typ);
    }
}

