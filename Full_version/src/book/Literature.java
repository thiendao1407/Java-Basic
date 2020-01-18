package book;

public class Literature extends Book {

	@Override
	public BookSubject getSubject() {
		return BookSubject.LITERATURE;
	}

	@Override
	public double getRentalFee() {
		return 5;
	}

}
