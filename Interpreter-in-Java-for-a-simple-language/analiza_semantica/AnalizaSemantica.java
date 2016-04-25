package analiza_semantica;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import postfix.GenerateTokens;

/**
 * Clasa analizeaza expresiile din input. In output voi pune rezultatele
 * analizarii expresiilor.
 * 
 * @author Johnny
 *
 */
public class AnalizaSemantica {

	private ArrayList<String> output = new ArrayList<>();

	/**
	 * 
	 * @return output, un ArrayList de string-uri.
	 */
	public ArrayList<String> getOutput() {
		return output;
	}

	/**
	 * Analizeaza expresiile.
	 * 
	 * @param lines
	 *            un ArrayList de expresii.
	 */
	public void Analizeaza(ArrayList<String> lines) {

		/*
		 * In stiva variables, voi pune variabilele analizate in expresiile
		 * anterioare.
		 */
		Stack<String> variables = new Stack<>();

		// Pun tokenii din expresie.
		ArrayList<String> tokeni = new ArrayList<>();

		// Reprezinta linia curenta.
		int i = 1;

		// Pentru fiecare expresie
		for (String line : lines) {
			/*
			 * Daca primul caracter din expresie e o cifra, atunci elementul
			 * aflat inainte de "=" nu e variabila.
			 */
			if (Character.isDigit(line.charAt(0))) {
				// Adaug in output rezultatul analizarii expresiei.
				output.add("membrul stang nu este o variabila la linia " + i + " coloana 1");
			} else {

				// Indexul unde se afla "=" in expresie.
				int index = line.indexOf("=");

				// Retin variabila dinainte de "=".
				String variable = line.substring(0, index);

				// Pun in subLine, ce se afla dupa "=".
				String subLine = line.substring(index + 1);

				/*
				 * Daca in subLine sunt numai cifre (si operatori, dar si "(" si
				 * ")" ) si nu sunt variabile, pot analiza expresia.
				 */
				if (subLine.matches("[0-9+-:?>()*]+")) {
					// Adaug in output rezultatul analizarii expresiei.
					output.add("Ok!");

					// Adaug in stiva variabila.
					variables.push(variable);
				} else {
					/*
					 * Altfel, inseamna ca in expresie(subLine) am si variabile
					 * care trebuie inlocuite.
					 */

					/*
					 * O folosesc pentru a verifica daca exista variabila
					 * nedeclarata in expresie.
					 */
					boolean check = false;

					// In undeclaredVariable retin prima variabila nedeclarata.
					String undeclaredVariable = null;

					// In finalTokens pun variabilele din subLine.
					ArrayList<String> finalTokens = new ArrayList<>();

					// Indexul unde se afla variabila nedeclarata.
					int varIndex = 0;

					// Generez tokenii expresiei.
					tokeni = GenerateTokens.SplitInputIntoTokens(line);

					// Ii retin doar pe cei care se afla dupa "=" in expresie.
					List<String> tokens = tokeni.subList(2, tokeni.size());

					/*
					 * Retin doar variabilele in finalTokens(regex-ul face match
					 * pe orice e variabila sau
					 * variabila<cifra|numar_intreg>)(e.g. a1, b2).
					 */
					for (String s : tokens) {
						if (s.matches("[a-zA-Z]+[0-9]*")) {
							finalTokens.add(s);
						}
					}

					// Pentru fiecare token din finalTokens...
					for (String token : finalTokens) {
						/*
						 * Daca in stiva nu gasesc token-ul respectiv, retin
						 * variabila nedeclarata si indexul unde se afla
						 * aceasta, iar check devine false si ma opresc.
						 */
						if (!variables.contains(token)) {
							varIndex = subLine.indexOf(token) + index + 2;
							undeclaredVariable = token;
							check = false;
							break;
						} else {
							// Altfel, daca gasesc potrivire.
							check = true;
						}
					}
					// Daca toate variabilele sunt declarate.
					if (check == true) {
						// Pun in stiva variabila de la expresia curenta.
						variables.push(variable);

						// Adaug in output rezultatul analizarii expresiei.
						output.add("Ok!");
					} else {
						// Altfel, am gasit variabila nedeclarata.
						
						// Adaug in output rezultatul analizarii expresiei.
						output.add(undeclaredVariable + " nedeclarata la linia " + i + " coloana " + varIndex);
					}
				}
			}
			// Incrementez linia curenta.
			i++;
		}
	}
}