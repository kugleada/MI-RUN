package datatypes;

import scheme_env.Environment;

import java.util.Objects;

public class SchemeSymbol extends Node {
    private String name;
    public SchemeSymbol(String name) {
        this.name = name;
    }

    @Override
    public Object eval(Environment env) {
        //return this.name;
        return env.getValue(this.name);
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SchemeSymbol that = (SchemeSymbol) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }
}
