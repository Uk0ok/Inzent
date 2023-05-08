package processing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class Test {
	
	/*
	 * oracle19c에 존재하는 AsysElement 테이블에서 oracle11g에 있는 AsysElement로 값을 옮기는 프로그램
	 * 
	 * ## 예외 - elementId가 primary key 이므로 중복값이 들어가면 안된다.
	 */
	public void Element(Connection conn1, Connection conn2) {
		PreparedStatement selectElementPstmt = null;
		PreparedStatement getRowsCountPstmt = null;
		PreparedStatement InsertElementPstmt = null;
		ResultSet selectElementRs = null;
		ResultSet rowsCountRs = null;
		ResultSetMetaData ElementRsmd = null;
		
		/*
		 * 이러한 방식은 너무 오래 걸린다.
		 */
		
		try {
			getRowsCountPstmt = conn1.prepareStatement("SELECT * FROM ASYSELEMENT WHERE ELEMENTID LIKE '2%'", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			// sql문 뒤의 TYPE_SCROLL_INSENSITIVE와 CONCUR_UPDATABLE 는 Resultset의 양방향 탐색이 가능하게 하는 것이다.
			selectElementPstmt = conn1.prepareStatement(ReadProperties.Query1);
			InsertElementPstmt = conn2.prepareStatement(ReadProperties.Query3);
			
			rowsCountRs = getRowsCountPstmt.executeQuery();
			rowsCountRs.last();
			int rowsCount = rowsCountRs.getRow();
			System.out.println(rowsCount);
			
			for(int seq = 1; seq <= rowsCount; seq += 10000) {
				selectElementPstmt.setInt(1, seq);
				
				selectElementRs = selectElementPstmt.executeQuery();
				
				ElementRsmd = selectElementRs.getMetaData();
				int ElementCols = ElementRsmd.getColumnCount(); // column 수 가져오기
				
				int insertCount = 0;
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
				InsertElementPstmt.executeBatch();
				conn2.commit(); 
				// commit 되지 못한 남은 나머지 값들에 대한 commit
			}
//			SELECT ELEMENTID, DESCR, USERSCLASS, ECLASSID, EXPIRATION FROM ( SELECT SEQ, ELEMENTID, DESCR, USERSCLASS, ECLASSID, EXPIRATION FROM	( SELECT ROWNUM AS SEQ, ELEMENTID, DESCR, USERSCLASS, ECLASSID, EXPIRATION FROM	( SELECT *	FROM ASYSELEMENT a	WHERE ELEMENTID LIKE '2%' ORDER BY ELEMENTID DESC )) WHERE SEQ >= ? ) WHERE ROWNUM <= 10000; 
			
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
}