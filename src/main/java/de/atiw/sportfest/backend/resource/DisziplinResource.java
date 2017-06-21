package de.atiw.sportfest.backend.resource;

import java.sql.Connection;

import javax.inject.Inject;
import javax.sql.DataSource;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/disziplin")
public class DisziplinResource {
	
	/*@Inject
	DataSource dataSource;*/

    @GET
    public String getPlainText(){
    	
    //	Connection connection = dataSource.getConnection();
    	
        return "Hello world!";
    }
    
    @POST
    @Path("/{did}")
    public String post(String did){
    	return "POST";
    }
}
