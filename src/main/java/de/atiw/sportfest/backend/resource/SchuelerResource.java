package de.atiw.sportfest.backend.resource;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

import de.atiw.sportfest.backend.auth.Role;
import de.atiw.sportfest.backend.auth.Secured;

@Path("/schueler")
public class SchuelerResource{
	@PUT
    @Secured({ Role.admin })
	public String putSchueler(){
		return "putSchueler";
	}
}
