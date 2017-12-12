package de.atiw.sportfest.backend.api;


import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;

import java.util.List;
import javax.validation.constraints.*;

@Path("/sportfest")

@Api(description = "the sportfest API")




public class SportfestApi  {

    @DELETE
    
    
    
    @ApiOperation(value = "Sportfest zurücksetzen", notes = "", response = void.class, tags={ "Meta",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 204, message = "Sportfest zurückgesetzt", response = void.class) })
    public Response sportfestDelete() {
        return Response.ok().entity("magic!").build();
    }

    @POST
    
    
    
    @ApiOperation(value = "Sportfest beenden", notes = "", response = void.class, tags={ "Meta" })
    @ApiResponses(value = { 
        @ApiResponse(code = 204, message = "Sportfest beendet", response = void.class) })
    public Response sportfestPost() {
        return Response.ok().entity("magic!").build();
    }
}

