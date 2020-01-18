package connectionpool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;

public class ConnectionPool {
	private int maxPoolSize;
	private int initialPoolSize;
	private String url;
	private String username;
	private String password;
	long deadTime;

	Hashtable<Connection, Long> lock, unlock;

	ConnectionPool() {
		initializeConnectionPool();
	}

	private void initializeConnectionPool() {
		deadTime = 50000; // 50 seconds
		lock = new Hashtable<Connection, Long>();
		unlock = new Hashtable<Connection, Long>();
		maxPoolSize = Configuration.MAX_POOL_SIZE;
		initialPoolSize = Configuration.INITIAL_POOL_SIZE;
		url = Configuration.URL;
		username = Configuration.USERNAME;
		password = Configuration.PASSWORD;
		for (int i = 0; i < initialPoolSize; i++) {
			unlock.put(createNewConnectionForPool(), System.currentTimeMillis());
		}

	}

	Connection createNewConnectionForPool() {
		try {
			return DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	void dead(Connection connection) {
		try {
			((Connection) connection).close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	boolean validate(Connection connection) {
		try {
			return (!connection.isClosed());
		} catch (SQLException e) {
			e.printStackTrace();
			return (false);
		}
	}

	synchronized Connection getConnectionFromPool() {
		while (lock.size() == maxPoolSize) {
			// Wait for an existing connection to be freed up.
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		long now = System.currentTimeMillis();
		Connection connection;
		if (unlock.size() > 0) {
			Enumeration<Connection> e = unlock.keys();
			while (e.hasMoreElements()) {
				connection = e.nextElement();
				if ((now - unlock.get(connection)) > deadTime) {
					// object has dead
					unlock.remove(connection);
					dead(connection);
					connection = null;
				} else {
					if (validate(connection)) {
						unlock.remove(connection);
						lock.put(connection, now);
						return connection;
					} else {
						// object failed validation
						unlock.remove(connection);
						dead(connection);
						connection = null;
					}
				}
			}
		}
		// no objects available, create a new one
		connection = createNewConnectionForPool();
		lock.put(connection, now);
		return (connection);
	}

	synchronized void returnConnectionToPool(Connection connection) {
		lock.remove(connection);
		unlock.put(connection, System.currentTimeMillis());
		notifyAll();
	}
}
