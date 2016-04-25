package postfix;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Clasa imparte o expresie in tokeni.
 * 
 * @author Johnny
 *
 */
public class GenerateTokens {

	/**
	 * 
	 * @param infix
	 *            un string, care reprezinta forma infixata a expresiei(din
	 *            input).
	 * @return un ArrayList de tokeni ai expresiei.
	 */
	public static ArrayList<String> SplitInputIntoTokens(String infix) {

		ArrayList<String> tokens = new ArrayList<>();

		/*
		 * Convertesc string-ul intr-un vector de caractere ca sa il pot
		 * parcurge.
		 */
		char[] arrayInfix = infix.toCharArray();

		// Un StringBuilder auxiliar in care voi pune tot ce nu e operator.
		StringBuilder auxString = new StringBuilder();

		// Un string in care pun operatorii unari gasiti.
		String unaryOperator = "";

		for (int i = 0; i < arrayInfix.length; i++) {
			// Caracterul curent.
			Character currentChar = arrayInfix[i];

			// Daca caracterul curent e operator, '(' sau ')'
			if (VerificareOperatori.isOperator(currentChar) || currentChar.equals('(') || currentChar.equals(')')) {

				/*
				 * Si daca caracterul anterior e operator si caracterul curent e
				 * diferit de '(' sau caracterul anterior e '(' si caracterul
				 * curent e diferit de '(', atunci caracterul curent e operator
				 * unar, si il retin.
				 */
				if ((VerificareOperatori.isOperator(arrayInfix[i - 1]) && !currentChar.equals('('))
						|| (arrayInfix[i - 1] == '(' && !currentChar.equals('('))) {
					unaryOperator = currentChar.toString();
				} else {
					/*
					 * Altfel, pun in lista operandul(e.g. poate fi 2 sau -2) si
					 * operatorul binar, separati.
					 */
					tokens.add(auxString.toString());

					// Golesc StringBuilder-ul.
					auxString.setLength(0);
					tokens.add(String.valueOf(currentChar));
				}
			} else {
				// Altfel, in auxString pun operatorul unar alaturi de
				// caracterul curent.
				auxString.append(unaryOperator).append(currentChar);

				// Golesc unaryOperator.
				unaryOperator = "";
			}
		}

		// Adaug in lista si ultimul caracter/operand.
		tokens.add(auxString.toString());

		// Elimin toate empty string-urile din lista.
		tokens.removeAll(Arrays.asList(""));

		// Returnez lista de tokeni.
		return tokens;
	}

}