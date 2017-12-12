package de.atiw.sportfest.backend.api;


import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;

import java.util.List;
import javax.validation.constraints.*;

@Path("/versus")

@Api(description = "the versus API")




public class VersusApi  {

    @DELETE
    @Path("/{vid}")
    
    
    @ApiOperation(value = "Versus löschen", notes = "Löscht den Versus, inklusive seiner Ergebnisse.", response = void.class, tags={ "Ergebnis" })
    @ApiResponses(value = { 
        @ApiResponse(code = 204, message = "Versus gelöscht.", response = void.class) })
    public Response versusVidDelete(@PathParam("vid") @ApiParam("Versus-ID") Long vid) {
        return Response.ok().entity("magic!").build();
    }
}

