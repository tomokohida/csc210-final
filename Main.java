package demo;

import java.util.*;
import java.io.File;
import com.google.common.graph.*;
import java.io.FileNotFoundException;
import java.awt.Color;

public class Main {

    /**
     *  This method reads in a file of the specified format and returns it as a tree
     *  @param String file name
     *  @return MutableValueGraph<String,Float>
     */
    public static MutableValueGraph<String,Float> filereading(String filename){
        MutableValueGraph<String,Float> network = ValueGraphBuilder.undirected().build();
        //read number of lines and have it be int n
        Scanner file = null;
        try {
          file = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
          System.err.println("Cannot locate file.");
          System.exit(-1);
        }
        //while file has next line and is the first line, specify what the values mean
        //repeat the file next line reading for the subsequent node lines (as specified by the first line)
        //repeat the file next line reading for the subsequent edge lines (as specified by the first line)
        String line1 = file.nextLine();
        // System.out.println(line1);
        String[] problem = line1.split("\t");
        // System.out.println(problem[0]);
        // System.out.println(problem[1]);
        // System.out.println(problem[2]);
 
        int numNodes = Integer.parseInt(problem [1]);
        int numEdges = Integer.parseInt(problem[2]);

        for (int i = 0; i <numNodes; i++ ){
            String line2 = file.nextLine();
            // System.out.println(line2);
            String[] nodeFields = line2.split("\t");
            String name = (nodeFields[1]);
            // This should initialize a new node with the data but I created a node class bc I didn't know how else to do this and 
            // is interpreting my class as a string
            network.addNode(name);
        }
        for (int i = 0; i <numEdges; i++ ){
            String line3 = file.nextLine();
            String[] edgeFields = line3.split("\t");
            Float  value = Float.parseFloat(edgeFields[3]);
            String firstNode = edgeFields[1];
            String secondNode = edgeFields[2];
            // Ideally this should connect two nodes with an edge but obvi this doesn't work because I'm telling it to createt an edge
            // between two integers
            // We may need to iterate through all the nodes to find the node which contains the node Indicator represented in the 
            // ints first node and second node
            network.putEdgeValue(firstNode, secondNode, value);
        }
        file.close();
        //will return a network with x nodes and y edges, with college names and distance
        return network;
    }
    
    /**
     *  This method will record the nodes for the shortest path from startnode to endnode
     *  @param graph MutableValueGraph<String,String> startStop String, endStop String, myPath LinkedList<String>
     *  @return the LinkedList<String> of the nodes that demonstrate the shortest path from starStop to endStop
     */
    public static LinkedList<String> pathFinder(HashMap<String, String> via, String startStop, String endStop, LinkedList<String> myPath){
        
        //value is the way to get to endStop
        String value = via.get(endStop);
        
        //1. if the value (way to get to endStop) is not startStop
        //2. if the new value (way to get to new endStop) is not startStop
        if (!value.equals(startStop)){
            //add the endStop to the linkedlist
            myPath.addFirst(endStop);
            //add the value to the linkedlist
            myPath.addFirst(value);
            //run pathfinder again upon the startstop and the value as the new endStop
            pathFinder(via, startStop, value, myPath);
        } else {
            //if the new value (way to get to new endStop) is startStop
            //add the new value to the linkedlist (which will be the startStop)
            myPath.addFirst(value);
        }
        //return the entire path
        return myPath;
    }

 
    public static void main(String[] args) {
        String filename ="app/data/Busroute2 - Sheet1.tsv";
        //String filename ="app/src/main/java/demo/busroute_data_sheet1.tsv";
        MutableValueGraph<String,Float> busroute = filereading(filename);
        GraphDisplay d3 = new GraphDisplay(busroute);
        d3.setColor("Smith",Color.BLUE);
        d3.setColor("Umass",Color.RED);
        d3.setColor("Amherst",Color.ORANGE);
        d3.setColor("Moho",Color.YELLOW);
        d3.setColor("Hampshire",Color.GREEN);
        d3.setColor("Smith College",Color.BLUE);
        d3.setColor("UMass Visitors Center",Color.RED);
        d3.setColor("Amherst College",Color.ORANGE);
        d3.setColor("Mount Holyoke College / Blanchard Hall",Color.YELLOW);
        d3.setColor("Hampshire College",Color.GREEN);

        System.out.println("Welcome to our busroute machine! We have data on the B43, 38, and 39E bus routes. What is your starting location?");
        Scanner input = new Scanner(System.in);
        String userStartStop = input.nextLine();
    
        Set<String> allNodes = busroute.nodes();
        System.out.println(allNodes);
        
        //while current via is not userInput, 
        System.out.println("Your starting location is: " + userStartStop);
        boolean containsUserStartStop = allNodes.contains(userStartStop);

        HashMap<String, Float> costAllStops = Calculations.shortestPath(busroute, userStartStop);
        System.out.println("Please specify your end stop.");
        String userEndStop = input.nextLine();
        boolean containsUserEndStop = busroute.nodes().contains(userEndStop);
        if(containsUserEndStop){
            System.out.println("You have entered " + userEndStop + " as your end location!" + " The shortest distance to " + userEndStop + " is: " + costAllStops.get(userEndStop) + " miles.");
        }

        System.out.println("The following demonstrates the path to your end stop.");
        LinkedList<String> myPath = new LinkedList<String>();
        System.out.println(pathFinder(Calculations.shortestPathVia(busroute, userStartStop), userStartStop, userEndStop, myPath));
        
        System.out.println("Operation complete!");    
        System.out.println("Total Number of Bus Stops: " + Calculations.countNodes(busroute));
        System.out.println("Total Number of Paths: " + Calculations.countEdges(busroute));
        System.out.println("Max Number of Transfers: " + Calculations.maxNodeDegree(busroute));
        System.out.println("Average Number of Transfers: " + Calculations.averageNodeDegree(busroute));


    }

    
    
}
