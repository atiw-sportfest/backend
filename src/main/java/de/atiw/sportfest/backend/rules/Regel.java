package de.atiw.sportfest.backend.rules;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;

import org.codehaus.commons.compiler.CompileException;

import de.atiw.sportfest.backend.resource.jaxb.Disziplin;

@XmlRootElement
public class Regel {

    /**
     * Regel-ID
     */
    @XmlElement
    private Integer rid;

    /**
     * Der Index dieser Regel.
     */
    @XmlElement
    private Integer index;

    @XmlElement
    private String expression;

    @XmlElement
    private Integer points;

    @XmlTransient
    private Regel next;

    @XmlTransient
    private int counter = 0;

    @XmlTransient
    private static final Logger logger = Logger.getLogger("Regel");

    public Regel() {
    }

    public Regel(String expression, int points) {
        this.expression = expression;
        this.points = points;
    }

    public Regel(String expression, int points, Regel next) {
        this.expression = expression;
        this.points = points;
        this.next = next;
    }

    public String getExpression() {
        return expression;
    }

    public int getPoints() {
        return points;
    }

    @XmlTransient
    public Regel getNext() {
        return next;
    }

    @XmlTransient
    public boolean isFirst(){
        return this.index == 0;
    }

    public int evaluate(List<Variable> vars, List<Object> vals) throws CompileException, InvocationTargetException {

        boolean success;

        try {

            if(vars.get(vars.size() - 1) != Variable.Counter){
                vars.add(Variable.Counter);
                vals.add((Integer) counter);
            } else vals.set(vals.size() - 1, (Integer) counter);

        } catch(UnsupportedOperationException e){

            // The list maybe does not support #add and/or #set.
            // ArrayList implements both, so continue with that.
            return evaluate(new ArrayList<>(vars), new ArrayList<>(vals));

        }

        logger.info(String.format("Evaluate %s", expression));

        for(int i = 0; i < vars.size(); i++)
            logger.info(String.format("Var %s val %s", vars.get(i).getExpressionParameter(), vals.get(i)));

        if(( success = RegelEvaluator.instance.evaluate(new EvaluationParameters(expression, vars), vals) ))
            counter++;

        return success ? points : next != null ? next.evaluate(vars, vals) : 0;

    }

    /**
     * <code>counter</code>-Variable für diese und nachfolgende Regeln zurücksetzen.
     */
    public void resetCounters(){

        this.counter = 0;

        if(next != null)
            next.resetCounters();

    }

    /**
     * Regel anhand ID
     */
    public static Regel getOne(Connection con, Integer rid, boolean close) throws SQLException, NotFoundException {

        ResultSet rs;
        PreparedStatement prep;
        int i = 1;

        try {

            prep = con.prepareStatement("CALL RegelAnzeigen(?)");
            prep.setInt(i++, rid);

            rs = prep.executeQuery();

            if(rs.next())
                return fromResulSet(rs);
            else
                throw new NotFoundException(String.format("Regel mit der ID \"%d\" konnte nicht gefunden werden!", rid));

        } finally { if(close) con.close(); }

    }

    public static Regel getOne(Connection con, Integer rid) throws SQLException {
        return getOne(con, rid, true);
    }

    /**
     * Erste Regel für Disziplin
     */
    public static Regel getFirst(Connection con, Integer did, boolean close) throws SQLException {

        ResultSet rs;
        PreparedStatement prep;
        int i = 1;

        try {

            prep = con.prepareStatement("CALL Regel1EinerDisziplinAnzeigen(?)");
            prep.setInt(i++, did);

            rs = prep.executeQuery();

            return rs.next() ? fromResulSet(rs) : null;

        } finally { if(close) con.close(); }
    }

    public static Regel getFirst(Connection con, Integer did) throws SQLException {
        return getFirst(con, did, true);
    }

    /**
     * Alle Regeln
     */
    public static List<Regel> getAll(Connection con, boolean close) throws SQLException {

        ResultSet rs;
        List<Regel> regeln = new ArrayList<>();

        try {
            rs = con.prepareStatement("CALL RegelnAnzeigen()").executeQuery();

            while(rs.next())
                regeln.add(fromResulSet(rs));

            return regeln;
        } finally { if(close) con.close(); }

    }

    public static List<Regel> getAll(Connection con) throws SQLException {
        return getAll(con, true);
    }

    /**
     * Alle Regeln einer Disziplin.
     *
     * Setzt {@code Regel.next}.
     */
    public static List<Regel> getAll(Connection con, Integer did, boolean close) throws SQLException {

        ResultSet rs;
        PreparedStatement prep;
        List<Regel> regeln = new ArrayList<>();
        Regel cur, last = null;
        int i = 1;

        try {

            prep = con.prepareStatement("CALL RegelnEinerDisziplinAnzeigen(?)"); // Regeln kommen rückwärst, also max. Index -> min Index.
            prep.setInt(i++, did);

            rs = prep.executeQuery();

            while(rs.next()){
                cur = fromResulSetWithNext(rs, last);
                regeln.add(cur);
                last = cur;
            }

            return regeln;

        } finally { if(close) con.close(); }
    }

    public static List<Regel> getAll(Connection con, Integer did) throws SQLException {
        return getAll(con, did, true);
    }

