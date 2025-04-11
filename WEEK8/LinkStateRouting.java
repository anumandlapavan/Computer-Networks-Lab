package WEEK8;

import java.util.*;

import java.util.*;

class LinkStateRouting {
    static final int INF = 999;

    public static void dijkstra(int[][] graph, int src) {
        int n = graph.length;
        int [] dist = new int[n];
        boolean [] visited = new boolean[n];
        int [] parent = new int[n];

        Arrays.fill(dist,INF);
        Arrays.fill(parent,-1);
        dist[src] = 0;

        for(int i =0;i<n;i++){
            int u = findMinVertex(dist,visited);
            visited[u] = true;

            for (int v = 0; v < n; v++) {
                if (graph[u][v] != INF && !visited[v] && dist[u] + graph[u][v] < dist[v]) {
                    dist[v] = dist[u] + graph[u][v];
                    parent[v] = u;
                }
            }
        }
        System.out.println("Link State Routing Table for Router " + src + ":");
        for (int i = 0; i < n; i++) {
            System.out.print("To " + i + " cost: " + dist[i] + " Path: ");
            printPath(parent, i);
            System.out.println();
        }
    }

    private static int findMinVertex(int[] dist, boolean[] visited) {
        int min = INF;
        int vertex = -1;
        for(int i=0;i< dist.length;i++){
            if(!visited[i] && dist[i] < min){
                min = dist[i];
                vertex = i;
            }
        }
        return vertex;
    }

    private static void printPath(int[] parent, int v) {
        if (v == -1) return;
        printPath(parent, parent[v]);
        System.out.print(v + " ");
    }

    public static void main(String[] args) {
        int[][] graph = {
                {0, 1, 4, INF},
                {1, 0, 2, 6},
                {4, 2, 0, 3},
                {INF, 6, 3, 0}
        };

        for (int i = 0; i < graph.length; i++) {
            dijkstra(graph, i);
            System.out.println();
        }
    }
}
