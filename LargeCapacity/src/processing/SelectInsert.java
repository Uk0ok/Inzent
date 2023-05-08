package processing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class SelectInsert {
	
	/*
	 * oracle19c에 존재하는 AsysElement 테이블에서 oracle11g에 있는 AsysElement로 값을 옮기는 프로그램
	 * 
	 * ## 예외 - elementId가 primary key 이므로 중복값이 들어가면 안된다.
	 */
	public void Element(Connection conn1, Connection conn2) {
		PreparedStatement selectElementPstmt = null;
		PreparedStatement InsertElementPstmt = null;
		ResultSet selectElementRs = null;
		ResultSetMetaData ElementRsmd = null;
		int insertCount = 0;
		
		try {
			selectElementPstmt = conn1.prepareStatement(ReadProperties.Query1, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			InsertElementPstmt = conn2.prepareStatement(ReadProperties.Query3);

			int seq;
			for (seq = 1; seq >= 1; seq += 10000) {
				selectElementPstmt.setInt(1, seq);
				selectElementRs = selectElementPstmt.executeQuery();
				
				selectElementRs.last();
				int rowsCount = selectElementRs.getRow();
				selectElementRs.beforeFirst();
				
				ElementRsmd = selectElementRs.getMetaData();
				int ElementCols = ElementRsmd.getColumnCount(); // column 수 가져오기
				
				while (selectElementRs.next()) { // Resultset 에 담긴 데이터를 한 줄씩 가져오기
					++insertCount;
					for (int i=1; i<=ElementCols; i++) {
						InsertElementPstmt.setString(i, selectElementRs.getString(selectElementPstmt.getMetaData().getColumnName(i))); // getColumeName으로 컬럼명 가져오기
					}
					InsertElementPstmt.addBatch(); // addBatch에 담기
					InsertElementPstmt.clearParameters(); // 파라미터 클리어
					
					if((insertCount % 1000) == 0) { // 1000개씩 담아서 한번에 excuteBatch 후 commit
						InsertElementPstmt.executeBatch(); // Batch 실행
						InsertElementPstmt.clearBatch(); // Batch 초기화
						conn2.commit(); // commit
					}
				}
				// commit 되지 못한 남은 나머지 값들에 대한 commit
				InsertElementPstmt.executeBatch();
				conn2.commit(); 
				
				if (rowsCount == 0) 
					break;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
//			System.err.println(e.getMessage());
			System.out.println("SQLException 발생");
			try {
				conn2.rollback();
				System.out.println("rollback 완료");
			} catch (SQLException e1) {
				System.err.println("rollback 오류");
				e1.printStackTrace();
				System.err.println("프로그램을 종료합니다.");
				System.exit(1); // 프로그램 종료
			}
			System.err.println("프로그램을 종료합니다.");
			System.exit(1); // 프로그램 종료
		} finally {
			try {
				if(selectElementRs != null) selectElementRs.close();
				if(selectElementPstmt != null) selectElementPstmt.close();
			} catch (SQLException e) {
				System.err.println(e.getMessage());
				System.out.println("SQLException 발생");
				System.err.println("프로그램을 종료합니다.");
				System.exit(1);
			}
		}
	}
	
	/*
	 * oracle19c에 존재하는 AsysContentElement 테이블에서 oracle11g에 있는 AsysContentElement로 값을 옮기는 프로그램
	 */
	public void ConetentElement(Connection conn1, Connection conn2) {
		PreparedStatement selectContentElementPstmt = null;
		PreparedStatement InsertContentElementPstmt = null;
		ResultSet selectContentElementRs = null;
		ResultSetMetaData ContentElementRsmd = null;
		int insertCount = 0;
		
		try {
			selectContentElementPstmt = conn1.prepareStatement(ReadProperties.Query2, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			InsertContentElementPstmt = conn2.prepareStatement(ReadProperties.Query4);
			
			
			
			int seq;
			for (seq = 1; seq >= 1; seq += 10000) {
				selectContentElementPstmt.setInt(1, seq);
				selectContentElementRs = selectContentElementPstmt.executeQuery();
				
				selectContentElementRs.last();
				int rowsCount = selectContentElementRs.getRow();
				selectContentElementRs.beforeFirst();
				
				ContentElementRsmd = selectContentElementRs.getMetaData();
				int ElementCols = ContentElementRsmd.getColumnCount(); // column 수 가져오기
				
				while (selectContentElementRs.next()) { // Resultset 에 담긴 데이터를 한 줄씩 가져오기
					++insertCount;
					for (int i=1; i<=ElementCols; i++) {
						InsertContentElementPstmt.setString(i, selectContentElementRs.getString(selectContentElementPstmt.getMetaData().getColumnName(i))); // getColumeName으로 컬럼명 가져오기
					}
					InsertContentElementPstmt.addBatch(); // addBatch에 담기
					InsertContentElementPstmt.clearParameters(); // 파라미터 클리어
					
					if((insertCount % 1000) == 0) { // 1000개씩 담아서 한번에 excuteBatch 후 commit
						InsertContentElementPstmt.executeBatch(); // Batch 실행
						InsertContentElementPstmt.clearBatch(); // Batch 초기화
						conn2.commit(); // commit
					}
				}
				// commit 되지 못한 남은 나머지 값들에 대한 commit
				InsertContentElementPstmt.executeBatch();
				conn2.commit(); 
				
				if (rowsCount == 0) 
					break;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
//			System.err.println(e.getMessage());
			System.out.println("SQLException 발생");
			try {
				conn2.rollback();
				System.out.println("rollback 완료");
			} catch (SQLException e1) {
				System.err.println("rollback 오류");
				e1.printStackTrace();
				System.err.println("프로그램을 종료합니다.");
				System.exit(1); // 프로그램 종료
			}
			System.err.println("프로그램을 종료합니다.");
			System.exit(1); // 프로그램 종료
		} finally {
			try {
				if(selectContentElementRs != null) selectContentElementRs.close();
				if(selectContentElementPstmt != null) selectContentElementPstmt.close();
			} catch (SQLException e) {
				System.err.println(e.getMessage());
				System.out.println("SQLException 발생");
				System.err.println("프로그램을 종료합니다.");
				System.exit(1);
			}
		}
	}
}
