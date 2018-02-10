package cz.schemeinterpreter.scheme_env;

import java.util.HashMap;


public class Environment {

    private final HashMap<String, Object> env = new HashMap<String, Object>();

    private final Environment parent;
    public Environment() {
        this(null);
    }

    public Environment(Environment parent) {
        this.parent = parent;
    }

    public Object getValue(String name) {
        if (this.env.containsKey(name)) {
            return this.env.get(name);
        } else if (this.parent != null) {
            return this.parent.getValue(name);
        } else {
            throw new SchemeException("No variable: " + name);
        }
    }

    public void putValue(String name, Object value) {
        this.env.put(name, value);
    }

    public static Environment getBaseEnvironment() {
        Environment env = new Environment();
        env.putValue("+", BuiltInFunction.PLUS);
        env.putValue("-", BuiltInFunction.MINUS);
        env.putValue("*", BuiltInFunction.MULT);
        env.putValue("/", BuiltInFunction.DIV);
        env.putValue("=", BuiltInFunction.EQUALS);
        env.putValue("<", BuiltInFunction.LESS_THAN);
        env.putValue(">", BuiltInFunction.GREATER_THAN);
        //env.putValue("list", BuiltInFunction.LIST);
        env.putValue("car", BuiltInFunction.CAR);
        env.putValue("cdr", BuiltInFunction.CDR);
        env.putValue("println", BuiltInFunction.PRINTLN);
        return env;
    }
}
