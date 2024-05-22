package projects.exception;


/*
 * This class handle Db error or any runtime error..and display to user
 */

@SuppressWarnings("serial")
public class DbException extends RuntimeException {
	public DbException() {
	}

	public DbException(String message) {
		super(message);
	}

	public DbException(Throwable cause) {
		super(cause);
	}

	public DbException(String message, Throwable cause) {
		super(message, cause);
	}

}
