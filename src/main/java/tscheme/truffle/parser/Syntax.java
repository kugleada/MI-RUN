package tscheme.truffle.parser;

import com.oracle.truffle.api.source.SourceSection;

/**
 * All datatypes that can be written literally (e.g., numbers, lists, symbols)
 * should implement this interface so the source location can be retrieved and
 * fed to Truffle.
 */
public abstract class Syntax<Datatype> {
    private final Datatype value;
    private final SourceSection sourceSection;
    /**
     * This syntax object was used in a define invocations with the give name.
     */
    private String name;

    public Syntax(Datatype value, SourceSection sourceSection) {
        this.value = value;
        this.sourceSection = sourceSection;
    }

    /**
     * Removes this object's outer-most syntax information. To recursively
     * remove all syntax information invocations {@link #strip()}.
     *
     * @return The value with no syntax information.
     */
    public Datatype getValue() {
        return this.value;
    }

    /**
     * @return The location in the source code where this syntax object is
     *         located.
     */
    public SourceSection getSourceSection() {
        return this.sourceSection;
    }

    /**
     * @return The name if this syntax object was in a define invocations. null otherwise.
     */
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Recursively removes the syntax information and returns the bare value. To
     * just strip of the outer-most syntax information, invocations {@link #getValue()}
     * .
     *
     * @return The literals value with no syntax information
     */
    public Object strip() {
        // Override for compound datatypes.
        return this.value;
    }

    @Override
    public String toString() {
        return "#'" + this.value;
    }
}
