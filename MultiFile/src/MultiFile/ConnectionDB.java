package MultiFile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
	/*
	 * Oracle19c DB Connection
	 */
	public Connection XTORMConnection() {
		Connection conn = null;
		
		try {
			Class.forName(PropertiesRead.DBDriver1);
			conn = DriverManager.getConnection(PropertiesRead.DBUrl1, PropertiesRead.DBId1, PropertiesRead.DBPwd1);
			conn.setAutoCommit(false);
			
		} catch (ClassNotFoundException e) {
			System.err.println(e.getMessage());
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		System.out.println(conn);
		return conn;
	}
	
	/*
	 * Oracle11g DB Connection
	 */
	public Connection XVARMConnection() {
		Connection conn2 = null;
		
		try {
			Class.forName(PropertiesRead.DBDriver2);
			
			conn2 = DriverManager.getConnection(PropertiesRead.DBUrl2, PropertiesRead.DBId2, PropertiesRead.DBPwd2);
			conn2.setAutoCommit(false);
			
		} catch (ClassNotFoundException e) {
			System.err.println(e.getMessage());
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return conn2;
	}
}
