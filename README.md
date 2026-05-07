Name: Karima Mursalimova

Group: IT-2502

A. Project Overview

This project implements an **undirected graph** using an **adjacency list** data structure. The graph consists of:

- **Vertices (nodes)** – represented by unique integer IDs (V0, V1, V2, ...)
- **Edges (connections)** – undirected connections between vertices

Two fundamental graph traversal algorithms are implemented:

- **BFS (Breadth-First Search)** – explores vertices level by level, moving outward from the start vertex
- **DFS (Depth-First Search)** – explores as far as possible along each branch before backtracking

The project measures and compares the performance of both algorithms on graphs of different sizes (10, 30, and 100 vertices).

B. Class Descriptions

| Class | Description |
|-------|-------------|
| **Vertex** | Represents a graph node. Contains a private `id` field (unique integer). Provides constructor, getter, and `toString()` method. |
| **Edge** | Represents a connection between two vertices. Contains `source` and `destination` fields (both Vertex objects). Provides constructor, getters, and `toString()`. |
| **Graph** | Core graph class using **adjacency list** representation (`Map<Integer, List<Integer>>`). Methods: `addVertex()`, `addEdge()`, `printGraph()`, `bfs()`, `dfs()`. |
| **Experiment** | Handles performance testing. Methods: `runTraversals()`, `runMultipleTests()`, `createTestGraph()`, `printResults()`. |
| **Main** | Entry point – creates graphs of different sizes, runs traversals, measures execution time using `System.nanoTime()`. |
