package de.atiw.sportfest.backend.resource;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

import java.security.Key;
import java.sql.Date;
import java.util.Calendar;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/authentication")
public class AuthenticationEndpoint {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response authenticateUser(@FormParam("username") String username,
            @FormParam("password") String password) {

        try {

            // Authenticate the user using the credentials provided
            authenticate(username, password);

            // Issue a token for the user
            String token = issueToken(username);

            // Return the token on the response
            return Response.ok(token).build();

        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    private void authenticate(String username, String password) throws Exception {
        // Authenticate against db

        int a=1;
        if(a==1){

        }
        else{
            // Throw an Exception if the credentials are invalid
            throw new Exception();
        }
    }

    private String issueToken(String username) {

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, 15);

        return Jwts.builder()
            .setAudience("You")
            .setSubject("Joe")
            .setIssuedAt(Calendar.getInstance().getTime())
            .setExpiration(cal.getTime())
            .signWith(SignatureAlgorithm.HS512, "secret".getBytes())
            .compact();
    }
}
