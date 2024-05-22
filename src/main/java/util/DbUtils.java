package util;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import model.dao.DbConnection;
import projects.exception.DbException;


public class DbUtils {
	

	
	PreparedStatement ps;
	private ResultSet rs;
	private int numOfRows;
	private int numOfColums;
	private ResultSetMetaData rsMetaData;
	
	
	
	//data update and insert method
	public int Update_Insert_Query(String query, Object obj) throws SQLException{

		
		Connection getConnection = DbConnection.connectDB();
		
		try {
			ps = getConnection.prepareStatement(query);
			
			ps.executeUpdate();
			
			//get number of rows 
			numOfRows = ps.getMaxRows();
						
		} catch(Exception e) {
			//throw new DbException(e);
			e.getMessage();
			e.printStackTrace();
		}
		
		return numOfRows;
	}
	
	
	
	//data fetching method
	public ResultSet Select_Query(String query ) throws SQLException, ClassNotFoundException{
		
		
		Connection getConnection = DbConnection.connectDB();
		
		try {
			
			ps = getConnection.prepareStatement(query);
			ps.executeQuery();
			
			rs = ps.getResultSet();
			
			
		} catch(Exception e) {
			throw new DbException(e);
		}
		
		return rs;
	}
	

	
	
	
	
	//display all data
	public void findAll(String query) {
		
		Connection getConnection = DbConnection.connectDB();
		
		ps = null;
		rs = null;
		rsMetaData = null;
		numOfColums = 0;
		String rsColumn = null;
		Object value;
		
		try {
			ps = (PreparedStatement) getConnection.createStatement();
			ps.executeQuery(query);
			
			
			rsMetaData = rs.getMetaData();
			
			// Get the list of column count
			numOfColums = rsMetaData.getColumnCount();
			
			//Get number of rows
			numOfRows = rs.getRow();
			
			// iterate to print the current record
			for (int i = 1; i<= numOfColums; i++) {
				rsColumn = rsMetaData.getColumnName(i);
				
				//print column
				System.out.printf("%s\t", rsColumn);
				
				while(rs.next()) {
					for(int j = 1; j <= numOfRows; j++) {
						value = rs.getObject(i);
						
						//print columns values
						System.out.printf("%s\t", value);
					}
				}
			}
			
		} catch(Exception e) {
			throw new DbException(e);
		}
		
	}
	

}
