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
