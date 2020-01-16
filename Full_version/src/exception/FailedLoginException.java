package exception;

public class FailedLoginException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FailedLoginException(String val) {
		super(val + "\n");
	}

	public FailedLoginException() {
		super("\n");
	}

}
