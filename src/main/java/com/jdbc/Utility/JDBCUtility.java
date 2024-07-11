package com.jdbc.Utility;

import java.sql.*;

public class JDBCUtility {

	static {
		
		//load and register the drivers
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
				}
				catch (ClassNotFoundException e) {
					
					e.printStackTrace();
				}
				System.out.println("Drivers are loaded");
	}
	
	public static Connection getDBConnection() throws SQLException {
		
		//establish connection
				String url= "jdbc:mysql://localhost:3306/mySql_Learning" ;
				String userName= "root";
				String password= "BeStrong23!";
				
				System.out.println("Connection has been established");
				return DriverManager.getConnection(url, userName, password);
				
	}
	
	public static void close(Connection connect, Statement stmnt, ResultSet rs) throws SQLException {
		
		if(connect != null)	
			connect.close();
		if(stmnt != null)
			stmnt.close();
		if(rs!= null)
			rs.close();
	}
}
