package com.QSP.Login;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignupServlet extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter out=resp.getWriter();
		String fullname=req.getParameter("fullname");
		String username=req.getParameter("username");
		String password=req.getParameter("password");
		String cpassword=req.getParameter("confirmpassword");
		String gender=req.getParameter("gender");
		String email=req.getParameter("email");
		Connection con=null;
		PreparedStatement pstmt=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String dburl="jdbc:mysql://localhost:3306/j2ee_db";
			con=DriverManager.getConnection(dburl,"root","root");
			String query="insert into trainers_info values(?,?,?,?,?)";
			pstmt=con.prepareStatement(query);
			if(password.equals(cpassword)) {
				pstmt.setString(1, fullname);
				pstmt.setString(2, username);
				pstmt.setString(3, password);
				pstmt.setString(4, gender);
				pstmt.setString(5, email);
				pstmt.executeUpdate();
				out.print("Trainer Registered sucesfully");
				RequestDispatcher rd=req.getRequestDispatcher("Between");
				rd.include(req, resp);
			}
			else {
				out.print("<h1 style=color:red>"+"password & confirm Password should be the same"+"</h1>");
				RequestDispatcher rd=req.getRequestDispatcher("signup");
				rd.include(req, resp);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(con!=null) {
					con.close();
				}
				if(pstmt!=null) {
					pstmt.close();
				}
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
