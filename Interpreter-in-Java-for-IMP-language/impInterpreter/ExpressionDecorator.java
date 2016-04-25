package impInterpreter;

/**
 * Clasa abstracta care implementeaza interfata pentru expresii. O expresie
 * consta in doi operanzi, left si right, asupra carora li se aplica o operatie.
 * 
 * @author Johnny
 *
 */
public abstract class ExpressionDecorator implements IExpression {
	IExpression left;
	IExpression right;

	public ExpressionDecorator(IExpression left, IExpression right) {
		this.left = left;
		this.right = right;
	}

}
