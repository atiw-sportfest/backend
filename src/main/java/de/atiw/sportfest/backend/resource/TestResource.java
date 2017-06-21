package de.atiw.sportfest.backend.resource;

import javax.annotation.Resource;
import javax.sql.DataSource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/test")
public class TestResource {

    @Resource(name="jdbc/sportfest")
    DataSource db;

    @GET
    @Path("cdi")
    public String getStatus(){
       return db != null ? "CDI works!" : "CDI is broken!";
    }

    @GET
    public String getPlainText(){
        return "Hello world!";
    }

}

