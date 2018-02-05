package datatypes;

import scheme_env.Environment;

public abstract class SchemeFunction extends SchemeNode {
    // other code...

    @Override
    public Object eval(Environment env) {
        return this;
    }

    public abstract Object apply(Object... args);
}
