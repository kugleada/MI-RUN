package tscheme.truffle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import tscheme.truffle.nodetypes.TSchemeNode;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static tscheme.truffle.TSchemeMain.createRootAndExecute;

@RunWith(Parameterized.class)
public class TSchemeMainTest {

    @Test
    public void testItTest()
    {
        assertNull(null);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"((lambda (x) (* x x)) 2)", "4"},
                {"(define square (lambda (x) (* x x))) (square 3)", "9"},
        });
    }

    private String functionInput;

    private String expectedOutput;

    public TSchemeMainTest (String input, String expected)
    {
        functionInput = input;
        expectedOutput = expected;
    }

    @Test
    public void testFactorial() throws Exception {

        String sourceCode = functionInput;

        Environment globEnv = new Environment();

        TSchemeNode[] nodes = CodeProcessor.getASTNodes(sourceCode, globEnv);

        Object ret = createRootAndExecute(nodes, globEnv.getGlobalFrame());

        assertEquals(expectedOutput,ret.toString());
    }
}