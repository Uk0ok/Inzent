package MultiFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class AdminFile {
	public int fileCount = 0;
	/*
	 * 파일 존재 여부 체크
	 */
	public void writeElementFile(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(PropertiesRead.Query1);
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ResultSetMetaData rsmd = null;
		
		try {
			rsmd = rs.getMetaData();
			int cols = rsmd.getColumnCount(); // ResultSet의 총 필드 수 반환
			System.out.println(cols);
			
			FileWriter fw = null;
			BufferedWriter bw = null;
			
			try {
				fw = new FileWriter(new File(PropertiesRead.FilePath + Integer.toString(fileCount) + ".sam"));
				bw = new BufferedWriter(fw);
				
				int count = 0;
				while(rs.next()) {
					++count;
					for (int i = 1; i <= cols; i++) {
						if(i==cols) {
							if (rs.getString(i)==null) {
								bw.write("NULL");
							} else {
								bw.write(rs.getString(i).trim());
							}
						} else {
							if(rs.getString(i) == null) {
								bw.write("NULL");
								bw.write(PropertiesRead.Delimiter);
							} else {
								bw.write(rs.getString(i).trim());
								bw.write(PropertiesRead.Delimiter);
							}
						}
					} 
					bw.write("\n");
					if(count == 1000) { // 1000개의 줄을 write 했으면(조건)
						++fileCount; // 생성될 파일 이름 변경을 위한 값 증가
						fw = new FileWriter(new File(PropertiesRead.FilePath + Integer.toString(fileCount) + ".sam"));
						bw = new BufferedWriter(fw);
						count = 0; // 다시 0부터 1000까지 count하도록 0으로 설정
					} 
				}
			} finally {
				if(bw != null)
					bw.close();
				if(fw != null)
					fw.close();
			}
		} catch (SQLException  | IOException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch(SQLException e) {
					e.printStackTrace();
					}
				}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch(SQLException e) {
					e.printStackTrace();
					}
				}
		}
	}
}
