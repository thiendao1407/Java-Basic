- Update returnConnectionToPool() method to to avoid users returning foreign connections to the pool
- We need to manually return connection to pool and assign the variable referring to it to null otherwise the the variable can continue to interfere with the database
	- Example 

			Connection con = null;
				try {
					con = DataSource.getConnection();
					try (PreparedStatement ps = con.prepareStatement(sql)) {
						int index = 1;
						ps.setString(index++, ...);
						...
						ps.executeUpdate();
					}
				} finally {
					DataSource.returnConnection(con);
					con = null;
				}
