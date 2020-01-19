package connectionpool;

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

	Hashtable<MyConnection, Long> lock, unlock;

	ConnectionPool() {
		initializeConnectionPool();
	}

	private void initializeConnectionPool() {
		deadTime = 50000; // 50 seconds
		lock = new Hashtable<MyConnection, Long>();
		unlock = new Hashtable<MyConnection, Long>();
		maxPoolSize = Configuration.MAX_POOL_SIZE;
		initialPoolSize = Configuration.INITIAL_POOL_SIZE;
		url = Configuration.URL;
		username = Configuration.USERNAME;
		password = Configuration.PASSWORD;
		for (int i = 0; i < initialPoolSize; i++) {
			unlock.put(createNewConnectionForPool(), System.currentTimeMillis());
		}

	}

	MyConnection createNewConnectionForPool() {
		try {
			return new MyConnection(DriverManager.getConnection(url, username, password));
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	void dead(MyConnection myConnection) {
		try {
			myConnection.actuallyClose();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	synchronized MyConnection getConnectionFromPool() {
		while (lock.size() == maxPoolSize) {
			// Wait for an existing connection to be freed up.
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		long now = System.currentTimeMillis();
		MyConnection myConnection;
		if (unlock.size() > 0) {
			Enumeration<MyConnection> e = unlock.keys();
			while (e.hasMoreElements()) {
				myConnection = e.nextElement();
				if ((now - unlock.get(myConnection)) > deadTime) {
					// object has dead
					unlock.remove(myConnection);
					dead(myConnection);
					myConnection = null;
				} else {
					unlock.remove(myConnection);
					lock.put(myConnection, now);
					myConnection.setMyConnectionToBeOpen();
					return myConnection;
				}
			}
		}
		// no objects available, create a new one
		myConnection = createNewConnectionForPool();
		lock.put(myConnection, now);
		return (myConnection);
	}

	synchronized void returnConnectionToPool(MyConnection myConnection) {
		lock.remove(myConnection);
		unlock.put(myConnection, System.currentTimeMillis());
		notifyAll();
	}

}
