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

public class UpdateServlet extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter out=resp.getWriter();
		String mobilenumber=req.getParameter("mobilenumber");
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String dburl="jdbc:mysql://localhost:3306/j2ee_db";
			con=DriverManager.getConnection(dburl,"root","root");
			
			String query="select * from student_details where mobilenumber=?";
			pstmt=con.prepareStatement(query);
			pstmt.setString(1, mobilenumber);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				String mb=rs.getString("mobilenumber");
				if(mb.equals(mobilenumber)) {
					RequestDispatcher rd=req.getRequestDispatcher("insert");
					rd.include(req, resp);
				}
			}
			else {
				out.print("INVALID NUMBER GO AND REGISTER");
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
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
