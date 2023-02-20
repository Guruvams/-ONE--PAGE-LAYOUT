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

public class AddStudentServlet extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter out=resp.getWriter();
		String name=req.getParameter("name");
		String mobilenumber=req.getParameter("mobilenumber");
		String email=req.getParameter("email");
		String per=req.getParameter("percentage");
		Connection con=null;
		PreparedStatement pstmt=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String dburl="jdbc:mysql://localhost:3306/j2ee_db";
			con=DriverManager.getConnection(dburl,"root","root");
			
			String query="insert into student_details values(?,?,?)";
			pstmt=con.prepareStatement(query);
			pstmt.setString(1, name);
			pstmt.setString(2, mobilenumber);
			pstmt.setString(3, email);
			pstmt.executeUpdate();
			Connection con1=null;
			PreparedStatement pstmt1=null;
			try {
				Class.forName("com.mysql.jdbc.Driver");
				String dburl1="jdbc:mysql://localhost:3306/j2ee_db";
				
				con1=DriverManager.getConnection(dburl1,"root","root");
				String query1="insert into students_mr values(?,?)";
				pstmt1=con.prepareStatement(query1);
				pstmt1.setString(1, mobilenumber);
				pstmt1.setString(2, per+"%");
				pstmt1.executeUpdate();
				out.print("Student Added");
			}
			catch(Exception e){
				e.printStackTrace();
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
				con.close();
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
