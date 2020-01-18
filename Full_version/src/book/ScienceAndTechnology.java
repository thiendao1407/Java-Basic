package book;

public class ScienceAndTechnology extends Book {

	@Override
	public BookSubject getSubject() {
		return BookSubject.SCIENCE_AND_TECHNOLOGY;
	}

	@Override
	public double getRentalFee() {
		// TODO Auto-generated method stub
		return 10;
	}

}
