package de.atiw.sportfest.backend.rules;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import javax.ws.rs.BadRequestException;
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

    @XmlElement
    private Integer sortIndex;

    public static final Variable Geschlecht = new Variable("", "", "geschlecht",
            new Typ("Geschlecht", "", String.class,
                new Zustand("", "", "m"), new Zustand("", "", "w")));

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

    @XmlTransient
    public Integer getVarId(){
        return var_id;
    }

    public static Variable getOne(Connection con, int vid, boolean close) throws SQLException, NotFoundException {

        Variable var = null;
        ResultSet rs;
        PreparedStatement prep;

        try {

            prep = con.prepareStatement("CALL VariableAnzeigen(?)");
            prep.setInt(1, vid);

            rs = prep.executeQuery();

            if(rs.next())
                var = fromResultSet(con, rs);

            if(var == null)
                throw new NotFoundException(String.format("Variable mit ID \"%d\" nicht gefunden!", vid));

            return var;

        } finally { if(close) con.close(); }

    }

    public static Variable getOne(Connection con, String vid, boolean close) throws SQLException, NotFoundException {
        return getOne(con, Integer.parseInt(vid), close);
    }

    public static Variable getOne(Connection con, String vid) throws SQLException, NotFoundException {
        return getOne(con, vid, true);
    }

    public static List<Variable> getAll(Connection con, boolean close) throws SQLException {
            return getAll(con, con.prepareStatement("CALL VariablenAnzeigen()"), close);
    }

    public static List<Variable> getAll(Connection con) throws SQLException {
        return getAll(con, true);
    }

    public static List<Variable> getAll(Connection con, int did, boolean close) throws SQLException {

            PreparedStatement prep = con.prepareStatement("CALL VariablenEinerDisziplinAnzeigen(?)");
            prep.setInt(1, did);

            return getAll(con, prep, close);
    }

    public static List<Variable> getAll(Connection con, PreparedStatement prep, boolean close) throws SQLException {

        List<Variable> all = new ArrayList<>();
        ResultSet rs;

        try {

            rs = prep.executeQuery();

            while(rs.next())
                all.add(fromResultSet(con, rs));

            return all;

        } finally { if(close) con.close(); }

    }

    public static List<Variable> createOrEdit(Connection con, int did, List<Variable> vars, boolean close) throws SQLException, InternalServerErrorException {

        List<Variable> diff, coe;
        List<Integer> coeIds;
        Variable coeV;

        try {

            // coe - createOrEdit
            coe = new ArrayList<>();
            coeIds = new ArrayList<>();

            // pre-fill diff with existing rules
            diff = getAll(con, did, false);

            for(Variable var : vars) {

                coeV = createOrEdit(con, did, var, false);

                coe.add(coeV);
                coeIds.add(coeV.var_id);

            }

            diff.removeAll(coe);

            // Variablen die nicht mehr vorhanden sind
            // und auch nicht verändert wurden löschen

            for(Variable var : diff){
                if(!coeIds.contains(var.var_id))
                    delete(con, var.var_id, false);
            }

            return coe;

        } finally { if(close) con.close(); }

    }

    public static Variable createOrEdit(Connection con, int disz_id, Variable var, boolean close) throws SQLException, InternalServerErrorException {

        Variable orig;

        try {

            if(var.var_id == null)
                return create(con, disz_id, var, false);
            else if ( (orig = getOne(con, var.var_id, false)) != null)
                return edit(con, var, orig, false);
            else
                return create(con, disz_id, var, false);

        } finally { if(close) con.close(); }

    }

    public static Variable create(Connection con, int disz_id, Variable var, boolean close) throws SQLException, InternalServerErrorException {
        return create(con, disz_id, Arrays.asList(new Variable[]{ var }), close).get(0);
    }

    public static List<Variable> create(Connection con, int disz_id, List<Variable> vars, boolean close) throws SQLException, InternalServerErrorException {

        List<Variable> ret = new ArrayList<>();
        PreparedStatement prep;
        ResultSet rs;
        int i = 1;

        try {

            prep = con.prepareStatement("CALL VariableAnlegen(?, ?, ?, ?, ?, ?)"); // var_name, var_descr, var_exprParam, typ_id, disz_id, var_sortIndex

            for(Variable var : vars){

                i = 1;

                prep.setString(i++, var.name);
                prep.setString(i++, var.desc);
                prep.setString(i++, var.expressionParameter);

                prep.setInt(i++, var.typ.getTypID());
                prep.setInt(i++, disz_id);

                prep.setInt(i++, var.sortIndex);

                rs = prep.executeQuery();

                if(rs.next())
                    ret.add(fromResultSet(con, rs));
                else
                    throw new InternalServerErrorException("Die Datenbank hat das erstellte Objekt nicht zurückgegeben ?!");

            }

            return ret;

        } finally { if(close) con.close(); }
    
    }

    public static Variable create(Connection con, int disz_id, Variable var) throws SQLException, InternalServerErrorException {
        return create(con, disz_id, var, true);
    }

    public static Variable edit(Connection con, Variable var, Variable orig, boolean close) throws SQLException, NotFoundException {

        PreparedStatement prep;
        ResultSet rs;
        int i = 1;

        try {

            prep = con.prepareStatement("CALL VariableBearbeiten(?, ?, ?, ?, ?, ?)"); // var_id, var_name, var_descr, var_exprParam, typ_id, var_sortIndex

            prep.setInt(i++, orig.var_id);;

            prep.setString(i++, var.name != null ? var.name : orig.name);
            prep.setString(i++, var.desc != null ? var.desc : orig.desc);
            prep.setString(i++, var.expressionParameter != null ? var.expressionParameter : orig.expressionParameter);

            prep.setInt(i++, var.typ != null ? var.typ.getTypID() : orig.typ.getTypID());

            prep.setNull(i++, var.sortIndex);

            rs = prep.executeQuery();

            if(rs.next())
                return fromResultSet(con, rs);
            else
                throw new InternalServerErrorException("Die Datenbank hat das bearbeitete Objekt nicht zurückgeliefert ?!");
        } finally { if(close) con.close(); }

    }

    public static Variable edit(Connection con, String vid, Variable var) throws SQLException, NotFoundException {

        var.var_id = Integer.parseInt(vid);

        return edit(con, var, getOne(con, vid, false), true);
    }

    public static void delete(Connection con, int vid) throws SQLException {
        delete(con, vid, true);
    }

    public static void delete(Connection con, int vid, boolean close) throws SQLException {
        delete(con, Arrays.asList(new Integer[]{ vid }), close);
    }

    public static void delete(Connection con, List<Integer> var_ids, boolean close) throws SQLException {
        PreparedStatement prep;
        ResultSet rs;
        int i = 1;

        try {

            prep = con.prepareStatement("CALL VariableEntfernen(?)"); // var_id

            for(int var_id : var_ids){

                prep.setInt(i++, var_id);
                prep.execute();

                i = 1;
            }

        } finally { if(close) con.close(); }
    }

    public static void deleteVars(Connection con, List<Variable> vars, boolean close) throws SQLException {
        delete(con, vars.stream().map((v) -> v.var_id).collect(Collectors.toList()), close);
    }

    public static List<Variable> deleteDisziplin(Connection con, int disz_id, boolean close) throws SQLException {

        PreparedStatement prep = con.prepareStatement("CALL VariablenEinerDisziplinEntfernen(?)"); // disz_id
        prep.setInt(1, disz_id);

        return getAll(con, prep, close);
    }

    private static Variable fromResultSet(Connection con, ResultSet rs) throws SQLException, InternalServerErrorException {

        Variable var = new Variable();
        int i = 1, typ_id;

        var.var_id = rs.getInt(i++);
        var.name =  rs.getString(i++);
        var.desc = rs.getString(i++);
        var.expressionParameter = rs.getString(i++);
        var.typ = Typ.getOne(con, rs.getInt(i++), false);
        i++; // disz_id
        var.sortIndex = rs.getInt(i++);

        return var;

    }

    public static int indexCompare(Variable v1, Variable v2) {

        int result;

        if(v1.sortIndex != null && v2.sortIndex != null)
            result = v2.sortIndex - v1.sortIndex;
        else
            return 0; // no sort index, vars are equal.

        return result;

    }

}

