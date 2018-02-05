import java.util.ArrayList;
// A vertex is a node in a graph, holds information about the name, and the connections it holds, the Edges.
public class Vertex{
	private ArrayList<Edge> neighborhood;
	private String name; // Identifier, could be a real name or just a letter, I will use letters in my set up
	
	//Constructor of a Vertex
	public Vertex(String name){
		this.name = name;
		this.neighborhood = new ArrayList<Edge>();
	}
	
	public void addNeighbor(Edge edge){
		if(this.neighborhood.contains(edge))
			return;
		this.neighborhood.add(edge);
	}
	
	public boolean containsNeighbor(Edge other){
		return this.neighborhood.contains(other);
	}

	public Edge getNeighbor(int index){
		return this.neighborhood.get(index);
	}
	
	public Edge removeNeighbor(int index){
		return this.neighborhood.remove(index);
	}
	
	public void removeNeighbor(Edge e){
		this.neighborhood.remove(e);
	}
	
	public int getNeighborCount(){
		return this.neighborhood.size();
	}
	
	public String getName(){
		return this.name;
	}
	
	@Override
	public String toString(){
		return "Vertex " + this.name;
	}
	
	public int hashCode(){
		return this.name.hashCode();
	}
	@Override
	public boolean equals(Object other){
		if(!(other instanceof Vertex))
			return false;
		Vertex v = (Vertex)other;
		return this.name.equals(v.name);
	}
	
	public ArrayList<Edge> getNeighbors(){
		return new ArrayList<Edge>(this.neighborhood);
	}
}
