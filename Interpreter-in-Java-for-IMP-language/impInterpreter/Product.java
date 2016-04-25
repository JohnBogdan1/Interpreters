package impInterpreter;

/**
 * Clasa care extinde un decorator pentru expresie. Returneaza produsul dintre
 * rezultatele evaluarii a doua expresii: left si right. Evaluarea unei expresii
 * la acest nivel consta in preluarea valorii unei variabile, sau in returnarea
 * numarului respectiv.
 * 
 * @author Johnny
 *
 */
public class Product extends ExpressionDecorator {

	public Product(IExpression left, IExpression right) {
		super(left, right);
	}

	@Override
	public Integer eval() {
		return left.eval() * right.eval();
	}

}
