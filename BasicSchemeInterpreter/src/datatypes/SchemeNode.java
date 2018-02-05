package datatypes;

import scheme_env.Environment;

public abstract class SchemeNode {
    public abstract Object eval(Environment env);
}
