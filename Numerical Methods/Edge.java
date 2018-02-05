// The Edge object describes an object that connects two verticies.
//
public class Edge implements Comparable<Edge>{
	private Vertex one, two;
	private int weight;
	public Edge(Vertex one, Vertex two, int weight) {
		this.one = (one.getName().compareTo(two.getName())<=0) ? one: two;
		this.two = (this.one == one) ? two:one;
		this.weight = weight;
		
	}
	public Edge(Vertex one, Vertex two){
		this(one,two,1);
	}
	
	public Vertex getNeighbor(Vertex current){
		if(!(current.equals(one) || current.equals(two)))
			return null;
		return(current.equals(one)) ? two:one;
	}
	
	public Vertex getOne(){
		return this.one;
	}
	
	public Vertex getTwo(){
		return this.two;
	}
	
	public int getWeight(){
		return this.weight;
	}
	
	public void setWeight(int weight){
		this.weight = weight;
	}
	
	@Override
	public int compareTo(Edge other) {
		// TODO Auto-generated method stub
		return this.weight - other.weight;
	}
	
	@Override
	public String toString(){
		return "((" + one + ", " + two + "), " + weight + ")";
	}
	
	public int hashCode(){
		return(one.getName() + two.getName()).hashCode();
	}
	
	public boolean equals(Object other){
		if(!(other instanceof Edge))
			return false;
		Edge e = (Edge)other;
		return e.one.equals(this.one) && e.two.equals(this.two);
	}

}
