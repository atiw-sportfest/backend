package de.atiw.sportfest.backend.api;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;

import de.atiw.sportfest.backend.model.User;
import io.swagger.annotations.*;
import javax.validation.constraints.*;
import javax.ws.rs.*;

@Path("/user")

@Api(description = "the user API")



@Stateless
public class UserApi  {

    @PersistenceContext
    EntityManager em;

    @GET
    
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Nutzer auflisten", notes = "", response = User.class, responseContainer = "List", tags={ "Nutzer",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Users", response = User.class, responseContainer = "List") })
    public List<User> userGet() {
        return em.createNamedQuery("user.list", User.class).getResultList().stream().map(u -> u.password(null)).collect(Collectors.toList());
    }

    @POST
    
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "Nutzer anlegen", notes = "", response = User.class, tags={ "Nutzer",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "User", response = User.class) })
    public User userPost(User user) {
        return em.merge(user);
    }

    @DELETE
    @Path("/{uid}")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Nutzer löschen", notes = "", response = void.class, tags={ "Nutzer",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 204, message = "Nutzer gelöscht.", response = void.class) })
    public Response userUidDelete(@PathParam("uid") @ApiParam("User-ID") Long uid) {
        em.remove(userUidGet(uid));
        return Response.noContent().build();
    }

    @GET
    @Path("/{uid}")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Nutzer anzeigen", notes = "", response = User.class, tags={ "Nutzer",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "User", response = User.class) })
    public User userUidGet(@PathParam("uid") @ApiParam("User-ID") Long uid) {
        User u = em.find(User.class, uid);

        if(u == null)
            throw new NotFoundException();

        return u;
    }

    @POST
    @Path("/{uid}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "Nutzer ändern", notes = "", response = User.class, tags={ "Nutzer" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "User", response = User.class) })
    public User userUidPost(@PathParam("uid") @ApiParam("User-ID") Long uid,User user) {
        user.id(uid);
        return em.merge(user);
    }
}

