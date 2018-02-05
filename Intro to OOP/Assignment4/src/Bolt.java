// Class that defines any general Bolt
public class Bolt extends Long {

	private static final long serialVersionUID = -4154044905011981426L;

	public Bolt(double length, String thread, String material, String finish, double costPerUnit, int numPerUnit) throws IllegalFastener {
		super(length, thread, material, finish, costPerUnit, numPerUnit);
	} //end constructor

}
	
