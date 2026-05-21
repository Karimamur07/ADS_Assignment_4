import java.util.*;

public class Graph {

    private final Map<Integer, List<Neighbor>> adjList;
    private final List<Edge> edges;
    private final Map<Integer, Vertex> vertices;


    private static class Neighbor {
        int vertexId;
        int weight;
        Neighbor(int vertexId, int weight) {
            this.vertexId = vertexId;
            this.weight = weight;
        }
        @Override
        public String toString() {
            return "V" + vertexId + "(" + weight + ")";
        }
    }

    public Graph() {
        adjList = new HashMap<>();
        edges = new ArrayList<>();
        vertices = new HashMap<>();
    }

    public void addVertex(Vertex v) {
        int id = v.getId();
        if (!vertices.containsKey(id)) {
            vertices.put(id, v);
            adjList.put(id, new ArrayList<>());
        }
    }


    public void addEdge(int fromId, int toId, int weight) {
        Vertex from = vertices.get(fromId);
        Vertex to = vertices.get(toId);
        if (from == null || to == null) {
            throw new IllegalArgumentException("Vertex does not exist");
        }

        adjList.get(fromId).add(new Neighbor(toId, weight));
        adjList.get(toId).add(new Neighbor(fromId, weight));

        edges.add(new Edge(from, to, weight));
        edges.add(new Edge(to, from, weight));
    }


    public void addEdge(int fromId, int toId) {
        addEdge(fromId, toId, 1);
    }

    public void printGraph() {
        System.out.println("Adjacency List (with weights):");
        for (int id : adjList.keySet()) {
            System.out.println(vertices.get(id) + " -> " + adjList.get(id));
        }
    }

    // ========== Dijkstra’s Algorithm ==========
    public void dijkstra(int startId) {
        if (!vertices.containsKey(startId)) {
            System.out.println("Start vertex " + startId + " not found!");
            return;
        }

        int n = vertices.size();
        Map<Integer, Integer> dist = new HashMap<>();
        Set<Integer> visited = new HashSet<>();
        Map<Integer, Integer> previous = new HashMap<>();


        for (int id : vertices.keySet()) {
            dist.put(id, Integer.MAX_VALUE);
        }
        dist.put(startId, 0);

        while (visited.size() < n) {
            int current = -1;
            int minDist = Integer.MAX_VALUE;
            for (int id : vertices.keySet()) {
                if (!visited.contains(id) && dist.get(id) < minDist) {
                    minDist = dist.get(id);
                    current = id;
                }
            }

            if (current == -1) break;

            visited.add(current);


            for (Neighbor neighbor : adjList.get(current)) {
                int neighborId = neighbor.vertexId;
                int weight = neighbor.weight;
                if (!visited.contains(neighborId)) {
                    int newDist = dist.get(current) + weight;
                    if (newDist < dist.get(neighborId)) {
                        dist.put(neighborId, newDist);
                        previous.put(neighborId, current);
                    }
                }
            }
        }


        printDijkstraResults(startId, dist, previous);
    }

    private void printDijkstraResults(int startId, Map<Integer, Integer> dist,
                                      Map<Integer, Integer> previous) {
        System.out.println("\n=== Dijkstra's Algorithm Results ===");
        System.out.println("Shortest paths from vertex " + vertices.get(startId) + ":\n");

        for (int id : vertices.keySet()) {
            if (id == startId) continue;

            System.out.print("To " + vertices.get(id) + ": ");
            if (dist.get(id) == Integer.MAX_VALUE) {
                System.out.println("NOT REACHABLE");
            } else {
                System.out.print("distance = " + dist.get(id) + ", path = ");
                printPath(startId, id, previous);
                System.out.println();
            }
        }
    }

    private void printPath(int startId, int targetId, Map<Integer, Integer> previous) {
        List<Integer> path = new ArrayList<>();
        int current = targetId;
        while (current != startId) {
            path.add(current);
            current = previous.get(current);
        }
        path.add(startId);
        Collections.reverse(path);

        for (int i = 0; i < path.size(); i++) {
            System.out.print(vertices.get(path.get(i)));
            if (i < path.size() - 1) System.out.print(" -> ");
        }
    }

    //  (BFS, DFS)
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

            for (Neighbor neighbor : adjList.get(current)) {
                int neighborId = neighbor.vertexId;
                if (!visited.contains(neighborId)) {
                    visited.add(neighborId);
                    queue.add(neighborId);
                }
            }
        }
        return order;
    }

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

        for (Neighbor neighbor : adjList.get(currentId)) {
            if (!visited.contains(neighbor.vertexId)) {
                dfsRecursive(neighbor.vertexId, visited, order);
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