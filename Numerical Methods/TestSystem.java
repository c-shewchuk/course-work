import java.util.Scanner;
import java.util.Random;
/* TestSystem is going to be the program that gets run to set up a graph
// I will say right now, it does not work all the time. To see how it's supposed to work
 * I suggest inputting the number of verticies as 2 and one edge. Then input the letter that connects to A
 * and you will see the proper output. It can work for higher numbers of verticies, but it does not always work.
*/
public class TestSystem {
	private static int numberOfVerticies;
	private static int numberOfEdges;
	public static Scanner sc = new Scanner(System.in);
	private static Vertex[] verticies = new Vertex[numberOfVerticies];
	
	private static int random(int min, int max){
		Random rand = new Random();
		int randNum = rand.nextInt((max - min) + 1) + min;
		return randNum;
	}
	
	private static void getLinks(){
		int links = 0;
		int edge = 0;
		System.out.println("Enter the number of Nodes in the Graph:");
		while(links == 0){
			links = sc.nextInt();
			if(links >= 27){
				links = 0;
				System.out.print("Only allowed a maximum of 26 verticies in the graph! Please enter a lower number.");
			}
			
		}
		System.out.print("Enter the number of edges wanted in the graph:");
		while(edge == 0){
			edge = sc.nextInt();
			if(links*(links-1) / 2 < edge){
				edge = 0;
				System.out.println("There need to be more links then edges! Enter a lower number of edges!");
			}
			
		}
		numberOfVerticies = links;
		numberOfEdges = edge;
		verticies = new Vertex[numberOfVerticies];
		System.out.println("Cool! A " + numberOfVerticies +" node graph is being randomly generated with " + numberOfEdges +" edges.");
		
	}
	
	public static Graph generateGraph() {
        Graph graph = new Graph();
        int start = 65;
        for(int i = 0; i< numberOfVerticies; i++){
        	String label = new Character((char)(start+i)).toString();
        	verticies[i] = new Vertex(label);
        	graph.addVertex(verticies[i], true);
        }
        int randomTwo = random(0,numberOfVerticies-1);
        graph.addEdge(verticies[0], verticies[randomTwo], 1);
        System.out.println(verticies[0].toString() + " connects to " +verticies[randomTwo].toString());
        for(int i = 0; i < numberOfEdges-1; i++){
        	int randomOne = randomTwo;
        	randomTwo = random(0,numberOfVerticies-1);
        	while(true){
        		if(randomOne != randomTwo)
        			break;
        		randomTwo = random(0,numberOfVerticies-1);
        			
        	}
            Edge edges = new Edge(verticies[randomOne], verticies[randomTwo], 1);
            if(graph.containsEdge(edges))
            	i--;
            else
            	graph.addEdge(verticies[randomOne], verticies[randomTwo]);
            System.out.println(randomOne);
            System.out.println(randomTwo);
            System.out.println(verticies[randomOne].toString() + " connects to " +verticies[randomTwo].toString());
            }
        return graph;
    }
	
	public static void findShortest(String input, Graph graph){
		String findPath = input;
		if(findPath == null)
			findPath = "A";
		Dijikstra shortestPath = new Dijikstra(graph, findPath);
		while(true){
			System.out.println("Shortest paths have been found. Input a letter to find the shortest path from " + findPath +" to that letter! Press enter to stop." );
			String in = sc.nextLine();
			if(in.equals(""))
				break;
			int distance = shortestPath.getDistance(in);
			System.out.println("Shortest path is " + distance + ".");
			System.out.println("Path taken: " + shortestPath.getPathTo(in));
		}
	}
	
	
	public static void main(String args[]) throws Exception{
		getLinks();
		Graph graph = generateGraph();
		System.out.println("Graph created. All nodes are labeled by their position in the alphabet, and are randomly connected to other letters.");
		System.out.println("ie, A may be connected to C, but not directly connected to B. Press enter to find the shortest path between A and all other nodes.");
		while(true){
			String in = sc.nextLine();
			if(in.equals(""))
				break;
		}
			
		findShortest(null,graph);
		while(true){
			System.out.print("Input another letter to start at, or press enter to end the program.");
			String in = sc.nextLine();
			if(in.equals(""))
				break;
			findShortest(in,graph);
		}
		System.out.println("Thanks for playing with my project!");
	}

}
