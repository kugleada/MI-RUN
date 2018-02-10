package cz.schemeinterpreter.reader;

import com.oracle.truffle.api.frame.FrameSlot;
import cz.schemeinterpreter.datatypes.*;
import cz.schemeinterpreter.lang.SpecialForm;
import cz.schemeinterpreter.scheme_env.TEnvironment;
import cz.schemeinterpreter.truffleNodes.*;
import cz.schemeinterpreter.truffleNodes.TDefineNodeGen;
import cz.schemeinterpreter.truffleNodes.TSchemeTypesGen;
import cz.schemeinterpreter.truffleNodes.node.TSchemeNode;
import cz.schemeinterpreter.truffleNodes.type.TSchemeList;
import cz.schemeinterpreter.truffleNodes.type.TSchemeSymbol;

import java.io.*;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

public class Reader {
    public static SchemeList read(InputStream istream) throws IOException {
        return read(new PushbackReader(new InputStreamReader(istream)));
    }

    private static SchemeList read(PushbackReader pstream)
            throws IOException {
        List<SchemeNode> nodes = new LinkedList<>();

        readWhitespace(pstream);
        char c = (char) pstream.read();
        while ((byte) c != -1) {
            pstream.unread(c);
            nodes.add(readNode(pstream));
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

    public static SchemeNode readNode(PushbackReader pstream) throws IOException {
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

    private static SchemeNode readNumber(PushbackReader pstream) throws IOException {
        StringBuffer stringNum = new StringBuffer();
        char digit = (char) pstream.read();
        while (Character.isDigit(digit)) {
            stringNum.append(digit);
            digit = (char) pstream.read();
        }
        //assert (digit == ' ' || digit == '\t' || digit == '\n') : "Reading a list must start with '('";
        pstream.unread(digit);
        return new SchemeNumber(new BigInteger(stringNum.toString()));
    }

    private static SchemeNode readBoolean(PushbackReader pstream) throws IOException {
        char bool = (char) pstream.read();
        //if (bool == '#') throw new IllegalArgumentException("Reading a boolean must start with '#'");
        bool = (char) pstream.read();
        if (bool != 't' && bool != 'f') throw new IllegalArgumentException("After # there must be 't' or 'f'");
        if (bool == 'f') {
            return new SchemeBoolean(false);
        }
        return new SchemeBoolean(true);
    }

    private static SchemeNode readSymbol(PushbackReader pstream) throws IOException {
        StringBuffer symbol = new StringBuffer();
        char c = (char) pstream.read();
        while (!Character.isWhitespace(c) && c != -1 && c != ')' && c != '\uFFFF') { //\uFFFF occured at the end of line while reading in IDE
            symbol.append(c);
            c = (char) pstream.read();
        }
        //assert (digit == ' ' || digit == '\t' || digit == '\n') : "Reading a list must start with '('";
        pstream.unread(c);
        return new SchemeSymbol(symbol.toString());
    }

    private static SchemeNode readList(PushbackReader pstream) throws IOException {
        char paren = (char) pstream.read();
        //assert paren == '(' : "Reading a list must start with '('";
        List<SchemeNode> list = new LinkedList<>();
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
        return SpecialForm.check(SchemeList.list(list));
    }






    public static TSchemeNode[] readTruffle(ByteArrayInputStream istream,
                                            TEnvironment tge) throws IOException {
        return readTruffle(new PushbackReader(new InputStreamReader(istream)), tge);
    }

    private static TSchemeNode[] readTruffle(PushbackReader pstream,
                                             TEnvironment tge)
            throws IOException {
        List<TSchemeNode> nodes = new LinkedList<>();

        readWhitespace(pstream);
        char c = (char) pstream.read();
        while ((byte) c != -1) {
            pstream.unread(c);
            nodes.add(readTNode(pstream, tge));
            readWhitespace(pstream);
            c = (char) pstream.read();
        }

        return nodes.toArray(new TSchemeNode[nodes.size()]);
    }

    public static TSchemeNode readTNode(PushbackReader pstream,
                                        TEnvironment tge) throws IOException {
        char c = (char) pstream.read();
        pstream.unread(c);
        if (c == '(') {
            return readTList(pstream, tge);
        } else if (Character.isDigit(c)) {
            return readTNumber(pstream);
        } else if (c == '#') {
            throw new IllegalArgumentException("Unmatched close paren");
        } else {
            return readTSymbol(pstream, tge);
        }
    }

    private static TSchemeNode readTNumber(PushbackReader pstream) throws IOException {
        StringBuffer stringNum = new StringBuffer();
        char digit = (char) pstream.read();
        while (Character.isDigit(digit)) {
            stringNum.append(digit);
            digit = (char) pstream.read();
        }
        //assert (digit == ' ' || digit == '\t' || digit == '\n') : "Reading a list must start with '('";
        pstream.unread(digit);
        return new TSchemeNumberNode(Long.parseLong(stringNum.toString()));
    }

    private static TSchemeNode readTSymbol(PushbackReader pstream,
                                           TEnvironment tge) throws IOException {
        System.out.println("readTSymbol");
        StringBuffer symbol = new StringBuffer();
        char c = (char) pstream.read();
        while (!Character.isWhitespace(c) && c != -1 && c != ')' && c != '\uFFFF') { //\uFFFF occured at the end of line while reading in IDE
            symbol.append(c);
            c = (char) pstream.read();
        }
        //assert (digit == ' ' || digit == '\t' || digit == '\n') : "Reading a list must start with '('";
        pstream.unread(c);

        return new TSchemeSymbol(symbol.toString());
    }


    private static TSchemeNode readTList(PushbackReader pstream,
                                         TEnvironment tge) throws IOException {
        char paren = (char) pstream.read();
        //assert paren == '(' : "Reading a list must start with '('";
        List<TSchemeNode> list = new LinkedList<>();
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
                list.add(readTNode(pstream, tge));
            }
        } while (true);
        System.out.println("Jsem tu ---");
        return TSpecialForm.check(TSchemeList.list(list), tge);

    }
}
