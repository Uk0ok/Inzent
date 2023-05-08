package processing;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {

	public static void main(String[] args) {
		long beforeTime = System.currentTimeMillis(); //코드 실행 전에 시간 받아오기
		ReadProperties.read();
		
		SelectInsert si = new SelectInsert();
		
		DBConnection db1 = new DBConnection();
		DBConnection db2 = new DBConnection();
		Connection conn1 = db1.XTORMConnection();
		Connection conn2 = db2.XVARMConnection();
		
		si.Element(conn1, conn2);
		if(conn1 != null) try {conn1.close();} catch (SQLException e) {e.printStackTrace();}
		if(conn2 != null) try {conn2.close();} catch (SQLException e) {e.printStackTrace();}
		System.out.println("Select Insert Element Complete");
		
		conn1 = db1.XTORMConnection();
		conn2 = db2.XVARMConnection();
		
		si.ConetentElement(conn1, conn2);
		if(conn1 != null) try {conn1.close();} catch (SQLException e) {e.printStackTrace();}
		if(conn2 != null) try {conn2.close();} catch (SQLException e) {e.printStackTrace();}
		System.out.println("Select Insert ContentElement Complete");
		
		long afterTime = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
		long secDiffTime = (afterTime - beforeTime) / 1000; //두 시간에 차 계산
		System.out.println("시간(s) : " + secDiffTime);
	}
}
