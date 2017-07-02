package de.atiw.sportfest.backend.resource;


import java.io.File;
import java.io.InputStream;
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
import javax.ws.rs.core.Response.ResponseBuilder;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import de.atiw.sportfest.backend.ExceptionResponse;
import de.atiw.sportfest.backend.auth.Role;
import de.atiw.sportfest.backend.auth.Secured;
import de.atiw.sportfest.backend.resource.jaxb.Klasse;
import de.atiw.sportfest.backend.resource.jaxb.Disziplin;
import de.atiw.sportfest.backend.resource.jaxb.Klasse;
import de.atiw.sportfest.backend.resource.jaxb.Schueler;
import excel.exports.DBToExcelDisziplin;
import excel.exports.DBToExcelExporter;
import excel.exports.DBToExcelSchueler;
import excel.imports.ExcelToDBImporter;
import excel.imports.ExcelToDBTeilnahme;

@Path("/klasse")
public class KlasseResource {

	@Resource(name="jdbc/sportfest")
    DataSource db;
	
	@GET
	@Path("/{kid}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Klasse getKlasse(@PathParam("kid") String kid) throws SQLException {
        return Klasse.getOne(db.getConnection(), kid);
    }
	
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<Klasse> getKlassen() throws SQLException {
        return Klasse.getAll(db.getConnection());
	}
	
	@PUT
    @Secured({ Role.admin })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response putKlasse(Klasse klasse){
		Response response = null;
		Connection connection = null;
    	try {
    		connection = db.getConnection();
			Klasse.getRSput(connection, klasse);
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
    
	@GET
    @Path("/{kid}/anmeldung")
	@Produces("application/vnd.ms-excel")
	public Response getAnmeldebogen(@PathParam("kid") String kid){
		Response response = null;
		Connection connection = null;
    	try {
    		connection = db.getConnection();
    		
    		
    		ArrayList<DBToExcelSchueler> schueler = new ArrayList<>();
			ResultSet rs = Schueler.getRSgetAllOfKlasse(connection, kid);
			while(rs.next()){
				schueler.add(new DBToExcelSchueler(rs.getString(2), rs.getString(3), rs.getInt(1)));
			}
			rs = Disziplin.getRSgetAll(connection);
			
			
    		ArrayList<DBToExcelDisziplin> disziplinen = new ArrayList<>();
			while(rs.next()){
				disziplinen.add(new DBToExcelDisziplin(rs.getString(2), rs.getInt(1), rs.getBoolean(7), rs.getInt(4), rs.getInt(5)));
			}
			

    		String klasse = "";
			rs = Klasse.getRSgetOne(connection, kid);
			if(rs.next()){
				klasse = rs.getString(2);
			}
						
			String fileName = klasse+".xlsx";
			String path = System.getProperty("user.dir")+"/"+fileName;
			
			DBToExcelExporter.export(path, klasse, schueler, disziplinen);
			
			File file = new File(path);
			ResponseBuilder responseBuilder = Response.ok((Object) file).header("Content-Disposition", "attachment; filename="+fileName);
			
			response = responseBuilder.build();
		} catch (Exception e) {
			e.printStackTrace();
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
    @Path("/anmeldung")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response putAnmeldebogen(
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail) {
		Response response = null;
		Connection connection = null;
    	try {
    		connection = db.getConnection();
    		
    		List<ExcelToDBTeilnahme> teilnahmen = ExcelToDBImporter.importTeilnahmen(uploadedInputStream);			
    		
    		for(ExcelToDBTeilnahme teilnahme : teilnahmen){
    			Klasse.getRSputAnmeldung(connection, teilnahme.getSchuelerID(), teilnahme.getDisziplinID());
    		}
    		
			response = Response.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
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
	
    @DELETE
    @Path("/{kid}")
    @Secured({ Role.admin })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response deleteKlasse(@PathParam("kid") String kid){
		Response response = null;
		Connection connection = null;
    	try {
    		connection = db.getConnection();
			Klasse.getRSdelete(connection, kid);
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
