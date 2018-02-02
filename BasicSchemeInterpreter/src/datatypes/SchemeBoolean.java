package datatypes;

import scheme_env.Environment;

public class SchemeBoolean extends Node {
    private boolean bool;
    public SchemeBoolean(boolean bool) {
        this.bool = bool;
    }

    @Override
    public Object eval(Environment env) {
        return this.bool;
    }
}
