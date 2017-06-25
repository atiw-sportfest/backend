package de.atiw.sportfest.backend.rules;

public class Variable {

    private String name, desc, expressionParameter;
    private Typ typ;

    public Variable() {
    }

    public Variable(String name, String desc, String expressionParameter, Typ typ) {
        this.name = name;
        this.desc = desc;
        this.expressionParameter = expressionParameter;
        this.typ = typ;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getExpressionParameter() {
        return expressionParameter;
    }

    public void setExpressionParameter(String expressionParameter) {
        this.expressionParameter = expressionParameter;
    }

    public Typ getTyp() {
        return typ;
    }

    public void setTyp(Typ typ) {
        this.typ = typ;
    }
}

