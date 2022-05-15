package de.dhbw.caput.tinf20b1.ll1;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.dhbw.caput.tinf20b1.thompson.Lexer;

final class CompilerTest {
	
	private static Lexer LEXER = new Lexer();
	
	@BeforeAll
	static void constructLexer( ){
		LEXER.add( " ", Tokens.WHITESPACE );
		LEXER.add( "+", Tokens.PLUS );
		LEXER.add( "-", Tokens.MINUS );
		LEXER.add( "^", Tokens.CIRCON );
		LEXER.add( "[", Tokens.LPAREN );
		LEXER.add( "]", Tokens.RPAREN );
		LEXER.add( ".", Tokens.MUL );
		LEXER.add( "/", Tokens.DIV );
		LEXER.add( "0|(1|2|3|4|5|6|7|8|9)·(0|1|2|3|4|5|6|7|8|9)*·(ε|d|f)", Tokens.NUMBER );
	}
	
	@Test
	void lexicalAnalysis( ){
		Lexer.Instance lexi = LEXER.runOn( "-[5/1f]+[5/1]^2-3" );
		
		assertThat( lexi.lookAhead().TYPE ).isSameAs( Tokens.MINUS );
		lexi.advance();
		assertThat( lexi.lookAhead().TYPE ).isSameAs( Tokens.LPAREN );
		lexi.advance();
		assertThat( lexi.lookAhead().TYPE ).isSameAs( Tokens.NUMBER );
		lexi.advance();
		assertThat( lexi.lookAhead().TYPE ).isSameAs( Tokens.DIV );
		lexi.advance();
		assertThat( lexi.lookAhead().TYPE ).isSameAs( Tokens.NUMBER );
		lexi.advance();
		assertThat( lexi.lookAhead().TYPE ).isSameAs( Tokens.RPAREN );
		lexi.advance();
		assertThat( lexi.lookAhead().TYPE ).isSameAs( Tokens.PLUS );
		lexi.advance();
		assertThat( lexi.lookAhead().TYPE ).isSameAs( Tokens.LPAREN );
		lexi.advance();
		assertThat( lexi.lookAhead().TYPE ).isSameAs( Tokens.NUMBER );
		lexi.advance();
		assertThat( lexi.lookAhead().TYPE ).isSameAs( Tokens.DIV );
		lexi.advance();
		assertThat( lexi.lookAhead().TYPE ).isSameAs( Tokens.NUMBER );
		lexi.advance();
		assertThat( lexi.lookAhead().TYPE ).isSameAs( Tokens.RPAREN );
		lexi.advance();
		assertThat( lexi.lookAhead().TYPE ).isSameAs( Tokens.CIRCON );
		lexi.advance();
		assertThat( lexi.lookAhead().TYPE ).isSameAs( Tokens.NUMBER );
		lexi.advance();
		assertThat( lexi.lookAhead().TYPE ).isSameAs( Tokens.MINUS );
		lexi.advance();
		assertThat( lexi.lookAhead().TYPE ).isSameAs( Tokens.NUMBER );
		lexi.advance();
		assertThat( lexi.lookAhead().TYPE ).isSameAs( Lexer.EOF );
		lexi.advance();
		assertThat( lexi.lookAhead().TYPE ).isSameAs( Lexer.EOF );
	}
	
	@Test
	void syntacticAnalysis( ){
		ArithmeticParser parser = new ArithmeticParser( LEXER );
		ArithmeticExpression expression = parser.parse( "-[5/1f]+[5/1]^2-3" );
		assertThat( expression.toString() ).isEqualTo( "((-((5 / 1f)) + ((5 / 1) ^ 2)) - 3)" );
	}
	
	@Test
	void typeInference( ){
		ArithmeticParser parser = new ArithmeticParser( LEXER );
		ArithmeticExpression expression = parser.parse( "-[5/1f]+[5/1]^2-3" );
		assertThat( expression.type ).isNull( );
		expression.inferTypes( );
		assertThat( expression.type ).isEqualTo( Datatype.F32 );
	}
	
	@Test
	void codeGeneration( ){
		ArithmeticParser parser = new ArithmeticParser( LEXER );
		ArithmeticExpression expression = parser.parse( "-[5/1f]+[5/1]^2-3" );
		String code = JavaBytecodeGenerator.runOn(expression);
		assertThat( code ).isEqualTo(
				"iconst_5\n" +
				"fconst_1\n" +
				"idiv\n" +
				"ineg\n" +
				"iconst_5\n" +
				"iconst_1\n" +
				"idiv\n" +
				"iconst_2\n" +
				"ipow\n" +
				"iadd\n" +
				"iconst_3\n" +
				"isub"
		);
	}

}
