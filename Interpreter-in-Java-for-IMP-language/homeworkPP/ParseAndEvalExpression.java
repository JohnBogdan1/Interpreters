package homeworkPP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Stack;

/**
 * Clasa care parseaza si evalueaza o expresie. Puteam parsa o expresie la fel
 * ca un program, dar am observat acest lucru dupa ce am facut parsarea
 * programului.
 * 
 * @author Johnny
 *
 */
public class ParseAndEvalExpression implements IExpression {
	private IExpression result;

	public ParseAndEvalExpression(String expression, Context context) {

		// impart string-ul in tokeni
		String[] tokensExpression = expression.replace("[", "").replace("]", "").split(" ");
		List<String> stringList = new ArrayList<String>(Arrays.asList(tokensExpression));

		// folosesc o stiva de expresii pentru a evalua
		Stack<IExpression> stack = new Stack<>();

		// folosesc un iterator
		ListIterator<String> li = stringList.listIterator(stringList.size());

		// parcurg lista de la final
		while (li.hasPrevious()) {

			// convertesc elementul la string
			Object el = li.previous();
			String elem = toString(el);

			// cand gasesc un operator, fac pop de doua ori
			// pentru a scoate doi operanzi si a efectua operatia pe ei
			// adaug in stiva rezultatul
			if (elem.equals("+")) {
				IExpression left = stack.pop();
				IExpression right = stack.pop();
				IExpression sum = new Sum(left, right);
				stack.push(sum);
			} else if (elem.equals("*")) {
				IExpression left = stack.pop();
				IExpression right = stack.pop();
				IExpression prod = new Product(left, right);
				stack.push(prod);
			} else if (elem.equals("==")) {
				IExpression left = stack.pop();
				IExpression right = stack.pop();
				IExpression eq = new Equality(left, right);
				stack.push(eq);
			} else if (elem.equals("<")) {
				IExpression left = stack.pop();
				IExpression right = stack.pop();
				IExpression less = new LessThan(left, right);
				stack.push(less);
			} else if (elem.matches("[a-zA-Z0-9]+")) {
				// cand intalnesc un operand, il adaug in stiva
				// Variable implementeaza IExpression, deci voi avea o expresie
				stack.push(new Variable(elem, context));
			}
		}

		// la final voi avea doar o expresie in stiva, care trebuie evaluata
		// expresia contine left si right
		// left si right contin alte expresii
		// evaluarea va arata sub forma unui arbore binar
		result = stack.pop();

	}

	private String toString(Object obj) {
		return obj.toString();
	}

	/**
	 * Evaluez expresia rezultata.
	 */
	@Override
	public Integer eval() {
		return result.eval();
	}

}
