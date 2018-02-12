package tscheme.truffle.parser;

import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import tscheme.truffle.parser.syntax.*;
import tscheme.truffle.datatypes.TSchemeList;
import tscheme.truffle.datatypes.TSchemeSymbol;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;

import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.source.SourceSection;

/**
 * ANTLR Visitor, going through source code using patterns in grammar file.
 * Visitor for each form.
 */
public class Reader extends TSchemeBaseVisitor<Syntax<?>> {

    private final Source source; //< Source code.

    /**
     * Takes source and creates ParseTree.
     * @param source Source code.
     * @return ListSyntax
     * @throws Exception In case of reader error.
     */
    public static ListSyntax read(Source source) throws Exception {

        Reader r = new Reader(source); // create reader

        ParseTree pt = createParseTree(source.getInputStream()); // create ANTLR syntax tree

        ListSyntax ls = (ListSyntax) r.visit(pt); // create Scheme Syntax tree

        return ls;
    }

    // Simple contructor for reader.
    private Reader(Source source) {
        //System.out.println("Source:" + source.getCharacters());
        this.source = source;
    }

    // Create parse tree from given stream.
    private static ParseTree createParseTree(InputStream istream)
            throws Exception {

        // deprecated, but works
        TSchemeLexer lexer = new TSchemeLexer(new ANTLRInputStream(istream));

        CommonTokenStream tokens = new CommonTokenStream(lexer);

        TSchemeParser parser = new TSchemeParser(tokens);

        ParseTree pt = parser.file(); // parse from root

        int errors = parser.getNumberOfSyntaxErrors();
        if (errors > 0) {
            System.out.println("Errors during parsing: " + errors);
            throw new Exception("EXCEPTION DURING PARSING!");
        }

        return pt;
    }

    // Create source section fromm given context and borders.
    private SourceSection createSourceSection(ParserRuleContext ctx) {
        return source.createSection(
                ctx.start.getLine(),
                (ctx.start.getCharPositionInLine() + 1),
                (ctx.stop.getStopIndex() - ctx.start.getStartIndex() + 1));
    }

    // Visit "file", that is, root of program.
    @Override
    public ListSyntax visitFile(TSchemeParser.FileContext ctx) {

        List<Syntax<?>> forms = new ArrayList<>();

        for (TSchemeParser.FormContext form : ctx.form()) {
            forms.add(this.visit(form));
            //System.out.println("Visited: " + form.getClass());
        }

        return new ListSyntax(TSchemeList.list(forms),
                createSourceSection(ctx));
    }

    // Visit "list".
    @Override
    public ListSyntax visitList(TSchemeParser.ListContext ctx) {
        List<Syntax<?>> forms = new ArrayList<>();
        for (TSchemeParser.FormContext form : ctx.form()) {
            forms.add(this.visit(form));
            //System.out.println("Visited: " + form.getText());
        }
        return new ListSyntax(TSchemeList.list(forms),
                createSourceSection(ctx));
    }

    // Visit number and return syntax.
    @Override
    public Syntax<?> visitNumber(TSchemeParser.NumberContext ctx) {
        try {
            //System.out.println("Mam cislo:" + ctx.getText());
        return new LongIntegerSyntax(Long.valueOf(ctx.getText(), 10),
                createSourceSection(ctx));
        } catch (NumberFormatException e) {
            return new BigIntegerSyntax(new BigInteger(ctx.getText()),
                    createSourceSection(ctx));
        }
    }

    // Visit double and return syntax.
    @Override
    public Syntax<?> visitDouble(TSchemeParser.DoubleContext ctx) {

        return new DoubleSyntax(Double.valueOf(ctx.getText()),
                    createSourceSection(ctx));

    }

    // Visit boolean and return syntax.
    @Override
    public BooleanSyntax visitBool(TSchemeParser.BoolContext ctx) {
        return new BooleanSyntax("#t".equals(ctx.getText()) ? true : false,
                createSourceSection(ctx));
    }

    // Visit symbol and return syntax.
    @Override
    public SymbolSyntax visitSymbol(TSchemeParser.SymbolContext ctx) {
        return new SymbolSyntax(new TSchemeSymbol(ctx.getText()),
                createSourceSection(ctx));
    }

    // Visit quote and return syntax.
    @Override
    public ListSyntax visitQuote(TSchemeParser.QuoteContext ctx) {
        return new ListSyntax(TSchemeList.list(
                new SymbolSyntax(new TSchemeSymbol("quote"), createSourceSection(ctx)),
                this.visit(ctx.form())),
                createSourceSection(ctx));
    }

    // Visit String and return syntax.
    @Override
    public StringSyntax visitString(TSchemeParser.StringContext ctx) {

        String input = ctx.getText();

        StringBuilder b = new StringBuilder();

        for (int i=1; i < input.length()-1; i++) { // from 1 to last-but-one, to skip " "

            char c = input.charAt(i);

            if (c == '\\') {

                char next = input.charAt(i + 1);

                i++;

                switch (next) {
                case '\\':
                    b.append('\\');
                    break;
                case '"':
                    b.append('"');
                    break;
                case 'n':
                    b.append('\n');
                    break;
                case 'r':
                    b.append('\r');
                    break;
                case 't':
                    b.append('\t');
                    break;
                case 'f':
                    b.append('\f');
                    break;
                case 'b':
                    b.deleteCharAt(b.length() -1);
                    break;
                default:
                    b.append(next);
                    break;
                }
            } else {
                b.append(c);
            }
        }

        return new StringSyntax(b.toString(), createSourceSection(ctx));
    }
}
