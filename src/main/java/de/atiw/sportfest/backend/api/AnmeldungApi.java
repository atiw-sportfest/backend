package de.atiw.sportfest.backend.api;

import de.atiw.sportfest.backend.model.Anmeldung;
import java.io.File;
import java.io.InputStream;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;

import java.util.List;
import javax.validation.constraints.*;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;

@Path("/anmeldung")

@Api(description = "the anmeldung API")




public class AnmeldungApi  {

    @DELETE
    @Path("/{aid}")
    
    
    @ApiOperation(value = "Anmeldung löschen", notes = "", response = void.class, tags={ "Anmeldung",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 204, message = "Anmeldung gelöscht", response = void.class) })
    public Response anmeldungAidDelete(@PathParam("aid") @ApiParam("Anmeldungs-ID") Integer aid) {
        return Response.ok().entity("magic!").build();
    }

    @GET
    @Path("/{aid}")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Anmeldung anzeigen", notes = "", response = Anmeldung.class, tags={ "Anmeldung",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Anmeldung", response = Anmeldung.class) })
    public Response anmeldungAidGet(@PathParam("aid") @ApiParam("Anmeldungs-ID") Integer aid) {
        return Response.ok().entity("magic!").build();
    }

    @GET
    
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Anmeldungen anzeigen", notes = "", response = Anmeldung.class, responseContainer = "List", tags={ "Anmeldung",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Anmeldungen", response = Anmeldung.class, responseContainer = "List") })
    public Response anmeldungGet() {
        return Response.ok().entity("magic!").build();
    }

    @POST
    @Path("/upload")
    @Consumes({ "multipart/form-data" })
    
    @ApiOperation(value = "Anmeldungen hochladen", notes = "", response = void.class, tags={ "Anmeldung" })
    @ApiResponses(value = { 
        @ApiResponse(code = 204, message = "Anmeldungen erstellt", response = void.class) })
    public Response anmeldungUploadPost( @FormParam(value = "file") InputStream fileInputStream,
   @FormParam(value = "file") Attachment fileDetail) {
        return Response.ok().entity("magic!").build();
    }
}

