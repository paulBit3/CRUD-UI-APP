# Java-Week11-Assignment--PromineoTech-Database--MySQL-Final-Project 


Using MVC design pattern approach to create a CRUD application that demonstrates we perform CRUD operation on a MySQL Database using JAVA - JDBC, and also how to read SQL queries in external JAVA class. I separate SQL queries from my java to make my code looks following DRY approach, and call them in my Java code when need them.

![Screenshot 2024-04-14 054034](https://github.com/paulBit3/Java-Week10-Assignment--PromineoTech-DB-Development--MySQL-JDBC/assets/43505777/9fdbf446-5454-4d4e-8279-8cf0baa2eefa)

![Screenshot 2024-04-24 180017](https://github.com/paulBit3/Java-Week10-Assignment--PromineoTech-DB-Development--MySQL-JDBC/assets/43505777/67124124-6fdb-44f1-a3ee-fccdc76e8ff7)

![Screenshot 2024-05-06 181633](https://github.com/paulBit3/Java-Week10-Assignment--PromineoTech-DB-Development--MySQL-JDBC/assets/43505777/d8d30da0-fda6-41c4-a618-0b1c8ef29bd3)



Once download or cloned the repo, look at the src folder. The database and sample data are in the main/resources folder
Here is a sample.
You also will see DbUtils and QueryUtils class files. DbUtils contains logic to interact with the Database and QueryUtils is where I wrote code to defined all my SQL queries.

--------------

data Sample

```SQL
use projects;

--		 --
--  project data --
--               --



-- Insert into the category table
INSERT INTO category (category_name) VALUES ('House');
INSERT INTO category (category_name) VALUES ('Software Development');
INSERT INTO category (category_name) VALUES ('Rideshare');
INSERT INTO category (category_name) VALUES ('Study');
INSERT INTO category (category_name) VALUES ('Car');
INSERT INTO category (category_name) VALUES ('Social Life');
INSERT INTO category (category_name) VALUES ('Education');



-- Insert into the step table
INSERT INTO step (project_id, step_text, step_order) VALUES (1, 'Remove old sink, and use glue to tie it against the wall.', 1);
INSERT INTO step (project_id, step_text, step_order) VALUES (2, 'Move sofa to the left, and put coffee table in the middle.', 2);
INSERT INTO step (project_id, step_text, step_order) VALUES (6, 'Remove bathroom trash bag and replace it with a new one.', 3);
INSERT INTO step (project_id, step_text, step_order) VALUES (7, 'Grab a Babrbecue grille.', 1);
INSERT INTO step (project_id, step_text, step_order) VALUES (7, 'Put your meat on the grille, make sure fire is good.', 2);

INSERT INTO step (project_id, step_text, step_order) VALUES (9, 'Download the Uber driver app, if you are a driver, and create an account', 1);
INSERT INTO step (project_id, step_text, step_order) VALUES (9, 'Download the Uber app, if you are a rider, and create an account', 1);
INSERT INTO step (project_id, step_text, step_order) VALUES (9, 'Create a username and a password, and enter your email and phone number to receive a temporary code and newsletter', 2);

INSERT INTO step (project_id, step_text, step_order) VALUES (13, 'You could buy a coding book and self-taught learn yourself how to code', 1);
INSERT INTO step (project_id, step_text, step_order) VALUES (13, 'You could earn a 4 year Bachelor degree in Computer Science or Infromation Technology or Engineering, where they will teach you how to code', 2);
INSERT INTO step (project_id, step_text, step_order) VALUES (12, 'Learn Java, JavaScript, Python, C#, Swift, Goland, SQL, Html,Css', 1);



-- Insert into the material table

INSERT INTO material (project_id, material_name, num_required, cost) VALUES (1, 'new sink', 3, 23.89);
INSERT INTO material (project_id, material_name, num_required, cost) VALUES (1, 'glue', 3, 3.89);
INSERT INTO material (project_id, material_name, num_required, cost) VALUES (1, 'harmer', 1, 2.99);

INSERT INTO material (project_id, material_name, num_required, cost) VALUES (9, 'a car', 2, 2000.00);
INSERT INTO material (project_id, material_name, num_required, cost) VALUES (9, 'Insurance', 1, 200.00);
INSERT INTO material (project_id, material_name, num_required, cost) VALUES (9, 'driver license', 1, null);

INSERT INTO material (project_id, material_name, num_required, cost) VALUES (12, 'computer and Internet', 5, 400.00);
INSERT INTO material (project_id, material_name, num_required, cost) VALUES (12, 'table', 2, 100.00);
INSERT INTO material (project_id, material_name, num_required, cost) VALUES (13, 'computer', 4, 300);
INSERT INTO material (project_id, material_name, num_required, cost) VALUES (13, 'chair', 1, 500);

INSERT INTO material (project_id, material_name, num_required, cost) VALUES (7, 'grille', 2, 500.00);
INSERT INTO material (project_id, material_name, num_required, cost) VALUES (7, 'characol', 5, 50.00);
INSERT INTO material (project_id, material_name, num_required, cost) VALUES (7, 'barbecue sauce', 1, 2.99);
INSERT INTO material (project_id, material_name, num_required, cost) VALUES (13, 'onion and tomatoes', 6, 10.00);

INSERT INTO material (project_id, material_name, num_required, cost) VALUES (5, 'dish washer', 1, 199.00);
INSERT INTO material (project_id, material_name, num_required, cost) VALUES (5, 'fridge', 2, 399.40);

-- Insert into the join  project_category table
INSERT INTO project_category (project_id, category_id) VALUES (1, 1);
INSERT INTO project_category (project_id, category_id) VALUES (2, 1);
INSERT INTO project_category (project_id, category_id) VALUES (3, 1);
INSERT INTO project_category (project_id, category_id) VALUES (5, 1);
INSERT INTO project_category (project_id, category_id) VALUES (6, 1);
INSERT INTO project_category (project_id, category_id) VALUES (10, 1);

INSERT INTO project_category (project_id, category_id) VALUES (7, 6);
INSERT INTO project_category (project_id, category_id) VALUES (12, 2);
INSERT INTO project_category (project_id, category_id) VALUES (13, 2);


INSERT INTO project_category (project_id, category_id) VALUES (11, 7);
INSERT INTO project_category (project_id, category_id) VALUES (8, 5);
INSERT INTO project_category (project_id, category_id) VALUES (9, 3);
```

-----------
This Query utils class stores sql statements. this approach decouples the sql logic from the application business logic


```Java

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


Db Utils class defines the sql logic to interact with the application business logic
------------



```Java
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

```

-----------------


The view class handles our application menu.

```Java
package view;


import controller.Controller;
import model.dao.DbConnection;

//import projects.dao.DbConnection;

/*
 * 
 * This class handles our application menu.
 * A list of menu options will be displaced
 * for the user to interact with our App.
 * 
 * This class extends on our Controller class
 * that handle code separately
 * 
 * @author Paul Technology
 */

public class ProjectsApp {

	public static void main(String[] args) {
		// creating a new connection
		DbConnection.connectDB();
			   
	  //new instance of controller
		Controller controller = new Controller();
		
		//calling our process method
		controller.processUserSelections();


	}

}

```

-------------------

Dao interface defines an abstract API that performs CRUD operations on objects of type T.

```Java


package model.dao;



import java.sql.SQLException;
import java.util.List;
import java.util.Optional;



/*
 * Here is our DAO API
 * 
 * This Dao interface defines an abstract API 
 * that performs CRUD operations on objects of type T.
 */

public interface Dao<T> {
	Optional<T> getById(Integer id) throws SQLException;
    
    List<T> getAll();
    
    void save(T t);
    
    void update(T t);
    
    void delete(T t);

}

```

---------------

Project dao class perform CRUD operation. It define a project-specific implementation of the Dao interface.
The projectDao class work like our Model to handle data and business rules



```Java

package model.dao;




import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;



import model.Category;
import model.Material;
import model.Project;
import model.Step;
import projects.exception.DbException;
import util.DbUtils;
import util.QueryUtils;

/*
 * This class perform CRUD operation on our project.
 * It define a project-specific implementation of the Dao interface.
 * 
 * all Db requests will be handled by Properties class
 * 
 * the projectDao class work like our Model to handle data and business rules
 * I choose this tips to keep my code clean and dry
 * 
 * @author Paul Technology
 */

public class ProjectDao implements Dao<Project>{
	//connection
	private Connection con = DbConnection.connectDB();
	
	//initialize statement object
	private PreparedStatement ps = null ;
	
	//initialize result set
	private ResultSet rs = null;
	
	//initialize project list
	List<Project> p = new ArrayList<>();
	
	private int numOfRows;

	
	DbUtils db = new DbUtils();
	QueryUtils q = new QueryUtils();
	
	
	
			/* ------------------ */
			/*                   */
			/*   M E T H O D S  */
			/*                 */
			/* ---------------*/
	
	/*
	 * Starting project methods
	 */
	
	//method which fetch project by Id
	
	@Override
	public Optional<Project> getById(Integer id) throws SQLException {
		
		// invoking list project method

		String query  = q.selectProjectByIdSql();
		
		try {
			 ps = con.prepareStatement(query);
			//set values
			ps.setInt(1, id);
			rs = ps.executeQuery();

			//do something with result set
			if(rs.next()) {
				//create an Optional object
				//Optional<Project> oString = Optional.of(createProject(rs));
				Optional<Project> oString = Optional.of(returnProjectById(rs, id));
				returnOneToManyToOne(con, id);
				//use lambda expression to check if values, print it out
				oString.ifPresent(c -> System.out.println(c));

				return oString;
				

			} else {
				//if not values, return empty
				return Optional.empty();
			}
			
		} catch(Exception e) {
			throw new DbException(e);
		} 


	}
	
	
	
	
	//method which return all project info

	@Override
	public List<Project> getAll(){
		
		//invoking list project method

		String query = q.selectProjectSql();
		try {
			rs = db.Select_Query(query);
			
			//do something with result set
			while (rs.next()) {
				//invoking create project method and add project
				p.add(createProject(rs));
		 	}

		} catch (SQLException | ClassNotFoundException err) {
			err.getMessage();
			//err.printStackTrace();
		}

		return p;

	}

	
	
	
    //method which populates projects table
	
	
	@Override
	public void save(Project t) {

		String query = q.insertProjectSql() ;

		try {
			//invoking inser projet method
			Insert_Project(query, t);
		} catch (SQLException err) {
			
			// handle SQL exception
			err.getMessage();
			//err.printStackTrace();
		}
	}


	
	
	//method which update a project
	@Override
	public void update(Project t) {
		
		String query = q.updateProjectSql();
		
		try {
			
			Update_Insert_Project(query, t);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			throw new DbException();
		}
		
	}
	
	

	
	

	
	//method which delete a project

	@Override
	public void delete(Project t) {
		// TODO Auto-generated method stub
		
		// invoking delete project method
		deleteProject(q.deleteProjectSql(), t);
		
	}
	
	
	

	
	//This method return a new project
	private Project createProject(ResultSet rs) throws SQLException {
		try {
			
			return new Project(rs.getInt("project_id"),
					rs.getString("project_name"),
					rs.getBigDecimal("estimated_hours"),
					rs.getBigDecimal("actual_hours"),
					rs.getInt("difficulty"),
					rs.getString("notes")
					);
		} catch(Exception e) {
			throw new DbException(e);
		} 
	}
	
	
	//This method take Id return the searched project with the matched Id
	private Project returnProjectById(ResultSet rs, int id) throws SQLException {
		
		//iterate project through project id and return
		for(Project p: p) {
			try {
				//if ID matches project Id, return project info
				if(id == p.getProjectId()) {
					//set project to appropriate values
					p.setProjectId(rs.getInt("project_id"));
					p.setProjectName(rs.getString("project_name"));
					p.setEstimatedHours(rs.getBigDecimal("estimated_hours"));
					p.setActualHours(rs.getBigDecimal("actual_hours"));
					p.setDifficulty(rs.getInt("difficulty"));
					p.setNotes(rs.getString("notes"));

					return p;
				}

			} catch(Exception e) {
				throw new DbException(e);
			} 

			
		}
		return null;

	}
	
	
	/*
	 * This method take a sql delete statment 
	 * and return true if a project is deleted , or false if not
	 */
	private boolean deleteProject(String query, Project t) {
		try {
			ps = con.prepareStatement(query);
			ps.setInt(1, t.getProjectId());
			ps.executeUpdate();
			System.out.println(t.getProjectId());
			return true;
			
		} catch(Exception e) {
			throw new DbException("Project with ID=" + t.getProjectId() + " does not exist.");
		}

		
	}
	
	

	
	
	/*
	 * End project methods
	 */
	
	
	//fetch Category methods
	//return categories for projects
	private List<Category> fetchCategoriesForProject(Connection con, 
			Integer id) throws SQLException {
		
		//initialize category
		List<Category> c = new LinkedList<>();
		
		//invoking fetch category method
		String query = q.selectCategorySql();
		
		try {
			ps = con.prepareStatement(query);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			
			//invoking select query metho from dbUtil
			//rs = db.Select_Query(query);
			while(rs.next()) {
				//invoking createCategory method to add category
				c.add(createCategory(rs, id));
			}
			
		} catch(Exception e) {
			throw new DbException(e);
		}

		
		return c;
	}
	
	
	
	//This method return a new category
	private Category createCategory(ResultSet rs, Integer id) throws SQLException {
		Category c = null;
		
		//new category instance
		c = new Category();
		
		//iterate project
		for(Project p: p) {
			try {
				if(id == p.getProjectId()) {
					//set category
					c.setCategoryId(rs.getInt("category_id"));
					c.setCategoryName(rs.getString("category_name"));
				}
			} catch(Exception e) {
				throw new DbException(e);
			}

		}	
		
		return c;
	}
	
	
	//End category methods
	

	
	
	
	
	//Steps methods
	

	//return step for project
	private List<Step> fetchStepsForProject(Connection con,
			Integer id) throws SQLException{
		
		//initialize step
		List<Step> s = new LinkedList<>();
		
		//invoking fetch step method
		String query = q.selectStepsSql();
		
		try {
			ps = con.prepareStatement(query);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			
			//invoking select query metho from dbUtil
			//rs = db.Select_Query(query);
			while(rs.next()){
				//invoking createStep method to add step
				s.add(createStep(rs, id));
				//System.out.println(s);
			}
			
		} catch(Exception e) {
			throw new DbException(e);
		}

		
		return s;
	}
	
	

	//This method return a new category
	private Step createStep(ResultSet rs, Integer id) throws SQLException {
		Step s = null;
		
		//new step instance
		s = new Step();
		
		//iterate project
		for(Project p: p) {
			try {
				if(id == p.getProjectId()) {
					//set step
					s.setStepId(rs.getInt("step_id"));
					s.setProjectId(rs.getInt("project_id"));
					s.setStepText(rs.getString("step_text"));
					s.setStepOrder(rs.getInt("step_order"));

				}
			}catch(Exception e) {
				throw new DbException(e);
			}

		}
		
		return s;
	}
	
	//End steps methods
	
	
	
	//Material methods
	
	
	//return materials for project
	private List<Material> fetchMaterialsForProject(Connection con, 
			Integer id) throws SQLException{
		
		//initialize material
		List<Material> m = new LinkedList<>();
		
		//invoking fetch material method
		String query = q.selectMaterialsSql();
		
		try {
			ps = con.prepareStatement(query);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			
			//invoking select query metho from dbUtil
			//rs = db.Select_Query(query);
			while(rs.next()) {
				//invoking createMaterial method to add material
				m.add(createMaterial(rs, id));
			}
		} catch(Exception e) {
			throw new DbException(e);
		}

		
		return m;
	}
	
	
	
	//This method return a new category
	private Material createMaterial(ResultSet rs, Integer id) throws SQLException {
		Material m = null;
		
		//new material instance
		m = new Material();
		
		//iterate project
		for(Project p: p) {
			try {
				if(id == p.getProjectId()) {
					//set material
					m.setMaterialId(rs.getInt("material_id"));
					m.setProjectId(rs.getInt("project_id"));
					m.setMaterialName(rs.getString("material_name"));
					m.setNumRequired(rs.getInt("num_required"));
					m.setCost(rs.getBigDecimal("cost"));
				}
			} catch(Exception e) {
				throw new DbException(e);
			}

		}
		return m;
		
	}
	
	//end Material methods
	

	
	
	//============= Utils methods =============
	
	
	/*
	 * this method deal with a One To Many and Many To One relationship
	 * if object is not null, It fetch data in the join table and return it
	 * or return nothing if null
	 */
	private Project returnOneToManyToOne ( Connection con, Integer id) {
		
		//iterate project object
		for(Project p: p) {
			if(id == p.getProjectId()) {
				if(Objects.nonNull(p)) {
					try {
						p.getMaterials().addAll(fetchMaterialsForProject(con, id));
						//System.out.println(p);
						p.getSteps().addAll(fetchStepsForProject(con, id));
						//System.out.println(p);
						p.getCategories().addAll(fetchCategoriesForProject(con, id));
						//System.out.println(p);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						throw new DbException(e);
					}

				}
				return p;
			}
		}
		return null;
	}
	
	
	
	//utils method that will grab project id
	public Project findByIdInternal(Integer Id) {
		
		for (Project p : p) {
			if (Id == p.getProjectId()) {
				return p;
			}
			//getEachFieldAndValue();
		}
		return null;
	}
	
	
	
	//utils method that will grab project id
	public String findByNameInternal(String str) {
		
		for (Project p : p) {
			if (str == p.getProjectName()) {
				return str;
				
			}
				
		}
			return null;
	}

	
	
	//method to check new value
	public boolean doesNewValue(Project newValue) {
		
		return false;
	}
	
	
	
	
	//project data update and insert method
	public int Update_Insert_Project(String query, Project t) throws SQLException {
			
			try {
				
				ps = con.prepareStatement(query);
				
				setValues(ps, t);
				
				numOfRows = ps.executeUpdate();
				
				if(numOfRows > 1) {
					System.out.println("Project" + t.getProjectId() + "successfully updated!");
				}
							
			} catch(Exception e) {
				//throw new DbException(e);
				e.printStackTrace();
			}

			return numOfRows;
   }


	//project data update and insert method
	public int Insert_Project(String query, Project t) throws SQLException {
			
			try {
				
				ps = con.prepareStatement(query);
				
				setSaveValues(ps, t);
				
				ps.executeUpdate();
				
				//get row id 
				numOfRows = ps.getMaxRows();
							
			} catch(Exception e) {
				//throw new DbException(e);
				e.printStackTrace();
			}

			return numOfRows;
   }
	
	
	/*method to set values. 
	*it takes a prepared statement and an object and return set values
	*
	*/
	private String setValues(final PreparedStatement ps, Project t) throws SQLException {
		try {
			
			
			ps.setString(1, t.getProjectName());
			ps.setBigDecimal(2, t.getEstimatedHours());
			ps.setBigDecimal(3,t.getActualHours());
			ps.setInt(4, t.getDifficulty());
			ps.setString(5, t.getNotes());
			ps.setInt(6, t.getProjectId());


		} catch (Exception e) {
			throw new DbException(e);
		} 

		return null;
		
	}
	
	
	/*method to set values. 
	*it takes a prepared statement and an object and return set values
	*
	*/
	private String setSaveValues(final PreparedStatement ps, Project t) throws SQLException {
		try {
			
			
			ps.setString(1, t.getProjectName());
			ps.setBigDecimal(2, t.getEstimatedHours());
			ps.setBigDecimal(3,t.getActualHours());
			ps.setInt(4, t.getDifficulty());
			ps.setString(5, t.getNotes());
			


		} catch (Exception e) {
			throw new DbException(e);
		} 

		return null;
		
	}
	

	// method to close connection/resulset/statement
	public void close() throws SQLException{
		
	     try {
	    	 if (rs != null) {
		         rs .close();
		     }
		     if (ps !=null) {
		         ps.close();
		     }
		     if (con != null) {
	             con.close();
	         }
	     } catch (Exception e) {
	    	 throw new DbException("\"Error closing the connection with the database...\"");
	     }
	 }

	

	
	

	
	
	//============= End utils methods =============
	

}


```
---------------




Model class that defines Project table in the database.


```Java
package model;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;


/*
 * this class defines Project table in the database
 * it is our model layer
 */

public class Project {
	
	  private Integer projectId;
	  private String projectName;
	  private BigDecimal estimatedHours;
	  private BigDecimal actualHours;
	  private Integer difficulty;
	  private String notes;

	  private List<Material> materials = new LinkedList<>();
	  private List<Step> steps = new LinkedList<>();
	  private List<Category> categories = new LinkedList<>();
      


	//constructor
	public Project(int project_id, String project_name, BigDecimal estimated_hours, BigDecimal actual_hours, int difficulty, String notes) {
		this.projectId = project_id;
		this.projectName = project_name;
		this.estimatedHours = estimated_hours;
		this.actualHours = actual_hours;
		this.difficulty = difficulty;
		this.notes = notes;
	}

	public Project() {
		// TODO Auto-generated constructor stub
	}

	public Integer getProjectId() {
	    return projectId;
	  }

	  public void setProjectId(Integer projectId) {
	    this.projectId = projectId;
	  }

	  public String getProjectName() {
	    return projectName;
	  }

	  public void setProjectName(String projectName) {
	    this.projectName = projectName;
	  }

	  public BigDecimal getEstimatedHours() {
	    return estimatedHours;
	  }

	  public void setEstimatedHours(BigDecimal estimatedHours) {
	    this.estimatedHours = estimatedHours;
	  }

	  public BigDecimal getActualHours() {
	    return actualHours;
	  }

	  public void setActualHours(BigDecimal actualHours) {
	    this.actualHours = actualHours;
	  }

	  public Integer getDifficulty() {
	    return difficulty;
	  }

	  public void setDifficulty(Integer difficulty) {
	    this.difficulty = difficulty;
	  }

	  public String getNotes() {
	    return notes;
	  }

	  public void setNotes(String notes) {
	    this.notes = notes;
	  }

	  public List<Material> getMaterials() {
	    return materials;
	  }

	  public List<Step> getSteps() {
	    return steps;
	  }

	  public List<Category> getCategories() {
	    return categories;
	  }

	  @Override
	  public String toString() {
	    String result = "";
	    
	    result += "\n   ID=" + projectId;
	    result += "\n   name=" + projectName;
	    result += "\n   estimatedHours=" + estimatedHours;
	    result += "\n   actualHours=" + actualHours;
	    result += "\n   difficulty=" + difficulty;
	    result += "\n   notes=" + notes;
	    
	    result += "\n   Materials:";
	    
	    for(Material material : materials) {
	      result += "\n      " + material;
	    }
	    
	    result += "\n   Steps:";
	    
	    for(Step step : steps) {
	      result += "\n      " + step;
	    }
	    
	    result += "\n   Categories:";
	    
	    for(Category category : categories) {
	      result += "\n      " + category;
	    }
	    
	    return result;
	  }

}

```
