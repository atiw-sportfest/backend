package de.atiw.sportfest.backend.resource;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/test")
public class TestResource {

    @GET
    public String getPlainText(){
        return "get worked";
    }
    
    @POST
    public String postTest(){
    	return "post worked";
    }
    
    @GET
    public String dummyData(){
    	return "dummy data";
    }
    
    
    @POST
    public String postDummy(){
    	return "dummy non persistant save";
    }
    

}

