package io.descoped.client.api.test.storage;

import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.jexl3.MapContext;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Variables {

    private static final String REGEX_EXPR = "\\$\\{([^}]+)}";

    private final MapContext jexlContext;
    private final JexlEngine jexl;
    private final Map<String,Object> variables;
    private final Pattern regex;


    public Variables() {
        regex = Pattern.compile(REGEX_EXPR);
        variables = new LinkedHashMap<>();
        jexl = new JexlBuilder().cache(512).strict(true).silent(false).create();
        jexlContext = new MapContext(variables);
    }

    public Map<String, Object> map() {
        return variables;
    }

    public void add(String key, Object value) {
        variables.put(key, value);
    }

    public Object evaluateExpression(String expr) {
        expr = (isExpression(expr) ? getExpression(expr) : expr);
        JexlExpression e = jexl.createExpression(expr);
        return e.evaluate(jexlContext);
    }

    public boolean isExpression(String expr) {
        Matcher m = regex.matcher(expr);
        return m.find();
    }

    public String getExpression(String expr) {
        Matcher m = regex.matcher(expr);
        if (m.find()) {
            return m.group(1);
        };
        return expr;
    }

    public String evaluate(String expr) {
        Pattern regex = Pattern.compile("\\$\\{(.*?)}+");
        Matcher m = regex.matcher(expr);

        StringBuffer buf = new StringBuffer();
        int last = 0;
        while (m.find()) {
            for(int n=0; n<m.groupCount(); n++) {
                String group = m.group(n);
                int current = m.start();
                String prev = expr.substring(last, current);
                buf.append(prev);
                buf.append(evaluateExpression(group));
                last = m.end();
            }
        };
        return buf.toString();
    }


}
