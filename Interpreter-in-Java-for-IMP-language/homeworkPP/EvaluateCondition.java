package homeworkPP;

/**
 * Clasa care extinde un decorator pentru program. Verifica daca o conditie este
 * adevarata, evaluand expresia. Conditia contine atat operatorul "<", cat si
 * "==". Returneaza 1 daca doua conditia este adevarata, altfel 0. De asemenea
 * accepta un visitor.
 * 
 * @author Johnny
 *
 */
public class EvaluateCondition extends ProgramDecorator {
	private String condition;
	private Context c;

	public EvaluateCondition(String condition, Context c) {
		this.condition = condition;
		this.c = c;

	}

	public String getCondition() {
		return condition;
	}

	@Override
	public Integer eval() {
		if (new ParseAndEvalExpression(condition, c).eval() > 0) {
			return 1;
		}
		return 0;
	}

	@Override
	public void accept(IProgramVisitor visitor) {
		visitor.visit(this);
	}

}
