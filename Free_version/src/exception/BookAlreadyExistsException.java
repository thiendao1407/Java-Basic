package exception;

public class BookAlreadyExistsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BookAlreadyExistsException(String book_id) {
		super("BookID: " + book_id + " already exists");
	}

}
