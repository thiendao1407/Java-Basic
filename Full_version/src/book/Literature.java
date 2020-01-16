package book;

public class Literature extends Book {

	@Override
	public String getSubject() {
		return "Literature";
	}

	@Override
	public double getRentalFee() {
		return 5;
	}

}
