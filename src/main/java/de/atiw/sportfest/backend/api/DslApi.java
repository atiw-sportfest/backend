package de.atiw.sportfest.backend.api;

import groovy.lang.GroovyShell;

import java.util.List;

import javax.ws.rs.core.Response;

import de.atiw.sportfest.backend.model.Script;
import de.atiw.sportfest.backend.model.ValidationResult;
import io.swagger.annotations.*;
import javax.validation.constraints.*;
import javax.ws.rs.*;

@Path("/dsl")

@Api(description = "the dsl API")




public class DslApi  {

    @POST
    @Path("/check/regel")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "Syntax-Prüfung der Regel-DSL", notes = "Muss aufgrund eines Bugs im TypeScript-Generator als Objekt versendet werden.", response = ValidationResult.class, tags={ "Meta" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Ergebniss der Syntaxprüfung", response = ValidationResult.class) })
    public ValidationResult dslCheckRegelPost(Script script) {

        try {
            new GroovyShell().parse(script.getScript());
            return new ValidationResult().pass(true);
        } catch (Exception e){
            return new ValidationResult().pass(false).messages(e.getMessage());
        }
    }
}

