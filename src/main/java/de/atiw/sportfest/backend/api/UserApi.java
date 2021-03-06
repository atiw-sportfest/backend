package de.atiw.sportfest.backend.api;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;

import de.atiw.sportfest.backend.model.NewPassword;
import de.atiw.sportfest.backend.model.User;
import io.swagger.annotations.*;
import javax.validation.constraints.*;
import javax.ws.rs.*;

@Path("/user")

@Api(description = "the user API")



@Stateless
@Lock(LockType.READ)
public class UserApi  {

    @PersistenceContext
    EntityManager em;

    @GET
    
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Nutzer auflisten", notes = "", response = User.class, responseContainer = "List", tags={ "Nutzer",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Users", response = User.class, responseContainer = "List") })
    public List<User> userGet() {
        return em.createNamedQuery("user.list", User.class).getResultList().stream().map(u -> { em.detach(u); return u.password(null); }).collect(Collectors.toList());
    }

    @POST
    @Path("/password")
    @Consumes({ "application/json" })
    
    @ApiOperation(value = "Passwort ändern", notes = "", response = void.class, tags={ "Nutzer",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 204, message = "Passwort erfolgreich geändert.", response = void.class),
        @ApiResponse(code = 403, message = "Altes Passwort falsch.", response = void.class) })
    public Response userPasswordPost(NewPassword newPassword) {
        return Response.ok().entity("magic!").build();
    }

    @POST
    
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "Nutzer anlegen", notes = "", response = User.class, tags={ "Nutzer",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "User", response = User.class) })
    public User userPost(User user) {
        em.merge(user);
        em.detach(user);
        return user.password(null);
    }

    @DELETE
    @Path("/{uid}")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Nutzer löschen", notes = "", response = void.class, tags={ "Nutzer",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 204, message = "Nutzer gelöscht.", response = void.class) })
    public Response userUidDelete(@PathParam("uid") @ApiParam("User-ID") Long uid) {
        em.remove(em.find(User.class, uid));
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

        em.detach(u);

        return u.password(null);
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
        em.merge(user);
        em.detach(user);
        return user.password(null);
    }
}

