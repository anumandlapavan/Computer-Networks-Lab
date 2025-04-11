package WEEK3;

import java.util.*;

class Edge implements Comparable<Edge>{
    char src;
    char des;

    int weight;

    Edge(char src,char des, int weight){
        this.des = des;
        this.weight = weight;
        this.src = src;
    }


    @Override
    public int compareTo(Edge other) {
        return this.weight - other.weight;
    }
}

class Graph{
    List<Edge> edges;
    int V;

    Graph(int v){
        V= v;
        edges = new ArrayList<>();
    }

    public void addEdge(char src,char des, int weight){
        edges.add(new Edge(src,des,weight));
    }

    public int find(int [] parent, int i){
        if(parent[i] != i){
            parent[i] = find(parent,parent[i]);
        }
        return parent[i];
    }

    public void union(int [] parent, int [] rank, int x, int y){
        int xroot = find(parent,x);
        int yroot = find(parent,y);

        if(rank[xroot] < rank[yroot]){
            parent[xroot] = yroot;
        }
        else if(rank[xroot] > rank[yroot]){
            parent[yroot] = xroot;
        }
        else{
            parent[yroot] = xroot;
            rank[xroot]++;
        }
    }

    public void find_msp(){
        Collections.sort(edges);
        int [] parent = new int [V];
        int [] rank = new int[V];

        for (int i = 0; i <V ; i++) {
            parent[i] = i;
            rank[i] = 0;
        }

        List<Edge> result = new ArrayList<>();

        for (Edge edge : edges){
            int x = find(parent, edge.src - 'a');
            int y = find(parent, edge.des - 'a');

            if(x!=y){
                result.add(edge);
                union(parent,rank,x,y);
            }
        }

        for(Edge e : result){
            System.out.println(e.src + " - " + e.des + " : " + e.weight);
        }
    }

    public void find_spt(char src){
        Map<Character,Integer> map = new HashMap<>();
        for(char v = 'a'; v<'a'+V; v++){
            map.put(v,Integer.MAX_VALUE);
        }

        map.put(src,0);

        PriorityQueue<Edge> pq = new PriorityQueue<>();
        pq.add(new Edge(src,src,0));

        while(!pq.isEmpty()){
            Edge curr = pq.poll();
            for (Edge e : edges){
                if(e.src == curr.des && map.get(e.des) > map.get(curr.des)+ e.weight){
                    map.put(e.des,map.get(curr.des)+ e.weight);
                    pq.add(new Edge(e.des, e.des, map.get(e.des)));
                }
            }
        }
        for (char v = 'a'; v < 'a' + V; v++)
            System.out.println(src + " -> " + v + " : " + map.get(v));
    }
}

public class MSP_SPT {
    public static void main(String[] args) {
        Graph g = new Graph(5); // 'a' to 'e'

        g.addEdge('a', 'b', 1);
        g.addEdge('a', 'c', 3);
        g.addEdge('b', 'c', 1);
        g.addEdge('b', 'd', 4);
        g.addEdge('c', 'd', 1);
        g.addEdge('d', 'e', 2);
        g.addEdge('c', 'e', 5);

        System.out.println("Edges in the Minimum Spanning Tree:");
//        g.find_msp();

        g.find_spt('a');
    }
}