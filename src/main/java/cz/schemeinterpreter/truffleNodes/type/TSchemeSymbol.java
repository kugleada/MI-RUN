package cz.schemeinterpreter.truffleNodes.type;

import com.oracle.truffle.api.frame.VirtualFrame;
import cz.schemeinterpreter.truffleNodes.node.TSchemeNode;

import java.util.Objects;

public class TSchemeSymbol extends TSchemeNode {
    public final String name;

    public TSchemeSymbol(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public Object execute(VirtualFrame virtualFrame) {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TSchemeSymbol that = (TSchemeSymbol) o;
        return Objects.equals(name, that.name);
    }
}
