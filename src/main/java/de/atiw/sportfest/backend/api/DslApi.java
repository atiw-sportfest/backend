package de.atiw.sportfest.backend.api;

import de.atiw.sportfest.backend.model.Script;
import de.atiw.sportfest.backend.model.ValidationResult;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;

import java.util.List;
import javax.validation.constraints.*;

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
    public Response dslCheckRegelPost(Script script) {
        return Response.ok().entity("magic!").build();
    }
}

