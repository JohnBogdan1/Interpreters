package homeworkPP;

/**
 * Clasa care primeste o variabila si un context si returneaza valoarea asociata
 * ei prin intermediul metodei eval() pe care o implementeaza.
 * 
 * @author Johnny
 *
 */
public class Variable implements IExpression {
	private String var;
	private Context c;

	public String getS() {
		return var;
	}

	public Variable(String s, Context c) {
		this.var = s;
		this.c = c;
	}

	@Override
	public Integer eval() {

		if (c.valueOf(var) == null) {
			try {
				// inseamna ca nu este o variabila, ci un numar
				return new Number(Integer.parseInt(var)).eval();
			} catch (Exception e) {
				return Integer.MIN_VALUE;
			}
		}

		// altfel, returnez valoarea asociata variabilei
		return new Number(c.valueOf(var)).eval();
	}

}
