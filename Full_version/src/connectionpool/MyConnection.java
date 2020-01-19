package connectionpool;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

public class MyConnection implements Connection {

	private Connection realConnection;
	private boolean isClosed;

	public MyConnection(Connection realConnection) {
		this.realConnection = realConnection;
		isClosed = false;
	}

	// Our new method (don't set public)
	// ------------------------
	Connection getRealConnection() {
		return this.realConnection;
	}

	void actuallyClose() throws SQLException {
		this.realConnection.close();
	}

	boolean isActuallyClosed() throws SQLException {
		return this.realConnection.isClosed();
	}

	// ------------------------
	@Override
	public void close() throws SQLException {
		isClosed = true;
		DataSource.returnConnection(this);
	}

	@Override
	public boolean isClosed() throws SQLException {
		return isClosed;
	}

	// ------------------------
	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		if (isClosed || realConnection.isClosed())
			throw new SQLException("Connection is closed");
		return realConnection.unwrap(iface);
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		if (isClosed || realConnection.isClosed())
			throw new SQLException("Connection is closed");
		return realConnection.isWrapperFor(iface);
	}

	@Override
	public Statement createStatement() throws SQLException {
		if (isClosed || realConnection.isClosed())
			throw new SQLException("Connection is closed");

		return realConnection.createStatement();
	}

	@Override
	public PreparedStatement prepareStatement(String sql) throws SQLException {
		if (isClosed || realConnection.isClosed())
			throw new SQLException("Connection is closed");
		return realConnection.prepareStatement(sql);
	}

	@Override
	public CallableStatement prepareCall(String sql) throws SQLException {
		if (isClosed || realConnection.isClosed())
			throw new SQLException("Connection is closed");
		return realConnection.prepareCall(sql);
	}

	@Override
	public String nativeSQL(String sql) throws SQLException {
		if (isClosed || realConnection.isClosed())
			throw new SQLException("Connection is closed");
		return realConnection.nativeSQL(sql);
	}

	@Override
	public void setAutoCommit(boolean autoCommit) throws SQLException {
		if (isClosed || realConnection.isClosed())
			throw new SQLException("Connection is closed");
		realConnection.setAutoCommit(autoCommit);

	}

	@Override
	public boolean getAutoCommit() throws SQLException {
		if (isClosed || realConnection.isClosed())
			throw new SQLException("Connection is closed");
		return realConnection.getAutoCommit();
	}

	@Override
	public void commit() throws SQLException {
		if (isClosed || realConnection.isClosed())
			throw new SQLException("Connection is closed");
		realConnection.commit();

	}

	@Override
	public void rollback() throws SQLException {
		if (isClosed || realConnection.isClosed())
			throw new SQLException("Connection is closed");
		realConnection.rollback();

	}

	@Override
	public DatabaseMetaData getMetaData() throws SQLException {
		if (isClosed || realConnection.isClosed())
			throw new SQLException("Connection is closed");
		return realConnection.getMetaData();
	}

	@Override
	public void setReadOnly(boolean readOnly) throws SQLException {
		if (isClosed || realConnection.isClosed())
			throw new SQLException("Connection is closed");
		realConnection.setReadOnly(readOnly);
	}

	@Override
	public boolean isReadOnly() throws SQLException {
		if (isClosed || realConnection.isClosed())
			throw new SQLException("Connection is closed");
		return realConnection.isReadOnly();
	}

	@Override
	public void setCatalog(String catalog) throws SQLException {
		if (isClosed || realConnection.isClosed())
			throw new SQLException("Connection is closed");
		realConnection.setCatalog(catalog);
	}

	@Override
	public String getCatalog() throws SQLException {
		if (isClosed || realConnection.isClosed())
			throw new SQLException("Connection is closed");
		return realConnection.getCatalog();
	}

	@Override
	public void setTransactionIsolation(int level) throws SQLException {
		if (isClosed || realConnection.isClosed())
			throw new SQLException("Connection is closed");
		realConnection.setTransactionIsolation(level);
	}

	@Override
	public int getTransactionIsolation() throws SQLException {
		if (isClosed || realConnection.isClosed())
			throw new SQLException("Connection is closed");
		return realConnection.getTransactionIsolation();
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		if (isClosed || realConnection.isClosed())
			throw new SQLException("Connection is closed");
		return realConnection.getWarnings();
	}

	@Override
	public void clearWarnings() throws SQLException {
		if (isClosed || realConnection.isClosed())
			throw new SQLException("Connection is closed");
		realConnection.clearWarnings();
	}

	@Override
	public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
		if (isClosed || realConnection.isClosed())
			throw new SQLException("Connection is closed");
		return realConnection.createStatement(resultSetType, resultSetConcurrency);
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
			throws SQLException {
		if (isClosed || realConnection.isClosed())
			throw new SQLException("Connection is closed");
		return realConnection.prepareStatement(sql, resultSetType, resultSetConcurrency);
	}

	@Override
	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
		if (isClosed || realConnection.isClosed())
			throw new SQLException("Connection is closed");
		return realConnection.prepareCall(sql, resultSetType, resultSetConcurrency);
	}

	@Override
	public Map<String, Class<?>> getTypeMap() throws SQLException {
		if (isClosed || realConnection.isClosed())
			throw new SQLException("Connection is closed");
		return realConnection.getTypeMap();
	}

	@Override
	public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
		if (isClosed || realConnection.isClosed())
			throw new SQLException("Connection is closed");
		realConnection.setTypeMap(map);
	}

	@Override
	public void setHoldability(int holdability) throws SQLException {
		if (isClosed || realConnection.isClosed())
			throw new SQLException("Connection is closed");
		realConnection.setHoldability(holdability);
	}

	@Override
	public int getHoldability() throws SQLException {
		if (isClosed || realConnection.isClosed())
			throw new SQLException("Connection is closed");
		return realConnection.getHoldability();
	}

	@Override
	public Savepoint setSavepoint() throws SQLException {
		if (isClosed || realConnection.isClosed())
			throw new SQLException("Connection is closed");
		return realConnection.setSavepoint();
	}

	@Override
	public Savepoint setSavepoint(String name) throws SQLException {
		if (isClosed || realConnection.isClosed())
			throw new SQLException("Connection is closed");
		return realConnection.setSavepoint(name);
	}

	@Override
	public void rollback(Savepoint savepoint) throws SQLException {
		if (isClosed || realConnection.isClosed())
			throw new SQLException("Connection is closed");
		realConnection.rollback(savepoint);
	}

	@Override
	public void releaseSavepoint(Savepoint savepoint) throws SQLException {
		if (isClosed || realConnection.isClosed())
			throw new SQLException("Connection is closed");
		realConnection.releaseSavepoint(savepoint);
	}

	@Override
	public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		if (isClosed || realConnection.isClosed())
			throw new SQLException("Connection is closed");
		return realConnection.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
			int resultSetHoldability) throws SQLException {
		if (isClosed || realConnection.isClosed())
			throw new SQLException("Connection is closed");
		return realConnection.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	@Override
	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
			int resultSetHoldability) throws SQLException {
		if (isClosed || realConnection.isClosed())
			throw new SQLException("Connection is closed");
		return realConnection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
		if (isClosed || realConnection.isClosed())
			throw new SQLException("Connection is closed");
		return realConnection.prepareStatement(sql, autoGeneratedKeys);
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
		if (isClosed || realConnection.isClosed())
			throw new SQLException("Connection is closed");
		return realConnection.prepareStatement(sql, columnIndexes);
	}

	@Override
	public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
		if (isClosed || realConnection.isClosed())
			throw new SQLException("Connection is closed");
		return realConnection.prepareStatement(sql, columnNames);
	}

	@Override
	public Clob createClob() throws SQLException {
		if (isClosed || realConnection.isClosed())
			throw new SQLException("Connection is closed");
		return realConnection.createClob();
	}

	@Override
	public Blob createBlob() throws SQLException {
		if (isClosed || realConnection.isClosed())
			throw new SQLException("Connection is closed");
		return realConnection.createBlob();
	}

	@Override
	public NClob createNClob() throws SQLException {
		if (isClosed || realConnection.isClosed())
			throw new SQLException("Connection is closed");
		return realConnection.createNClob();
	}

	@Override
	public SQLXML createSQLXML() throws SQLException {
		if (isClosed || realConnection.isClosed())
			throw new SQLException("Connection is closed");
		return realConnection.createSQLXML();
	}

	@Override
	public boolean isValid(int timeout) throws SQLException {
		if (isClosed || realConnection.isClosed())
			throw new SQLException("Connection is closed");
		return realConnection.isValid(timeout);
	}

	@Override
	public void setClientInfo(String name, String value) throws SQLClientInfoException {

		try {
			if (!isClosed && !realConnection.isClosed())
				realConnection.setClientInfo(name, value);
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	@Override
	public void setClientInfo(Properties properties) throws SQLClientInfoException {
		try {
			if (!isClosed && !realConnection.isClosed())
				realConnection.setClientInfo(properties);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public String getClientInfo(String name) throws SQLException {
		if (isClosed || realConnection.isClosed())
			throw new SQLException("Connection is closed");
		return realConnection.getClientInfo(name);
	}

	@Override
	public Properties getClientInfo() throws SQLException {
		if (isClosed || realConnection.isClosed())
			throw new SQLException("Connection is closed");
		return realConnection.getClientInfo();
	}

	@Override
	public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
		if (isClosed || realConnection.isClosed())
			throw new SQLException("Connection is closed");
		return realConnection.createArrayOf(typeName, elements);
	}

	@Override
	public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
		if (isClosed || realConnection.isClosed())
			throw new SQLException("Connection is closed");
		return realConnection.createStruct(typeName, attributes);
	}

	@Override
	public void setSchema(String schema) throws SQLException {
		if (isClosed || realConnection.isClosed())
			throw new SQLException("Connection is closed");
		realConnection.setSchema(schema);
	}

	@Override
	public String getSchema() throws SQLException {
		if (isClosed || realConnection.isClosed())
			throw new SQLException("Connection is closed");
		return realConnection.getSchema();
	}

	@Override
	public void abort(Executor executor) throws SQLException {
		if (isClosed || realConnection.isClosed())
			throw new SQLException("Connection is closed");
		realConnection.abort(executor);
	}

	@Override
	public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
		if (isClosed || realConnection.isClosed())
			throw new SQLException("Connection is closed");
		realConnection.setNetworkTimeout(executor, milliseconds);
	}

	@Override
	public int getNetworkTimeout() throws SQLException {
		if (isClosed || realConnection.isClosed())
			throw new SQLException("Connection is closed");
		return realConnection.getNetworkTimeout();
	}

}
