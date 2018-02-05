import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
// The Class Dijikstra implements Dijikstras algorithm on the graph that it is handed.
// Instantiating the class creates an object which searches and finds the path.
// The algorithm is well known and works for a variety of graphs.
public class Dijikstra {
    private Graph graph;
    private String initialVertexLabel;
    private HashMap<String, String> predecessors;
    private HashMap<String, Integer> distances;
    private PriorityQueue<Vertex> availableVertices;
    private HashSet<Vertex> visitedVertices;
    
    public Dijikstra(Graph graph, String initialVertexLabel) throws IllegalArgumentException{
        this.graph = graph;
        Set<String> vertexKeys = this.graph.vertexKeys();
        if(!vertexKeys.contains(initialVertexLabel))
        	throw new IllegalArgumentException("The graph must contain this vertex to start there!");
        this.initialVertexLabel = initialVertexLabel;
        this.predecessors = new HashMap<String, String>();
        this.distances = new HashMap<String, Integer>();
        this.availableVertices = new PriorityQueue<Vertex>(vertexKeys.size(), new Comparator<Vertex>(){
        	public int compare(Vertex one, Vertex two){
        		int weightOne = Dijikstra.this.distances.get(one);
        		int weightTwo = Dijikstra.this.distances.get(two);
        		return weightOne - weightTwo;
        	}
        	});
        this.visitedVertices = new HashSet<Vertex>();
        
        for(String key: vertexKeys){
        	this.predecessors.put(key, null);
        	this.distances.put(key, Integer.MAX_VALUE);
        }
        this.distances.put(initialVertexLabel, 0);
        Vertex initialVertex = this.graph.getVertex(initialVertexLabel);
        ArrayList<Edge> initialVertexNeighbors = initialVertex.getNeighbors();
        for(Edge e: initialVertexNeighbors){
        	Vertex other = e.getNeighbor(initialVertex);
        	this.predecessors.put(other.getName(), initialVertexLabel);
        	this.distances.put(other.getName(), e.getWeight());
        	this.availableVertices.add(other);
        	}
        this.visitedVertices.add(initialVertex);
        findShortPath();
        }
    //finds all shortest paths hahahahahaha
    private void findShortPath(){
    	while(this.availableVertices.size() > 0){
    		Vertex next = this.availableVertices.poll();
    		int distanceToNext = this.distances.get(next.getName());
    		List<Edge> nextNeighbors = next.getNeighbors();
    		for(Edge e: nextNeighbors){
    			Vertex other = e.getNeighbor(next);
    			if(this.visitedVertices.contains(other))
    				continue;
    			int currentWeight = this.distances.get(other.getName());
    			int newWeight = distanceToNext + e.getWeight();
    			if(newWeight < currentWeight){
    				this.predecessors.put(other.getName(), next.getName());
    				this.distances.put(other.getName(), newWeight);
    				this.availableVertices.remove(other);
    				this.availableVertices.add(other);
    			}
    			this.visitedVertices.add(next);
    	
    		}
    	}
    }
    
    public List<Vertex> getPathTo(String destinationLabel){
    	LinkedList<Vertex> path = new LinkedList<Vertex>();
    	path.add(graph.getVertex(destinationLabel));
    	while(!destinationLabel.equals(this.initialVertexLabel)){
    		Vertex predecessor = graph.getVertex(this.predecessors.get(destinationLabel));
    		destinationLabel = predecessor.getName();
    		path.add(0, predecessor);
    	}
    	return path;
    }
    
    public int getDistance(String destinationLabel){
    	return this.distances.get(destinationLabel);
    }
        
    }

         
       
