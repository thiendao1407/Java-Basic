1. Auto-return connection to pool when calling close() method
 
	@Override
	public void close() throws SQLException {
		isClosed = true;
		DataSource.returnConnection(this);
	}

2. Remove validate() method from 'ConnectionPool'
3. Change the visibility of returnConnection() method of 'DataSource' to default.
 Now we can only use this method indirectly outside the 'connectionpool' package by calling close () method and we do not need to check if Hashtable<Connection, Long> 'lock' contains the returning connection or not 