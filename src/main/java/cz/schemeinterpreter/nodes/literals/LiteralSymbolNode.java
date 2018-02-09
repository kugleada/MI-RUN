package cz.schemeinterpreter.nodes.literals;

import com.oracle.truffle.api.frame.VirtualFrame;
import cz.schemeinterpreter.nodes.SchemeNode;
import cz.schemeinterpreter.types.SchemeSymbol;

public class LiteralSymbolNode extends SchemeNode {
        public final SchemeSymbol symbol;

        public LiteralSymbolNode(SchemeSymbol symbol) {
            this.symbol = symbol;
        }

        @Override
        public SchemeSymbol executeSchemeSymbol(VirtualFrame virtualFrame) {
            return this.symbol;
        }

        @Override
        public Object execute(VirtualFrame virtualFrame) {
            return this.symbol;
        }

        @Override
        public String toString() {
            return "'" + this.symbol.toString();
        }
    }
