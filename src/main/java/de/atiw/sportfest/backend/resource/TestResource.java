package de.atiw.sportfest.backend.resource;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.commons.compiler.CompileException;

import de.atiw.sportfest.backend.ExceptionResponse;
import de.atiw.sportfest.backend.auth.Secured;
import de.atiw.sportfest.backend.resource.jaxb.Disziplin;
import de.atiw.sportfest.backend.resource.jaxb.Leistung;
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
    public List<Integer> testRules() throws SQLException, CompileException, InvocationTargetException {

        Disziplin d = Disziplin.getOne(db.getConnection(), "1000");

        ArrayList<Integer> pts = new ArrayList<>();
        ArrayList<Variable> vars = new ArrayList<>();
        ArrayList<Object> vals = new ArrayList<>();

        vars.add(Variable.Geschlecht);
        vals.add("m");

        vars.addAll(d.getVariablen());
        vals.addAll(Arrays.asList(new Object[]{ 1.2f }));

        pts.add(d.getPoints(vars, vals));
        pts.add(d.getPoints(vars, vals));
        pts.add(d.getPoints(vars, vals));
        pts.add(d.getPoints(vars, vals));

        return pts;

    }

    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Path("disziplin")
    public Response testDisziplin(){

        try {

            return Response.ok(Disziplin.getOne(db.getConnection(), "1000")).build();

        } catch(Exception e){

            return ExceptionResponse.internalServerError(e);

        }
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

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("leistung")
    public Leistung testLeistungUpload(Leistung leistung) {
        return leistung;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("reflection")
    public Response testReflection(TestType test) throws Exception {

        if(test.convert){

            Class<?> type = Class.forName(test.className);
            Method method = type.getDeclaredMethod(test.convertMethod, String.class);

            return Response.ok(method.invoke(null, test.string)).build();
        } else {
            return Response.ok(test.string).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("test2")
    public Response test2(ValueTest test) throws Exception {
        return Response.ok(test.aTest).build();
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

    @XmlTransient
    String aTest;

    @XmlElement
    private void setTest(Integer test){
        this.aTest = Integer.toString(test);
    }

}

@XmlRootElement
class TestType{

    @XmlElement
    String className;

    @XmlElement
    String convertMethod;

    @XmlElement
    String string;

    @XmlElement
    Boolean convert;

}

