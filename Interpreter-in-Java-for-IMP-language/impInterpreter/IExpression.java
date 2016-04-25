package impInterpreter;

/**
 * IExpression este un decorator pentru expresie.
 * 
 * @author Johnny
 *
 */
public interface IExpression {
	/**
	 * @return rezultatul evaluarii expresiei.
	 */
	public Integer eval();
}
