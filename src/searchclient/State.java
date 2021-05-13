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
    DistanceDiscovery distance;     

    private final int g;
    private int hash = 0;


    // Constructs an initial state.
    // Arguments are not copied, and therefore should not be modified after being passed in.
    public State(Color[] agentColors, int[][] agentPos, int[][] agentGoalPos, int numAgents, Color[] boxColors, int[][] boxPos, int[][] boxGoalPos, int numBoxes, boolean[][] walls, DistanceDiscovery distance)
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
        this.distance     = distance;
        this.g            = 0;
        this.hash         = 0;
    }
    public void printOutState() {
        for (int i = 0; i < walls.length; i++) {
            for (int j = 0; j < walls[0].length; j++) {
                if(walls[i][j])
                    System.err.print("+");
                else {
                    char c = ' ';
                    for (int j2 = 0; j2 < agentPos.length; j2++) {
                        if(agentPos[j2][0] == i && agentPos[j2][1] == j){
                            c =  (char)('0' + j2);
                        }
                    }
                    for (int j2 = 0; j2 < boxPos.length; j2++) {
                        if(boxPos[j2][0] == i && boxPos[j2][1] == j){
                            c = (char)('A' + j2);
                        }
                    }
                    System.err.print(c);
                }
            }
            System.err.println();
        }
    }
    
}
