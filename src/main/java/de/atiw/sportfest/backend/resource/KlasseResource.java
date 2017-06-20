package de.atiw.sportfest.backend.resource;


import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

@Path("/klasse")
public class KlasseResource {

	@GET
	@Path("/{kid}")
    public String getKlasse(String kid){
        return "GET Klasse(kid)";
    }
	
	@GET
	public String getKlasse(){
		return "GET Klasse";
	}
	
	@PUT
	public String putKlasse(){
		return "PUT Klasse";
	}
	
}
