package de.atiw.sportfest.backend.api;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import javax.ws.rs.core.Response;

import de.atiw.sportfest.backend.model.Anmeldung;
import de.atiw.sportfest.backend.model.Ergebnis;
import java.io.File;
import java.io.InputStream;
import de.atiw.sportfest.backend.model.Klasse;
import de.atiw.sportfest.backend.model.Schueler;
import io.swagger.annotations.*;
import javax.validation.constraints.*;
import javax.ws.rs.*;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;

@Path("/klasse")

@Api(description = "the klasse API")




@Stateless
public class KlasseApi  {

    @PersistenceContext
    EntityManager em;

    @GET
    @Produces({ "application/json" })
    @ApiOperation(value = "Klassen auflisten", notes = "", response = Klasse.class, responseContainer = "List", tags={ "Teilnehmer",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Klassen", response = Klasse.class, responseContainer = "List") })
    public List<Klasse> klasseGet() {
        return em.createNamedQuery("klasse.list", Klasse.class).getResultList();
    }

    @GET
    @Path("/{kid}/anmeldebogen")
    
    @Produces({ "application/vnd.ms-excel" })
    @ApiOperation(value = "Anmeldebogen für eine Klasse herunterladen", notes = "", response = byte[].class, tags={ "Anmeldung",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Excel-Anmeldebogen", response = byte[].class) })
    public Response klasseKidAnmeldebogenGet(@PathParam("kid") @ApiParam("Klassen-ID") Long kid) {
        return Response.ok().entity("magic!").build();
    }

    @POST
    @Path("/{kid}/anmeldebogen")
    @Consumes({ "multipart/form-data" })
    
    @ApiOperation(value = "Anmeldebogen für eine Klasse hochladen", notes = "", response = void.class, tags={ "Anmeldung",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 204, message = "Anmeldungen erstellt", response = void.class) })
    public Response klasseKidAnmeldebogenPost(@PathParam("kid") @ApiParam("Klassen-ID") Long kid, @FormParam(value = "file") InputStream fileInputStream,
   @FormParam(value = "file") Attachment fileDetail) {
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

        Klasse k = klasseKidGet(kid);

        em.remove(k);

        return Response.ok().build();
    }

    @GET
    @Path("/{kid}/ergebnisse/{did}")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Ergebnisse einer Klasse fuer eine Disziplin anzeigen", notes = "", response = Ergebnis.class, responseContainer = "List", tags={ "Teilnehmer",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Ergebnisse", response = Ergebnis.class, responseContainer = "List") })
    public List<Ergebnis> klasseKidErgebnisseDidGet(@PathParam("did") @ApiParam("Disziplin-ID") Long did,@PathParam("kid") @ApiParam("Klassen-ID") Long kid) {
        return em.createNamedQuery("ergebnis.listByDisziplinAndKlasse", Ergebnis.class).setParameter("did", did).setParameter("kid", kid).getResultList();
    }

    @GET
    @Path("/{kid}/ergebnisse")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Ergebnisse einer Klasse anzuzeigen", notes = "", response = Ergebnis.class, responseContainer = "List", tags={ "Teilnehmer",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Ergebnisse", response = Ergebnis.class, responseContainer = "List") })
    public List<Ergebnis> klasseKidErgebnisseGet(@PathParam("kid") @ApiParam("Klassen-ID") Long kid) {
        return em.createNamedQuery("ergebnis.listByKlasse", Ergebnis.class).setParameter("kid", kid).getResultList();
    }

    @GET
    @Path("/{kid}")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Klasse abrufen", notes = "", response = Klasse.class, tags={ "Teilnehmer",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Klasse", response = Klasse.class) })
    public Klasse klasseKidGet(@PathParam("kid") @ApiParam("Klassen-ID") Long kid) {
        Klasse k = em.find(Klasse.class, kid);

        if(k==null)
            throw new NotFoundException();

        return k;
    }

    @GET
    @Path("/{kid}/schueler")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Schueler einer Klasse anzuzeigen", notes = "", response = Schueler.class, responseContainer = "List", tags={ "Teilnehmer" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Schueler_pl", response = Schueler.class, responseContainer = "List") })
    public List<Schueler> klasseKidSchuelerGet(@PathParam("kid") @ApiParam("Klassen-ID") Long kid) {
        return em.createNamedQuery("schueler.listByKlasse", Schueler.class).setParameter("kid", kid).getResultList();
    }
}

// vim: set ts=4 sw=4 tw=0 et :
