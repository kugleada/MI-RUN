package cz.schemeinterpreter.lang;

import cz.schemeinterpreter.datatypes.SchemeNode;
import cz.schemeinterpreter.datatypes.SchemeList;
import cz.schemeinterpreter.scheme_env.Environment;

public class IfSpecialForm extends SpecialForm {
    public IfSpecialForm(SchemeList listNode) {
        super(listNode);
    }

    @Override
    public Object eval(Environment env) {
        SchemeNode testNode = this.listNode.cdr().car();
        SchemeNode thenNode = this.listNode.cdr().cdr().car();
        SchemeNode elseNode = this.listNode.cdr().cdr().cdr().car();

        Object result = testNode.eval(env);
        if (result == SchemeList.EMPTY || Boolean.FALSE == result) {
            return elseNode.eval(env);
        } else {
            return thenNode.eval(env);
        }
    }
}
