package tscheme.truffle;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.source.Source;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import tscheme.truffle.helpers.TEnvironment;
import tscheme.truffle.helpers.TEnvironmentBuilder;
import tscheme.truffle.nodetypes.TSchemeNode;
import tscheme.truffle.nodetypes.TSchemeRootNode;
import tscheme.truffle.parser.Converter;
import tscheme.truffle.parser.Reader;
import tscheme.truffle.syntax.ListSyntax;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(Parameterized.class)
public class FactorialTest {


    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"(define factorial (lambda (n) (if (< n 1) 1 (* n (factorial (- n 1)))))) (factorial 1)", "1"},
                {"(define factorial (lambda (n) (if (< n 1) 1 (* n (factorial (- n 1)))))) (factorial 2)", "2"},
                {"(define factorial (lambda (n) (if (< n 1) 1 (* n (factorial (- n 1)))))) (factorial 3)", "6"},
                {"(define factorial (lambda (n) (if (< n 1) 1 (* n (factorial (- n 1)))))) (factorial 4)", "24"},
                {"(define factorial (lambda (n) (if (< n 1) 1 (* n (factorial (- n 1)))))) (factorial 5)", "120"},
                {"(define factorial (lambda (n) (if (< n 1) 1 (* n (factorial (- n 1)))))) (factorial 10)", "3628800"},
                {"(define factorial (lambda (n) (if (< n 1) 1 (* n (factorial (- n 1)))))) (factorial 15)", "1307674368000"},
        });
    }

    private String functionInput;

    private String expectedOutput;

    public FactorialTest(String input, String expected)
    {
        functionInput = input;
        expectedOutput = expected;
    }

    @Test
    public void testBuiltIns() throws Exception {

        Environment context = new Environment();

        TEnvironment environment = new TEnvironmentBuilder().createEnvironment();

        String sourceCode = functionInput;

        Source source = Source.newBuilder(sourceCode).name("<stdin>").mimeType(TSchemeLanguage.MIME_TYPE).build();

        ListSyntax synExpressions = Reader.read(source);

        Converter converter = new Converter();

        TSchemeNode[] nodes = converter.convertSexp(context, synExpressions, environment);

        TSchemeRootNode root = new TSchemeRootNode(nodes, context.getGlobalFrame().getFrameDescriptor());

        CallTarget ct = Truffle.getRuntime().createCallTarget(root);

        Object ret =  ct.call(context.getGlobalFrame());

        assertEquals(expectedOutput,ret.toString());
    }


}