import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.DirectedGraph;

import edu.uci.ics.jung.graph.util.Pair;
import edu.uci.ics.jung.graph.util.EdgeType;

import org.apache.commons.collections15.Factory;

import java.util.Collection;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * A directed graph.
 * 
 * @author Byron Washington
 */
class FlexGraph implements Graph<GraphNode, GraphEdge>, DirectedGraph<GraphNode, GraphEdge> {
	// HINTS:
	// 1 -- Learn about what methods are available in Java's LinkedList
	// class before trying this, a lot of them will come in handy...
	// 2 -- You may want to become friendly with the ListIterator as well.
	// This iterator support things beyond Iterator, e.g. removal...

	/**
	 * The maximum number of nodes.
	 */
	private static final int MAX_NUMBER_OF_NODES = 200;

	/**
	 * The adjacency list.
	 */
	private LinkedList<Connection>[] adjList = null;

	/**
	 * A (destination,edge) to store in the adjacency list.
	 */
	private class Connection {
		/**
		 * The graph node.
		 */
		GraphNode node;

		/**
		 * The graph edge.
		 */
		GraphEdge edge;

		/**
		 * The constructor.
		 * 
		 * @param n the graph node
		 * @param e the graph edge
		 */
		Connection(GraphNode n, GraphEdge e) {
			this.node = n;
			this.edge = e;
		}

	}

	/**
	 * The constructor.
	 */
	@SuppressWarnings("unchecked")
	public FlexGraph() {
		adjList = (LinkedList<Connection>[]) new LinkedList[MAX_NUMBER_OF_NODES];
		// reminder: you can NOT do this: ClassWithGeneric<T>[] items =
		// (ClassWithGeneric<T>[]) new Object[10];
		// you must use this format instead: ClassWithGeneric<T>[] items =
		// (ClassWithGeneric<T>[]) new ClassWithGeneric[10];

	}

	/**
	 * Returns a view of all edges in this graph. In general, this
	 * obeys the Collection contract, and therefore makes no guarantees
	 * about the ordering of the edges within the set.
	 * 
	 * @return a Collection view of all edges in this graph
	 */
	public Collection<GraphEdge> getEdges() {
		Collection<GraphEdge> value = (Collection<GraphEdge>) new LinkedList<GraphEdge>();
		for (int i = 0; i < adjList.length; i++) {
			if (adjList[i] != null && !adjList[i].isEmpty()) {
				ListIterator<Connection> vertIter = adjList[i].listIterator();
				vertIter.next();
				while (vertIter.hasNext())
					value.add(vertIter.next().edge);
			}
		}
		return value;
		// Hint: this method returns a Collection, look at the imports for
		// what collections you could return here.

		// O(n+e) where e is the number of edges in the graph and
		// n is the max number of nodes in the graph
	}

	/**
	 * Returns a view of all vertices in this graph. In general, this
	 * obeys the Collection contract, and therefore makes no guarantees
	 * about the ordering of the vertices within the set.
	 * 
	 * @return a Collection view of all vertices in this graph
	 */
	public Collection<GraphNode> getVertices() {
		Collection<GraphNode> value = (Collection<GraphNode>) new LinkedList<GraphNode>();
		for (int i = 0; i < adjList.length; i++) {
			if (adjList[i] != null && !adjList[i].isEmpty())
				value.add(adjList[i].getFirst().node);
		}
		return value;
		// Hint: this method returns a Collection, look at the imports for
		// what collections you could return here.

		// O(n) where n is the max number of nodes.
	}

	/**
	 * Returns the number of edges in this graph.
	 * 
	 * @return the number of edges in this graph
	 */
	public int getEdgeCount() {
		int numEdge = 0;
		for (int i = 0; i < adjList.length; i++) {
			if (adjList[i] != null && !adjList[i].isEmpty())
				numEdge += (adjList[i].size() - 1);
		}
		return numEdge;
		// O(n) where n is the max number of nodes in the graph
		// note: this is NOT O(n+e), just O(n)

		// Note: this runtime is not a mistake, think about how
		// you could find out the number of edges *without*
		// looking at each one
	}

	/**
	 * Returns the number of vertices in this graph.
	 * 
	 * @return the number of vertices in this graph
	 */
	public int getVertexCount() {
		int numEdge = 0;
		for (int i = 0; i < adjList.length; i++) {
			if (adjList[i] != null && !adjList[i].isEmpty())
				numEdge++;
		}
		return numEdge;
		// O(n) where n is the max number of nodes in the graph
	}

	/**
	 * Returns true if this graph's vertex collection contains vertex.
	 * Equivalent to getVertices().contains(vertex).
	 * 
	 * @param vertex the vertex whose presence is being queried
	 * @return true iff this graph contains a vertex vertex
	 */
	public boolean containsVertex(GraphNode vertex) {
		if (vertex == null)
			throw new RuntimeException();
		if (adjList[vertex.id] != null && !adjList[vertex.id].isEmpty())
			return true;
		else
			return false;
		// O(1) -- NOT O(n)!
		// Note: this runtime is not a mistake, look at
		// the storage overview in the project description for ideas
	}

