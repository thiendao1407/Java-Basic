package connectionpool;

import java.sql.SQLException;

public class DataSource {

	static ConnectionPool pool = new ConnectionPool();

	public static MyConnection getConnection() {
		MyConnection myConnection = pool.getConnectionFromPool();
		return myConnection;
	}

	static void returnConnection(MyConnection myConnection) throws SQLException {
		pool.returnConnectionToPool(myConnection);
	}

}
