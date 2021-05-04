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
    public static ArrayList<String> parseLevel (BufferedReader serverMessages)
    throws IOException
    {   
        System.err.println("ddd");
        ArrayList<String> list = new ArrayList<String>();
        // We can assume that the level file is conforming to specification, since the server verifies this.
        // Read domain
        list.add(serverMessages.readLine()); // #domain
        list.add(serverMessages.readLine()); // hospital
        
        // Read Level name
        list.add(serverMessages.readLine()); // #levelname
        list.add(serverMessages.readLine()); // <name>

        // Read colors
        list.add(serverMessages.readLine());; // #colors
        String line = serverMessages.readLine();
        while (!line.startsWith("#")){
            list.add(line);
            line = serverMessages.readLine();     
        }
        line = serverMessages.readLine();
        while (!line.startsWith("#")){
                list.add(line);
                line = serverMessages.readLine();
        }
      
        line = serverMessages.readLine();
        
        while (!line.startsWith("#"))
        {
            list.add(line);
            line = serverMessages.readLine();
        }
        return list;
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
        ArrayList<String> list = SearchClient.parseLevel(serverMessages);
        
        System.err.println(list.size());
        for (String string : list) {
            System.err.println(string);
        }
    }
}
