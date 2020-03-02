package user;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.UserDAO;
import user.UserVO;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/useruseruser")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	String returns="";
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		/*
		//인스턴스 생성
		UserDAO dao = new UserDAO();
		PrintWriter out = response.getWriter();

		//로그인 요청인지 회원가입 요청인지를 구분하여 메서드를 실행하도록 합니다.
		String command = request.getParameter("type");
		
		//로그인
		if(command != null && "login".equals(command)) {
			String id = request.getParameter("id");
			String pwd = request.getParameter("pwd");
			returns = dao.login(id,pwd);
			out.print(returns);
		}
		//회원가입
		else if(command != null && "join".equals(command)) {
			String id = request.getParameter("id");
			String pwd = request.getParameter("pwd");
			returns = dao.join(id, pwd);
			System.out.println(returns);
			out.print(returns);
		}
		//아이디 찾기
		else if(command != null && "forget-id".equals(command)) {

		} 
		//비밀번호 찾기
		else if(command != null && "forget-pass".equals(command)) {

		}
		//회원 탈퇴
		else if(command != null && "del".equals(command)) {
			String id = request.getParameter("id");
			returns = dao.delUser(id);
			out.print(returns);
		}
		*/
		List<UserVO> list = new UserDAO().listUsers();
		
		out.print("<html><body>");
	    out.print("<table  border=1><tr align='center' bgcolor='lightgreen'>");
	    out.print("<td>아이디</td><td>비밀번호</td><td>이름</td><td>성별</td><td>나이</td></tr>");
	    
	    for (int i=0; i<list.size();i++){
			UserVO userVO=(UserVO) list.get(i);
			String id=userVO.getId();
			String pwd = userVO.getPwd();
			String name=userVO.getName();
			String gender=userVO.getGender();
			String age = userVO.getAge();
			out.print("<tr><td>"+id+"</td><td>"+pwd+"</td><td>"+name+"</td><td>"+
				                gender+"</td><td>"+age+"</td></tr>");		
	      }
	      out.print("</table></body></html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
