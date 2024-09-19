

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.mysql.cj.Session;
@WebServlet("/login")
public class Login extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String uemail=req.getParameter("username");
		String upwd=req.getParameter("password");
		HttpSession httpSession=req.getSession();
		
		Connection con=null;
		PreparedStatement pstmt=null;
		RequestDispatcher requestDispatcher=null;
		
		// code for the database..
		
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/yadavji?useSSL=false","root","chuku");
		pstmt=con.prepareStatement("select * from users where uemail=? and upwd=?");
		pstmt.setString(1, uemail);
		pstmt.setString(2, upwd);
		
		ResultSet resultSet=pstmt.executeQuery();
		
//		if(resultSet.next())
//		{
//			httpSession.setAttribute("name", resultSet.getString("uname"));
//			requestDispatcher=req.getRequestDispatcher("index.jsp");
//		}
//		else
//		{
//			req.setAttribute("status", "failed");
//			requestDispatcher=req.getRequestDispatcher("login.jsp");	
//		}
//		requestDispatcher.forward(req, resp);
		if (resultSet.next()) {
		    httpSession.setAttribute("name", resultSet.getString("uname"));
		    requestDispatcher = req.getRequestDispatcher("index.jsp");
		} else {
		    req.setAttribute("status", "failed");
		    requestDispatcher = req.getRequestDispatcher("login.jsp");    
		}
		requestDispatcher.forward(req, resp);
		
		
	}
	catch (Exception e) {
		e.printStackTrace();
	}
		
	}
 
}
