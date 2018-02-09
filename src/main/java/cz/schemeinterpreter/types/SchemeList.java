package cz.schemeinterpreter.types;

import cz.schemeinterpreter.exceptions.SchemeException;

import static java.util.Arrays.asList;

import java.util.Iterator;
import java.util.List;


public class SchemeList<T extends Object> implements Iterable<T> {
    public static final SchemeList<?> EMPTY = new SchemeList<>();

    private final T car;
    private final SchemeList<T> cdr;
    private final int length;

    private SchemeList() {
        this.car = null;
        this.cdr = null;
        this.length = 0;
    }

    private SchemeList(T car, SchemeList<T> cdr) {
        this.car = car;
        this.cdr = cdr;
        this.length = cdr.length + 1;
    }

    @SafeVarargs
    public static <T> SchemeList<T> list(T... objs) {
        return list(asList(objs));
    }

    public static <T> SchemeList<T> list(List<T> objs) {
        @SuppressWarnings("unchecked")
        SchemeList<T> l = (SchemeList<T>) EMPTY;
        for (int i=objs.size()-1; i>=0; i--) {
            l = l.cons(objs.get(i));
        }
        return l;
    }

    public SchemeList<T> cons(T node) {
        return new SchemeList<T>(node, this);
    }

    public T car() {
        if (this != EMPTY) {
            return this.car;
        }
        throw new SchemeException("Cannot car the empty list");
    }

    public SchemeList<T> cdr() {
        if (this != EMPTY) {
            return this.cdr;
        }
        throw new SchemeException("Cannot cdr the empty list");
    }

    public long size() {
        return this.length;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private SchemeList<T> l = SchemeList.this;

            @Override
            public boolean hasNext() {
                return this.l != EMPTY;
            }

            @Override
            public T next() {
                if (this.l == EMPTY) {
                    throw new SchemeException("At end of list");
                }
                T car = this.l.car;
                this.l = this.l.cdr;
                return car;
            }

            @Override
            public void remove() {
                throw new SchemeException("Iterator is immutable");
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

        SchemeList<?> that = (SchemeList<?>) other;
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
        SchemeList<T> rest = this.cdr;
        while (rest != null && rest != EMPTY) {
            b.append(" ");
            b.append(rest.car);
            rest = rest.cdr;
        }
        b.append(")");
        return b.toString();
    }
}