	/**
	 * Returns a Collection view of the incoming edges incident to vertex
	 * in this graph.
	 * 
	 * @param vertex the vertex whose incoming edges are to be returned
	 * @return a Collection view of the incoming edges incident
	 *         to vertex in this graph
	 */
	public Collection<GraphEdge> getInEdges(GraphNode vertex) {
		if (vertex == null)
			throw new RuntimeException();
		Collection<GraphEdge> value = (Collection<GraphEdge>) new LinkedList<GraphEdge>();
		for (int i = 0; i < adjList.length; i++) {
			if (adjList[i] != null && i != vertex.id && !adjList[i].isEmpty()) {
				ListIterator<Connection> vertIter = adjList[i].listIterator();
				vertIter.next();
				while (vertIter.hasNext()) {
					Connection con = vertIter.next();
					if (con.node.id == vertex.id)
						value.add(con.edge);
				}
			}
		}
		return value;
		// if vertex not present in graph, return null

		// O(n+e) where e is the number of edges in the graph and
		// n is the max number of nodes in the graph
	}

	/**
	 * Returns a Collection view of the outgoing edges incident to vertex
	 * in this graph.
	 * 
	 * @param vertex the vertex whose outgoing edges are to be returned
	 * @return a Collection view of the outgoing edges incident
	 *         to vertex in this graph
	 */
	public Collection<GraphEdge> getOutEdges(GraphNode vertex) {
		if (vertex == null)
			throw new RuntimeException();
		Collection<GraphEdge> value = (Collection<GraphEdge>) new LinkedList<GraphEdge>();
		if (adjList[vertex.id] == null)
			return null;
		ListIterator<Connection> vertIter = adjList[vertex.id].listIterator();
		if (!adjList[vertex.id].isEmpty())
			vertIter.next();
		while (vertIter.hasNext()) {
			Connection con = vertIter.next();
			if (!con.node.equals(vertex))
				value.add(con.edge);
		}
		return value;
		// if vertex not present in graph, return null

		// O(e) where e is the number of edges in the graph
	}

	/**
	 * Returns the number of incoming edges incident to vertex.
	 * Equivalent to getInEdges(vertex).size().
	 * 
	 * @param vertex the vertex whose indegree is to be calculated
	 * @return the number of incoming edges incident to vertex
	 */
	public int inDegree(GraphNode vertex) {
		if (vertex == null)
			throw new RuntimeException();
		int numDeg = 0;
		for (int i = 0; i < adjList.length; i++) {
			if (adjList[i] != null && i != vertex.id && !adjList[i].isEmpty()) {
				ListIterator<Connection> vertIter = adjList[i].listIterator();
				vertIter.next();
				while (vertIter.hasNext()) {
					Connection con = vertIter.next();
					if (con.node.id == vertex.id)
						numDeg++;
				}
			}
		}
		if (numDeg < 1)
			numDeg = -1;
		return numDeg;
		// if vertex not present in graph, return -1

		// O(n+e) where e is the number of edges in the graph and
		// n is the max number of nodes in the graph
	}

	/**
	 * Returns the number of outgoing edges incident to vertex.
	 * Equivalent to getOutEdges(vertex).size().
	 * 
	 * @param vertex the vertex whose outdegree is to be calculated
	 * @return the number of outgoing edges incident to vertex
	 */
	public int outDegree(GraphNode vertex) {
		if (vertex == null)
			throw new RuntimeException();
		int index = vertex.id;
		int numDeg = -1;
		if (adjList[index] != null && !adjList[index].isEmpty())
			numDeg = adjList[index].size() - 1;
		return numDeg;
		// if vertex not present in graph, return -1
		// O(1)
	}

	/**
	 * Returns a Collection view of the predecessors of vertex
	 * in this graph. A predecessor of vertex is defined as a vertex v
	 * which is connected to
	 * vertex by an edge e, where e is an outgoing edge of
	 * v and an incoming edge of vertex.
	 * 
	 * @param vertex the vertex whose predecessors are to be returned
	 * @return a Collection view of the predecessors of
	 *         vertex in this graph
	 */
	public Collection<GraphNode> getPredecessors(GraphNode vertex) {
		if (vertex == null)
			return null;
		Collection<GraphNode> value = (Collection<GraphNode>) new LinkedList<GraphNode>();
		for (int i = 0; i < adjList.length; i++) {
			if (adjList[i] != null && i != vertex.id && !adjList[i].isEmpty()) {
				ListIterator<Connection> vertIter = adjList[i].listIterator();
				vertIter.next();
				while (vertIter.hasNext()) {
					Connection con = vertIter.next();
					if (con.node.id == vertex.id)
						value.add(con.node);
				}
			}
		}
		return value;
		// O(n+e) where e is the number of edges in the graph and
		// n is the max number of nodes in the graph
	}

	/**
	 * Returns a Collection view of the successors of vertex
	 * in this graph. A successor of vertex is defined as a vertex v
	 * which is connected to
	 * vertex by an edge e, where e is an incoming edge of
	 * v and an outgoing edge of vertex.
	 * 
	 * @param vertex the vertex whose predecessors are to be returned
	 * @return a Collection view of the successors of
	 *         vertex in this graph
	 */
	public Collection<GraphNode> getSuccessors(GraphNode vertex) {
		if (vertex == null)
			return null;
		Collection<GraphNode> value = (Collection<GraphNode>) new LinkedList<GraphNode>();
		if (adjList[vertex.id] == null)
			return null;
		ListIterator<Connection> vertIter = adjList[vertex.id].listIterator();
		if (!adjList[vertex.id].isEmpty())
			vertIter.next();
		vertIter.next();
		while (vertIter.hasNext()) {
			Connection con = vertIter.next();
			if (con.node.id != vertex.id)
				value.add(con.node);
		}
		return value;
		// if vertex not present in graph, return null
		// O(e) where e is the number of edges in the graph

	}

