package postfix;

/**
 * Clasa verifica existenta si returneaza precedenta si asociativitatea unui
 * operator.
 * 
 * @author Johnny
 *
 */
public class VerificareOperatori {

	/**
	 * 
	 * @param c
	 *            un caracter.
	 * @return true daca este caracter, altfel false.
	 */
	protected static boolean isOperator(Character c) {
		if (c.equals('+') || c.equals('-') || c.equals('*') || c.equals('=') || c.equals('?') || c.equals('>')
				|| c.equals(':'))
			return true;
		else
			return false;
	}

	/**
	 * 
	 * @param c
	 *            un caracter.
	 * @return un string: "left" daca operatorul este asociativ la stanga,
	 *         "right" altfel.
	 */
	protected static String getAssociativity(Character c) {
		switch (c) {

		case '+':
		case '-':
		case '*':
		case '>':
		case ':':
			return "left";

		case '?':
		case '=':
			return "right";
		default:
			return null;
		}

	}

	/**
	 * 
	 * @param c
	 *            un caracter.
	 * @return precedenta unui operator.
	 */
	protected static int getPrecedence(Character c) {

		int precedence = -1;

		if (c.equals('*'))
			precedence = 4;
		else {
			if (c.equals('+') || c.equals('-'))
				precedence = 3;
			else if (c.equals('>')) {
				precedence = 2;
			} else if (c.equals('?') || c.equals(':'))
				precedence = 1;
			else if (c.equals('=')) {
				precedence = 0;
			}
		}
		return precedence;
	}

}