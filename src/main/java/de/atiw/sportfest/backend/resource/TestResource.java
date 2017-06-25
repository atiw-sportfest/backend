package de.atiw.sportfest.backend.resource;

import java.util.Arrays;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.sql.DataSource;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.atiw.sportfest.backend.auth.Secured;
import de.atiw.sportfest.backend.resource.jaxb.Disziplin;
import de.atiw.sportfest.backend.rules.EvaluationParameters;
import de.atiw.sportfest.backend.rules.Regel;
import de.atiw.sportfest.backend.rules.Typ;
import de.atiw.sportfest.backend.rules.Variable;
import de.atiw.sportfest.backend.rules.Zustand;

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
    public String getPlainText(){
        return "Hello world!";
    }

    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Path("rules")
    public Response testRules(){

        try {

            Disziplin d = makeTestDisziplin();

            return Response.ok(d.getErsteRegel().evaluate(d.getVariablen().toArray(new Variable[0]), new Object[]{ "m", 1.2f })).build();

        } catch(Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }

    }

    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Path("disziplin")
    public Response testDisziplin(){
        return Response.ok(makeTestDisziplin()).build();
    }

    private Disziplin makeTestDisziplin(){

        // m
        // >=2.4 -> 10
        // >=1.2 ->  5
        // >=0.6 ->  2
        // >=0.3 ->  1
        //
        // w
        // >=1.2 -> 10
        // >=0.6 ->  5
        // >=0.3 ->  2
        // >=0.15->  1

        Regel regel8 = new Regel("geschlecht == \"w\" && weite >= 0.15", 1),
              regel7 = new Regel("geschlecht == \"w\" && weite >= 0.3", 2, regel8),
              regel6 = new Regel("geschlecht == \"w\" && weite >= 0.6", 5, regel7),
              regel5 = new Regel("geschlecht == \"w\" && weite >= 1.2", 10, regel6),
              regel4 = new Regel("geschlecht == \"m\" && weite >= 0.3", 1, regel5),
              regel3 = new Regel("geschlecht == \"m\" && weite >= 0.6", 2, regel4),
              regel2 = new Regel("geschlecht == \"m\" && weite >= 1.2", 5, regel3),
              regel1 = new Regel("geschlecht == \"m\" && weite >= 2.4", 10, regel2);

        Zustand m = new Zustand("Männlich", "", "m"),
                w = new Zustand("Weiblich", "", "w");

        Typ geschlechtT = new Typ("Geschlecht", "", String.class, Arrays.asList(new Zustand[]{m, w})),
            integerT = new Typ(int.class),
            floatT = new Typ(float.class);

        Variable geschlecht = new Variable("Geschlecht", "", "geschlecht", geschlechtT),
                 counter = new Variable("Counter", "", "counter", integerT),
                 weite = new Variable("Weite", "", "weite", floatT);

        Disziplin d = new Disziplin();

        d.setErsteRegel(regel1);
        d.setVariablen(Arrays.asList(new Variable[]{ geschlecht, weite }));

        return d;
    }

}

