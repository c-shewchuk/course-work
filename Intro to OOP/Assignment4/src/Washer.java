// extends the fastener class to a generl washer
public class Washer extends Fastener{

	
	private static final long serialVersionUID = -2598017776505590660L;
	private double outer;
	private double inner;
	private double thickness;
	
	public Washer(double outer, double inner, double thickness, String material, String finish, double costPerUnit, int numPerUnit) throws IllegalFastener {
		super(material, finish, costPerUnit, numPerUnit);
		
		//setting outer diameter
		if(outer >= 0.187 && outer <= 2.010)
			this.outer=outer;
		else
			throw new IllegalFastener("Illegal washer outer diameter!");
		
		//setting inner diameter
		if(inner > outer - 0.1)
			throw new IllegalFastener("Inner diameter too large!");
		if(inner >= 0.068 && inner <= 0.817)
			this.inner=inner;
		else
			throw new IllegalFastener("Illegal washer inner diameter!");
		
		//setting thickness
		if(thickness >= 0.015 && thickness <= 0.109)
			this.thickness=thickness;
		else
			throw new IllegalFastener("Illegal washer thickness!");
	} //end constructor
	
	@Override
    public String toString() {
        return outer + " OD, " + inner + " ID, " + thickness + " thick, " + super.toString();
    } //end toString
	
}

