package de.atiw.sportfest.backend.api;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Calendar;
import java.util.List;
import javax.annotation.Resource;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import de.atiw.sportfest.backend.auth.Secured;
import de.atiw.sportfest.backend.model.Authentication;
import de.atiw.sportfest.backend.model.Role;
import de.atiw.sportfest.backend.model.User;
import io.swagger.annotations.*;
import javax.validation.constraints.*;
import javax.ws.rs.*;

@Path("/authenticate")

@Api(description = "the authenticate API")




@Stateless
@Lock(LockType.READ)
public class AuthenticateApi  {

    @Resource(name="jwt_secret")
    String secret;

    @Context
    HttpServletRequest hsr;

    @PersistenceContext
    EntityManager em;

    @POST
    
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "JWT Login", notes = "", response = Authentication.class, tags={ "Meta" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Authentication", response = Authentication.class),
        @ApiResponse(code = 403, message = "Authentication", response = Authentication.class) })
    public Response authenticatePost(User user) {

        User checkUser;
        Authentication auth = new Authentication();

        if(user.getUsername() == null)
            throw new BadRequestException("Username cannot be null!");

        try {
            checkUser = em.createNamedQuery("user.findByUsername", User.class).setParameter("username", user.getUsername()).getSingleResult();
        } catch(NoResultException e){
            return Response.status(Response.Status.NOT_FOUND).entity(auth.success(false)).build();
        }

        if(checkUser.getPassword() != null && user.getPassword() != null && checkUser.getPassword().equals(user.getPassword()))
            return Response.ok().entity(auth
                    .success(true)
                    .token(issueToken(checkUser.getUsername(), checkUser.getRole()))).build();

        return Response.status(Response.Status.FORBIDDEN).entity(auth.success(false)).build();
    }

    @GET
    @Path("/test")
    @Secured
    public String test(){
        return "yes!";
    }

	private String issueToken(String username, Role priv) {

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, 10);

		return Jwts.builder()
				.setAudience(hsr.getRemoteAddr())
                .claim("username", username)
				.claim("role", priv)
				.setIssuedAt(Calendar.getInstance().getTime())
				.setExpiration(cal.getTime())
				.signWith(SignatureAlgorithm.HS512, secret.getBytes()).compact();
	}

}

