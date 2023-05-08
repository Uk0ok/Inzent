package SamFile;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
	
	public static void main(String[] args) {
		ReadProperties.read(); // readProperties의 read() 가져오기
		/*
		 * File 유무와 없을 시 생성
		 */
		FileAdmin ad = new FileAdmin();
		try {
			ad.chkFile();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		/*
		 * Asyselement 테이블 값을 sam 파일로 작성
		 */
		DBConnection db = new DBConnection();
		Connection conn = db.XTORMConnection(); // DBConnection에서 connection 정보 가져와서 conn에 입력
		ad.writeElementFile(conn);
		if(conn != null) try {conn.close();} catch (SQLException e) {e.printStackTrace();}
		System.out.println("write Element Complete");
		
		/*
		 * AsysContentElement 테이블 값을 sam 파일로 작성
		 */
		db = new DBConnection();
		conn = db.XTORMConnection();
		ad.writeContentElementFile(conn);
		if(conn != null) try {conn.close();} catch (SQLException e) {e.printStackTrace();}
		System.out.println("write ContentElement Complete");
		
		/*
		 * Sam 파일 값을 다른 DB의 AsysElement로 Insert(MIG)
		 */
		DBConnection db2 = new DBConnection();
		Connection conn2 = db2.XVARMConnection();
		ad.insertElementTable(conn2);
		if(conn2 != null) try {conn2.close();} catch (SQLException e) {e.printStackTrace();}
		System.out.println("insert Element Table Complete");
		
		/*
		 * Sam 파일 값을 다른 DB의 AsysContentElement로 Insert(MIG)
		 */
		db2 = new DBConnection();
		conn2 = db2.XVARMConnection();
		ad.insertContentElementTable(conn2);
		if(conn2 != null) try {conn2.close();} catch (SQLException e) {e.printStackTrace();}
		System.out.println("insert ContentElement Table Complete");
	}
}
