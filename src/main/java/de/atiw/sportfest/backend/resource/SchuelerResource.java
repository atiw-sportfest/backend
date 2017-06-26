package de.atiw.sportfest.backend.resource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import de.atiw.sportfest.backend.ExceptionResponse;
import de.atiw.sportfest.backend.auth.Role;
import de.atiw.sportfest.backend.auth.Secured;
import de.atiw.sportfest.backend.resource.jaxb.Klasse;
import de.atiw.sportfest.backend.resource.jaxb.Schueler;

@Path("/schueler")
public class SchuelerResource{

	@Resource(name="jdbc/sportfest")
    DataSource db;
	
	@GET
	@Path("/{sid}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response getSchueler(@PathParam("sid") String sid){
		Response response = null;
		Connection connection = null;
		try{
    		connection = db.getConnection();
			ResultSet rs = Schueler.getRSgetOne(connection, sid);
			if(rs.next()){
				response = Response.ok(new Schueler(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5))).build();
			}else{ 
				response = Response.status(Response.Status.NOT_FOUND).build();
			}
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
	
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getSchueler(){
		Response response = null;
		Connection connection = null;
    	try {
    		ArrayList<Schueler> returner = new ArrayList<Schueler>();
    		connection = db.getConnection();
			ResultSet rs = Schueler.getRSgetAll(connection);
			while(rs.next()){
				returner.add(new Schueler(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5)));
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
	
	@GET
    @Path("/klasse/{kid}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getSchuelerEinerKlasse(@PathParam("kid") String kid){
		Response response = null;
		Connection connection = null;
    	try {
    		ArrayList<Schueler> returner = new ArrayList<Schueler>();
    		connection = db.getConnection();
			ResultSet rs = Schueler.getRSgetAllOfKlasse(connection, kid);
			while(rs.next()){
				returner.add(new Schueler(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5)));
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
	
	@GET
    @Path("/klasse/{kid}/disziplin/{did}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getSchuelerEinerKlasseUndDisziplin(@PathParam("kid") String kid, @PathParam("did") String did){
		Response response = null;
		Connection connection = null;
    	try {
    		ArrayList<Schueler> returner = new ArrayList<Schueler>();
    		connection = db.getConnection();
			ResultSet rs = Schueler.getRSgetAllOfKlasseAndDisziplin(connection, kid, did);
			while(rs.next()){
				returner.add(new Schueler(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5)));
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
	
	@PUT
    @Secured({ Role.admin })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response putSchueler(Schueler schueler){
		Response response = null;
		Connection connection = null;
    	try {
    		connection = db.getConnection();
    		Schueler.getRSput(connection, schueler);
			response = Response.ok().build();
    	} catch (SQLException e) {
    		response = ExceptionResponse.internalServerError(e);
		}finally {
			try {
				connection.close();
			} catch (SQLException e) {
	    		response = ExceptionResponse.internalServerError(e);
			}
		}
    	return response;
    }
    
	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail) {

		Connection connection = null;
		Response response = null;
		
		String output = "";
		
		String line;
		BufferedReader br = new BufferedReader(new InputStreamReader(uploadedInputStream));

		try {

			connection = db.getConnection();
			List<Klasse> klassen = new ArrayList<>();

			while((line = br.readLine()) != null){

				String[] split = line.split(",");
                String klassenName = split[2];

				Schueler neuerSchueler = new Schueler();

				neuerSchueler.setSid(-1);
				neuerSchueler.setName(split[0]);
				neuerSchueler.setVorname(split[1]);				
				neuerSchueler.setGid((split[3].equals("m")?1:2));
				
				Klasse neueKlasse = null;

				for(Klasse klasse : klassen){
					if(klasse.getName().equals(klassenName)){
                        neueKlasse = klasse;
					}
				}

				if(neueKlasse == null){

                    neueKlasse = new Klasse(klassenName);

					output += "PUT KLASSE: " + klassenName + "<br>";
					Klasse.getRSput(connection, neueKlasse);

					klassen.add(neueKlasse);
				}

				neuerSchueler.setKid(neueKlasse.getKid());

				output += "PUT SCHUELER: "+neuerSchueler.getVorname()+" "+neuerSchueler.getName()+"<br>";
				Schueler.getRSput(connection, neuerSchueler);
			}
			response = Response.ok(output).build();
		} catch (Exception e) {
    		response = ExceptionResponse.internalServerError(e);
		}finally {
			try {
				br.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return response;

	}
	
    @DELETE
    @Path("/{sid}")
    @Secured({ Role.admin })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response deleteSchueler(@PathParam("sid") String sid){
		Response response = null;
		Connection connection = null;
    	try {
    		connection = db.getConnection();
    		Schueler.getRSdelete(connection, sid);
			response = Response.ok().build();
    	} catch (SQLException e) {
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
}
