import java.util.List;

/**
 * Class for measuring BFS and DFS performance on graphs of different sizes.
 */
public class Experiment {

    /**
     * Runs both traversals on the given graph starting from vertex 0.
     */
    public void runTraversals(Graph g, boolean printOrder) {
        int start = 0;

        // BFS
        long startTime = System.nanoTime();
        List<Vertex> bfsOrder = g.bfs(start);
        long endTime = System.nanoTime();
        long bfsDuration = endTime - startTime;

        // DFS
        startTime = System.nanoTime();
        List<Vertex> dfsOrder = g.dfs(start);
        endTime = System.nanoTime();
        long dfsDuration = endTime - startTime;

        if (printOrder) {
            System.out.println("BFS Traversal Order: " + bfsOrder);
            System.out.println("DFS Traversal Order: " + dfsOrder);
        }

        System.out.printf("BFS Time: %d ns%n", bfsDuration);
        System.out.printf("DFS Time: %d ns%n", dfsDuration);
    }

    /**
     * Creates a test graph with n vertices.
     * Edges: i connected to i+1 and i+2 (if exists).
     */
    public Graph createTestGraph(int n) {
        Graph g = new Graph();
        for (int i = 0; i < n; i++) {
            g.addVertex(new Vertex(i));
        }
        for (int i = 0; i < n; i++) {
            if (i + 1 < n) g.addEdge(i, i + 1);
            if (i + 2 < n) g.addEdge(i, i + 2);
        }
        return g;
    }

    /**
     * Runs experiments for graph sizes: 10, 30, 100.
     */
    public void runMultipleTests() {
        int[] sizes = {10, 30, 100};
        System.out.println("=== Experiments: Effect of Graph Size on Performance ===");
        System.out.println("(Undirected graphs, edges: i-i+1 and i-i+2)");
        System.out.printf("%-10s %-18s %-18s %-12s%n", "Vertices", "BFS Time (ns)", "DFS Time (ns)", "Edges");
        System.out.println("--------------------------------------------------------------");

        for (int size : sizes) {
            Graph g = createTestGraph(size);
            // Warm-up
            g.bfs(0);
            g.dfs(0);

            // Measure BFS
            long start = System.nanoTime();
            g.bfs(0);
            long bfsTime = System.nanoTime() - start;

            // Measure DFS
            start = System.nanoTime();
            g.dfs(0);
            long dfsTime = System.nanoTime() - start;

            System.out.printf("%-10d %-18d %-18d %-12d%n",
                    size, bfsTime, dfsTime, g.getEdgeCount());
        }
    }

    /**
     * Prints detailed results for a single graph.
     */
    public void printResults(Graph g) {
        System.out.println("=== Detailed Output for Small Graph ===");
        g.printGraph();
        System.out.println();
        runTraversals(g, true);
    }
}