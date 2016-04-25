package arbore_de_parsare;

import java.util.Stack;

/**
 * Evalueaza arborele de parsare corespunzator unei expresii.
 * 
 * @author Johnny
 *
 */
public class EvaluareArbore {

	/**
	 * 
	 * @param tree
	 *            un nod.
	 * @return un string care reprezinta rezultatul in urma evaluarii.
	 */
	public static String evaluateParseTree(Nod tree) {

		String result = "";

		/*
		 * Stiva o voi folosi pentru a pune valoarea din dreapta lui ':' si
		 * valoarea din dreapta lui '?'.
		 */
		Stack<Integer> stack = new Stack<>();

		/*
		 * Evaluarea incepe de la copilul din dreapta al radacinii(adica dupa
		 * "=").
		 */
		result += evaluate(tree.right, stack);

		return result;
	}

	/**
	 * 
	 * @param tree
	 *            un nod.
	 * @param stack
	 *            o stiva in care pun intregi.
	 * @return rezultatul evaluarii arborelui, un intreg.
	 */
	public static int evaluate(Nod tree, Stack<Integer> stack) {

		// Daca nodul e null, ma intorc.
		if (tree == null)
			return 0;

		/*
		 * Daca nodul este atat "+", cat si "-", cat si "*", evaluez partea din
		 * stanga a nodului, ma intorc, evaluez partea din dreapta a nodului, ma
		 * intorc si apoi adun cele doua rezultate ale evaluarilor.
		 */
		if (tree.data.equals("+"))
			return evaluate(tree.left, stack) + evaluate(tree.right, stack);
		else if (tree.data.equals("-"))
			return evaluate(tree.left, stack) - evaluate(tree.right, stack);
		else if (tree.data.equals("*"))
			return evaluate(tree.left, stack) * evaluate(tree.right, stack);

		else if (tree.data.equals(":")) {
			/*
			 * Daca nodul este ":", adaug in stiva ceea ce imi returneaza
			 * evaluarea din dreapta nodului. Folosesc o instanta noua a stivei,
			 * pentru cazul in care in dreapta lui ":" exista o expresie si nu
			 * un intreg.
			 */

			stack.add(evaluate(tree.right, new Stack<Integer>()));

			// Si, cobor pe ramura din stanga.
			return evaluate(tree.left, stack);

		} else if (tree.data.equals("?")) {
			/*
			 * Daca nodul este "?", adaug in stiva ceea ce imi returneaza
			 * evaluarea din dreapta nodului. Folosesc o instanta noua a stivei,
			 * pentru cazul in care in dreapta lui "?" exista o expresie si nu
			 * un intreg.
			 */

			stack.add(evaluate(tree.right, new Stack<Integer>()));

			// Si, cobor pe ramura din stanga.
			return evaluate(tree.left, stack);

		} else if (tree.data.equals(">")) {
			/*
			 * Daca nodul este ">", evaluez ce e in stanga, evaluez ce e in
			 * dreapta, si daca ce e in stanga e mai mare decat ce e in dreapta,
			 * returnez ultimul element din stiva, altfel il returnez pe primul.
			 * Pentru cele doua evaluari folosesc o noua stiva, in cazul in care
			 * in stanga sau in dreapta lui ">", exista o expresie si nu un
			 * intreg.
			 */

			if (evaluate(tree.left, new Stack<Integer>()) > evaluate(tree.right, new Stack<Integer>())) {
				return stack.lastElement();
			} else {
				return stack.firstElement();
			}
			/*
			 * Altfel, daca e frunza(operand), returnez valoarea sa, convertita
			 * la intreg.
			 */
		} else
			return Integer.parseInt(tree.data);
	}
}