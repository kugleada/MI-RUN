package tscheme.truffle.datatypes;

/**
 * Represents symbol.
 */
public class TSchemeSymbol {

    public final String name; // name of this symbol (define, if, ...)

    /**
     * Constructor for Symbol.
     * @param name Symbol name.
     */
    public TSchemeSymbol(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
