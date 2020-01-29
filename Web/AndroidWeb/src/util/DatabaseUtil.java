package util;
import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseUtil {	

	public static Connection getConnection() {
		try {
			//?useSSL=false
			//String dbURL = "jdbc:mysql://13.125.248.126:3306/android";			// AWS 서버
			String dbURL = "jdbc:mysql://localhost:3306/android?useSSL=false";				// 로컬 작업용
			String dbID = "root";											// MySQL 계정 아이디
			String dbPassword = "3333";									// MySQL 계정 패스워드 (jjyong10)
			Class.forName("com.mysql.jdbc.Driver"); 						// 드라이버 호출
			return DriverManager.getConnection(dbURL, dbID, dbPassword);	// 컨넥션 객체 생성(DB와 연결)
		}
		// 예외처리
		catch(Exception e)													
		{
			e.printStackTrace();
		}
		return null;
		
	}
}
