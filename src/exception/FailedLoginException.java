package exception;

public class FailedLoginException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FailedLoginException(String user_name) {
		super("User: " + user_name + " not found.");
	}
	
	public FailedLoginException(String user_name, String password) {
		super("Password is incorrect.");
	}

}
