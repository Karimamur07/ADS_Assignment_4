public class Main {
    public static void main(String[] args) {
        Experiment exp = new Experiment();


        System.out.println("=".repeat(70));
        Graph smallGraph = exp.createTestGraph(10);
        exp.printResults(smallGraph);

        System.out.println("\n" + "=".repeat(70) + "\n");


        exp.runMultipleTests();
    }
}