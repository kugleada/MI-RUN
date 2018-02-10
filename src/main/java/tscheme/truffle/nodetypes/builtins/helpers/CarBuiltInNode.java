package tscheme.truffle.nodetypes.builtins.helpers;

import tscheme.truffle.nodetypes.builtins.BuiltInNode;
import tscheme.truffle.datatypes.TSchemeList;

import com.oracle.truffle.api.dsl.GenerateNodeFactory;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

/**
 * Built-in function for car operation on list, returns left-most value ("left" child of first nodetypes)
 */
@NodeInfo(shortName="car")
@GenerateNodeFactory
public abstract class CarBuiltInNode extends BuiltInNode {

    @Specialization
    protected Object car(TSchemeList<?> list) {
        return list.car();
    }
}
