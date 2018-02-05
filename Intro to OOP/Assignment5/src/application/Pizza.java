package application;
import java.io.Serializable;
/**
 * Class to store the information of a "boring" pizza.
 * <p>
 * A pizza has the attributes for size, amount of chesse, hamd and pepperoni, 
 * as well as cost.
 * @author curtisshewchuk
 * Version 1.0
 */
public class Pizza implements Serializable {
	private static final long serialVersionUID = -4389354322275679120L;
	private String size;
	private int cheese = 1;
	private int ham;
	private int pepperoni = 1;
	private double costPizza;
	
	/**
	 * Four paramter constructor for the object
	 * @param sz: the size of pizza, either small, medium or large.
	 * @param chs: the amount of chesse on the pizza.
	 * @param hm: the amount of ham on the pizza.
	 * @param pepper: the amount of pepperoni on the pizza.
	 * @throws IllegalPizza when a @param sz is null or when any of setter methods throw IllegalPizza.
	 * See the setter methods for more information on why the exception is thrown.
	 */
	public Pizza(String sz, int chs, int hm, int pepper) throws IllegalPizza {
		setSize(sz);
		setCheese(chs);
		setMeat(hm,pepper);
		costOfPizza();
	}// end 4 parameter constructor
	/**
	 * One parameter constructor for the pizza.
	 * Defaults @param cheese to 1 and @param pepperoni to 1.
	 * @param size: the size of the pizza.
	 * @throws IllegalPizza when the setter methods throw IllegalPizza.
	 */
	public Pizza(String size) throws IllegalPizza{
		setSize(size);
		cheese = 1;
		pepperoni = 1;
		ham = 0;
		costOfPizza();
	} // end 1 parameter constructor
	private void setSize(String sz) throws IllegalPizza{
		if(sz == null)
			throw new IllegalPizza("NULL string inputted!");
		if(sz.equalsIgnoreCase("small") || sz.equalsIgnoreCase(("medium")) || sz.equalsIgnoreCase("large"))
			size = sz;
		else
			throw new IllegalPizza("Not a valid pizza size! Only small, medium and large sized pizzas.");
	}// end setSize
	//
	private void setCheese(int size) throws IllegalPizza{
		if(size <= 3 && size >=1)
			cheese = size;
		else
			throw new IllegalPizza("Illegal ammount of cheese" + size);
	}
	// method to set the meat attributes and to determine if the attributes supplied are legal inputs. 
	private void setMeat(int hm, int pepper) throws IllegalPizza{
		if((hm + pepper) > 3)
			throw new IllegalPizza("Too much meat on the pizza! Lower the amount of meat!");
		if(pepper >=0 && pepper <=3)
			pepperoni = pepper;
		else 
			throw new IllegalPizza("Not a valid amount of pepperoni!");
		if(hm >= 0 && hm <= 3)
			ham = hm;
		else
			throw new IllegalPizza("Not a valid amount of ham!");
	}// end setMeat
	// method to determine the cost of a single pizza.
	private void costOfPizza(){
		double tempCost = 0;
		int toppings = numberOfToppings(cheese, ham, pepperoni);
		if(size.equalsIgnoreCase("small"))
			tempCost = 7.00;
		if(size.equalsIgnoreCase("medium"))
			tempCost = 9.00;
		if(size.equalsIgnoreCase("large"))
			tempCost = 11.00;
		costPizza = tempCost + 1.50*toppings;		
	}// end costOfPizza
	private int numberOfToppings(int chs, int hm, int pepper){
		return (chs-1)+hm+pepper;
	}
	/**
	 * Accessor for the cost of the pizza.
	 * @return costPizza, the pizzas cost attribute.
	 */
	public double getCost(){
		return costPizza;
	}
	/**
	 * Overrides "Objects" equals method to have a way of comparing if two pizzas are equal.
	 */
	@Override
	public boolean equals(Object otherObject){
		if(otherObject instanceof Pizza){
			Pizza otherPizza = (Pizza)otherObject;
			return pepperoni == otherPizza.pepperoni && cheese == otherPizza.cheese &&
                    ham == otherPizza.ham &&
                    size.equalsIgnoreCase(otherPizza.size);
		}
		return false;
	}
	@Override
	/**
	 * Overrides "Objects" clone method.
	 * Creates a copy of the current pizza object and @returns pizzaCopy, the cloned pizza.
	 * Used to ensure no privacy leaks occur.
	 */
	public Pizza clone(){
		Pizza pizzaCopy = null;
		try{
			pizzaCopy = new Pizza(size, cheese, ham, pepperoni);
		}
		//should never need the catch part of the code!
		catch(IllegalPizza e){
			return null; 
		}
		return pizzaCopy;
	}
	@Override
	/**
	 * Overrides the toString method found in Object.
	 * Creates a string representation of the pizza.
	 * @returns s: The final string which contains information about the cost, size and toppings on the pizza.
	 */
	public String toString(){
		String s = size.toLowerCase() + " pizza, ";
    	//cheese
    	if(cheese == 1)
    		s += "cheese";
    	else if(cheese == 2)
    		s += "double cheese";
    	else if(cheese == 3)
    		s += "triple cheese";
    	//meat
    	if(ham == 0 && pepperoni == 0)
    		s += " only";
    	else{
    		if(ham == 1)
        		s += ", ham";
        	else if(ham == 2)
        		s += ", double ham";
        	else if(ham == 3)
        		s += ", triple ham";
    		if(pepperoni == 1)
        		s += ", pepperoni";
        	else if(pepperoni == 2)
        		s += ", double pepperoni";
        	else if(ham == 3)
        		s += ", triple pepperoni";
    	}
    	s+= ". Cost: $" + this.getCost() + "0 each.";
    	return s;
	}
}
