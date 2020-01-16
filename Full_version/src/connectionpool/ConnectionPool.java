package connectionpool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {
	List<Connection> availableConnections = new ArrayList<Connection>();

	public ConnectionPool() {
		initializeConnectionPool();
	}

	private void initializeConnectionPool() {
		while (!checkIfConnectionPoolIsFull()) {
			availableConnections.add(createNewConnectionForPool());
		}
	}

	private synchronized boolean checkIfConnectionPoolIsFull() {
		final int MAX_POOL_SIZE = Configuration.MAX_CONNECTIONS;

		if (availableConnections.size() < MAX_POOL_SIZE) {
			return false;
		}

		return true;
	}

	// Creating a connection
	private Connection createNewConnectionForPool() {
		try {
			Connection connection = (Connection) DriverManager.getConnection(Configuration.URL, Configuration.USERNAME,
					Configuration.PASSWORD);
			return connection;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	public synchronized Connection getConnectionFromPool() {
		Connection connection = null;
		if (availableConnections.size() > 0) {
			connection = (Connection) availableConnections.get(0);
			availableConnections.remove(0);
		}
		return connection;
	}

	public synchronized void returnConnectionToPool(Connection connection) {
		try {
			if (connection.isClosed()) {
				initializeConnectionPool();
			} else {
				availableConnections.add(connection);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
