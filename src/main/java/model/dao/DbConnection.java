package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import projects.exception.DbException;

/*
 * This class manages Db connection
 * 
 * @author Paul Technology
 */

public class DbConnection {
	
	//variable jdbc driver
	public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
			
	//password and username for db authentication
	public static final String Username = "projects";
	public static final String Password = "projects";
			
	//variable database
	public static final String $db = "projects";
			
	//variable db connection string. I concatenated the URI and the Db to build the whole uri
	public static final String DATABASE_URL = String.format("jdbc:mysql://localhost:3306/" + $db);
			
	//variable connection
	private static Connection conn = null;
			
	//variable statement
	public static Statement stmt = null;
			
			
	//method to handle Db connection
			
	@SuppressWarnings("deprecation")
	public static Connection connectDB() {
		try {
			//I loaded a driver file class in memory
			Class.forName(DRIVER).newInstance();
					
			//I established a connection using the Connection class object
			try {
				//After loading the driver, I established connections
				conn = DriverManager.getConnection(DATABASE_URL, Username, Password);
							
				//create statement
				stmt = conn.createStatement();
							
				//printing cnx status
				System.out.println("Connection to schema " + $db + " is successful.");
							
				} catch (SQLException e) {
					/*throw exception using our Exception class we created*/
					throw new DbException(e);
					}
					
				} catch(Exception ex) {
					//if any error, print it out
					System.out.println(ex);
				}
				
				//return connection
				return conn;
			}

}
