package tscheme.truffle;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import tscheme.truffle.nodetypes.TSchemeNode;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tscheme.truffle.TSchemeMain.createRootAndExecute;

@RunWith(Parameterized.class)
public class FibonacciTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"(define fibon (lambda (n) (if (< n 3) 1 (+ (fibon (- n 1)) (fibon (- n 2)))))) (fibon 1)", "1"},
                {"(define fibon (lambda (n) (if (< n 3) 1 (+ (fibon (- n 1)) (fibon (- n 2)))))) (fibon 2)", "1"},
                {"(define fibon (lambda (n) (if (< n 3) 1 (+ (fibon (- n 1)) (fibon (- n 2)))))) (fibon 3)", "2"},
                {"(define fibon (lambda (n) (if (< n 3) 1 (+ (fibon (- n 1)) (fibon (- n 2)))))) (fibon 4)", "3"},
                {"(define fibon (lambda (n) (if (< n 3) 1 (+ (fibon (- n 1)) (fibon (- n 2)))))) (fibon 5)", "5"},
                {"(define fibon (lambda (n) (if (< n 3) 1 (+ (fibon (- n 1)) (fibon (- n 2)))))) (fibon 6)", "8"},
                {"(define fibon (lambda (n) (if (< n 3) 1 (+ (fibon (- n 1)) (fibon (- n 2)))))) (fibon 7)", "13"},
                {"(define fibon (lambda (n) (if (< n 3) 1 (+ (fibon (- n 1)) (fibon (- n 2)))))) (fibon 8)", "21"},
                {"(define fibon (lambda (n) (if (< n 3) 1 (+ (fibon (- n 1)) (fibon (- n 2)))))) (fibon 9)", "34"},
                {"(define fibon (lambda (n) (if (< n 3) 1 (+ (fibon (- n 1)) (fibon (- n 2)))))) (fibon 10)", "55"},
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