package impInterpreter;

/**
 * Clasa care extinde un decorator pentru program. Primeste o expresie pe care o
 * evalueaza cu ajutorul contextului. De asemenea accepta un visitor care
 * viziteaza metoda care verifica expresia asociata lui return.
 * 
 * @author Johnny
 *
 */
public class ReturnProgramStatement extends ProgramDecorator {

	private String expression;
	private Context c;

	public ReturnProgramStatement() {

	}

	public String getExpression() {
		return expression;
	}

	public ReturnProgramStatement(String expression, Context c) {
		this.expression = expression;
		this.c = c;
	}

	@Override
	public Integer eval() {
		return new ParseAndEvalExpression(expression, c).eval();
	}

	@Override
	public void accept(IProgramVisitor visitor) {
		visitor.visit(this);
	}

}
