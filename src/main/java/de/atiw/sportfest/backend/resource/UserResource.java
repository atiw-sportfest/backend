package de.atiw.sportfest.backend.resource;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

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
	public String setUser(){
		return "user set";
	}
	
	@DELETE
	@Path("/{uid}")
	public String delUser(){
		return "tot";
	}
	
	
	
}
