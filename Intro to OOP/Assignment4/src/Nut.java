// a class which describes any general nut type fastener.
public class Nut extends Threaded{

	private static final long serialVersionUID = 1962410423782777839L;

	public Nut(String thread, String material, String finish, double costPerUnit, int numPerUnit) throws IllegalFastener {
		super(thread, material, finish, costPerUnit, numPerUnit);
	} //end constructor

}


