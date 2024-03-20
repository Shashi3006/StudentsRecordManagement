package com.College;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ResultsOut extends HttpServlet
{
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet res;
	String url = "jdbc:mysql://localhost:3306/jdbc_test";
	String un = "root";
	String passwd = "Shashi@3006";
	String query = "select * from students_details where Roll_no = (?)";
	
	@Override
	public void init() throws ServletException
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
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
			
			String StringrollNo = req.getParameter("rollNo");
			int rollNo = Integer.parseInt(StringrollNo);
		
			pstmt.setInt(1, rollNo);
			
			
			res = pstmt.executeQuery();
			resp.setContentType("text/html");
			PrintWriter out = resp.getWriter();
			
			boolean foundResult = false;
			if(res.next())
			{
				foundResult = true;
			
			out.println("\r\n"
					+ "<html>\r\n"
					+ "<head>\r\n"
					+ "<meta charset=\"UTF-8\">\r\n"
					+ "<title>Results</title>\r\n"
					+ "<link type=\"text/css\" href=\"style.css\" rel=\"stylesheet\" />\r\n"
					+ "<style>\r\n"
					+ "table {\r\n"
					+ "    width: 80%;\r\n"
					+ "    border-collapse: collapse; \r\n"
					+ "}\r\n"
					+ "th {\r\n"
					+ "    background-color: #f2f2f2;\r\n"
					+ "    text-align: center;\r\n"
					+ "    padding: 18px 0;\r\n"
					+ "}\r\n"
					+ "td {\r\n"
					+ "    border: 1px solid #ddd;\r\n"
					+ "    text-align: center;\r\n"
					+ "    padding: 12px;\r\n"
					+ "}\r\n"
					+ "tr {\r\n"
					+ "    background-color: #93e07f;\r\n"
					+ "}\r\n"
					+ "</style>\r\n"
					+ "</head>\r\n"
					+ "<body>\r\n"
					+ "       <div class=\"container\">\r\n"
					+ "                <h3>Roll no.: "+res.getInt(2)+"</h3><br>\r\n"
					+ "        <table>\r\n"
					+ "            <tr>\r\n"
					+ "                <th>Name</th>\r\n"
					+ "                <th>Roll No</th>\r\n"
					+ "                <th>Date of Birth</th>\r\n"
					+ "                <th>Frontend</th>\r\n"
					+ "                <th>Java</th>\r\n"
					+ "                <th>SQL</th>\r\n"
					+ "            </tr>\r\n"
					+ "            <tr>\r\n"
					+ "                <td>"+res.getString(1)+"</td>\r\n"
					+ "                <td>"+res.getInt(2)+"</td>\r\n"
					+ "                <td>"+res.getString(3)+"</td>\r\n"
					+ "                <td>"+res.getInt(4)+"</td>\r\n"
					+ "                <td>"+res.getInt(5)+"</td>\r\n"
					+ "                <td>"+res.getInt(6)+"</td>\r\n"
					+ "            </tr>\r\n"
					+ "        </table>\r\n"
					+ "    </div>\r\n"
					+ "</body>\r\n"
					+ "</html>");
			}
			else {
				out.println("<!DOCTYPE html>\r\n"
						+ "<html lang=\"en\">\r\n"
						+ "<head>\r\n"
						+ "    <meta charset=\"UTF-8\">\r\n"
						+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
						+ "    <title>No record found</title>\r\n"
						+ "    <link type=\"text/css\" href=\"style.css\" rel=\"stylesheet\" />\r\n"
						+ "</head>\r\n"
						+ "<body>\r\n"
						+ "   <div class=\"container\">\r\n"
						+ "    <h3>No data found</h3>\r\n"
						+ "   </div> \r\n"
						+ "</body>\r\n"
						+ "</html>");
			}
	
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