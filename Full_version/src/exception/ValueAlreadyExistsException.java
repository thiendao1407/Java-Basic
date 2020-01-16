package exception;

public class ValueAlreadyExistsException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ValueAlreadyExistsException(String val) {
		super(val + "\n");
	}

	public ValueAlreadyExistsException() {
		super("\n");
	}

}
