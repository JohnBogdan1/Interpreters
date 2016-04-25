package impInterpreter;

import java.util.HashMap;
import java.util.Map;

public class Context {

	private Map<String, Integer> links = new HashMap<>();

	/**
	 * Adaug in Map perechea (variabila, valoare)
	 * @param v
	 * @param i
	 */
	public void add(String v, Integer i) {
		links.put(v, i);
	}

	// Treat undefined variable problem using exceptions
	public Integer valueOf(String v) {
		try {
			return links.get(v);
		} catch (Exception e) {
			return Integer.MIN_VALUE;
		}

	}
}