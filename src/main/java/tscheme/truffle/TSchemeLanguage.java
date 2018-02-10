package tscheme.truffle;

import com.oracle.truffle.api.TruffleLanguage;

@TruffleLanguage.Registration(
        name = "TScheme",
        version = "0.1",
        mimeType = "application/x-tscheme")
public class TSchemeLanguage extends TruffleLanguage< Object > {

    public static final String MIME_TYPE = "application/x-tscheme"; // mime datatypes of this language

    // Below are auto-generated methods, should be implemented, but they are not essential.
    @Override
    protected Object createContext(TruffleLanguage.Env env) {
        return new Environment();
    }

    @Override
    protected Object getLanguageGlobal(Object arg0) {
        return null;
    }

    @Override
    protected boolean isObjectOfLanguage(Object obj) {
        return false;
    }

}
