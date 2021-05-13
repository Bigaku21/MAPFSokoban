package searchclient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.ArrayDeque;
import java.util.HashSet;

public class DistanceDiscovery {  
    private boolean[][] walls;      
    private int[][][][] distances;
    

    /*
    * Constructs an the distance 4d array where distance[i][j][k][l] holds 
    * a length of the path from point i,j to point k,l.
    */
    public DistanceDiscovery(boolean[][] walls)
    {        
        this.walls        = walls;            
        this.distances    = new int[walls.length][walls[0].length][walls.length][walls[0].length];
        findShortestPaths(walls, distances);
    }


    /* 
    * If the current cell is a wall, set all directions from it to -1,
    * otherwise call the fill nodes to fill all distances from i, j
    */
    public void findShortestPaths(boolean[][] walls, int[][][][] distances) {
        for (int i = 0; i < walls.length; i++) {
            for (int j = 0; j < walls[0].length; j++) {
                if(walls[i][j]){
                    setToMinus(walls, i, j);
                }
                else{
                    fillNodes(walls, distances, i, j);
                }
            }
        }
        System.err.println(walls.length);
        System.err.println(walls[0].length);
    }

    /* 
    * Set all directions from it to -1. Used when i, j is a wall
    */
    public void setToMinus(boolean[][] walls, int i, int j){
        for (int k = 0; k < walls.length; k++) {
            for (int l = 0; l < walls[0].length; l++) {
                distances[i][j][k][l] = -1;
            }
        }
    }

    /* 
    * Function to use Dijstra BFS to calculate distances from i,j to each point
    */
    public void fillNodes(boolean[][] walls, int[][][][] distances, int i, int j) {
        ArrayDeque<int[]> queue = new ArrayDeque<>(65536);
        // initialize the first location in the queue with the distance of 0
        
        int[] tmp = new int[3];
        
        tmp[0] = i; // k
        tmp[1] = j; // l
        tmp[2] = 0; // the distance from i,j
        //printSearchStatus();
        queue.addLast(tmp);
        while(!queue.isEmpty()) {
            int[] loc = queue.poll();
            if(walls[loc[0]][loc[1]]){
                distances[i][j][loc[0]][loc[1]] = -1;
            } else if(distances[i][j][loc[0]][loc[1]] == 0){ // && !(i==loc[0]&&j==loc[1]&&loc[2]==0)
                distances[i][j][loc[0]][loc[1]] = loc[2];
                
                // expand to the right
                if(loc[0] < distances.length-1){
                    int[] toQueue = loc.clone();
                    toQueue[0] = loc[0]+1; // k + 1
                    toQueue[2] = loc[2]+1; // add the to the distance from i,j to this point
                    queue.addLast(toQueue);
                }
                // expand to the left
                if(loc[0]>0){
                    int[] toQueue = loc.clone();
                    toQueue[0] = loc[0]-1; // k - 1
                    toQueue[2] = loc[2]+1; // add the to the distance from i,j to this point
                    queue.addLast(toQueue);
                }
                // expand down
                 if(loc[1]<distances[0].length){
                    int[] toQueue = loc.clone();
                    toQueue[1] = loc[1]+1; // l + 1
                    toQueue[2] = loc[2]+1; // add the to the distance from i,j to this point
                    queue.addLast(toQueue);
                }
                // expand up
                if(loc[1]>0){
                    int[] toQueue = loc.clone();
                    toQueue[1] = loc[1]-1; // l - 1
                    toQueue[2] = loc[2]+1; // add the to the distance from i,j to this point
                    queue.addLast(toQueue);
                }
            }
            distances[i][j][i][j] = 0;
        }   
    }

    public int getDistances(int x1, int y1, int x2, int y2) {
        return this.distances[y1][x1][y2][x2];
    }

    private static long startTime = System.nanoTime();
    
    private static void printSearchStatus()
    {
        String statusTemplate = "#Time: %3.3f s\n%s\n";
        double elapsedTime = (System.nanoTime() - startTime) / 1_000_000_000d;
        System.err.format(statusTemplate, elapsedTime, Memory.stringRep());
    }

    public void testPtint(int i, int j){
        System.err.println();
        System.err.println("Distances from "+ i+", "+ j);
        for (int j2 = 0; j2 < distances[i][j].length; j2++) {
            for (int k = 0; k < distances[i][j][0].length; k++) {
                if(distances[i][j][j2][k]>-1)
                   System.err.print(" ");
                System.err.print(" ");
                if(distances[i][j][j2][k]<10)
                   System.err.print(" ");
                System.err.print(distances[i][j][j2][k]);
            }
            System.err.println();
            System.err.println();
        }
        System.err.println("Walls");
        for (int j2 = 0; j2 < walls.length; j2++) {
            for (int k = 0; k < walls[0].length; k++) {
                if(walls[j2][k])
                    System.err.print("+");
                else
                    System.err.print(" ");
            }
            System.err.println();
        }
        System.err.println();
        
    }

}




           
