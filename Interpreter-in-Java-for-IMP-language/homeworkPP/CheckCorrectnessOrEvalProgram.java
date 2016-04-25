package homeworkPP;

/**
 * Clasa care parseaza un program si il evalueaza(verifica corectitudinea) in
 * functie de type.
 * 
 * @author Johnny
 *
 */
public class CheckCorrectnessOrEvalProgram implements IProgramVisitor {

	private IProgram program;
	private final Context context = new Context();
	private String type;
	private CheckCorrectness checkCorrectness;

	public CheckCorrectnessOrEvalProgram(String check, CheckCorrectness checkCorrectness) {
		this.type = check;
		this.checkCorrectness = checkCorrectness;
	}

	/**
	 * @param string
	 *            - programul respectiv care trebuie evaluat/verificat.
	 */
	public void ProgramEval(String string) {

		// daca intalnesc ";", fac split si stiu ca primeste doua programe
		// astfel, apelez recursiv pe fiecare
		if (string.startsWith(";", 1)) {
			String[] splittedProgram = Main.splitList(string.substring(1, string.length() - 1));
			ProgramEval(splittedProgram[1]);
			ProgramEval(splittedProgram[2]);
		} else if (string.startsWith("=", 1)) {
			// cand intalnesc "=", inseamna ca este o asignare
			String[] res = Main.splitList(string.substring(1, string.length() - 1));
			Assignment assignment = new Assignment(res[1], res[2], context);
			if (type.equals("eval")) {
				// se asigneaza unei variabile rezultatul evaluarii expresiei
				assignment.addToContext();
			} else if (type.equals("correctness")) {
				// apelez metoda care viziteaza Assignment
				// iar aceasta face accept pe checkCorrectness
				// pentru a vizita metoda din clasa CheckCorrectness
				visit(assignment);
			}
		} else if (string.startsWith("if", 1)) {

			// cand intalnesc "if", inseamna ca este evaluarea unei conditii
			// conditia este o expresie
			// in functie de rezultat, apelez recursiv metoda ProgramEval
			String[] res = Main.splitList(string.substring(1, string.length() - 1));
			EvaluateCondition condition = new EvaluateCondition(res[1], context);

			if (type.equals("eval")) {
				if (condition.eval() == 1) {
					ProgramEval(res[2]);
				} else {
					ProgramEval(res[3]);
				}
			} else if (type.equals("correctness")) {

				// apelez metoda care viziteaza EvaluateCondition
				// iar aceasta face accept pe checkCorrectness
				// pentru a vizita metoda din clasa CheckCorrectness
				visit(condition);

				// apelez recursiv metoda pe ambele programe asociate lui if
				// pentru fiecare program, verific corectitudinea
				initVisit();
				ProgramEval(res[2]);
				endVisit();

				initVisit();
				ProgramEval(res[3]);
				endVisit();
			}
		} else if (string.startsWith("while", 1)) {

			// cand intalnesc "while", inseamna ca este evaluarea unei conditii
			// conditia este o expresie
			// in functie de rezultat, apelez recursiv metoda pe program
			String[] res = Main.splitList(string.substring(1, string.length() - 1));
			EvaluateCondition condition = new EvaluateCondition(res[1], context);
			if (type.equals("eval")) {
				while (condition.eval() == 1) {
					ProgramEval(res[2]);
				}
			} else if (type.equals("correctness")) {

				// apelez metoda care viziteaza EvaluateCondition
				// iar aceasta face accept pe checkCorrectness
				// pentru a vizita metoda din clasa CheckCorrectness
				visit(condition);

				initVisit();

				// apelez recursiv metoda pe programul aferent lui while
				ProgramEval(res[2]);

				endVisit();
			}
		} else if (string.startsWith("return", 1)) {
			// cand intalnesc return, inseamna ca trebuie sa returnez valoarea
			// expresiei respective
			String[] res = Main.splitList(string.substring(1, string.length() - 1));
			ReturnProgramStatement programReturn = new ReturnProgramStatement(res[1], context);
			if (type.equals("eval")) {
				program = programReturn;
			} else if (type.equals("correctness")) {
				// apelez metoda care viziteaza ReturnProgramStatement
				// iar aceasta face accept pe checkCorrectness
				// pentru a vizita metoda din clasa CheckCorrectness
				visit(programReturn);
			}
		}
	}

	/**
	 * 
	 * @return rezultatul evaluarii expresiei asociate lui return.
	 */
	public Integer eval() {
		return program.eval();
	}

	/**
	 * In clasa Assignment accept visitor-ul checkCorrectness, iar acolo vizitez
	 * metoda asociata lui Assignment din CheckCorrectness.
	 */
	@Override
	public void visit(Assignment assignment) {
		assignment.accept(checkCorrectness);
	}

	/**
	 * In clasa ReturnProgramStatement accept visitor-ul checkCorrectness, iar
	 * acolo vizitez metoda asociata lui ReturnProgramStatement din
	 * CheckCorrectness.
	 */
	@Override
	public void visit(ReturnProgramStatement returnProgramStatement) {
		returnProgramStatement.accept(checkCorrectness);
	}

	/**
	 * In clasa EvaluateCondition accept visitor-ul checkCorrectness, iar acolo
	 * vizitez metoda asociata lui EvaluateCondition din CheckCorrectness.
	 */
	@Override
	public void visit(EvaluateCondition evaluateCondition) {
		evaluateCondition.accept(checkCorrectness);

	}

	/**
	 * Intru in bloc.
	 */
	@Override
	public void initVisit() {
		checkCorrectness.initVisit();
	}

	/**
	 * Ies din bloc.
	 */
	@Override
	public void endVisit() {
		checkCorrectness.endVisit();

	}

}
