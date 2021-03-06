package de.dhbw.caput.tinf20b1.ll1;

class UnaryOperation extends ArithmeticExpression {
	
	enum Type {
		NEGATION('-', "ineg");

		final String INSTRUCTION;
		private final char OPERATOR;

		private Type( char operator, String instruction ){
			OPERATOR = operator;
			INSTRUCTION = instruction;
		}
	};
	
	final ArithmeticExpression BASE;
	final Type OPERATION;
	
	private UnaryOperation( Type operation, ArithmeticExpression base ){
		super( );
		BASE = base;
		OPERATION = operation;
	}
	
	static UnaryOperation negate( ArithmeticExpression expression ){
		return new UnaryOperation( Type.NEGATION, expression );
	}
	
	String generateJavaBytecode( ){
		String base = BASE.generateJavaBytecode();
		return String.format( "%s\n%s", base, OPERATION.INSTRUCTION );
	}
	
	@Override
	Datatype inferTypes( ){
		type = BASE.inferTypes();
		return type;
	}
	
	@Override
	public String toString( ){
		return String.format( "%c(%s)", OPERATION.OPERATOR, BASE );
	}
	
	@Override
	<T> T accept( AstTraverser<T> traverser ){
		return traverser.visit( this );
	}

}
