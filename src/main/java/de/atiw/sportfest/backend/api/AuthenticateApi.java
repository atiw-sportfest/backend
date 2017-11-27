package de.atiw.sportfest.backend.api;

import java.io.File;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;

import java.util.List;
import javax.validation.constraints.*;

@Path("/authenticate")

@Api(description = "the authenticate API")




public class AuthenticateApi  {

    @GET
    
    @Consumes({ "application/x-www-form-urlencoded" })
    @Produces({ "text/plain" })
    @ApiOperation(value = "JWT Login", notes = "", response = File.class, tags={ "Meta" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "JWT Token", response = File.class),
        @ApiResponse(code = 403, message = "Wrong credentials", response = File.class) })
    public Response authenticateGet(@FormParam(value = "username")  String username,@FormParam(value = "password")  String password) {
        return Response.ok().entity("magic!").build();
    }
}

