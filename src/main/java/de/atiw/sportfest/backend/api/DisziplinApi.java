package de.atiw.sportfest.backend.api;

import de.atiw.sportfest.backend.model.Anmeldung;
import de.atiw.sportfest.backend.model.Disziplin;
import de.atiw.sportfest.backend.model.Ergebnis;
import de.atiw.sportfest.backend.model.Leistung;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;

import java.util.List;
import javax.validation.constraints.*;

@Path("/disziplin")

@Api(description = "the disziplin API")




public class DisziplinApi  {

    @GET
    @Path("/{did}/anmeldungen")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Anmeldungen einer Disziplin anzeigen", notes = "", response = Anmeldung.class, responseContainer = "List", tags={ "Disziplin", "Anmeldung",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Anmeldungen", response = Anmeldung.class, responseContainer = "List") })
    public Response disziplinDidAnmeldungenGet(@PathParam("did") @ApiParam("Disziplin-ID") Long did) {
        return Response.ok().entity("magic!").build();
    }

    @DELETE
    @Path("/{did}")
    
    
    @ApiOperation(value = "Disziplin löschen", notes = "", response = void.class, tags={ "Disziplin",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 204, message = "Disziplin gelöscht", response = void.class) })
    public Response disziplinDidDelete(@PathParam("did") @ApiParam("Disziplin-ID") Long did) {
        return Response.ok().entity("magic!").build();
    }

    @GET
    @Path("/{did}/ergebnisse")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Ergebnisse einer Disziplin anzeigen", notes = "", response = Ergebnis.class, responseContainer = "List", tags={ "Disziplin", "Ergebnis",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Ergebnisse", response = Ergebnis.class, responseContainer = "List") })
    public Response disziplinDidErgebnisseGet(@PathParam("did") @ApiParam("Disziplin-ID") Long did) {
        return Response.ok().entity("magic!").build();
    }

    @POST
    @Path("/{did}/ergebnisse")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Ergebnisse für eine Disziplin anlegen", notes = "Für die anzulegenden Ergebnisse wird die Disziplin-ID mit der im Pfad angegenen ID überschrieben.", response = Ergebnis.class, responseContainer = "List", tags={ "Disziplin", "Ergebnis",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Ergebnisse", response = Ergebnis.class, responseContainer = "List") })
    public Response disziplinDidErgebnissePost(@PathParam("did") @ApiParam("Disziplin-ID") Long did,List<Ergebnis> ergebnisse) {
        return Response.ok().entity("magic!").build();
    }

    @GET
    @Path("/{did}")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Disziplin anzeigen", notes = "", response = Disziplin.class, tags={ "Disziplin",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Disziplin erfolgreich erstellt", response = Disziplin.class) })
    public Response disziplinDidGet(@PathParam("did") @ApiParam("Disziplin-ID") Long did) {
        return Response.ok().entity("magic!").build();
    }

    @GET
    @Path("/{did}/leistungen")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Leistungen einer Disziplin anzeigen", notes = "", response = Leistung.class, responseContainer = "List", tags={ "Disziplin", "Ergebnis",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Leistungen", response = Leistung.class, responseContainer = "List") })
    public Response disziplinDidLeistungenGet(@PathParam("did") @ApiParam("Disziplin-ID") Long did) {
        return Response.ok().entity("magic!").build();
    }

    @POST
    @Path("/{did}/leistungen/{tid}")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Leistungen fuer eine Disziplin anlegen", notes = "", response = Leistung.class, responseContainer = "List", tags={ "Disziplin", "Ergebnis",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Leistungen", response = Leistung.class, responseContainer = "List") })
    public Response disziplinDidLeistungenTidPost(@PathParam("did") @ApiParam("Disziplin-ID") Long did,@PathParam("tid") @ApiParam("Schueler- oder Klassen-ID") Long tid,List<Leistung> leistungen) {
        return Response.ok().entity("magic!").build();
    }

    @POST
    @Path("/{did}")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Disziplin bearbeiten", notes = "", response = Disziplin.class, tags={ "Disziplin",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Disziplin erfolgreich erstellt", response = Disziplin.class) })
    public Response disziplinDidPost(@PathParam("did") @ApiParam("Disziplin-ID") Long did,Disziplin body) {
        return Response.ok().entity("magic!").build();
    }

    @GET
    
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Disziplinen auflisten", notes = "", response = Disziplin.class, responseContainer = "List", tags={ "Disziplin",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "successful operation", response = Disziplin.class, responseContainer = "List") })
    public Response disziplinGet() {
        return Response.ok().entity("magic!").build();
    }

    @POST
    
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "Disziplin anlegen", notes = "", response = Disziplin.class, tags={ "Disziplin" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Disziplin erfolgreich erstellt", response = Disziplin.class) })
    public Response disziplinPost(Disziplin disziplin) {
        return Response.ok().entity("magic!").build();
    }
}

