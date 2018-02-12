package tscheme.truffle.parser;

import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.source.SourceSection;

import tscheme.truffle.helpers.TSchemeException;
import tscheme.truffle.parser.syntax.Syntax;

public abstract class TSchemeReadException extends TSchemeException {
    private static final long serialVersionUID = 1L;

    public static void throwReaderException(String message, Syntax<?> syntax,
            Namespace ns) {
        throw new TSchemeReadException(message) {
            private static final long serialVersionUID = 1L;

            @Override
            public SourceSection getSourceSection() {
                return syntax.getSourceSection();
            }

            @Override
            public String getMethodName() {
                return ns.getFunctionName();
            }
        };
    }

    public TSchemeReadException(String message) {
        super(message);
    }

    private static String filename(String path) {
        int end = path.lastIndexOf('.');
        if (end == -1) {
            end = path.length();
        }
        int start = path.lastIndexOf('/') + 1;
        return path.substring(start, end);
    }

    @Override
    public Throwable fillInStackTrace() {
        SourceSection sourceSection = this.getSourceSection();
        Source source = sourceSection != null ? sourceSection.getSource() : null;
        String sourceName = source != null ? source.getName() : null;
        int lineNumber;
        try {
            lineNumber = sourceSection != null ? sourceSection.getLineLocation().getLineNumber() : -1;
        } catch (UnsupportedOperationException e) {
            /*
             * SourceSection#getLineLocation() may throw an UnsupportedOperationException.
             */
            lineNumber = -1;
        }
        StackTraceElement[] traces = new StackTraceElement[] {
                new StackTraceElement(filename(sourceName),
                        this.getMethodName(),
                        sourceName,
                        lineNumber)
        };
        this.setStackTrace(traces);
        return this;
    }

    public abstract SourceSection getSourceSection();

    public abstract String getMethodName();
}
