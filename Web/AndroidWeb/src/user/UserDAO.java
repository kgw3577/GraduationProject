package user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import util.DatabaseUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class UserDAO {
	private Connection conn = null;
	
	public UserDAO() {
		try {
			conn = DatabaseUtil.getConnection();
		}  catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private PreparedStatement pstmt = null;
	private PreparedStatement pstmt2 = null;
	private ResultSet rs = null;
	private String sql = "";
	private String sql2 = "";
	String returns = "";
	String returns2 = "";

	// 데이터베이스와 통신하기 위한 코드가 들어있는 메서드
	public String joindb(String id, String pwd) {
		try {
			//Class.forName("com.mysql.jdbc.Driver");
			//conn = DriverManager.getConnection(jdbcUrl, dbId, dbPw);
			sql = "select userID from USER where userID=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				if (rs.getString("userID").equals(id)) { // 이미 아이디가 있는 경우
					returns = "id";
				} 
			} else { // 입력한 아이디가 없는 경우
				sql2 = "insert into USER values(?, ?, ?, ?, ?, ?, ?);";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, id);
				pstmt2.setString(2, "test");
				pstmt2.setString(3, "test@test.com");
				pstmt2.setString(4, pwd);
				pstmt2.setString(5, "남자");
				pstmt2.setString(6, "25");
				pstmt2.setString(7, "NO");
				pstmt2.executeUpdate();

				returns = "ok";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
			if (conn != null)try {conn.close();} catch (SQLException ex) {}
			if (pstmt2 != null)try {pstmt2.close();} catch (SQLException ex) {}
			if (rs != null)try {rs.close();} catch (SQLException ex) {}
		}
		return returns;
	}

	public String logindb(String id, String pwd) {
		try {
			//Class.forName("com.mysql.jdbc.Driver");
			//conn = DriverManager.getConnection(jdbcUrl, dbId, dbPw);
			sql = "select userID, userPassword from USER where userID=? and userPassword=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				if (rs.getString("userID").equals(id) && rs.getString("userPassword").equals(pwd)) {
					returns2 = "true";// 로그인 가능
				} else {
					returns2 = "false"; // 로그인 실패
				}
			} else {
				returns2 = "false"; // 아이디 또는 비밀번호 존재 X
			}

		} catch (Exception e) {

		} finally {if (rs != null)try {rs.close();} catch (SQLException ex) {}
			if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
			if (conn != null)try {conn.close();} catch (SQLException ex) {}
		}
		return returns2;
	}
	
	public String joindbs(String id, String pwd) {
		try {
			//Class.forName("com.mysql.jdbc.Driver");
			//conn = DriverManager.getConnection(jdbcUrl, dbId, dbPw);
			sql = "select userID from USER where userID=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				if (rs.getString("userID").equals(id)) { // 이미 아이디가 있는 경우
					returns = "id";
				} 
			} else { // 입력한 아이디가 없는 경우
				sql2 = "insert into USER values(?, ?, ?, ?, ?, ?, ?);";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, id);
				pstmt2.setString(2, "test");
				pstmt2.setString(3, "test@test.com");
				pstmt2.setString(4, pwd);
				pstmt2.setString(5, "남자");
				pstmt2.setString(6, "25");
				pstmt2.setString(7, "NO");
				pstmt2.executeUpdate();

				returns = "ok";
				
				JSONObject obj = new JSONObject();
				obj.put("userID", id);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
			if (conn != null)try {conn.close();} catch (SQLException ex) {}
			if (pstmt2 != null)try {pstmt2.close();} catch (SQLException ex) {}
			if (rs != null)try {rs.close();} catch (SQLException ex) {}
		}
		return returns;
	}
}
