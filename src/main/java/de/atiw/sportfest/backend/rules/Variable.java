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

@XmlRootElement
public class Variable {

    @XmlElement
    private int var_id;

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

    public static Variable getOne(Connection con, String vid, boolean close) throws InternalServerErrorException, NotFoundException {

        Variable var = null;
        ResultSet rs;
        PreparedStatement prep;

        try {

            prep = con.prepareStatement("CALL VariableAnzeigen(?)");
            prep.setInt(1, Integer.parseInt(vid));

            rs = prep.executeQuery();

            if(rs.next())
                var = fromResultSet(con, rs);

        } catch (SQLException e){
            throw new InternalServerErrorException(e);
        } finally {

            try {
                if(con != null && close)
                    con.close();
            } catch (SQLException e){
                throw new InternalServerErrorException(e);
            }

        }

        if(var == null)
            throw new NotFoundException(String.format("Variable mit ID \"%s\" nicht gefunden!", vid));

        return var;
    }

    public static List<Variable> getAll(Connection con, boolean close) throws InternalServerErrorException {

        List<Variable> all = new ArrayList<>();
        ResultSet rs;

        try {

            rs = con.prepareStatement("CALL VariablenAnzeigen()").executeQuery();

            while(rs.next())
                all.add(fromResultSet(con, rs));


        } catch (SQLException e){
            throw new InternalServerErrorException(e);
        } finally {

            try {
                if(con != null && close)
                    con.close();
            } catch (SQLException e){
                throw new InternalServerErrorException(e);
            }

        }

        return all;

    }

    public static List<Variable> getAll(Connection con, int did, boolean close) throws InternalServerErrorException {

        List<Variable> all = new ArrayList<>();
        PreparedStatement prep;
        ResultSet rs;

        try {

            prep = con.prepareStatement("CALL VariablenEinerDisziplinAnzeigen(?)");
            prep.setInt(1, did);

            rs = prep.executeQuery();

            while(rs.next())
                all.add(fromResultSet(con, rs));

        } catch (SQLException e){
            throw new InternalServerErrorException(e);
        } finally {

            try {
                if(con != null && close)
                    con.close();
            } catch (SQLException e){
                throw new InternalServerErrorException(e);
            }

        }

        return all;

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

