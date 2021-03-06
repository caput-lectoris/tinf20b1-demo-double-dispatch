package de.dhbw.caput.tinf20b1.ll1;

abstract class ArithmeticExpression {
	
	protected Datatype type;
	
	ArithmeticExpression(){
		super( );
	}
	
	void set( Datatype type ){
		this.type = type;
	}
	
	Datatype type( ){
		return type;
	}
	
	abstract <T> T accept( AstTraverser<T> traverser );
	
	abstract String generateJavaBytecode( );
	abstract Datatype inferTypes( );
	
}
