grammar PuzzleScript;

@header {
package cn.codependency.framework.puzzle.script.core.ast;
}

script
    : BLANK_LINE* headers BLANK_LINE* filters? BLANK_LINE* statements EOF
    ;

headers
    : header*
    ;

header
   : tag=IDENTIFIER COLON val=stringItem
   ;

filters
    : 'filters' ':' expression
    ;

statements
    : (statement BLANK_LINE*) *
    ;

statement
    : BREAK
    | CONTINUE
    | BLANK_LINE
    | IF parExpression (LBRACE statements RBRACE) (ELSE_IF parExpression (LBRACE statements RBRACE))* (ELSE (LBRACE statements RBRACE))?
    | FOR LPAREN key=IDENTIFIER ',' value=IDENTIFIER 'in' expression RPAREN (LBRACE statements RBRACE)
    | whenExpression
    | callTask
    | callFunction
    | RETURN expression?
    ;

expression
    : ref=expression DOT callFunction
    | primary
    | id=IDENTIFIER
    | callFunction
    | ref=expression DOT id=IDENTIFIER
    | list
    | groupExpr
    | when=expression '??' f=expression
    | array=expression DOT 'map' LPAREN value=IDENTIFIER ARROW itexpr=expression RPAREN
    | array=expression '[' start=expression ':' end=expression ']'
    | array=expression '[' index=expression ']'
    | array=expression '|[' filter=IDENTIFIER fop=('=='|'>'|'<'|'>='|'<='|'!='|'in') right=expression ']'
    | not='!' expression
    | field=expression ':' before=expression '->' after=expression
    | prefix=('+'|'-') expression
    | left=expression bop=('*'|'/'|'%') right=expression
    | left=expression bop=('+'|'-') right=expression
    | left=expression bop=('<=' | '>=' | '>' | '<' | '==' | '!=' | 'in' | 'not in') right=expression
    | left=expression bop='&&' right=expression
    | left=expression bop='||' right=expression
    | when=expression '?' t=expression ':' f=expression
    ;

callFunction
    : func=IDENTIFIER LPAREN (expression (COMMA expression)*)? RPAREN
    ;

groupExpr
    : AT IDENTIFIER
    ;

callTask
    : '=>' behavior=IDENTIFIER LPAREN template=STRING_LITERAL RPAREN
    ;

list
    : '[' (expression (','expression)*)? ']'
    ;

whenExpression
    : WHEN whenStatements
    | WHEN parExpression whenWopThenStatements
    ;

whenStatements
    : LBRACE whenThenStatement* defaultStatement? RBRACE
    |
    ;

whenThenStatement
    : expression ':' statements
    ;

defaultStatement
    : DEFAULT ':' statements
    ;

parExpression
    : LPAREN expression RPAREN
    ;

whenWopThenStatements
    : LBRACE whenWopThenStatement* defaultStatement? RBRACE
    ;

whenWopThenStatement
    : wop=('<=' | '>=' | '>' | '<' | '==' | '!=' | '&&' | '||' | 'in')? expression ':' statements
    ;

primary
    : '(' expression ')'
    | literal
    ;

stringItem
    : UN_QUOTED_STRING
    | IDENTIFIER
    | DECIMAL_LITERAL
    | STRING_LITERAL
    ;

/**
 * keyword
 */

IF:             'if';
ELSE_IF:        ('elif'|'else if');
ELSE:           'else';
FOR:            'for';
WHEN:           'when';
MAP:            'map';
IN:             'in';
RETURN:         'return';
BREAK:          'break';
CONTINUE:       'continue';
DEFAULT:        'default';
NULL_LITERAL:   'null';
BLANK_LINE:     '\n' [ \t\r\u000C]* '\n';

/**
 * lexer
 */
LPAREN:             '(';
RPAREN:             ')';
LBRACE:             '{';
RBRACE:             '}';
LBRACK:             '[';
RBRACK:             ']';
SEMI:               ';';
COMMA:              ',';
DOT:                '.';

