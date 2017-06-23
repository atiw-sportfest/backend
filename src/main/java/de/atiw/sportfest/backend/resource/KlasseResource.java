package de.atiw.sportfest.backend.resource;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.atiw.sportfest.backend.resource.jaxb.Klasse;

@Path("/klasse")
public class KlasseResource {

	@Resource(name="jdbc/sportfest")
    DataSource db;
	
	@GET
	@Path("/{kid}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Klasse getKlasse(@PathParam("kid") String kid){
		try{
			Statement stmt = db.getConnection().createStatement();
			String query = "Call KlasseAnzeigen("+ Integer.parseInt(kid)+");";
			ResultSet rs = stmt.executeQuery( query );
			if(rs.next())
				return new Klasse(rs.getInt(1), rs.getString(2));
			else 
				return null;
	    	}catch(SQLException e){
	    		//Log Datei
	    		return null;
	    	}
	    	catch (Exception e) {
	    		//Parse
	    		//Log Datei
				return null;
			}
			
    }
	
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public ArrayList<Klasse> getKlassen(){
		ArrayList<Klasse> returner = new ArrayList<Klasse>();
    	try {
    		Statement stmt = db.getConnection().createStatement();
			String query = "Call KlassenAnzeigen();  ";
			ResultSet rs = stmt.executeQuery( query );
			while(rs.next()){
				returner.add(new Klasse(rs.getInt(1), rs.getString(2)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
      	return returner;
	}
	
	@PUT
	@Path("/")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String putKlasse(){
		return "PUT Klasse";
	}
	
}
