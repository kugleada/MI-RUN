package tscheme.truffle.nodetypes.builtins;

import tscheme.truffle.nodetypes.TSchemeNode;

import com.oracle.truffle.api.dsl.NodeChild;

/**
 * Root for Built-in nodes.
 */
@NodeChild(value = "arguments", type = TSchemeNode[].class)
public abstract class BuiltInNode extends TSchemeNode {
}
