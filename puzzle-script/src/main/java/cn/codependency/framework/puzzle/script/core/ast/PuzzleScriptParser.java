// Generated from /Users/echo/Documents/workspace/projects/puzzle/puzzle-script/src/main/java/cn/codependency/framework/puzzle/script/core/antlr/PuzzleScript.g4 by ANTLR 4.13.2

package cn.codependency.framework.puzzle.script.core.ast;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class PuzzleScriptParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, IF=6, ELSE_IF=7, ELSE=8, FOR=9, 
		WHEN=10, MAP=11, IN=12, RETURN=13, BREAK=14, CONTINUE=15, DEFAULT=16, 
		NULL_LITERAL=17, BLANK_LINE=18, LPAREN=19, RPAREN=20, LBRACE=21, RBRACE=22, 
		LBRACK=23, RBRACK=24, SEMI=25, COMMA=26, DOT=27, ASSIGN=28, GT=29, LT=30, 
		BANG=31, QUESTION=32, COLON=33, EQUAL=34, LE=35, GE=36, NOTEQUAL=37, AND=38, 
		OR=39, ADD=40, SUB=41, MUL=42, DIV=43, BITAND=44, BITOR=45, CARET=46, 
		MOD=47, AT=48, ADD_ASSIGN=49, SUB_ASSIGN=50, MUL_ASSIGN=51, DIV_ASSIGN=52, 
		AND_ASSIGN=53, OR_ASSIGN=54, XOR_ASSIGN=55, MOD_ASSIGN=56, LSHIFT_ASSIGN=57, 
		RSHIFT_ASSIGN=58, URSHIFT_ASSIGN=59, ARROW=60, IDENTIFIER=61, DECIMAL_LITERAL=62, 
		UN_QUOTED_STRING=63, FLOAT_LITERAL=64, BOOL_LITERAL=65, CHAR_LITERAL=66, 
		STRING_LITERAL=67, TEXT_BLOCK=68, WS=69, COMMENT=70, LINE_COMMENT=71, 
		MINLINE_COMMENT=72;
	public static final int
		RULE_infinite = 0, RULE_headers = 1, RULE_header = 2, RULE_filters = 3, 
		RULE_statements = 4, RULE_statement = 5, RULE_expression = 6, RULE_callFunction = 7, 
		RULE_groupExpr = 8, RULE_callTask = 9, RULE_list = 10, RULE_whenExpression = 11, 
		RULE_whenStatements = 12, RULE_whenThenStatement = 13, RULE_defaultStatement = 14, 
		RULE_parExpression = 15, RULE_whenWopThenStatements = 16, RULE_whenWopThenStatement = 17, 
		RULE_primary = 18, RULE_stringItem = 19, RULE_literal = 20, RULE_integerLiteral = 21, 
		RULE_floatLiteral = 22;
	private static String[] makeRuleNames() {
		return new String[] {
			"infinite", "headers", "header", "filters", "statements", "statement", 
			"expression", "callFunction", "groupExpr", "callTask", "list", "whenExpression", 
			"whenStatements", "whenThenStatement", "defaultStatement", "parExpression", 
			"whenWopThenStatements", "whenWopThenStatement", "primary", "stringItem", 
			"literal", "integerLiteral", "floatLiteral"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'filters'", "'??'", "'|['", "'not in'", "'=>'", "'if'", null, 
			"'else'", "'for'", "'when'", "'map'", "'in'", "'return'", "'break'", 
			"'continue'", "'default'", "'null'", null, "'('", "')'", "'{'", "'}'", 
			"'['", "']'", "';'", "','", "'.'", "'='", "'>'", "'<'", "'!'", "'?'", 
			"':'", "'=='", "'<='", "'>='", "'!='", "'&&'", "'||'", "'+'", "'-'", 
			"'*'", "'/'", "'&'", "'|'", "'^'", "'%'", "'@'", "'+='", "'-='", "'*='", 
			"'/='", "'&='", "'|='", "'^='", "'%='", "'<<='", "'>>='", "'>>>='", "'->'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, "IF", "ELSE_IF", "ELSE", "FOR", "WHEN", 
			"MAP", "IN", "RETURN", "BREAK", "CONTINUE", "DEFAULT", "NULL_LITERAL", 
			"BLANK_LINE", "LPAREN", "RPAREN", "LBRACE", "RBRACE", "LBRACK", "RBRACK", 
			"SEMI", "COMMA", "DOT", "ASSIGN", "GT", "LT", "BANG", "QUESTION", "COLON", 
			"EQUAL", "LE", "GE", "NOTEQUAL", "AND", "OR", "ADD", "SUB", "MUL", "DIV", 
			"BITAND", "BITOR", "CARET", "MOD", "AT", "ADD_ASSIGN", "SUB_ASSIGN", 
			"MUL_ASSIGN", "DIV_ASSIGN", "AND_ASSIGN", "OR_ASSIGN", "XOR_ASSIGN", 
			"MOD_ASSIGN", "LSHIFT_ASSIGN", "RSHIFT_ASSIGN", "URSHIFT_ASSIGN", "ARROW", 
			"IDENTIFIER", "DECIMAL_LITERAL", "UN_QUOTED_STRING", "FLOAT_LITERAL", 
			"BOOL_LITERAL", "CHAR_LITERAL", "STRING_LITERAL", "TEXT_BLOCK", "WS", 
			"COMMENT", "LINE_COMMENT", "MINLINE_COMMENT"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "PuzzleScript.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public PuzzleScriptParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class InfiniteContext extends ParserRuleContext {
		public HeadersContext headers() {
			return getRuleContext(HeadersContext.class,0);
		}
		public StatementsContext statements() {
			return getRuleContext(StatementsContext.class,0);
		}
		public TerminalNode EOF() { return getToken(PuzzleScriptParser.EOF, 0); }
		public List<TerminalNode> BLANK_LINE() { return getTokens(PuzzleScriptParser.BLANK_LINE); }
		public TerminalNode BLANK_LINE(int i) {
			return getToken(PuzzleScriptParser.BLANK_LINE, i);
		}
		public FiltersContext filters() {
			return getRuleContext(FiltersContext.class,0);
		}
		public InfiniteContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_infinite; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PuzzleScriptListener ) ((PuzzleScriptListener)listener).enterInfinite(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PuzzleScriptListener ) ((PuzzleScriptListener)listener).exitInfinite(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PuzzleScriptVisitor ) return ((PuzzleScriptVisitor<? extends T>)visitor).visitInfinite(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InfiniteContext infinite() throws RecognitionException {
		InfiniteContext _localctx = new InfiniteContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_infinite);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(49);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(46);
					match(BLANK_LINE);
					}
					} 
				}
				setState(51);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
			}
			setState(52);
			headers();
			setState(56);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(53);
					match(BLANK_LINE);
					}
					} 
				}
				setState(58);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
			}
			setState(60);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__0) {
				{
				setState(59);
				filters();
				}
			}

			setState(65);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(62);
					match(BLANK_LINE);
					}
					} 
				}
				setState(67);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			}
			setState(68);
			statements();
			setState(69);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class HeadersContext extends ParserRuleContext {
		public List<HeaderContext> header() {
			return getRuleContexts(HeaderContext.class);
		}
		public HeaderContext header(int i) {
			return getRuleContext(HeaderContext.class,i);
		}
		public HeadersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_headers; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PuzzleScriptListener ) ((PuzzleScriptListener)listener).enterHeaders(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PuzzleScriptListener ) ((PuzzleScriptListener)listener).exitHeaders(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PuzzleScriptVisitor ) return ((PuzzleScriptVisitor<? extends T>)visitor).visitHeaders(this);
			else return visitor.visitChildren(this);
		}
	}

	public final HeadersContext headers() throws RecognitionException {
		HeadersContext _localctx = new HeadersContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_headers);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(74);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(71);
					header();
					}
					} 
				}
				setState(76);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class HeaderContext extends ParserRuleContext {
		public Token tag;
		public StringItemContext val;
		public TerminalNode COLON() { return getToken(PuzzleScriptParser.COLON, 0); }
		public TerminalNode IDENTIFIER() { return getToken(PuzzleScriptParser.IDENTIFIER, 0); }
		public StringItemContext stringItem() {
			return getRuleContext(StringItemContext.class,0);
		}
		public HeaderContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_header; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PuzzleScriptListener ) ((PuzzleScriptListener)listener).enterHeader(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PuzzleScriptListener ) ((PuzzleScriptListener)listener).exitHeader(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PuzzleScriptVisitor ) return ((PuzzleScriptVisitor<? extends T>)visitor).visitHeader(this);
			else return visitor.visitChildren(this);
		}
	}

	public final HeaderContext header() throws RecognitionException {
		HeaderContext _localctx = new HeaderContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_header);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(77);
			((HeaderContext)_localctx).tag = match(IDENTIFIER);
			setState(78);
			match(COLON);
			setState(79);
			((HeaderContext)_localctx).val = stringItem();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FiltersContext extends ParserRuleContext {
		public TerminalNode COLON() { return getToken(PuzzleScriptParser.COLON, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public FiltersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_filters; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PuzzleScriptListener ) ((PuzzleScriptListener)listener).enterFilters(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PuzzleScriptListener ) ((PuzzleScriptListener)listener).exitFilters(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PuzzleScriptVisitor ) return ((PuzzleScriptVisitor<? extends T>)visitor).visitFilters(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FiltersContext filters() throws RecognitionException {
		FiltersContext _localctx = new FiltersContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_filters);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(81);
			match(T__0);
			setState(82);
			match(COLON);
			setState(83);
			expression(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StatementsContext extends ParserRuleContext {
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public List<TerminalNode> BLANK_LINE() { return getTokens(PuzzleScriptParser.BLANK_LINE); }
		public TerminalNode BLANK_LINE(int i) {
			return getToken(PuzzleScriptParser.BLANK_LINE, i);
		}
		public StatementsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statements; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PuzzleScriptListener ) ((PuzzleScriptListener)listener).enterStatements(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PuzzleScriptListener ) ((PuzzleScriptListener)listener).exitStatements(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PuzzleScriptVisitor ) return ((PuzzleScriptVisitor<? extends T>)visitor).visitStatements(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementsContext statements() throws RecognitionException {
		StatementsContext _localctx = new StatementsContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_statements);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(94);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(85);
					statement();
					setState(89);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
					while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
						if ( _alt==1 ) {
							{
							{
							setState(86);
							match(BLANK_LINE);
							}
							} 
						}
						setState(91);
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
					}
					}
					} 
				}
				setState(96);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StatementContext extends ParserRuleContext {
		public Token key;
		public Token value;
		public TerminalNode BREAK() { return getToken(PuzzleScriptParser.BREAK, 0); }
		public TerminalNode CONTINUE() { return getToken(PuzzleScriptParser.CONTINUE, 0); }
		public TerminalNode BLANK_LINE() { return getToken(PuzzleScriptParser.BLANK_LINE, 0); }
		public TerminalNode IF() { return getToken(PuzzleScriptParser.IF, 0); }
		public List<ParExpressionContext> parExpression() {
			return getRuleContexts(ParExpressionContext.class);
		}
		public ParExpressionContext parExpression(int i) {
			return getRuleContext(ParExpressionContext.class,i);
		}
		public List<TerminalNode> LBRACE() { return getTokens(PuzzleScriptParser.LBRACE); }
		public TerminalNode LBRACE(int i) {
			return getToken(PuzzleScriptParser.LBRACE, i);
		}
		public List<StatementsContext> statements() {
			return getRuleContexts(StatementsContext.class);
		}
		public StatementsContext statements(int i) {
			return getRuleContext(StatementsContext.class,i);
		}
		public List<TerminalNode> RBRACE() { return getTokens(PuzzleScriptParser.RBRACE); }
		public TerminalNode RBRACE(int i) {
			return getToken(PuzzleScriptParser.RBRACE, i);
		}
		public List<TerminalNode> ELSE_IF() { return getTokens(PuzzleScriptParser.ELSE_IF); }
		public TerminalNode ELSE_IF(int i) {
			return getToken(PuzzleScriptParser.ELSE_IF, i);
		}
		public TerminalNode ELSE() { return getToken(PuzzleScriptParser.ELSE, 0); }
		public TerminalNode FOR() { return getToken(PuzzleScriptParser.FOR, 0); }
		public TerminalNode LPAREN() { return getToken(PuzzleScriptParser.LPAREN, 0); }
		public TerminalNode COMMA() { return getToken(PuzzleScriptParser.COMMA, 0); }
		public TerminalNode IN() { return getToken(PuzzleScriptParser.IN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(PuzzleScriptParser.RPAREN, 0); }
		public List<TerminalNode> IDENTIFIER() { return getTokens(PuzzleScriptParser.IDENTIFIER); }
		public TerminalNode IDENTIFIER(int i) {
			return getToken(PuzzleScriptParser.IDENTIFIER, i);
		}
		public WhenExpressionContext whenExpression() {
			return getRuleContext(WhenExpressionContext.class,0);
		}
		public CallTaskContext callTask() {
			return getRuleContext(CallTaskContext.class,0);
		}
		public CallFunctionContext callFunction() {
			return getRuleContext(CallFunctionContext.class,0);
		}
		public TerminalNode RETURN() { return getToken(PuzzleScriptParser.RETURN, 0); }
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PuzzleScriptListener ) ((PuzzleScriptListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PuzzleScriptListener ) ((PuzzleScriptListener)listener).exitStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PuzzleScriptVisitor ) return ((PuzzleScriptVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_statement);
		int _la;
		try {
			setState(143);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case BREAK:
				enterOuterAlt(_localctx, 1);
				{
				setState(97);
				match(BREAK);
				}
				break;
			case CONTINUE:
				enterOuterAlt(_localctx, 2);
				{
				setState(98);
				match(CONTINUE);
				}
				break;
			case BLANK_LINE:
				enterOuterAlt(_localctx, 3);
				{
				setState(99);
				match(BLANK_LINE);
				}
				break;
			case IF:
				enterOuterAlt(_localctx, 4);
				{
				setState(100);
				match(IF);
				setState(101);
				parExpression();
				{
				setState(102);
				match(LBRACE);
				setState(103);
				statements();
				setState(104);
				match(RBRACE);
				}
				setState(114);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==ELSE_IF) {
					{
					{
					setState(106);
					match(ELSE_IF);
					setState(107);
					parExpression();
					{
					setState(108);
					match(LBRACE);
					setState(109);
					statements();
					setState(110);
					match(RBRACE);
					}
					}
					}
					setState(116);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(122);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ELSE) {
					{
					setState(117);
					match(ELSE);
					{
					setState(118);
					match(LBRACE);
					setState(119);
					statements();
					setState(120);
					match(RBRACE);
					}
					}
				}

				}
				break;
			case FOR:
				enterOuterAlt(_localctx, 5);
				{
				setState(124);
				match(FOR);
				setState(125);
				match(LPAREN);
				setState(126);
				((StatementContext)_localctx).key = match(IDENTIFIER);
				setState(127);
				match(COMMA);
				setState(128);
				((StatementContext)_localctx).value = match(IDENTIFIER);
				setState(129);
				match(IN);
				setState(130);
				expression(0);
				setState(131);
				match(RPAREN);
				{
				setState(132);
				match(LBRACE);
				setState(133);
				statements();
				setState(134);
				match(RBRACE);
				}
				}
				break;
			case WHEN:
				enterOuterAlt(_localctx, 6);
				{
				setState(136);
				whenExpression();
				}
				break;
			case T__4:
				enterOuterAlt(_localctx, 7);
				{
				setState(137);
				callTask();
				}
				break;
			case IDENTIFIER:
				enterOuterAlt(_localctx, 8);
				{
				setState(138);
				callFunction();
				}
				break;
			case RETURN:
				enterOuterAlt(_localctx, 9);
				{
				setState(139);
				match(RETURN);
				setState(141);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
				case 1:
					{
					setState(140);
					expression(0);
					}
					break;
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionContext extends ParserRuleContext {
		public ExpressionContext ref;
		public ExpressionContext when;
		public ExpressionContext array;
		public ExpressionContext field;
		public ExpressionContext left;
		public Token id;
		public Token not;
		public Token prefix;
		public ExpressionContext f;
		public ExpressionContext before;
		public ExpressionContext after;
		public Token bop;
		public ExpressionContext right;
		public ExpressionContext t;
		public Token value;
		public ExpressionContext itexpr;
		public ExpressionContext start;
		public ExpressionContext end;
		public ExpressionContext index;
		public Token filter;
		public Token fop;
		public PrimaryContext primary() {
			return getRuleContext(PrimaryContext.class,0);
		}
		public TerminalNode IDENTIFIER() { return getToken(PuzzleScriptParser.IDENTIFIER, 0); }
		public CallFunctionContext callFunction() {
			return getRuleContext(CallFunctionContext.class,0);
		}
		public ListContext list() {
			return getRuleContext(ListContext.class,0);
		}
		public GroupExprContext groupExpr() {
			return getRuleContext(GroupExprContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode BANG() { return getToken(PuzzleScriptParser.BANG, 0); }
		public TerminalNode ADD() { return getToken(PuzzleScriptParser.ADD, 0); }
		public TerminalNode SUB() { return getToken(PuzzleScriptParser.SUB, 0); }
		public TerminalNode COLON() { return getToken(PuzzleScriptParser.COLON, 0); }
		public TerminalNode ARROW() { return getToken(PuzzleScriptParser.ARROW, 0); }
		public TerminalNode MUL() { return getToken(PuzzleScriptParser.MUL, 0); }
		public TerminalNode DIV() { return getToken(PuzzleScriptParser.DIV, 0); }
		public TerminalNode MOD() { return getToken(PuzzleScriptParser.MOD, 0); }
		public TerminalNode LE() { return getToken(PuzzleScriptParser.LE, 0); }
		public TerminalNode GE() { return getToken(PuzzleScriptParser.GE, 0); }
		public TerminalNode GT() { return getToken(PuzzleScriptParser.GT, 0); }
		public TerminalNode LT() { return getToken(PuzzleScriptParser.LT, 0); }
		public TerminalNode EQUAL() { return getToken(PuzzleScriptParser.EQUAL, 0); }
		public TerminalNode NOTEQUAL() { return getToken(PuzzleScriptParser.NOTEQUAL, 0); }
		public TerminalNode IN() { return getToken(PuzzleScriptParser.IN, 0); }
		public TerminalNode AND() { return getToken(PuzzleScriptParser.AND, 0); }
		public TerminalNode OR() { return getToken(PuzzleScriptParser.OR, 0); }
		public TerminalNode QUESTION() { return getToken(PuzzleScriptParser.QUESTION, 0); }
		public TerminalNode DOT() { return getToken(PuzzleScriptParser.DOT, 0); }
		public TerminalNode MAP() { return getToken(PuzzleScriptParser.MAP, 0); }
		public TerminalNode LPAREN() { return getToken(PuzzleScriptParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(PuzzleScriptParser.RPAREN, 0); }
		public TerminalNode LBRACK() { return getToken(PuzzleScriptParser.LBRACK, 0); }
		public TerminalNode RBRACK() { return getToken(PuzzleScriptParser.RBRACK, 0); }
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PuzzleScriptListener ) ((PuzzleScriptListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PuzzleScriptListener ) ((PuzzleScriptListener)listener).exitExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PuzzleScriptVisitor ) return ((PuzzleScriptVisitor<? extends T>)visitor).visitExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 12;
		enterRecursionRule(_localctx, 12, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(155);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				{
				setState(146);
				primary();
				}
				break;
			case 2:
				{
				setState(147);
				((ExpressionContext)_localctx).id = match(IDENTIFIER);
				}
				break;
			case 3:
				{
				setState(148);
				callFunction();
				}
				break;
			case 4:
				{
				setState(149);
				list();
				}
				break;
			case 5:
				{
				setState(150);
				groupExpr();
				}
				break;
			case 6:
				{
				setState(151);
				((ExpressionContext)_localctx).not = match(BANG);
				setState(152);
				expression(9);
				}
				break;
			case 7:
				{
				setState(153);
				((ExpressionContext)_localctx).prefix = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==ADD || _la==SUB) ) {
					((ExpressionContext)_localctx).prefix = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(154);
				expression(7);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(223);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(221);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
					case 1:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						_localctx.when = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(157);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(158);
						match(T__1);
						setState(159);
						((ExpressionContext)_localctx).f = expression(15);
						}
						break;
					case 2:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						_localctx.field = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(160);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(161);
						match(COLON);
						setState(162);
						((ExpressionContext)_localctx).before = expression(0);
						setState(163);
						match(ARROW);
						setState(164);
						((ExpressionContext)_localctx).after = expression(9);
						}
						break;
					case 3:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						_localctx.left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(166);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(167);
						((ExpressionContext)_localctx).bop = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 153931627888640L) != 0)) ) {
							((ExpressionContext)_localctx).bop = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(168);
						((ExpressionContext)_localctx).right = expression(7);
						}
						break;
					case 4:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						_localctx.left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(169);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(170);
						((ExpressionContext)_localctx).bop = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==ADD || _la==SUB) ) {
							((ExpressionContext)_localctx).bop = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(171);
						((ExpressionContext)_localctx).right = expression(6);
						}
						break;
					case 5:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						_localctx.left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(172);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(173);
						((ExpressionContext)_localctx).bop = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 259308654608L) != 0)) ) {
							((ExpressionContext)_localctx).bop = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(174);
						((ExpressionContext)_localctx).right = expression(5);
						}
						break;
					case 6:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						_localctx.left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(175);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(176);
						((ExpressionContext)_localctx).bop = match(AND);
						setState(177);
						((ExpressionContext)_localctx).right = expression(4);
						}
						break;
					case 7:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						_localctx.left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(178);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(179);
						((ExpressionContext)_localctx).bop = match(OR);
						setState(180);
						((ExpressionContext)_localctx).right = expression(3);
						}
						break;
					case 8:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						_localctx.when = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(181);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(182);
						match(QUESTION);
						setState(183);
						((ExpressionContext)_localctx).t = expression(0);
						setState(184);
						match(COLON);
						setState(185);
						((ExpressionContext)_localctx).f = expression(2);
						}
						break;
					case 9:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						_localctx.ref = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(187);
						if (!(precpred(_ctx, 21))) throw new FailedPredicateException(this, "precpred(_ctx, 21)");
						setState(188);
						match(DOT);
						setState(189);
						callFunction();
						}
						break;
					case 10:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						_localctx.ref = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(190);
						if (!(precpred(_ctx, 17))) throw new FailedPredicateException(this, "precpred(_ctx, 17)");
						setState(191);
						match(DOT);
						setState(192);
						((ExpressionContext)_localctx).id = match(IDENTIFIER);
						}
						break;
					case 11:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						_localctx.array = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(193);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(194);
						match(DOT);
						setState(195);
						match(MAP);
						setState(196);
						match(LPAREN);
						setState(197);
						((ExpressionContext)_localctx).value = match(IDENTIFIER);
						setState(198);
						match(ARROW);
						setState(199);
						((ExpressionContext)_localctx).itexpr = expression(0);
						setState(200);
						match(RPAREN);
						}
						break;
					case 12:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						_localctx.array = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(202);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(203);
						match(LBRACK);
						setState(204);
						((ExpressionContext)_localctx).start = expression(0);
						setState(205);
						match(COLON);
						setState(206);
						((ExpressionContext)_localctx).end = expression(0);
						setState(207);
						match(RBRACK);
						}
						break;
					case 13:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						_localctx.array = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(209);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(210);
						match(LBRACK);
						setState(211);
						((ExpressionContext)_localctx).index = expression(0);
						setState(212);
						match(RBRACK);
						}
						break;
					case 14:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						_localctx.array = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(214);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(215);
						match(T__2);
						setState(216);
						((ExpressionContext)_localctx).filter = match(IDENTIFIER);
						setState(217);
						((ExpressionContext)_localctx).fop = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 259308654592L) != 0)) ) {
							((ExpressionContext)_localctx).fop = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(218);
						((ExpressionContext)_localctx).right = expression(0);
						setState(219);
						match(RBRACK);
						}
						break;
					}
					} 
				}
				setState(225);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CallFunctionContext extends ParserRuleContext {
		public Token func;
		public TerminalNode LPAREN() { return getToken(PuzzleScriptParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(PuzzleScriptParser.RPAREN, 0); }
		public TerminalNode IDENTIFIER() { return getToken(PuzzleScriptParser.IDENTIFIER, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(PuzzleScriptParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(PuzzleScriptParser.COMMA, i);
		}
		public CallFunctionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_callFunction; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PuzzleScriptListener ) ((PuzzleScriptListener)listener).enterCallFunction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PuzzleScriptListener ) ((PuzzleScriptListener)listener).exitCallFunction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PuzzleScriptVisitor ) return ((PuzzleScriptVisitor<? extends T>)visitor).visitCallFunction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CallFunctionContext callFunction() throws RecognitionException {
		CallFunctionContext _localctx = new CallFunctionContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_callFunction);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(226);
			((CallFunctionContext)_localctx).func = match(IDENTIFIER);
			setState(227);
			match(LPAREN);
			setState(236);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 17)) & ~0x3f) == 0 && ((1L << (_la - 17)) & 4415640869814341L) != 0)) {
				{
				setState(228);
				expression(0);
				setState(233);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(229);
					match(COMMA);
					setState(230);
					expression(0);
					}
					}
					setState(235);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(238);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class GroupExprContext extends ParserRuleContext {
		public TerminalNode AT() { return getToken(PuzzleScriptParser.AT, 0); }
		public TerminalNode IDENTIFIER() { return getToken(PuzzleScriptParser.IDENTIFIER, 0); }
		public GroupExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_groupExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PuzzleScriptListener ) ((PuzzleScriptListener)listener).enterGroupExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PuzzleScriptListener ) ((PuzzleScriptListener)listener).exitGroupExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PuzzleScriptVisitor ) return ((PuzzleScriptVisitor<? extends T>)visitor).visitGroupExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GroupExprContext groupExpr() throws RecognitionException {
		GroupExprContext _localctx = new GroupExprContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_groupExpr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(240);
			match(AT);
			setState(241);
			match(IDENTIFIER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CallTaskContext extends ParserRuleContext {
		public Token behavior;
		public Token template;
		public TerminalNode LPAREN() { return getToken(PuzzleScriptParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(PuzzleScriptParser.RPAREN, 0); }
		public TerminalNode IDENTIFIER() { return getToken(PuzzleScriptParser.IDENTIFIER, 0); }
		public TerminalNode STRING_LITERAL() { return getToken(PuzzleScriptParser.STRING_LITERAL, 0); }
		public CallTaskContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_callTask; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PuzzleScriptListener ) ((PuzzleScriptListener)listener).enterCallTask(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PuzzleScriptListener ) ((PuzzleScriptListener)listener).exitCallTask(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PuzzleScriptVisitor ) return ((PuzzleScriptVisitor<? extends T>)visitor).visitCallTask(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CallTaskContext callTask() throws RecognitionException {
		CallTaskContext _localctx = new CallTaskContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_callTask);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(243);
			match(T__4);
			setState(244);
			((CallTaskContext)_localctx).behavior = match(IDENTIFIER);
			setState(245);
			match(LPAREN);
			setState(246);
			((CallTaskContext)_localctx).template = match(STRING_LITERAL);
			setState(247);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ListContext extends ParserRuleContext {
		public TerminalNode LBRACK() { return getToken(PuzzleScriptParser.LBRACK, 0); }
		public TerminalNode RBRACK() { return getToken(PuzzleScriptParser.RBRACK, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(PuzzleScriptParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(PuzzleScriptParser.COMMA, i);
		}
		public ListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PuzzleScriptListener ) ((PuzzleScriptListener)listener).enterList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PuzzleScriptListener ) ((PuzzleScriptListener)listener).exitList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PuzzleScriptVisitor ) return ((PuzzleScriptVisitor<? extends T>)visitor).visitList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ListContext list() throws RecognitionException {
		ListContext _localctx = new ListContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_list);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(249);
			match(LBRACK);
			setState(258);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 17)) & ~0x3f) == 0 && ((1L << (_la - 17)) & 4415640869814341L) != 0)) {
				{
				setState(250);
				expression(0);
				setState(255);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(251);
					match(COMMA);
					setState(252);
					expression(0);
					}
					}
					setState(257);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(260);
			match(RBRACK);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class WhenExpressionContext extends ParserRuleContext {
		public TerminalNode WHEN() { return getToken(PuzzleScriptParser.WHEN, 0); }
		public WhenStatementsContext whenStatements() {
			return getRuleContext(WhenStatementsContext.class,0);
		}
		public ParExpressionContext parExpression() {
			return getRuleContext(ParExpressionContext.class,0);
		}
		public WhenWopThenStatementsContext whenWopThenStatements() {
			return getRuleContext(WhenWopThenStatementsContext.class,0);
		}
		public WhenExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whenExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PuzzleScriptListener ) ((PuzzleScriptListener)listener).enterWhenExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PuzzleScriptListener ) ((PuzzleScriptListener)listener).exitWhenExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PuzzleScriptVisitor ) return ((PuzzleScriptVisitor<? extends T>)visitor).visitWhenExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhenExpressionContext whenExpression() throws RecognitionException {
		WhenExpressionContext _localctx = new WhenExpressionContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_whenExpression);
		try {
			setState(268);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(262);
				match(WHEN);
				setState(263);
				whenStatements();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(264);
				match(WHEN);
				setState(265);
				parExpression();
				setState(266);
				whenWopThenStatements();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class WhenStatementsContext extends ParserRuleContext {
		public TerminalNode LBRACE() { return getToken(PuzzleScriptParser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(PuzzleScriptParser.RBRACE, 0); }
		public List<WhenThenStatementContext> whenThenStatement() {
			return getRuleContexts(WhenThenStatementContext.class);
		}
		public WhenThenStatementContext whenThenStatement(int i) {
			return getRuleContext(WhenThenStatementContext.class,i);
		}
		public DefaultStatementContext defaultStatement() {
			return getRuleContext(DefaultStatementContext.class,0);
		}
		public WhenStatementsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whenStatements; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PuzzleScriptListener ) ((PuzzleScriptListener)listener).enterWhenStatements(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PuzzleScriptListener ) ((PuzzleScriptListener)listener).exitWhenStatements(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PuzzleScriptVisitor ) return ((PuzzleScriptVisitor<? extends T>)visitor).visitWhenStatements(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhenStatementsContext whenStatements() throws RecognitionException {
		WhenStatementsContext _localctx = new WhenStatementsContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_whenStatements);
		int _la;
		try {
			setState(282);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LBRACE:
				enterOuterAlt(_localctx, 1);
				{
				setState(270);
				match(LBRACE);
				setState(274);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (((((_la - 17)) & ~0x3f) == 0 && ((1L << (_la - 17)) & 4415640869814341L) != 0)) {
					{
					{
					setState(271);
					whenThenStatement();
					}
					}
					setState(276);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(278);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==DEFAULT) {
					{
					setState(277);
					defaultStatement();
					}
				}

				setState(280);
				match(RBRACE);
				}
				break;
			case EOF:
			case T__4:
			case IF:
			case FOR:
			case WHEN:
			case IN:
			case RETURN:
			case BREAK:
			case CONTINUE:
			case DEFAULT:
			case NULL_LITERAL:
			case BLANK_LINE:
			case LPAREN:
			case RBRACE:
			case LBRACK:
			case GT:
			case LT:
			case BANG:
			case EQUAL:
			case LE:
			case GE:
			case NOTEQUAL:
			case AND:
			case OR:
			case ADD:
			case SUB:
			case AT:
			case IDENTIFIER:
			case DECIMAL_LITERAL:
			case FLOAT_LITERAL:
			case BOOL_LITERAL:
			case CHAR_LITERAL:
			case STRING_LITERAL:
			case TEXT_BLOCK:
				enterOuterAlt(_localctx, 2);
				{
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class WhenThenStatementContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode COLON() { return getToken(PuzzleScriptParser.COLON, 0); }
		public StatementsContext statements() {
			return getRuleContext(StatementsContext.class,0);
		}
		public WhenThenStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whenThenStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PuzzleScriptListener ) ((PuzzleScriptListener)listener).enterWhenThenStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PuzzleScriptListener ) ((PuzzleScriptListener)listener).exitWhenThenStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PuzzleScriptVisitor ) return ((PuzzleScriptVisitor<? extends T>)visitor).visitWhenThenStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhenThenStatementContext whenThenStatement() throws RecognitionException {
		WhenThenStatementContext _localctx = new WhenThenStatementContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_whenThenStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(284);
			expression(0);
			setState(285);
			match(COLON);
			setState(286);
			statements();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DefaultStatementContext extends ParserRuleContext {
		public TerminalNode DEFAULT() { return getToken(PuzzleScriptParser.DEFAULT, 0); }
		public TerminalNode COLON() { return getToken(PuzzleScriptParser.COLON, 0); }
		public StatementsContext statements() {
			return getRuleContext(StatementsContext.class,0);
		}
		public DefaultStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_defaultStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PuzzleScriptListener ) ((PuzzleScriptListener)listener).enterDefaultStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PuzzleScriptListener ) ((PuzzleScriptListener)listener).exitDefaultStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PuzzleScriptVisitor ) return ((PuzzleScriptVisitor<? extends T>)visitor).visitDefaultStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DefaultStatementContext defaultStatement() throws RecognitionException {
		DefaultStatementContext _localctx = new DefaultStatementContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_defaultStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(288);
			match(DEFAULT);
			setState(289);
			match(COLON);
			setState(290);
			statements();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ParExpressionContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(PuzzleScriptParser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(PuzzleScriptParser.RPAREN, 0); }
		public ParExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PuzzleScriptListener ) ((PuzzleScriptListener)listener).enterParExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PuzzleScriptListener ) ((PuzzleScriptListener)listener).exitParExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PuzzleScriptVisitor ) return ((PuzzleScriptVisitor<? extends T>)visitor).visitParExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParExpressionContext parExpression() throws RecognitionException {
		ParExpressionContext _localctx = new ParExpressionContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_parExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(292);
			match(LPAREN);
			setState(293);
			expression(0);
			setState(294);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class WhenWopThenStatementsContext extends ParserRuleContext {
		public TerminalNode LBRACE() { return getToken(PuzzleScriptParser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(PuzzleScriptParser.RBRACE, 0); }
		public List<WhenWopThenStatementContext> whenWopThenStatement() {
			return getRuleContexts(WhenWopThenStatementContext.class);
		}
		public WhenWopThenStatementContext whenWopThenStatement(int i) {
			return getRuleContext(WhenWopThenStatementContext.class,i);
		}
		public DefaultStatementContext defaultStatement() {
			return getRuleContext(DefaultStatementContext.class,0);
		}
		public WhenWopThenStatementsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whenWopThenStatements; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PuzzleScriptListener ) ((PuzzleScriptListener)listener).enterWhenWopThenStatements(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PuzzleScriptListener ) ((PuzzleScriptListener)listener).exitWhenWopThenStatements(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PuzzleScriptVisitor ) return ((PuzzleScriptVisitor<? extends T>)visitor).visitWhenWopThenStatements(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhenWopThenStatementsContext whenWopThenStatements() throws RecognitionException {
		WhenWopThenStatementsContext _localctx = new WhenWopThenStatementsContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_whenWopThenStatements);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(296);
			match(LBRACE);
			setState(300);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((((_la - 12)) & ~0x3f) == 0 && ((1L << (_la - 12)) & 141300508098693281L) != 0)) {
				{
				{
				setState(297);
				whenWopThenStatement();
				}
				}
				setState(302);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(304);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==DEFAULT) {
				{
				setState(303);
				defaultStatement();
				}
			}

			setState(306);
			match(RBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class WhenWopThenStatementContext extends ParserRuleContext {
		public Token wop;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode COLON() { return getToken(PuzzleScriptParser.COLON, 0); }
		public StatementsContext statements() {
			return getRuleContext(StatementsContext.class,0);
		}
		public TerminalNode LE() { return getToken(PuzzleScriptParser.LE, 0); }
		public TerminalNode GE() { return getToken(PuzzleScriptParser.GE, 0); }
		public TerminalNode GT() { return getToken(PuzzleScriptParser.GT, 0); }
		public TerminalNode LT() { return getToken(PuzzleScriptParser.LT, 0); }
		public TerminalNode EQUAL() { return getToken(PuzzleScriptParser.EQUAL, 0); }
		public TerminalNode NOTEQUAL() { return getToken(PuzzleScriptParser.NOTEQUAL, 0); }
		public TerminalNode AND() { return getToken(PuzzleScriptParser.AND, 0); }
		public TerminalNode OR() { return getToken(PuzzleScriptParser.OR, 0); }
		public TerminalNode IN() { return getToken(PuzzleScriptParser.IN, 0); }
		public WhenWopThenStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whenWopThenStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PuzzleScriptListener ) ((PuzzleScriptListener)listener).enterWhenWopThenStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PuzzleScriptListener ) ((PuzzleScriptListener)listener).exitWhenWopThenStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PuzzleScriptVisitor ) return ((PuzzleScriptVisitor<? extends T>)visitor).visitWhenWopThenStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhenWopThenStatementContext whenWopThenStatement() throws RecognitionException {
		WhenWopThenStatementContext _localctx = new WhenWopThenStatementContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_whenWopThenStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(309);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 1083942375424L) != 0)) {
				{
				setState(308);
				((WhenWopThenStatementContext)_localctx).wop = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 1083942375424L) != 0)) ) {
					((WhenWopThenStatementContext)_localctx).wop = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
			}

			setState(311);
			expression(0);
			setState(312);
			match(COLON);
			setState(313);
			statements();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PrimaryContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(PuzzleScriptParser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(PuzzleScriptParser.RPAREN, 0); }
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public PrimaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primary; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PuzzleScriptListener ) ((PuzzleScriptListener)listener).enterPrimary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PuzzleScriptListener ) ((PuzzleScriptListener)listener).exitPrimary(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PuzzleScriptVisitor ) return ((PuzzleScriptVisitor<? extends T>)visitor).visitPrimary(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrimaryContext primary() throws RecognitionException {
		PrimaryContext _localctx = new PrimaryContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_primary);
		try {
			setState(320);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LPAREN:
				enterOuterAlt(_localctx, 1);
				{
				setState(315);
				match(LPAREN);
				setState(316);
				expression(0);
				setState(317);
				match(RPAREN);
				}
				break;
			case NULL_LITERAL:
			case DECIMAL_LITERAL:
			case FLOAT_LITERAL:
			case BOOL_LITERAL:
			case CHAR_LITERAL:
			case STRING_LITERAL:
			case TEXT_BLOCK:
				enterOuterAlt(_localctx, 2);
				{
				setState(319);
				literal();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StringItemContext extends ParserRuleContext {
		public TerminalNode UN_QUOTED_STRING() { return getToken(PuzzleScriptParser.UN_QUOTED_STRING, 0); }
		public TerminalNode IDENTIFIER() { return getToken(PuzzleScriptParser.IDENTIFIER, 0); }
		public TerminalNode DECIMAL_LITERAL() { return getToken(PuzzleScriptParser.DECIMAL_LITERAL, 0); }
		public TerminalNode STRING_LITERAL() { return getToken(PuzzleScriptParser.STRING_LITERAL, 0); }
		public StringItemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stringItem; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PuzzleScriptListener ) ((PuzzleScriptListener)listener).enterStringItem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PuzzleScriptListener ) ((PuzzleScriptListener)listener).exitStringItem(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PuzzleScriptVisitor ) return ((PuzzleScriptVisitor<? extends T>)visitor).visitStringItem(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StringItemContext stringItem() throws RecognitionException {
		StringItemContext _localctx = new StringItemContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_stringItem);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(322);
			_la = _input.LA(1);
			if ( !(((((_la - 61)) & ~0x3f) == 0 && ((1L << (_la - 61)) & 71L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LiteralContext extends ParserRuleContext {
		public IntegerLiteralContext integerLiteral() {
			return getRuleContext(IntegerLiteralContext.class,0);
		}
		public FloatLiteralContext floatLiteral() {
			return getRuleContext(FloatLiteralContext.class,0);
		}
		public TerminalNode CHAR_LITERAL() { return getToken(PuzzleScriptParser.CHAR_LITERAL, 0); }
		public TerminalNode STRING_LITERAL() { return getToken(PuzzleScriptParser.STRING_LITERAL, 0); }
		public TerminalNode BOOL_LITERAL() { return getToken(PuzzleScriptParser.BOOL_LITERAL, 0); }
		public TerminalNode NULL_LITERAL() { return getToken(PuzzleScriptParser.NULL_LITERAL, 0); }
		public TerminalNode TEXT_BLOCK() { return getToken(PuzzleScriptParser.TEXT_BLOCK, 0); }
		public LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PuzzleScriptListener ) ((PuzzleScriptListener)listener).enterLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PuzzleScriptListener ) ((PuzzleScriptListener)listener).exitLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PuzzleScriptVisitor ) return ((PuzzleScriptVisitor<? extends T>)visitor).visitLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_literal);
		try {
			setState(331);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case DECIMAL_LITERAL:
				enterOuterAlt(_localctx, 1);
				{
				setState(324);
				integerLiteral();
				}
				break;
			case FLOAT_LITERAL:
				enterOuterAlt(_localctx, 2);
				{
				setState(325);
				floatLiteral();
				}
				break;
			case CHAR_LITERAL:
				enterOuterAlt(_localctx, 3);
				{
				setState(326);
				match(CHAR_LITERAL);
				}
				break;
			case STRING_LITERAL:
				enterOuterAlt(_localctx, 4);
				{
				setState(327);
				match(STRING_LITERAL);
				}
				break;
			case BOOL_LITERAL:
				enterOuterAlt(_localctx, 5);
				{
				setState(328);
				match(BOOL_LITERAL);
				}
				break;
			case NULL_LITERAL:
				enterOuterAlt(_localctx, 6);
				{
				setState(329);
				match(NULL_LITERAL);
				}
				break;
			case TEXT_BLOCK:
				enterOuterAlt(_localctx, 7);
				{
				setState(330);
				match(TEXT_BLOCK);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class IntegerLiteralContext extends ParserRuleContext {
		public TerminalNode DECIMAL_LITERAL() { return getToken(PuzzleScriptParser.DECIMAL_LITERAL, 0); }
		public IntegerLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_integerLiteral; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PuzzleScriptListener ) ((PuzzleScriptListener)listener).enterIntegerLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PuzzleScriptListener ) ((PuzzleScriptListener)listener).exitIntegerLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PuzzleScriptVisitor ) return ((PuzzleScriptVisitor<? extends T>)visitor).visitIntegerLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IntegerLiteralContext integerLiteral() throws RecognitionException {
		IntegerLiteralContext _localctx = new IntegerLiteralContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_integerLiteral);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(333);
			match(DECIMAL_LITERAL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FloatLiteralContext extends ParserRuleContext {
		public TerminalNode FLOAT_LITERAL() { return getToken(PuzzleScriptParser.FLOAT_LITERAL, 0); }
		public FloatLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_floatLiteral; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PuzzleScriptListener ) ((PuzzleScriptListener)listener).enterFloatLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PuzzleScriptListener ) ((PuzzleScriptListener)listener).exitFloatLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PuzzleScriptVisitor ) return ((PuzzleScriptVisitor<? extends T>)visitor).visitFloatLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FloatLiteralContext floatLiteral() throws RecognitionException {
		FloatLiteralContext _localctx = new FloatLiteralContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_floatLiteral);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(335);
			match(FLOAT_LITERAL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 6:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 14);
		case 1:
			return precpred(_ctx, 8);
		case 2:
			return precpred(_ctx, 6);
		case 3:
			return precpred(_ctx, 5);
		case 4:
			return precpred(_ctx, 4);
		case 5:
			return precpred(_ctx, 3);
		case 6:
			return precpred(_ctx, 2);
		case 7:
			return precpred(_ctx, 1);
		case 8:
			return precpred(_ctx, 21);
		case 9:
			return precpred(_ctx, 17);
		case 10:
			return precpred(_ctx, 13);
		case 11:
			return precpred(_ctx, 12);
		case 12:
			return precpred(_ctx, 11);
		case 13:
			return precpred(_ctx, 10);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001H\u0152\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0002\u0016\u0007\u0016\u0001\u0000\u0005\u00000\b\u0000\n\u0000\f\u0000"+
		"3\t\u0000\u0001\u0000\u0001\u0000\u0005\u00007\b\u0000\n\u0000\f\u0000"+
		":\t\u0000\u0001\u0000\u0003\u0000=\b\u0000\u0001\u0000\u0005\u0000@\b"+
		"\u0000\n\u0000\f\u0000C\t\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001"+
		"\u0001\u0005\u0001I\b\u0001\n\u0001\f\u0001L\t\u0001\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0004\u0001\u0004\u0005\u0004X\b\u0004\n\u0004\f\u0004[\t"+
		"\u0004\u0005\u0004]\b\u0004\n\u0004\f\u0004`\t\u0004\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0005\u0005q\b\u0005\n\u0005\f\u0005t\t\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0003\u0005{\b"+
		"\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0003"+
		"\u0005\u008e\b\u0005\u0003\u0005\u0090\b\u0005\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0003\u0006\u009c\b\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0005\u0006\u00de\b\u0006\n\u0006\f\u0006\u00e1"+
		"\t\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0005"+
		"\u0007\u00e8\b\u0007\n\u0007\f\u0007\u00eb\t\u0007\u0003\u0007\u00ed\b"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\b\u0001\t\u0001\t"+
		"\u0001\t\u0001\t\u0001\t\u0001\t\u0001\n\u0001\n\u0001\n\u0001\n\u0005"+
		"\n\u00fe\b\n\n\n\f\n\u0101\t\n\u0003\n\u0103\b\n\u0001\n\u0001\n\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0003"+
		"\u000b\u010d\b\u000b\u0001\f\u0001\f\u0005\f\u0111\b\f\n\f\f\f\u0114\t"+
		"\f\u0001\f\u0003\f\u0117\b\f\u0001\f\u0001\f\u0003\f\u011b\b\f\u0001\r"+
		"\u0001\r\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u0010\u0001\u0010"+
		"\u0005\u0010\u012b\b\u0010\n\u0010\f\u0010\u012e\t\u0010\u0001\u0010\u0003"+
		"\u0010\u0131\b\u0010\u0001\u0010\u0001\u0010\u0001\u0011\u0003\u0011\u0136"+
		"\b\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0012\u0001"+
		"\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0003\u0012\u0141\b\u0012\u0001"+
		"\u0013\u0001\u0013\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001"+
		"\u0014\u0001\u0014\u0001\u0014\u0003\u0014\u014c\b\u0014\u0001\u0015\u0001"+
		"\u0015\u0001\u0016\u0001\u0016\u0001\u0016\u0000\u0001\f\u0017\u0000\u0002"+
		"\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e"+
		" \"$&(*,\u0000\u0006\u0001\u0000()\u0002\u0000*+//\u0004\u0000\u0004\u0004"+
		"\f\f\u001d\u001e\"%\u0003\u0000\f\f\u001d\u001e\"%\u0003\u0000\f\f\u001d"+
		"\u001e\"\'\u0002\u0000=?CC\u0172\u00001\u0001\u0000\u0000\u0000\u0002"+
		"J\u0001\u0000\u0000\u0000\u0004M\u0001\u0000\u0000\u0000\u0006Q\u0001"+
		"\u0000\u0000\u0000\b^\u0001\u0000\u0000\u0000\n\u008f\u0001\u0000\u0000"+
		"\u0000\f\u009b\u0001\u0000\u0000\u0000\u000e\u00e2\u0001\u0000\u0000\u0000"+
		"\u0010\u00f0\u0001\u0000\u0000\u0000\u0012\u00f3\u0001\u0000\u0000\u0000"+
		"\u0014\u00f9\u0001\u0000\u0000\u0000\u0016\u010c\u0001\u0000\u0000\u0000"+
		"\u0018\u011a\u0001\u0000\u0000\u0000\u001a\u011c\u0001\u0000\u0000\u0000"+
		"\u001c\u0120\u0001\u0000\u0000\u0000\u001e\u0124\u0001\u0000\u0000\u0000"+
		" \u0128\u0001\u0000\u0000\u0000\"\u0135\u0001\u0000\u0000\u0000$\u0140"+
		"\u0001\u0000\u0000\u0000&\u0142\u0001\u0000\u0000\u0000(\u014b\u0001\u0000"+
		"\u0000\u0000*\u014d\u0001\u0000\u0000\u0000,\u014f\u0001\u0000\u0000\u0000"+
		".0\u0005\u0012\u0000\u0000/.\u0001\u0000\u0000\u000003\u0001\u0000\u0000"+
		"\u00001/\u0001\u0000\u0000\u000012\u0001\u0000\u0000\u000024\u0001\u0000"+
		"\u0000\u000031\u0001\u0000\u0000\u000048\u0003\u0002\u0001\u000057\u0005"+
		"\u0012\u0000\u000065\u0001\u0000\u0000\u00007:\u0001\u0000\u0000\u0000"+
		"86\u0001\u0000\u0000\u000089\u0001\u0000\u0000\u00009<\u0001\u0000\u0000"+
		"\u0000:8\u0001\u0000\u0000\u0000;=\u0003\u0006\u0003\u0000<;\u0001\u0000"+
		"\u0000\u0000<=\u0001\u0000\u0000\u0000=A\u0001\u0000\u0000\u0000>@\u0005"+
		"\u0012\u0000\u0000?>\u0001\u0000\u0000\u0000@C\u0001\u0000\u0000\u0000"+
		"A?\u0001\u0000\u0000\u0000AB\u0001\u0000\u0000\u0000BD\u0001\u0000\u0000"+
		"\u0000CA\u0001\u0000\u0000\u0000DE\u0003\b\u0004\u0000EF\u0005\u0000\u0000"+
		"\u0001F\u0001\u0001\u0000\u0000\u0000GI\u0003\u0004\u0002\u0000HG\u0001"+
		"\u0000\u0000\u0000IL\u0001\u0000\u0000\u0000JH\u0001\u0000\u0000\u0000"+
		"JK\u0001\u0000\u0000\u0000K\u0003\u0001\u0000\u0000\u0000LJ\u0001\u0000"+
		"\u0000\u0000MN\u0005=\u0000\u0000NO\u0005!\u0000\u0000OP\u0003&\u0013"+
		"\u0000P\u0005\u0001\u0000\u0000\u0000QR\u0005\u0001\u0000\u0000RS\u0005"+
		"!\u0000\u0000ST\u0003\f\u0006\u0000T\u0007\u0001\u0000\u0000\u0000UY\u0003"+
		"\n\u0005\u0000VX\u0005\u0012\u0000\u0000WV\u0001\u0000\u0000\u0000X[\u0001"+
		"\u0000\u0000\u0000YW\u0001\u0000\u0000\u0000YZ\u0001\u0000\u0000\u0000"+
		"Z]\u0001\u0000\u0000\u0000[Y\u0001\u0000\u0000\u0000\\U\u0001\u0000\u0000"+
		"\u0000]`\u0001\u0000\u0000\u0000^\\\u0001\u0000\u0000\u0000^_\u0001\u0000"+
		"\u0000\u0000_\t\u0001\u0000\u0000\u0000`^\u0001\u0000\u0000\u0000a\u0090"+
		"\u0005\u000e\u0000\u0000b\u0090\u0005\u000f\u0000\u0000c\u0090\u0005\u0012"+
		"\u0000\u0000de\u0005\u0006\u0000\u0000ef\u0003\u001e\u000f\u0000fg\u0005"+
		"\u0015\u0000\u0000gh\u0003\b\u0004\u0000hi\u0005\u0016\u0000\u0000ir\u0001"+
		"\u0000\u0000\u0000jk\u0005\u0007\u0000\u0000kl\u0003\u001e\u000f\u0000"+
		"lm\u0005\u0015\u0000\u0000mn\u0003\b\u0004\u0000no\u0005\u0016\u0000\u0000"+
		"oq\u0001\u0000\u0000\u0000pj\u0001\u0000\u0000\u0000qt\u0001\u0000\u0000"+
		"\u0000rp\u0001\u0000\u0000\u0000rs\u0001\u0000\u0000\u0000sz\u0001\u0000"+
		"\u0000\u0000tr\u0001\u0000\u0000\u0000uv\u0005\b\u0000\u0000vw\u0005\u0015"+
		"\u0000\u0000wx\u0003\b\u0004\u0000xy\u0005\u0016\u0000\u0000y{\u0001\u0000"+
		"\u0000\u0000zu\u0001\u0000\u0000\u0000z{\u0001\u0000\u0000\u0000{\u0090"+
		"\u0001\u0000\u0000\u0000|}\u0005\t\u0000\u0000}~\u0005\u0013\u0000\u0000"+
		"~\u007f\u0005=\u0000\u0000\u007f\u0080\u0005\u001a\u0000\u0000\u0080\u0081"+
		"\u0005=\u0000\u0000\u0081\u0082\u0005\f\u0000\u0000\u0082\u0083\u0003"+
		"\f\u0006\u0000\u0083\u0084\u0005\u0014\u0000\u0000\u0084\u0085\u0005\u0015"+
		"\u0000\u0000\u0085\u0086\u0003\b\u0004\u0000\u0086\u0087\u0005\u0016\u0000"+
		"\u0000\u0087\u0090\u0001\u0000\u0000\u0000\u0088\u0090\u0003\u0016\u000b"+
		"\u0000\u0089\u0090\u0003\u0012\t\u0000\u008a\u0090\u0003\u000e\u0007\u0000"+
		"\u008b\u008d\u0005\r\u0000\u0000\u008c\u008e\u0003\f\u0006\u0000\u008d"+
		"\u008c\u0001\u0000\u0000\u0000\u008d\u008e\u0001\u0000\u0000\u0000\u008e"+
		"\u0090\u0001\u0000\u0000\u0000\u008fa\u0001\u0000\u0000\u0000\u008fb\u0001"+
		"\u0000\u0000\u0000\u008fc\u0001\u0000\u0000\u0000\u008fd\u0001\u0000\u0000"+
		"\u0000\u008f|\u0001\u0000\u0000\u0000\u008f\u0088\u0001\u0000\u0000\u0000"+
		"\u008f\u0089\u0001\u0000\u0000\u0000\u008f\u008a\u0001\u0000\u0000\u0000"+
		"\u008f\u008b\u0001\u0000\u0000\u0000\u0090\u000b\u0001\u0000\u0000\u0000"+
		"\u0091\u0092\u0006\u0006\uffff\uffff\u0000\u0092\u009c\u0003$\u0012\u0000"+
		"\u0093\u009c\u0005=\u0000\u0000\u0094\u009c\u0003\u000e\u0007\u0000\u0095"+
		"\u009c\u0003\u0014\n\u0000\u0096\u009c\u0003\u0010\b\u0000\u0097\u0098"+
		"\u0005\u001f\u0000\u0000\u0098\u009c\u0003\f\u0006\t\u0099\u009a\u0007"+
		"\u0000\u0000\u0000\u009a\u009c\u0003\f\u0006\u0007\u009b\u0091\u0001\u0000"+
		"\u0000\u0000\u009b\u0093\u0001\u0000\u0000\u0000\u009b\u0094\u0001\u0000"+
		"\u0000\u0000\u009b\u0095\u0001\u0000\u0000\u0000\u009b\u0096\u0001\u0000"+
		"\u0000\u0000\u009b\u0097\u0001\u0000\u0000\u0000\u009b\u0099\u0001\u0000"+
		"\u0000\u0000\u009c\u00df\u0001\u0000\u0000\u0000\u009d\u009e\n\u000e\u0000"+
		"\u0000\u009e\u009f\u0005\u0002\u0000\u0000\u009f\u00de\u0003\f\u0006\u000f"+
		"\u00a0\u00a1\n\b\u0000\u0000\u00a1\u00a2\u0005!\u0000\u0000\u00a2\u00a3"+
		"\u0003\f\u0006\u0000\u00a3\u00a4\u0005<\u0000\u0000\u00a4\u00a5\u0003"+
		"\f\u0006\t\u00a5\u00de\u0001\u0000\u0000\u0000\u00a6\u00a7\n\u0006\u0000"+
		"\u0000\u00a7\u00a8\u0007\u0001\u0000\u0000\u00a8\u00de\u0003\f\u0006\u0007"+
		"\u00a9\u00aa\n\u0005\u0000\u0000\u00aa\u00ab\u0007\u0000\u0000\u0000\u00ab"+
		"\u00de\u0003\f\u0006\u0006\u00ac\u00ad\n\u0004\u0000\u0000\u00ad\u00ae"+
		"\u0007\u0002\u0000\u0000\u00ae\u00de\u0003\f\u0006\u0005\u00af\u00b0\n"+
		"\u0003\u0000\u0000\u00b0\u00b1\u0005&\u0000\u0000\u00b1\u00de\u0003\f"+
		"\u0006\u0004\u00b2\u00b3\n\u0002\u0000\u0000\u00b3\u00b4\u0005\'\u0000"+
		"\u0000\u00b4\u00de\u0003\f\u0006\u0003\u00b5\u00b6\n\u0001\u0000\u0000"+
		"\u00b6\u00b7\u0005 \u0000\u0000\u00b7\u00b8\u0003\f\u0006\u0000\u00b8"+
		"\u00b9\u0005!\u0000\u0000\u00b9\u00ba\u0003\f\u0006\u0002\u00ba\u00de"+
		"\u0001\u0000\u0000\u0000\u00bb\u00bc\n\u0015\u0000\u0000\u00bc\u00bd\u0005"+
		"\u001b\u0000\u0000\u00bd\u00de\u0003\u000e\u0007\u0000\u00be\u00bf\n\u0011"+
		"\u0000\u0000\u00bf\u00c0\u0005\u001b\u0000\u0000\u00c0\u00de\u0005=\u0000"+
		"\u0000\u00c1\u00c2\n\r\u0000\u0000\u00c2\u00c3\u0005\u001b\u0000\u0000"+
		"\u00c3\u00c4\u0005\u000b\u0000\u0000\u00c4\u00c5\u0005\u0013\u0000\u0000"+
		"\u00c5\u00c6\u0005=\u0000\u0000\u00c6\u00c7\u0005<\u0000\u0000\u00c7\u00c8"+
		"\u0003\f\u0006\u0000\u00c8\u00c9\u0005\u0014\u0000\u0000\u00c9\u00de\u0001"+
		"\u0000\u0000\u0000\u00ca\u00cb\n\f\u0000\u0000\u00cb\u00cc\u0005\u0017"+
		"\u0000\u0000\u00cc\u00cd\u0003\f\u0006\u0000\u00cd\u00ce\u0005!\u0000"+
		"\u0000\u00ce\u00cf\u0003\f\u0006\u0000\u00cf\u00d0\u0005\u0018\u0000\u0000"+
		"\u00d0\u00de\u0001\u0000\u0000\u0000\u00d1\u00d2\n\u000b\u0000\u0000\u00d2"+
		"\u00d3\u0005\u0017\u0000\u0000\u00d3\u00d4\u0003\f\u0006\u0000\u00d4\u00d5"+
		"\u0005\u0018\u0000\u0000\u00d5\u00de\u0001\u0000\u0000\u0000\u00d6\u00d7"+
		"\n\n\u0000\u0000\u00d7\u00d8\u0005\u0003\u0000\u0000\u00d8\u00d9\u0005"+
		"=\u0000\u0000\u00d9\u00da\u0007\u0003\u0000\u0000\u00da\u00db\u0003\f"+
		"\u0006\u0000\u00db\u00dc\u0005\u0018\u0000\u0000\u00dc\u00de\u0001\u0000"+
		"\u0000\u0000\u00dd\u009d\u0001\u0000\u0000\u0000\u00dd\u00a0\u0001\u0000"+
		"\u0000\u0000\u00dd\u00a6\u0001\u0000\u0000\u0000\u00dd\u00a9\u0001\u0000"+
		"\u0000\u0000\u00dd\u00ac\u0001\u0000\u0000\u0000\u00dd\u00af\u0001\u0000"+
		"\u0000\u0000\u00dd\u00b2\u0001\u0000\u0000\u0000\u00dd\u00b5\u0001\u0000"+
		"\u0000\u0000\u00dd\u00bb\u0001\u0000\u0000\u0000\u00dd\u00be\u0001\u0000"+
		"\u0000\u0000\u00dd\u00c1\u0001\u0000\u0000\u0000\u00dd\u00ca\u0001\u0000"+
		"\u0000\u0000\u00dd\u00d1\u0001\u0000\u0000\u0000\u00dd\u00d6\u0001\u0000"+
		"\u0000\u0000\u00de\u00e1\u0001\u0000\u0000\u0000\u00df\u00dd\u0001\u0000"+
		"\u0000\u0000\u00df\u00e0\u0001\u0000\u0000\u0000\u00e0\r\u0001\u0000\u0000"+
		"\u0000\u00e1\u00df\u0001\u0000\u0000\u0000\u00e2\u00e3\u0005=\u0000\u0000"+
		"\u00e3\u00ec\u0005\u0013\u0000\u0000\u00e4\u00e9\u0003\f\u0006\u0000\u00e5"+
		"\u00e6\u0005\u001a\u0000\u0000\u00e6\u00e8\u0003\f\u0006\u0000\u00e7\u00e5"+
		"\u0001\u0000\u0000\u0000\u00e8\u00eb\u0001\u0000\u0000\u0000\u00e9\u00e7"+
		"\u0001\u0000\u0000\u0000\u00e9\u00ea\u0001\u0000\u0000\u0000\u00ea\u00ed"+
		"\u0001\u0000\u0000\u0000\u00eb\u00e9\u0001\u0000\u0000\u0000\u00ec\u00e4"+
		"\u0001\u0000\u0000\u0000\u00ec\u00ed\u0001\u0000\u0000\u0000\u00ed\u00ee"+
		"\u0001\u0000\u0000\u0000\u00ee\u00ef\u0005\u0014\u0000\u0000\u00ef\u000f"+
		"\u0001\u0000\u0000\u0000\u00f0\u00f1\u00050\u0000\u0000\u00f1\u00f2\u0005"+
		"=\u0000\u0000\u00f2\u0011\u0001\u0000\u0000\u0000\u00f3\u00f4\u0005\u0005"+
		"\u0000\u0000\u00f4\u00f5\u0005=\u0000\u0000\u00f5\u00f6\u0005\u0013\u0000"+
		"\u0000\u00f6\u00f7\u0005C\u0000\u0000\u00f7\u00f8\u0005\u0014\u0000\u0000"+
		"\u00f8\u0013\u0001\u0000\u0000\u0000\u00f9\u0102\u0005\u0017\u0000\u0000"+
		"\u00fa\u00ff\u0003\f\u0006\u0000\u00fb\u00fc\u0005\u001a\u0000\u0000\u00fc"+
		"\u00fe\u0003\f\u0006\u0000\u00fd\u00fb\u0001\u0000\u0000\u0000\u00fe\u0101"+
		"\u0001\u0000\u0000\u0000\u00ff\u00fd\u0001\u0000\u0000\u0000\u00ff\u0100"+
		"\u0001\u0000\u0000\u0000\u0100\u0103\u0001\u0000\u0000\u0000\u0101\u00ff"+
		"\u0001\u0000\u0000\u0000\u0102\u00fa\u0001\u0000\u0000\u0000\u0102\u0103"+
		"\u0001\u0000\u0000\u0000\u0103\u0104\u0001\u0000\u0000\u0000\u0104\u0105"+
		"\u0005\u0018\u0000\u0000\u0105\u0015\u0001\u0000\u0000\u0000\u0106\u0107"+
		"\u0005\n\u0000\u0000\u0107\u010d\u0003\u0018\f\u0000\u0108\u0109\u0005"+
		"\n\u0000\u0000\u0109\u010a\u0003\u001e\u000f\u0000\u010a\u010b\u0003 "+
		"\u0010\u0000\u010b\u010d\u0001\u0000\u0000\u0000\u010c\u0106\u0001\u0000"+
		"\u0000\u0000\u010c\u0108\u0001\u0000\u0000\u0000\u010d\u0017\u0001\u0000"+
		"\u0000\u0000\u010e\u0112\u0005\u0015\u0000\u0000\u010f\u0111\u0003\u001a"+
		"\r\u0000\u0110\u010f\u0001\u0000\u0000\u0000\u0111\u0114\u0001\u0000\u0000"+
		"\u0000\u0112\u0110\u0001\u0000\u0000\u0000\u0112\u0113\u0001\u0000\u0000"+
		"\u0000\u0113\u0116\u0001\u0000\u0000\u0000\u0114\u0112\u0001\u0000\u0000"+
		"\u0000\u0115\u0117\u0003\u001c\u000e\u0000\u0116\u0115\u0001\u0000\u0000"+
		"\u0000\u0116\u0117\u0001\u0000\u0000\u0000\u0117\u0118\u0001\u0000\u0000"+
		"\u0000\u0118\u011b\u0005\u0016\u0000\u0000\u0119\u011b\u0001\u0000\u0000"+
		"\u0000\u011a\u010e\u0001\u0000\u0000\u0000\u011a\u0119\u0001\u0000\u0000"+
		"\u0000\u011b\u0019\u0001\u0000\u0000\u0000\u011c\u011d\u0003\f\u0006\u0000"+
		"\u011d\u011e\u0005!\u0000\u0000\u011e\u011f\u0003\b\u0004\u0000\u011f"+
		"\u001b\u0001\u0000\u0000\u0000\u0120\u0121\u0005\u0010\u0000\u0000\u0121"+
		"\u0122\u0005!\u0000\u0000\u0122\u0123\u0003\b\u0004\u0000\u0123\u001d"+
		"\u0001\u0000\u0000\u0000\u0124\u0125\u0005\u0013\u0000\u0000\u0125\u0126"+
		"\u0003\f\u0006\u0000\u0126\u0127\u0005\u0014\u0000\u0000\u0127\u001f\u0001"+
		"\u0000\u0000\u0000\u0128\u012c\u0005\u0015\u0000\u0000\u0129\u012b\u0003"+
		"\"\u0011\u0000\u012a\u0129\u0001\u0000\u0000\u0000\u012b\u012e\u0001\u0000"+
		"\u0000\u0000\u012c\u012a\u0001\u0000\u0000\u0000\u012c\u012d\u0001\u0000"+
		"\u0000\u0000\u012d\u0130\u0001\u0000\u0000\u0000\u012e\u012c\u0001\u0000"+
		"\u0000\u0000\u012f\u0131\u0003\u001c\u000e\u0000\u0130\u012f\u0001\u0000"+
		"\u0000\u0000\u0130\u0131\u0001\u0000\u0000\u0000\u0131\u0132\u0001\u0000"+
		"\u0000\u0000\u0132\u0133\u0005\u0016\u0000\u0000\u0133!\u0001\u0000\u0000"+
		"\u0000\u0134\u0136\u0007\u0004\u0000\u0000\u0135\u0134\u0001\u0000\u0000"+
		"\u0000\u0135\u0136\u0001\u0000\u0000\u0000\u0136\u0137\u0001\u0000\u0000"+
		"\u0000\u0137\u0138\u0003\f\u0006\u0000\u0138\u0139\u0005!\u0000\u0000"+
		"\u0139\u013a\u0003\b\u0004\u0000\u013a#\u0001\u0000\u0000\u0000\u013b"+
		"\u013c\u0005\u0013\u0000\u0000\u013c\u013d\u0003\f\u0006\u0000\u013d\u013e"+
		"\u0005\u0014\u0000\u0000\u013e\u0141\u0001\u0000\u0000\u0000\u013f\u0141"+
		"\u0003(\u0014\u0000\u0140\u013b\u0001\u0000\u0000\u0000\u0140\u013f\u0001"+
		"\u0000\u0000\u0000\u0141%\u0001\u0000\u0000\u0000\u0142\u0143\u0007\u0005"+
		"\u0000\u0000\u0143\'\u0001\u0000\u0000\u0000\u0144\u014c\u0003*\u0015"+
		"\u0000\u0145\u014c\u0003,\u0016\u0000\u0146\u014c\u0005B\u0000\u0000\u0147"+
		"\u014c\u0005C\u0000\u0000\u0148\u014c\u0005A\u0000\u0000\u0149\u014c\u0005"+
		"\u0011\u0000\u0000\u014a\u014c\u0005D\u0000\u0000\u014b\u0144\u0001\u0000"+
		"\u0000\u0000\u014b\u0145\u0001\u0000\u0000\u0000\u014b\u0146\u0001\u0000"+
		"\u0000\u0000\u014b\u0147\u0001\u0000\u0000\u0000\u014b\u0148\u0001\u0000"+
		"\u0000\u0000\u014b\u0149\u0001\u0000\u0000\u0000\u014b\u014a\u0001\u0000"+
		"\u0000\u0000\u014c)\u0001\u0000\u0000\u0000\u014d\u014e\u0005>\u0000\u0000"+
		"\u014e+\u0001\u0000\u0000\u0000\u014f\u0150\u0005@\u0000\u0000\u0150-"+
		"\u0001\u0000\u0000\u0000\u001b18<AJY^rz\u008d\u008f\u009b\u00dd\u00df"+
		"\u00e9\u00ec\u00ff\u0102\u010c\u0112\u0116\u011a\u012c\u0130\u0135\u0140"+
		"\u014b";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}