package tscheme.truffle.datatypes;

import static java.util.Arrays.asList;

import java.util.List;
import java.util.Iterator;

import tscheme.truffle.helpers.TSchemeException;

/**
 * Represents List = (a b c d e ...).
 * @param <Datatype> Type of items to be sotred in this list.
 */
public class TSchemeList<Datatype> implements Iterable<Datatype> {

    public static final TSchemeList<?> EMPTY = new TSchemeList<>(); //< Defition of empty list '().

    private final Datatype car; // Left-most item of list.
    private final TSchemeList<Datatype> cdr; // Rest of the list (all except car).
    private final int length; //< Size of this list.

    private TSchemeList() {
        this.length = 0;
        this.car = null;
        this.cdr = null;
    }

    private TSchemeList(Datatype car, TSchemeList<Datatype> cdr) {
        this.length = cdr.length + 1;
        this.car = car; // "left"
        this.cdr = cdr; // "the rest"
    }

    public static <Datatype> TSchemeList<Datatype> list(List<Datatype> objs) {

        TSchemeList<Datatype> list = (TSchemeList<Datatype>) EMPTY; // using empty list as end of new list, viz iterator

        for (int i = objs.size() - 1; i >= 0; --i) {
            list = list.cons(objs.get(i));
        }
        return list;
    }

    @Override
    public Iterator<Datatype> iterator() {
        return new Iterator<Datatype>() {
            private TSchemeList<Datatype> list = TSchemeList.this;

            @Override
            public boolean hasNext() {
                return this.list != EMPTY;
            }

            @Override
            public Datatype next() {
                if (this.list == EMPTY) {
                    throw new TSchemeException("END OF LIST");
                }

                Datatype car = this.list.car;

                this.list = this.list.cdr;

                return car;
            }

            @Override
            public void remove() {
                throw new TSchemeException("NOT POSSIBLE TO REMOVE FROM LIST.");
            }
        };
    }

    public long size() {
        return this.length;
    }

    @Override
    public boolean equals(Object right) {
        if (!(right instanceof TSchemeList)) {
            return false;
        }
        if (this == EMPTY && right == EMPTY) {
            return true;
        }

        TSchemeList<?> rightList = (TSchemeList<?>) right;
        if (this.cdr == EMPTY && rightList.cdr != EMPTY) {
            return false;
        }

        return (this.car.equals(rightList.car) &&
                this.cdr.equals(rightList.cdr));
    }

    @Override
    public String toString() {
        if (this == EMPTY) {
            return "()";
        }

        // the left-most
        StringBuilder stringRepresentation = new StringBuilder("(" + this.car);

        // and the rest
        TSchemeList<Datatype> tmp = this.cdr;

        while (tmp != null && tmp != EMPTY) {
            stringRepresentation.append(" ");
            stringRepresentation.append(tmp.car);
            tmp = tmp.cdr;
        }
        stringRepresentation.append(")");
        return stringRepresentation.toString();
    }

    /**
     * Constructs new list, with node as car and this list as cdr.
     * @param node Node to become car.
     * @return New scheme list.
     */
    public TSchemeList<Datatype> cons(Datatype node) {
        return new TSchemeList<Datatype>(node, this);
    }

    /**
     * car method of list, should return left-most value of this list.
     * @return Left-most value of this list.
     */
    public Datatype car() {
        if (this != EMPTY) {
            return this.car;
        }
        throw new TSchemeException("Not possible to apply car on empty list.");
    }

    /**
     * cdr method of list, should return the list except for car (left-most item).
     * @return Rest of this list, without car.
     */
    public TSchemeList<Datatype> cdr ( ) {
        if (this != EMPTY) {
            return this.cdr;
        }
        throw new TSchemeException("Not possible to apply cdr on empty list.");
    }

    /**
     * Convert series of Objects into SchemeList.
     * @param objs Objects to be converted into this SchemeList.
     * @param <Datatype> Type of objects.
     * @return TSchemeList
     */
    public static <Datatype> TSchemeList<Datatype> list(Datatype... objs) {
        return list(asList(objs));
    }
}
