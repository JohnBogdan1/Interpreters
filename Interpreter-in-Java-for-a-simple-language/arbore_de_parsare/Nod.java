package arbore_de_parsare;

/**
 * Clasa Nod implementeaza arborele de parsare. Fiecare nod va avea un string
 * data(informatia), doua variabile de tip Nod, left si right, o variabila
 * boolean isLeaf care reprezinta tipul nodului(frunza sau nu), si o variabila
 * boolean checked pe care o voi folosi la afisarea arborelui.
 * 
 * @author Johnny
 *
 */
public class Nod {

	String data;
	Nod left = null, right = null;
	boolean isLeaf = false;
	boolean checked = false;

	// Constructor pentru Nod.
	public Nod(String data, Nod left, Nod right, boolean isLeaf, boolean checked) {
		this.data = data;
		this.left = left;
		this.right = right;
		this.isLeaf = isLeaf;
		this.checked = checked;
	}

}
