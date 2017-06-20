package de.atiw.sportfest.backend.resource;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

@Path("/ergebnis")
public class ErgebnisResource {

	@GET
	@Path("/{did}")
    public String getErgebnis(String did){
        return "GET Ergebnis";
    }
	
		
	@POST
	@Path("/{did}/{eid}")
	public String postErgenis(String did, String eid){
		return "PUT Ergebnis";
	}
	
	@PUT
	@Path("/{did}")
	 public String putErgebnis(String did){
        return "GET Ergebnis";
    }
	
	@DELETE
	@Path("/{did}")
	 public String deleteErgebnis(String eid){
        return "DELETE Ergebnis";
    }
}
