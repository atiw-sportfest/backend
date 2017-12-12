package de.atiw.sportfest.backend.api;

import de.atiw.sportfest.backend.model.Typ;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;

import java.util.List;
import javax.validation.constraints.*;

@Path("/typ")

@Api(description = "the typ API")




public class TypApi  {

    @GET
    
    
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "Typen auflisten", response = Typ.class, responseContainer = "List", tags={ "Meta",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Typen", response = Typ.class, responseContainer = "List") })
    public Response typGet() {
        return Response.ok().entity("magic!").build();
    }

    @POST
    
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "Typ anlegen", response = Typ.class, tags={ "Meta",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Typ", response = Typ.class) })
    public Response typPost(Typ typ) {
        return Response.ok().entity("magic!").build();
    }

    @DELETE
    @Path("/{typid}")
    
    
    @ApiOperation(value = "", notes = "Typ löschen", response = void.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 204, message = "Typ gelöscht", response = void.class) })
    public Response typTypidDelete(@PathParam("typid") @ApiParam("Typ-ID") Long typid) {
        return Response.ok().entity("magic!").build();
    }

    @GET
    @Path("/{typid}")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "Typ abrufen", response = Typ.class, tags={ "Meta",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Typ", response = Typ.class) })
    public Response typTypidGet(@PathParam("typid") @ApiParam("Typ-ID") Long typid) {
        return Response.ok().entity("magic!").build();
    }

    @POST
    @Path("/{typid}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "Typ ändern", response = Typ.class, tags={ "Meta" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Typ", response = Typ.class) })
    public Response typTypidPost(@PathParam("typid") @ApiParam("Typ-ID") Long typid,Typ typ) {
        return Response.ok().entity("magic!").build();
    }
}

