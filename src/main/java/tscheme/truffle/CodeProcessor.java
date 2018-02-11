package tscheme.truffle;

import com.oracle.truffle.api.source.Source;
import tscheme.truffle.nodetypes.TSchemeNode;
import tscheme.truffle.parser.Converter;
import tscheme.truffle.parser.Reader;
import tscheme.truffle.syntax.ListSyntax;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Helper function for operating with code.
 */
public class CodeProcessor {

    /**
     * Wrapper around whole code processing.
     * @param sourceCode Source code, as String.
     * @param env Environment to write into.
     * @return Array of AST nodes.
     * @throws Exception If anything fails during code processing.
     */
    public static TSchemeNode[] getASTNodes(String sourceCode, Environment env) throws Exception {
        // Create representation of source code.
        Source source = Source.newBuilder(sourceCode).name("<stdin>").mimeType(TSchemeLanguage.MIME_TYPE).build();

        // Read input - contains lexing, parsing, etc.
        ListSyntax synExpressions = Reader.read(source);

        //System.out.println(synExpressions);

        // Converts Syntax into AST.
        Converter converter = new Converter();

        // Get AST nodes
        TSchemeNode[] nodes = converter.convertSyntaxToAST(env, synExpressions, null);

        return nodes;
    }

    /**
     * Simply reads a text file and returns the content.
     * @param filename Which file to open.
     * @return Content of file as String.
     * @throws IOException File or Stream errors.
     */
    public static String getSourceCodeFromFile(String filename) throws IOException {
        String sourceCode = "";
        // Open file in stream.
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
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            br.close();
        }

        return sourceCode;
    }
}
