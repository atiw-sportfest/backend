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
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.atiw.sportfest.backend.auth.Role;

@Path("/authentication")
public class AuthenticationEndpoint {

	@Resource(name = "jdbc/sportfest")
	DataSource db;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response authenticateUser(@FormParam("username") String username, @FormParam("password") String password) {

		try {

			// Authenticate the user using the credentials provided
			Role priv = authenticate(username, password);

			// Issue a token for the user
			String token = issueToken(username, priv);

			// Return the token on the response;
			return Response.ok(token).build();

		} catch (Exception e) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}

	public Role authenticate(String username, String password) throws Exception {
		Connection conn = null;
		Role priv = Role.gast;

		try {
			conn = db.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {

			PreparedStatement ps = conn.prepareStatement("Call gibBerechtigung(?,?);");
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {

				int role = rs.getInt(1);
				switch (role) {
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
		} catch (SQLException sqle) {

		}

		return priv;

	}

	private String issueToken(String username, Role priv) {

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR,10);

		return Jwts.builder()
				.setAudience(username)
				.claim("role", priv != null ? priv : Role.gast)
				.setSubject("Joe")
				.setIssuedAt(Calendar.getInstance().getTime())
				.setExpiration(cal.getTime())
				.signWith(SignatureAlgorithm.HS512, "secret".getBytes()).compact();
	}
}
