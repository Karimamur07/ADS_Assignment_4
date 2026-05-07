import java.util.*;

/**
 * Graph implemented using adjacency list.
 * Supports BFS and DFS traversals.
 */
public class Graph {
    private final Map<Integer, List<Integer>> adjList;
    private final List<Edge> edges;
    private final Map<Integer, Vertex> vertices;

    public Graph() {
        adjList = new HashMap<>();
        edges = new ArrayList<>();
        vertices = new HashMap<>();
    }

    /**
     * Adds a vertex to the graph.
     */
    public void addVertex(Vertex v) {
        int id = v.getId();
        if (!vertices.containsKey(id)) {
            vertices.put(id, v);
            adjList.put(id, new ArrayList<>());
        }
    }

    /**
     * Adds an undirected edge between two vertices.
     */
    public void addEdge(int fromId, int toId) {
        Vertex from = vertices.get(fromId);
        Vertex to = vertices.get(toId);
        if (from == null || to == null) {
            throw new IllegalArgumentException("Vertex does not exist");
        }

        adjList.get(fromId).add(toId);
        adjList.get(toId).add(fromId);

        edges.add(new Edge(from, to));
        edges.add(new Edge(to, from));
    }

    /**
     * Prints the graph structure: adjacency list.
     */
    public void printGraph() {
        System.out.println("Adjacency List:");
        for (int id : adjList.keySet()) {
            System.out.println(vertices.get(id) + " -> " + adjList.get(id));
        }
    }

    /**
     * Breadth-First Search traversal starting from vertex startId.
     * Returns list of vertices in traversal order.
     */
    public List<Vertex> bfs(int startId) {
        if (!vertices.containsKey(startId)) return Collections.emptyList();

        List<Vertex> order = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();

        visited.add(startId);
        queue.add(startId);

        while (!queue.isEmpty()) {
            int current = queue.poll();
            order.add(vertices.get(current));

            for (int neighbor : adjList.get(current)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
        return order;
    }

    /**
     * Depth-First Search traversal starting from vertex startId.
     * Returns list of vertices in traversal order.
     */
    public List<Vertex> dfs(int startId) {
        if (!vertices.containsKey(startId)) return Collections.emptyList();

        List<Vertex> order = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        dfsRecursive(startId, visited, order);
        return order;
    }

    private void dfsRecursive(int currentId, Set<Integer> visited, List<Vertex> order) {
        visited.add(currentId);
        order.add(vertices.get(currentId));

        for (int neighbor : adjList.get(currentId)) {
            if (!visited.contains(neighbor)) {
                dfsRecursive(neighbor, visited, order);
            }
        }
    }

    public int getVertexCount() {
        return vertices.size();
    }

    public int getEdgeCount() {
        return edges.size() / 2;
    }
}