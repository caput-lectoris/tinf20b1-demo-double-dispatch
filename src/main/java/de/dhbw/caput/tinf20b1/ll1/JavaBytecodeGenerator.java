package de.dhbw.caput.tinf20b1.ll1;

public class JavaBytecodeGenerator implements AstTraverser<String> {
	
	public static String runOn( ArithmeticExpression expression ){
		JavaBytecodeGenerator generator = new JavaBytecodeGenerator();
		return expression.accept( generator );
	}
	
	@Override
	public String visit( NumericLiteral literal ){
		return String.format( "%sconst_%s", literal.TYPE, literal.VALUE );
	}
	
	@Override
	public String visitPost( UnaryOperation op, String base ){
		return String.format( "%s\n%s", base, op.OPERATION.INSTRUCTION );
	}

	@Override
	public String visitPost( BinaryOperation op, String left, String right ){
		return String.format( "%s\n%s\n%s", left, right, op.OPERATION.INSTRUCTION );
	}

}
