package mulCal.util;

//Raised when a mapping (dictionary) key is not found in the set of existing keys.
public class KeyException extends Exception {

    private static final long serialVersionUID = 1L;
    
	public KeyException() {
	         super();           
	 }

	 public KeyException(String message) {
	         super(message);  
	 }
}
