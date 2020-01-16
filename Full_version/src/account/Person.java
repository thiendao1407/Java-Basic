package account;

import exception.InvalidValueException;

public class Person {

	private String name;
	private String address; // We can make Address type by creating class Address in reality huhu
	private String email;
	private String phone;

	public Person() {
		//
	}

	public Person(String name, String address, String email, String phone) {
		this.name = name;
		this.address = address;
		this.email = email;
		this.phone = phone;
	}

	public String toString() {
		return "Name: " + name + ", Address: " + address + ", Email: " + email + ", Phone: " + phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws InvalidValueException {
		if (name.isBlank())
			throw new InvalidValueException("Name could not be blank");
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		if (address.isBlank())
			return;
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	// use REGEX to validate email
	public void setEmail(String email) {
		if (email.isBlank())
			return;
		this.email = email;
	}

	// use REGEX to validate phone number
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		if (phone.isBlank())
			return;
		this.phone = phone;
	}

}
