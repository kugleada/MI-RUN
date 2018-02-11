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
            //System.out.println(synExpressions);

            Converter converter = new Converter();

            TSchemeNode[] nodes = converter.convertSexp(context, synExpressions, environment);

            //System.out.println("Nodes size:" + nodes.length);
            //System.out.println(nodes[0].toString());
            //System.out.println(nodes[0].getClass());
            // EVAL
            Object result = execute(nodes, context.getGlobalFrame());
            // PRINT
            if (result != TSchemeList.EMPTY) {
                System.out.println("Result: " + result);
            }
        }
    }

    private static void runTSchemeFromFile(String filename) throws Exception {
        String sourceCode = "";
        BufferedReader br = new BufferedReader(new FileReader(filename));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            sourceCode = sb.toString();
        } finally {
            br.close();
        }

        Source source = Source.newBuilder(sourceCode).name("<file>").mimeType(TSchemeLanguage.MIME_TYPE).build();

        Environment context = new Environment();
        TEnvironment env = new TEnvironmentBuilder().createEnvironment();

        ListSyntax sexp = Reader.read(source);
        System.out.println(sexp);
        Converter converter = new Converter(); // true - tail optimization enabled

        TSchemeNode[] nodes = converter.convertSexp(context, sexp, env);

        Object result = execute(nodes, context.getGlobalFrame());

        if (result != TSchemeList.EMPTY) {
            System.out.println(result);
        }
    }

    private static Object execute(TSchemeNode[] nodes, MaterializedFrame globalFrame) {
        TSchemeRootNode root = new TSchemeRootNode(nodes, globalFrame.getFrameDescriptor());

        CallTarget ct = Truffle.getRuntime().createCallTarget(root);

        Object ret =  ct.call(globalFrame);

        //System.out.println(ret.getClass());
        //System.out.println(ret);

        return ret;

    }
}
