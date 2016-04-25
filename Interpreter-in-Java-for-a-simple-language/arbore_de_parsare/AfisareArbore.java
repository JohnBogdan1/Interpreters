package arbore_de_parsare;

import read_write.WriteOutput;

/**
 * Afiseaza arborele pe nivele, pornind cu nivelul 0.
 * 
 * @author Johnny
 *
 */
public class AfisareArbore {

	/**
	 * Determina inaltimea arborelui.
	 * 
	 * @param root
	 *            radacina arborelui.
	 * @return inaltimea arborelui.
	 */
	private static int height(Nod root) {
		if (root == null)
			return 0;
		else {
			// Cobor pe partea stanga recursiv.
			int leftHeight = height(root.left);

			// Cobor pe partea dreapta recursiv.
			int rightHeight = height(root.right);

			// Returnez pe cea mai mare.
			if (leftHeight > rightHeight)
				return (leftHeight + 1);
			else
				return (rightHeight + 1);
		}
	}

	/**
	 * 
	 * @param root
	 *            radacina arborelui
	 * @param outputArboreDeParsare
	 *            fisier output.
	 */
	public static void printLevelOrder(Nod root, WriteOutput outputArboreDeParsare) {

		// Inaltimea arborelui.
		int height = height(root) - 1;

		// Nivelul curent al arborelui.
		int k = 0;

		// Apelez functia de afisare pentru toate nivelele.
		for (int i = 0; i <= height; i++) {
			printLevel(root, k, i, outputArboreDeParsare);

			// Scriu in fisier un newline pentru fiecare nivel.
			outputArboreDeParsare.write("\n");
		}

	}

	/**
	 * Afiseaza arborele pe nivele, incepand cu nivelul 0.
	 * 
	 * @param node
	 *            un nod. In cazul nostru, radacina arborelui.
	 * @param k
	 *            nivelul curent.
	 * @param level
	 *            nivelul maxim.
	 * @param outputArboreDeParsare
	 *            fisier output.
	 */
	private static void printLevel(Nod node, int k, int level, WriteOutput outputArboreDeParsare) {

		// Daca nivelul curent e mai mic decat cel maxim...
		if (k <= level) {

			// In info pun informatia care trebuie scrisa in fisier.
			String info = "";

			// Daca nodul a fost verificat(afisat) la nivelul precedent...
			if (node.checked)
				// In info pun informatia din nod.
				info = node.data;
			else {
				// Altfel, pun un caracter corespunzator tipului de nod.
				if (node instanceof Expresii)
					info = "E";
				else if (node instanceof Termeni)
					info = "T";
				else if (node instanceof Factori)
					info = "F";
				else if (node instanceof ElementTernar)
					info = "N";
			}

			// Daca nodul este operator, il marchez ca verificat.
			if ((TipOperator.isBasicOperator(node.data.charAt(0)) || TipOperator.isTernaryOperator(node.data.charAt(0)))
					&& node.data.length() == 1) {
				node.checked = true;
			}

			// Daca nodul este frunza...
			if (node.isLeaf)
				// Scriu info in fisier.
				outputArboreDeParsare.write(info);
			else {
				/*
				 * Altfel, nodul este operator, si pun paranteze la copii lui,
				 * mai putin daca e "=".
				 */

				if ((node.data.charAt(0) != '=') && (node.left.isLeaf || node.right.isLeaf))
					outputArboreDeParsare.write("(");

				// Parcurg in stanga si incrementez nivel.
				if (node.left != null)
					printLevel(node.left, k + 1, level, outputArboreDeParsare);

				// Scriu info in fisier.
				outputArboreDeParsare.write(info);

				// Parcurg in dreapta si incrementez nivel.
				if (node.right != null)
					printLevel(node.right, k + 1, level, outputArboreDeParsare);

				if ((node.data.charAt(0) != '=') && (node.left.isLeaf || node.right.isLeaf))
					outputArboreDeParsare.write(")");
			}

		}

	}
}