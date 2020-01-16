package exception;

public class BookNotFoundException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BookNotFoundException(String book_id) {
		super("BookID: " + book_id + " not found");
	}
}
