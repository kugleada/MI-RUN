package cz.schemeinterpreter.reader;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlot;
import cz.schemeinterpreter.exceptions.NotYetImplemented;
import cz.schemeinterpreter.nodes.InvokeNode;
import cz.schemeinterpreter.nodes.SchemeNode;
import cz.schemeinterpreter.nodes.SymbolNode;
import cz.schemeinterpreter.nodes.literals.BigIntegerNode;
import cz.schemeinterpreter.nodes.literals.BooleanNode;
import cz.schemeinterpreter.nodes.literals.LiteralListNode;
import cz.schemeinterpreter.nodes.special.DefineNode;
import cz.schemeinterpreter.nodes.special.IfNode;
import cz.schemeinterpreter.nodes.special.LambdaNodeGen;
import cz.schemeinterpreter.types.SchemeFunction;
import cz.schemeinterpreter.types.SchemeList;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class TruffleReader  {
    public static final Stack<FrameDescriptor> frameDescriptors = new Stack<>();
    static {
        frameDescriptors.add(new FrameDescriptor());
    }

    private static interface Convertible {
        public SchemeNode convert();
    }

    private static class LiteralConvertible implements Convertible {
        final SchemeNode value;
        public LiteralConvertible(SchemeNode value) {
            this.value = value;
        }

        @Override
        public SchemeNode convert() {
            return this.value;
        }
    }

    private static class SymbolConvertible implements Convertible {
        final String name;
        public SymbolConvertible(String name) {
            this.name = name;
        }

        @Override
        public SchemeNode convert() {
            return new SymbolNode(frameDescriptors.peek().findOrAddFrameSlot(this.name));
        }
    }

    private static class ListConvertible implements Convertible {
        final List<Convertible> list;
        public ListConvertible (List<Convertible> list) {
            this.list = list;
        }

        @Override
        public SchemeNode convert() {
            if (this.list.size() == 0) {
                return new LiteralListNode(SchemeList.EMPTY);
            } else if (this.list.get(0) instanceof SymbolConvertible
                    && this.isSpecialForm((SymbolConvertible) this.list.get(0))) {
                return this.toSpecialForm();
            }
            return new InvokeNode(this.list.get(0).convert(),
                    this.list.subList(1, this.list.size())
                            .stream()
                            .map(Convertible::convert)
                            .toArray(size -> new SchemeNode[size]));
        }

        private boolean isSpecialForm(SymbolConvertible symbol) {
            switch (symbol.name) {
                case "lambda":
                case "define":
                case "quote":
                case "if":
                    return true;
                default:
                    return false;
            }
        }

        private SchemeNode toSpecialForm() {
            SymbolConvertible symbol = (SymbolConvertible) this.list.get(0);
            switch (symbol.name) {
                case "quote":
                    throw new NotYetImplemented();
                    //return quote(this.list.get(1));
                case "if":
                    return new IfNode(this.list.get(1).convert(),
                            this.list.get(2).convert(),
                            this.list.get(3).convert());
                case "define":
                    return new DefineNode(this.list.get(2).convert(), frameDescriptors.peek().findOrAddFrameSlot(
                                    ((SymbolConvertible) this.list.get(1)).name));
                case "lambda":
                    frameDescriptors.push(new FrameDescriptor());
                    List<FrameSlot> formalParameters = new ArrayList<>();
                    System.out.println("creating lambda");
                    for (Convertible arg : ((ListConvertible) this.list.get(1)).list) {
                        System.out.println(((SymbolConvertible) arg).name);
                        formalParameters.add(((SymbolNode) arg.convert()).getSlot());
                    }
                    List<SchemeNode> bodyNodes = new ArrayList<>();
                    for (Convertible bodyConv : this.list.subList(2, this.list.size())) {
                        bodyNodes.add(bodyConv.convert());
                    }
                    //bodyNodes.get(bodyNodes.size() - 1).setIsTail();
                    //TODO kde se nastavi lexical scope?


                    SchemeFunction function = SchemeFunction.create(
                            formalParameters.toArray(new FrameSlot[] {}),
                            bodyNodes.toArray(new SchemeNode[] {}), //bez parametru?
                            frameDescriptors.pop().copy());
                    return LambdaNodeGen.create(function);
                default:
                    throw new IllegalStateException("Unknown special form");
            }
        }
        /*
        private static SchemeNode quote(Convertible conv) {
            if (conv instanceof LiteralConvertible) {
                return ((LiteralConvertible) conv).convert();
            } else if (conv instanceof SymbolConvertible) {
                return new LiteralSymbolNode(new MumblerSymbol(
                        ((SymbolConvertible) conv).name));
            } else if (conv instanceof ListConvertible) {
                List<MumblerNode> output = new ArrayList<>();
                for (Convertible el : ((ListConvertible) conv).list) {
                    output.add(quote(el));
                }
                return new LiteralListNode(MumblerList.list(output));
            } else {
                throw new IllegalStateException("Unknown Convertible");
            }
        }
        */
    }

    public static SchemeList<SchemeNode> read(InputStream istream) throws IOException {
        return read(new PushbackReader(new InputStreamReader(istream)));
    }

    private static SchemeList<SchemeNode> read(PushbackReader pstream)
            throws IOException {
        List<SchemeNode> nodes = new LinkedList<>();

        readWhitespace(pstream);
        char c = (char) pstream.read();
        while ((byte) c != -1) {
            pstream.unread(c);
            nodes.add(readNode(pstream).convert());
            readWhitespace(pstream);
            c = (char) pstream.read();
        }

        return SchemeList.list(nodes);
    }

    private static void readWhitespace(PushbackReader pstream) throws IOException {
        char c;
        do {
            c = (char) pstream.read();
        } while (Character.isWhitespace(c));
        pstream.unread(c);
    }

    public static Convertible readNode(PushbackReader pstream) throws IOException {
        char c = (char) pstream.read();
        pstream.unread(c);
        if (c == '(') {
            return readList(pstream);
        } else if (Character.isDigit(c)) {
            return readNumber(pstream);
        } else if (c == '#') {
            return readBoolean(pstream);
        } else if (c == ')') {
            throw new IllegalArgumentException("Unmatched close paren");
        } else {
            return readSymbol(pstream);
        }
    }

    private static LiteralConvertible readNumber(PushbackReader pstream) throws IOException {
        StringBuffer stringNum = new StringBuffer();
        char digit = (char) pstream.read();
        while (Character.isDigit(digit)) {
            stringNum.append(digit);
            digit = (char) pstream.read();
        }
        //assert (digit == ' ' || digit == '\t' || digit == '\n') : "Reading a list must start with '('";
        pstream.unread(digit);
        return new LiteralConvertible(new BigIntegerNode(new BigInteger(stringNum.toString())));
    }

    private static LiteralConvertible readBoolean(PushbackReader pstream) throws IOException {
        char bool = (char) pstream.read();
        //if (bool == '#') throw new IllegalArgumentException("Reading a boolean must start with '#'");
        bool = (char) pstream.read();
        if (bool != 't' && bool != 'f') throw new IllegalArgumentException("After # there must be 't' or 'f'");
        if (bool == 'f') {
            return new LiteralConvertible(new BooleanNode(false));
        }
        return new LiteralConvertible(new BooleanNode(true));
    }

    private static SymbolConvertible readSymbol(PushbackReader pstream) throws IOException {
        StringBuffer symbol = new StringBuffer();
        char c = (char) pstream.read();
        while (!Character.isWhitespace(c) && c != -1 && c != ')' && c != '\uFFFF') { //\uFFFF occured at the end of line while reading in IDE
            symbol.append(c);
            c = (char) pstream.read();
        }
        //assert (digit == ' ' || digit == '\t' || digit == '\n') : "Reading a list must start with '('";
        pstream.unread(c);
        return new SymbolConvertible(symbol.toString());
    }

    private static ListConvertible readList(PushbackReader pstream) throws IOException {
        char paren = (char) pstream.read();
        //assert paren == '(' : "Reading a list must start with '('";
        List<Convertible> list = new LinkedList<>();
        do {
            readWhitespace(pstream);
            char c = (char) pstream.read();

            if (c == ')') {
                // end of list
                break;
            } else if ((byte) c == -1) {
                throw new EOFException("EOF reached before closing of list");
            } else {
                pstream.unread(c);
                list.add(readNode(pstream));
            }
        } while (true);
        return new ListConvertible(list);
    }
    /*
    public class Reader {
    public static final Stack<FrameDescriptor> frameDescriptors = new Stack<>();
    static {
        frameDescriptors.add(new FrameDescriptor());
    }

    private static interface Convertible {
        public MumblerNode convert();
    }

    private static class LiteralConvertible implements Convertible {
        final MumblerNode value;
        public LiteralConvertible(MumblerNode value) {
            this.value = value;
        }

        @Override
        public MumblerNode convert() {
            return this.value;
        }
    }

    private static class SymbolConvertible implements Convertible {
        final String name;
        public SymbolConvertible(String name) {
            this.name = name;
        }

        @Override
        public MumblerNode convert() {
            return SymbolNodeGen.create(
                    frameDescriptors.peek().findOrAddFrameSlot(this.name));
        }
    }

    private static class ListConvertible implements Convertible {
        final List<Convertible> list;
        public ListConvertible(List<Convertible> list) {
            this.list = list;
        }

        @Override
        public MumblerNode convert() {
            if (this.list.size() == 0) {
                return new LiteralListNode(MumblerList.EMPTY);
            } else if (this.list.get(0) instanceof SymbolConvertible
                    && this.isSpecialForm((SymbolConvertible) this.list.get(0))) {
                return this.toSpecialForm();
            }
            return new InvokeNode(this.list.get(0).convert(),
                    this.list.subList(1, this.list.size())
                    .stream()
                    .map(Convertible::convert)
                    .toArray(size -> new MumblerNode[size]));
        }

        private boolean isSpecialForm(SymbolConvertible symbol) {
            switch (symbol.name) {
            case "lambda":
            case "define":
            case "quote":
            case "if":
                return true;
            default:
                return false;
            }
        }

        private MumblerNode toSpecialForm() {
            SymbolConvertible symbol = (SymbolConvertible) this.list.get(0);
            switch (symbol.name) {
            case "quote":
                return quote(this.list.get(1));
            case "if":
                return new IfNode(this.list.get(1).convert(),
                        this.list.get(2).convert(),
                        this.list.get(3).convert());
            case "define":
                return DefineNodeGen.create(this.list.get(2).convert(),
                        frameDescriptors.peek().findOrAddFrameSlot(
                                ((SymbolConvertible) this.list.get(1)).name));
            case "lambda":
                frameDescriptors.push(new FrameDescriptor());
                List<FrameSlot> formalParameters = new ArrayList<>();
                for (Convertible arg : ((ListConvertible) this.list.get(1)).list) {
                    formalParameters.add(((SymbolNode) arg.convert()).getSlot());
                }
                List<MumblerNode> bodyNodes = new ArrayList<>();
                for (Convertible bodyConv : this.list.subList(2, this.list.size())) {
                    bodyNodes.add(bodyConv.convert());
                }
                bodyNodes.get(bodyNodes.size() - 1).setIsTail();

                MumblerFunction function = MumblerFunction.create(
                        formalParameters.toArray(new FrameSlot[] {}),
                        bodyNodes.toArray(new MumblerNode[] {}),
                        frameDescriptors.pop().copy());
                return LambdaNodeGen.create(function);
            default:
                throw new IllegalStateException("Unknown special form");
            }
        }

        private static MumblerNode quote(Convertible conv) {
            if (conv instanceof LiteralConvertible) {
                return ((LiteralConvertible) conv).convert();
            } else if (conv instanceof SymbolConvertible) {
                return new LiteralSymbolNode(new MumblerSymbol(
                        ((SymbolConvertible) conv).name));
            } else if (conv instanceof ListConvertible) {
                List<MumblerNode> output = new ArrayList<>();
                for (Convertible el : ((ListConvertible) conv).list) {
                    output.add(quote(el));
                }
                return new LiteralListNode(MumblerList.list(output));
            } else {
                throw new IllegalStateException("Unknown Convertible");
            }
        }
    }

    public static MumblerList<MumblerNode> read(InputStream istream) throws IOException {
        return read(new PushbackReader(new InputStreamReader(istream)));
    }

    private static MumblerList<MumblerNode> read(PushbackReader pstream)
            throws IOException {
        List<MumblerNode> nodes = new ArrayList<MumblerNode>();

        readWhitespace(pstream);
        char c = (char) pstream.read();
        while ((byte) c != -1) {
            pstream.unread(c);
            nodes.add(readNode(pstream).convert());
            readWhitespace(pstream);
            c = (char) pstream.read();
        }

        return MumblerList.list(nodes);
    }

    public static Convertible readNode(PushbackReader pstream) throws IOException {
        char c = (char) pstream.read();
        pstream.unread(c);
        if (c == '(') {
            return readList(pstream);
        } else if (Character.isDigit(c)) {
            return readNumber(pstream);
        } else if (c == '#') {
            return readBoolean(pstream);
        } else if (c == ')') {
            throw new IllegalArgumentException("Unmatched close paren");
        } else {
            return readSymbol(pstream);
        }
    }

    private static void readWhitespace(PushbackReader pstream)
            throws IOException {
        char c = (char) pstream.read();
        while (Character.isWhitespace(c)) {
            c = (char) pstream.read();
        }
        pstream.unread(c);
    }

    private static SymbolConvertible readSymbol(PushbackReader pstream)
            throws IOException {
        StringBuilder b = new StringBuilder();
        char c = (char) pstream.read();
        while (!(Character.isWhitespace(c) || (byte) c == -1 || c == '(' || c == ')')) {
            b.append(c);
            c = (char) pstream.read();
        }
        pstream.unread(c);
        return new SymbolConvertible(b.toString());
    }

    private static ListConvertible readList(PushbackReader pstream)
            throws IOException {
        char paren = (char) pstream.read();
        assert paren == '(' : "Reading a list must start with '('";
        List<Convertible> list = new ArrayList<>();
        do {
            readWhitespace(pstream);
            char c = (char) pstream.read();

            if (c == ')') {
                // end of list
                break;
            } else if ((byte) c == -1) {
                throw new EOFException("EOF reached before closing of list");
            } else {
                pstream.unread(c);
                list.add(readNode(pstream));
            }
        } while (true);
        return new ListConvertible(list);
    }

    private static Convertible readNumber(PushbackReader pstream)
            throws IOException {
        StringBuilder b = new StringBuilder();
        char c = (char) pstream.read();
        while (Character.isDigit(c)) {
            b.append(c);
            c = (char) pstream.read();
        }
        pstream.unread(c);
        try {
            return new LiteralConvertible(new LongNode(
                    Long.valueOf(b.toString(), 10)));
        } catch (NumberFormatException e) {
            // Number doesn't fit in a long. Using BigInteger.
            return new LiteralConvertible(new BigIntegerNode(
                    new BigInteger(b.toString(), 10)));
        }
    }

    private static LiteralConvertible readBoolean(PushbackReader pstream)
            throws IOException {
        char hash = (char) pstream.read();
        assert hash == '#' : "Reading a boolean must start with '#'";

        SymbolConvertible sym = readSymbol(pstream);
        if ("t".equals(sym.name)) {
            return new LiteralConvertible(BooleanNode.TRUE);
        } else if ("f".equals(sym.name)) {
            return new LiteralConvertible(BooleanNode.FALSE);
        } else {
            throw new IllegalArgumentException("Unknown value: #" + sym);
        }
    }
}
     */
}
