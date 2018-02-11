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
import static org.junit.jupiter.api.Assertions.assertNull;

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