package de.atiw.sportfest.backend.auth;

import java.security.Principal;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;

import javax.inject.Inject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

@Path("/secure")
@Secured
public class SecureResource {

    @GET
    @Secured({ Role.admin })
    @Path("admin")
    public String admin(){
        return "You got Role.admin!";
    }

    @GET
    @Secured({ Role.schiedsrichter })
    @Path("schiedsrichter")
    public String schiedsrichter(){
        return "You got Role.schiedsrichter!";
    }

    @GET
    @Secured({ Role.gast })
    @Path("gast")
    public String gast(){
        return "You got Role.gast!";
    }
}
