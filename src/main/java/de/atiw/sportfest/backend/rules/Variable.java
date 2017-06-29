package de.atiw.sportfest.backend.rules;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

@XmlRootElement
public class Variable {

    @XmlElement
    private Integer var_id;

    @XmlElement
    private String name;

    @XmlElement
    private String desc;

    @XmlElement
    private String expressionParameter;

    @XmlElement
    private Typ typ;

    public Variable(){}

    public Variable(String name, String desc, String expressionParameter, Typ typ) {
        this.name = name;
        this.desc = desc;
        this.expressionParameter = expressionParameter;
        this.typ = typ;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String getExpressionParameter() {
        return expressionParameter;
    }

    public Typ getTyp() {
        return typ;
    }

    public static Variable getOne(Connection con, String vid, boolean close) throws SQLException, NotFoundException {

        Variable var = null;
        ResultSet rs;
        PreparedStatement prep;

        try {

            prep = con.prepareStatement("CALL VariableAnzeigen(?)");
            prep.setInt(1, Integer.parseInt(vid));

            rs = prep.executeQuery();

            if(rs.next())
                var = fromResultSet(con, rs);

            if(var == null)
                throw new NotFoundException(String.format("Variable mit ID \"%s\" nicht gefunden!", vid));

            return var;

        } finally { if(close) con.close(); }

    }

    public static Variable getOne(Connection con, String vid) throws SQLException, NotFoundException {
        return getOne(con, vid, true);
    }

    public static List<Variable> getAll(Connection con, boolean close) throws SQLException {

        List<Variable> all = new ArrayList<>();
        ResultSet rs;

        try {

            rs = con.prepareStatement("CALL VariablenAnzeigen()").executeQuery();

            while(rs.next())
                all.add(fromResultSet(con, rs));

            return all;

        } finally { if(close) con.close(); }

    }

    public static List<Variable> getAll(Connection con) throws SQLException {
        return getAll(con, true);
    }

    public static List<Variable> getAll(Connection con, int did, boolean close) throws SQLException {

        List<Variable> all = new ArrayList<>();
        PreparedStatement prep;
        ResultSet rs;

        try {

            prep = con.prepareStatement("CALL VariablenEinerDisziplinAnzeigen(?)");
            prep.setInt(1, did);

            rs = prep.executeQuery();

            while(rs.next())
                all.add(fromResultSet(con, rs));

            return all;

        } finally { if(close) con.close(); }

    }

    public static Variable create(Connection con, Variable var, boolean close) throws SQLException, InternalServerErrorException {

        PreparedStatement prep;
        ResultSet rs;
        int i = 1;

        try {

            prep = con.prepareStatement("CALL VariableAnlegen(?, ?, ?, ?)"); // var_name, var_descr, var_exprParam, typ_id

            prep.setString(i++, var.name);
            prep.setString(i++, var.desc);
            prep.setString(i++, var.expressionParameter);

            prep.setInt(i++, var.typ.getTypID());

            rs = prep.executeQuery();

            if(rs.next())
                return fromResultSet(con, rs);
            else
                throw new InternalServerErrorException("Die Datenbank hat das erstellte Objekt nicht zurückgegeben ?!");


        } finally { if(close) con.close(); }
    
    }

    public static Variable create(Connection con, Variable var) throws SQLException, InternalServerErrorException {
        return create(con, var, true);
    }

    public static Variable edit(Connection con, String vid, Variable var, boolean close) throws SQLException, NotFoundException {

        Variable orig;
        PreparedStatement prep;
        ResultSet rs;
        int i = 1;

        try {

            orig = getOne(con, vid, false);

            prep = con.prepareStatement("CALL VariableBearbeiten(?, ?, ?, ?, ?)"); // var_id, var_name, var_descr, var_exprParam, typ_id

            prep.setInt(i++, orig.var_id);;

            prep.setString(i++, var.name != null ? var.name : orig.name);
            prep.setString(i++, var.desc != null ? var.desc : orig.desc);
            prep.setString(i++, var.expressionParameter != null ? var.expressionParameter : orig.expressionParameter);

            prep.setInt(i++, var.typ != null ? var.typ.getTypID() : orig.typ.getTypID());
            
            rs = prep.executeQuery();

            if(rs.next())
                return fromResultSet(con, rs);
            else
                throw new InternalServerErrorException("Die Datenbank hat das bearbeitete Objekt nicht zurückgeliefert ?!");
        } finally { if(close) con.close(); }

    }

    public static Variable edit(Connection con, String vid, Variable var) throws SQLException, NotFoundException {
        return edit(con, vid, var, true);
    }

    public static void delete(Connection con, String vid, boolean close) throws SQLException, WebApplicationException {

        PreparedStatement prep;
        ResultSet rs;
        int i = 1;

        try {

            prep = con.prepareStatement("SELECT disz_id FROM disziplin_variable WHERE var_id = ?");
            prep.setInt(i++, Integer.parseInt(vid));

            rs = prep.executeQuery();

            if(rs.next()){

                StringBuilder sb = new StringBuilder("Variable wird noch in folgenden/r Disziplin verwendet: ").append(rs.getInt(1));

                while(rs.next())
                    sb.append(", ").append(rs.getInt(1));

                throw new WebApplicationException(sb.toString(), Response.Status.CONFLICT);
            } // else continue

            i = 1;

            prep = con.prepareStatement("CALL VariableEntfernen(?)");
            prep.setInt(i++, Integer.parseInt(vid));

            prep.execute();

        } finally { if(close) con.close(); }
    }

    public static void delete(Connection con, String vid) throws SQLException, NotFoundException {
        delete(con, vid, true);
    }

    private static Variable fromResultSet(Connection con, ResultSet rs) throws SQLException, InternalServerErrorException {

        Variable var = new Variable();
        int i = 1, typ_id;

        var.var_id = rs.getInt(i++);
        var.name =  rs.getString(i++);
        var.desc = rs.getString(i++);
        var.expressionParameter = rs.getString(i++);
        var.typ = Typ.getOne(con, rs.getInt(i++), false);

        return var;

    }
}

