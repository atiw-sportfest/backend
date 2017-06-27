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

    @XmlElement
	private Integer did;

    @XmlElement
	private String name;

    @XmlElement
	private String beschreibung;

    @XmlElement
	private Integer minTeilnehmer;

    @XmlElement
	private Integer maxTeilnehmer;

    @XmlElement
	private Boolean aktiviert;

    @XmlElement
	private Boolean teamleistung;

    @XmlElement
    private List<Variable> variablen;

    @XmlElement
    private List<Regel> regeln;
    
    @XmlElement
    private Integer kontrahentenAnzahl;

	public Disziplin(){}

	public Disziplin(Integer did, String name, String beschreibung, Integer minTeilnehmer, Integer maxTeilnehmer, Boolean aktiviert, Boolean temleistung, Integer kontrahentenAnzahl){
		this.did = did;
		this.name = name;
		this.beschreibung = beschreibung;
		this.minTeilnehmer = minTeilnehmer;
		this.maxTeilnehmer = maxTeilnehmer;
		this.aktiviert = aktiviert;
		this.teamleistung = temleistung;
		this.kontrahentenAnzahl = kontrahentenAnzahl;
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

		PreparedStatement ps = conn.prepareStatement("Call DisziplinAnlegen(?,?,?,?,?,?,?)");

		ps.setString(1, disziplin.name);
		ps.setString(2, disziplin.beschreibung);

		ps.setInt(3, disziplin.minTeilnehmer);
		ps.setInt(4, disziplin.maxTeilnehmer);

		ps.setBoolean(5, disziplin.teamleistung);
		ps.setBoolean(6, disziplin.aktiviert);

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

        Disziplin orig = Disziplin.getOne(conn, Integer.toString(disziplin.did), false);

		PreparedStatement ps = conn.prepareStatement("Call DisziplinBearbeiten(?,?,?,?,?,?,?,?)");

		ps.setInt(1, orig.did); // Never change id

		ps.setString(2, disziplin.name != null ? disziplin.name : orig.name);
		ps.setString(3, disziplin.beschreibung != null ? disziplin.beschreibung : orig.beschreibung);

		ps.setInt(4, disziplin.minTeilnehmer != null ? disziplin.minTeilnehmer : orig.minTeilnehmer);
		ps.setInt(5, disziplin.maxTeilnehmer != null ? disziplin.maxTeilnehmer : orig.maxTeilnehmer);

		ps.setBoolean(6, disziplin.teamleistung != null ? disziplin.teamleistung : orig.teamleistung);
		ps.setBoolean(7, disziplin.aktiviert != null ? disziplin.aktiviert : orig.aktiviert);

		ps.execute();

        conn.close();
	}

    public static void edit(Connection con, String did, Disziplin disziplin) throws SQLException, NotFoundException, NumberFormatException {

        disziplin.did = Integer.parseInt(did);

        edit(con, disziplin);
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
     * Lädt zusätzlich die Regeln aus der Datenbank.
     *
     * @param conn die zu nutzende Datenbankverbindung
     * @param did die ID der abzurufenden Disziplin
     * @return die gefundene Disziplin
     * @throws Disziplin.NotFoundException wenn keine Disziplin mit dieser ID gefunden wurde.
     */
	public static Disziplin getOne(Connection conn, String did) throws SQLException, NotFoundException {
        return getOne(conn, did, true);
    }

    /**
     * Ruft eine Disziplin aus der Datenbank ab.
     *
     * Lädt zusätzlich die Regeln aus der Datenbank.
     *
     * @param conn die zu nutzende Datenbankverbindung
     * @param did die ID der abzurufenden Disziplin
     * @param close ob die Connection geschlossen werden soll
     * @return die gefundene Disziplin
     * @throws Disziplin.NotFoundException wenn keine Disziplin mit dieser ID gefunden wurde.
     */
	public static Disziplin getOne(Connection conn, String did, boolean close) throws SQLException, NotFoundException {

		ResultSet rs = getRSgetOne(conn, did);
        Disziplin one = null;

		if(rs.next())
            one = fromResultSet(conn, rs);

        if(close)
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
            returner.add(fromResultSet(conn, rs));

        conn.close();

		return returner;
	}

    private static Disziplin fromResultSet(Connection conn, ResultSet rs) throws SQLException {

        Disziplin d = new Disziplin();
        int i = 1;

        d.did = rs.getInt(i++);

        d.name = rs.getString(i++);
        d.beschreibung = rs.getString(i++);

        d.minTeilnehmer = rs.getInt(i++);
        d.maxTeilnehmer = rs.getInt(i++);

        d.aktiviert = rs.getBoolean(i++);
        d.teamleistung = rs.getBoolean(i++);

        d.kontrahentenAnzahl = rs.getInt(i++);

        d.regeln = Regel.getAll(conn, d.did);
        d.variablen = Variable.getAll(conn, d.did, false);

        return d;
    }

    @XmlTransient
    public Regel getErsteRegel() {

        Regel regel;

        // Erste Regel kann entweder am Anfang oder Ende stehen.
        // Erstmal versuchen dort zu finden.
        // Wenn das alles nix bringt, versuchen die erste Regel in der Liste zu finden.

        // Erst am Ende, wie wenn aus der DB,
        // dann am Anfang,
        // dann alles durchsuchen

        if(regeln != null && regeln.size() > 0)
            if( ( regel = regeln.get(regeln.size()-1) ) != null && regel.isFirst())
                return regel;
            else if( ( regel = regeln.get(0) ) != null && regel.isFirst())
                return regel;
            else
                for(Regel regel_ : regeln)
                    if(regel_ != null && regel_.isFirst())
                        return regel_;

        return null;
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
