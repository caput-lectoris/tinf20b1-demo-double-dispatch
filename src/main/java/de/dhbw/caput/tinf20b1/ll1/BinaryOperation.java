package de.dhbw.caput.tinf20b1.ll1;

class BinaryOperation extends ArithmeticExpression {
	
	enum Type {
		ADDITION( '+', "iadd" ), SUBTRACTION( '-', "isub" ), MULTIPLICATION( '*', "imul" ),
		DIVISION( '/', "idiv" ), POWER( '^', "ipow" );

		private final String INSTRUCTION;
		private final char OPERATOR;

		private Type( char operator, String instruction ){
			OPERATOR = operator;
			INSTRUCTION = instruction;
		}
	};
	
	final ArithmeticExpression LEFT, RIGHT;
	final Type OPERATION;
	
	private BinaryOperation( ArithmeticExpression left, Type operation, ArithmeticExpression right ){
		super( );
		LEFT  = left;
		RIGHT = right;
		OPERATION = operation;
	}
	
	static BinaryOperation add( ArithmeticExpression left, ArithmeticExpression right ){
		return new BinaryOperation( left, Type.ADDITION, right );
	}
	static BinaryOperation subtract( ArithmeticExpression left, ArithmeticExpression right ){
		return new BinaryOperation( left, Type.SUBTRACTION, right );
	}
	static BinaryOperation multiply( ArithmeticExpression left, ArithmeticExpression right ){
		return new BinaryOperation( left, Type.MULTIPLICATION, right );
	}
	static BinaryOperation divide( ArithmeticExpression left, ArithmeticExpression right ){
		return new BinaryOperation( left, Type.DIVISION, right );
	}
	static BinaryOperation power( ArithmeticExpression left, ArithmeticExpression right ){
		return new BinaryOperation( left, Type.POWER, right );
	}

	@Override
	String generateJavaBytecode( ){
		String left = LEFT.generateJavaBytecode( );
		String right = RIGHT.generateJavaBytecode( );
		return String.format( "%s\n%s\n%s", left, right, OPERATION.INSTRUCTION );
	}
	
	@Override
	public String toString( ){
		return String.format( "(%s %c %s)", LEFT, OPERATION.OPERATOR, RIGHT );
	}

}
