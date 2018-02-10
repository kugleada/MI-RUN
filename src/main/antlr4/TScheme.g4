grammar TScheme;

@header {
    package tscheme.truffle.parser;
}

// root node
file : form* ;

// forms
form : '(' form* ')'            # list
    | '\'' form                 # quote
    | INT                       # number
    | DOUBLE                    # double
    | BOOLEAN                   # bool
    | STRING                    # string
    | SYMBOL                    # symbol
    ;

// single tokens
WS : [ \t\r\n] -> skip ;
INT : [0-9]+ ;
DOUBLE: INT '.' INT ;
BOOLEAN : ('#f'|'#t') ;
STRING : '"' ( ~'"' | '\\' '"')* '"' ;
SYMBOL : ~('#'|'"'|'\''|[()]|[ \t\r\n]) ~('"'|'\''|[()]|[ \t\r\n])* ;

