package lang;

import datatypes.Node;
import datatypes.SchemeFunction;
import datatypes.SchemeList;
import datatypes.SchemeSymbol;
import scheme_env.Environment;

public class LambdaSpecialForm extends SpecialForm {
    public LambdaSpecialForm(SchemeList paramsAndBody) {
        super(paramsAndBody);
    }

    @Override
    public Object eval(final Environment parentEnv) {
        final SchemeList formalParams = (SchemeList) this.listNode.cdr().car();
        final SchemeList body = this.listNode.cdr().cdr();
        return new SchemeFunction() {
            @Override
            public Object apply(Object... args) {
                Environment lambdaEnv = new Environment(parentEnv);
                if (args.length != formalParams.getLength()) {
                    throw new RuntimeException(
                            "Wrong number of arguments. Expected: " +
                                    formalParams.getLength() + ". Got: " +
                                    args.length);
                }

                // Map parameter values to formal parameter names
                int i = 0;
                for (Node param : formalParams) {
                    SchemeSymbol paramSymbol = (SchemeSymbol) param;
                    lambdaEnv.putValue(paramSymbol.getName(), args[i]);
                    i++;
                }

                // Evaluate body
                Object output = null;
                for (Node node : body) {
                    output = node.eval(lambdaEnv);
                }

                return output;
            }
        };
    }
}
