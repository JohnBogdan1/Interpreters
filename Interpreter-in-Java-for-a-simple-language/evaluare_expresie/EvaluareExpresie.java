package evaluare_expresie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import arbore_de_parsare.EvaluareArbore;
import arbore_de_parsare.ArboreDeParsare;
import postfix.GenerateTokens;
import postfix.InfixToPostfix;

/**
 * Clasa evalueaza expresiile din input. In output voi pune rezultatele
 * evaluarii expresiilor.
 * 
 * @author Johnny
 *
 */
public class EvaluareExpresie {

	private ArrayList<String> output = new ArrayList<>();

	/**
	 * 
	 * @return output, un ArrayList de string-uri.
	 */
	public ArrayList<String> getOutput() {
		return output;
	}

	/**
	 * Evalueaza expresiile.
	 * 
	 * @param lines
	 *            un ArrayList de expresii.
	 */
	public void Evalueaza(ArrayList<String> lines) {

		/*
		 * In Hash, pun variabila deja evaluata la expresia precedenta si
		 * valoarea ei.
		 */
		HashMap<String, Integer> variables = new HashMap<>();

		// Pun tokenii din expresie.
		ArrayList<String> tokeni = new ArrayList<>();

		// Pentru fiecare expresie
		for (String line : lines) {
			/*
			 * Daca primul caracter din expresie e o cifra, atunci elementul
			 * aflat inainte de "=" nu e variabila.
			 */
			if (Character.isDigit(line.charAt(0))) {
				// Adaug in output rezultatul evaluarii expresiei.
				output.add("error");
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

					// Creez un arbore din expresia curenta.
					ArboreDeParsare ex = new ArboreDeParsare();

					ex.creeazaArbore(InfixToPostfix.ToPostfix(line));

					// Evaluez expresia.
					String valoareExpresie = EvaluareArbore.evaluateParseTree(ex.getRoot());

					// Adaug in output rezultatul evaluarii expresiei.
					output.add(variable + "=" + valoareExpresie);

					// Pun in Hash, variabila de la expresia curenta si valoarea ei.
					variables.put(variable, Integer.parseInt(valoareExpresie));
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

					// In finalTokens pun variabilele din subLine.
					ArrayList<String> finalTokens = new ArrayList<>();

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
						 * Daca in Hash nu gasesc token-ul respectiv, check
						 * devine false si ma opresc.
						 */
						if (!variables.containsKey(token)) {
							check = false;
							break;
						} else
							// Altfel, daca gasesc potrivire.
							check = true;
					}

					// Daca toate variabilele sunt declarate.
					if (check == true) {

						// Le inlocuiesc cu valorile lor corespunzatoare din
						// Hash.
						for (String var : variables.keySet()) {
							if (subLine.contains(var))
								subLine = subLine.replaceAll(var, variables.get(var).toString());
						}

						/*
						 * Creez arborele de parsare pentru Expression(expresia
						 * curenta cu variabilele declarate in expresiile
						 * anterioare inlocuite de valorile lor).
						 */
						ArboreDeParsare ex = new ArboreDeParsare();

						String Expression = variable + "=" + subLine;

						// Intai convertesc Expression la postfix si dupa creez
						// arborele.
						ex.creeazaArbore(InfixToPostfix.ToPostfix(Expression));

						// Evaluez expresia.
						String valoareExpresie = EvaluareArbore.evaluateParseTree(ex.getRoot());

						// Adaug in output rezultatul evaluarii expresiei.
						output.add(variable + "=" + valoareExpresie);

						// Pun in Hash, variabila de la expresia curenta si valoarea ei.
						variables.put(variable, Integer.parseInt(valoareExpresie));
					} else
						/*
						 * Daca exista variabila nedeclarata, nu s-a putut
						 * evalua expresia si scriu "error".
						 */
						output.add("error");
				}
			}
		}
	}
}
