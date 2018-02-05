package datatypes;

import scheme_env.Environment;

public class SchemeBoolean extends Node {
    public static final SchemeBoolean TRUE = new SchemeBoolean(Boolean.TRUE);
    public static final SchemeBoolean FALSE = new SchemeBoolean(Boolean.FALSE);

    private Boolean bool;
    public SchemeBoolean(Boolean bool) {
        this.bool = bool;
    }

    @Override
    public Object eval(Environment env) {
        return this.bool;
    }
}
