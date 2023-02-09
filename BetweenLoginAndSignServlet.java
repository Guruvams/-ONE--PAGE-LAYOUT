package com.QSP.Login;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BetweenLoginAndSignServlet extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter out=resp.getWriter();
		String username=req.getParameter("username");
		String password=req.getParameter("password");
		Connection con=null;
		PreparedStatement pstmt=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String dburl="jdbc:mysql://localhost:3306/j2ee_db";
			con=DriverManager.getConnection(dburl,"root","root");
			String query="insert into trainers_data values(?,?)";
			pstmt=con.prepareStatement(query);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			pstmt.executeUpdate();
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
