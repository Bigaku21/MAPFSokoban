package searchclient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
// import java.lang.Math.*;
import java.util.ArrayDeque;

public class State
{
    Color[]     agentColors; 
    int[][]     agentPos;    
    int[][]     agentGoalPos; 
    int         numAgents;  
    Color[]     boxColors;   
    int[][]     boxPos;      
    int[][]     boxGoalPos;  
    int         numBoxes;    
    boolean[][] walls;      

    private final int g;
    private int hash = 0;


    // Constructs an initial state.
    // Arguments are not copied, and therefore should not be modified after being passed in.
    public State(Color[] agentColors, int[][] agentPos, int[][] agentGoalPos, int numAgents, Color[] boxColors, int[][] boxPos, int[][] boxGoalPos, int numBoxes, boolean[][] walls)
    {
        this.agentColors  = agentColors;    
        this.agentPos     = agentPos;         
        this.agentGoalPos = agentGoalPos;  
        this.numAgents    = numAgents;    
        this.boxColors    = boxColors;        
        this.boxPos       = boxPos;         
        this.boxGoalPos   = boxGoalPos;     
        this.numBoxes     = numBoxes;         
        this.walls        = walls;            
        this.g            = 0;
        this.hash         = 0;
    }
    public int[][][][] findShortestPaths(boolean[][] walls) {
        int[][][][] distances = new int[walls.length][walls[0].length][walls.length][walls[0].length]; 
        for (int i = 0; i < walls.length; i++) {
            for (int j = 0; j < walls[0].length; j++) {
                if(walls[i][j]){
                    for (int k = 0; k < walls.length; k++) {
                        for (int l = 0; l < walls[0].length; l++) {
                            distances[i][j][k][l] = -1;
                        }
                    }
                }
                else{

                }
            }
        }
        return distances;
    }
    public void RecursiveSearch(boolean[][] walls, int[][][][] distances, int i, int j, int k, int l) {
        private ArrayDeque<int[]> stack = new ArrayDeque<>(65536);
        // Move right 
        if(k-1<distances.length){
            if(distances[i][j][k+1][l]  != -1 && distances[i][j][k+1][l]  == null){
                distances[i][j][k+1][l] = distances[i][j][k][l]+1;
                RecursiveSearch(walls, distances, i, j, k+1, l);
            }
        }
        // Move left
        if(k>0){
            if(distances[i][j][k-1][l]  != -1 && distances[i][j][k+1][l]  == null){
                distances[i][j][k-1][l] = distances[i][j][k][l]+1;
                RecursiveSearch(walls, distances, i, j, k-1, l);
            }
        }
        // Move down
        if(l-1<distances[0].length){
            if(distances[i][j][k][l+1]  != -1 && distances[i][j][k+1][l]  == null){
                distances[i][j][k][l+1] = distances[i][j][k][l]+1;
            }
        }
        // Move up
        if(l>0){
            if(distances[i][j][k][l-1]  != -1 && distances[i][j][k+1][l]  == null){
                distances[i][j][k][l-1] = distances[i][j][k][l]+1;
            }
        }
    }

    public void setValues(){
        
    }
}
