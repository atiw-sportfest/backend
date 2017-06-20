package de.atiw.sportfest.backend.resource;


import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/ergebnis")
public class ErgebnisResource {

	@GET
	@Path("/{did}")
    public String getErgebnis(@PathParam("did") String did){
        return "Die gewählte Disziplin ist: "+did;
    }
	
		
	@POST
	@Path("/{did}/{eid}")
	public String postErgenis(@PathParam("did") String did, @PathParam("eid") String eid){
		return "PUT "+did+", "+eid;
	}
	
	@PUT
	@Path("/{did}")
	 public String putErgebnis(@PathParam("did") String did){
		return "PUT  "+ did;
    }
	
	@DELETE
	@Path("/{did}")
	 public String deleteErgebnis(@PathParam("eid") String eid){
		return "DELETE "+ eid;
    }
}
