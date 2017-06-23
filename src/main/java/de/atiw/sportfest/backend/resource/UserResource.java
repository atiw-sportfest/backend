package de.atiw.sportfest.backend.resource;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import de.atiw.sportfest.backend.auth.Secured;

@Path("/user")
public class UserResource {

	
	@GET
    @Secured
	@Path("/privileges")
    public Jws<Claims> getPrivileges(@HeaderParam("Authorization") String token){
        return Jwts.parser().setSigningKey("secret".getBytes()).parseClaimsJws(token.substring("Bearer ".length()));
	}
	
	@GET
	@Path("/login")
	public String getLogin(){
		return "login";
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
