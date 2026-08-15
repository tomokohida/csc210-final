CSC210 Final Project
Grace Cordova and Tomoko Hida

April 23, 2024

Ensured Gradle was working on both of our computers
Sat and read the directions together

When you turn in your work on this project, be sure to include each of the following:
Your reflection on the assignment, including your development journal. Both of these should be inside readme.md, in clearly marked sections. Also include here the list of those you consulted with and a bibliography of any web sites consulted beyond the standard javadoc pages.

April 24, 2024
	Decisions we are making regarding our data
Nodes represent locations- bus stops
Immutable- the bus route does not need to be changed by the user
Undirected- you can go either direction on any bus route
Edges are weighted with their distance
Interaction Idea: 
General stats
the number of nodes (stops)
number of edges
maximum node degree - max number of connections at one bus stop
average node degree - average number of connections at one bus stop
User Enters a Start position and Destination and program returns:
Shortest route
The shortest route is the route which takes the lowest valued edges
Distance 
Add up the edge weights of all edges along the route
Cost

In order to assign cost it would be anytime a person switches busses
We would need to assign each bus route to some identifier
Perhaps a network so edges are unique and can be treated as objects
Create arrays for each bus route containing the edges associated with each bus route
OR assign a variable to each bus route and then assign each edge one or more of these, possibly in array form
Bus schedule- tell when the next bus is coming

April 28, 2024
The readFile method is nearly complete. We were able to make good progress on calculations. We still need to write a shortestRoute method which will be tricky because it will be a recursive program. Here’s what we still need to figure out:
Why our readFile method returns a “Cannot Locate File” error
What type the sets should be in the calculations methods
Write pseudo code for our shortestRoute

April 29, 2024
We were able to complete the readFile method, it was having trouble locating the file because it was not in the correct folder. The calculations methods are complete, we had trouble using the nodes() method which takes in a tree and returns a set of nodes because we weren’t sure how to initialize it. We realized that it would be a set of type string because all of our nodes are of type string. 

May 1, 2024
Today we worked on setting up a new data file containing all of our nodes. And finding and adding the values of the edges. This took a long time.

May 2, 2024
While demoing, we realized that we had some duplicate nodes and  some nodes that were not connected to everything when we ran our large data set. We went through and made sure that every line was properly tab spaced. We also had to make sure that there were no “invisible characters” as Quinn He informed us was a possibility. Basically, I fiddled with the nodes in the data file that were being unusual until they worked. We also added color to the colleges so that they were more easily identifiable.

May 7 and 8, 2024
We completed the shortestPath after coding Breadth First Traversal in the Calculations class! We then began running tests in Main to see how we might be able to use the shortestPath method within our main class.

May 9, 2024
After running the main method in Main, we continued coding the method to get the path of the shortestPath. We figured out a recursive way to add the nodes into a linked list, and were able to return a list of the path—which we then were able to print in the main method upon user input of a start and end location! The project is complete


Reflection: 
	Through the course of this project we had the opportunity to grapple with Guava a bit. Despite some challenges with learning how to use Guava for our intended purposes, we were able to create a graph which represents our data set in an interesting and engaging way. It was very helpful to get some experience experimenting with and navigating a new open-source set of common libraries. It was interesting to learn how graphs, which we have seen applied and utilized in discrete math, can be applied.
	Especially in the method in which we returned the path, recursion was difficult to understand immediately. However, the struggle to code the getPath method was especially helpful in a quick implementation of the method and the declaration of its parameters in main. It was an incredibly helpful project in understanding how various data structures we worked with over the semester are able to interact.
