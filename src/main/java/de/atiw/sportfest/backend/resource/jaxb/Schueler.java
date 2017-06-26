package de.atiw.sportfest.backend.resource.jaxb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Schueler {

	private Integer sid;
	private String vorname;
	private String name;
	private Integer kid;
	private Integer gid;

	public Schueler() {}
	
	public Schueler(Integer sid, String vorname, String name, Integer kid, Integer gid) {
		this.sid = sid;
		this.vorname = vorname;
		this.name = name;
		this.kid = kid;
		this.gid = gid;
	}

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getKid() {
		return kid;
	}

	public void setKid(Integer kid) {
		this.kid = kid;
	}

	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public static ResultSet getRSgetAll(Connection conn) throws SQLException {
		return conn.prepareStatement("Call SchuelerPAnzeigen();").executeQuery();

	}

	public static ResultSet getRSgetOne(Connection conn, String sid) throws SQLException {
		PreparedStatement ps = conn.prepareStatement("Call SchuelerAnzeigen(?);");
		ps.setString(1, sid);
		return ps.executeQuery();
	}

	public static ResultSet getRSgetAllOfKlasse(Connection conn, String kid) throws SQLException {
		PreparedStatement ps = conn.prepareStatement("Call SchuelerEinerKlasseAnzeigen(?);");
		ps.setString(1, kid);
		return ps.executeQuery();
	}
	
	public static ResultSet getRSgetAllOfKlasseAndDisziplin(Connection conn, String kid, String did) throws SQLException {
		PreparedStatement ps = conn.prepareStatement("Call SchuelerEinerDisziplinUndKlasseAnzeigen(?, ?);");
		ps.setString(1, kid);
		ps.setString(2, did);
		return ps.executeQuery();
	}

	public static void getRSput(Connection conn, Schueler schueler) throws SQLException {
		PreparedStatement ps = conn.prepareStatement("Call SchuelerAnlegen(?, ?, ?, ?);");
		ps.setString(1, schueler.getVorname());
		ps.setString(2, schueler.getName());
		ps.setInt(3, schueler.getKid());
		ps.setInt(4, schueler.getGid());
		ResultSet rs = ps.executeQuery();
		if(rs.next()){
			schueler.setSid(rs.getInt(1));
		}
	}

	public static void getRSdelete(Connection conn, String sid) throws SQLException {
		PreparedStatement ps = conn.prepareStatement("Call SchuelerLoeschen(?);");
		ps.setString(1, sid);
		ps.execute();
	}
}
