package cz.schemeinterpreter.truffleNodes.type;


import com.oracle.truffle.api.frame.VirtualFrame;
import cz.schemeinterpreter.truffleNodes.node.TSchemeNode;

import static java.util.Arrays.asList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class TSchemeList extends TSchemeNode implements Iterable<TSchemeNode> {
    public static final TSchemeList EMPTY = new TSchemeList();

    private final TSchemeNode car;
    private final TSchemeList cdr;
    private final int length;

    private TSchemeList() {
        this.car = null;
        this.cdr = null;
        this.length = 0;
    }

    private TSchemeList(TSchemeNode car, TSchemeList cdr) {
        this.car = car;
        this.cdr = cdr;
        this.length = cdr.length + 1;
    }

    @SafeVarargs
    public static TSchemeList list(TSchemeNode... objs) {
        return list(asList(objs));
    }


    public static TSchemeList list(List<TSchemeNode> objs) {
        TSchemeList l = EMPTY;
        for (int i=objs.size()-1; i>=0; i--) {
            l = l.cons(objs.get(i));
        }
        return l;
    }


    public TSchemeList cons(TSchemeNode node) {
        return new TSchemeList(node, this);
    }

    public TSchemeNode car() {
        if (this != EMPTY) {
            return this.car;
        }
        return null;
    }

    public TSchemeList cdr() {
        if (this != EMPTY) {
            return this.cdr;
        }
        return null;
    }

    public long size() {
        return this.length;
    }

    @Override
    public Iterator<TSchemeNode> iterator() {
        return new Iterator<TSchemeNode>() {
            private TSchemeList l = TSchemeList.this;

            @Override
            public boolean hasNext() {
                return this.l != EMPTY;
            }

            @Override
            public TSchemeNode next() {
                if (this.l == EMPTY) {
                    //throw new SchemeException("At end of list");
                    return null;
                }
                TSchemeNode car = this.l.car;
                this.l = this.l.cdr;
                return car;
            }

            @Override
            public void remove() {
                //throw new SchemeException("Iterator is immutable");
            }
        };
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof TSchemeList)) {
            return false;
        }
        if (this == EMPTY && other == EMPTY) {
            return true;
        }

        TSchemeList that = (TSchemeList) other;
        if (this.cdr == EMPTY && that.cdr != EMPTY) {
            return false;
        }
        return this.car.equals(that.car) && this.cdr.equals(that.cdr);
    }

    @Override
    public String toString() {
        if (this == EMPTY) {
            return "()";
        }

        StringBuilder b = new StringBuilder("(" + this.car);
        TSchemeList rest = this.cdr;
        while (rest != null && rest != EMPTY) {
            b.append(" ");
            b.append(rest.car);
            rest = rest.cdr;
        }
        b.append(")");
        return b.toString();
    }

    public int getLength() {
        return length;
    }

    @Override
    public Object execute(VirtualFrame virtualFrame) {

        TSchemeFunction function = (TSchemeFunction) this.car.execute(virtualFrame);

        List<Object> args = new LinkedList<>();
        for (TSchemeNode node : this.cdr) {
            args.add(node.execute(virtualFrame));
        }
        return null;
    }
}
