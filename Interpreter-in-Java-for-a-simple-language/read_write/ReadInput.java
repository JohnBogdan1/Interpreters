package read_write;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Citeste datele din fisier.
 * 
 * @author Johnny
 */
public class ReadInput {

	private final ArrayList<String> lines = new ArrayList<>();

	public ArrayList<String> getLines() {
		return lines;
	}

	/**
	 * 
	 * @param fileName
	 *            numele fisierului de intrare(un string).
	 * @exception FileNotFoundException
	 *                daca nu este gasit fisierul.
	 */
	public ReadInput(String fileName) {
		try {
			Scanner in = new Scanner(new File(fileName));

			// Citesc fiecare linie si o adaug in ArrayList-ul lines.
			while (in.hasNextLine()) {
				lines.add(in.nextLine());
			}

			// Inchid fisier input.
			in.close();

		} catch (FileNotFoundException ex) {
			Logger.getLogger(ReadInput.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}