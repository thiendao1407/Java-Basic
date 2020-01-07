package exception;

public class InvalidFormatException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidFormatException() {
		super("\n-BookID, Bookname fields can not be blank"
				+ "\n-The number of books must be greater than or equal to 0");
	}

}