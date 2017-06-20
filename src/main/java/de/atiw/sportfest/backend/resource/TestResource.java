package de.atiw.sportfest.backend.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/test")
public class TestResource {

    @GET
    public String getPlainText(){
        return "Hello world!";
    }

}

