package de.atiw.sportfest.backend.resource;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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

import de.atiw.sportfest.backend.resource.jaxb.Disziplin;

@Path("/disziplin")
public class DisziplinResource {

	ArrayList<Disziplin> dis = new ArrayList<Disziplin>(); 
	
	@Resource(name="jdbc/sportfest")
    DataSource db;
	
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public ArrayList<Disziplin> getAlleDiziplinen(){
        ArrayList<Disziplin> returner = new ArrayList<Disziplin>();
    	try {		
    		Connection connection = db.getConnection();
			ResultSet rs = Disziplin.getRSgetAll(connection);
			while(rs.next()){
				returner.add(new Disziplin(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getBoolean(6), rs.getBoolean(7)));
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
      	return returner;
    }
    
    @GET
    @Path("/{did}")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Disziplin getDiziplin(@PathParam("did") String did){
	    try{
	    	Connection connection = db.getConnection();
			ResultSet rs = Disziplin.getRSgetOne(connection, did);
			connection.close();
		if(rs.next())
			return new Disziplin(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getBoolean(6), rs.getBoolean(7));
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
    
    
    @PUT
	@Path("/{did}")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public boolean putDisziplin(Disziplin disziplin){
    	try {
    		Connection connection = db.getConnection();
			Disziplin.getRSput(connection, disziplin);
			connection.close();;
			return true;
    	} catch (SQLException e) {
			return false;
		}
    }
}
