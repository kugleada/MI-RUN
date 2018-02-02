package datatypes;

import scheme_env.Environment;

public class SchemeSymbol extends Node {
    private String name;
    public SchemeSymbol(String name) {
        this.name = name;
    }

    @Override
    public Object eval(Environment env) {
        return env.getValue(this.name);
    }
}
