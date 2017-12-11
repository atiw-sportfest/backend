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
import de.atiw.sportfest.backend.model.Schueler;
import io.swagger.annotations.*;
import javax.validation.constraints.*;
import javax.ws.rs.*;

@Path("/anmeldebogen")

@Api(description = "the anmeldebogen API")




public class AnmeldebogenApi  {

    @PersistenceContext
    EntityManager em;

    @GET
    @Path("/{kid}")
    
    @Produces({ "application/vnd.ms-excel" })
    @ApiOperation(value = "Anmeldebogen für eine Klasse herunterladen", notes = "", response = void.class, tags={ "Anmeldung",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Excel-Anmeldebogen", response = void.class) })
    public Response anmeldebogenKidGet(@PathParam("kid") @ApiParam("Klassen-ID") Long kid) {

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
    
    @Consumes({ "multipart/form-data" })
    
    @ApiOperation(value = "Anmeldebogen für eine Klasse hochladen", notes = "", response = void.class, tags={ "Anmeldung" })
    @ApiResponses(value = { 
        @ApiResponse(code = 204, message = "Anmeldungen erstellt", response = void.class) })
    public Response anmeldebogenPost( @Multipart(value = "file") InputStream fileInputStream,
   @Multipart(value = "file") Attachment fileDetail) throws IOException {

        ExcelToDBImporter.importTeilnahmen(fileInputStream).stream().forEach(t ->
                em.merge(new Anmeldung()
                    .disziplin(new Disziplin().id(new Integer(t.getDisziplinID()).longValue()))
                    .schueler(new Schueler().id(new Integer(t.getSchuelerID()).longValue())))
                );

        return Response.noContent().build();

    }
}

