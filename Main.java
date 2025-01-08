/**
 *
 *
 */
import java.util.*;

class Graph 
{
    private final Map<String, List<Edge>> adjacencyList;

    public Graph() 
    {
        this.adjacencyList = new HashMap<>();
    }

    // Adds an edge to the graph with cost, time and distance
    public void addEdge(String source, String destination, double cost, double time, double distance) 
    {
        // Check if the source node already exists in the adjacency list, otherwise initialize it
        this.adjacencyList.putIfAbsent(source, new ArrayList<>());

        // Check if the edge already exists to prevent duplicates
        boolean edgeExists = this.adjacencyList.get(source).stream()
            .anyMatch(edge -> edge.getDestination().equals(destination) && 
                              edge.getCost() == cost &&
                              edge.getTime() == time &&
                              edge.getDistance() == distance);

        if (!edgeExists) 
        {
            // Add a new edge from source to destination
            this.adjacencyList.get(source).add(new Edge(destination, cost, time, distance));
        }
    }


    // Calculates the details (total cost, time, and distance) for all provided paths
    public void calculatePathDetails(List<List<String>> paths) 
    {
        System.out.println("\n========================================================================================================================================================================================================");
        System.out.println("                                                           Path Details Calculation By Bus");
        System.out.println("========================================================================================================================================================================================================\n");

        for (int i = 0; i < paths.size(); i++) 
        {
            List<String> path = paths.get(i);
            double totalCost = 0.0;
            double totalTime = 0.0; // Time in hours
            double totalDistance = 0.0;

            System.out.println("Path " + (i + 1) + ": " + String.join(" -> ", path));

            for (int j = 0; j < path.size() - 1; j++) 
            {
                String current = path.get(j);
                String next = path.get(j + 1);

                Edge edge = adjacencyList.getOrDefault(current, new ArrayList<>()).stream()
                        .filter(e -> e.getDestination().equals(next))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Invalid path segment: " + current + " -> " + next));

                totalCost += edge.getCost();
                totalTime += edge.getTime(); // Keep time in hours
                totalDistance += edge.getDistance();
            }

            System.out.printf("  Total Cost: RM%.2f\n", totalCost);
            System.out.printf("  Total Time: %.2f hours\n", totalTime); // Display time in hours
            System.out.printf("  Total Distance: %.2f km\n\n", totalDistance);
        }
    }

    // Finds the shortest path from startNode to endNode step by step
    public void findExactShortestPath(String startNode, String endNode) 
    {
        Set<String> visited = new HashSet<>();
        List<String> path = new ArrayList<>();
        String current = startNode;
        path.add(current);

        System.out.println("\n========================================================================================================================================================================================================");
        System.out.println("                                                           Shortest Pathfinding Process by Bus");
        System.out.println("========================================================================================================================================================================================================\n");

        System.out.println("Starting Pathfinding from " + startNode + " to " + endNode);

        while (!current.equals(endNode)) 
        {
            visited.add(current);
            List<Edge> edges = adjacencyList.getOrDefault(current, new ArrayList<>());

            final String currentCopy = current;

            System.out.println("\nFrom " + current + ", checking possible routes:");
            edges.forEach(edge -> 
            {
                System.out.println(currentCopy + " -> " + edge.getDestination() + " (" + edge.getDistance() + " km)");
            });

            Edge shortestEdge = edges.stream()
                    .filter(edge -> !visited.contains(edge.getDestination()))
                    .min(Comparator.comparingDouble(Edge::getDistance))
                    .orElse(null);

            if (shortestEdge == null) 
            {
                System.out.println("No unvisited paths available from " + current);
                break;
            }

            current = shortestEdge.getDestination();
            path.add(current);

            System.out.println("\nSelected Path: " + path);
        }

        System.out.println("\n========================================================================================================================================================================================================");
        System.out.println("                                                           Shortest Pathfinding Display By Bus ");
        System.out.println("========================================================================================================================================================================================================");
        System.out.println("\nFinal Path: " + String.join(" -> ", path));
    }

}

