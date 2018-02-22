package io.descoped.client.api.test.storage;

import java.util.LinkedHashMap;
import java.util.Objects;

public class ELMap<K,V> extends LinkedHashMap<K,V> {

    private final Variables variables;

    public ELMap(Variables variables) {
        super();
        Objects.requireNonNull(variables);
        this.variables = variables;
    }

    public String eval(String key) {
        String elKey = (variables.isExpression(key) ? variables.evaluate(key) : key);
        return (String) this.get(elKey);
    }
}
