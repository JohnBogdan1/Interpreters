package arbore_de_parsare;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Stack;

/**
 * Creeaza arborele de parsare. root este radacina arborelui.
 * 
 * @author Johnny
 *
 */
public class ArboreDeParsare {

	private Nod root;

	/**
	 * 
	 * @return radacina arborelui.
	 */
	public Nod getRoot() {
		return root;
	}

	/**
	 * Construieste arborele.
	 * 
	 * @param tokens
	 *            un ArrayList care contine tokenii expresiei postfixate.
	 */
	public void creeazaArbore(ArrayList<String> tokens) {

		// nodes este o stiva in care pun nodurile care sunt operatori
		final Stack<Nod> nodes = new Stack<Nod>();

		// Instantiez radacina arborelui cu ultimul element din tokens.
		root = new Expresii(tokens.get(tokens.size() - 1), null, null, false, false);

		// Setez nodul curent.
		Nod currentNode = root;

		/*
		 * Creez un iterator, pentru a parcurge tokens de la penultimul element
		 * inapoi.
		 */
		ListIterator<String> li = tokens.listIterator(tokens.size() - 1);

		// Radacina este reprezentata de "=", deci o adaug in stiva.
		nodes.add(currentNode);

		// newNode va fi nodul care trebuie adaugat.
		Nod newNode = null;

		// Parcurg tokens.
		while (li.hasPrevious()) {

			// Elementul curent.
			Object element = li.previous();

			// Convertesc element la String si retin primul caracter.
			Character c = toString(element).charAt(0);

			// Retin primul caracter din nodul curent.
			Character data = currentNode.data.charAt(0);

			// Daca nodul curent nu este operator.
			if ((!TipOperator.isBasicOperator(currentNode.data.charAt(0))
					&& !TipOperator.isTernaryOperator(currentNode.data.charAt(0))) || currentNode.data.length() > 1) {

				// Ma intorc la nodul precedent in care am fost.
				currentNode = nodes.pop();

				// Retin primul caracter din nodul curent.
				data = currentNode.data.charAt(0);

				// Creez un nou nod al carui tip depinde de parintele sau.
				newNode = VerificaTipNod.verificaNod(c, data, toString(element), newNode);

				/*
				 * Adaug newNode in stanga/dreapta si pozitionez currentNode pe
				 * pozitia unde a fost adaugat newnode.
				 */
				if (currentNode.right == null) {
					currentNode.right = newNode;
					currentNode = currentNode.right;
				} else if (currentNode.left == null) {
					currentNode.left = newNode;
					currentNode = currentNode.left;
				}
			} else {
				// Altfel, nodul curent este operator.

				// Creez un nou nod al carui tip depinde de parintele sau.
				newNode = VerificaTipNod.verificaNod(c, data, toString(element), newNode);

				/*
				 * Adaug newNode in stanga/dreapta si pozitionez currentNode pe
				 * pozitia unde a fost adaugat newnode.
				 */
				if (currentNode.right == null) {
					currentNode.right = newNode;
					currentNode = currentNode.right;
				} else if (currentNode.left == null) {
					currentNode.left = newNode;
					currentNode = currentNode.left;
				}
			}
			// Daca elementul curent din tokens este operator, il adaug in
			// stiva.
			if ((TipOperator.isBasicOperator(c) || TipOperator.isTernaryOperator(c)) && toString(element).length() == 1)
				nodes.add(newNode);
		}
	}

	/**
	 * 
	 * @param obj
	 *            un obiect.
	 * @return un string.
	 */
	private String toString(Object obj) {
		return obj.toString();
	}
}