package lang;

import datatypes.SchemeList;
import scheme_env.Environment;

public class QuoteSpecialForm extends SpecialForm {
    public QuoteSpecialForm(SchemeList listNode) {
        super(listNode);
    }

    @Override
    public Object eval(Environment env) {
        //return unevaluated second element
        return this.listNode.cdr().car();
    }
}
