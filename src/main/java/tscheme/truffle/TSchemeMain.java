package tscheme.truffle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.frame.MaterializedFrame;

import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.Truffle;

import tscheme.truffle.helpers.TEnvironment;
import tscheme.truffle.helpers.TEnvironmentBuilder;
import tscheme.truffle.nodetypes.TSchemeNode;
import tscheme.truffle.nodetypes.TSchemeRootNode;
import tscheme.truffle.parser.Converter;
import tscheme.truffle.parser.Reader;
import tscheme.truffle.syntax.ListSyntax;
import tscheme.truffle.datatypes.TSchemeList;

public class TSchemeMain {

    public static void main(String[] args) throws Exception {
        System.out.println("Running on: " + Truffle.getRuntime().getName());
        if(args.length > 0)
        {
            runTSchemeFromFile(args[0]);
        }
        else {
            startREPL();
        }
    }

    private static void startREPL() throws Exception {

        // Prepare input reader from stdin.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // Prepare global environment - storage for all variables and built-in functions.
        Environment globEnv = new Environment();

        // Start actual REPL.
        while (true) {

            // READ line.
            System.out.print("~> ");
            String sourceCode = br.readLine();

            // If nothing read, exit program.
            if (sourceCode == null) {
                // Handle EOF - Ctrl+D, Ctrl+C etc.
                System.out.println("Exiting Scheme interpreter ...");
                break;
            }

            TSchemeNode[] nodes = CodeProcessor.getASTNodes(sourceCode, globEnv);

            // EVALuation.
            Object result = createRootAndExecute(nodes, globEnv.getGlobalFrame());

            // PRINT results.
            if (result != TSchemeList.EMPTY) {
                System.out.println("Result: " + result);
            }
        }
    }

    private static void runTSchemeFromFile(String filename) throws Exception {

        Environment globEnv = new Environment();

        String sourceCode = CodeProcessor.getSourceCodeFromFile(filename);

        TSchemeNode[] nodes = CodeProcessor.getASTNodes(sourceCode, globEnv);

        Object result = createRootAndExecute(nodes, globEnv.getGlobalFrame());

        if (result != TSchemeList.EMPTY) {
            System.out.println(result);
        }
    }

    public static Object createRootAndExecute(TSchemeNode[] nodes, MaterializedFrame globalFrame) {
        TSchemeRootNode root = new TSchemeRootNode(nodes, globalFrame.getFrameDescriptor());

        CallTarget ct = Truffle.getRuntime().createCallTarget(root);

        Object ret =  ct.call(globalFrame);

        return ret;
    }
}
