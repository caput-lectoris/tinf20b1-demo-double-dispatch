package de.dhbw.caput.tinf20b1.ll1;

import de.dhbw.caput.tinf20b1.thompson.Lexer;

class ArithmeticParser {
	
	private final Lexer LEXER;
	
	public ArithmeticParser( Lexer lexer ){
		super( );
		LEXER = lexer;
	}
	
	public ArithmeticExpression parse( String input ){
		Lexer.Instance lexi = LEXER.runOn( input );
		return sum( lexi );
	}
	
	
	/*
	 * sum : multiplication summand
	 */
	private ArithmeticExpression sum( Lexer.Instance lexer ){
		if( Tokens.MINUS == lexer.lookAhead().TYPE
				|| Tokens.LPAREN == lexer.lookAhead().TYPE
				|| Tokens.NUMBER == lexer.lookAhead().TYPE ){
			ArithmeticExpression unary = multiplication( lexer );
			ArithmeticExpression sum = summand( lexer, unary );
			return sum;
		}else{
			throw new RuntimeException( );
		}
	}
	
	
	/*
	 * summand : '+' multiplication summand
	 * summand : '-' multiplication summand
	 * summand : epsilon
	 */
	private ArithmeticExpression summand( Lexer.Instance lexer, ArithmeticExpression lhs ){
		if( Tokens.PLUS == lexer.lookAhead().TYPE ){
			advance( lexer );
			ArithmeticExpression product = multiplication( lexer );
			ArithmeticExpression sum = BinaryOperation.add( lhs, product );
			return summand( lexer, sum );
		}else if( Tokens.MINUS == lexer.lookAhead().TYPE ){
			advance( lexer );
			ArithmeticExpression product = multiplication( lexer );
			ArithmeticExpression difference = BinaryOperation.subtract( lhs, product );
			return summand( lexer, difference );
		}else if( Lexer.EOF == lexer.lookAhead().TYPE
				|| Tokens.RPAREN == lexer.lookAhead().TYPE ){
			return lhs;
		}else{
			throw new RuntimeException( );
		}
	}
	
	
	/*
	 * multiplication : unary factor
	 */
	private ArithmeticExpression multiplication( Lexer.Instance lexer ){
		if( Tokens.MINUS == lexer.lookAhead().TYPE
				|| Tokens.LPAREN == lexer.lookAhead().TYPE
				|| Tokens.NUMBER == lexer.lookAhead().TYPE ){
			ArithmeticExpression unary = unary( lexer );
			ArithmeticExpression product = factor( lexer, unary );
			return product;
		}else{
			throw new RuntimeException( );
		}
	}
	
	
	/*
	 * factor : '*' unary factor
	 * factor : '/' unary factor
	 * factor : epsilon
	 */
	private ArithmeticExpression factor( Lexer.Instance lexer, ArithmeticExpression lhs ){
		if( Tokens.MUL == lexer.lookAhead().TYPE ){
			advance( lexer );
			ArithmeticExpression unary = unary( lexer );
			ArithmeticExpression product = BinaryOperation.multiply( lhs, unary );
			return factor( lexer, product );
		}else if( Tokens.DIV == lexer.lookAhead().TYPE ){
			advance( lexer );
			ArithmeticExpression unary = unary( lexer );
			ArithmeticExpression quotient = BinaryOperation.divide( lhs, unary );
			return factor( lexer, quotient );
		}else if( Tokens.PLUS == lexer.lookAhead().TYPE
				|| Tokens.MINUS == lexer.lookAhead().TYPE
				|| Lexer.EOF == lexer.lookAhead().TYPE
				|| Tokens.RPAREN == lexer.lookAhead().TYPE ){
			return lhs;
		}else{
			throw new RuntimeException( );
		}
	}
	
	
	/*
	 * unary : power
	 * unary : '-' power
	 */
	private ArithmeticExpression unary( Lexer.Instance lexer ){
		if( Tokens.MINUS == lexer.lookAhead().TYPE ){
			advance( lexer );
			ArithmeticExpression power = power( lexer );
			return UnaryOperation.negate( power );
		}else if( Tokens.LPAREN == lexer.lookAhead().TYPE
				|| Tokens.NUMBER == lexer.lookAhead().TYPE ){
			return power( lexer );
		}else{
			throw new RuntimeException( );
		}
	}
	
	
	/*
	 * power : paren exponent
	 */
	private ArithmeticExpression power( Lexer.Instance lexer ){
		if( Tokens.LPAREN == lexer.lookAhead().TYPE
				|| Tokens.NUMBER == lexer.lookAhead().TYPE){
			ArithmeticExpression base = paren( lexer );
			ArithmeticExpression exponent = exponent( lexer, base );
			return exponent;
		}else{
			throw new RuntimeException( );
		}
	}
	
	
	/*
	 * exponent : '^' power
	 * exponent : epsilon
	 */
	private ArithmeticExpression exponent( Lexer.Instance lexer, ArithmeticExpression base ){
		if( Tokens.CIRCON == lexer.lookAhead().TYPE ){
			advance( lexer );
			ArithmeticExpression exponent = power( lexer );
			return BinaryOperation.power( base, exponent );
		}else if( Tokens.MUL == lexer.lookAhead().TYPE
				|| Tokens.DIV == lexer.lookAhead().TYPE
				|| Tokens.PLUS == lexer.lookAhead().TYPE
				|| Tokens.MINUS == lexer.lookAhead().TYPE 
				|| Lexer.EOF == lexer.lookAhead().TYPE 
				|| Tokens.RPAREN == lexer.lookAhead().TYPE ){
			return base;
		}else{
			throw new RuntimeException( );
		}
	}
	
	
	/*
	 * paren : '(' sum ')'
	 * paren : number
	 */
	private ArithmeticExpression paren( Lexer.Instance lexer ){
		if( Tokens.LPAREN == lexer.lookAhead().TYPE ){
			advance( lexer );
			ArithmeticExpression sum = sum( lexer );
			lexer.expect( Tokens.RPAREN );
			return sum;
		}else if( Tokens.NUMBER == lexer.lookAhead().TYPE ){
			NumericLiteral number = new NumericLiteral( lexer.lookAhead() );
			advance( lexer );
			return number;
		}else{
			throw new RuntimeException( );
		}
	}
	
	
	/*
	 * whitespace -> skip
	 */
	private void advance( Lexer.Instance lexer ){
		lexer.advance( );
		while( Tokens.WHITESPACE == lexer.lookAhead().TYPE ){
			lexer.advance( );
		}
	}
	
}
