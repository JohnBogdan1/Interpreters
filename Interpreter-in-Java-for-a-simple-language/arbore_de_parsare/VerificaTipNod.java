package arbore_de_parsare;

/**
 * 
 * Clasa verifica tipul nodului curent si creeaza un nou nod cu un tip
 * corespunzator, in functie de elementul care trebuie introdus. Nodul este
 * Expresie (E) daca nu reprezinta o frunza in descrierea arborelui de parsare.
 * Nodul este Termen (T) daca este o frunza care are ca parinte "+", "-", sau
 * "=". Nodul este Factor (F) daca este o frunza care are ca parinte "*". Nodul
 * este Element Ternar (N) daca reprezinta un element care are ca parinte o
 * componenta a operatorului ternar ":" , ">?".
 * 
 * @author Johnny
 *
 */
public class VerificaTipNod {

	/**
	 * Verifica tipul nodului curent si creeaza un nou nod cu un tip
	 * corespunzator, in functie de elementul care trebuie introdus. In plus,
	 * seteaza isLeaf pentru fiecare nod.
	 * 
	 * @param c
	 *            primul caracter al elementului curent care trebuie adaugat in
	 *            arbore.
	 * @param data
	 *            caracterul din nodul curent.
	 * @param element
	 *            este elementul curent.
	 * @param newNode
	 *            un nod.
	 * @return newNode.
	 */
	protected static Nod verificaNod(Character c, Character data, String element, Nod newNode) {
		if ((data.equals('=')) || (data.equals('+') || data.equals('-'))) {
			if ((TipOperator.isBasicOperator(c) || TipOperator.isTernaryOperator(c)) && element.length() == 1) {
				newNode = new Expresii(element, null, null, false, false);
			} else
				newNode = new Termeni(element, null, null, true, false);
		} else if (data.equals('*')) {
			if ((TipOperator.isBasicOperator(c) || TipOperator.isTernaryOperator(c)) && element.length() == 1) {
				newNode = new Expresii(element, null, null, false, false);
			} else
				newNode = new Factori(element, null, null, true, false);
		} else if (TipOperator.isTernaryOperator(data)) {
			if ((TipOperator.isBasicOperator(c) || TipOperator.isTernaryOperator(c)) && element.length() == 1) {
				newNode = new Expresii(element, null, null, false, false);
			} else
				newNode = new ElementTernar(element, null, null, true, false);
		}

		return newNode;
	}
}
