package de.atiw.sportfest.backend.api;

import groovy.lang.GroovyShell;

import java.util.List;

import javax.ws.rs.core.Response;

import de.atiw.sportfest.backend.model.ValidationResult;
import io.swagger.annotations.*;
import javax.validation.constraints.*;
import javax.ws.rs.*;

@Path("/dsl")

@Api(description = "the dsl API")




public class DslApi  {

    @POST
    @Path("/check/regel")
    @Consumes({ "text/plain" })
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "Syntax-Prüfung der Regel-DSL", response = ValidationResult.class, tags={ "Meta" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Ergebniss der Syntaxprüfung", response = ValidationResult.class) })
    public ValidationResult dslCheckRegelPost(String script) {

        try {
            new GroovyShell().parse(script);
            return new ValidationResult().pass(true);
        } catch (Exception e){
            return new ValidationResult().pass(false).messages(e.getMessage());
        }
    }
}

