package de.atiw.sportfest.backend.api;

import de.atiw.sportfest.backend.model.User;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;

import java.util.List;
import javax.validation.constraints.*;

@Path("/user")

@Api(description = "the user API")




public class UserApi  {

    @GET
    
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Nutzer auflisten", notes = "", response = User.class, responseContainer = "List", tags={ "Nutzer",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Users", response = User.class, responseContainer = "List") })
    public Response userGet() {
        return Response.ok().entity("magic!").build();
    }

    @POST
    
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "Nutzer anlegen", notes = "", response = User.class, tags={ "Nutzer",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "User", response = User.class) })
    public Response userPost(User user) {
        return Response.ok().entity("magic!").build();
    }

    @DELETE
    @Path("/{uid}")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Nutzer löschen", notes = "", response = void.class, tags={ "Nutzer",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 204, message = "Nutzer gelöscht.", response = void.class) })
    public Response userUidDelete(@PathParam("uid") @ApiParam("User-ID") Long uid) {
        return Response.ok().entity("magic!").build();
    }

    @GET
    @Path("/{uid}")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Nutzer anzeigen", notes = "", response = User.class, tags={ "Nutzer",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "User", response = User.class) })
    public Response userUidGet(@PathParam("uid") @ApiParam("User-ID") Long uid) {
        return Response.ok().entity("magic!").build();
    }

    @POST
    @Path("/{uid}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "Nutzer ändern", notes = "", response = User.class, tags={ "Nutzer" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "User", response = User.class) })
    public Response userUidPost(@PathParam("uid") @ApiParam("User-ID") Long uid,User user) {
        return Response.ok().entity("magic!").build();
    }
}

