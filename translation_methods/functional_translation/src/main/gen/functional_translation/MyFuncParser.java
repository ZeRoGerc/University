// Generated from /Users/zerogerc/Documents/University/repository/University/translation_methods/functional_translation/src/main/java/functional_translation/MyFunc.g4 by ANTLR 4.7
package functional_translation;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MyFuncParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, MUL=11, DIV=12, ADD=13, SUB=14, MOD=15, AND=16, OR=17, NOT=18, 
		EQ=19, NE=20, GT=21, GE=22, LT=23, LE=24, TYPE=25, BOOL=26, ID=27, INT=28, 
		WS=29;
	public static final int
		RULE_prog = 0, RULE_fun = 1, RULE_constr = 2, RULE_vars = 3, RULE_stat = 4, 
		RULE_e = 5, RULE_funcInvocation = 6, RULE_boolExpr = 7, RULE_unaryExpr = 8, 
		RULE_expr = 9;
	public static final String[] ruleNames = {
		"prog", "fun", "constr", "vars", "stat", "e", "funcInvocation", "boolExpr", 
		"unaryExpr", "expr"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'::'", "'->'", "';'", "'='", "'if'", "'{'", "'}'", "'else'", "'('", 
		"')'", "'*'", "'/'", "'+'", "'-'", "'%'", "'&&'", "'||'", "'!'", "'=='", 
		"'!='", "'>'", "'>='", "'<'", "'<='"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, "MUL", 
		"DIV", "ADD", "SUB", "MOD", "AND", "OR", "NOT", "EQ", "NE", "GT", "GE", 
		"LT", "LE", "TYPE", "BOOL", "ID", "INT", "WS"
	};
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
	public String getGrammarFileName() { return "MyFunc.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public MyFuncParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ProgContext extends ParserRuleContext {
		public List<FunContext> fun() {
			return getRuleContexts(FunContext.class);
		}
		public FunContext fun(int i) {
			return getRuleContext(FunContext.class,i);
		}
		public ProgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prog; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyFuncListener ) ((MyFuncListener)listener).enterProg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyFuncListener ) ((MyFuncListener)listener).exitProg(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyFuncVisitor ) return ((MyFuncVisitor<? extends T>)visitor).visitProg(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgContext prog() throws RecognitionException {
		ProgContext _localctx = new ProgContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_prog);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(23);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ID) {
				{
				{
				setState(20);
				fun();
				}
				}
				setState(25);
				_errHandler.sync(this);
				_la = _input.LA(1);
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

	public static class FunContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(MyFuncParser.ID, 0); }
		public List<TerminalNode> TYPE() { return getTokens(MyFuncParser.TYPE); }
		public TerminalNode TYPE(int i) {
			return getToken(MyFuncParser.TYPE, i);
		}
		public List<ConstrContext> constr() {
			return getRuleContexts(ConstrContext.class);
		}
		public ConstrContext constr(int i) {
			return getRuleContext(ConstrContext.class,i);
		}
		public FunContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fun; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyFuncListener ) ((MyFuncListener)listener).enterFun(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyFuncListener ) ((MyFuncListener)listener).exitFun(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyFuncVisitor ) return ((MyFuncVisitor<? extends T>)visitor).visitFun(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunContext fun() throws RecognitionException {
		FunContext _localctx = new FunContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_fun);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(26);
			match(ID);
			setState(27);
			match(T__0);
			setState(28);
			match(TYPE);
			setState(33);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__1) {
				{
				{
				setState(29);
				match(T__1);
				setState(30);
				match(TYPE);
				}
				}
				setState(35);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(36);
			match(T__2);
			setState(38); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(37);
					constr();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(40); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
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

	public static class ConstrContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(MyFuncParser.ID, 0); }
		public VarsContext vars() {
			return getRuleContext(VarsContext.class,0);
		}
		public StatContext stat() {
			return getRuleContext(StatContext.class,0);
		}
		public ConstrContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyFuncListener ) ((MyFuncListener)listener).enterConstr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyFuncListener ) ((MyFuncListener)listener).exitConstr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyFuncVisitor ) return ((MyFuncVisitor<? extends T>)visitor).visitConstr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstrContext constr() throws RecognitionException {
		ConstrContext _localctx = new ConstrContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_constr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(42);
			match(ID);
			setState(43);
			vars();
			setState(44);
			match(T__3);
			setState(45);
			stat();
			setState(46);
			match(T__2);
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

	public static class VarsContext extends ParserRuleContext {
		public List<TerminalNode> ID() { return getTokens(MyFuncParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(MyFuncParser.ID, i);
		}
		public List<TerminalNode> INT() { return getTokens(MyFuncParser.INT); }
		public TerminalNode INT(int i) {
			return getToken(MyFuncParser.INT, i);
		}
		public List<TerminalNode> BOOL() { return getTokens(MyFuncParser.BOOL); }
		public TerminalNode BOOL(int i) {
			return getToken(MyFuncParser.BOOL, i);
		}
		public VarsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_vars; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyFuncListener ) ((MyFuncListener)listener).enterVars(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyFuncListener ) ((MyFuncListener)listener).exitVars(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyFuncVisitor ) return ((MyFuncVisitor<? extends T>)visitor).visitVars(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarsContext vars() throws RecognitionException {
		VarsContext _localctx = new VarsContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_vars);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(51);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOL) | (1L << ID) | (1L << INT))) != 0)) {
				{
				{
				setState(48);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOL) | (1L << ID) | (1L << INT))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				}
				setState(53);
				_errHandler.sync(this);
				_la = _input.LA(1);
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

	public static class StatContext extends ParserRuleContext {
		public StatContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stat; }
	 
		public StatContext() { }
		public void copyFrom(StatContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class StatEContext extends StatContext {
		public EContext e() {
			return getRuleContext(EContext.class,0);
		}
		public StatEContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyFuncListener ) ((MyFuncListener)listener).enterStatE(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyFuncListener ) ((MyFuncListener)listener).exitStatE(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyFuncVisitor ) return ((MyFuncVisitor<? extends T>)visitor).visitStatE(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StatIfContext extends StatContext {
		public BoolExprContext boolExpr() {
			return getRuleContext(BoolExprContext.class,0);
		}
		public List<StatContext> stat() {
			return getRuleContexts(StatContext.class);
		}
		public StatContext stat(int i) {
			return getRuleContext(StatContext.class,i);
		}
		public StatIfContext(StatContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyFuncListener ) ((MyFuncListener)listener).enterStatIf(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyFuncListener ) ((MyFuncListener)listener).exitStatIf(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyFuncVisitor ) return ((MyFuncVisitor<? extends T>)visitor).visitStatIf(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatContext stat() throws RecognitionException {
		StatContext _localctx = new StatContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_stat);
		try {
			setState(65);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__8:
			case SUB:
			case NOT:
			case BOOL:
			case ID:
			case INT:
				_localctx = new StatEContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(54);
				e();
				}
				break;
			case T__4:
				_localctx = new StatIfContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(55);
				match(T__4);
				setState(56);
				boolExpr(0);
				setState(57);
				match(T__5);
				setState(58);
				stat();
				setState(59);
				match(T__6);
				setState(60);
				match(T__7);
				setState(61);
				match(T__5);
				setState(62);
				stat();
				setState(63);
				match(T__6);
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

	public static class EContext extends ParserRuleContext {
		public BoolExprContext boolExpr() {
			return getRuleContext(BoolExprContext.class,0);
		}
		public UnaryExprContext unaryExpr() {
			return getRuleContext(UnaryExprContext.class,0);
		}
		public EContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_e; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyFuncListener ) ((MyFuncListener)listener).enterE(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyFuncListener ) ((MyFuncListener)listener).exitE(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyFuncVisitor ) return ((MyFuncVisitor<? extends T>)visitor).visitE(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EContext e() throws RecognitionException {
		EContext _localctx = new EContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_e);
		try {
			setState(69);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(67);
				boolExpr(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(68);
				unaryExpr();
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

	public static class FuncInvocationContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(MyFuncParser.ID, 0); }
		public List<EContext> e() {
			return getRuleContexts(EContext.class);
		}
		public EContext e(int i) {
			return getRuleContext(EContext.class,i);
		}
		public FuncInvocationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funcInvocation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyFuncListener ) ((MyFuncListener)listener).enterFuncInvocation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyFuncListener ) ((MyFuncListener)listener).exitFuncInvocation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyFuncVisitor ) return ((MyFuncVisitor<? extends T>)visitor).visitFuncInvocation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FuncInvocationContext funcInvocation() throws RecognitionException {
		FuncInvocationContext _localctx = new FuncInvocationContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_funcInvocation);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(71);
			match(T__8);
			setState(72);
			match(ID);
			setState(76);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__8) | (1L << SUB) | (1L << NOT) | (1L << BOOL) | (1L << ID) | (1L << INT))) != 0)) {
				{
				{
				setState(73);
				e();
				}
				}
				setState(78);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(79);
			match(T__9);
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

	public static class BoolExprContext extends ParserRuleContext {
		public BoolExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_boolExpr; }
	 
		public BoolExprContext() { }
		public void copyFrom(BoolExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class BoolExprIdContext extends BoolExprContext {
		public TerminalNode ID() { return getToken(MyFuncParser.ID, 0); }
		public BoolExprIdContext(BoolExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyFuncListener ) ((MyFuncListener)listener).enterBoolExprId(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyFuncListener ) ((MyFuncListener)listener).exitBoolExprId(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyFuncVisitor ) return ((MyFuncVisitor<? extends T>)visitor).visitBoolExprId(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BoolExprExprEqContext extends BoolExprContext {
		public Token op;
		public List<UnaryExprContext> unaryExpr() {
			return getRuleContexts(UnaryExprContext.class);
		}
		public UnaryExprContext unaryExpr(int i) {
			return getRuleContext(UnaryExprContext.class,i);
		}
		public TerminalNode EQ() { return getToken(MyFuncParser.EQ, 0); }
		public TerminalNode NE() { return getToken(MyFuncParser.NE, 0); }
		public TerminalNode GT() { return getToken(MyFuncParser.GT, 0); }
		public TerminalNode GE() { return getToken(MyFuncParser.GE, 0); }
		public TerminalNode LT() { return getToken(MyFuncParser.LT, 0); }
		public TerminalNode LE() { return getToken(MyFuncParser.LE, 0); }
		public BoolExprExprEqContext(BoolExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyFuncListener ) ((MyFuncListener)listener).enterBoolExprExprEq(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyFuncListener ) ((MyFuncListener)listener).exitBoolExprExprEq(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyFuncVisitor ) return ((MyFuncVisitor<? extends T>)visitor).visitBoolExprExprEq(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BoolExprBoolExprEqContext extends BoolExprContext {
		public Token op;
		public List<BoolExprContext> boolExpr() {
			return getRuleContexts(BoolExprContext.class);
		}
		public BoolExprContext boolExpr(int i) {
			return getRuleContext(BoolExprContext.class,i);
		}
		public TerminalNode EQ() { return getToken(MyFuncParser.EQ, 0); }
		public TerminalNode NE() { return getToken(MyFuncParser.NE, 0); }
		public BoolExprBoolExprEqContext(BoolExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyFuncListener ) ((MyFuncListener)listener).enterBoolExprBoolExprEq(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyFuncListener ) ((MyFuncListener)listener).exitBoolExprBoolExprEq(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyFuncVisitor ) return ((MyFuncVisitor<? extends T>)visitor).visitBoolExprBoolExprEq(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BoolExprNotContext extends BoolExprContext {
		public TerminalNode NOT() { return getToken(MyFuncParser.NOT, 0); }
		public BoolExprContext boolExpr() {
			return getRuleContext(BoolExprContext.class,0);
		}
		public BoolExprNotContext(BoolExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyFuncListener ) ((MyFuncListener)listener).enterBoolExprNot(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyFuncListener ) ((MyFuncListener)listener).exitBoolExprNot(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyFuncVisitor ) return ((MyFuncVisitor<? extends T>)visitor).visitBoolExprNot(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BoolExprFuncContext extends BoolExprContext {
		public FuncInvocationContext funcInvocation() {
			return getRuleContext(FuncInvocationContext.class,0);
		}
		public BoolExprFuncContext(BoolExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyFuncListener ) ((MyFuncListener)listener).enterBoolExprFunc(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyFuncListener ) ((MyFuncListener)listener).exitBoolExprFunc(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyFuncVisitor ) return ((MyFuncVisitor<? extends T>)visitor).visitBoolExprFunc(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BoolExprBoolContext extends BoolExprContext {
		public TerminalNode BOOL() { return getToken(MyFuncParser.BOOL, 0); }
		public BoolExprBoolContext(BoolExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyFuncListener ) ((MyFuncListener)listener).enterBoolExprBool(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyFuncListener ) ((MyFuncListener)listener).exitBoolExprBool(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyFuncVisitor ) return ((MyFuncVisitor<? extends T>)visitor).visitBoolExprBool(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BoolExprParensContext extends BoolExprContext {
		public BoolExprContext boolExpr() {
			return getRuleContext(BoolExprContext.class,0);
		}
		public BoolExprParensContext(BoolExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyFuncListener ) ((MyFuncListener)listener).enterBoolExprParens(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyFuncListener ) ((MyFuncListener)listener).exitBoolExprParens(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyFuncVisitor ) return ((MyFuncVisitor<? extends T>)visitor).visitBoolExprParens(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BoolExprOpContext extends BoolExprContext {
		public Token op;
		public List<BoolExprContext> boolExpr() {
			return getRuleContexts(BoolExprContext.class);
		}
		public BoolExprContext boolExpr(int i) {
			return getRuleContext(BoolExprContext.class,i);
		}
		public TerminalNode AND() { return getToken(MyFuncParser.AND, 0); }
		public TerminalNode OR() { return getToken(MyFuncParser.OR, 0); }
		public BoolExprOpContext(BoolExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyFuncListener ) ((MyFuncListener)listener).enterBoolExprOp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyFuncListener ) ((MyFuncListener)listener).exitBoolExprOp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyFuncVisitor ) return ((MyFuncVisitor<? extends T>)visitor).visitBoolExprOp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BoolExprContext boolExpr() throws RecognitionException {
		return boolExpr(0);
	}

	private BoolExprContext boolExpr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		BoolExprContext _localctx = new BoolExprContext(_ctx, _parentState);
		BoolExprContext _prevctx = _localctx;
		int _startState = 14;
		enterRecursionRule(_localctx, 14, RULE_boolExpr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(95);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				{
				_localctx = new BoolExprIdContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(82);
				match(ID);
				}
				break;
			case 2:
				{
				_localctx = new BoolExprBoolContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(83);
				match(BOOL);
				}
				break;
			case 3:
				{
				_localctx = new BoolExprNotContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(84);
				match(NOT);
				setState(85);
				boolExpr(5);
				}
				break;
			case 4:
				{
				_localctx = new BoolExprExprEqContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(86);
				unaryExpr();
				setState(87);
				((BoolExprExprEqContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << EQ) | (1L << NE) | (1L << GT) | (1L << GE) | (1L << LT) | (1L << LE))) != 0)) ) {
					((BoolExprExprEqContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(88);
				unaryExpr();
				}
				break;
			case 5:
				{
				_localctx = new BoolExprFuncContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(90);
				funcInvocation();
				}
				break;
			case 6:
				{
				_localctx = new BoolExprParensContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(91);
				match(T__8);
				setState(92);
				boolExpr(0);
				setState(93);
				match(T__9);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(105);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(103);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
					case 1:
						{
						_localctx = new BoolExprOpContext(new BoolExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_boolExpr);
						setState(97);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(98);
						((BoolExprOpContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==AND || _la==OR) ) {
							((BoolExprOpContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(99);
						boolExpr(9);
						}
						break;
					case 2:
						{
						_localctx = new BoolExprBoolExprEqContext(new BoolExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_boolExpr);
						setState(100);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(101);
						((BoolExprBoolExprEqContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==EQ || _la==NE) ) {
							((BoolExprBoolExprEqContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(102);
						boolExpr(4);
						}
						break;
					}
					} 
				}
				setState(107);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
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

	public static class UnaryExprContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public UnaryExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unaryExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyFuncListener ) ((MyFuncListener)listener).enterUnaryExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyFuncListener ) ((MyFuncListener)listener).exitUnaryExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyFuncVisitor ) return ((MyFuncVisitor<? extends T>)visitor).visitUnaryExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UnaryExprContext unaryExpr() throws RecognitionException {
		UnaryExprContext _localctx = new UnaryExprContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_unaryExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(109);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SUB) {
				{
				setState(108);
				match(SUB);
				}
			}

			setState(111);
			expr(0);
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

	public static class ExprContext extends ParserRuleContext {
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
	 
		public ExprContext() { }
		public void copyFrom(ExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ExprIntContext extends ExprContext {
		public TerminalNode INT() { return getToken(MyFuncParser.INT, 0); }
		public ExprIntContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyFuncListener ) ((MyFuncListener)listener).enterExprInt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyFuncListener ) ((MyFuncListener)listener).exitExprInt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyFuncVisitor ) return ((MyFuncVisitor<? extends T>)visitor).visitExprInt(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExprFuncContext extends ExprContext {
		public FuncInvocationContext funcInvocation() {
			return getRuleContext(FuncInvocationContext.class,0);
		}
		public ExprFuncContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyFuncListener ) ((MyFuncListener)listener).enterExprFunc(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyFuncListener ) ((MyFuncListener)listener).exitExprFunc(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyFuncVisitor ) return ((MyFuncVisitor<? extends T>)visitor).visitExprFunc(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExprIdContext extends ExprContext {
		public TerminalNode ID() { return getToken(MyFuncParser.ID, 0); }
		public ExprIdContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyFuncListener ) ((MyFuncListener)listener).enterExprId(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyFuncListener ) ((MyFuncListener)listener).exitExprId(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyFuncVisitor ) return ((MyFuncVisitor<? extends T>)visitor).visitExprId(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExprParensContext extends ExprContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ExprParensContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyFuncListener ) ((MyFuncListener)listener).enterExprParens(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyFuncListener ) ((MyFuncListener)listener).exitExprParens(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyFuncVisitor ) return ((MyFuncVisitor<? extends T>)visitor).visitExprParens(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExprOpContext extends ExprContext {
		public Token op;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode ADD() { return getToken(MyFuncParser.ADD, 0); }
		public TerminalNode SUB() { return getToken(MyFuncParser.SUB, 0); }
		public TerminalNode MUL() { return getToken(MyFuncParser.MUL, 0); }
		public TerminalNode DIV() { return getToken(MyFuncParser.DIV, 0); }
		public TerminalNode MOD() { return getToken(MyFuncParser.MOD, 0); }
		public ExprOpContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyFuncListener ) ((MyFuncListener)listener).enterExprOp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyFuncListener ) ((MyFuncListener)listener).exitExprOp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyFuncVisitor ) return ((MyFuncVisitor<? extends T>)visitor).visitExprOp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		return expr(0);
	}

	private ExprContext expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExprContext _localctx = new ExprContext(_ctx, _parentState);
		ExprContext _prevctx = _localctx;
		int _startState = 18;
		enterRecursionRule(_localctx, 18, RULE_expr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(121);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				{
				_localctx = new ExprIdContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(114);
				match(ID);
				}
				break;
			case 2:
				{
				_localctx = new ExprIntContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(115);
				match(INT);
				}
				break;
			case 3:
				{
				_localctx = new ExprFuncContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(116);
				funcInvocation();
				}
				break;
			case 4:
				{
				_localctx = new ExprParensContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(117);
				match(T__8);
				setState(118);
				expr(0);
				setState(119);
				match(T__9);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(128);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new ExprOpContext(new ExprContext(_parentctx, _parentState));
					pushNewRecursionContext(_localctx, _startState, RULE_expr);
					setState(123);
					if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
					setState(124);
					((ExprOpContext)_localctx).op = _input.LT(1);
					_la = _input.LA(1);
					if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MUL) | (1L << DIV) | (1L << ADD) | (1L << SUB) | (1L << MOD))) != 0)) ) {
						((ExprOpContext)_localctx).op = (Token)_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					setState(125);
					expr(6);
					}
					} 
				}
				setState(130);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
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

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 7:
			return boolExpr_sempred((BoolExprContext)_localctx, predIndex);
		case 9:
			return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean boolExpr_sempred(BoolExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 8);
		case 1:
			return precpred(_ctx, 3);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 2:
			return precpred(_ctx, 5);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\37\u0086\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\3\2\7\2\30\n\2\f\2\16\2\33\13\2\3\3\3\3\3\3\3\3\3\3\7\3\"\n\3\f"+
		"\3\16\3%\13\3\3\3\3\3\6\3)\n\3\r\3\16\3*\3\4\3\4\3\4\3\4\3\4\3\4\3\5\7"+
		"\5\64\n\5\f\5\16\5\67\13\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6"+
		"\5\6D\n\6\3\7\3\7\5\7H\n\7\3\b\3\b\3\b\7\bM\n\b\f\b\16\bP\13\b\3\b\3\b"+
		"\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\5\tb\n\t\3\t"+
		"\3\t\3\t\3\t\3\t\3\t\7\tj\n\t\f\t\16\tm\13\t\3\n\5\np\n\n\3\n\3\n\3\13"+
		"\3\13\3\13\3\13\3\13\3\13\3\13\3\13\5\13|\n\13\3\13\3\13\3\13\7\13\u0081"+
		"\n\13\f\13\16\13\u0084\13\13\3\13\2\4\20\24\f\2\4\6\b\n\f\16\20\22\24"+
		"\2\7\3\2\34\36\3\2\25\32\3\2\22\23\3\2\25\26\3\2\r\21\2\u008e\2\31\3\2"+
		"\2\2\4\34\3\2\2\2\6,\3\2\2\2\b\65\3\2\2\2\nC\3\2\2\2\fG\3\2\2\2\16I\3"+
		"\2\2\2\20a\3\2\2\2\22o\3\2\2\2\24{\3\2\2\2\26\30\5\4\3\2\27\26\3\2\2\2"+
		"\30\33\3\2\2\2\31\27\3\2\2\2\31\32\3\2\2\2\32\3\3\2\2\2\33\31\3\2\2\2"+
		"\34\35\7\35\2\2\35\36\7\3\2\2\36#\7\33\2\2\37 \7\4\2\2 \"\7\33\2\2!\37"+
		"\3\2\2\2\"%\3\2\2\2#!\3\2\2\2#$\3\2\2\2$&\3\2\2\2%#\3\2\2\2&(\7\5\2\2"+
		"\')\5\6\4\2(\'\3\2\2\2)*\3\2\2\2*(\3\2\2\2*+\3\2\2\2+\5\3\2\2\2,-\7\35"+
		"\2\2-.\5\b\5\2./\7\6\2\2/\60\5\n\6\2\60\61\7\5\2\2\61\7\3\2\2\2\62\64"+
		"\t\2\2\2\63\62\3\2\2\2\64\67\3\2\2\2\65\63\3\2\2\2\65\66\3\2\2\2\66\t"+
		"\3\2\2\2\67\65\3\2\2\28D\5\f\7\29:\7\7\2\2:;\5\20\t\2;<\7\b\2\2<=\5\n"+
		"\6\2=>\7\t\2\2>?\7\n\2\2?@\7\b\2\2@A\5\n\6\2AB\7\t\2\2BD\3\2\2\2C8\3\2"+
		"\2\2C9\3\2\2\2D\13\3\2\2\2EH\5\20\t\2FH\5\22\n\2GE\3\2\2\2GF\3\2\2\2H"+
		"\r\3\2\2\2IJ\7\13\2\2JN\7\35\2\2KM\5\f\7\2LK\3\2\2\2MP\3\2\2\2NL\3\2\2"+
		"\2NO\3\2\2\2OQ\3\2\2\2PN\3\2\2\2QR\7\f\2\2R\17\3\2\2\2ST\b\t\1\2Tb\7\35"+
		"\2\2Ub\7\34\2\2VW\7\24\2\2Wb\5\20\t\7XY\5\22\n\2YZ\t\3\2\2Z[\5\22\n\2"+
		"[b\3\2\2\2\\b\5\16\b\2]^\7\13\2\2^_\5\20\t\2_`\7\f\2\2`b\3\2\2\2aS\3\2"+
		"\2\2aU\3\2\2\2aV\3\2\2\2aX\3\2\2\2a\\\3\2\2\2a]\3\2\2\2bk\3\2\2\2cd\f"+
		"\n\2\2de\t\4\2\2ej\5\20\t\13fg\f\5\2\2gh\t\5\2\2hj\5\20\t\6ic\3\2\2\2"+
		"if\3\2\2\2jm\3\2\2\2ki\3\2\2\2kl\3\2\2\2l\21\3\2\2\2mk\3\2\2\2np\7\20"+
		"\2\2on\3\2\2\2op\3\2\2\2pq\3\2\2\2qr\5\24\13\2r\23\3\2\2\2st\b\13\1\2"+
		"t|\7\35\2\2u|\7\36\2\2v|\5\16\b\2wx\7\13\2\2xy\5\24\13\2yz\7\f\2\2z|\3"+
		"\2\2\2{s\3\2\2\2{u\3\2\2\2{v\3\2\2\2{w\3\2\2\2|\u0082\3\2\2\2}~\f\7\2"+
		"\2~\177\t\6\2\2\177\u0081\5\24\13\b\u0080}\3\2\2\2\u0081\u0084\3\2\2\2"+
		"\u0082\u0080\3\2\2\2\u0082\u0083\3\2\2\2\u0083\25\3\2\2\2\u0084\u0082"+
		"\3\2\2\2\17\31#*\65CGNaiko{\u0082";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}