package WEEK8;

import java.util.Arrays;

public class DistanceVectorRouting{
    public static final int INF = 999;

    public static class Routers{
        int id;
        int [] distance;
        int [] nextHop;
        Routers(int id,int n){
            this.id = id;
            this.distance = new int[n];
            this.nextHop = new int[n];
            Arrays.fill(distance,INF);
            Arrays.fill(nextHop,-1);
            this.distance[id] = 0;
            this.nextHop[id] = id;
        }
    }

    public static void main(String[] args) {
        int n=4;
        int[][] graph = {
                {0, 1, 3, INF},
                {1, 0, 1, 4},
                {3, 1, 0, 1},
                {INF, 4, 1, 0}
        };
        Routers [] router = new Routers[n];

        for (int i = 0; i <n ; i++) {
            router[i] = new Routers(i,n);
            for (int j = 0; j <n ; j++) {
                if(graph[i][j] != INF && i != j){
                    router[i].distance[j] = graph[i][j];
                    router[i].nextHop[j] = j;
                }
            }
        }

        boolean updated;
        
        do{
            updated = false;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j <n ; j++) {
                    for (int k = 0; k <n; k++) {
                        if(router[i].distance[j] > router[i].distance[k] + router[k].distance[j]){
                            router[i].distance[j] = router[i].distance[k] + router[k].distance[j];
                            router[i].nextHop[j] =  router[i].nextHop[k] ;

                            updated = true;
                        }
                    }
                }
            }
        } while (updated);


        System.out.println("Distance Vector Routing Tables:");
        for (Routers route : router) {
            System.out.println("Router " + route.id + ":");
            for (int i = 0; i < n; i++) {
                System.out.println("To " + i + " via " + route.nextHop[i] + " cost: " + route.distance[i]);
            }
            System.out.println();
        }
    }
}