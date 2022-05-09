package de.dhbw.caput.tinf20b1.ll1;

import de.dhbw.caput.tinf20b1.thompson.Token;

class NumericLiteral extends ArithmeticExpression {
	
	private final Datatype TYPE;
	private final String LEXEME;
	private final String VALUE;
	
	NumericLiteral( Token token ){
		super( );
		LEXEME = token.LEXEME;
		int split = positionOfSuffix( LEXEME );
		VALUE = LEXEME.substring(0, split);
		String suffix = LEXEME.substring( split );
		if( suffix.isEmpty() ){
			TYPE = Datatype.I32;
		}else{
			TYPE = Datatype.evaluateTypeSuffix( suffix );
		}
	}
	
	private int positionOfSuffix( String literal ){
		int pos = 0;
		for( pos = 0; pos < literal.length(); ++pos ){
			switch( literal.charAt(pos) ){
				case 'f':
				case 'i':
				case 'u':
					return pos;
			}
		}
		return pos;
	}
	
	@Override
	String generateJavaBytecode( ){
		return String.format( "%sconst_%s", TYPE, VALUE );
	}
	
	@Override
	public String toString( ){
		return LEXEME;
	}

}
