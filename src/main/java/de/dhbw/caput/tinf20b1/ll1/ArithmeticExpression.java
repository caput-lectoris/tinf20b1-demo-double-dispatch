package de.dhbw.caput.tinf20b1.ll1;

abstract class ArithmeticExpression {
	
	protected Datatype type;
	
	ArithmeticExpression(){
		super( );
	}
	
	abstract String generateJavaBytecode( );
	abstract Datatype inferTypes( );
	
}
