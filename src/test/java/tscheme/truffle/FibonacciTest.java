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
import static tscheme.truffle.TSchemeMain.createRootAndExecute;

@RunWith(Parameterized.class)
public class FibonacciTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"(define fibon (lambda (n) (if (< n 2) 1 (+ (fibon (- n 1)) (fibon (- n 2)))))) (fibon 1)", "1"},
                {"(define fibon (lambda (n) (if (< n 2) 1 (+ (fibon (- n 1)) (fibon (- n 2)))))) (fibon 2)", "2"},
                {"(define fibon (lambda (n) (if (< n 2) 1 (+ (fibon (- n 1)) (fibon (- n 2)))))) (fibon 3)", "3"},
                {"(define fibon (lambda (n) (if (< n 2) 1 (+ (fibon (- n 1)) (fibon (- n 2)))))) (fibon 4)", "5"},
                {"(define fibon (lambda (n) (if (< n 2) 1 (+ (fibon (- n 1)) (fibon (- n 2)))))) (fibon 5)", "8"},
                {"(define fibon (lambda (n) (if (< n 2) 1 (+ (fibon (- n 1)) (fibon (- n 2)))))) (fibon 6)", "13"},
                {"(define fibon (lambda (n) (if (< n 2) 1 (+ (fibon (- n 1)) (fibon (- n 2)))))) (fibon 7)", "21"},
        });
    }

    private String functionInput;

    private String expectedOutput;

    public FibonacciTest(String input, String expected)
    {
        functionInput = input;
        expectedOutput = expected;
    }

    @Test
    public void testBuiltIns() throws Exception {

        String sourceCode = functionInput;

        Environment globEnv = new Environment();

        TSchemeNode[] nodes = CodeProcessor.getASTNodes(sourceCode, globEnv);

        Object ret = createRootAndExecute(nodes, globEnv.getGlobalFrame());

        assertEquals(expectedOutput,ret.toString());
    }
}