	/**
	 * Returns true if v1 is a predecessor of v2 in this graph.
	 * 
	 * @param v1 the first vertex to be queried
	 * @param v2 the second vertex to be queried
	 * @return true if v1 is a predecessor of v2, and false otherwise.
	 */
	public boolean isPredecessor(GraphNode v1, GraphNode v2) {
		if (v1 == null || v2 == null)
			return false;
		if (v1.equals(v2))
			return false;
		if (adjList[v1.id] == null || adjList[v2.id] == null)
			return false;
		if (adjList[v1.id].isEmpty() || adjList[v2.id].isEmpty())
			return false;
		ListIterator<Connection> vertIter = adjList[v1.id].listIterator();
		vertIter.next();
		while (vertIter.hasNext()) {
			Connection con = vertIter.next();
			if (con.node.id == v2.id)
				return true;
		}
		return false;
		// O(e) where e is the number of edges in the graph
	}

	/**
	 * Returns true if v1 is a successor of v2 in this graph.
	 * 
	 * @param v1 the first vertex to be queried
	 * @param v2 the second vertex to be queried
	 * @return true if v1 is a successor of v2, and false otherwise.
	 */
	public boolean isSuccessor(GraphNode v1, GraphNode v2) {
		if (v1 == null || v2 == null)
			return false;
		if (v1.equals(v2))
			return false;
		if (adjList[v1.id] == null || adjList[v2.id] == null)
			return false;
		if (adjList[v1.id].isEmpty() || adjList[v2.id].isEmpty())
			return false;
		ListIterator<Connection> vertIter = adjList[v2.id].listIterator();
		vertIter.next();
		while (vertIter.hasNext()) {
			Connection con = vertIter.next();
			if (con.node.id == v1.id)
				return true;
		}
		return false;
		// O(e) where e is the number of edges in the graph
	}

	/**
	 * Returns the collection of vertices which are connected to vertex
	 * via any edges in this graph.
	 * If vertex is connected to itself with a self-loop, then
	 * it will be included in the collection returned.
	 * 
	 * @param vertex the vertex whose neighbors are to be returned
	 * @return the collection of vertices which are connected to vertex,
	 *         or null if vertex is not present
	 */
	public Collection<GraphNode> getNeighbors(GraphNode vertex) {
		if (vertex == null)
			throw new RuntimeException();
		Collection<GraphNode> value = (Collection<GraphNode>) new LinkedList<GraphNode>();
		for (int i = 0; i < adjList.length; i++) {
			if (adjList[i] != null && !adjList[i].isEmpty()) {
				ListIterator<Connection> vertIter = adjList[i].listIterator();
				vertIter.next();
				edges: while (vertIter.hasNext()) {
					Connection con = vertIter.next();
					if (i == vertex.id) {
						if (value.contains(con.node))
							continue edges;
						else
							value.add(con.node);
					} else if (con.node.id == vertex.id) {
						if (value.contains(adjList[i].getFirst().node))
							continue edges;
						else
							value.add(adjList[i].getFirst().node);
					}
				}
			}
		}
		return value;
		// O(n^2) where n is the max number of vertices in the graph

		// NOTE: there should be no duplicates in the neighbor list.
	}

	/**
	 * If edge is a directed edge in this graph, returns the source;
	 * otherwise returns null.
	 * The source of a directed edge d is defined to be the vertex for which
	 * d is an outgoing edge.
	 * edge is guaranteed to be a directed edge if
	 * its EdgeType is DIRECTED.
	 * 
	 * @param edge the edge to be tested
	 * @return the source of directed_edge if it is a directed edge in this graph,
	 *         or null otherwise
	 */
	public GraphNode getSource(GraphEdge edge) {
		if (edge == null)
			return null;
		for (int i = 0; i < adjList.length; i++) {
			if (adjList[i] != null && !adjList[i].isEmpty()) {
				ListIterator<Connection> vertIter = adjList[i].listIterator();
				vertIter.next();
				while (vertIter.hasNext()) {
					Connection con = vertIter.next();
					if (con.edge.id == edge.id)
						return adjList[i].getFirst().node;
				}
			}
		}
		return null;
		// O(n+e) where n is the max number of vertices in the graph
		// and e is the number of edges in the graph
	}

	/**
	 * If edge is a directed edge in this graph, returns the destination;
	 * otherwise returns null.
	 * The destination of a directed edge d is defined to be the vertex
	 * incident to d for which
	 * d is an incoming edge.
	 * edge is guaranteed to be a directed edge if
	 * its EdgeType is DIRECTED.
	 * 
	 * @param edge the edge to be tested
	 * @return the destination of directed_edge if it is a directed edge in this
	 *         graph, or null otherwise
	 */
	public GraphNode getDest(GraphEdge edge) {
		if (edge == null)
			return null;
		for (int i = 0; i < adjList.length; i++) {
			if (adjList[i] != null && !adjList[i].isEmpty()) {
				ListIterator<Connection> vertIter = adjList[i].listIterator();
				vertIter.next();
				while (vertIter.hasNext()) {
					Connection con = vertIter.next();
					if (con.edge.id == edge.id)
						return con.node;
				}
			}
		}
		return null;
		// O(n+e) where n is the max number of vertices in the graph
		// and e is the number of edges in the graph
	}