class Edge 
{
    private final String destination; // Destination node of the edge
    private final double cost; // Cost of traveling this edge
    private final double time; // Time taken to travel this edge
    private final double distance; // Distance of the edge
    
    // Constructor to initialize edge attributes
    public Edge(String destination, double cost, double time, double distance) 
    {
        this.destination = destination;
        this.cost = cost;
        this.time = time;
        this.distance = distance;
    }

    public String getDestination() 
    {
        return destination;
    }

    public double getCost() 
    {
        return cost;
    }

    public double getTime() 
    {
        return time;
    }

    public double getDistance() 
    {
        return distance;
    }
}

public class Main
{
    public static void main(String[] args) 
    {
        Graph graph = new Graph();

        // Add edges for Path 1
        graph.addEdge("Tokyo St", "Shizuoka St", 56.90, 2.25, 180);
        graph.addEdge("Shizuoka St", "Nagoya St", 51.21, 1.88, 150);
        graph.addEdge("Nagoya St", "Kyoto St", 42.68, 1.62, 130);
        graph.addEdge("Kyoto St", "Osaka St", 28.45, 0.62, 50);
     

        // Add edges for Path 2
        graph.addEdge("Tokyo St", "Yokohama St", 14.23, 0.38, 30);
        graph.addEdge("Yokohama St", "Nagoya St", 88.35, 3.60, 320);
        graph.addEdge("Nagoya St", "Kobe St", 56.90, 1.75, 140);
        graph.addEdge("Kobe St", "Osaka St", 14.23, 0.37, 30);
       

        // Add edges for Path 3
        graph.addEdge("Tokyo St", "Hamamatsu St", 71.25, 3.25, 260);
        graph.addEdge("Hamamatsu St", "Nagoya St", 34.14, 1, 80);
        graph.addEdge("Nagoya St", "Kyoto St", 42.68, 1.62, 130);
        graph.addEdge("Kyoto St", "Osaka St", 28.45, 0.62, 50);
      

        // Add edges for Path 4
        graph.addEdge("Tokyo St", "Shizuoka St", 56.90, 2.25, 180); // Corrected node name
        graph.addEdge("Shizuoka St", "Nagoya St", 51.21, 1.88, 150);
        graph.addEdge("Nagoya St", "Nara St", 48.37, 1.5, 130);
        graph.addEdge("Nara St", "Osaka St", 22.76, 0.5, 40);

        

        // Add edges for Path 5
        graph.addEdge("Tokyo St", "Yokohama St", 14.23, 0.38, 30);
        graph.addEdge("Yokohama St", "Shizuoka St", 47.22, 1.58, 130);
        graph.addEdge("Shizuoka St", "Nagoya St", 51.21, 1.88, 150);
        graph.addEdge("Nagoya St", "Osaka St", 45.9, 1.29, 120);
       
        // Define all possible paths
        List<List<String>> paths = new ArrayList<>();
        
        // This list represents predefined paths to calculate their details.
        paths.add(Arrays.asList("Tokyo St", "Shizuoka St", "Nagoya St", "Kyoto St", "Osaka St")); // Path 1
        paths.add(Arrays.asList("Tokyo St", "Yokohama St", "Nagoya St", "Kobe St", "Osaka St"));  // Path 2
        paths.add(Arrays.asList("Tokyo St", "Hamamatsu St", "Nagoya St", "Kyoto St", "Osaka St")); // Path 3
        paths.add(Arrays.asList("Tokyo St", "Shizuoka St", "Nagoya St", "Nara St", "Osaka St"));   // Path 4
        paths.add(Arrays.asList("Tokyo St", "Yokohama St", "Shizuoka St", "Nagoya St", "Osaka St")); // Path 5


        // Calculate and display details for all paths
        graph.calculatePathDetails(paths);

        // Find the shortest path using dijktstra alogorithm
        graph.findExactShortestPath("Tokyo St", "Osaka St");
    }
}