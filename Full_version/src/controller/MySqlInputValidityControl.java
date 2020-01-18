package controller;

public interface MySqlInputValidityControl {
	void checkSqlInputValidity(String... val) throws IllegalArgumentException;
}
