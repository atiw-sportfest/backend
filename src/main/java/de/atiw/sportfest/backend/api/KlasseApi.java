package de.atiw.sportfest.backend.api;

import excel.exports.DBToExcelDisziplin;
import excel.exports.DBToExcelExporter;
import excel.exports.DBToExcelSchueler;
import excel.imports.ExcelToDBImporter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;

import de.atiw.sportfest.backend.model.Anmeldung;
import de.atiw.sportfest.backend.model.Disziplin;
import de.atiw.sportfest.backend.model.Ergebnis;
import de.atiw.sportfest.backend.model.Klasse;
import de.atiw.sportfest.backend.model.KlasseMitPunkten;
import de.atiw.sportfest.backend.model.Schueler;
import io.swagger.annotations.*;
import javax.validation.constraints.*;
import javax.ws.rs.*;

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

        List<DBToExcelDisziplin> disz = em.createNamedQuery("disziplin.list", Disziplin.class).getResultList().stream()
            .map(d -> new DBToExcelDisziplin( d.getBezeichnung(), d.getId().intValue(), d.getTeam() == null ? false : d.getTeam(), 0, 5)) //TODO: Disziplin.{min,max}
            .collect(Collectors.toList());

        List<DBToExcelSchueler> schueler = em.createNamedQuery("schueler.listByKlasse", Schueler.class).setParameter("kid", kid).getResultList().stream()
            .map(s -> new DBToExcelSchueler(s.getNachname(), s.getNachname(), s.getId().intValue()))
            .collect(Collectors.toList());

        Klasse k = em.find(Klasse.class, kid);

        StreamingOutput stream = new StreamingOutput(){
            public void write(OutputStream os) throws IOException, WebApplicationException {
                DBToExcelExporter.export(os, k.getBezeichnung(), schueler, disz);
                os.flush();
            }
        };

        return Response.ok().entity(stream).header("Content-Disposition", String.format("attachment; filename=%s.xlsx", k.getBezeichnung())).build();
    }

    @POST
    @Path("/{kid}/anmeldebogen")
    @Consumes({ "multipart/form-data" })
    
    @ApiOperation(value = "Anmeldebogen für eine Klasse hochladen", notes = "", response = void.class, tags={ "Anmeldung",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 204, message = "Anmeldungen erstellt", response = void.class) })
    public Response klasseKidAnmeldebogenPost(@PathParam("kid") @ApiParam("Klassen-ID") Long kid, @Multipart(value = "file") InputStream fileInputStream,
   @Multipart(value = "file") Attachment fileDetail) throws IOException {

        ExcelToDBImporter.importTeilnahmen(fileInputStream).stream().forEach(t ->
                em.merge(new Anmeldung()
                    .disziplin(new Disziplin().id(new Integer(t.getDisziplinID()).longValue()))
                    .schueler(new Schueler().id(new Integer(t.getSchuelerID()).longValue())))
                );

        return Response.noContent().build();
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
    @ApiOperation(value = "Schueler einer Klasse anzuzeigen", notes = "", response = Schueler.class, responseContainer = "List", tags={ "Teilnehmer",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Schueler_pl", response = Schueler.class, responseContainer = "List") })
    public List<Schueler> klasseKidSchuelerGet(@PathParam("kid") @ApiParam("Klassen-ID") Long kid) {
        return em.createNamedQuery("schueler.listByKlasse", Schueler.class).setParameter("kid", kid).getResultList();
    }

    @GET
    @Path("/summary")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Alle Klassen mit Punkten auflisten", notes = "", response = KlasseMitPunkten.class, responseContainer = "List", tags={ "Teilnehmer" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Klassen mit Punkten", response = KlasseMitPunkten.class, responseContainer = "List") })
    public Response klasseSummaryGet() {
        return Response.ok().entity("magic!").build();
    }
}

// vim: set ts=4 sw=4 tw=0 et :
