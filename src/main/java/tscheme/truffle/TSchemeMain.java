package tscheme.truffle;

import java.io.BufferedReader;
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
        startREPL();
    }

    private static void startREPL() throws Exception {

        // Prepare input reader from stdin.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // Prepare global environment - storage for all variables and built-in functions.
        Environment context = new Environment();

        TEnvironment environment = new TEnvironmentBuilder().createEnvironment();

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

            // Create representation of source code.
            Source source = Source.newBuilder(sourceCode).name("<stdin>").mimeType(TSchemeLanguage.MIME_TYPE).build();

            // Read input - contains lexing, parsing, etc.
            ListSyntax synExpressions = Reader.read(source);
            System.out.println("ListSyntax: " + synExpressions);

            Converter converter = new Converter(false);

            TSchemeNode[] nodes = converter.convertSexp(context, synExpressions, environment);


            System.out.println("Nodes read:");
            for (TSchemeNode a : nodes) {
                System.out.println(a.getClass());
            }

            // EVAL
            Object result = execute(nodes, context.getGlobalFrame());

            // PRINT
            if (result != TSchemeList.EMPTY) {
                System.out.println(result);
            }
        }
    }

    private static void runMumbler(String filename) throws Exception {
        /*Source source = Source.newBuilder(filename).name("<file>").mimeType(TSchemeLanguage.MIME_TYPE).build();
        Environment context = new Environment();
        ListSyntax sexp = Reader.read(source);
        Converter converter = new Converter(true); // true - tail optimization enabled
        TSchemeNode[] nodes = converter.convertSexp(context, sexp);

        execute(nodes, context.getGlobalFrame());*/
    }

    private static Object execute(TSchemeNode[] nodes, MaterializedFrame globalFrame) {

        System.out.println("To execute: ");
        System.out.println(nodes);

        System.out.println("Global frame:");
        System.out.println(globalFrame);

        System.out.println("Creating root nodetypes ...");
        TSchemeRootNode root = new TSchemeRootNode(nodes, globalFrame.getFrameDescriptor());
        System.out.println("Root nodetypes created ...");

        CallTarget ct = Truffle.getRuntime().createCallTarget(root);


        Object ret =  ct.call(globalFrame);

        System.out.println(ret.getClass());
        System.out.println(ret);

        return ret;

    }
}
