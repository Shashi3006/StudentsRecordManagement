package com.Drive;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Placement extends HttpServlet
{
	
	Connection con = null;
	PreparedStatement pstmt = null;
	Statement stmt = null;
	ResultSet res;
	String url = "jdbc:mysql://localhost:3306/jdbc_test";
	String un = "root";
	String passwd = "Shashi@3006";
	String query = "select * from students_details where Roll_no = (?)";
	
	
	public void init() throws ServletException
	 {
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver loaded");
			con = DriverManager.getConnection(url, un, passwd);
			System.out.println("CONNECTION ESTABLISHED");
			
		}
		catch (ClassNotFoundException | SQLException e) 
		{
			e.printStackTrace();
		}
	 }
	
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
			 

			String query2 = "select * from placementdrive where "+res.getInt(5)+" >= Java_cutOut and "+res.getInt(6)+" >= SQL_cutOut and "+res.getInt(4)+" >= Frontend_cutOut;";	
			stmt = con.createStatement();
			ResultSet res2 = stmt.executeQuery(query2);
			
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
					+ "                <h3>Placement Drives for Roll no.: "+res.getInt(2)+"</h3><br>\r\n"
					+ "        <table>\r\n"
					+ "            <tr>\r\n"
					+ "                <th>Organization</th>\r\n"
					+ "                <th>Package</th>\r\n"
					+ "                <th>Java_cutOut</th>\r\n"
					+ "                <th>SQL_cutOut</th>\r\n"
					+ "                <th>Frontend_cutOut</th>\r\n"
					+ "                <th>Location</th>\r\n"
					+ "                <th>PassOut</th>\r\n"
					+ "            </tr>\r\n");
			
			        
			       boolean foundResults2 = false;
			       while(res2.next()) {
			         foundResults2 = true;
                     out.println("<tr>\r\n"
                		+ "    <td>"+res2.getString(2)+"</td>\r\n"
                		+ "    <td>"+res2.getFloat(3)+"</td>\r\n"
                		+ "    <td>"+res2.getInt(4)+"</td>\r\n"
                		+ "    <td>"+res2.getInt(5)+"</td>\r\n"
                		+ "    <td>"+res2.getInt(6)+"</td>\r\n"
                		+ "    <td>"+res2.getString(7)+"</td>\r\n"
                		+ "    <td>"+res2.getInt(8)+"</td>\r\n"
                		+ "</tr>");
			         }
			        
					if(!foundResults2)
					{
						 out.println("<tr>\r\n"
						 		+ "    <td colspan=\"7\"><h4>No Placement drive found</h4></td>\r\n"
						 		+ "</tr>");
					}
			       out.println("\r\n"
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
						+ "    <h3>No record found</h3>\r\n"
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
			stmt.close();		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
	}
 }