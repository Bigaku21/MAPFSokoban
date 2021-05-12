package searchclient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.ArrayDeque;

public class DistanceDiscovery
{  
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
                    setToMinus(i, j, walls);
                }
                else{
                    fillNodes(walls, distances, i, j);
                }
            }
        }
    }

    /* 
    * Set all directions from it to -1
    */
    public void setToMinus(int i, int j, boolean[][] walls){
        for (int k = 0; k < walls.length; k++) {
            for (int l = 0; l < walls[0].length; l++) {
                distances[i][j][k][l] = -1;
            }
        }
    }

    /* 
    * Ugly function to use Dijstra BFS to calculate distances from each point
    */
    //boolean[][] walls, int[][][][] distances, int i, int j, int k, int l
    public void fillNodes(boolean[][] walls, int[][][][] distances, int i, int j) {
        ArrayDeque<int[]> queue = new ArrayDeque<>(65536);
        // initialize the first location in the queue with the distance of 0
        int[] tmp = new int[3];
        tmp[0] = i; // k
        tmp[1] = j; // l
        tmp[2] = 0; // the distance from i,j
        queue.addLast(tmp);
        while(!queue.isEmpty()) {
            int[] loc = queue.getFirst();
            if(walls[loc[0]][loc[1]]){
                distances[i][j][loc[0]][loc[1]] = -1;
            } else if(distances[i][j][loc[0]][loc[1]] == null){
                distances[i][j][loc[0]][loc[1]] = loc[2];
        
                // expand to the right
                if(loc[0]<distances.length-1){
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
                 if(loc[1]<distances.length-1){
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
        }
        // if there are parts that cant be accessed, set it to -1 as a wall
        for (int k = 0; k < walls.length; k++) {
            for (int l = 0; l < walls[0].length; l++) {
                if(distances[i][j][k][l] == null){
                    distances[i][j][k][l] = -1;
                }
            }
        }   
    }
}




           
