package book;

public class BookFactory {
	public static Book retrieveBook(String subject) {
		if (subject == null) {
			throw new IllegalArgumentException();
		}
		if (subject.equalsIgnoreCase("SCIENCE_AND_TECHNOLOGY")) {
			return new ScienceAndTechnology();

		} else if (subject.equalsIgnoreCase("DICTIONARY")) {
			return new Dictionary();

		} else if (subject.equalsIgnoreCase("LITERATURE")) {
			return new Literature();
		} else
			throw new IllegalArgumentException();
	}
}