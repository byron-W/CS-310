//TODO: Implement the required methods and add JavaDoc for them.
//Remember: Do NOT add any additional instance or class variables (local variables are ok)
//and do NOT alter any provided methods or change any method signatures!

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;

import java.awt.Color;

import javax.swing.JPanel;

import org.w3c.dom.Node;

import java.util.Collection;
import java.util.Collections; //only for sorting edges, do NOT use it anywhere else!

import java.util.Iterator;
import java.util.NoSuchElementException;

import java.util.LinkedList;

/**
 * Simulation of Bellman-Ford algorithm.
 * 
 */
class BellmanFord implements GraphAlg {
	/**
	 * The graph the algorithm will run on.
	 */
	Graph<GraphNode, GraphEdge> graph;

	/**
	 * The list of edges in graph.
	 */
	LinkedList<GraphEdge> edgeList;

	/**
	 * The iterator of edges from edgeList.
	 */
	private Iterator<GraphEdge> iter;

	/**
	 * The edge checked in current step.
	 */
	private GraphEdge currentEdge;

	/**
	 * Whether or not the algorithm has been started.
	 */
	private boolean started = false;

	/**
	 * Whether or not the algorithm has completed.
	 */
	private boolean completed = false;

	/**
	 * Whether or not the graph has a nagative weighted cycle reachable from source
	 * node.
	 */
	private boolean hasNegCycle = false;

	/**
	 * The round in simulation.
	 */
	private int round = 0;

	/**
	 * The source node of the shortest path task.
	 */
	private GraphNode sourceNode;

	/**
	 * Whether or not the relaxation has triggered updates of shortest paths.
	 */
	private boolean changed = false;

	/**
	 * The color when a node has "no color".
	 */
	public static final Color COLOR_NONE_NODE = Color.WHITE;

	/**
	 * The color when an edge has "no color".
	 */
	public static final Color COLOR_NONE_EDGE = Color.BLACK;

	/**
	 * The color when a node/edge is highlighted.
	 */
	public static final Color COLOR_HIGHLIGHT = new Color(255, 204, 51);

	/**
	 * The color when an edge is marked as part of the shortest path.
	 */
	public static final Color COLOR_ANS = Color.BLUE;

	/**
	 * The color when a node or edge is in warning.
	 */
	public static final Color COLOR_WARNING = new Color(255, 51, 51);

	/**
	 * The infinity sign.
	 */
	public static final String INFINITY_SIGN = "\u221e";

	/**
	 * {@inheritDoc}
	 */
	public EdgeType graphEdgeType() {
		return EdgeType.DIRECTED;
	}

