// will set up a new Graph, the size of the graph can not be mutated afterwards
// Requires the Edge and Vertex classes
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
public class Graph {

	private HashMap<String,Vertex> verticies;
	private HashMap<Integer, Edge> edges;
	//Produces a graph structure, the graph constructor that initializes the HashMaps
	public Graph(){
		this.verticies = new HashMap<String, Vertex>();
		this.edges = new HashMap<Integer, Edge>();
	}
	// alternative constructor where the number of nodes is specified.
	public Graph(int V){
		this.verticies = new HashMap<String, Vertex>();
		this.edges = new HashMap<Integer, Edge>();
	}
	// if an arraylist already exists then use this constructor to make the graph
	public Graph(ArrayList<Vertex> verticies){
		this.verticies = new HashMap<String, Vertex>();
		this.edges = new HashMap<Integer, Edge>();
		
		for(Vertex v: verticies)
			this.verticies.put(v.getName(), v);
	}
	
	public boolean addEdge(Vertex one, Vertex two){
		return addEdge(one, two, 1);
	}
	
	@SuppressWarnings("unchecked")
	public List<Vertex> getVerticies(){
		return (List<Vertex>) this.verticies;
	}
	
	@SuppressWarnings("unchecked")
	public List<Edge> getAllEdges(){
		return (List<Edge>) this.edges;
	}
	public boolean addEdge(Vertex one, Vertex two, int weight){
		if(one.equals(two))
			return false;
		Edge e = new Edge(one,two,weight);
		if(edges.containsKey(e.hashCode()))
			return false;
		else if(one.containsNeighbor(e) || two.containsNeighbor(e))
				return false;
		edges.put(e.hashCode(), e);
		one.addNeighbor(e);
		two.addNeighbor(e);
		return true;
	}
	
	public boolean containsEdge(Edge e){
		if(e.getOne() == null || e.getTwo() == null)
			return false;
		return this.edges.containsKey(e.hashCode());
	}
	
	public Edge removeEdge(Edge e){
		e.getOne().removeNeighbor(e);
		e.getTwo().removeNeighbor(e);
		return this.edges.remove(e.hashCode());
	}
	
	public boolean containsVertex(Vertex v){
		return this.verticies.get(v.getName()) != null;
	}
	
	public Vertex getVertex(String name){
		return verticies.get(name);
		
	}
	public boolean addVertex(Vertex vertex, boolean overwriteExisting){
		Vertex current = this.verticies.get(vertex.getName());
		if(current != null){
			if(!overwriteExisting)
				return false;
		while(current.getNeighborCount() > 0)
			this.removeEdge(current.getNeighbor(0));
		}
		verticies.put(vertex.getName(), vertex);
		return true;
	}
	
	public Vertex removeVertex(String name){
		Vertex v = verticies.remove(name);
		while(v.getNeighborCount() > 0)
			this.removeEdge(v.getNeighbor(0));
		
		return v;
	}
	
	public Set<String> vertexKeys(){
		return this.verticies.keySet();
	}
	public Set<Edge> getEdges(){
		return new HashSet<Edge>(this.edges.values());
	}
}
