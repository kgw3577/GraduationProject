package util;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DatabaseUtil {	

	public static Connection getConnection() {
		DataSource dataFactory;
		try {
			/*
			String dbURL = "jdbc:mysql://54.180.99.123:3306/android?useSSL=false";				// 로컬 작업용
			String dbID = "redgrave";											// MySQL 계정 아이디
			String dbPassword = "3333";								// MySQL 계정 패스워드 (jjyong10)
			Class.forName("com.mysql.jdbc.Driver"); 						// 드라이버 호출
			return DriverManager.getConnection(dbURL, dbID, dbPassword);	// 컨넥션 객체 생성(DB와 연결)
			*/
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			dataFactory = (DataSource) envContext.lookup("jdbc/mysql");
			return dataFactory.getConnection();
		}
		// 예외처리
		catch(Exception e)													
		{
			e.printStackTrace();
		}
		return null;
		
	}
}
