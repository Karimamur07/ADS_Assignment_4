Name: Karima Mursalimova

Group: IT-2502

A. Project Overview

This project implements an undirected graph using an adjacency list data structure. The graph consists of:

- Vertices (nodes) – represented by unique integer IDs (V0, V1, V2, ...)
- Edges (connections) – undirected connections between vertices

Two fundamental graph traversal algorithms are implemented:

- BFS (Breadth-First Search)– explores vertices level by level, moving outward from the start vertex
- DFS (Depth-First Search) – explores as far as possible along each branch before backtracking

The project measures and compares the performance of both algorithms on graphs of different sizes (10, 30, and 100 vertices).

B. Class Descriptions

Vertex class: Represents a graph node. Contains a private id field (unique integer). Provides constructor, getter, and toString method.

Edge class: Represents a connection between two vertices. Contains source and destination fields (both Vertex objects). Provides constructor, getters, and toString.

Graph class: Core graph class using adjacency list representation (Map<Integer, List<Integer>>). Methods include addVertex, addEdge, printGraph, bfs, and dfs.

Experiment class: Handles performance testing. Methods include runTraversals, runMultipleTests, createTestGraph, and printResults.

Main class: Entry point – creates graphs of different sizes, runs traversals, measures execution time using System.nanoTime.

Adjacency List Representation:

The graph stores for each vertex a list of its neighboring vertices. Example:

V0 -> [1, 2]
V1 -> [0, 2, 3]
V2 -> [1, 0, 3, 4]

Advantages of adjacency list: efficient memory usage (O(V+E) space), fast iteration over neighbors (O(degree) time), and easy to add or remove vertices and edges.


C. Algorithm Descriptions

BFS (Breadth-First Search)

Step-by-step explanation:

1. Create a queue and a visited set
2. Add the start vertex to the queue and mark it as visited
3. While the queue is not empty:
   - Remove a vertex from the front of the queue
   - Process the vertex (add to traversal order)
   - For each unvisited neighbor of this vertex:
     - Mark it as visited
     - Add it to the back of the queue
4. Repeat until the queue is empty

Use cases:
- Finding the shortest path in unweighted graphs
- Web crawling (processing pages level by level)
- Social network friend suggestions (finding people at distance N)
- Checking if a graph is bipartite

Time Complexity: O(V + E) – each vertex and edge is processed once.



DFS (Depth-First Search)

Step-by-step explanation:

1. Mark the current vertex as visited
2. Process the vertex (add to traversal order)
3. For each neighbor of the current vertex:
   - If the neighbor has not been visited:
     - Recursively call DFS on that neighbor
4. When no more unvisited neighbors exist, backtrack

Use cases:
- Detecting cycles in a graph
- Topological sorting (for directed acyclic graphs)
- Solving mazes and puzzles
- Finding connected components
- Generating mazes

Time Complexity: O(V + E) – each vertex and edge is processed once.


D. Experimental Results

### Execution Time Comparison Table

| Vertices | BFS Time (ns) | DFS Time (ns) | Number of Edges |
|----------|---------------|---------------|-----------------|
| 10       | 625,500       | 40,100        | 17              |
| 30       | 111,400       | 80,400        | 57              |
| 100      | 5,014,500     | 2,566,100     | 197             |

Observations and Patterns

| Observation | Analysis |
|-------------|----------|
| DFS is consistently faster | For all graph sizes, DFS outperforms BFS. The difference ranges from ~1.4x to ~15x faster. |
| Unexpected spike at 10 vertices for BFS | The BFS time for 10 vertices (625,500 ns) is anomalously high compared to 30 vertices (111,400 ns). This suggests a JVM warm-up issue or garbage collection interference during the first measurement. |
| Linear scaling (mostly) | From 30→100 vertices (3.3x growth), DFS time increased from 80,400 → 2,566,100 (~32x increase – not linear). This may indicate recursion overhead or memory constraints at larger sizes. |

Corrected Analysis (excluding anomaly)

If we ignore the anomalous 10-vertex BFS result (likely due to JVM warm-up), the pattern shows:

- DFS is approximately 1.5-2x faster than BFS on this graph structure
- The overhead of maintaining an explicit Queue (LinkedList) makes BFS slower than recursive DFS in Java

Why is DFS faster in these experiments?

1. Queue overhead – BFS uses LinkedList as a queue, which involves more object allocations
2. Recursion advantage – DFS uses the native call stack (faster than heap-allocated queue)
3. Graph structure – The graph is chain-like (i-i+1 and i-i+2), which favors DFS deep traversal

E. Screenshots

1.Figure 1: Graph Structure (Adjacency List)

<img width="411" height="276" alt="image" src="https://github.com/user-attachments/assets/8431dd2e-7e92-4e99-bc7a-3fa1d7024e3f" />

2.Figure 2: BFS Traversal Output

<img width="519" height="25" alt="image" src="https://github.com/user-attachments/assets/512b939f-84fb-44e6-9c8f-f667540afdff" />

3.Figure 3: DFS Traversal Output

<img width="518" height="20" alt="image" src="https://github.com/user-attachments/assets/3db773b1-b14a-4242-bfce-3b407e5323ce" />

