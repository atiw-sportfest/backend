package de.atiw.sportfest.backend.resource.jaxb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

import de.atiw.sportfest.backend.rules.Regel;
import de.atiw.sportfest.backend.rules.Variable;

@XmlRootElement
public class Disziplin {

	private Integer did;
	private String name;
	private String beschreibung;
	private Integer minTeilnehmer;
	private Integer maxTeilnehmer;
	private Boolean aktiviert;
	private Boolean teamleistung;
    private List<Variable> variablen;

    @XmlElement
    private List<Regel> regeln;

	public Disziplin(){}

	public Disziplin(Integer did, String name, String beschreibung, Integer minTeilnehmer, Integer maxTeilnehmer, Boolean aktiviert, Boolean temleistung){
		this.did = did;
		this.name = name;
		this.beschreibung = beschreibung;
		this.minTeilnehmer = minTeilnehmer;
		this.maxTeilnehmer = maxTeilnehmer;
		this.aktiviert = aktiviert;
		this.teamleistung = temleistung;
	}


	public Integer getDid() {
		return did;
	}
	public void setDid(Integer did) {
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
	public Integer getMinTeilnehmer() {
		return minTeilnehmer;
	}
	public void setMinTeilnehmer(Integer minTeilnehmer) {
		this.minTeilnehmer = minTeilnehmer;
	}
	public Integer getMaxTeilnehmer() {
		return maxTeilnehmer;
	}
	public void setMaxTeilnehmer(Integer maxTeilnehmer) {
		this.maxTeilnehmer = maxTeilnehmer;
	}
	public Boolean isAktiviert() {
		return aktiviert;
	}
	public void setAktiviert(Boolean aktiviert) {
		this.aktiviert = aktiviert;
	}
	public Boolean isTeamleistung() {
		return teamleistung;
	}
	public void setTeamleistung(Boolean teamleistung) {
		this.teamleistung = teamleistung;
	}

    /**
     * Alle Disziplinen aus der Datenbank abfragen.
     *
     * <em>Die Connection muss vom Caller geschlossen werden.</em>
     *
     * @param conn die zu nutzende Datenbankverbindung
     * @return a {@link java.sql.ResultSet}
     */
	public static ResultSet getRSgetAll(Connection conn) throws SQLException{
		return conn.prepareStatement("Call DisziplinenAnzeigen(); ").executeQuery();

	}

    /**
     * Eine Disziplinen aus der Datenbank abfragen.
     *
     * Die Connection muss <em>vom Caller geschlossen</em> werden.
     *
     * @param conn die zu nutzende Datenbankverbindung
     * @param did die ID der Disziplin
     * @return a {@link java.sql.ResultSet}
     */
	public static ResultSet getRSgetOne(Connection conn, String did) throws SQLException{

		PreparedStatement ps = conn.prepareStatement("Call DisziplinAnzeigen(?)");

		ps.setString(1, did);

		return ps.executeQuery();
	}

    /**
     * Legt eine Disziplin in der Datenbank ab.
     *
     * Die Connection wird <em>von der Methode geschlossen</em>.
     *
     * @param conn die zu nutzende Datenbankverbindung
     * @param disziplin die abzulegende Disziplin
     */
	public static void create(Connection conn, Disziplin disziplin) throws SQLException{

		PreparedStatement ps = conn.prepareStatement("Call DisziplinAnlegen(?,?,?,?,?,?)");

		ps.setString(1,disziplin.getName() );
		ps.setString(2,disziplin.getBeschreibung());

		ps.setInt(3,disziplin.getMinTeilnehmer());
		ps.setInt(4,disziplin.getMaxTeilnehmer());

		ps.setBoolean(5,disziplin.isTeamleistung());
		ps.setBoolean(6,disziplin.isAktiviert());

		ps.execute();

        conn.close();
	}

    /**
     * Bearbeitet eine Disziplin in der Datenbank ab.
     *
     * Gesetze Attribute werden überschrieben, nicht gesetzte beibehalten.
     *
     * Für {@code int} gilt 0 als ungesetzt.
     *
     * Die Connection wird <em>von der Methode geschlossen</em>.
     *
     * @param conn die zu nutzende Datenbankverbindung
     * @param disziplin die neuen Werte der Disziplin
     * @throws Disziplin.NotFoundException wenn die zu bearbeitende Disziplin nicht vorhanden ist.
     */
	public static void edit(Connection conn, Disziplin disziplin) throws SQLException, NotFoundException {

        Disziplin orig = Disziplin.getOne(conn, Integer.toString(disziplin.getDid()));

		PreparedStatement ps = conn.prepareStatement("Call DisziplinBearbeiten(?,?,?,?,?,?,?)");

		ps.setInt(1,disziplin.getDid());

		ps.setString(2, disziplin.getName() != null ? disziplin.getName() : orig.getName());
		ps.setString(3, disziplin.getBeschreibung() != null ? disziplin.getBeschreibung() : orig.getBeschreibung());

		ps.setInt(4, disziplin.getMinTeilnehmer() != 0 ? disziplin.getMinTeilnehmer() : orig.getMinTeilnehmer());
		ps.setInt(5, disziplin.getMaxTeilnehmer() != 0 ? disziplin.getMaxTeilnehmer() : orig.getMaxTeilnehmer());

		ps.setBoolean(6, disziplin.isTeamleistung());
		ps.setBoolean(7, disziplin.isAktiviert());

		ps.execute();

        conn.close();
	}

    /**
     * Löscht eine Disziplin aus der Datenbank.
     *
     * Die Connection wird <em>von der Methode geschlossen</em>.
     *
     * @param conn die zu nutzende Datenbankverbindung
     * @param did die ID der zu löschenden Disziplin
     */
	public static void delete(Connection conn, String did) throws SQLException{

		PreparedStatement ps = conn.prepareStatement("Call DisziplinLoeschen(?)");

		ps.setString(1,did);

		ps.execute();

        conn.close();
	}

    /**
     * Ruft eine Disziplin aus der Datenbank ab.
     *
     * Die Connection wird <em>von der Methode geschlossen</em>.
     *
     * @param conn die zu nutzende Datenbankverbindung
     * @param did die ID der abzurufenden Disziplin
     * @return die gefundene Disziplin
     * @throws Disziplin.NotFoundException wenn keine Disziplin mit dieser ID gefunden wurde.
     */
	public static Disziplin getOne(Connection conn, String did) throws SQLException, NotFoundException {

		ResultSet rs = getRSgetOne(conn, did);
        Disziplin one = null;

		if(rs.next())
            one = new Disziplin(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getBoolean(6), rs.getBoolean(7));

        conn.close();

        if(one != null)
            return one;
        else
            throw new NotFoundException(String.format("Keine Disziplin zu ID \"%s\" gefunden!", did));
	}

    /**
     * Ruft alle Disziplin aus der Datenbank ab.
     *
     * Die Connection wird <em>von der Methode geschlossen</em>.
     *
     * @param conn die zu nutzende Datenbankverbindung
     * @return die gefundene Disziplin
     */
	public static ArrayList<Disziplin> getAll(Connection conn) throws SQLException{

		ArrayList<Disziplin> returner = new ArrayList<>();
		ResultSet rs = getRSgetAll(conn);

		while(rs.next())
            returner.add(new Disziplin(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getBoolean(6), rs.getBoolean(7)));

        conn.close();

		return returner;
	}

    @XmlTransient
    public Regel getErsteRegel() {
        return regeln != null && regeln.size() > 0 ? regeln.get(0) : null;
    }

    public void setRegeln(Regel ersteRegel) {

        Regel regel = ersteRegel;
        this.regeln = new ArrayList<>();

        do {
            this.regeln.add(regel);
            regel = regel.getNext();
        } while(regel != null && regel.getNext() != null);

    }

    public List<Variable> getVariablen() {
        return variablen;
    }

    public void setVariablen(List<Variable> variablen) {
        this.variablen = variablen;
    }

    public static class NotFoundException extends Exception {

        private static final long serialVersionUID = 1L;

        public NotFoundException(String message){
            super(message);
        }

    }

}
