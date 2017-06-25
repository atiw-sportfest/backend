package de.atiw.sportfest.backend.rules;

import java.util.ArrayList;

public class EvaluationParameters {

    private String expression;
    private ArrayList<String> parameters;
    private ArrayList<Class<?>> types;

    public EvaluationParameters(String expression, Variable ...vars){

        parameters = new ArrayList<>();
        types = new ArrayList<>();

        for(Variable var : vars){
            parameters.add(var.getExpressionParameter());
            types.add(var.getTyp().getTyp());
        }

        setExpression(expression);

    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public ArrayList<String> getParameters() {
        return parameters;
    }

    public void setParameters(ArrayList<String> parameters) {
        this.parameters = parameters;
    }

    public ArrayList<Class<?>> getTypes() {
        return types;
    }

    public void setTypes(ArrayList<Class<?>> types) {
        this.types = types;
    }

    @Override
    public int hashCode() {
        int result = 29401;
        result = 42641 * result + (expression != null ? expression.hashCode() : 0);
        result = 42641 * result + (parameters != null ? parameters.hashCode() : 0);
        result = 42641 * result + (types != null ? types.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EvaluationParameters object = (EvaluationParameters) o;

        if (expression != null ? !expression.equals(object.expression) : object.expression != null) return false;
        if (parameters != null ? !parameters.equals(object.parameters) : object.parameters != null) return false;
        return !(types != null ? !types.equals(object.types) : object.types != null);
    }

}

