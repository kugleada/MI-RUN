package cz.schemeinterpreter.datatypes;

import cz.schemeinterpreter.scheme_env.Environment;

import static java.util.Arrays.asList;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class SchemeList extends SchemeNode implements Iterable<SchemeNode> {
        public static final SchemeList EMPTY = new SchemeList();

        private final SchemeNode car;
        private final SchemeList cdr;
        private final int length;

        private SchemeList() {
            this.car = null;
            this.cdr = null;
            this.length = 0;
        }

        private SchemeList(SchemeNode car, SchemeList cdr) {
            this.car = car;
            this.cdr = cdr;
            this.length = cdr.length + 1;
        }

        //@SafeVarargs
        public static SchemeList list(SchemeNode... objs) {
            return list(asList(objs));
        }


        public static  SchemeList list(List<SchemeNode> objs) {
            SchemeList l = EMPTY;
            for (int i=objs.size()-1; i>=0; i--) {
                l = l.cons(objs.get(i));
            }
            return l;
        }


        public SchemeList cons(SchemeNode node) {
            return new SchemeList(node, this);
        }

        public SchemeNode car() {
            if (this != EMPTY) {
                return this.car;
            }
            //throw new SchemeException("Cannot car the empty list");
            return null;
        }

        public SchemeList cdr() {
            if (this != EMPTY) {
                return this.cdr;
            }
            //throw new SchemeException("Cannot cdr the empty list");
            return null;
        }

        public long size() {
            return this.length;
        }

        @Override
        public Iterator<SchemeNode> iterator() {
            return new Iterator<SchemeNode>() {
                private SchemeList l = SchemeList.this;

                @Override
                public boolean hasNext() {
                    return this.l != EMPTY;
                }

                @Override
                public SchemeNode next() {
                    if (this.l == EMPTY) {
                        //throw new SchemeException("At end of list");
                        return null;
                    }
                    SchemeNode car = this.l.car;
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
            if (!(other instanceof SchemeList)) {
                return false;
            }
            if (this == EMPTY && other == EMPTY) {
                return true;
            }

            SchemeList that = (SchemeList) other;
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
            SchemeList rest = this.cdr;
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
    public Object eval(Environment env) {
        //return this.toString();

        SchemeFunction function = (SchemeFunction) this.car.eval(env);

        List<Object> args = new LinkedList<>();
        for (SchemeNode node : this.cdr) {
            args.add(node.eval(env));
        }
        return function.apply(args.toArray());
    }
}
