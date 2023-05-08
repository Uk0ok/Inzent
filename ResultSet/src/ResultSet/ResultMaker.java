package ResultSet;

import java.io.File;
import java.io.IOException;

public class ResultMaker {
	/*
	 * 파일 존재 여부 체크
	 */
	public void chkFile(File file) throws IOException {
//		File file = new File(ReadProperties.FilePathElement);
		
		if (!file.exists() && !file.createNewFile()) // 파일이 존재하지 않고 + 파일생성에 실패할 때
			throw new IOException("파일 생성에 실패하였습니다.");
	}
}
