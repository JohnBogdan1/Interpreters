package read_write;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Scrie output in fisier.
 * 
 * @author Johnny
 *
 */
public class WriteOutput {

	private PrintWriter out = null;

	/**
	 * 
	 * @param filename
	 *            numele fisierului de iesire(un string)
	 * @exception FileNotFoundException
	 *                daca nu este gasit fisierul
	 */
	public WriteOutput(String filename) {
		try {
			out = new PrintWriter(filename);

		} catch (FileNotFoundException ex) {
			Logger.getLogger(WriteOutput.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Preia fiecare string din ArrayList si il scrie in fisier.
	 * 
	 * @param lines
	 *            un ArrayList.
	 */
	public void writeLines(ArrayList<String> lines) {
		for (String line : lines) {
			out.println(line);
		}
	}

	/**
	 * Scrie un string pe aceeasi linie.
	 * 
	 * @param line
	 *            un string.
	 */
	public void write(String line) {
		out.print(line);
	}

	/**
	 * Inchide fisier
	 */
	public void close() {
		out.close();
	}

}
