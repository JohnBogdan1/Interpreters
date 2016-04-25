package arbore_de_parsare;

/**
 * Clasa extinde Nod, reprezentand un tip de nod.
 * 
 * @author Johnny
 *
 */
public class Termeni extends Nod {

	// Constructor pentru Termeni.
	public Termeni(String data, Nod left, Nod right, boolean isLeaf, boolean checked) {
		super(data, left, right, isLeaf, checked);
	}

}
