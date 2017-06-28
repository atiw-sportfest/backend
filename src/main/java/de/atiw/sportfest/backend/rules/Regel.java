package de.atiw.sportfest.backend.rules;

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

import org.codehaus.commons.compiler.CompileException;

@XmlRootElement
public class Regel {

    /**
     * Regel-ID
     */
    @XmlElement
    private Integer rid;

    /**
     * ID der Disziplin zu der die Regel gehört.
     */
    @XmlElement
    private Integer did;

    /**
     * Der Index dieser Regel.
     */
    @XmlElement
    private Integer index;

    @XmlElement
    private String expression;

    @XmlElement
    private int points;

    @XmlTransient
    private Regel next;

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

    public int evaluate(Variable[] vars, Object[] values) throws CompileException, InvocationTargetException {
       return RegelEvaluator.instance.evaluate(new EvaluationParameters(expression, vars), values) ? points : next != null ? next.evaluate(vars, values) : 0;
    }

    /**
     * Regel anhand ID
     */
    public static Regel getOne(Connection con, Integer rid) throws SQLException {

        ResultSet rs;
        PreparedStatement prep;
        int i = 1;

        prep = con.prepareStatement("CALL RegelAnzeigen(?)");
        prep.setInt(i++, rid);

        rs = prep.executeQuery();

        return rs.next() ? fromResulSet(rs) : null;
    }

    /**
     * Erste Regel für Disziplin
     */
    public static Regel getFirst(Connection con, Integer did) throws SQLException {

        ResultSet rs;
        PreparedStatement prep;
        int i = 1;

        prep = con.prepareStatement("CALL Regel1EinerDisziplinAnzeigen(?)");
        prep.setInt(i++, did);

        rs = prep.executeQuery();

        return rs.next() ? fromResulSet(rs) : null;

    }

    /**
     * Alle Regeln
     */
    public static List<Regel> getAll(Connection con) throws SQLException {

        ResultSet rs;
        List<Regel> regeln = new ArrayList<>();

        rs = con.prepareStatement("CALL RegelnAnzeigen()").executeQuery();

        while(rs.next())
            regeln.add(fromResulSet(rs));

        return regeln;
    }

    /**
     * Alle Regeln einer Disziplin.
     *
     * Setzt {@code Regel.next}.
     */
    public static List<Regel> getAll(Connection con, Integer did) throws SQLException {

        ResultSet rs;
        PreparedStatement prep;
        List<Regel> regeln = new ArrayList<>();
        Regel cur, last = null;
        int i = 1;

        prep = con.prepareStatement("CALL RegelnEinerDisziplinAnzeigen(?)"); // Regeln kommen rückwärst, also max. Index -> min Index.
        prep.setInt(i++, did);

        rs = prep.executeQuery();

        while(rs.next()){
            cur = fromResulSetWithNext(rs, last);
            regeln.add(cur);
            last = cur;
        }

        return regeln;
    }

    private static Regel fromResulSet(ResultSet rs) throws SQLException {

        int i = 1;

        Regel regel = new Regel();

        regel.rid = rs.getInt(i++);
        regel.did = rs.getInt(i++);
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

}