	/**
	 * Returns an edge that connects v1 to v2.
	 * If this edge is not uniquely
	 * defined (that is, if the graph contains more than one edge connecting
	 * v1 to v2), any of these edges
	 * may be returned. findEdgeSet(v1, v2) may be
	 * used to return all such edges.
	 * Returns null if either of the following is true:
	 * <ul>
	 * <li/>v1 is not connected to v2
	 * <li/>either v1 or v2 are not present in this graph
	 * </ul>
	 * <b>Note</b>: for purposes of this method, v1 is only considered to be
	 * connected to
	 * v2 via a given <i>directed</i> edge e if
	 * v1 == e.getSource() && v2 == e.getDest() evaluates to true.
	 * (v1 and v2 are connected by an undirected edge u if
	 * u is incident to both v1 and v2.)
	 * 
	 * @param v1 the first node
	 * @param v2 the second node
	 * @return an edge that connects v1 to v2,
	 *         or null if no such edge exists (or either vertex is not present)
	 * @see Hypergraph#findEdgeSet(Object, Object)
	 */
	public GraphEdge findEdge(GraphNode v1, GraphNode v2) {
		if (v1 == null || v2 == null)
			return null;
		int index1 = v1.id;
		int index2 = v2.id;
		if (adjList[index1] == null || adjList[index2] == null)
			return null;
		if (adjList[index1].isEmpty() || adjList[index2].isEmpty())
			return null;
		ListIterator<Connection> firstIterator = adjList[index1].listIterator();
		while (firstIterator.hasNext()) { // checking if the 2nd node is a successor of the 1st
			Connection con = firstIterator.next();
			if (con.node.id == index2)
				return con.edge;
		}
		ListIterator<Connection> secondIterator = adjList[index2].listIterator();
		while (secondIterator.hasNext()) { // checking if the 1st node is a successor of the 2nd
			Connection con = secondIterator.next();
			if (con.node.id == index1)
				return con.edge;
		}
		return null;
		// O(e) where e is the number of edges in the graph
	}

	/**
	 * Returns true if vertex and edge
	 * are incident to each other.
	 * Equivalent to getIncidentEdges(vertex).contains(edge) and to
	 * getIncidentVertices(edge).contains(vertex).
	 * 
	 * @param vertex the vertex to be checked
	 * @param edge   the edge to be checked
	 * @return true if vertex and edge
	 *         are incident to each other
	 */
	public boolean isIncident(GraphNode vertex, GraphEdge edge) {
		if (vertex == null || edge == null)
			return false;
		for (int i = 0; i < adjList.length; i++) {
			if (adjList[i] != null && !adjList[i].isEmpty()) {
				ListIterator<Connection> vertIter = adjList[i].listIterator();
				vertIter.next();
				while (vertIter.hasNext()) {
					Connection con = vertIter.next();
					// checks for both incoming and outgoing
					if ((con.edge.id == edge.id) && (i == vertex.id || con.node.id == vertex.id))
						return true;
				}
			}
		}
		return false;
		// O(n+e) where n is the max number of vertices in the graph
		// and e is the number of edges in the graph
	}

	/**
	 * Adds edge e to this graph such that it connects
	 * vertex v1 to v2.
	 * Equivalent to addEdge(e, new PairGraphNode(v1, v2)).
	 * If this graph does not contain v1, v2,
	 * or both, implementations may choose to either silently add
	 * the vertices to the graph or throw an IllegalArgumentException.
	 * If this graph assigns edge types to its edges, the edge type of
	 * e will be the default for this graph.
	 * See Hypergraph.addEdge() for a listing of possible reasons
	 * for failure.
	 * 
	 * @param e  the edge to be added
	 * @param v1 the first vertex to be connected
	 * @param v2 the second vertex to be connected
	 * @return true if the add is successful, false otherwise
	 * @see Hypergraph#addEdge(Object, Collection)
	 * @see #addEdge(Object, Object, Object, EdgeType)
	 */
	public boolean addEdge(GraphEdge e, GraphNode v1, GraphNode v2) {
		if (e == null || v1 == null || v2 == null)
			throw new IllegalArgumentException();
		if (v1.equals(v2))
			return false; // self-loop
		int index1 = v1.id;
		int index2 = v2.id;
		// silently adding the new vertices if needed
		if (adjList[index1] == null)
			this.addVertex(v1);
		if (adjList[index2] == null)
			this.addVertex(v2);
		for (int i = 0; i < adjList.length; i++) {
			if (adjList[i] != null && !adjList[i].isEmpty()) {
				ListIterator<Connection> vertIter = adjList[i].listIterator();
				vertIter.next();
				while (vertIter.hasNext()) {
					Connection con = vertIter.next();
					if (con.edge.id == e.id || (i == index1 && con.node.id == index2))
						return false; // parallel edge
				}
			}
		}
		adjList[index1].add(new Connection(v2, e));
		return true;
		// remember we do not allow self-loops nor parallel edges

		// O(n+e) where n is the max number of vertices in the graph
		// and e is the number of edges in the graph
	}

	/**
	 * Adds vertex to this graph.
	 * Fails if vertex is null or already in the graph.
	 * 
	 * @param vertex the vertex to add
	 * @return true if the add is successful, and false otherwise
	 * @throws IllegalArgumentException if vertex is null
	 */
	public boolean addVertex(GraphNode vertex) {
		if (vertex == null)
			throw new IllegalArgumentException();
		int index = vertex.id;
		if (adjList[index] != null && !adjList[index].isEmpty()) // if the vertex already exists
			return false;
		if (adjList[index] == null)
			adjList[index] = new LinkedList<Connection>();
		adjList[index].add(new Connection(vertex, null));
		return true;
		// O(1)
	}

