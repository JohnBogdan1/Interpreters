package impInterpreter;

/**
 * Clasa care extinde un decorator pentru expresie. Evaluarea unei expresii la
 * acest nivel consta in preluarea valorii unei variabile, sau in returnarea
 * numarului respectiv. Returneaza 1 daca expresia evaluata din stanga este mai
 * mica decat expresia evaluata din dreapta, altfel 0.
 * 
 * @author Johnny
 *
 */
public class LessThan extends ExpressionDecorator {

	public LessThan(IExpression left, IExpression right) {
		super(left, right);
	}

	@Override
	public Integer eval() {
		// evaluez expresia din stanga, apoi pe cea din dreapta
		// verific conditia
		if (left.eval() < right.eval())
			return 1;
		return 0;
	}

}
