package tscheme.truffle.nodetypes.builtins;

import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.dsl.NodeFactory;
import com.oracle.truffle.api.frame.FrameDescriptor;

import tscheme.truffle.datatypes.TSchemeFunction;
import tscheme.truffle.nodetypes.TSchemeNode;
import tscheme.truffle.nodetypes.TSchemeRootNode;
import tscheme.truffle.nodetypes.builtins.basicop.AddBuiltInNodeFactory;
import tscheme.truffle.nodetypes.builtins.basicop.DivBuiltInNodeFactory;
import tscheme.truffle.nodetypes.builtins.basicop.ModBuiltInNodeFactory;
import tscheme.truffle.nodetypes.builtins.basicop.MulBuiltInNodeFactory;
import tscheme.truffle.nodetypes.builtins.basicop.SubBuiltInNodeFactory;
import tscheme.truffle.nodetypes.builtins.helpers.CarBuiltInNodeFactory;
import tscheme.truffle.nodetypes.builtins.helpers.CdrBuiltInNodeFactory;
import tscheme.truffle.nodetypes.builtins.print.PrintVarBuiltInNodeFactory;
import tscheme.truffle.nodetypes.builtins.relationop.EqualBuiltInNodeFactory;
import tscheme.truffle.nodetypes.builtins.relationop.GreaterThanBuiltInNodeFactory;
import tscheme.truffle.nodetypes.builtins.relationop.LessThanBuiltInNodeFactory;
import tscheme.truffle.nodetypes.read.ReadArgumentNode;

import java.util.HashMap;

/**
 * Helper class for gathering Built-In functions.
 */
public class BuiltInCreator {

    /**
     * Creates instance of builtin function.
     * @param nodeFactory Node factory for generating built-in nodes.
     * @return Return Scheme function.
     */
    public static TSchemeFunction createSchemeBuiltinFunction(
            NodeFactory<? extends BuiltInNode> nodeFactory) {

        // amount of arguments of this function
        int amountOfArguments = nodeFactory.getExecutionSignature().size();

        // nodes for given arguments
        TSchemeNode[] argumentNodes = new TSchemeNode[amountOfArguments];

        // fill in argument nodes
        for (int i=0; i < amountOfArguments; i++) {
            argumentNodes[i] = new ReadArgumentNode(i);
        }

        // create Built-in node
        BuiltInNode newBuiltinNode = nodeFactory.createNode((Object) argumentNodes);
        // create root of new function
        TSchemeRootNode rootNode = new TSchemeRootNode(new TSchemeNode[] {newBuiltinNode}, new FrameDescriptor());
        // call target, so we can directly call it
        RootCallTarget rct = Truffle.getRuntime().createCallTarget(rootNode);
        return new TSchemeFunction(rct);
    }

    /**
     * Create and return all Built-in functions as a map.
     * @return HashMap<String, TSchemeFunction> Map of all Built-in functions.
     */
    public static HashMap<String, TSchemeFunction> getAllBuiltIns ()
    {
        // storage for functions
        HashMap<String, TSchemeFunction> functions = new HashMap<String, TSchemeFunction>();

        // output - print variable
        functions.put("printvar", createSchemeBuiltinFunction(PrintVarBuiltInNodeFactory.getInstance()));

        // arithmetic operators
        functions.put("+",createSchemeBuiltinFunction(AddBuiltInNodeFactory.getInstance()));
        functions.put("-",createSchemeBuiltinFunction(SubBuiltInNodeFactory.getInstance()));
        functions.put("*",createSchemeBuiltinFunction(MulBuiltInNodeFactory.getInstance()));
        functions.put("/",createSchemeBuiltinFunction(DivBuiltInNodeFactory.getInstance()));
        functions.put("%",createSchemeBuiltinFunction(ModBuiltInNodeFactory.getInstance()));

        // relational operators - >, <, =
        functions.put("=", createSchemeBuiltinFunction(EqualBuiltInNodeFactory.getInstance()));
        functions.put("<", createSchemeBuiltinFunction(LessThanBuiltInNodeFactory.getInstance()));
        functions.put(">", createSchemeBuiltinFunction(GreaterThanBuiltInNodeFactory.getInstance()));

        // helpers - list operators etc.
        functions.put("car", createSchemeBuiltinFunction(CarBuiltInNodeFactory.getInstance()));
        functions.put("cdr", createSchemeBuiltinFunction(CdrBuiltInNodeFactory.getInstance()));

        return functions;
    }


}
