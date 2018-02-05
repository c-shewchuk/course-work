package application;
import java.io.Serializable;
/**
 * A class that holds an instance of the Pizza class, and the number of those pizzas to be ordered.
 * <p>
 * @author curtisshewchuk
 * Version 1.0
 */
public class LineItem implements Serializable, Comparable<LineItem>{
	private static final long serialVersionUID = 5467497009700200109L;
	private int numPizzas;
	private Pizza pizza;
	private double cost;
	/**
	 * Two parameter constructor that instantiates the an order of pizzas.
	 * @param number: The number of pizzas that are to be ordered.
	 * @param pz: A pointer to an instance of the Pizza class.
	 * @throws IllegalPizza if the pizza object supplied is null or if the mutator setNumber throws IllegalPizza.
	 */
	public LineItem(int number, Pizza pz) throws IllegalPizza {
		if(pz == null)
			throw new IllegalPizza("Null pizza object!");
		pizza = pz;
		setNumber(number);
		cost = number*pizza.getCost();
	}
	/**
	 * One parameter constructor that takes a pointer to an instance of the Pizza class.
	 * The method then gives the 2 parameter constructor a default value of 1 for the
	 * number of pizzas ordered, and passes the pointer to the pizza.
	 * @param pz: The pointer to the pizza.
	 * @throws IllegalPizza: If the 2 parameter constructor throws the IllegalPizza.
	 */
	public LineItem(Pizza pz) throws IllegalPizza{
		this(1,pz);
	}
	/**
	 * Sets the number of pizzas to be ordered.
	 * Checks that the number of pizzas is between 1 and 100 inclusive.
	 * @param number:
	 * @throws IllegalPizza if the number of pizzas is less than 1 or greater than 100.
	 */
	public void setNumber(int number) throws IllegalPizza{
		if(number <= 100 && number >= 1)
			numPizzas = number;
		else 
			throw new IllegalPizza("Illegal number of pizzas!");
	}
	/**
	 * Accessor
	 * @return a copy of the pizza. 
	 * 
	 */
	public Pizza getPizza(){
		return pizza.clone();
	}
	/**
	 * Accessor
	 * @return numPizzas, the number of pizzas in the order.
	 */
	public int getNumber(){
		return numPizzas;
	}
	/**
	 * Accessor: Returns the cost of a pizza.
	 * @return The cost of a pizza order.
	 */
	public double getCost(){
		return cost;
	}
	@Override
	/**
	 * Overrides "Objects" toString method. Uses the toString from the Pizza class, and adds the number of pizzas to the beginning of the string.
	 * @return the string representation of the pizza order.
	 */
	public String toString(){
		String s = String.format("%2d", numPizzas) + " " + pizza.toString();
		return s;
	}
	
	@Override
	/**
	 * A method used to compare pizza orders so that they can be sorted by Java's built sorting algorithms
	 * @returns an integer value for comparison.
	 */
	public int compareTo(LineItem otherItem) {
		if (Math.abs(otherItem.getCost()-getCost()) < 1)
			return 0;
		return (int)(otherItem.getCost()-getCost());
	}

}
