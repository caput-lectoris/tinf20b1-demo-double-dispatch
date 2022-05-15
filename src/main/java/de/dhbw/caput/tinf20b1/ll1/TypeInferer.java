package de.dhbw.caput.tinf20b1.ll1;

public class TypeInferer implements AstTraverser<Datatype> {
	
	public static void runOn( ArithmeticExpression expression ){
		TypeInferer inferer = new TypeInferer();
		expression.accept( inferer );
	}
	
	@Override
	public Datatype visit( NumericLiteral op ){
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Datatype visitPost( UnaryOperation binOp, Datatype left ){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Datatype visitPost( BinaryOperation op, Datatype left, Datatype right ){
		// TODO Auto-generated method stub
		return null;
	}

}
