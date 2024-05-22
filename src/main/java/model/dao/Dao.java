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
