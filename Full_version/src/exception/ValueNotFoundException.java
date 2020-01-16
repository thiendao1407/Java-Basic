package exception;

public class ValueNotFoundException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ValueNotFoundException(String val) {
		super(val + "\n");
	}

	public ValueNotFoundException() {
		super("\n");
	}
}