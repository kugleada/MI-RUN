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
                /*
                {"(define fast-factorial-aux (lambda (N A) (if (= N 1) A (fast-factorial-aux (- N 1) (* N A)))))" +
                "(define fast-factorial (lambda (N) (fast-factorial-aux N 1)))"
                        "(fast-factorial 5)"
                */
                //(define loop (lambda (n a) (if (= a n) a (loop n (+ a 1)))))
        });
    }

    private String functionInput;

    private String expectedOutput;

    public FactorialTest(String input, String expected) {
        functionInput = input;
        expectedOutput = expected;
    }

    @Test
    public void testBuiltIns() throws Exception {

        String sourceCode = functionInput;

        Environment globEnv = new Environment();

        TSchemeNode[] nodes = CodeProcessor.getASTNodes(sourceCode, globEnv);

        Object ret = createRootAndExecute(nodes, globEnv.getGlobalFrame());

        assertEquals(expectedOutput, ret.toString());
    }
}