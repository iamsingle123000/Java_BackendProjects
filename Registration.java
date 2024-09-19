package in.ineuron.ai;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
  
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String uname=req.getParameter("name");
		String uemail=req.getParameter("email");
		String upwd=req.getParameter("pass");
		String umobile=req.getParameter("contact");
		
		PrintWriter out= resp.getWriter();
		out.print(uname);
		out.print(uemail);
		out.print(upwd);
		out.print(umobile);
		
		RequestDispatcher dispatcher=null;
		Connection con=null;
		PreparedStatement pstmt=null;
		
		// for the database..
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			 con=DriverManager.getConnection("jdbc:mysql://localhost:3306/yadavji?useSSL=false","root","chuku");
			 pstmt=con.prepareStatement("insert into users(uname,upwd,uemail,umobile)values(?,?,?,?)");
			pstmt.setString(1, uname);
			pstmt.setString(2, upwd);
			pstmt.setString(3, uemail);
			pstmt.setString(4, umobile);
			
			int noOfRowEffected=pstmt.executeUpdate();
			
			dispatcher=req.getRequestDispatcher("registration.jsp");
			if(noOfRowEffected>0)
			{
				req.setAttribute("status", "successful");
			}
			else {
				req.setAttribute("status", "failed");
			}
			dispatcher.forward(req, resp);
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				con.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