    public static void createOrEdit(Connection con, int did, List<Regel> regeln, boolean close) throws SQLException, InternalServerErrorException {
        
        List<Regel> diff;
        List<Integer> coe;

        try {

            // coe - createOrEdit
            coe = new ArrayList<>();

            // pre-fill diff with existing rules
            diff = getAll(con, did, false);

            for(Regel regel : regeln)
                coe.add(createOrEdit(con, did, regel, false).rid);

            diff.removeAll(coe);

            // Regeln die nicht mehr vorhanden sind
            // und auch nicht verändert wurden löschen

            for(Regel regel : diff){
                if(!coe.contains(regel.rid))
                    delete(con, regel, false);
            }

        } finally { if(close) con.close(); }

        
    }

    public static void createOrEdit(Connection con, int did, List<Regel> regeln) throws SQLException, InternalServerErrorException {
        createOrEdit(con, did, regeln, true);
    }

    public static Regel createOrEdit(Connection con, int did, Regel regel, boolean close) throws SQLException, InternalServerErrorException {

        Regel orig;

        if(regel.rid == null)
            return create(con, did, regel, close);
        else if ( (orig = getOne(con, regel.rid, false)) != null)
            return edit(con, regel, orig, close);
        else
            return create(con, did, regel, close);

    }

    public static Regel createOrEdit(Connection con, int did, Regel regel) throws SQLException, InternalServerErrorException{
        return createOrEdit(con, did, regel, true);
    }

    public static List<Regel> create(Connection con, int did, List<Regel> regeln, boolean close) throws SQLException, InternalServerErrorException {

        PreparedStatement prep;
        List<Regel> ret = new ArrayList<>();
        ResultSet rs;
        int i = 1;

        try {

            prep = con.prepareStatement("CALL RegelAnlegen(?, ?, ?, ?)"); // did, expr, idx, points

            for(Regel regel : regeln){

                i = 1;

                prep.setInt(i++, did);
                prep.setString(i++, regel.expression);
                prep.setInt(i++, regel.index);
                prep.setInt(i++, regel.points);

                rs = prep.executeQuery();

                if(rs != null && rs.next())
                    ret.add(getOne(con, rs.getInt(1), false));
                else
                    throw new InternalServerErrorException("Keine LAST_INSERT_ID ?!");

            }

            return ret;

        } finally { if(close) con.close(); }

    }

    public static List<Regel> create(Connection con, int did, List<Regel> regeln) throws SQLException {
        return create(con, did, regeln, true);
    }

    public static Regel create(Connection con, int did, Regel regel, boolean close) throws SQLException {
        return create(con, did, Arrays.asList(new Regel[]{ regel }), close).get(0);
    }

    public static Regel edit(Connection con, Regel regel, boolean close) throws SQLException {

        Regel orig;

        try { return (orig = getOne(con, regel.rid)) != null ? edit(con, regel, orig, close) : null; }
        finally { if(close) con.close(); }

    }

    public static Regel edit(Connection con, Regel regel) throws SQLException {
        return edit(con, regel, true);
    }

    public static Regel edit(Connection con, Regel regel, Regel orig, boolean close) throws SQLException {

        if(regel.rid != null && regel.expression == null && regel.index == null && regel.points == null)
            return orig;

        PreparedStatement prep = con.prepareStatement("CALL RegelBearbeiten(?, ?, ?, ?)"); // rid, expr, idx, points
        int i = 1;

        try {

            prep.setInt(i++, orig.rid); // never change rid
            prep.setString(i++, regel.expression != null ? regel.expression : orig.expression);
            prep.setInt(i++, regel.index != null ? regel.index : orig.index);
            prep.setInt(i++, regel.points != null ? regel.points : orig.points);

            prep.execute();

            return getOne(con, orig.rid, close);

        } finally { if(close) con.close(); }

    }

    public static Regel edit(Connection con, Regel regel, Regel orig) throws SQLException {
        return edit(con, regel, orig, true);
    }

    public static void delete(Connection con, Regel regel, boolean close) throws SQLException, InternalServerErrorException {

        PreparedStatement prep;

        try {

            if(regel.rid == null)
                throw new InternalServerErrorException("Zu löschende Regel hat keine ID ?!");

            prep = con.prepareStatement("CALL RegelLoeschen(?)");
            prep.setInt(1, regel.rid);
            prep.execute();

        } finally { if(close) con.close(); }

    }

    public static void delete(Connection con, Regel regel) throws SQLException, InternalServerErrorException {
        delete(con, regel, true);
    }

    private static Regel fromResulSet(ResultSet rs) throws SQLException {

        int i = 1;

        Regel regel = new Regel();

        regel.rid = rs.getInt(i++);
        i++; // did
        regel.expression = rs.getString(i++);
        regel.index = rs.getInt(i++);
        regel.points = rs.getInt(i++);

        return regel;
    }

    private static Regel fromResulSetWithNext(ResultSet rs, Regel next) throws SQLException {

        Regel regel = fromResulSet(rs);

        regel.next = next;

        return regel;
    }

    /*
     * Generated
     */
    @Override
    public int hashCode() {
        int result = 24229;
        result = 47581 * result + (rid != null ? rid.hashCode() : 0);
        result = 47581 * result + (index != null ? index.hashCode() : 0);
        result = 47581 * result + (expression != null ? expression.hashCode() : 0);
        result = 47581 * result + (points != null ? points.hashCode() : 0);
        result = 47581 * result + (next != null ? next.hashCode() : 0);
        return result;
    }

    /*
     * Generated
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Regel object = (Regel) o;

        if (rid != null ? !rid.equals(object.rid) : object.rid != null) return false;
        if (index != null ? !index.equals(object.index) : object.index != null) return false;
        if (expression != null ? !expression.equals(object.expression) : object.expression != null) return false;
        if (points != null ? !points.equals(object.points) : object.points != null) return false;
        return !(next != null ? !next.equals(object.next) : object.next != null);
    }

}

