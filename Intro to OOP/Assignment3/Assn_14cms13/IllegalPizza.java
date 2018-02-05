/**
 * Class to handle the exceptions thrown by the pizza class and the LineItem class.
 * @author curtisshewchuk
 *
 */
public class IllegalPizza extends Exception {
	public IllegalPizza(){
		    super("Illegal parameter value supplied to" + " object.");
 	}
	public IllegalPizza(String message){
		super(message);
	}


}
