package de.atiw.sportfest.backend.api;


import java.util.List;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;

import de.atiw.sportfest.backend.model.Versus;
import io.swagger.annotations.*;
import javax.validation.constraints.*;
import javax.ws.rs.*;

@Path("/versus")

@Api(description = "the versus API")



@Stateless
@Lock(LockType.READ)
public class VersusApi  {

    @PersistenceContext
    EntityManager em;

    @DELETE
    @Path("/{vid}")
    
    
    @ApiOperation(value = "Versus löschen", notes = "Löscht den Versus, inklusive seiner Ergebnisse.", response = void.class, tags={ "Ergebnis" })
    @ApiResponses(value = { 
        @ApiResponse(code = 204, message = "Versus gelöscht.", response = void.class) })
    public Response versusVidDelete(@PathParam("vid") @ApiParam("Versus-ID") Long vid) {
        Versus v = em.find(Versus.class, vid);

        if(v == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        em.remove(v);

        return Response.noContent().build();
    }
}

