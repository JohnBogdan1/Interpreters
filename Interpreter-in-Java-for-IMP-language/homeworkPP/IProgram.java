package homeworkPP;

/**
 * IProgram este un decorator pentru program. Este folosita la evaluarea &
 * verificarea corectitudinii unui program.
 * 
 * @author Johnny
 *
 */
public interface IProgram {

	/**
	 * Un program consta in evaluarea mai multor expresii si modificarea
	 * contextului.
	 * 
	 * @return rezultatul evaluarii programului.
	 */
	public Integer eval();

	/**
	 * Folosesc Visitor pentru a verifica corectitudinea programului. accept
	 * accepta un visitor din interfata IProgramVisitor. Este folosita atunci
	 * cand vreau sa vizitez diferite componente ale programului, pentru a
	 * verifica corectitudinea lor.
	 * 
	 * @param visitor
	 */
	public void accept(IProgramVisitor visitor);
}
