// a class which describes any screw type fastener.
public class Screw extends Long{
	
	private static final long serialVersionUID = 8625975123271442465L;

	public Screw(double length, String thread, String material, String finish, double costPerUnit, int numPerUnit) throws IllegalFastener {
		super(length, thread, material, finish, costPerUnit, numPerUnit);
		
		// legal finishes for steel screws only
		if(!material.equalsIgnoreCase("Steel")) {
			if(finish.equalsIgnoreCase("Black Phosphate") || finish.equalsIgnoreCase("ACQ 1000 Hour") || finish.equalsIgnoreCase("Lubricated"))
				throw new IllegalFastener("This finish is only for steel screws!");	
		}
		
	} //end constructor

}