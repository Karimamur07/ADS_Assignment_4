public class Main {
    public static void main(String[] args) {
        Experiment exp = new Experiment();

        // 1) Create small graph (10 vertices) and print details
        System.out.println("=".repeat(70));
        Graph smallGraph = exp.createTestGraph(10);
        exp.printResults(smallGraph);

        System.out.println("\n" + "=".repeat(70) + "\n");

        // 2) Run multiple tests for different sizes
        exp.runMultipleTests();
    }
}