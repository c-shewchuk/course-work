
public class IllegalGraph extends Exception {

	public IllegalGraph(){
	    super("Illegal parameter value supplied to" + " object.");
	}
	public IllegalGraph(String message){
	super(message);
	}
}
