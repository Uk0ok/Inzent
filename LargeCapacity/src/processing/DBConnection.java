package processing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	/*
	 * Oracle19c DB Connection
	 */
	public Connection XTORMConnection() {
		Connection conn = null;
		
		try {
			Class.forName(ReadProperties.DBDriver1);
			conn = DriverManager.getConnection(ReadProperties.DBUrl1, ReadProperties.DBId1, ReadProperties.DBPwd1);
			conn.setAutoCommit(false);
			
		} catch (ClassNotFoundException e) {
			System.err.println(e.getMessage());
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return conn;
	}
	
	/*
	 * Oracle11g DB Connection
	 */
	public Connection XVARMConnection() {
		Connection conn2 = null;
		
		try {
			Class.forName(ReadProperties.DBDriver2);
			
			conn2 = DriverManager.getConnection(ReadProperties.DBUrl2, ReadProperties.DBId2, ReadProperties.DBPwd2);
			conn2.setAutoCommit(false);
			
		} catch (ClassNotFoundException e) {
			System.err.println(e.getMessage());
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return conn2;
	}
}
