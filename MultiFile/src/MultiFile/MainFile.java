package MultiFile;

import java.sql.Connection;
import java.sql.SQLException;

public class MainFile {
	public static void main(String[] args) {
		PropertiesRead.read();
		
		AdminFile af = new AdminFile();
		
		ConnectionDB dba = new ConnectionDB();
		Connection conn = dba.XTORMConnection();
		System.out.println(conn);
		af.writeElementFile(conn);
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("write Element Complete!");
	}
}
