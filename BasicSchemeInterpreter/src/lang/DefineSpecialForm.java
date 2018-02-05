package lang;

import datatypes.SchemeList;
import datatypes.SchemeSymbol;
import scheme_env.Environment;

public class DefineSpecialForm extends SpecialForm {
    public DefineSpecialForm(SchemeList listNode) {
        super(listNode);
    }

    @Override
    public Object eval(Environment env) {
        SchemeSymbol sym = (SchemeSymbol) this.listNode.cdr().car(); // 2nd element
        env.putValue(sym.getName(),
                this.listNode.cdr().cdr().car().eval(env)); // 3rd element
        return SchemeList.EMPTY;
    }
}
