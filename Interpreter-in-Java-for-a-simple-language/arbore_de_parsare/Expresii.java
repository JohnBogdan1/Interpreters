package arbore_de_parsare;

/**
 * Clasa extinde Nod, reprezentand un tip de nod.
 * 
 * @author Johnny
 *
 */
public class Expresii extends Nod {

	// Constructor pentru Expresii.
	public Expresii(String data, Nod left, Nod right, boolean isLeaf, boolean checked) {
		super(data, left, right, isLeaf, checked);
	}

}
