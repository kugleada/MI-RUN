package tscheme.truffle.nodetypes.builtins.helpers;

import tscheme.truffle.nodetypes.builtins.BuiltInNode;
import tscheme.truffle.datatypes.TSchemeList;

import com.oracle.truffle.api.dsl.GenerateNodeFactory;
import com.oracle.truffle.api.dsl.Specialization;

@GenerateNodeFactory
public abstract class ListBuiltInNode extends BuiltInNode {

    @Specialization
    protected TSchemeList<Object> list(Object first, Object second) {
        return TSchemeList.list(first, second);
    }
}
