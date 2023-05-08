package SamFile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class FileAdmin {
	/*
	 * 파일 존재 여부 체크
	 */
	public void chkFile() throws IOException {
		File file = new File(ReadProperties.FilePath);
		
		if (!file.exists() && !file.createNewFile()) // 파일이 존재하지 않고 + 파일생성에 실패할 때
			throw new IOException("파일 생성에 실패하였습니다.");
	}
	
	/*
	 * DB에서 정보를 가져와 파일에 입력 (ASYSELEMENT)
	 */
	public void writeElementFile(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(ReadProperties.Query1);
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		
		ResultSetMetaData rsmd = null;
		try {
			rsmd = rs.getMetaData();
			int cols = rsmd.getColumnCount(); // 컬럼수 판별
			
			FileWriter fw = null;
			BufferedWriter  writer = null;
			try {
				fw = new FileWriter(new File(ReadProperties.FilePath)); // 파일 경로에 있는 파일에 작성
				writer = new BufferedWriter(fw);
				
				while(rs.next()) { // 한줄씩 읽음
					System.out.println(pstmt.getMetaData().getColumnName(1));
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
								writer.write(ReadProperties.Delimiter);
							} else {
								writer.write(rs.getString(i).trim());
								writer.write(ReadProperties.Delimiter);
							}
						}
					}
					writer.write("\n");
				}
			} finally {
				if(writer != null)
					writer.close();
				if(fw != null)
					fw.close();
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		} finally {
			if(rs != null) {try {rs.close();} catch(SQLException e) {e.printStackTrace();}}
			if(pstmt != null) {try {pstmt.close();} catch(SQLException e) {e.printStackTrace();}}
		}
		
	}
	
	/*
	 * DB에서 정보를 가져와 파일에 입력 (ASYSCONTENTELEMENT)
	 */
	public void writeContentElementFile(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(ReadProperties.Query2);
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		
		ResultSetMetaData rsmd = null;
		try {
			rsmd = rs.getMetaData();
			int cols = rsmd.getColumnCount(); // 컬럼수 판별
			
			FileWriter fw = null;
			BufferedWriter  writer = null;
			try {
				fw = new FileWriter(new File(ReadProperties.FilePath)); // %
				writer = new BufferedWriter(fw);
				
				while(rs.next()) { // 한줄씩 읽음
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
								writer.write(ReadProperties.Delimiter);
							} else {
								writer.write(rs.getString(i).trim());
								writer.write(ReadProperties.Delimiter);
							}
						}
					}
					writer.write("\n");
				}
			} finally {
				if(writer != null)
					writer.close();
				if(fw != null)
					fw.close();
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		} finally {
			if(rs != null) {try {rs.close();} catch(SQLException e) {e.printStackTrace();}}
			if(pstmt != null) {try {pstmt.close();} catch(SQLException e) {e.printStackTrace();}}
		}
		
	}
	
	/*
	 * 파일에서 값을 받아와 DB접속에 insert (ASYSELEMENT)
	 */
	public void insertElementTable(Connection conn2) {
		PreparedStatement pstmt = null;
		BufferedReader br = null;
		String postData = null;
		
		try {
			br = new BufferedReader(new FileReader(ReadProperties.FilePath));
			
			String query = ReadProperties.Query3;
			pstmt = conn2.prepareStatement(query);
			
			try {
				while ((postData = br.readLine()) != null) {
					String inds[] = postData.split("\\" + ReadProperties.Delimiter);
					for (int i = 0; i < inds.length; i++) {
						pstmt.setString(i+1, inds[i]);
					}
					pstmt.execute();
				}
			} catch (SQLIntegrityConstraintViolationException e) {
				e.getMessage();
				System.err.println("ERROR:::UNIQUE 조건으로 인한 insertElementTable 오류");
			}
			conn2.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
			System.out.println("ArrayIndexOfBoundException 발생");
		} finally {
			if(pstmt != null) try {pstmt.close();} catch (SQLException e) {};
			if(br != null) try {br.close();} catch (IOException e) {};
		}
		
	}
	
	/*
	 * 파일에서 값을 받아와 DB접속에 insert (ASYSCONTENTELEMENT)
	 */
	public void insertContentElementTable(Connection conn2) {

		PreparedStatement pstmt = null;
		BufferedReader br = null;
		String postData = null;
		
		try {
			br = new BufferedReader(new FileReader(ReadProperties.FilePath)); // 파일 경로에 있는 파일 읽기
			
			String query = ReadProperties.Query4;
			pstmt = conn2.prepareStatement(query);
			
			try {
				while ((postData = br.readLine()) != null) {
					String inds[] = postData.split("\\" + ReadProperties.Delimiter); // Delimiter인 |가 나올 때 마다 나누어서 list에 넣기
					for (int i = 0; i < inds.length; i++) {
						if (inds[i].equalsIgnoreCase("null"))
							pstmt.setNull(i+1, java.sql.Types.NULL);
						else
							pstmt.setString(i+1, inds[i]);
					}
					pstmt.execute();
				}
			} catch (SQLIntegrityConstraintViolationException e) {
				e.getMessage();
				System.err.println("ERROR:::UNIQUE 조건으로 인한 insertConteElementTable 오류");
			}
			conn2.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
			System.out.println("ArrayIndexOfBoundException 발생");
		} finally {
			if(pstmt != null) try {pstmt.close();} catch (SQLException e) {};
			if(br != null) try {br.close();} catch (IOException e) {};
		}
		
	}
}
