package lang;

import datatypes.Node;
import datatypes.SchemeList;
import scheme_env.Environment;

public class IfSpecialForm extends SpecialForm {
    public IfSpecialForm(SchemeList listNode) {
        super(listNode);
    }

    @Override
    public Object eval(Environment env) {
        Node testNode = this.listNode.cdr().car();
        Node thenNode = this.listNode.cdr().cdr().car();
        Node elseNode = this.listNode.cdr().cdr().cdr().car();

        Object result = testNode.eval(env);
        if (result == SchemeList.EMPTY || Boolean.FALSE == result) {
            return elseNode.eval(env);
        } else {
            return thenNode.eval(env);
        }
    }
}
