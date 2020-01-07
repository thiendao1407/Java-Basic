package repository;

import static repository.MySQLDatabaseConfiguration.PASSWORD;
import static repository.MySQLDatabaseConfiguration.URL;
import static repository.MySQLDatabaseConfiguration.USERNAME;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import entity.User;
import entity.User.Permission;
import exception.FailedLoginException;

public class UserRepository {

	public User getUserByName(String user_name) throws SQLException, FailedLoginException {
		User found = null;

		final String sql = "SELECT user_name, password, permission FROM user WHERE user_name = '" + user_name + "'";
		try (Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				Statement stmt = con.createStatement()) {

			try (ResultSet rs = stmt.executeQuery(sql)) {
				if (rs.first()) {
					String password = rs.getString("password");
					Permission permission = Permission.getPermission(rs.getString("permission"));
					found = new User(user_name, password, permission);
				} else {
					throw new FailedLoginException(user_name);
				}
			}
		}
		return found;
	}

}