ASSIGN:             '=';
GT:                 '>';
LT:                 '<';
BANG:               '!';
QUESTION:           '?';
COLON:              ':';
EQUAL:              '==';
LE:                 '<=';
GE:                 '>=';
NOTEQUAL:           '!=';
AND:                '&&';
OR:                 '||';
ADD:                '+';
SUB:                '-';
MUL:                '*';
DIV:                '/';
BITAND:             '&';
BITOR:              '|';
CARET:              '^';
MOD:                '%';
AT:                 '@';

ADD_ASSIGN:         '+=';
SUB_ASSIGN:         '-=';
MUL_ASSIGN:         '*=';
DIV_ASSIGN:         '/=';
AND_ASSIGN:         '&=';
OR_ASSIGN:          '|=';
XOR_ASSIGN:         '^=';
MOD_ASSIGN:         '%=';
LSHIFT_ASSIGN:      '<<=';
RSHIFT_ASSIGN:      '>>=';
URSHIFT_ASSIGN:     '>>>=';
ARROW:              '->';

// Literals
IDENTIFIER:         Letter LetterOrDigit*;
DECIMAL_LITERAL:    ('0' | [1-9] (Digits? | '_'+ Digits)) [lL]?;
UN_QUOTED_STRING:   Digits LetterOrDigit* ;

//HEX_LITERAL:        '0' [xX] [0-9a-fA-F] ([0-9a-fA-F_]* [0-9a-fA-F])? [lL]?;
//OCT_LITERAL:        '0' '_'* [0-7] ([0-7_]* [0-7])? [lL]?;
//BINARY_LITERAL:     '0' [bB] [01] ([01_]* [01])? [lL]?;

FLOAT_LITERAL:      (Digits '.' Digits? | '.' Digits) [fFdD]?
             |       Digits [fFdD]
             ;

//HEX_FLOAT_LITERAL:  '0' [xX] (HexDigits '.'? | HexDigits? '.' HexDigits) [pP] [+-]? Digits [fFdD]?;

BOOL_LITERAL:       'true'
            |       'false'
            ;

CHAR_LITERAL:       '\'' (~['\\\r\n] | EscapeSequence) '\'';

STRING_LITERAL:     '"' (~["\\\r\n] | EscapeSequence)* '"';

TEXT_BLOCK:         '"""' [ \t]* [\r\n] (. | EscapeSequence)*? '"""';


WS:                 [ \t\r\n\u000C]+ -> channel(HIDDEN);
COMMENT:            '/*' .*? '*/'    -> channel(HIDDEN);
LINE_COMMENT:       '//' ~[\r\n]*    -> channel(HIDDEN);
MINLINE_COMMENT:    '---' ~[\r\n]*   -> channel(HIDDEN);


literal
    : integerLiteral
    | floatLiteral
    | CHAR_LITERAL
    | STRING_LITERAL
    | BOOL_LITERAL
    | NULL_LITERAL
    | TEXT_BLOCK
    ;

integerLiteral
    : DECIMAL_LITERAL
//    | HEX_LITERAL
//    | OCT_LITERAL
//    | BINARY_LITERAL
    ;

floatLiteral
    : FLOAT_LITERAL
//    | HEX_FLOAT_LITERAL
    ;

fragment Digits
    : [0-9] ([0-9_]* [0-9])?
    ;

fragment Letter
    : [a-zA-Z$_]
    | ~[\u0000-\u007F\uD800-\uDBFF]
    | [\uD800-\uDBFF] [\uDC00-\uDFFF]
    ;

fragment LetterOrDigit
    : Letter
    | [0-9]
    ;

fragment EscapeSequence
    : '\\' [btnfr"'\\]
    | '\\' ([0-3]? [0-7])? [0-7]
    | '\\' 'u'+ HexDigit HexDigit HexDigit HexDigit
    ;

fragment HexDigit
    : [0-9a-fA-F]
    ;

fragment HexDigits
    : HexDigit ((HexDigit | '_')* HexDigit)?
    ;
