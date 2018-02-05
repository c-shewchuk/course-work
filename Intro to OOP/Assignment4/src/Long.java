// extends the threaded class to those types of fasteners that have a length, ie screws and bolts.
public abstract class Long extends Threaded {

	private static final long serialVersionUID = -1410394740583812085L;
	private double length;
	
	public Long(double length, String thread, String material, String finish, double costPerUnit, int numPerUnit) throws IllegalFastener {
		super(thread, material, finish, costPerUnit, numPerUnit);
		
		//setting length
		if(length >= 0.5 && length <= 6){
			if(length%0.25 == 0)
				this.length=length;
			else
				throw new IllegalFastener("Length must be in units of 1/4\"!");
		}
		else if(length >= 6 && length <= 11) {
			if(length%0.5 == 0)
				this.length=length;
			else
				throw new IllegalFastener("Length must be in units of 1/2\"!");
		}
		else if(length >= 11 && length <= 20) {
			if(length%1 == 0)
				this.length=length;
			else
				throw new IllegalFastener("Length must be in units of 1\"!");
		}
		else
			throw new IllegalFastener("Illegal length!");
	} //end constructor
	
	@Override
    public String toString() {
        return length + " long, " + super.toString();
    } //end toString

}