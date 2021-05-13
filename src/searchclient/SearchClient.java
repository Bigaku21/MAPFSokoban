package searchclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;

public class SearchClient
{
    // public static <T> int getLength(T[] arr){
    //     int count = 0;
    //     for(T el : arr)
    //         if (el != null)
    //             ++count;
    //     return count;
    // }

    public static State parseLevel(BufferedReader serverMessages)
    throws IOException
    {
        serverMessages.readLine(); // #domain
        serverMessages.readLine(); // hospital

        // Read Level name
        serverMessages.readLine(); // #levelname
        serverMessages.readLine(); // <name>

        // Read colors
        serverMessages.readLine(); // #colors
        Color[] agentColors = new Color[10];
        Color[] boxColors = new Color[26];
        String line = serverMessages.readLine();
        while (!line.startsWith("#"))
        {
            String[] split = line.split(":");
            Color color = Color.fromString(split[0].strip());
            String[] entities = split[1].split(",");
            for (String entity : entities)
            {
                char c = entity.strip().charAt(0);
                if ('0' <= c && c <= '9')
                {
                    agentColors[c - '0'] = color;
                }
                else if ('A' <= c && c <= 'Z')
                {
                    boxColors[c - 'A'] = color;
                }
            }
            line = serverMessages.readLine();
        }

        // Read initial state
        // line is currently "#initial"
        int numRows = 0;
        int numCols = 0;
        ArrayList<String> levelLines = new ArrayList<>(64);
        line = serverMessages.readLine();
        while (!line.startsWith("#"))
            {
                levelLines.add(line);
                numCols = Math.max(numCols, line.length());
                ++numRows;
                line = serverMessages.readLine();
        }
        int numAgents = 0;
        int numBoxes  = 0;
        int[][] agentPos  = new int[10][2];
        int[][] boxPos    = new int[26][2];
        boolean[][] walls = new boolean[numRows][numCols];
        for (int row = 0; row < numRows; ++row)
        {
            line = levelLines.get(row);
            for (int col = 0; col < line.length(); ++col)
            {
                char c = line.charAt(col);

                if ('0' <= c && c <= '9')
                {
                    agentPos[c - '0'][0] = row;
                    agentPos[c - '0'][1] = col;
                    ++numAgents;
                }
                else if ('A' <= c && c <= 'Z')
                {
                    boxPos[c - 'A'][0] = row;
                    boxPos[c - 'A'][1] = col;
                    ++numBoxes;
                }
                else if (c == '+')
                {
                    walls[row][col] = true;
                }
            }
        }
        agentPos = Arrays.copyOf(agentPos, numAgents);
        boxPos   = Arrays.copyOf(boxPos, numBoxes);

        // Read goal state
        // line is currently "#goal"
        int[][] agentGoalPos = new int[numAgents][2];
        int[][] boxGoalPos = new int[numBoxes][2];
        line = serverMessages.readLine();
        int row = 0;
        while (!line.startsWith("#"))
        {
            for (int col = 0; col < line.length(); ++col)
            {
                char c = line.charAt(col);

                if (('0' <= c && c <= '9'))
                {
                    System.err.println(c - '0');
                    agentGoalPos[c - '0'][0] = row;
                    agentGoalPos[c - '0'][1] = col;
                }
                else if ('A' <= c && c <= 'Z')
                {
                    boxGoalPos[c - 'A'][0] = row;
                    boxGoalPos[c - 'A'][1] = col;
                }
            }
            ++row;
            line = serverMessages.readLine();
        }
        DistanceDiscovery distances = new DistanceDiscovery(walls);
        distances.testPtint(1,1);
        // recreate an optimal state     
        return new State(agentColors, agentPos, agentGoalPos, numAgents, boxColors, boxPos, boxGoalPos, numBoxes, walls, distances);
    }

    public static void main(String[] args)
    throws IOException
    {
        // Use stderr to print to the console.
        System.err.println("SearchClient initializing. I am sending this using the error output stream.");

        // Send client name to server.
        System.out.println("SearchClient");

        // We can also print comments to stdout by prefixing with a #.
        System.out.println("#This is a comment.");

        BufferedReader serverMessages = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.US_ASCII));
        State initState = SearchClient.parseLevel(serverMessages);
        initState.printOutState();
        System.err.println(initState.distance.getDistances(2, 3, 3, 5));
        
    }
}
