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
public class BuiltInTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"(+ 9 15)", "24"},
                {"(- 5 3)", "2"},
                {"(* 9 4)", "36"},
                {"(/ 6 3)", "2"},
                {"(% 5 2)", "1"},
                {"(printvar 2)", "2"},
                {"(car '(1 2 3))", "1"},
                {"(cdr '(1 2 3))", "(2 3)"},
        });
    }

    private String functionInput;

    private String expectedOutput;

    public BuiltInTest(String input, String expected)
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