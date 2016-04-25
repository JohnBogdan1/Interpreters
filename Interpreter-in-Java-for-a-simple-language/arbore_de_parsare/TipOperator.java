package arbore_de_parsare;

/**
 * Clasa verifica tipul unui operator.
 * 
 * @author Johnny
 *
 */
public class TipOperator {

	/**
	 * 
	 * @param c
	 *            un caracter.
	 * @return true, daca e operator, altfel false.
	 */
	protected static boolean isBasicOperator(Character c) {
		if (c.equals('+') || c.equals('-') || c.equals('*') || c.equals('='))
			return true;
		else
			return false;
	}

	/**
	 * 
	 * @param c
	 *            un caracter.
	 * @return true, daca e operator ternar, altfel false.
	 */
	protected static boolean isTernaryOperator(Character c) {
		if (c.equals('?') || c.equals('>') || c.equals(':'))
			return true;
		else
			return false;
	}
}
