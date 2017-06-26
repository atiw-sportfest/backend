package de.atiw.sportfest.backend.resource.jaxb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.atiw.sportfest.backend.rules.Regel;
import de.atiw.sportfest.backend.rules.Variable;

public class Disziplin {

	private int did;
	private String name;
	private String beschreibung;
	private int minTeilnehmer;
	private int maxTeilnehmer;
	private boolean aktiviert;
	private boolean teamleistung;
    private Regel ersteRegel;
    private List<Variable> variablen;
	
	public Disziplin(){}
	
	public Disziplin(int did, String name, String beschreibung, int minTeilnehmer, int maxTeilnehmer, boolean aktiviert, boolean temleistung){
		this.did = did;
		this.name = name;
		this.beschreibung = beschreibung;
		this.minTeilnehmer = minTeilnehmer;
		this.maxTeilnehmer = maxTeilnehmer;
		this.aktiviert = aktiviert;
		this.teamleistung = temleistung;
	}
	

	public int getDid() {
		return did;
	}
	public void setDid(int did) {
		this.did = did;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBeschreibung() {
		return beschreibung;
	}
	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}
	public int getMinTeilnehmer() {
		return minTeilnehmer;
	}
	public void setMinTeilnehmer(int minTeilnehmer) {
		this.minTeilnehmer = minTeilnehmer;
	}
	public int getMaxTeilnehmer() {
		return maxTeilnehmer;
	}
	public void setMaxTeilnehmer(int maxTeilnehmer) {
		this.maxTeilnehmer = maxTeilnehmer;
	}
	public boolean isAktiviert() {
		return aktiviert;
	}
	public void setAktiviert(boolean aktiviert) {
		this.aktiviert = aktiviert;
	}
	public boolean isTeamleistung() {
		return teamleistung;
	}
	public void setTeamleistung(boolean teamleistung) {
		this.teamleistung = teamleistung;
	}
	
	public static ResultSet getRSgetAll(Connection conn) throws SQLException{		
		return conn.prepareStatement("Call DisziplinenAnzeigen(); ").executeQuery();
		
	}
	public static ResultSet getRSgetOne(Connection conn, String did) throws SQLException{			 
		PreparedStatement ps = conn.prepareStatement("Call DisziplinAnzeigen(?)");
		ps.setString(1, did);
		return ps.executeQuery();
	}
	public static void put(Connection conn, Disziplin disziplin) throws SQLException{	
		PreparedStatement ps = conn.prepareStatement("Call DisziplinAnlegen(?,?,?,?,?,?)");
		ps.setString(1,disziplin.getName() );
		ps.setString(2,disziplin.getBeschreibung());
		ps.setInt(3,disziplin.getMinTeilnehmer());
		ps.setInt(4,disziplin.getMaxTeilnehmer());
		ps.setBoolean(5,disziplin.isTeamleistung());
		ps.setBoolean(6,disziplin.isAktiviert());
		ps.execute();
	}
	public static void post(Connection conn, Disziplin disziplin) throws SQLException{	
		PreparedStatement ps = conn.prepareStatement("Call DisziplinBearbeiten(?,?,?,?,?,?,?)");
		ps.setInt(1,disziplin.getDid());
		ps.setString(2,disziplin.getName() );
		ps.setString(3,disziplin.getBeschreibung());
		ps.setInt(4,disziplin.getMinTeilnehmer());
		ps.setInt(5,disziplin.getMaxTeilnehmer());
		ps.setBoolean(6,disziplin.isTeamleistung());
		ps.setBoolean(7,disziplin.isAktiviert());
		ps.execute();
	}
	public static void delete(Connection conn, String did) throws SQLException{	
		PreparedStatement ps = conn.prepareStatement("Call DisziplinLoeschen(?)");
		ps.setString(1,did);
		ps.execute();
	}

	public Disziplin getOne(Connection conn, String did) throws SQLException{
		ResultSet rs;
		rs = getRSgetOne(conn, did);
		if(rs.next()){
			return new Disziplin(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getBoolean(6), rs.getBoolean(7));
		}
		return null;
	}
	
	public ArrayList<Disziplin> getAll(Connection conn) throws SQLException{
		ResultSet rs;
		rs = getRSgetAll(conn);
		ArrayList<Disziplin> returner = new ArrayList<>();
		while(rs.next()){
			returner.add(new Disziplin(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getBoolean(6), rs.getBoolean(7)));
		}
		return returner;
	}
	
    public Regel getErsteRegel() {
        return ersteRegel;
    }

    public void setErsteRegel(Regel ersteRegel) {
        this.ersteRegel = ersteRegel;
    }

    public List<Variable> getVariablen() {
        return variablen;
    }

    public void setVariablen(List<Variable> variablen) {
        this.variablen = variablen;
    }
}