4.Figure 4: Performance Results Table

<img width="487" height="174" alt="image" src="https://github.com/user-attachments/assets/9cbfdb17-6e10-47f4-8795-bc29dfcdecba" />

F. Reflection Section

### What I Learned About Graph Traversal

Implementing BFS and DFS gave me hands-on experience with how different data structures (queue vs. recursion stack) affect performance. The adjacency list representation proved to be both memory-efficient and easy to work with. I was surprised to see that DFS consistently outperformed BFS in my experiments – this challenged my initial assumption that both algorithms would have similar execution times. The anomaly at 10 vertices (very slow BFS) taught me about the importance of JVM warm-up and the impact of garbage collection on benchmark measurements.

### Differences Between BFS and DFS

The fundamental difference is the order of exploration. BFS expands like a wave – all neighbors first, then their neighbors. This makes it ideal for finding shortest paths but requires an explicit queue. DFS goes deep into one branch before backtracking – it's more memory-efficient (no queue needed) but can get lost in deep graphs. In my implementation, DFS used recursion, which was faster but has the limitation of potential stack overflow on very deep graphs (thousands of vertices).

### Challenges Faced During Implementation

1. Measurement accuracy – The first BFS measurement (10 vertices) was likely affected by JVM warm-up. In real benchmarks, you should run "warm-up" traversals before measuring.

2. Recursion depth limits – Java has a default recursion limit (~10,000 calls). For very large graphs, an iterative DFS (using an explicit stack) would be safer.

3. Undirected edge handling – Adding each edge twice (both directions) was easy to forget and caused connectivity issues.

4. nanosecond precision – System.nanoTime() is precise but can be affected by OS scheduling; running multiple trials would give more accurate results.

### Answering the Required Questions

How does graph size affect BFS and DFS performance?
Both algorithms scale with O(V+E), but my results show DFS scaling less predictably due to recursion overhead. BFS shows more consistent scaling.

Which traversal is faster in your experiments?
DFS was consistently faster – approximately 1.5-2x faster on 30 and 100 vertices. The only exception was the anomalous 10-vertex BFS result.

Do results match expected complexity O(V+E)?
Partially. The scaling from 30→100 vertices for DFS (32x increase vs 3.3x growth in V+E) suggests additional overhead from recursion depth. BFS scales more closely to O(V+E).

How does graph structure affect traversal order?
On a chain-like graph, BFS processes level-by-level (V0, then V1,V2, then V3,V4...), while DFS goes deep (V0→V1→V2→...→V9). This is clearly visible in the traversal output.

When is BFS preferred over DFS?
BFS is preferred when finding the shortest path (in unweighted graphs), when the graph has a "shallow" structure, or when you need level-order information (e.g., social networks).

What are the limitations of DFS?
Recursive DFS can cause stack overflow on deep graphs. Also, DFS does NOT find shortest paths – it finds any path, not necessarily the optimal one.



## Bonus Task: Dijkstra's Algorithm (Shortest Path)

### Task Description
Implement Dijkstra's Algorithm to find the shortest path from a starting vertex to all other vertices in a **weighted graph**.

#### Data Structures Used
- **Adjacency List with weights**: `Map<Integer, List<Neighbor>>` where `Neighbor` stores `(vertexId, weight)`
- **Distance array**: `Map<Integer, Integer>` for storing shortest distances
- **Visited set**: `Set<Integer>` for tracking processed vertices
- **Previous map**: `Map<Integer, Integer>` for path reconstruction

#### Algorithm Steps
1. Initialize distances to `INFINITY`, starting vertex distance = 0
2. While unvisited vertices exist:
   - Select unvisited vertex with minimum distance
   - Mark it as visited
   - Relax all edges from this vertex (update distances if shorter path found)
3. Print results with distances and reconstructed paths

#### Complexity
- **Time**: O(V²) with simple array/loop (no priority queue)
- **Space**: O(V + E)

#### Files Modified
| File | Changes |
|------|---------|
| `Edge.java` | Added `weight` field and getter method |
| `Graph.java` | Added `Neighbor` inner class, `addEdge()` with weight, `dijkstra()` method, path reconstruction |
| `Experiment.java` | Added `createWeightedTestGraph()` and `testDijkstra()` |
| `Main.java` | Integrated Dijkstra test execution |


Screenshots
1.Figure 1: Output of Dijkstra's algorithm showing shortest distances and paths from vertex V0 to all other vertices in the weighted graph.
<img width="518" height="239" alt="image" src="https://github.com/user-attachments/assets/ba702fda-eacb-47ec-ad67-694970f41403" />

2.Figure 2: Adjacency list representation of the weighted graph, displaying each vertex connected to its neighbors with corresponding edge weights.
<img width="304" height="210" alt="image" src="https://github.com/user-attachments/assets/9875413f-880b-479d-888f-c76d399263e2" />

3.Figure 3: Dijkstra's algorithm execution starting from vertex V3, confirming correct functionality for any source vertex in the graph.
<img width="486" height="205" alt="image" src="https://github.com/user-attachments/assets/9e6ff703-74e2-43ec-a06f-45bf3a50d5dd" />

