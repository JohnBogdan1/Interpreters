package impInterpreter;

/**
 * Reprezinta un atom(numar) al expresiei.
 * 
 * @author Johnny
 *
 */
public class Number implements IExpression {

	private int number;

	public Number(int number) {
		this.number = number;
	}

	@Override
	public Integer eval() {
		return number;
	}

}
