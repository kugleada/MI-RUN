package cz.schemeinterpreter.reader;
/*
import cz.schemeinterpreter.datatypes.*;
import cz.schemeinterpreter.lang.SpecialForm;
import cz.schemeinterpreter.nodes.SchemeNode;

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
}
*/