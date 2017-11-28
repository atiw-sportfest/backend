package de.atiw.sportfest.backend.api;

import de.atiw.sportfest.backend.model.Anmeldung;
import de.atiw.sportfest.backend.model.Ergebnis;
import de.atiw.sportfest.backend.model.Schueler;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;

import java.util.List;
import javax.validation.constraints.*;

@Path("/schueler")

@Api(description = "the schueler API")




public class SchuelerApi  {

    @GET
    
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Schueler auflisten", notes = "", response = Schueler.class, responseContainer = "List", tags={ "Teilnehmer",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Schueler_pl", response = Schueler.class, responseContainer = "List") })
    public Response schuelerGet() {
        return Response.ok().entity("magic!").build();
    }

    @GET
    @Path("/{sid}/anmeldungen")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Anmeldungen eines Schuelers anzuzeigen", notes = "", response = Anmeldung.class, responseContainer = "List", tags={ "Anmeldung",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Anmeldungen", response = Anmeldung.class, responseContainer = "List") })
    public Response schuelerSidAnmeldungenGet(@PathParam("sid") @ApiParam("Schueler-ID") Long sid) {
        return Response.ok().entity("magic!").build();
    }

    @DELETE
    @Path("/{sid}/")
    
    
    @ApiOperation(value = "Schueler löschen", notes = "", response = void.class, tags={ "Teilnehmer",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 204, message = "Schueler gelöscht.", response = void.class) })
    public Response schuelerSidDelete(@PathParam("sid") @ApiParam("Schueler-ID") Long sid) {
        return Response.ok().entity("magic!").build();
    }

    @GET
    @Path("/{sid}/ergebnisse/{did}")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Ergebnisse eines Schuelers fuer eine Disziplin anzeigen", notes = "", response = Ergebnis.class, responseContainer = "List", tags={ "Teilnehmer",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Ergebnisse", response = Ergebnis.class, responseContainer = "List") })
    public Response schuelerSidErgebnisseDidGet(@PathParam("did") @ApiParam("Disziplin-ID") Integer did,@PathParam("sid") @ApiParam("Schueler-ID") Long sid) {
        return Response.ok().entity("magic!").build();
    }

    @GET
    @Path("/{sid}/ergebnisse")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Ergebnisse eines Schuelers anzuzeigen", notes = "", response = Ergebnis.class, responseContainer = "List", tags={ "Teilnehmer",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Ergebnisse", response = Ergebnis.class, responseContainer = "List") })
    public Response schuelerSidErgebnisseGet(@PathParam("sid") @ApiParam("Schueler-ID") Long sid) {
        return Response.ok().entity("magic!").build();
    }

    @GET
    @Path("/{sid}/")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Schueler abrufen", notes = "", response = Schueler.class, tags={ "Teilnehmer" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Schueler", response = Schueler.class) })
    public Response schuelerSidGet(@PathParam("sid") @ApiParam("Schueler-ID") Long sid) {
        return Response.ok().entity("magic!").build();
    }
}

