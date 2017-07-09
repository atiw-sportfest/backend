package de.atiw.sportfest.backend.resource.jaxb;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;

import org.codehaus.commons.compiler.CompileException;

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
	public static ResultSet getRSgetOne(Connection conn, int did) throws SQLException{

		PreparedStatement ps = conn.prepareStatement("Call DisziplinAnzeigen(?)");

		ps.setInt(1, did);

		return ps.executeQuery();
	}

    /**
     * Legt eine Disziplin in der Datenbank ab.
     *
     * Die Connection wird <em>von der Methode geschlossen</em>.
     *
     * @param conn die zu nutzende Datenbankverbindung
     * @param disziplin die abzulegende Disziplin
     * @throws falseInputException 
     */
	public static Disziplin create(Connection conn, Disziplin disziplin) throws SQLException, BadRequestException, InternalServerErrorException {

        ResultSet rs;
        PreparedStatement ps;
        int did;

        if(disziplin.minTeilnehmer == null || disziplin.maxTeilnehmer == null || 
                disziplin.teamleistung == null || disziplin.aktiviert == null)
            throw new BadRequestException("minTeilnehmer, maxTeilnehmer, teamleistung und aktiviert müssen immer gesetzt sein!");

        try {

            ps = conn.prepareStatement("Call DisziplinAnlegen(?,?,?,?,?,?,?)");

            ps.setString(1, disziplin.name);
            ps.setString(2, disziplin.beschreibung);

            if(disziplin.minTeilnehmer != null && disziplin.minTeilnehmer > 0)
                ps.setInt(3, disziplin.minTeilnehmer);
            else 
                throw new BadRequestException("Das Attribut && minTeilnehmer darf nicht kleiner als 1 sein!");

            ps.setInt(4, disziplin.maxTeilnehmer);

            ps.setBoolean(5, disziplin.teamleistung);
            ps.setBoolean(6, disziplin.aktiviert);

            ps.setInt(7, disziplin.kontrahentenAnzahl);

            rs = ps.executeQuery();

            if(rs.next())
                did = rs.getInt(1);
            else
                throw new InternalServerErrorException("Keine LAST_INSERT_ID ?!");

            Regel.create(conn, did, disziplin.regeln, false);
            Variable.create(conn, did, disziplin.variablen, false);

            return getOne(conn, did, false);

        } finally { conn.close(); }

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
     * @throws FalseInputException 
     */
	public static Disziplin edit(Connection conn, Disziplin disziplin) throws SQLException, NotFoundException, BadRequestException {

        Disziplin orig;
        PreparedStatement ps;

        try {

            orig = Disziplin.getOne(conn, Integer.toString(disziplin.did), false);

            Variable.createOrEdit(conn, disziplin.did, disziplin.variablen, false);

            ps = conn.prepareStatement("Call DisziplinBearbeiten(?,?,?,?,?,?,?,?)");

            ps.setInt(1, orig.did); // Never change id

            ps.setString(2, disziplin.name != null ? disziplin.name : orig.name);
            ps.setString(3, disziplin.beschreibung != null ? disziplin.beschreibung : orig.beschreibung);

            if(disziplin.minTeilnehmer > 0)
                ps.setInt(4, disziplin.minTeilnehmer != null ? disziplin.minTeilnehmer : orig.minTeilnehmer);
            else	
                throw new BadRequestException("Das Attribut && minTeilnehmer darf nicht kleiner als 1 sein!");
            ps.setInt(5, disziplin.maxTeilnehmer != null ? disziplin.maxTeilnehmer : orig.maxTeilnehmer);

            ps.setBoolean(6, disziplin.teamleistung != null ? disziplin.teamleistung : orig.teamleistung);
            ps.setBoolean(7, disziplin.aktiviert != null ? disziplin.aktiviert : orig.aktiviert);

            ps.setInt(8, disziplin.kontrahentenAnzahl != null ? disziplin.kontrahentenAnzahl : orig.kontrahentenAnzahl);

            ps.execute();

            if(disziplin.regeln != null)
                Regel.createOrEdit(conn, orig.did, disziplin.regeln, false);

            return getOne(conn, Integer.toString(disziplin.did));

        } finally { conn.close(); }
	}

    public static Disziplin edit(Connection con, String did, Disziplin disziplin) throws SQLException, NotFoundException, NumberFormatException, BadRequestException {

        disziplin.did = Integer.parseInt(did);

        return edit(con, disziplin);
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
        delete(conn, did, false);
    }

	public static void delete(Connection conn, int did, boolean close) throws SQLException{

        Disziplin d;
		PreparedStatement ps;

        try {

            d = getOne(conn, did, false);

            ps = conn.prepareStatement("Call DisziplinLoeschen(?)");
            ps.setInt(1, did);

            Variable.deleteDisziplin(conn, did, false);

            for(Regel regel : d.regeln)
                Regel.delete(conn, regel, false);

            ps.execute();

        } finally { if(close) conn.close(); }

	}

    public static void delete(Connection con, String did, boolean close) throws SQLException {
        delete(con, Integer.parseInt(did), close);
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
	public static Disziplin getOne(Connection conn, int did, boolean close) throws SQLException, NotFoundException {

		ResultSet rs = getRSgetOne(conn, did);
        Disziplin one = null;

		if(rs.next())
            one = fromResultSet(conn, rs);

        if(close)
            conn.close();

        if(one != null)
            return one;
        else
            throw new NotFoundException(String.format("Keine Disziplin zu ID \"%d\" gefunden!", did));
	}

	public static Disziplin getOne(Connection conn, String did, boolean close) throws SQLException, NotFoundException {
        return getOne(conn, Integer.parseInt(did), close);
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

        d.regeln = Regel.getAll(conn, d.did, false);
        d.variablen = Variable.getAll(conn, d.did, false);

        return d;
    }

    @XmlTransient
    public Regel getErsteRegel() {

        Regel regel = null;

        // Erste Regel kann entweder am Anfang oder Ende stehen.
        // Erstmal versuchen dort zu finden.
        // Wenn das alles nix bringt, versuchen die erste Regel in der Liste zu finden.

        // Erst am Ende, wie wenn aus der DB,
        // dann am Anfang,
        // dann alles durchsuchen

        if(regeln != null && regeln.size() > 0)
            if( ( regel = regeln.get(regeln.size()-1) ) != null && regel.isFirst())
                ; // regel = regel
            else if( ( regel = regeln.get(0) ) != null && regel.isFirst())
                ; // regel = regel
            else
                for(Regel regel_ : regeln)
                    if(regel_ != null && regel_.isFirst())
                        regel = regel_;

        if(regel != null)
            regel.resetCounters();

        return regel;
    }

    @XmlTransient
    public void setRegeln(Regel ersteRegel) {

        Regel regel = ersteRegel;
        this.regeln = new ArrayList<>();

        do {
            this.regeln.add(regel);
            regel = regel.getNext();
        } while(regel != null && regel.getNext() != null);
    }

    public int getPoints(List<Variable> vars, List<Object> vals) throws CompileException, InvocationTargetException {
        return getErsteRegel().evaluate(vars, vals);
    }

    public List<Variable> getVariablen() {
        return variablen;
    }

    public void setVariablen(List<Variable> variablen) {
        this.variablen = variablen;
    }

    @XmlTransient
    public String getName(){
        return this.name;
    }

    @XmlTransient
    public Integer getDid(){
        return this.did;
    }

    @XmlTransient
    public List<Variable> getVariablenSorted(){
        List<Variable> vars = new ArrayList<>(variablen);
        vars.sort(Variable::indexCompare);
        return vars;
    }

    @XmlTransient
    public List<Leistung> getLeistungenSorted(Connection con, boolean close) throws SQLException {

        List<Leistung> ret = new ArrayList<>(Leistung.getAllDisziplin(con, this.did, close));

        return Leistung.getErgebnisDeepSorted(getVariablenSorted(), ret, 0);

    }
}
