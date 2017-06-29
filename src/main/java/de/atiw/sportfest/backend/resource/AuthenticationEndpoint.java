package de.atiw.sportfest.backend.resource;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

import java.security.Key;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import javax.annotation.Resource;
import javax.sql.DataSource;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.Consumes;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.atiw.sportfest.backend.auth.Role;

@Path("/authentication")
public class AuthenticationEndpoint {

	@Resource(name = "jdbc/sportfest")
	DataSource db;

    @Context
    HttpServletRequest hsr;

	@POST
    @Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response authenticateUser(@FormParam("username") String username, @FormParam("password") String password) throws ForbiddenException, SQLException {

        // Authenticate the user using the credentials provided
        Role priv = authenticate(username, password);

        if(priv == null)
            throw new ForbiddenException(String.format("Wrong password for %s!", username));

        // Issue a token for the user
        String token = issueToken(username, priv);

        // Return the token on the response;
        return Response.ok(token).build();

	}

	public Role authenticate(String username, String password) throws SQLException {

		Connection conn = null;
        PreparedStatement ps;
        ResultSet rs;
		Role priv = null;

		try {

			conn = db.getConnection();

			ps = conn.prepareStatement("Call BerechtigungAnzeigen(?,?);");

			ps.setString(1, username);
			ps.setString(2, password);

			rs = ps.executeQuery();

			if (rs.next()) {

				switch (rs.getInt(1)) {
				case 1:
					priv = Role.admin;
					break;
				case 2:
					priv = Role.schiedsrichter;
					break;
				default:
					priv = Role.gast;
					break;

				}
			}

            return priv;

		} finally{ if(conn != null) conn.close(); } 

	}

	private String issueToken(String username, Role priv) {

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, 10);

		return Jwts.builder()
				.setAudience(hsr.getRemoteAddr())
                .claim("username", username)
				.claim("role", priv)
				.setIssuedAt(Calendar.getInstance().getTime())
				.setExpiration(cal.getTime())
				.signWith(SignatureAlgorithm.HS512, "secret".getBytes()).compact();
	}

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/gast")
    public String guestLogin(){
        return issueToken(null, null);
    }


}
