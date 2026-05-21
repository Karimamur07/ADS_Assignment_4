public class Experiment {

    public Graph createWeightedTestGraph() {
        Graph g = new Graph();


        for (int i = 0; i <= 5; i++) {
            g.addVertex(new Vertex(i));
        }

        g.addEdge(0, 1, 4);
        g.addEdge(0, 2, 2);
        g.addEdge(1, 2, 1);
        g.addEdge(1, 3, 5);
        g.addEdge(2, 3, 8);
        g.addEdge(2, 4, 10);
        g.addEdge(3, 4, 2);
        g.addEdge(3, 5, 6);
        g.addEdge(4, 5, 3);

        return g;
    }


    public void testDijkstra() {
        System.out.println("=== Dijkstra's Algorithm Test ===\n");

        Graph g = createWeightedTestGraph();
        g.printGraph();

        g.dijkstra(0);

        System.out.println("\n" + "=".repeat(50));

        g.dijkstra(3);
    }


    public void runTraversals(Graph g, boolean printOrder) {
        int start = 0;

        long startTime = System.nanoTime();
        g.bfs(start);
        long bfsDuration = System.nanoTime() - startTime;

        startTime = System.nanoTime();
        g.dfs(start);
        long dfsDuration = System.nanoTime() - startTime;

        if (printOrder) {
            System.out.println("BFS Traversal Order: " + g.bfs(start));
            System.out.println("DFS Traversal Order: " + g.dfs(start));
        }

        System.out.printf("BFS Time: %d ns%n", bfsDuration);
        System.out.printf("DFS Time: %d ns%n", dfsDuration);
    }

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

    public void runMultipleTests() {
        int[] sizes = {10, 30, 100};
        System.out.println("=== Experiments: Effect of Graph Size on Performance ===");
        System.out.println("(Undirected graphs, edges: i-i+1 and i-i+2)");
        System.out.printf("%-10s %-18s %-18s %-12s%n", "Vertices", "BFS Time (ns)", "DFS Time (ns)", "Edges");
        System.out.println("--------------------------------------------------------------------");

        for (int size : sizes) {
            Graph g = createTestGraph(size);
            g.bfs(0);
            g.dfs(0);

            long start = System.nanoTime();
            g.bfs(0);
            long bfsTime = System.nanoTime() - start;

            start = System.nanoTime();
            g.dfs(0);
            long dfsTime = System.nanoTime() - start;

            System.out.printf("%-10d %-18d %-18d %-12d%n", size, bfsTime, dfsTime, g.getEdgeCount());
        }
    }

    public void printResults(Graph g) {
        System.out.println("=== Detailed Output for Small Graph ===");
        g.printGraph();
        System.out.println();
        runTraversals(g, true);
    }
}