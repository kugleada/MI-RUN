package tscheme.truffle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import tscheme.truffle.nodetypes.TSchemeNode;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tscheme.truffle.TSchemeMain.TAIL_CALL_ENABLED;
import static tscheme.truffle.TSchemeMain.createRootAndExecute;

@RunWith(Parameterized.class)
public class TailCallTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"(define fast-factorial-aux (lambda (N A) (if (= N 1) A (fast-factorial-aux (- N 1) (* N A)))))" +
                "(define fast-factorial (lambda (N) (fast-factorial-aux N 1)))" + "(fast-factorial 5)", "120"},
                {"(define loop (lambda (n a) (if (= a n) a (loop n (+ a 1)))))(loop 1000 0)", "1000"}

        });
    }

    private String functionInput;

    private String expectedOutput;

    public TailCallTest(String input, String expected) {
        functionInput = input;
        expectedOutput = expected;
    }

    @Test
    public void testTailCalls() throws Exception {
        TAIL_CALL_ENABLED = true;

        String sourceCode = functionInput;

        Environment globEnv = new Environment();

        TSchemeNode[] nodes = CodeProcessor.getASTNodes(sourceCode, globEnv);

        Object ret = createRootAndExecute(nodes, globEnv.getGlobalFrame());

        assertEquals(expectedOutput, ret.toString());
    }
}