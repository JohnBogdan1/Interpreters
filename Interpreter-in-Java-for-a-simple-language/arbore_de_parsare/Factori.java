package arbore_de_parsare;

/**
 * Clasa extinde Nod, reprezentand un tip de nod.
 * 
 * @author Johnny
 *
 */
public class Factori extends Nod {

	// Constructor pentru Factori.
	public Factori(String data, Nod left, Nod right, boolean isLeaf, boolean checked) {
		super(data, left, right, isLeaf, checked);
	}

}
