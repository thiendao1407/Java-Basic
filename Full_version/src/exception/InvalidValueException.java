package exception;

public class InvalidValueException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidValueException(String val) {
		super(val + "\n");
	}

	public InvalidValueException() {
		super("\n");
	}

}
