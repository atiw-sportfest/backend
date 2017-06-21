package de.atiw.sportfest.backend.resource;


import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/klasse")
public class KlasseResource {

	@GET
	@Path("/{kid}")
    public String getKlasse(@PathParam("kid") String kid){
		return "Die Klassenbezeichnung ist: "+kid;
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
