
public class IllegalFastener extends Exception {

	public IllegalFastener(){
	    super("Illegal parameter value supplied to" + " object.");
	}
	public IllegalFastener(String message){
	super(message);
	}
}
