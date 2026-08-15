package demo;

import com.google.common.graph.*;
import java.util.*;
import com.google.common.graph.*;
import java.io.File;
import com.google.common.graph.*;
import java.io.FileNotFoundException;

public class Calculations {

    /**
     *  This method will count the total number of nodes. This is also the total number of stop on our route.
     *  @param graph MutableValueGraph<String,Float>
     *  @return the number of nodes as an integer
     */
    public static int countNodes(MutableValueGraph<String,Float> graph){
        return graph.nodes().size();
    }

    
    /**
     *  This method will count the total number of edges
     *  @param graph MutableValueGraph<String,Float>
     *  @return the number of edges as an integer
     */
    public static int countEdges(MutableValueGraph<String, Float> graph){
        return graph.edges().size();
    }

    /**
     *  Returns the the maximum degree of any node. This is equivalent to the maximum number of connections at a bus stop
     *  @param graph MutableValueGraph<String,Float>
     *  @return the maximum degree of any node
     */
    public static int maxNodeDegree(MutableValueGraph<String,Float> graph){
        Set<String> allNodes = graph.nodes();
        int maxNodeDegree = 0;
        for (String element : allNodes){
            if (graph.degree(element)>maxNodeDegree){
                maxNodeDegree = graph.degree(element);
            }
        }
        return maxNodeDegree;
    }

    /**
     *  Average degree of each node, average number of transfers at each bus stop
     *  @param graph MutableValueGraph<String,Float>
     *  @return the maximum degree of any node
     */
    public static int averageNodeDegree(MutableValueGraph<String,Float> graph){
        Set<String> allNodes = graph.nodes();
        int sum = 0;
        for (String element: allNodes){
            sum += graph.degree(element);
        }
        int average = sum/allNodes.size();
        return average;
    }

    // public static ArrayList<String> BFT(MutableValueGraph<String,Integer> network, String startnode){
    //     Set<String> startnbr = network.adjacentNodes(startnode);

    //     Queue <String> queue= new ArrayDeque<String>();
    //     HashSet<String> visited = new HashSet<String>();
    //     visited.add(startnode);
    //     queue.addAll(startnbr);
    //     while (!queue.isEmpty()){
    //         String currentNode = queue.remove();// get next node
    //         visited.add(currentNode);// visit it
    //         for (String nbr:network.adjacentNodes(currentNode)){
    //             if (!visited.contains(nbr)){
    //                 visited.add(nbr);
    //                 queue.addAll(network.adjacentNodes(nbr));
    //             }
    //         }
    //     }
    //     return null;
    // }

    /**
     *  This method will create a hashset of nodes and their distance from the startnode
     *  @param graph MutableValueGraph<String,Float> startnode String
     *  @return the hashmap of nodes and their distance from the startnode
     */
    public static HashMap<String, Float> shortestPath(MutableValueGraph<String,Float> graph, String startnode){
        HashMap<String, Float> cost = new HashMap<>();
        HashMap<String, String> viaValue = new HashMap<>();
        Queue <String> queue = new ArrayDeque<String>();
        HashSet<String> visited = new HashSet<String>();


        //for each node in network.nodes, initialize the node, cost, and via values
        for (String node:graph.nodes()){
            cost.put(node, Float.MAX_VALUE);
            //initialize as null, update with strings
            viaValue.put(node, null);
        }
        //overwriting the starter node to have 0 instead of infinity
        cost.put(startnode, 0.0f);

        //in the cost hash set, update the Float with the edge cost from startnode to startnbr
        for(String startnbr:graph.adjacentNodes(startnode)){
            //because of the optional float we need to return the default value in case there is no associated edge value
            Float edgeCost = graph.edgeValueOrDefault(startnode, startnbr, Float.MAX_VALUE);
            //courtesy error message for if i forgot an edge value
            if (edgeCost == Float.MAX_VALUE){
                System.out.println("There may be a missing edge value between " + startnode + " and " + startnbr);
            }
            cost.put(startnbr, edgeCost);
            viaValue.put(startnbr, startnode);
        }

        //put startnode in visited and put the startnode neighbors in the queue
        visited.add(startnode);
        queue.addAll(graph.adjacentNodes(startnode));

        //while there are nodes in the queue
        while(!queue.isEmpty()){
            //return and remove the first node from the queue
            String currentNode = queue.poll();
            //put the current node in visited
            visited.add(currentNode);
            //for each nbr in the set of currentNode's neighbor nodes
            for(String nbr:graph.adjacentNodes(currentNode)){
                //if the node is not in visited, add the neighbors of current Node to queue 
                //!queue.contains(nbr) &&
                if(!visited.contains(nbr)){
                    queue.add(nbr);
                }

                //Initialize edgeCost 
                //using defaultvalue (optional float) in case there is no associated edge value (from stackoverflow)
                Float edgeCost = graph.edgeValueOrDefault(currentNode, nbr, Float.MAX_VALUE);
                    //courtesy error message for if i forgot an edge value
                    if (edgeCost == Float.MAX_VALUE){
                     System.out.println("There may be a missing edge value between " + currentNode + " and " + nbr);
                    }

                //if the edgeCost to the nbr via the current node is less than the already associated cost of nbr
                //update nbr's cost with currentCost and via with the currentNode
                Float currentCost = edgeCost+cost.get(currentNode);
                if(currentCost < cost.get(nbr)){
                    cost.put(nbr, currentCost);
                    viaValue.put(nbr, currentNode);
                }
            }
        }
        return cost;
    }


