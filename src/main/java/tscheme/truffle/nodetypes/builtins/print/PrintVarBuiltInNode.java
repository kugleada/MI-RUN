package tscheme.truffle.nodetypes.builtins.print;

import com.oracle.truffle.api.dsl.GenerateNodeFactory;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

import tscheme.truffle.nodetypes.builtins.BuiltInNode;

@NodeInfo(shortName = "printvar")
@GenerateNodeFactory
public abstract class PrintVarBuiltInNode extends BuiltInNode {

    @Specialization
    public long printvar(long value) {
        System.out.println(value);
        return value;
    }

    @Specialization
    public boolean printvar(boolean value) {
        System.out.println(value);
        return value;
    }

    @Specialization
    public double printvar(double value) {
        System.out.println(value);
        return value;
    }

    @Specialization
    public Object printvar(Object value) {
        System.out.println(value);
        return value;
    }
}
