package de.dhbw.caput.tinf20b1.ll1;

enum Datatype {

	I32( "i", 0 ), F32( "f", 1 ), F64( "d", 2 );
	
	final String PREFIX;
	private final int PRECEDENCE;
	
	private Datatype( String prefix, int precedence ){
		PREFIX = prefix;
		PRECEDENCE = precedence;
	}
	
	static Datatype evaluateTypeSuffix( String suffix ){
		switch( suffix ){
			case "f": return Datatype.F32;
			default: throw new RuntimeException( "invalid type suffix" );
		}
	}
	
	static Datatype getDominantType( Datatype a, Datatype b ){
		return a.PRECEDENCE > b.PRECEDENCE ? a : b;
	}
	
	@Override
	public String toString(){
		return PREFIX;
	}
	
}
