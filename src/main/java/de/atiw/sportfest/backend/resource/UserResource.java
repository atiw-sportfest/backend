package de.atiw.sportfest.backend.resource;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.atiw.sportfest.backend.auth.Secured;

@Path("/user")
public class UserResource {

	
	@GET
    @Secured
	@Path("/privileges")
    public Jws<Claims> getPrivileges(@HeaderParam("Authorization") String token){
        return Jwts.parser().setSigningKey("secret".getBytes()).parseClaimsJws(token.substring("Bearer ".length()));
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
	public String setUser(){
		return "user set";
	}
	
	@DELETE
	@Path("/{uid}")
	public String delUser(){
		return "tot";
	}
	
}
