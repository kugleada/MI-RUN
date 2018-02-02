package reader;

import datatypes.SchemeBoolean;
import datatypes.Node;
import datatypes.SchemeNumber;
import datatypes.SchemeSymbol;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PushbackReader;
import java.math.BigInteger;
import java.util.List;

public class Reader {
    public static MumblerListNode read(InputStream istream) throws IOException {
        return read(new PushbackReader(new InputStreamReader(istream)));
    }

    private static MumblerListNode read(PushbackReader pstream)
            throws IOException {
        List<Node> nodes = new ArrayList<Node>();

        readWhitespace(pstream);
        char c = (char) pstream.read();
        while ((byte) c != -1) {
            pstream.unread(c);
            nodes.add(readNode(pstream));
            readWhitespace(pstream);
            c = (char) pstream.read();
        }

        return MumblerListNode.list(nodes);
    }

    private static void readWhitespace(PushbackReader pstream) throws IOException {
        char c;
        do {
            c = (char) pstream.read();
        } while (Character.isWhitespace(c));
        pstream.unread(c);
    }

    public static Node readNode(PushbackReader pstream) throws IOException {
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

    private static Node readNumber(PushbackReader pstream) throws IOException {
        StringBuffer stringNum = new StringBuffer();
        char digit;
        do {
             digit = (char) pstream.read();
             stringNum.append(digit);
        } while (Character.isDigit(digit));
        //assert (digit == ' ' || digit == '\t' || digit == '\n') : "Reading a list must start with '('";
        pstream.unread(digit);
        return new SchemeNumber(new BigInteger(stringNum.toString()));
    }

    private static Node readBoolean(PushbackReader pstream) throws IOException {
        char bool = (char) pstream.read();
        assert bool == '#' : "Reading a boolean must start with '#'";
        bool = (char) pstream.read();
        assert (bool == 't' || bool == 'f') : "After # there must be 't' or 'f'";
        if (bool == 'f') {
            return new SchemeBoolean(false);
        }
        return new SchemeBoolean(true);
    }

    private static Node readSymbol(PushbackReader pstream) throws IOException {
        StringBuffer symbol = new StringBuffer();
        char c;
        do {
            c = (char) pstream.read();
            symbol.append(c);
        } while (c != -1 && c != ')' && !Character.isWhitespace(c));
        //assert (digit == ' ' || digit == '\t' || digit == '\n') : "Reading a list must start with '('";
        pstream.unread(c);
        return new SchemeSymbol(symbol.toString());
    }

    private static Node readList(PushbackReader pstream) throws IOException {
        return null;
        /*
        char paren = (char) pstream.read();
        assert paren == '(' : "Reading a list must start with '('";
        List<Node> list = new ArrayList<Node>();
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
        return SpecialForm.check(MumblerListNode.list(list));
        */
    }
}
