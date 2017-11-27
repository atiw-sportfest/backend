package de.atiw.sportfest.backend.api;

import de.atiw.sportfest.backend.model.Leistung;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;

import java.util.List;
import javax.validation.constraints.*;

@Path("/leistung")

@Api(description = "the leistung API")




public class LeistungApi  {

    @GET
    
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Leistungen anzeigen", notes = "", response = Leistung.class, responseContainer = "List", tags={ "Ergebnis",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Leistungen", response = Leistung.class, responseContainer = "List") })
    public Response leistungGet() {
        return Response.ok().entity("magic!").build();
    }

    @DELETE
    @Path("/{lid}")
    
    
    @ApiOperation(value = "Leistung löschen", notes = "", response = void.class, tags={ "Ergebnis",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 204, message = "Leistung gelöscht.", response = void.class) })
    public Response leistungLidDelete(@PathParam("lid") @ApiParam("Leistungs-ID") Long lid) {
        return Response.ok().entity("magic!").build();
    }

    @GET
    @Path("/{lid}")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Leistung anzeigen", notes = "", response = Leistung.class, tags={ "Ergebnis",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Leistung", response = Leistung.class) })
    public Response leistungLidGet(@PathParam("lid") @ApiParam("Leistungs-ID") Long lid) {
        return Response.ok().entity("magic!").build();
    }

    @POST
    @Path("/{lid}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "Leistung bearbeiten", notes = "", response = Leistung.class, tags={ "Ergebnis" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Leistung", response = Leistung.class) })
    public Response leistungLidPost(@PathParam("lid") @ApiParam("Leistungs-ID") Long lid,Leistung leistung) {
        return Response.ok().entity("magic!").build();
    }
}

