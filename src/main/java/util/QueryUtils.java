package util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import projects.exception.DbException;

/*
 * This class stores sql statements in a properties
 * It decouple the sql logic from the application business logic
 * This makes modification easier, by
   eliminating the need to search for database statements within application logic
 */
public class QueryUtils {
	
	
	
	//private variables for table
	
	private static final String CATEGORY_TABLE = "category";
	private static final String MATERIAL_TABLE = "material";
	private static final String PROJECT_TABLE = "projects";
	private static final String PROJECT_CATEGORY_TABLE = "project_category";
	private static final String STEP_TABLE = "step";
	
	//private variables for queries
	
	protected static String ADD_PROJECT;
	protected static String LIST_PROJECT;
	protected static String FETCH_PROJECT;
	protected static String FETCH_PROJECT_BY_ID;
	protected static String UPDATE_PROJECT;
	protected static String DELETE_PROJECT;
	
	protected static String FETCH_CATEGORY;
	protected static String FETCH_STEPS;
	protected static String FETCH_MATERIALS;
	
	
	/*
	 * M E T H O D S 
	 */
	
	//method to add new project
	public String insertProjectSql() {
		return ADD_PROJECT = "INSERT INTO " 
				+ PROJECT_TABLE + ""
				+ "(project_name, estimated_hours, actual_hours, difficulty, notes) "
				+ "VALUES "
				+ "(?, ?, ?, ?, ?)";
		
		//return ADD_PROJECT;
	}
	
	
	//method to fetch project
	public String selectProjectSql() {
		return FETCH_PROJECT = "SELECT * FROM "
				+ PROJECT_TABLE + ""
				+ " ORDER BY project_name";
		
		//return FETCH_PROJECT;
	}
	
	
	//method to fetch project by Id
	public String selectProjectByIdSql() {
		return FETCH_PROJECT_BY_ID = "SELECT * FROM " 
				+ PROJECT_TABLE + ""
				+ " WHERE project_id = ?";
		
		//return FETCH_PROJECT_BY_ID;
	}
	
	
	//method to update project by project Id
	public String updateProjectSql() {
		return UPDATE_PROJECT = "UPDATE "
				+ PROJECT_TABLE + " SET "
				+ "project_name = ?, "
				+ "estimated_hours = ?, "
				+ "actual_hours = ?, "
				+ "difficulty = ?, "
				+ "notes = ? "
				+ "WHERE project_id = ?";
		
		//return UPDATE_PROJECT;
	}
	
	
	//method to delete a project
	public String deleteProjectSql() {
		return DELETE_PROJECT = "DELETE FROM "
				+ PROJECT_TABLE + ""
				+ " WHERE project_id = ?";
				
		//return DELETE_PROJECT;
	}
	
	
	//method to fetch project category by Id
	public String selectCategorySql() {
		return FETCH_CATEGORY = "SELECT c.* FROM "
				+ CATEGORY_TABLE + " c "
				+ "JOIN " + PROJECT_CATEGORY_TABLE + " pc USING (category_id)"
				+ " WHERE project_id = ?";
		
		//return FETCH_CATEGORY;
	}
	
	
	
	//method to fetch steps by project Id
	public String selectStepsSql() {
		return FETCH_STEPS = "SELECT * FROM "
				+ STEP_TABLE + ""
				+ " WHERE project_id = ?";
		
		//return FETCH_STEPS;
	}
	
	
	
	//method to fetch materials by project Id
	public String selectMaterialsSql() {
		return FETCH_MATERIALS = "SELECT * FROM "
				+ MATERIAL_TABLE + ""
				+ " WHERE project_id = ?";
		
		//return FETCH_MATERIALS;
	}
	
	

	
	

		


}
