package book;

public class Dictionary extends Book {

	@Override
	public BookSubject getSubject() {
		return BookSubject.DICTIONARY;
	}

	@Override
	public double getRentalFee() {
		return 20;
	}

}
