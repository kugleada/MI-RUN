package tscheme.truffle.nodetypes.builtins.helpers;

import tscheme.truffle.nodetypes.builtins.BuiltInNode;
import tscheme.truffle.datatypes.TSchemeList;

import com.oracle.truffle.api.dsl.GenerateNodeFactory;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

/**
 * Built-in function for cdr operation on list, returns values on the right of left-most nodetypes ("right" child of first nodetypes)
 */
@NodeInfo(shortName="cdr")
@GenerateNodeFactory
public abstract class CdrBuiltInNode extends BuiltInNode {

    @Specialization
    protected TSchemeList<?> cdr(TSchemeList<?> list) {
        return list.cdr();
    }
}
