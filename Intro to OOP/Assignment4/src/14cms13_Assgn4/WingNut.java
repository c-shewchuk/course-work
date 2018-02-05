// extends the general nut class to a specific type of nut, the wing nut.
public class WingNut extends Nut{

	private static final long serialVersionUID = 3744863551235647028L;

	public WingNut(String thread, String material, String finish, double costPerUnit, int numPerUnit) throws IllegalFastener {
		super(thread, material, finish, costPerUnit, numPerUnit);
		
		// legal materials for steel screws only
		if(finish.equalsIgnoreCase("Black Phosphate") || finish.equalsIgnoreCase("ACQ 1000 Hour") || finish.equalsIgnoreCase("Lubricated"))
			throw new IllegalFastener("Illegal wing nut finish!");
	} //end constructor

	@Override
    public String toString() {
        return "Wing Nut, " + super.toString();
    } // end toString

}
