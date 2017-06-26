package de.atiw.sportfest.backend.rules;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

@XmlRootElement
public class Variable {

    @XmlElement
    private String name;

    @XmlElement
    private String desc;

    @XmlElement
    private String expressionParameter;

    @XmlElement
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

    public String getDesc() {
        return desc;
    }

    public String getExpressionParameter() {
        return expressionParameter;
    }

    public Typ getTyp() {
        return typ;
    }
}

