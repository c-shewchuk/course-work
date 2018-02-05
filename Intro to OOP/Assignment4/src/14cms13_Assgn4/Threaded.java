//extends the general fastener to those fasteners that have threads
public abstract class Threaded extends Fastener {
	
	private static final long serialVersionUID = -2331097779583111262L;
	private String thread;
	
	public Threaded(String thread, String material, String finish, double costPerUnit, int numPerUnit) throws IllegalFastener {
		super(material, finish, costPerUnit, numPerUnit);
		
		//setting thread
		if(thread.equalsIgnoreCase("#8-13") || thread.equalsIgnoreCase("#8-15") || thread.equalsIgnoreCase("#8-32") || thread.equalsIgnoreCase("#10-13") || thread.equalsIgnoreCase("#10-24") || thread.equalsIgnoreCase("#10-32") || thread.equalsIgnoreCase("1/4-20") || thread.equalsIgnoreCase("5/16-18") || thread.equalsIgnoreCase("3/8-16") || thread.equalsIgnoreCase("7/16-14") || thread.equalsIgnoreCase("1/2-13") || thread.equalsIgnoreCase("5/8-11") || thread.equalsIgnoreCase("3/4-10"))
			this.thread=thread;
		else
			throw new IllegalFastener("Illegal diameter/thread size!");
	
		// legal materials for flat washers only
		if(material.equalsIgnoreCase("Aluminium") || material.equalsIgnoreCase("Copper") || material.equalsIgnoreCase("Nylon"))
			throw new IllegalFastener("This material is only applicable to flat washers!");
	}
	
	@Override
    public String toString() {
        return thread + " thread, " + super.toString();
    } //end toString
	
}