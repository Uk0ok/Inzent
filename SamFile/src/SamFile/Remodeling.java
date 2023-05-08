package SamFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class Remodeling extends ReadProperties{
	public static Connection conn = null;
	public static PreparedStatement pstmt = null;
	public static ResultSet rs = null;
	
	public static void con() {
		
		try {
			Class.forName(DBDriver1);
			conn = DriverManager.getConnection(DBUrl1, DBId1, DBPwd1); 
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void file() {
		
		File file = new File(FilePath);
		
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			pstmt = conn.prepareStatement(Query1);
			rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			
			int cols = rsmd.getColumnCount();
			
			StringBuilder sb = new StringBuilder();
			FileWriter fw = new FileWriter(file);
			BufferedWriter writer = new BufferedWriter(fw);
			
			while(rs.next()) {
				for(int i=1; i <= cols; i++) {
					if(i==cols) {
						if(rs.getString(i) == null) {
							writer.write("NULL");
						} else {
							writer.write(rs.getString(i).trim());
						}
					} else {
						if(rs.getString(i) == null) {
							writer.write("NULL");
							writer.write(Delimiter);
						} else {
							writer.write(rs.getString(i).trim());
							writer.write(Delimiter);
						}
					}
				}
				writer.write("\n");
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		read();
		con();
		file();
		
		System.out.println("finished!");
		
		try {
			conn.close();
			pstmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}