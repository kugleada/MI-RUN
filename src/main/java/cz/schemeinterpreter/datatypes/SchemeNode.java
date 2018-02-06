package cz.schemeinterpreter.datatypes;

import cz.schemeinterpreter.scheme_env.Environment;

public abstract class SchemeNode {
    public abstract Object eval(Environment env);
}
