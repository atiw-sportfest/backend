package de.atiw.sportfest.backend.rules;

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

}

