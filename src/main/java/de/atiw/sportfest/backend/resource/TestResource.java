package de.atiw.sportfest.backend.resource;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.atiw.sportfest.backend.ExceptionResponse;
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

        try {

            return Response.ok(makeTestDisziplin()).build();

        } catch(Exception e){

            return ExceptionResponse.internalServerError(e);

        }
    }

    private Disziplin makeTestDisziplin() throws SQLException, Disziplin.NotFoundException {

        Zustand m = new Zustand("Männlich", "", "m"),
                w = new Zustand("Weiblich", "", "w");

        Typ geschlechtT = new Typ("Geschlecht", "", String.class, Arrays.asList(new Zustand[]{m, w})),
            integerT = new Typ(int.class),
            floatT = new Typ(float.class);

        Variable geschlecht = new Variable("Geschlecht", "", "geschlecht", geschlechtT),
                 counter = new Variable("Counter", "", "counter", integerT),
                 weite = new Variable("Weite", "", "weite", floatT);

        Disziplin d = Disziplin.getOne(db.getConnection(), "1000");

        d.setVariablen(Arrays.asList(new Variable[]{ geschlecht, weite }));

        return d;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("value")
    public Response testValues(){
        return Response.ok(new ValueTest()).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("value")
    public Response testValues(ValueTest v){
        return Response.ok(v).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("variable/{vid:\\d+}")
    public Variable testVariableVid(@PathParam("vid") String vid) throws NotFoundException, InternalServerErrorException {
        try {
            return Variable.getOne(db.getConnection(), vid, true);
        } catch (Exception e){
            throw new InternalServerErrorException(e);
        }
    }
}

@XmlRootElement
class ValueTest {

    @XmlElement
    Integer i;
    @XmlElement
    Boolean b;
    @XmlElement
    Float f;

}

