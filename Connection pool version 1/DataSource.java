package connectionpool;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {

	static ConnectionPool pool = new ConnectionPool();

	public static Connection getConnection() {
		Connection connection = pool.getConnectionFromPool();
		return connection;
	}

	public static void returnConnection(Connection connection) throws SQLException {
		pool.returnConnectionToPool(connection);
	}

}