	/**
	 * Removes edge from this graph.
	 * Fails if edge is null, or is otherwise not an element of this graph.
	 * 
	 * @param edge the edge to remove
	 * @return true if the removal is successful, false otherwise
	 */
	public boolean removeEdge(GraphEdge edge) {
		if (edge == null)
			throw new IllegalArgumentException();
		for (int i = 0; i < adjList.length; i++) {
			if (adjList[i] != null && !adjList[i].isEmpty()) {
				ListIterator<Connection> vertIter = adjList[i].listIterator();
				vertIter.next();
				while (vertIter.hasNext()) {
					Connection con = vertIter.next();
					if (con.edge.id == edge.id) {
						adjList[i].remove(con);
						return true;
					}
				}
			}
		}
		return false;
		// O(n+e) where n is the max number of vertices in the graph
		// and e is the number of edges in the graph
	}

	/**
	 * Removes vertex from this graph.
	 * As a side effect, removes any edges e incident to vertex if the
	 * removal of vertex would cause e to be incident to an illegal
	 * number of vertices. (Thus, for example, incident hyperedges are not removed,
	 * but incident edges--which must be connected to a vertex at both
	 * endpoints--are removed.)
	 * 
	 * @param vertex the vertex to remove
	 * @return true if the removal is successful, false otherwise
	 */
	public boolean removeVertex(GraphNode vertex) {
		if (vertex == null)
			return false;
		int index = vertex.id;
		if (adjList[index] == null || adjList[index].isEmpty()) // if the vertex doesn't exist
			return false;
		adjList[index].clear(); // clears the vertex and outgoing edges
		for (int i = 0; i < adjList.length; i++) { // clearing incoming edges
			if (adjList[i] != null && !adjList[i].isEmpty()) {
				ListIterator<Connection> vertIter = adjList[i].listIterator();
				vertIter.next();
				while (vertIter.hasNext()) {
					Connection con = vertIter.next();
					if (con.node.id == index)
						// i could continue here but if there are somehow parallel edges
						// here is a good spot to get rid of them
						adjList[i].remove(con);
				}
			}
		}
		return true;
		// O(n+e) where n is the max number of vertices in the graph
		// and e is the number of edges in the graph
	}

	/**
	 * Returns a string of the depth first traversal of the graph from the given
	 * vertex.
	 * If vertex is null or not present in graph, return an empty string.
	 * Otherwise, return the string with node IDs in the depth first traversal
	 * order, separated by a single space.
	 *
	 * @param vertex the starting vertex of the depth first traversal, may be null
	 * @return a string representation of the depth first traversal, or an empty
	 *         string if vertex is null or not present
	 */
	public String depthFirstTraversal(GraphNode vertex) {
		if (vertex == null)
			return "";
		LinkedList<GraphNode> visited = new LinkedList<GraphNode>();
		visited.add(vertex);
		visited = dfsHelper(vertex, visited, adjList);
		StringBuilder strBuilder = new StringBuilder();
		ListIterator<GraphNode> iter = visited.listIterator();
		while (iter.hasNext())
			strBuilder.append(Integer.toString(iter.next().id) + " ");
		return strBuilder.toString().trim();
		// Hint: feel free to define private helper method
		// Use StringBuilder!

		// O(n+e) where n is the max number of vertices in the graph
		// and e is the number of edges in the graph
	}

	/**
	 * Recusively performs a DFS on a graph.
	 * 
	 * @param node    the node that's being visited
	 * @param visited the linked list of visited nodes
	 * @param list    the adjacency list of the reversed graph
	 * @return a linked list of the visited nodes
	 */
	private LinkedList<GraphNode> dfsHelper(GraphNode node, LinkedList<GraphNode> visited,
			LinkedList<Connection>[] list) {
		boolean added = false;
		for (GraphNode graphNode : visited) {
			if (node.equals(graphNode)) {
				added = true;
				break;
			}
		}
		if (!added)
			visited.add(node);
		ListIterator<Connection> iter = list[node.id].listIterator();
		while (iter.hasNext()) {
			Connection con = iter.next();
			if (!visited.contains(con.node))
				dfsHelper(con.node, visited, list);
		}
		return visited;
	}

	/**
	 * Counts the number of nodes that are reachable from the given vertex,
	 * and the number of nodes that can reach the given vertex in graph.
	 * Returns a pair of the two integer counters as the answer.
	 * 
	 * @param vertex the node we check for reachable feature
	 * @return a pair of integer counters or null if not present
	 */
	@SuppressWarnings("unchecked")
	public IntPair countReachable(GraphNode vertex) {
		if (vertex == null)
			throw new RuntimeException();
		int reachable = 0;
		int reaching = 0;
		LinkedList<GraphNode> visited = new LinkedList<GraphNode>();
		visited.add(vertex);
		visited = dfsHelper(vertex, visited, adjList);
		reachable = visited.size() - 1;

		LinkedList<Connection>[] newList = (LinkedList<Connection>[]) new LinkedList[MAX_NUMBER_OF_NODES];
		for (int i = 0; i < adjList.length; i++) {
			if (adjList[i] != null && !adjList[i].isEmpty()) {
				newList[i] = new LinkedList<Connection>();
				newList[i].addFirst(adjList[i].getFirst());
			}
		}
		for (int i = 0; i < adjList.length; i++) {
			if (adjList[i] != null && !adjList[i].isEmpty()) {
				ListIterator<Connection> vertIter = adjList[i].listIterator();
				vertIter.next();
				while (vertIter.hasNext()) {
					Connection con = vertIter.next();
					newList[con.node.id].add(new Connection(adjList[i].getFirst().node, con.edge));
				}
			}
		}
		LinkedList<GraphNode> visitors = new LinkedList<GraphNode>();
		visitors.add(vertex);
		visitors = dfsHelper(vertex, visitors, newList);
		reaching = visitors.size() - 1;
		// O(n+e) where n is the max number of vertices in the graph
		// and e is the number of edges in the graph

		// Note: big-O is not O(n(n+e))
		// Re-check zyBookCh14 for graph traversals if you need more hints ...

		return new IntPair(reachable, reaching);
	}

