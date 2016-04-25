package homeworkPP;

/**
 * Clasa care extinde un decorator pentru program. Primeste o variabila si o
 * expresie si atribuie variabilei rezultatul evaluarii expresiei, cu ajutorul
 * contextului. De asemenea accepta un visitor care viziteaza metoda care
 * verifica expresia asignata unei variabile.
 * 
 * @author Johnny
 *
 */
public class Assignment extends ProgramDecorator {

	private String expression;
	private String var;
	private Context c;

	public Assignment() {

	}

	public String getVar() {
		return var;
	}

	public String getExpression() {
		return expression;
	}

	public Assignment(String var, String expression, Context c) {
		this.var = var;
		this.expression = expression;
		this.c = c;
	}

	/**
	 * Adaug in context variabila cu valoarea specifica expresiei evaluate.
	 * 
	 * @param c
	 */
	public void addToContext() {
		c.add(var, eval());
	}

	@Override
	public Integer eval() {
		// returnez rezultatul evaluarii expresiei
		return new ParseAndEvalExpression(expression, c).eval();
	}

	@Override
	public void accept(IProgramVisitor visitor) {
		visitor.visit(this);
	}

}
