package de.atiw.sportfest.backend.rules;

import java.lang.reflect.InvocationTargetException;

import org.codehaus.commons.compiler.CompileException;

public class Regel {

    private String expression;
    private int points;
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

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Regel getNext() {
        return next;
    }

    public void setNext(Regel next) {
        this.next = next;
    }

    public int evaluate(Variable[] vars, Object[] values) throws CompileException, InvocationTargetException {
       return RegelEvaluator.instance.evaluate(new EvaluationParameters(expression, vars), values) ? points : next != null ? next.evaluate(vars, values) : 0;
    }

}

