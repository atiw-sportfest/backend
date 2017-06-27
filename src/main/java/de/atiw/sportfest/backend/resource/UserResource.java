package de.atiw.sportfest.backend.resource;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import javax.xml.bind.annotation.XmlRootElement;

import de.atiw.sportfest.backend.ExceptionResponse;
import de.atiw.sportfest.backend.auth.Role;
import de.atiw.sportfest.backend.auth.Secured;
import de.atiw.sportfest.backend.auth.TokenParser;
import de.atiw.sportfest.backend.resource.jaxb.Klasse;
import de.atiw.sportfest.backend.resource.jaxb.User;

@Path("/user")
public class UserResource {


	@Resource(name = "jdbc/sportfest")
	DataSource db;

	private static Logger logger = Logger.getAnonymousLogger();


	@GET
	@Secured
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Path("/privileges")
	public Response getPrivileges(@HeaderParam("Authorization") String token) {
		try {
			return Response.ok(new TokenParser(token.substring("Bearer ".length())).verify().getClaims()).build();
		} catch (Exception e) {
			// TokenParser throws a Exception if token is invalid,
			// but a invalid token would never reach this method,
			// the AuthenticationFilter would abort any request with an invalid
			// token.
			// That's why Internal Server Error and not Forbidden.
			return ExceptionResponse.internalServerError(e);
		}
	}

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/login")
	public Response login(@FormParam("username") String username, @FormParam("password") String password) {
		return Response.ok("login").build();
	}

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/password")
	@Secured({Role.admin,Role.schiedsrichter})
	public String changepw(@HeaderParam("Authorization") String token, @FormParam("currpw") String currentpw, @FormParam("newpw") String password) {
		
		Connection conn = null;
		PreparedStatement ps;
		ResultSet rs;
		try {
			conn = db.getConnection();
		} catch (SQLException e1) {
			
			e1.printStackTrace();
			return "db";
		}
		
		String username = "";
		try {
			username = new TokenParser(token.substring("Bearer ".length())).verify().getClaims().getAudience();
		} catch (Exception e1) {
			e1.printStackTrace();
			return "tp";
			//return Response.status(Response.Status.CONFLICT).build();
		}
		
		AuthenticationEndpoint auth = new AuthenticationEndpoint();
		
		// If soll abfragen, ob currentpw stimmt. TODO
		try {
			if(true){ //auth.authenticate(username, currentpw)!=Role.gast
				
				try {
					ps = conn.prepareStatement("Call BenutzerPasswortAendern(?,?);");
					ps.setString(1, username);
					ps.setString(2, password);
					rs = ps.executeQuery();
				} catch (SQLException e) {
					e.printStackTrace();
					return "db";
					//return Response.status(Response.Status.BAD_REQUEST).build();
				}
				
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			return e1.getMessage()+ "wat?!";
			//return Response.status(Response.Status.BAD_GATEWAY).build();
		}
		


		

		//return Response.ok("pw changed").build();
		return "gut";
		
		

	}
	

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser() {
		Response response = null;
		Connection connection = null;
    	try {
    		ArrayList<User> returner = new ArrayList<User>();
    		connection = db.getConnection();
			ResultSet rs = User.getRSgetAll(connection);
			while(rs.next()){
				returner.add(new User(rs.getString(1),rs.getString(2),rs.getInt(3)));
			}
			response = Response.ok(returner).build();
		} catch (Exception e) {
			response = ExceptionResponse.internalServerError(e);
		}finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
    	return response;

	}

	@POST
	@Secured({ Role.admin })
	public Response setUser(@FormParam("name") String username, @FormParam("role") String role) {
		Connection conn = null;
		PreparedStatement ps;
		ResultSet rs;
		int roleint = 3;
		switch (role) {
		case "admin":
			roleint = 1;
			break;
		case "schiedsrichter":
			roleint = 2;
			break;
		default:
			break;
		}
		try {
			conn = db.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		try {
			// Code geht nicht
			ps = conn.prepareStatement("Call BenutzerAnlegen(?,?,?);");
			ps.setString(1, username);
			ps.setString(2, "63f205e15d34aafe2b7a931bddfe467e"); // Atiw2017
			ps.setInt(3, roleint);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(Response.Status.CONFLICT).build();

		}

		return Response.ok("user created, pw:Atiw2017").build();
	}

	@DELETE
	@Secured({ Role.admin })
	@Path("/{uid}")
	public Response delUser(@PathParam("uid") String uid) {
		Connection conn = null;
		PreparedStatement ps;
		ResultSet rs;
		try {
			conn = db.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		try {
			ps = conn.prepareStatement("Call BenutzerLoeschen(?);");
			ps.setString(1, uid);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(Response.Status.CONFLICT).build();
		}

		return Response.ok("user deleted").build();
	}


}
