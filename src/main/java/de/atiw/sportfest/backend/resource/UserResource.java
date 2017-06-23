package de.atiw.sportfest.backend.resource;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import de.atiw.sportfest.backend.auth.Role;
import de.atiw.sportfest.backend.auth.Secured;

@Path("/user")
public class UserResource {

	
	@GET
	@Path("/privileges")
	public String getPrivileges(){
		return "priv";
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
	@Secured(Role.admin)
	public String setUser(){
		return "user set";
	}
	
	@DELETE
	@Secured({Role.admin})
	@Path("/{uid}")
	public String delUser(){
		return "tot";
	}
	
	
	
}
