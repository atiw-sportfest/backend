package de.atiw.sportfest.backend.api;

import de.atiw.sportfest.backend.model.Ergebnis;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;

import java.util.List;
import javax.validation.constraints.*;

@Path("/ergebnis")

@Api(description = "the ergebnis API")




public class ErgebnisApi  {

    @DELETE
    @Path("/{eid}")
    
    
    @ApiOperation(value = "Ergebnis löschen", notes = "", response = void.class, tags={ "Ergebnis",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 204, message = "Ergebnis gelöscht", response = void.class) })
    public Response ergebnisEidDelete(@PathParam("eid") @ApiParam("Ergebnis-ID") Integer eid) {
        return Response.ok().entity("magic!").build();
    }

    @GET
    @Path("/{eid}")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Ergebnis anzeigen", notes = "", response = Ergebnis.class, tags={ "Ergebnis",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Ergebnis", response = Ergebnis.class) })
    public Response ergebnisEidGet(@PathParam("eid") @ApiParam("Ergebnis-ID") Integer eid) {
        return Response.ok().entity("magic!").build();
    }

    @GET
    
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Ergebnisse abrufen", notes = "", response = Ergebnis.class, responseContainer = "List", tags={ "Ergebnis",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Ergebnisse", response = Ergebnis.class, responseContainer = "List") })
    public Response ergebnisGet() {
        return Response.ok().entity("magic!").build();
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

