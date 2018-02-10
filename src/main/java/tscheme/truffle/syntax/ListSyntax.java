package tscheme.truffle.syntax;

import java.util.ArrayList;
import java.util.List;

import tscheme.truffle.parser.Syntax;
import tscheme.truffle.datatypes.TSchemeList;

import com.oracle.truffle.api.source.SourceSection;

public class ListSyntax extends Syntax<TSchemeList<? extends Syntax<?>>> {
    public ListSyntax(TSchemeList<? extends Syntax<?>> value,
            SourceSection sourceSection) {
        super(value, sourceSection);
    }

    @Override
    public Object strip() {
        List<Object> list = new ArrayList<Object>();
        for (Syntax<? extends Object> syntax : getValue()) {
            list.add(syntax.strip());
        }
        return TSchemeList.list(list);
    }

    @Override
    public String getName() {
        if (super.getName() != null) {
            return super.getName();
        }
        if (this.getValue().size() == 0) {
            return "()";
        }
        return this.getValue().car().getValue().toString() + "-" + this.hashCode();
    }
}
