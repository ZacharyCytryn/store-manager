package departmentStoreSimulator;

/**
 * EmptyListException for when a list is empty
 * 
 * @author Zachary Cytryn
 *
 */
public class EmptyListException extends Exception {
	/**
	 * Constructor
	 * 
	 * @param message
	 * Desired error message
	 */
	public EmptyListException(String message) {
		super(message);
	}
}
