// Class that describes a specific bolt, the Carriage bolt.
public class CarriageBolt extends Bolt{
	
	private static final long serialVersionUID = 2661778837025872948L;

	public CarriageBolt(double length, String thread, String material, String finish, double costPerUnit, int numPerUnit) throws IllegalFastener {
		super(length, thread, material, finish, costPerUnit, numPerUnit);
		
		// legal materials for carraige bolts only
		if(finish.equalsIgnoreCase("Black Phosphate") || finish.equalsIgnoreCase("ACQ 1000 Hour") || finish.equalsIgnoreCase("Lubricated"))
			throw new IllegalFastener("Illegal carriage bolt finish!");
	} //end constructor
	
	@Override
    public String toString() {
        return "Carriage Bolt, " + super.toString();
    } // end toString

}
