package datatypes;

import scheme_env.Environment;

public class SchemeFunction extends Node {
    // other code...

    @Override
    public Object eval(Environment env) {
        return this;
    }
}
