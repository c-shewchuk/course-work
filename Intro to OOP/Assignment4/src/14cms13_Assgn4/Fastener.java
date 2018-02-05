import java.io.Serializable;
// the Base class of the hierarchy, a fastener cannot be instantiated, but provides the basis for all screws, nuts, bolts etc.
public abstract class Fastener implements Serializable{

	private static final long serialVersionUID = -3536814088900708548L;
	private String material;
	private String finish;
	private double costPerUnit;
	private int numPerUnit;
	
	public Fastener(String material, String finish, double unit_price, int numPerUnit ) throws IllegalFastener {
		
		//setting material
		if(material == null)
			throw new IllegalFastener("Material cannot be null!");
		if(material.equalsIgnoreCase("Brass") || material.equalsIgnoreCase("Stainless Steel") || material.equalsIgnoreCase("Steel") || material.equalsIgnoreCase("Aluminium") || material.equalsIgnoreCase("Copper") || material.equalsIgnoreCase("Nylon"))
			this.material = material;
		else
			throw new IllegalFastener("Illegal material!");
		
		//setting finish
		if(finish==null)
			throw new IllegalFastener("Finish cannot be null!");
		if(finish.equalsIgnoreCase("Chrome") || finish.equalsIgnoreCase("Hot Dipped Galvanized") ||  finish.equalsIgnoreCase("Yellow Zinc") || finish.equalsIgnoreCase("Zinc") || finish.equalsIgnoreCase("Black Phosphate") || finish.equalsIgnoreCase("ACQ 1000 Hour") || finish.equalsIgnoreCase("Lubricated")) {
			if(material.equalsIgnoreCase("Steel"))
				this.finish=finish;
			else
				throw new IllegalFastener("Non-steel fasteners can only have a plain finish!");
		}
		else if(finish.equalsIgnoreCase("Plain"))
			this.finish=finish;
		else
			throw new IllegalFastener("Illegal finish!");
			
		//setting number per unit
		if(numPerUnit < 1 || numPerUnit > 1000)
			throw new IllegalFastener("Must have between 1 and 1000 units!");
		if(numPerUnit %5 == 0 || numPerUnit ==1)
			this.numPerUnit = numPerUnit;	
		else
			throw new IllegalFastener("Number of units must either be equal to 1 or divisible by 5!");
       
		//setting unit price
		if(unit_price > 0)
			this.costPerUnit = unit_price;
		else
			throw new IllegalFastener("Unit price must be greater than zero!");
        
    } // end constructor
	
	public double getOrderCost(int num_per_unit) {
		return costPerUnit * num_per_unit;
	} //end getOrderCost
	
    @Override
    public String toString() {
        return material + ", with a " + finish + " finish. " + numPerUnit + " in a unit, $" + costPerUnit + " per unit.";
    } //end toString
	
}