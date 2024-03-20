package com.College;

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

public class StudentsResult extends HttpServlet
{
	Connection con = null;
	PreparedStatement pstmt = null;
	String url = "jdbc:mysql://localhost:3306/jdbc_test";
	String un = "root";
	String passwd = "Shashi@3006";
	String query = "insert into students_details values(?, ?, ?, ?, ?, ?)";
	
	@Override
	public void init() throws ServletException
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver loaded");
			con = DriverManager.getConnection(url, un, passwd);
			System.out.println("CONNECTION ESTABLISHED");
		} 
		catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	  {
		try {
			pstmt = con.prepareStatement(query);
			
			String name = req.getParameter("S_name");
			
			String StringrollNo = req.getParameter("rollNo");
			int rollNo = Integer.parseInt(StringrollNo);
			
			String dob = req.getParameter("dob");
			
			String Strngfrend = req.getParameter("frend");
			int frend = Integer.parseInt(Strngfrend);
			
			String Strngjava = req.getParameter("java");
			int java = Integer.parseInt(Strngjava);
			
			String Strngsql = req.getParameter("sql");
			int sql = Integer.parseInt(Strngsql);
			
			System.out.println(name+" "+rollNo+" "+dob+" "+frend+" "+java+" "+sql);
		
			pstmt.setString(1, name);
			pstmt.setInt(2, rollNo);
			pstmt.setString(3, dob);
			pstmt.setInt(4, frend);
			pstmt.setInt(5, java);
			pstmt.setInt(6, sql);
			pstmt.executeUpdate();
			
			
			PrintWriter out = resp.getWriter();
			out.println("Records are Successfully submitted");
			} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	  }
	
	@Override
	public void destroy()
	{
		try {
			con.close();
			pstmt.close();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
	}
	
}