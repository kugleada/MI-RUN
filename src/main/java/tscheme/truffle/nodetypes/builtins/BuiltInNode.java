package tscheme.truffle.nodetypes.builtins;

import tscheme.truffle.nodetypes.TSchemeNode;

import com.oracle.truffle.api.dsl.NodeChild;

@NodeChild(value = "arguments", type = TSchemeNode[].class)
public abstract class BuiltInNode extends TSchemeNode {
}
