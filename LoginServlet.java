package com.QSP.Login;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter out=resp.getWriter();
		String username=req.getParameter("username");
		String password=req.getParameter("password");
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String dburl="jdbc:mysql://localhost:3306/j2ee_db";
			con=DriverManager.getConnection(dburl,"root","root");
			String query="select * from trainers_data where  username=?";
			pstmt=con.prepareStatement(query);
			pstmt.setString(1, username);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				String ps=rs.getString("password");
				if(ps.equals(password)) {
					RequestDispatcher rd=req.getRequestDispatcher("main1");
					rd.forward(req, resp);
				}
				else {
					out.print("<h1 style=color:red>"+"INVALID PASSWORD"+"</h1>");
					resp.sendRedirect("http://localhost:8080/Mock-App/login.html");
				}
			}
			else {
				out.print("INVALID USERNAME");
				resp.sendRedirect("http://localhost:8080/Mock-App/login.html");
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
				if(rs!=null) {
					rs.close();
				}
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