	// ********************************************************************************
	// testing code goes here... edit this as much as you want!
	// ********************************************************************************
	/**
	 * Main method.
	 * 
	 * @param args cmd line arguments
	 */
	public static void main(String[] args) {
		// create a set of nodes and edges to test with
		GraphNode[] nodes = { new GraphNode(0), new GraphNode(1), new GraphNode(2), new GraphNode(3), new GraphNode(4), new GraphNode(5), new GraphNode(6), new GraphNode(7), new GraphNode(8), new GraphNode(9) };

		GraphEdge[] edges = { new GraphEdge(0), new GraphEdge(1), new GraphEdge(2), new GraphEdge(3), new GraphEdge(4), new GraphEdge(5), new GraphEdge(6), new GraphEdge(7) };

		// constructs a graph
		FlexGraph graph = new FlexGraph();
		for (GraphNode n : nodes) {
			graph.addVertex(n);
		}

		graph.addEdge(edges[0], nodes[0], nodes[1]);
		graph.addEdge(edges[1], nodes[1], nodes[2]); // 1-->2
		graph.addEdge(edges[2], nodes[3], nodes[6]);
		graph.addEdge(edges[3], nodes[6], nodes[7]);
		graph.addEdge(edges[4], nodes[8], nodes[9]);
		graph.addEdge(edges[5], nodes[9], nodes[0]);
		graph.addEdge(edges[6], nodes[2], nodes[7]);
		graph.addEdge(edges[7], nodes[1], nodes[8]); // 1-->8

		if (graph.getVertexCount() == 10 && graph.getEdgeCount() == 8) {
			System.out.println("Yay 1");
		}

		if (graph.inDegree(nodes[0]) == 1 && graph.outDegree(nodes[1]) == 2) {
			System.out.println("Yay 2");
		}

		// lots more testing here...
		// If your graph "looks funny" you probably want to check:
		// getVertexCount(), getVertices(), getInEdges(vertex),
		// and getIncidentVertices(incomingEdge) first. These are
		// used by the layout class.

		// some testing for the advanced graph operations:

		if (graph.depthFirstTraversal(nodes[9]).trim().equals("9 0 1 2 7 8")) {
			System.out.println("Yay 3");
		}
		// NOTE: in traversal, after node 1, we visited node 2 before node 8
		// since edge 1-->2 was added into graph before edge 1-->8

		// System.out.println(graph.depthFirstTraversal(nodes[9]));

		IntPair counts = graph.countReachable(nodes[1]);
		if (counts.getFirst() == 5 && counts.getSecond() == 3) {
			System.out.println("Yay 4");
		}
		// System.out.println(graph.countReachable(nodes[0]));

		// again, many more testing by yourself...

	}

	// ********************************************************************************
	// YOU MAY, BUT DON'T NEED TO EDIT THINGS IN THIS SECTION
	// NOTE: you do need to fix JavaDoc issues if there is any in this section.
	// ********************************************************************************

	/**
	 * Returns the number of edges incident to vertex.
	 * Special cases of interest:
	 * <ul>
	 * <li/>Incident self-loops are counted once.
	 * <li>If there is only one edge that connects this vertex to
	 * each of its neighbors (and vice versa), then the value returned
	 * will also be equal to the number of neighbors that this vertex has
	 * (that is, the output of getNeighborCount).
	 * <li>If the graph is directed, then the value returned will be
	 * the sum of this vertex's indegree (the number of edges whose
	 * destination is this vertex) and its outdegree (the number
	 * of edges whose source is this vertex), minus the number of
	 * incident self-loops (to avoid double-counting).
	 * </ul>
	 * Equivalent to getIncidentEdges(vertex).size().
	 * 
	 * @param vertex the vertex whose degree is to be returned
	 * @return the degree of this node
	 * @see Hypergraph#getNeighborCount(Object)
	 */
	public int degree(GraphNode vertex) {
		return inDegree(vertex) + outDegree(vertex);
	}

	/**
	 * Returns true if v1 and v2 share an incident edge.
	 * Equivalent to getNeighbors(v1).contains(v2).
	 * 
	 * @param v1 the first vertex to test
	 * @param v2 the second vertex to test
	 * @return true if v1 and v2 share an incident edge
	 */
	public boolean isNeighbor(GraphNode v1, GraphNode v2) {
		return (findEdge(v1, v2) != null || findEdge(v2, v1) != null);
	}

	/**
	 * Returns the endpoints of edge as a Pair(GraphNode).
	 * 
	 * @param edge the edge whose endpoints are to be returned
	 * @return the endpoints (incident vertices) of edge
	 */
	public Pair<GraphNode> getEndpoints(GraphEdge edge) {
		if (edge == null)
			return null;

		GraphNode v1 = getSource(edge);
		if (v1 == null)
			return null;

		GraphNode v2 = getDest(edge);
		if (v2 == null)
			return null;

		return new Pair<GraphNode>(v1, v2);
	}

	/**
	 * Returns the collection of edges in this graph which are connected to vertex.
	 * 
	 * @param vertex the vertex whose incident edges are to be returned
	 * @return the collection of edges which are connected to vertex,
	 *         or null if vertex is not present
	 */
	public Collection<GraphEdge> getIncidentEdges(GraphNode vertex) {
		LinkedList<GraphEdge> ret = new LinkedList<>();
		ret.addAll(getInEdges(vertex));
		ret.addAll(getOutEdges(vertex));
		return ret;
	}