	/**
	 * {@inheritDoc}
	 */
	public void reset(Graph<GraphNode, GraphEdge> graph) {
		this.graph = graph;
		started = false;
		completed = false;
		edgeList = null;
		iter = null;
		round = 0;
		changed = false;
		currentEdge = null;
		hasNegCycle = false;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isStarted() {
		return started;
	}

	/**
	 * Report whether or not we have identified a negative-weighted cycle reachable
	 * from source node.
	 * 
	 * @return whether or not we have identified a negative-weighted cycle reachable
	 *         from source node
	 */
	public boolean hasNegCycle() {
		return hasNegCycle;
	}

	/**
	 * Report the current round in Bellman-Ford simulation.
	 * 
	 * @return the current round in Bellman-Ford simulation
	 */
	public int getRound() {
		return round;
	}

	/**
	 * Sets the node to display a given cost (or infinity if appropriate).
	 * 
	 * @param n    the node to set
	 * @param cost the cost to set
	 */
	private void setNodeText(GraphNode n, int cost) {
		String text = (cost == Integer.MAX_VALUE ? INFINITY_SIGN : "" + cost);
		n.setText(text);
	}

	/**
	 * Sets the node to display a given cost (or infinity if appropriate)
	 * while also displaying the change from a previous value.
	 * 
	 * @param n       the node to set
	 * @param oldCost the previous cost
	 * @param newCost the new cost
	 */
	private void setNodeText(GraphNode n, int oldCost, int newCost) {
		String text = (oldCost == Integer.MAX_VALUE ? INFINITY_SIGN : "" + oldCost);
		n.setText("" + text + "->" + newCost);
	}

	/**
	 * {@inheritDoc}
	 */
	public void start() {
		started = true;
		completed = false;

		// set up source node to be the vertex with highest ID
		// ----------------------
		// YOU IMPLEMENT THIS!
		// ----------------------
		sourceNode = getMaximumIdNode();

		if (sourceNode == null) {
			completed = true;
			return;
		}
		sourceNode.setColor(COLOR_HIGHLIGHT);

		// only one node or no edges
		if (graph.getEdgeCount() == 0 || graph.getVertexCount() == 1) {
			completed = true;
			return;
		}

		// create an list of edges, sorted by id
		edgeList = new LinkedList<>();
		for (GraphEdge e : graph.getEdges())
			edgeList.add(e);

		// sort edges based on edge ID
		Collections.sort(edgeList);

		// initialize
		iter = edgeList.iterator();
		round = 0;
		changed = false;
		currentEdge = null;

		// initialize the cost (display) for all vertices
		// ----------------------
		// YOU IMPLEMENT THIS!
		// ----------------------
		initCost();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isCompleted() {
		return completed;
	}

	/**
	 * Cleanup the highlighted edge/node from last step.
	 */
	private void cleanUpLastEdge() {
		if (currentEdge == null)
			return;
		currentEdge.setColor(COLOR_NONE_EDGE);
		GraphNode dest = graph.getDest(currentEdge);
		setNodeText(dest, dest.getCost());

	}

	/**
	 * {@inheritDoc}
	 */
	public void doNextStep() {

		// simulation done
		if (completed)
			return;

		if (!iter.hasNext()) {
			// one round completed
			if (changed == false) {
				// early termination if no updates
				cleanUpLastEdge();
				completed = true;
				return;
			} else {
				// start a new round
				round++;

				if (round == graph.getVertexCount() - 1) {
					// already repeated |V|-1 times (starting from round=0)
					cleanUpLastEdge();

					// verify negative-weighted cycle
					// ----------------------
					// YOU IMPLEMENT THIS!
					// ----------------------
					hasNegCycle = findNegativeCycle();
					completed = true;
					return;
				} else {
					// normal new round
					iter = edgeList.iterator();
					changed = false;
				}
			}
		}

		// set up for next edge
		if (iter.hasNext()) { // should be true; being extra careful

			GraphEdge nextEdge = iter.next();
			cleanUpLastEdge();

			// reset current edge
			currentEdge = nextEdge;
			currentEdge.setColor(COLOR_HIGHLIGHT);
			// relax and update for currentEdge
			// ----------------------
			// YOU IMPLEMENT THIS!
			// ----------------------
			relax();
		}
	}

	// ----------------------------------------------------
	// - DO NOT change the signature of any required public method;
	// - Feel free to define additional method but they must be private.
	// - Add JavaDoc and comments for these methods.
	//
	// YOUR CODE HERE
	// ----------------------------------------------------
	/**
	 * Returns the maximum ID node from all vertices in the graph.
	 * This is chosen as the starting spot for the algorithm
	 * 
	 * @return the highest ID or null if graph is null or has 0 vertices
	 */
	public GraphNode getMaximumIdNode() {
		if (graph == null)
			return null;
		if (graph.getVertexCount() < 1)
			return null;
		Collection<GraphNode> nodes = graph.getVertices();
		GraphNode max = (GraphNode) nodes.toArray()[0];
		for (GraphNode graphNode : nodes) {
			if (graphNode.getId() > max.getId())
				max = graphNode;
		}
		return max;
	}

	/**
	 * Initialize the path length and text for each node.
	 * Source node is 0, the rest are infinity
	 */
	public void initCost() {
		Collection<GraphNode> nodes = graph.getVertices();
		for (GraphNode graphNode : nodes) {
			if (graphNode.equals(sourceNode)) {
				sourceNode.setCost(0);
				sourceNode.setText("0");
			} else
				graphNode.setText(INFINITY_SIGN);
		}
	}

	/**
	 * Performs relaxation for the current edge.
	 */
	public void relax() {
		GraphNode dest = graph.getDest(currentEdge);
		GraphNode source = graph.getSource(currentEdge);
		int prevWeight = source.getCost();
		if (prevWeight != Integer.MAX_VALUE && currentEdge.getWeight() != Integer.MAX_VALUE)
			prevWeight += currentEdge.getWeight();
		if (prevWeight < dest.getCost()) {
			dest.setParent(source);
			dest.setCost(prevWeight);
			dest.setText(prevWeight + "");
			changed = true;
		}
	}

	/**
	 * Checks if the graph contains a negative weighted cycle.
	 * 
	 * @return true iff there is at least one negative weighted cycle
	 */
	public boolean findNegativeCycle() {
		Iterator<GraphEdge> edgeIter = graph.getEdges().iterator();
		while (edgeIter.hasNext()) {
			GraphEdge curr = edgeIter.next();
			GraphNode dest = graph.getDest(curr);
			GraphNode source = graph.getSource(curr);
			int prevWeight = source.getCost() + currentEdge.getWeight();
			if (prevWeight < dest.getCost())
				return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public void finish() {
		if (!hasNegCycle) {
			LinkedList<GraphNode> visited = new LinkedList<GraphNode>();
			LinkedList<GraphEdge> paths = new LinkedList<GraphEdge>();
			paths = findPaths(sourceNode, paths, visited);
			for (GraphEdge graphEdge : paths) {
				graphEdge.setColor(COLOR_ANS);
			}
		} else {
			Collection<GraphNode> allNodes = graph.getVertices();
			for (GraphNode graphNode : allNodes) {
				graphNode.setText(graphNode.id + "");
			}
		}
		started = false; // keep this line!
		// clean up
		// - if no negative-weighted cycles: highlight shortest paths identified for
		// each node,
		// keep the path length display for each node
		// - otherwise, update the display for each node to be its nodeId
	}

	/**
	 * Recursively stores the edges that reach nodes with the lowest weight.
	 * 
	 * @param node    the current node
	 * @param paths   the stored correct paths
	 * @param visited the nodes that have been visited
	 * @return the stored correct paths
	 */
	private LinkedList<GraphEdge> findPaths(GraphNode node, LinkedList<GraphEdge> paths,
			LinkedList<GraphNode> visited) {
		visited.add(node);
		Collection<GraphEdge> edges = graph.getOutEdges(node);
		if (edges.size() < 1)
			return paths;
		for (GraphEdge graphEdge : edges) {
			GraphNode dest = graph.getDest(graphEdge);
			if (visited.contains(dest))
				continue;
			int shortest = node.getCost() + graphEdge.getWeight();
			if (shortest == dest.getCost()) {
				paths.add(graphEdge);
				findPaths(dest, paths, visited);
			}
		}
		return paths;
	}

	// ********************************************************************************
	// testing code goes here... edit this as much as you want!
	// ********************************************************************************
	/**
	 * The main method.
	 * 
	 * @param args cmd line arguments
	 */
	public static void main(String[] args) {
		FlexGraph graph = new FlexGraph();
		BellmanFord simulation = new BellmanFord();

		// test 1
		GraphNode[] nodes = { new GraphNode(0), new GraphNode(1) };
		GraphEdge[] edges = { new GraphEdge(0, -5) }; // weight -5

		// set up graph with 2 nodes, 1 edge
		graph.addVertex(nodes[0]);
		graph.addVertex(nodes[1]);
		graph.addEdge(edges[0], nodes[1], nodes[0]); // 1-->0

		// set up simulation
		simulation.reset(graph);
		while (simulation.step()) {
		} // execution of all steps

		// check result (shortest paths w/ source node as nodes[1])
		if (nodes[0].getCost() == -5 && nodes[0].getParent().equals(nodes[1])
				&& simulation.getMaximumIdNode().equals(nodes[1]) &&
				!simulation.hasNegCycle() && simulation.getRound() == 1)
			System.out.println("pass one-edge graph!");

		// test 2
		GraphNode[] nodes2 = { new GraphNode(0), new GraphNode(1), new GraphNode(2), new GraphNode(3), };

		GraphEdge[] edges2 = { new GraphEdge(0, -5), new GraphEdge(1, 5), new GraphEdge(2, 10), new GraphEdge(3, -1) };

		graph = new FlexGraph(); // graph from samplerun.pdf Example 4
		graph.addVertex(nodes2[0]);
		graph.addVertex(nodes2[1]);
		graph.addVertex(nodes2[2]);
		graph.addVertex(nodes2[3]);

		graph.addEdge(edges2[0], nodes2[1], nodes2[0]);
		graph.addEdge(edges2[1], nodes2[1], nodes2[2]);
		graph.addEdge(edges2[2], nodes2[1], nodes2[3]);
		graph.addEdge(edges2[3], nodes2[3], nodes2[1]);
		simulation.reset(graph);
		while (simulation.step()) {
		} // execution of all steps

		if (!simulation.hasNegCycle() && simulation.getRound() == 2 &&
				nodes2[0].getCost() == -6 && nodes2[1].getCost() == -1 && nodes2[2].getCost() == 4 &&
				nodes2[1].getParent().equals(nodes2[3]) && edges2[1].getColor() == COLOR_ANS)
			System.out.println("pass samplerun.pdf Example4!");

		// write your own testing code ...
	}
}