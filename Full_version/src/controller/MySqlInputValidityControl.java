package controller;

import exception.InvalidValueException;

public interface MySqlInputValidityControl {
	void checkSqlInputValidity(String... val) throws InvalidValueException;
}