	/**
	 * Returns the collection of vertices in this graph which are connected to edge.
	 * Note that for some graph types there are guarantees about the size of this
	 * collection
	 * (i.e., some graphs contain edges that have exactly two endpoints, which may
	 * or may
	 * not be distinct). Implementations for those graph types may provide alternate
	 * methods
	 * that provide more convenient access to the vertices.
	 * 
	 * @param edge the edge whose incident vertices are to be returned
	 * @return the collection of vertices which are connected to edge,
	 *         or null if edge is not present
	 */
	public Collection<GraphNode> getIncidentVertices(GraphEdge edge) {
		Pair<GraphNode> p = getEndpoints(edge);
		LinkedList<GraphNode> ret = new LinkedList<>();
		ret.add(p.getFirst());
		ret.add(p.getSecond());
		return ret;
	}

	/**
	 * Returns true if this graph's edge collection contains edge.
	 * Equivalent to getEdges().contains(edge).
	 * 
	 * @param edge the edge whose presence is being queried
	 * @return true iff this graph contains an edge edge
	 */
	public boolean containsEdge(GraphEdge edge) {
		return (getEndpoints(edge) != null);
	}

	/**
	 * Returns the collection of edges in this graph which are of type edge_type.
	 * 
	 * @param edgeType the type of edges to be returned
	 * @return the collection of edges which are of type edge_type, or
	 *         null if the graph does not accept edges of this type
	 * @see EdgeType
	 */
	public Collection<GraphEdge> getEdges(EdgeType edgeType) {
		if (edgeType == EdgeType.DIRECTED) {
			return getEdges();
		}
		return null;
	}

	/**
	 * Returns the number of edges of type edge_type in this graph.
	 * 
	 * @param edgeType the type of edge for which the count is to be returned
	 * @return the number of edges of type edge_type in this graph
	 */
	public int getEdgeCount(EdgeType edgeType) {
		if (edgeType == EdgeType.DIRECTED) {
			return getEdgeCount();
		}
		return 0;
	}

	/**
	 * Returns the number of predecessors that vertex has in this graph.
	 * Equivalent to vertex.getPredecessors().size().
	 * 
	 * @param vertex the vertex whose predecessor count is to be returned
	 * @return the number of predecessors that vertex has in this graph
	 */
	public int getPredecessorCount(GraphNode vertex) {
		return inDegree(vertex);
	}

	/**
	 * Returns the number of successors that vertex has in this graph.
	 * Equivalent to vertex.getSuccessors().size().
	 * 
	 * @param vertex the vertex whose successor count is to be returned
	 * @return the number of successors that vertex has in this graph
	 */
	public int getSuccessorCount(GraphNode vertex) {
		return outDegree(vertex);
	}

	/**
	 * Returns the number of vertices that are adjacent to vertex
	 * (that is, the number of vertices that are incident to edges in vertex's
	 * incident edge set). Equivalent to getNeighbors(vertex).size().
	 * 
	 * @param vertex the vertex whose neighbor count is to be returned
	 * @return the number of neighboring vertices
	 */
	public int getNeighborCount(GraphNode vertex) {
		if (!containsVertex(vertex))
			return -1;

		return getNeighbors(vertex).size();
	}

	/**
	 * Returns the vertex at the other end of edge from vertex.
	 * (That is, returns the vertex incident to edge which is not vertex.)
	 * 
	 * @param vertex the vertex to be queried
	 * @param edge   the edge to be queried
	 * @return the vertex at the other end of edge from vertex
	 */
	public GraphNode getOpposite(GraphNode vertex, GraphEdge edge) {
		if (getSource(edge).equals(vertex)) {
			return getDest(edge);
		} else if (getDest(edge).equals(vertex)) {
			return getSource(edge);
		} else
			return null;
	}

	/**
	 * Returns all edges that connects v1 to v2.
	 * If this edge is not uniquely
	 * defined (that is, if the graph contains more than one edge connecting
	 * v1 to v2), any of these edges
	 * may be returned. findEdgeSet(v1, v2) may be
	 * used to return all such edges.
	 * Returns null if v1 is not connected to v2.
	 * <br/>
	 * Returns an empty collection if either v1 or v2 are not present in this graph.
	 * 
	 * <b>Note</b>: for purposes of this method, v1 is only considered to be
	 * connected to
	 * v2 via a given <i>directed</i> edge d if
	 * v1 == d.getSource() && v2 == d.getDest() evaluates to true.
	 * (v1 and v2 are connected by an undirected edge u if
	 * u is incident to both v1 and v2.)
	 * 
	 * @param v1 the first node
	 * @param v2 the second node
	 * @return a collection containing all edges that connect v1 to v2,
	 *         or null if either vertex is not present
	 * @see Hypergraph#findEdge(Object, Object)
	 */
	public Collection<GraphEdge> findEdgeSet(GraphNode v1, GraphNode v2) {
		GraphEdge edge = findEdge(v1, v2);
		if (edge == null) {
			return null;
		}

		LinkedList<GraphEdge> ret = new LinkedList<>();
		ret.add(edge);
		return ret;

	}

	/**
	 * Returns true if vertex is the source of edge.
	 * Equivalent to getSource(edge).equals(vertex).
	 * 
	 * @param vertex the vertex to be queried
	 * @param edge   the edge to be queried
	 * @return true iff vertex is the source of edge
	 */
	public boolean isSource(GraphNode vertex, GraphEdge edge) {
		return getSource(edge).equals(vertex);
	}

