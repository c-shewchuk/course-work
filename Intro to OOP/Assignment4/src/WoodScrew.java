// extends the genral screw class to a specific type of screw, the wood screw.
public class WoodScrew extends Screw {
	
	private static final long serialVersionUID = -2130142484982162899L;
	private String head;
	private String drive;
	private String point;
	
	public WoodScrew(double length, String thread, String material, String finish, String head, String drive, String point, double costPerUnit, int numberOfItem) throws IllegalFastener {
		super(length, thread, material, finish, costPerUnit, numberOfItem);
		
		//setting head
		if(head.equalsIgnoreCase("Bugle") || head.equalsIgnoreCase("Flat") || head.equalsIgnoreCase("Oval") || head.equalsIgnoreCase("Pan") || head.equalsIgnoreCase("Round"))
			this.head=head;
		else
			throw new IllegalFastener("Illegal wood screw head!");
		
		//setting drive
		if(drive.equalsIgnoreCase("6-Lobe") || drive.equalsIgnoreCase("Philips") || drive.equalsIgnoreCase("Slotted") || drive.equalsIgnoreCase("Square"))
			this.drive=drive;
		else
			throw new IllegalFastener("Illegal wood screw drive!");
		
		//setting point
		if(point.equalsIgnoreCase("Double Cut") || point.equalsIgnoreCase("Sharp") || point.equalsIgnoreCase("Type 17"))
			this.point=point;
		else
			throw new IllegalFastener("Illegal wood screw drive!");	
	} //end constructor
	
	@Override
    public String toString() {
        return "Wood Screw, " + head + " head, " + drive + " drive, " + point + " point " + super.toString();
    } // end toString
	
}
