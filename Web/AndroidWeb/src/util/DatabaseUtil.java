package util;
import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseUtil {	

	public static Connection getConnection() {
		try {
			//?useSSL=false
			String dbURL = "jdbc:mysql://13.125.248.126:3306/android";			// DB 주소 및 DB 이름
			String dbID = "root";											// MySQL 계정 아이디
			String dbPassword = "jjyong10";									// MySQL 계정 패스워드
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
