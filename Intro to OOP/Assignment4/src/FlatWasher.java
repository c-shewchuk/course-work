//Extends the washer class to a specific type of washer, the FlatWasher
public class FlatWasher extends Washer{

	private static final long serialVersionUID = 8912359068210376643L;

	public FlatWasher(double outer, double inner, double thickness, String material, String finish, double costPerUnit, int numPerUnit) throws IllegalFastener {
		super(outer, inner, thickness, material, finish, costPerUnit, numPerUnit);
		
		// legal materials for flatwashers only
		if(finish.equalsIgnoreCase("Black Phosphate") || finish.equalsIgnoreCase("ACQ 1000 Hour") || finish.equalsIgnoreCase("Lubricated"))
			throw new IllegalFastener("Illegal flat washer finish!");
	} //end constructor
	
	@Override
    public String toString() {
        return "Flat Washer, " + super.toString();
    } // end toString

}
