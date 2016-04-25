package postfix;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Clasa converteste o expresie din forma infixata in forma postfixata.
 * 
 * @author Johnny
 *
 */
public class InfixToPostfix {

	/**
	 * 
	 * @param infix
	 *            un string, care reprezinta forma infixata a expresiei(din
	 *            input).
	 * @return un ArrayList care contine forma postfixata.
	 */
	public static ArrayList<String> ToPostfix(String infix) {

		// Preiau tokenii lui infix.
		ArrayList<String> tokens = GenerateTokens.SplitInputIntoTokens(infix);

		ArrayList<String> postfix = new ArrayList<>();

		// O stiva in care voi pune operatori, '(' si ')'.
		Stack<Character> stack = new Stack<>();

		// Parcurg lista de tokeni.
		for (String token : tokens) {

			// Retin primul caracter din token.
			Character c = token.charAt(0);

			/*
			 * Daca e operator si nu e cazul aici ca token sa fie operand
			 * unar(care sa aiba un operator unar in fata)
			 */
			if (VerificareOperatori.isOperator(c) && token.length() == 1) {

				// Daca stiva e goala, pun primul operator.
				if (stack.empty()) {
					stack.push(c);
				} else {
					// Altfel, cat timp stiva nu e goala.
					while (!stack.empty()) {
						/*
						 * Daca operatorul este asociativ la stanga si
						 * precedenta lui este mai mica sau egala ca precedenta
						 * operatorului din varful stivei, sau este asociativ la
						 * dreapta si precedenta lui este mai mica ca precedenta
						 * operatorului din varful stivei, atunci scot
						 * operatorul din stiva si il pun in lista(adaugarea se
						 * face la sfarsit).
						 */
						if ((VerificareOperatori.getAssociativity(c) == "left" && VerificareOperatori
								.getPrecedence(c) <= VerificareOperatori.getPrecedence(stack.peek()))
								|| (VerificareOperatori.getAssociativity(c) == "right" && VerificareOperatori
										.getPrecedence(c) < VerificareOperatori.getPrecedence(stack.peek()))) {
							postfix.add(stack.pop().toString());
						} else
							/*
							 * Altfel, ma opresc din cautat daca am gasit
							 * contrariul.
							 */
							break;
					}
					// Adaug operatorul curent in stiva.
					stack.push(c);
				}
				// Daca e '(', o adaug in stiva.
			} else if (c.equals('(')) {
				stack.push(c);
			} else if (c.equals(')')) {
				/*
				 * Daca e ')', pana cand gasesc '(', scot operatorii din stiva
				 * si ii pun in lista
				 */
				while (!stack.empty() && !stack.peek().equals('(')) {
					postfix.add(stack.pop().toString());
				}

				// Scot '(' din stiva.
				stack.pop();
			} else {
				/*
				 * Daca e orice altceva(operand sau operand precedat de operator
				 * unar), il adaug in lista.
				 */
				postfix.add(token);
			}
		}

		/*
		 * Dupa ce am terminat parcurgerea listei de tokeni, daca stiva nu e
		 * goala, scot toti operatorii si ii pun in lista, adaugand la sfarsit.
		 */
		while (!stack.empty()) {
			postfix.add(stack.pop().toString());
		}

		// Returnez lista.
		return postfix;
	}
}
