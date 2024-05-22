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
