package projects.service;




import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

import model.Project;
import model.dao.ProjectDao;
import projects.exception.DbException;

/*
 * this class handle the business layer of our application
 * it is our model layer
 * 
 * @author Paul Technology
 */
public class ProjectService {
	
	//creating new instance of a data access object
	private ProjectDao projectDao  = new ProjectDao();
	
	//method that calls the DAO class to insert a project row.
	 public Project addProject(Project p) {
		 
		 projectDao.save(p);
		 
		return p; 
     }

	
	
	//retrieve all project details
	public List<Project> fetchAllProjects() {
		
		return projectDao.getAll();

	}
	
	
	
	//get project by project Id
	public Project fetchProjectById(Integer iD) {
		/* 
		 * If a value is present, returns the value, 
		 * otherwise throws Exception.
		 * we use lambda function to handle the error
		 */
		try {
			return projectDao.getById(iD).orElseThrow(() -> new NoSuchElementException (""
					+ "Project with project ID= " + iD + " does not exist."));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new DbException(e);
		}
		
	}
	
	
	
	//delete project by Id
	public Project deleteProject(Project t) {
		
		projectDao.delete(t);

		return t;
	}
	
	
	//update a project
	public Project updateProject(Project t) {
		
		projectDao.update(t);
		
		return t;
	}
	
	
	//utils methods
	
	//grab project id
	public Project findByIdInternal(Integer ID) {
		
		return projectDao.findByIdInternal(ID);
		
	}
	
	
	
	//get each field of the entity
	
//	public String getEntityField(Object obj) {
//		return projectDao.getEachFieldAndValue(obj);
//		
//	}
	
	
//	public Project getEntityField(Integer iD) throws SQLException {
//		return projectDao.eachFieldAndValue(iD);
//		
//	}
	
	
	public String findByNameInternal(String str) {
		return projectDao.findByNameInternal( str);
	}
	
	//end utils methods




}
