
package clothes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.DatabaseUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ClothesDAO {
	private Connection conn = null;
	
	public ClothesDAO() {
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
	
	public String add(ClothesVO c) {
		int no = c.getNo(); //자동으로 채워지게 하기.
		String img = c.getImg();
		String closetName = c.getClosetName();
		String userID = c.getUserID();
		
		try {
			String query = "insert into CLOTHES (cloNo, cloImg, userID, closetName) values(?, ?, ?, ?);"; System.out.println("prepareStatememt: " + query);
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			pstmt.setString(2, img);
			pstmt.setString(3, userID);
			pstmt.setString(4, closetName);
			pstmt.executeUpdate(); //확인 처리 필요

			returns = "ok";
			
			//JSONObject obj = new JSONObject();
			//obj.put("userID", id);
			
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (conn != null)try {conn.close();} catch (SQLException ex) {}
				if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
				if (pstmt2 != null)try {pstmt2.close();} catch (SQLException ex) {}
				if (rs != null)try {rs.close();} catch (SQLException ex) {}
			}
			return returns;
	}
	
	
	
	
	public String delete(ClothesVO c) {
		int no = c.getNo();
		try {
			String query = "delete from CLOTHES where cloNo=?"; System.out.println("prepareStatememt: " + query);
			pstmt = conn.prepareStatement(query); 
			pstmt.setInt(1, no); //값 바인딩
			pstmt.executeUpdate(); //확인 처리 필요

			returns = "ok";
			
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (conn != null)try {conn.close();} catch (SQLException ex) {}
				if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
				if (pstmt2 != null)try {pstmt2.close();} catch (SQLException ex) {}
				if (rs != null)try {rs.close();} catch (SQLException ex) {}
			}
			return returns;
	}
	
	public List<ClothesVO> listUsers() {
		List<ClothesVO> list = new ArrayList<ClothesVO>();
		try {
			String query = "select * from CLOTHES "; System.out.println("prepareStatememt: " + query);
			pstmt = conn.prepareStatement(query); System.out.println("Statement 생성 성공");
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				String name = rs.getString("cloName");
				String category = rs.getString("cloCategory");
				String color = rs.getString("cloColor");
				String userID = rs.getString("userID");
				
				ClothesVO vo = new ClothesVO();
				vo.setName(name);
				vo.setCategory(category);
				vo.setColor(color);
				vo.setUserID(userID);
				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
			if (conn != null)try {conn.close();} catch (SQLException ex) {}
			if (rs != null)try {rs.close();} catch (SQLException ex) {}
		}
		return list;
	}
	
	public JSONArray info(ClothesVO c) {
		
		
		
		return new JSONArray(); //임시
	}
	
	
	/*
	public String login(ClothesVO c) {
		try {
			
			String id = u.getId();
			String pwd = u.getPwd();
			
			String query = "select userID, userPassword from USER where userID=? and userPassword=?"; System.out.println("prepareStatememt: " + query);
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.setString(2, pwd); //값 바인딩
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				if (rs.getString("userID").equals(id) && rs.getString("userPassword").equals(pwd)) {
					returns = "true";// 로그인 가능
					}
				else {
					returns = "false"; // 로그인 실패
					}
				}
			else {
				returns = "false"; // 아이디 또는 비밀번호 존재 X
				}
			
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (conn != null)try {conn.close();} catch (SQLException ex) {}
				if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
				if (pstmt2 != null)try {pstmt2.close();} catch (SQLException ex) {}
				if (rs != null)try {rs.close();} catch (SQLException ex) {}
			}
		
		return returns;

	}
	*/
	
	
}