    /**
     *  This method will create a hashset of nodes and the node via which they are accessible
     *  @param graph MutableValueGraph<String,Float> startnode String
     *  @return the hashmap of nodes and the node via which they are accessible for the shortest distance
     */
    public static HashMap<String, String> shortestPathVia(MutableValueGraph<String,Float> graph, String startnode){
        HashMap<String, Float> cost = new HashMap<>();
        HashMap<String, String> viaValue = new HashMap<>();
        Queue <String> queue = new ArrayDeque<String>();
        HashSet<String> visited = new HashSet<String>();


        //for each node in network.nodes, initialize the node, cost, and via values
        for (String node:graph.nodes()){
            cost.put(node, Float.MAX_VALUE);
            //initialize as null, update with strings
            viaValue.put(node, null);
        }
        //overwriting the starter node to have 0 instead of infinity
        cost.put(startnode, 0.0f);

        //in the cost hash set, update the Float with the edge cost from startnode to startnbr
        for(String startnbr:graph.adjacentNodes(startnode)){
            //because of the optional float we need to return the default value in case there is no associated edge value
            Float edgeCost = graph.edgeValueOrDefault(startnode, startnbr, Float.MAX_VALUE);
            //courtesy error message for if i forgot an edge value
            if (edgeCost == Float.MAX_VALUE){
                System.out.println("There may be a missing edge value between " + startnode + " and " + startnbr);
            }
            cost.put(startnbr, edgeCost);
            viaValue.put(startnbr, startnode);
        }

        //put startnode in visited and put the startnode neighbors in the queue
        visited.add(startnode);
        queue.addAll(graph.adjacentNodes(startnode));

        //while there are nodes in the queue
        while(!queue.isEmpty()){
            //return and remove the first node from the queue
            String currentNode = queue.poll();
            //put the current node in visited
            visited.add(currentNode);
            //for each nbr in the set of currentNode's neighbor nodes
            for(String nbr:graph.adjacentNodes(currentNode)){
                //if the node is not in visited, add the neighbors of current Node to queue 
                //!queue.contains(nbr) &&
                if(!visited.contains(nbr)){
                    queue.add(nbr);
                }

                //Initialize edgeCost 
                //using defaultvalue (optional float) in case there is no associated edge value (from stackoverflow)
                Float edgeCost = graph.edgeValueOrDefault(currentNode, nbr, Float.MAX_VALUE);
                    //courtesy error message for if i forgot an edge value
                    if (edgeCost == Float.MAX_VALUE){
                     System.out.println("There may be a missing edge value between " + currentNode + " and " + nbr);
                    }

                //if the edgeCost to the nbr via the current node is less than the already associated cost of nbr
                //update nbr's cost with currentCost and via with the currentNode
                Float currentCost = edgeCost+cost.get(currentNode);
                if(currentCost < cost.get(nbr)){
                    cost.put(nbr, currentCost);
                    viaValue.put(nbr, currentNode);
                }
            }
        }
        return viaValue;
    }

}
