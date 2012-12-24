package graph;

import java.util.ArrayList;
import java.util.Stack;
import java.util.Iterator;

/** A depth-first traversal of a graph.  One typically specializes
 *  this class by overriding the Visit methods, as needed. By default,
 *  no action is taken on any vertex or edge. A Visit method may
 *  throw a RejectException or a StopException to alter the course of a
 *  traversal.
 *  @author Andrea Wu
 */
public class DepthFirst<VLabel, ELabel> {

    /** The Vertex (if any) that terminated the last traversal. */
    private Vertex<VLabel> _finalVertex;
    /** The Edge (if any) that terminated the last traversal. */
    private Edge<VLabel, ELabel> _finalEdge;
    /** The Graph currently being traversed. */
    private Graph<VLabel, ELabel> _graph;
    /** The currently traversing graph's fringed vertices. */
    private Stack<Vertex<VLabel>> _fringe;
    /** The currently traversing graph's marked vertices. */
    private ArrayList<Vertex<VLabel>> _markedVertices;
    /** The marked Edges. */
    private ArrayList<Edge<VLabel, ELabel>> _markedEdges;

    /** Constructor for DepthFirst. */
    public DepthFirst() {
        Stack<Vertex<VLabel>> fringe = new Stack<Vertex<VLabel>>();
        _fringe = fringe;
        ArrayList<Vertex<VLabel>> markedVertices
            = new ArrayList<Vertex<VLabel>>();
        _markedVertices = markedVertices;
        ArrayList<Edge<VLabel, ELabel>> markedEdges
            = new ArrayList<Edge<VLabel, ELabel>>();
        _markedEdges = markedEdges;
    }

    /** Constructor for DepthFirst that takes in GRAPH. */
    public DepthFirst(Graph<VLabel, ELabel> graph) {
        Stack<Vertex<VLabel>> fringe = new Stack<Vertex<VLabel>>();
        _fringe = fringe;
        ArrayList<Vertex<VLabel>> markedVertices
            = new ArrayList<Vertex<VLabel>>();
        _markedVertices = markedVertices;
        ArrayList<Edge<VLabel, ELabel>> markedEdges
            = new ArrayList<Edge<VLabel, ELabel>>();
        _markedEdges = markedEdges;
        _graph = graph;
    }

    /** Perform a depth-first traversal of G over all vertices reachable
     *  from V, or as modified by one of the visit methods throwing an
     *  exception. */
    public void traverse(Graph<VLabel, ELabel> G, Vertex<VLabel> v) {
        _graph = G;
        if (_fringe.isEmpty()) {
            _fringe.add(v);
        }
        while (!_fringe.empty()) {
            Vertex<VLabel> vert = _fringe.pop();
            if (!_markedVertices.contains(vert)) {
                _markedVertices.add(vert);
                try {
                    _finalEdge = null;
                    _finalVertex = vert;
                    this.preVisit(vert);
                } catch (StopException e) {
                    return;
                } catch (RejectException e) {
                    continue;
                }
                Iterator<Edge<VLabel, ELabel>> iter = G.outEdges(vert);
                while (iter.hasNext()) {
                    Edge<VLabel, ELabel> nextEdge = iter.next();
                    if (!_markedEdges.contains(nextEdge)) {
                        _markedEdges.add(nextEdge);
                        try {
                            _finalVertex = null;
                            _finalEdge = nextEdge;
                            this.preVisit(nextEdge, vert);
                        } catch (StopException e) {
                            return;
                        } catch (RejectException e) {
                            String placeholder = "";
                        }
                        if (!_markedVertices.contains(nextEdge.getV1())) {
                            continueTraversing(nextEdge.getV1());
                        }
                        try {
                            _finalVertex = null;
                            _finalEdge = nextEdge;
                            this.postVisit(nextEdge, vert);
                        } catch (StopException e) {
                            return;
                        } catch (RejectException e) {
                            continue;
                        }
                    }
                }
                try {
                    _finalEdge = null;
                    _finalVertex = vert;
                    this.postVisit(vert);
                } catch (StopException e) {
                    return;
                } catch (RejectException e) {
                    continue;
                }
            }
        }
    }

    /** Continue the previous traversal starting from V.
     *  Continuing a traversal means that we do not traverse
     *  vertices or edges that have been traversed previously. */
    public void continueTraversing(Vertex<VLabel> v) {
        traverse(theGraph(), v);
    }

    /** If the traversal ends prematurely, returns the Vertex argument to
     *  preVisit or postVisit that caused a Visit stop the traversal.
     *  Otherwise, returns null. */
    public Vertex<VLabel> finalVertex() {
        return _finalVertex;
    }

    /** If the traversal ends prematurely, returns the Edge argument to
     *  preVisit or postVisit that caused a Visit routine to stop the
     *  traversal. If it was not an edge that caused termination,
     *  returns null. */
    public Edge<VLabel, ELabel> finalEdge() {
        return _finalEdge;
    }

    /** Returns the graph currently being traversed.  Undefined if no traversal
     *  is in progress. */
    protected Graph<VLabel, ELabel> theGraph() {
        return _graph;
    }

    /** Method to be called on the first visit to vertex V in
     *  a traversal, before visiting the unvisited successors
     *  of V.  If this routine throws a StopException, the traversal ends.
     *  If it throws a RejectException, outgoing edges are not considered.
     *  The default does nothing.  */
    protected void preVisit(Vertex<VLabel> v) {
    }

    /** Method to be called on leaving vertex V after visiting all its
     *  unvisited successors in a traversal.  If this routine throws
     *  a StopException, the traversal ends.  A RejectException has no effect.
     *  The default simply returns true. */
    protected void postVisit(Vertex<VLabel> v) {
    }

    /** Method to be called when traversing an edge E from vertex V0
     *  during a traversal.  If this routine throws a StopException,
     *  the traversal ends. If it throws a RejectException, the node at the
     *  other end of the edge is not traversed.  The default does nothing. */
    protected void preVisit(Edge<VLabel, ELabel> e, Vertex<VLabel> v0) {
    }

    /** Method to be called after traversing the edge E from V0
     *  and finishing the traversal from the other incident vertex.
     *  If this routine throws a StopException, the traversal ends.
     *  A RejectException has no effect. The default does nothing. */
    protected void postVisit(Edge<VLabel, ELabel> e, Vertex<VLabel> v0) {
    }

}
