package impInterpreter;

/**
 * Definesc o interfata pentru a representa visitor.
 * 
 * @author Johnny
 *
 */
public interface IProgramVisitor {

	// vizitarea asignarii unei variabile
	public void visit(Assignment assignment);

	// vizitarea returnarii in program
	public void visit(ReturnProgramStatement returnProgramStatement);

	// vizitarea unei conditii dintr-un if/while
	public void visit(EvaluateCondition evaluateCondition);

	// intrarea intr-un bloc if/while
	public void initVisit();

	// iesirea dintr-un bloc if/while
	public void endVisit();

}