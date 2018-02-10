package cz.schemeinterpreter;

import com.oracle.truffle.api.Truffle;
import cz.schemeinterpreter.datatypes.SchemeNode;
import cz.schemeinterpreter.datatypes.SchemeList;
import cz.schemeinterpreter.reader.Reader;
import cz.schemeinterpreter.scheme_env.Environment;

import java.io.*;

/**
 * Main class, contains main function to run the interpreter.
 */
public class BasicSchemeMain {


        public static void main(String[] args) throws IOException {

            assert args.length < 2 : "BasicScheme only accepts 1 or 0 files";
            if (args.length == 0) { // no arguments, start REPL
                startREPL();
            } else if (args.length == 2) {
                startTruffleREPL();
            }
            else if (args.length == 1) {
                runScheme(args[0]); // open file and interpret given code
            }
        }

    /**
     * Starts Read-Eval-Print Loop.
     * @throws IOException
     */
    private static void startREPL() throws IOException {
            System.out.println("SCHEME interpreter. To Exit, press Ctrl+D.");
            System.out.println("Running on: " + Truffle.getRuntime().getName());

            // initialize environment
            Environment globalEnv = Environment.getBaseEnvironment();

            // initialize reader from command line
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            // Do while (CTRL+D) is not received...
            while (true) {

                // READ line by line, press Enter to submit a line
                System.out.print("~> ");

                // Read line
                String data = br.readLine();

                // If nothing, exit interpreter, catching e.g. CTRL+D
                if (data == null) {
                    // EOF sent
                    System.out.println("Exiting SCHEME interpreter ...");
                    break;
                }

                // Create nodes from given code
                SchemeList nodes = Reader.read(
                        new ByteArrayInputStream(data.getBytes()));

                // EVALuate all nodes
                Object result = SchemeList.EMPTY;
                for (SchemeNode node : nodes) {
                    System.out.println(node);
                    result = node.eval(globalEnv);
                }

                // PRINT results of evaluation
                if (result != SchemeList.EMPTY) {
                    System.out.println(result);
                }
            }
        }

    /**
     * Starts Read-Eval-Print Loop.
     * @throws IOException
     */
    private static void startTruffleREPL() throws IOException {
        System.out.println("SCHEME interpreter. To Exit, press Ctrl+D.");
        System.out.println("Running on: " + Truffle.getRuntime().getName());

        // initialize reader from command line
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            // READ line by line, press Enter to submit a line
            System.out.print("~> ");

            // Read line
            String data = br.readLine();
            if (data == null) {
                // Ctrl+D etc.
                break;
            }

        }

    }


    /**
     * Reads provided file with SCHEME code and executes.
     * @param filename Name of file with SCHEME code.
     * @throws IOException
     */
    private static void runScheme(String filename) throws IOException {
            Environment topEnv = Environment.getBaseEnvironment();

            SchemeList nodes = Reader.read(new FileInputStream(filename));
            for (SchemeNode node : nodes) {
                node.eval(topEnv);
            }
        }
}

