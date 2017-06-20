package de.atiw.sportfest.backend.resource;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/disziplin")
public class DisziplinResource {

    @GET
    public String getPlainText(){
        return "Hello world!";
    }
    
    @POST
    @Path("/{did}")
    public String post(String did){
    	return "POST";
    }
}
