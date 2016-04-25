package impInterpreter;

/**
 * Clasa care extinde un decorator pentru expresie. Evaluarea unei expresii la
 * acest nivel consta in preluarea valorii unei variabile, sau in returnarea
 * numarului respectiv. Returneaza 1 daca doua expresii evaluate sunt egale,
 * altfel 0.
 * 
 * @author Johnny
 *
 */
public class Equality extends ExpressionDecorator {

	public Equality(IExpression left, IExpression right) {
		super(left, right);
	}

	@Override
	public Integer eval() {
		// evaluez expresia din stanga, apoi pe cea din dreapta
		// verific conditia
		if (left.eval() == right.eval())
			return 1;
		return 0;
	}

}
