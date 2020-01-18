package book;

public class BookFactory {
	public static Book retrieveBook(String subject) {
		if (subject == null) {
			throw new IllegalArgumentException();
		}
		if (subject.equalsIgnoreCase("Science - Technology")) {
			return new ScienceAndTechnology();

		} else if (subject.equalsIgnoreCase("Dictionary")) {
			return new Dictionary();

		} else if (subject.equalsIgnoreCase("Literature")) {
			return new Literature();
		} else
			throw new IllegalArgumentException();
	}
}