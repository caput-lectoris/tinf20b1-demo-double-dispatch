package de.dhbw.caput.tinf20b1.ll1;

import de.dhbw.caput.tinf20b1.thompson.Token;

class Tokens {
	
	static final Token.Type WHITESPACE = new Token.Type( "whitespace" );
	static final Token.Type LPAREN     = new Token.Type( "lparen" );
	static final Token.Type RPAREN     = new Token.Type( "rparen" );
	static final Token.Type NUMBER     = new Token.Type( "number" );
	static final Token.Type PLUS       = new Token.Type( "plus" );
	static final Token.Type MINUS      = new Token.Type( "minus" );
	static final Token.Type CIRCON     = new Token.Type( "circon" );
	static final Token.Type MUL        = new Token.Type( "mul" );
	static final Token.Type DIV        = new Token.Type( "div" );

}
