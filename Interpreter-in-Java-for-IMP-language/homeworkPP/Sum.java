package homeworkPP;

/**
 * Clasa care extinde un decorator pentru expresie. Returneaza suma dintre
 * rezultatele evaluarii a doua expresii: left si right. Evaluarea unei expresii
 * la acest nivel consta in preluarea valorii unei variabile, sau in returnarea
 * numarului respectiv.
 * 
 * @author Johnny
 *
 */
public class Sum extends ExpressionDecorator {

	public Sum(IExpression left, IExpression right) {
		super(left, right);
	}

	@Override
	public Integer eval() {
		return left.eval() + right.eval();
	}

}
