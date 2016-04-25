package impInterpreter;

import java.util.ArrayList;
import java.util.List;

/**
 * Creez un visitor concret care implementeaza interfata IProgramVisitor. Aici,
 * determin daca un program este corect.
 * 
 * @author Johnny
 *
 */
public class CheckCorrectness implements IProgramVisitor {

	// Pun variabilele initializate in afara unui bloc.
	private List<String> variables = new ArrayList<>();

	// Pun variabile declarate in interiorul unui bloc if/while.
	// Folosesc o lista de liste.
	// Fiecare lista contine variabile declarate la nivelul aferent.
	// Dupa ce ies dintr-un bloc, elimin lista.
	// Deoarece variabilele declarate acolo nu vor mai fi vizibile in alta parte
	private ArrayList<ArrayList<String>> list = new ArrayList<>();

	// verific daca sunt intr-un bloc
	private boolean isInsideBlock = false;

	// nivelul de vizibilitate al variabilelor
	// variabilele cu nivel mai mic sunt vizibile in blocuri aflate pe un nivel mai mare
	private int level = 0;

	// Daca expressionsCorrectness ramane true, inseamna ca toate variabilele au
	// fost initializiate. Programul este corect din acest punct.
	private boolean expressionsCorrectness = true;
	
	// Verific daca return nu este ultimul program.
	private boolean programAfterReturn = false;

	// Daca am ajuns la return, reachedReturn devine true.
	private boolean reachedReturn = false;

	// Rezultatul final al evaluarii corectitudinii.
	private boolean programCorrectness = false;

	/**
	 * Vizitez un assignment.
	 */
	@Override
	public void visit(Assignment assignment) {

		// Preiau expresia si variabila
		String expression = assignment.getExpression();
		String var = assignment.getVar();
		
		// daca am ajuns la return si am intrat aici, inseamna ca return nu este la final
		if (reachedReturn)
			programAfterReturn = true;

		// Daca programul este inca corect, are sens sa evaluez in continuare
		if (expressionsCorrectness == true) {

			// Verific daca expresia are variabile out of scope
			checkVariables(expression);

			// Daca nu are, adaug variabila in lista
			// in functie daca sunt sau nu intr-un bloc
			// daca ma aflu intr-un bloc, adaug in lista aferenta nivelului
			// nu vreau sa adaug duplicate
			if (!isInsideBlock) {
				if (expressionsCorrectness == true && !variables.contains(var)) {
					variables.add(var);
				}
			} else {
				if (expressionsCorrectness == true && !list.get(level - 1).contains(var)) {
					list.get(level - 1).add(var);
				}
			}
		}
	}

	/**
	 * Verific fiecare variabila din expresie.
	 * 
	 * @param expression
	 */
	public void checkVariables(String expression) {

		// Daca este o lista(expresie), iau fiecare variabila
		if (!expression.matches("[a-zA-Z0-9]+")) {

			String[] tokens = expression.substring(1, expression.length() - 1).replace("[", "").replace("]", "")
					.split(" ");

			for (String s : tokens) {

				// Daca este variabila si nu se afla in lista
				// atunci programul este incorect
				if (!isInsideBlock) {
					// daca nu ma aflu intr-un bloc
					// ma uit la lista de variabile declarate in afara blocurilor
					if (s.matches("[a-zA-Z]+") && !variables.contains(s)) {
						expressionsCorrectness = false;
					}
				} else {
					// altfel, ma uit la lista de variabile declarate in afara blocurilor
					// si la listele precedente, deoarece au un nivel mai mic
					if (s.matches("[a-zA-Z]+") && !checkLists(s) && !variables.contains(s)) {
						expressionsCorrectness = false;
					}
				}
			}
		} else {
			// altfel, daca este doar o variabila, o verific
			if (!isInsideBlock) {
				// daca nu ma aflu intr-un bloc
				// ma uit la lista de variabile declarate in afara blocurilor
				if (expression.matches("[a-zA-Z]+") && !variables.contains(expression)) {
					expressionsCorrectness = false;
				}
			} else {
				// altfel, ma uit la lista de variabile declarate in afara blocurilor
				// si la listele precedente, deoarece au un nivel mai mic
				if (expression.matches("[a-zA-Z]+") && !checkLists(expression) && !variables.contains(expression)) {
					expressionsCorrectness = false;
				}
			}
		}
	}

	/**
	 * Verific listele de variabile.
	 * @param expression
	 * @return - false, daca listele nu contin variabila, altfel true.
	 */
	private boolean checkLists(String var) {

		for (ArrayList<String> array : list) {
			if (array.contains(var)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Vizitez un return si verific expresia asociata lui.
	 */
	@Override
	public void visit(ReturnProgramStatement returnProgramStatement) {
		String expression = returnProgramStatement.getExpression();
		
		// daca am ajuns la return si am intrat aici, inseamna ca return nu este la final
		// si nu este unul singur
		if (reachedReturn)
			programAfterReturn = true;

		// Daca programul este inca corect, are sens sa evaluez in continuare
		if (expressionsCorrectness == true) {
			// Am ajuns la return
			reachedReturn = true;

			// Verific expresia
			checkVariables(expression);
		}
	}

	/**
	 * Vizitez o conditie.
	 */
	@Override
	public void visit(EvaluateCondition evaluateCondition) {
		String expression = evaluateCondition.getCondition();
		
		// daca am ajuns la return si am intrat aici, inseamna ca return nu este la final
		if (reachedReturn)
			programAfterReturn = true;

		// Daca programul este inca corect, are sens sa evaluez in continuare
		if (expressionsCorrectness == true) {

			// Verific expresia
			checkVariables(expression);
		}
	}

	/**
	 * 
	 * @return rezultatul evaluarii corectitudinii programului.
	 */
	public boolean getProgramCorrectness() {
		programCorrectness = expressionsCorrectness && reachedReturn && !programAfterReturn;
		
		return programCorrectness;
	}

	/**
	 * Cand intru intr-un bloc, adaug o lista noua care va 
	 * contine variabile declarate in acest bloc si incrementez nivelul. 
	 * De asemenea, fac variabila isInsideBlock true, pentru a stii ca ma aflu 
	 * intr-un bloc.
	 */
	@Override
	public void initVisit() {
		ArrayList<String> insideBlockVariables = new ArrayList<>();
		list.add(insideBlockVariables);
		level++;
		isInsideBlock = true;
	}

	/**
	 * Cand ies dintr-un bloc, sterg lista, decrementez nivelul 
	 * si daca am iesit din toate blocurile, fac variabila 
	 * isInsideBlock false.
	 */
	@Override
	public void endVisit() {

		list.remove(level - 1);

		level--;
		if (level == 0) {
			isInsideBlock = false;
		}

	}
}
