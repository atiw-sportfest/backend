package de.atiw.sportfest.backend.resource;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/disziplin")
public class DisziplinResource {

	ArrayList<Disziplin> dis = new ArrayList<Disziplin>(); 
	
	
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public ArrayList<Disziplin> getAlleDiziplinen(){
          	Disziplin weit = new Disziplin();
          	weit.setName("Weitsprung");
          	weit.setBeschreibung("Springe so weit, wie du kannst!");
          	weit.setMinTeilnehmer(1);
          	weit.setMaxTeilnehmer(1);
          	weit.setTeamleistung(false);
          	Disziplin hoch = new Disziplin();
          	hoch.setName("Hochsprung");
          	hoch.setBeschreibung("Springe so hoch, wie du kannst!");
          	hoch.setMinTeilnehmer(1);
          	hoch.setMaxTeilnehmer(1);
          	hoch.setTeamleistung(false);
          	
          	dis.add(weit);
          	dis.add(hoch);
          	
          	return dis;
    }
    
    @GET
    @Path("/{did}")
    public String getDiziplin(@PathParam("did") String did){
          	
    	return did;
    }
    
    
    @POST
    @Path("/{did}")
    public void postDiziplin(@PathParam("did") String did){
    	//Disziplin.setName(did);
    }
}
