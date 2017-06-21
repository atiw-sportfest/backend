package de.atiw.sportfest.backend.resource;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import javax.annotation.Resource;
import javax.sql.DataSource;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.atiw.sportfest.backend.resource.Secured;

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
    @Secured
    @Path("auth")
    public String checkAuth(@HeaderParam("Authorization") String token){
        return "Seems legit.\nYour token: " + token.substring("Bearer ".length());
    }

    @GET
    @Secured
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Path("token")
    public Jws<Claims> checkToken(@HeaderParam("Authorization") String token){
        return Jwts.parser().setSigningKey("secret".getBytes()).parseClaimsJws(token.substring("Bearer ".length()));
    }

    @GET
    public String getPlainText(){
        return "Hello world!";
    }

}

