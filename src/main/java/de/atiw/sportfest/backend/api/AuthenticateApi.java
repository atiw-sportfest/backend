package de.atiw.sportfest.backend.api;

import de.atiw.sportfest.backend.model.Authentication;
import de.atiw.sportfest.backend.model.User;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;

import java.util.List;
import javax.validation.constraints.*;

@Path("/authenticate")

@Api(description = "the authenticate API")




public class AuthenticateApi  {

    @POST
    
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "JWT Login", notes = "", response = Authentication.class, tags={ "Meta" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Authentication", response = Authentication.class),
        @ApiResponse(code = 403, message = "Authentication", response = Authentication.class) })
    public Response authenticatePost(User user) {
        return Response.ok().entity("magic!").build();
    }
}

