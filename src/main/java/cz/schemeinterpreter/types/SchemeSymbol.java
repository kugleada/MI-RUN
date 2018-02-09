package cz.schemeinterpreter.types;

public class SchemeSymbol {
    public final String name;

    public SchemeSymbol(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "'" + this.name;
    }
}
