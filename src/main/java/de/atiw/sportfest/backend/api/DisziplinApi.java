package de.atiw.sportfest.backend.api;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import javax.ws.rs.core.Response;

import de.atiw.sportfest.backend.model.Anmeldung;
import de.atiw.sportfest.backend.model.Disziplin;
import de.atiw.sportfest.backend.model.Ergebnis;
import de.atiw.sportfest.backend.model.Leistung;
import io.swagger.annotations.*;
import javax.validation.constraints.*;
import javax.ws.rs.*;

@Path("/disziplin")

@Api(description = "the disziplin API")




@Lock(LockType.READ)
@Singleton
public class DisziplinApi  {

    @PersistenceContext
    EntityManager em;

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

        Disziplin d = em.find(Disziplin.class, did.longValue());

        if(d != null){
            em.remove(d);
            return Response.status(204).build();
        } else return Response.status(404).build();

    }

    @GET
    @Path("/{did}/ergebnisse")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Ergebnisse einer Disziplin anzeigen", notes = "", response = Ergebnis.class, responseContainer = "List", tags={ "Disziplin", "Ergebnis",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Ergebnisse", response = Ergebnis.class, responseContainer = "List") })
    public List<Ergebnis> disziplinDidErgebnisseGet(@PathParam("did") @ApiParam("Disziplin-ID") Long did) {
        return em.createNamedQuery("ergebnis.listByDisziplin", Ergebnis.class).setParameter("did", did).getResultList();
    }

    @POST
    @Path("/{did}/ergebnisse")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Ergebnisse für eine Disziplin anlegen", notes = "Für die anzulegenden Ergebnisse wird die Disziplin-ID mit der im Pfad angegenen ID überschrieben.", response = Ergebnis.class, responseContainer = "List", tags={ "Disziplin", "Ergebnis",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Ergebnisse", response = Ergebnis.class, responseContainer = "List") })
    public List<Ergebnis> disziplinDidErgebnissePost(@PathParam("did") @ApiParam("Disziplin-ID") Long did,List<Ergebnis> ergebnisse) {

        return ergebnisse.stream().map(e -> {

            if(e.getDisziplin() == null)
                e.setDisziplin(new Disziplin());

            e.getDisziplin().setId(did);

            return em.merge(e);

        }).collect(Collectors.toList());
    }

    @GET
    @Path("/{did}")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Disziplin anzeigen", notes = "", response = Disziplin.class, tags={ "Disziplin",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Disziplin erfolgreich erstellt", response = Disziplin.class) })
    public Disziplin disziplinDidGet(@PathParam("did") @ApiParam("Disziplin-ID") Long did) {
        return em.find(Disziplin.class, did);
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
    public Disziplin disziplinDidPost(@PathParam("did") @ApiParam("Disziplin-ID") Long did,Disziplin body) {
        body.setId(did);
        return em.merge(body);
    }

    @GET
    
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Disziplinen auflisten", notes = "", response = Disziplin.class, responseContainer = "List", tags={ "Disziplin",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "successful operation", response = Disziplin.class, responseContainer = "List") })
    public List<Disziplin> disziplinGet() {
        return em.createNamedQuery("disziplin.list", Disziplin.class).getResultList();
    }

    @POST
    
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "Disziplin anlegen", notes = "", response = Disziplin.class, tags={ "Disziplin" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Disziplin erfolgreich erstellt", response = Disziplin.class) })
    public Disziplin disziplinPost(Disziplin disziplin) {
        return em.merge(disziplin);
    }
}

// vim: set ts=4 sw=4 tw=0 et :
