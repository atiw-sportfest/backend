package de.atiw.sportfest.backend.resource;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

@Path("/schueler")
public class SchuelerResource{
	@PUT
	public String putSchueler(){
		return "putSchueler";
	}
}
