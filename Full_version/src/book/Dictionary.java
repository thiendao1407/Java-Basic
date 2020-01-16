package book;

public class Dictionary extends Book {

	@Override
	public String getSubject() {
		return "Dictionary";
	}

	@Override
	public double getRentalFee() {
		return 20;
	}

}