	/**
	 * Returns true if vertex is the destination of edge.
	 * Equivalent to getDest(edge).equals(vertex).
	 * 
	 * @param vertex the vertex to be queried
	 * @param edge   the edge to be queried
	 * @return true iff vertex is the destination of edge
	 */
	public boolean isDest(GraphNode vertex, GraphEdge edge) {
		return getDest(edge).equals(vertex);
	}

	/**
	 * Adds edge e to this graph such that it connects
	 * vertex v1 to v2.
	 * Equivalent to addEdge(e, new Pair(GraphNode)(v1, v2)).
	 * If this graph does not contain v1, v2,
	 * or both, implementations may choose to either silently add
	 * the vertices to the graph or throw an IllegalArgumentException.
	 * If edgeType is not legal for this graph, this method will
	 * throw IllegalArgumentException.
	 * See Hypergraph.addEdge() for a listing of possible reasons
	 * for failure.
	 * 
	 * @param e        the edge to be added
	 * @param v1       the first vertex to be connected
	 * @param v2       the second vertex to be connected
	 * @param edgeType the type to be assigned to the edge
	 * @return true if the add is successful, false otherwise
	 * @see Hypergraph#addEdge(Object, Collection)
	 * @see #addEdge(Object, Object, Object)
	 */
	public boolean addEdge(GraphEdge e, GraphNode v1, GraphNode v2, EdgeType edgeType) {
		// NOTE: Only directed edges allowed

		if (edgeType == EdgeType.UNDIRECTED) {
			throw new IllegalArgumentException();
		}

		return addEdge(e, v1, v2);
	}

	/**
	 * Adds edge to this graph.
	 * Fails under the following circumstances:
	 * <ul>
	 * <li/>edge is already an element of the graph
	 * <li/>either edge or vertices is null
	 * <li/>vertices has the wrong number of vertices for the graph type
	 * <li/>vertices are already connected by another edge in this graph,
	 * and this graph does not accept parallel edges
	 * </ul>
	 * 
	 * @param edge     the edge to be added
	 * @param vertices the collection vertices to be added
	 * @return true if the add is successful, and false otherwise
	 * @throws IllegalArgumentException if edge or vertices is null,
	 *                                  or if a different vertex set in this graph
	 *                                  is already connected by edge,
	 *                                  or if vertices are not a legal vertex set
	 *                                  for edge
	 */
	@SuppressWarnings("unchecked")
	public boolean addEdge(GraphEdge edge, Collection<? extends GraphNode> vertices) {
		if (edge == null || vertices == null || vertices.size() != 2) {
			return false;
		}

		GraphNode[] vs = (GraphNode[]) vertices.toArray();
		return addEdge(edge, vs[0], vs[1]);
	}

	/**
	 * Adds edge to this graph with type edge_type.
	 * Fails under the following circumstances:
	 * <ul>
	 * <li/>edge is already an element of the graph
	 * <li/>either edge or vertices is null
	 * <li/>vertices has the wrong number of vertices for the graph type
	 * <li/>vertices are already connected by another edge in this graph,
	 * and this graph does not accept parallel edges
	 * <li/>edge_type is not legal for this graph
	 * </ul>
	 * 
	 * @param edge     the edge to be added
	 * @param vertices the collection vertices to be added
	 * @param edgeType the edgetype
	 * @return true if the add is successful, and false otherwise
	 * @throws IllegalArgumentException if edge or vertices is null,
	 *                                  or if a different vertex set in this graph
	 *                                  is already connected by edge,
	 *                                  or if vertices are not a legal vertex set
	 *                                  for edge
	 */
	@SuppressWarnings("unchecked")
	public boolean addEdge(GraphEdge edge, Collection<? extends GraphNode> vertices, EdgeType edgeType) {
		if (edge == null || vertices == null || vertices.size() != 2) {
			return false;
		}

		GraphNode[] vs = (GraphNode[]) vertices.toArray();
		return addEdge(edge, vs[0], vs[1], edgeType);
	}

	// ********************************************************************************
	// DO NOT EDIT ANYTHING BELOW THIS LINE EXCEPT FOR FIXING JAVADOC
	// ********************************************************************************

	/**
	 * Returns a {@code Factory} that creates an instance of this graph type.
	 * 
	 * @param <V> the vertex
	 * @param <E> the edge
	 * @return a factory
	 */
	public static <V, E> Factory<Graph<GraphNode, GraphEdge>> getFactory() {
		return new Factory<Graph<GraphNode, GraphEdge>>() {
			@SuppressWarnings("unchecked")
			public Graph<GraphNode, GraphEdge> create() {
				return (Graph<GraphNode, GraphEdge>) new FlexGraph();
			}
		};
	}

	/**
	 * Returns the edge type of edge in this graph.
	 * 
	 * @param edge the edge to be checked
	 * @return the EdgeType of edge, or null if edge has no defined type
	 */
	public EdgeType getEdgeType(GraphEdge edge) {
		return EdgeType.DIRECTED;
	}

	/**
	 * Returns the default edge type for this graph.
	 * 
	 * @return the default edge type for this graph
	 */
	public EdgeType getDefaultEdgeType() {
		return EdgeType.DIRECTED;
	}

	/**
	 * Returns the number of vertices that are incident to edge.
	 * For hyperedges, this can be any nonnegative integer; for edges this
	 * must be 2 (or 1 if self-loops are permitted). Equivalent to
	 * getIncidentVertices(edge).size().
	 * 
	 * @param edge the edge whose incident vertex count is to be returned
	 * @return the number of vertices that are incident to edge.
	 */
	public int getIncidentCount(GraphEdge edge) {
		return 2;
	}
}