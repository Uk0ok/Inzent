package SamFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Test {

	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "XVARM", "XVARM");
			
			File file = new File("D:\\dev\\testSql.txt");
			
			if(!file.exists()) {
				file.createNewFile();
			}

			String query = "SELECT * FROM ASYSELEMENT";
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			FileWriter fw = new FileWriter(file);
			BufferedWriter writer = new BufferedWriter(fw);
			
			while(rs.next()) {
				writer.write(rs.getString("ELEMENTID").trim());
				writer.write("|");
				writer.write(rs.getString("DESCR").trim());
				writer.write("|");
				writer.write(rs.getString("USERSCLASS").trim());
				writer.write("|");
				writer.write(rs.getString("ECLASSID").trim());
				writer.write("|");
				if(rs.getString("EXPIRATION") == null) {
					writer.write("NULL");
					writer.write("\n");
				} else {
					writer.write(rs.getString("EXPIRATION").trim());
					writer.write("\n");
				}
			}
			writer.close();
			System.out.println("finished!");
			
			
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
		}
	}

}
