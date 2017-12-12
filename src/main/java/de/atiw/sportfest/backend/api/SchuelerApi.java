package de.atiw.sportfest.backend.api;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;

import de.atiw.sportfest.backend.model.Anmeldung;
import de.atiw.sportfest.backend.model.Ergebnis;
import de.atiw.sportfest.backend.model.Klasse;
import de.atiw.sportfest.backend.model.Schueler;
import io.swagger.annotations.*;
import javax.validation.constraints.*;
import javax.ws.rs.*;
import org.apache.commons.csv.*;

@Path("/schueler")

@Api(description = "the schueler API")




@Stateless
@Lock(LockType.READ)
public class SchuelerApi  {

    @PersistenceContext
    EntityManager em;

    @GET
    
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Schueler auflisten", notes = "", response = Schueler.class, responseContainer = "List", tags={ "Teilnehmer",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Schueler_pl", response = Schueler.class, responseContainer = "List") })
    @Lock(LockType.READ)
    public List<Schueler> schuelerGet() {
        return em.createNamedQuery("schueler.list", Schueler.class).getResultList();
    }

    @PUT
    
    @Consumes({ "multipart/form-data" })
    
    @ApiOperation(value = "Schüler importieren", notes = "Schüler aus einer CSV-Datei importieren. Die Datei muss folgende Spalten in der angegebenen Reihenfolge enthalten: Nachname, Vorname, Klasse, Geschlecht.", response = Schueler.class, responseContainer = "List", tags={ "Teilnehmer",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Schueler_pl", response = Schueler.class, responseContainer = "List") })
    @Lock(LockType.WRITE)
    public List<Schueler> schuelerPut(@Multipart("csv") Attachment csvDetail) throws IOException {

        Map<String, Klasse> klassen = new HashMap<>();

        return CSVFormat.DEFAULT
            .parse(new InputStreamReader(csvDetail.getObject(InputStream.class))) // https://stackoverflow.com/a/26329902
            .getRecords().stream()
            .map(record -> {

                if(record.size() == 4){

                    String kName = record.get(2);
                    Klasse k = klassen.get(kName);

                    if(k == null) {
                        try {
                            k = em.createNamedQuery("klasse.findByName", Klasse.class).setParameter("name", kName).getSingleResult();
                        } catch(NonUniqueResultException|NoResultException e){
                            k = em.merge(new Klasse().bezeichnung(record.get(2)));
                        }
                        klassen.put(kName, k);
                    }

                    return em.merge(new Schueler()
                            .nachname(record.get(0))
                            .vorname(record.get(1))
                            .klasse(k)
                            .geschlecht(record.get(3)));
                }
                throw new BadRequestException("Zu wenig Spalten!");
            }).collect(Collectors.toList());
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
        em.remove(schuelerSidGet(sid));
        return Response.ok().build();
    }

    @GET
    @Path("/{sid}/ergebnisse/{did}")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Ergebnisse eines Schuelers fuer eine Disziplin anzeigen", notes = "", response = Ergebnis.class, responseContainer = "List", tags={ "Teilnehmer",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Ergebnisse", response = Ergebnis.class, responseContainer = "List") })
    public List<Ergebnis> schuelerSidErgebnisseDidGet(@PathParam("did") @ApiParam("Disziplin-ID") Long did,@PathParam("sid") @ApiParam("Schueler-ID") Long sid) {
        return em.createNamedQuery("ergebnis.listByDisziplinAndSchueler", Ergebnis.class).setParameter("did", did).setParameter("sid", sid).getResultList();
    }

    @GET
    @Path("/{sid}/ergebnisse")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Ergebnisse eines Schuelers anzuzeigen", notes = "", response = Ergebnis.class, responseContainer = "List", tags={ "Teilnehmer",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Ergebnisse", response = Ergebnis.class, responseContainer = "List") })
    public List<Ergebnis> schuelerSidErgebnisseGet(@PathParam("sid") @ApiParam("Schueler-ID") Long sid) {
        return em.createNamedQuery("ergebnis.listBySchueler", Ergebnis.class).setParameter("sid", sid).getResultList();
    }

    @GET
    @Path("/{sid}/")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Schueler abrufen", notes = "", response = Schueler.class, tags={ "Teilnehmer" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Schueler", response = Schueler.class) })
    public Schueler schuelerSidGet(@PathParam("sid") @ApiParam("Schueler-ID") Long sid) {
        Schueler s = em.find(Schueler.class, sid);

        if(s == null)
            throw new NotFoundException();

        return s;
    }
}

