package de.atiw.sportfest.backend.api;

import de.atiw.sportfest.backend.model.Anmeldung;
import de.atiw.sportfest.backend.model.Ergebnis;
import de.atiw.sportfest.backend.model.Klasse;
import de.atiw.sportfest.backend.model.Schueler;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;

import java.util.List;
import javax.validation.constraints.*;

@Path("/klasse")

@Api(description = "the klasse API")




public class KlasseApi  {

    @GET
    
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Klassen auflisten", notes = "", response = Klasse.class, responseContainer = "List", tags={ "Teilnehmer",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Klassen", response = Klasse.class, responseContainer = "List") })
    public Response klasseGet() {
        return Response.ok().entity("magic!").build();
    }

    @GET
    @Path("/{kid}/anmeldungen")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Anmeldungen einer Klasse anzuzeigen", notes = "", response = Anmeldung.class, responseContainer = "List", tags={ "Anmeldung",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Anmeldungen", response = Anmeldung.class, responseContainer = "List") })
    public Response klasseKidAnmeldungenGet(@PathParam("kid") @ApiParam("Klassen-ID") Long kid) {
        return Response.ok().entity("magic!").build();
    }

    @DELETE
    @Path("/{kid}")
    
    
    @ApiOperation(value = "Klasse löschen", notes = "", response = void.class, tags={ "Teilnehmer",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 204, message = "Gelöscht.", response = void.class) })
    public Response klasseKidDelete(@PathParam("kid") @ApiParam("Klassen-ID") Long kid) {
        return Response.ok().entity("magic!").build();
    }

    @GET
    @Path("/{kid}/ergebnisse/{did}")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Ergebnisse einer Klasse fuer eine Disziplin anzeigen", notes = "", response = Ergebnis.class, responseContainer = "List", tags={ "Teilnehmer",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Ergebnisse", response = Ergebnis.class, responseContainer = "List") })
    public Response klasseKidErgebnisseDidGet(@PathParam("did") @ApiParam("Disziplin-ID") Integer did,@PathParam("kid") @ApiParam("Klassen-ID") Long kid) {
        return Response.ok().entity("magic!").build();
    }

    @GET
    @Path("/{kid}/ergebnisse")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Ergebnisse einer Klasse anzuzeigen", notes = "", response = Ergebnis.class, responseContainer = "List", tags={ "Teilnehmer",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Ergebnisse", response = Ergebnis.class, responseContainer = "List") })
    public Response klasseKidErgebnisseGet(@PathParam("kid") @ApiParam("Klassen-ID") Long kid) {
        return Response.ok().entity("magic!").build();
    }

    @GET
    @Path("/{kid}")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Klasse abrufen", notes = "", response = Klasse.class, tags={ "Teilnehmer",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Klasse", response = Klasse.class) })
    public Response klasseKidGet(@PathParam("kid") @ApiParam("Klassen-ID") Long kid) {
        return Response.ok().entity("magic!").build();
    }

    @GET
    @Path("/{kid}/schueler")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Schueler einer Klasse anzuzeigen", notes = "", response = Schueler.class, responseContainer = "List", tags={ "Teilnehmer" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Schueler_pl", response = Schueler.class, responseContainer = "List") })
    public Response klasseKidSchuelerGet(@PathParam("kid") @ApiParam("Klassen-ID") Long kid) {
        return Response.ok().entity("magic!").build();
    }
}
