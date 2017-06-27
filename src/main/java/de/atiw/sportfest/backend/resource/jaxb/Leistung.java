package de.atiw.sportfest.backend.resource.jaxb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import de.atiw.sportfest.backend.resource.jaxb.Disziplin.NotFoundException;

@XmlRootElement
public class Leistung {

    @XmlElement
	private Integer lid;
    @XmlElement
	private Integer did;
    @XmlElement
	private Integer eid;
    @XmlElement
	private Integer kid;
    @XmlElement
	private Integer sid;
    @XmlElement
	private Date timestamp;
    
    private static String pattern = "yyyy-MM-dd hh:mm:ss";
	
	public Leistung() {}
	
    public Leistung(Integer lid, Integer did, Integer eid, Integer kid, Integer sid, Date timestamp){
    	this.lid = lid;
    	this.did = did;
    	this.eid = eid;
    	this.kid = kid;
    	this.sid = sid;
    	this.timestamp = timestamp;
    }
    
	public static ResultSet getRSgetAll(Connection conn) throws SQLException{		
		return conn.prepareStatement("Call LeistungenAnzeigen();").executeQuery();
		
	}
	
	public static ResultSet getRSgetOne(Connection conn, String lid) throws SQLException{
		PreparedStatement ps = conn.prepareStatement("Call LeistungAnzeigen(?)");
		ps.setString(1, lid);
		return ps.executeQuery();
	}
	
	public static void putSchuelerleistung(Connection conn, Leistung leistung) throws SQLException{	
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern); 
		PreparedStatement ps = conn.prepareStatement("Call LeistungSchuelerAnlegen(?,?,?,?)");
		ps.setInt(1, leistung.did);
		ps.setInt(2, leistung.eid);
		ps.setInt(3, leistung.sid);
		ps.setString(4, simpleDateFormat.format(leistung.timestamp));
		ps.executeQuery();
	}
	
	public static void putKlassenleistung(Connection conn, Leistung leistung) throws SQLException{	
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern); 
		PreparedStatement ps = conn.prepareStatement("Call LeistungKlasseAnlegen(?,?,?,?)");
		ps.setInt(1, leistung.did);
		ps.setInt(2, leistung.eid);
		ps.setInt(3, leistung.kid);
		ps.setString(4, simpleDateFormat.format(leistung.timestamp));
		ps.executeQuery();
	}
	
	public static void post(Connection conn, Leistung leistung) throws SQLException {	
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern); 
		PreparedStatement ps = conn.prepareStatement("Call LeistungAendern(?,?,?,?,?,?)");
		ps.setInt(1, leistung.lid);
		ps.setInt(2, leistung.did);
		ps.setInt(3, leistung.eid);
		ps.setInt(4, leistung.kid);
		ps.setInt(5, leistung.sid);
		ps.setString(6, simpleDateFormat.format(leistung.timestamp));
		ps.executeQuery();
	}
	public static void delete(Connection conn, String lid) throws SQLException{	
		PreparedStatement ps = conn.prepareStatement("Call LeistungLoeschen(?)");
		ps.setString(1, lid);
		ps.execute();
    }	
	
	public static Leistung getOne(Connection conn, String lid, boolean close) throws SQLException, NotFoundException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		ResultSet rs = getRSgetOne(conn, lid);
        Leistung one = null;
		if(rs.next()){
			try {
				one = new Leistung(rs.getInt(1),rs.getInt(2),rs.getInt(3), rs.getInt(4), rs.getInt(5), simpleDateFormat.parse(rs.getString(6)) );
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
		if(close)
			conn.close();
        if(one != null)
            return one;
		
	    else
            throw new NotFoundException(String.format("Keine Leistung zu ID \"%s\" gefunden!", lid));
	}

	public static ArrayList<Leistung> getAll(Connection conn, boolean close) throws SQLException, NotFoundException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		ResultSet rs = getRSgetAll(conn);
        ArrayList<Leistung> all = new ArrayList<>();
		while(rs.next()){
			try {
				all.add(new Leistung(rs.getInt(1),rs.getInt(2),rs.getInt(3), rs.getInt(4), rs.getInt(5),simpleDateFormat.parse(rs.getString(6))));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		conn.close();
		return all;
	}    
}
