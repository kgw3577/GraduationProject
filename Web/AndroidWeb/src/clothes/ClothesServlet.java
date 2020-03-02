package clothes;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ClothesServlet
 */
@WebServlet("/clothesclothesclothes")
public class ClothesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	String returns="";
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

		List<ClothesVO> list = new ClothesDAO().listUsers();
		
		out.print("<html><body>");
	    out.print("<table  border=1><tr align='center' bgcolor='lightgreen'>");
	    out.print("<td>이름</td><td>카테고리</td><td>색상</td><td>소유자</td></tr>");
	    
	    for (int i=0; i<list.size();i++){
	    	ClothesVO clothesVO=(ClothesVO) list.get(i);
			String name=clothesVO.getName();
			String cate = clothesVO.getCategory();
			String color=clothesVO.getColor();
			String userID=clothesVO.getUserID();
			out.print("<tr><td>"+name+"</td><td>"+cate+"</td><td>"+color+"</td><td>"+
					userID+"</td></tr>");		
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
