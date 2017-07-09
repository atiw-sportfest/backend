package de.atiw.sportfest.backend.rules;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.codehaus.commons.compiler.CompileException;

import org.codehaus.janino.ExpressionEvaluator;

public enum RegelEvaluator {
    instance;


    private static Logger logger = Logger.getLogger("RegelEvaluator");

    private static final String[] paramsAT= new String[0];
    private static final Class<?>[] typesAT = new Class<?>[0];
    private static final Object[] valsAT = new Object[0];

    private Map<EvaluationParameters, ExpressionEvaluator> eeCache = new HashMap<>();

    public boolean evaluate(EvaluationParameters params, List<Object> values) throws CompileException, InvocationTargetException {

        ExpressionEvaluator ee;
        String expression = params.getExpression();

        for(String param : params.getParameters())
            logger.info("Param: " + param);

        for(Object value : values)
            logger.info("Value: " + value);

        if( (ee = eeCache.get(params)) == null){

            logger.info(String.format("Cache miss for \"%s\"", expression));

            ee = new ExpressionEvaluator();

            ee.setParameters(params.getParameters().toArray(paramsAT), params.getTypes().toArray(typesAT));
            ee.setExpressionType(boolean.class);

            ee.cook(expression);

            eeCache.put(params, ee);

        }

        return (boolean) ee.evaluate(values.toArray(valsAT));
    }

}

