package de.atiw.sportfest.backend.resource;


import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

@Path("/klassen")
public class KlasseResource {

	@GET
	@Path("/{kid}")
    public String getKlasse(String klassenID){
        return "Klasse";
    }
	
	@GET
	public String getKlasse(){
		return "Klasse2";
	}
	
	@PUT
	public void putKlasse(){
		
	}
	
}
