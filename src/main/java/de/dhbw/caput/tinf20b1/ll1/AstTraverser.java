package de.dhbw.caput.tinf20b1.ll1;

public interface AstTraverser<T> {
	
	public T visit( NumericLiteral literal );
	
	public default T visit( UnaryOperation op ){
		T base = op.BASE.accept( this );
		return visitPost( op, base);
	}
	public T visitPost( UnaryOperation binOp, T left );
	
	public default T visit( BinaryOperation op ){
		T left = op.LEFT.accept( this );
		T right = op.RIGHT.accept( this );
		return visitPost( op, left, right );
	}
	public T visitPost( BinaryOperation binOp, T left, T right );

}
