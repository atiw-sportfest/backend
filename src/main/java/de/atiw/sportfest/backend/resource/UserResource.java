package de.atiw.sportfest.backend.resource;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.atiw.sportfest.backend.ExceptionResponse;
import de.atiw.sportfest.backend.auth.Role;
import de.atiw.sportfest.backend.auth.Secured;
import de.atiw.sportfest.backend.auth.TokenParser;

@Path("/user")
public class UserResource {

	
	@GET
    @Secured
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Path("/privileges")
    public Response getPrivileges(@HeaderParam("Authorization") String token){
        try {
            return Response.ok(new TokenParser(token.substring("Bearer ".length())).verify().getClaims()).build();
        } catch(Exception e){
            // TokenParser throws a Exception if token is invalid,
            // but a invalid token would never reach this method,
            // the AuthenticationFilter would abort any request with an invalid token.
            // That's why Internal Server Error and not Forbidden.
            return ExceptionResponse.internalServerError(e);
        }
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/login")
	public Response login(@FormParam("username") String username, @FormParam("password") String password) {
        return Response.ok("login").build();
	}
	
	@GET
	public String getUser(){
		return "user";
	}
	
	@POST
    @Secured({ Role.admin })
	public String setUser(){
		return "user set";
	}
	
	@DELETE
    @Secured({ Role.admin })
	@Path("/{uid}")
	public String delUser(){
		return "tot";
	}
	
}
