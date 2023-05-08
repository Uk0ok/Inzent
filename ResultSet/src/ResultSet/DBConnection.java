package ResultSet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	public Connection connection19c() {
		Connection conn19 = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn19 = DriverManager.getConnection("jdbc:oracle:thin:@192.168.18.137:1521:orcl", "XTORM", "XTORM");
			conn19.setAutoCommit(false);
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		} return conn19;
	}
	
	public Connection connection11g() {
		Connection conn11 = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn11 = DriverManager.getConnection("jdbc:oracle:thin:@192.168.80.132:1522:orcl", "XTORM", "XTORM");
			conn11.setAutoCommit(false);
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		} return conn11;
	}
}
