package de.atiw.sportfest.backend.api;

import java.io.File;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;

import java.util.List;
import javax.validation.constraints.*;

@Path("/anmeldebogen")

@Api(description = "the anmeldebogen API")




public class AnmeldebogenApi  {

    @GET
    @Path("/{kid}")
    
    @Produces({ "application/vnd.ms-excel" })
    @ApiOperation(value = "Anmeldebogen für eine Klasse herunterladen", notes = "", response = void.class, tags={ "Anmeldung",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Excel-Anmeldebogen", response = void.class) })
    public Response anmeldebogenKidGet(@PathParam("kid") @ApiParam("Klassen-ID") Long kid) {
        return Response.ok().entity("magic!").build();
    }

    @POST
    
    @Consumes({ "multipart/form-data" })
    
    @ApiOperation(value = "Anmeldebogen für eine Klasse hochladen", notes = "", response = void.class, tags={ "Anmeldung" })
    @ApiResponses(value = { 
        @ApiResponse(code = 204, message = "Anmeldungen erstellt", response = void.class) })
    public Response anmeldebogenPost( @FormParam(value = "file") InputStream fileInputStream,
   @FormParam(value = "file") Attachment fileDetail) {
        return Response.ok().entity("magic!").build();
    }
}

