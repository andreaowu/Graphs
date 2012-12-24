package graph;

import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Iterator;

/** Implements a generalized traversal of a graph.  At any given time,
 *  there is a particular set of untraversed vertices---the "fringe."
 *  Traversal consists of repeatedly removing an untraversed vertex
 *  from the fringe, visting it, and then adding its untraversed
 *  successors to the fringe.  The client can dictate an ordering on
 *  the fringe, determining which item is next removed, by means of
 *  a Comparator argument given to the traverse method.  By default,
 *  no action is taken on any vertex or edge.  A Visit method may
 *  throw a RejectException or a StopException to alter the course of a
 *  traversal.
 *  @author Andrea Wu
 */
public class Traversal<VLabel, ELabel> {

    /** The Vertex (if any) that terminated the last traversal. */
    protected Vertex<VLabel> _finalVertex;
    /** The Edge (if any) that terminated the last traversal. */
    protected Edge<VLabel, ELabel> _finalEdge;
    /** The graph currently being traversed. */
    protected Graph<VLabel, ELabel> _graph;
    /** Current fringe. */
    private ArrayList<Vertex<VLabel>> _fringe;
    /** Visited vertices. */
    private ArrayList<Vertex<VLabel>> _markedVertices;
    /** Visited edges. */
    private ArrayList<Edge<VLabel, ELabel>> _markedEdges;
    /** Comparator for VLabels. */
    private Comparator<VLabel> _order;

    /** Constructor for Traversal. */
    public Traversal() {
        ArrayList<Vertex<VLabel>> fringe = new ArrayList<Vertex<VLabel>>();
        _fringe = fringe;
        ArrayList<Vertex<VLabel>> markedVertices
            = new ArrayList<Vertex<VLabel>>();
        _markedVertices = markedVertices;
        ArrayList<Edge<VLabel, ELabel>> markedEdges
            = new ArrayList<Edge<VLabel, ELabel>>();
        _markedEdges = markedEdges;
    }

    /** Constructor for Traversal that takes in GRAPH. */
    public Traversal(Graph<VLabel, ELabel> graph) {
        _graph = graph;
        ArrayList<Vertex<VLabel>> fringe = new ArrayList<Vertex<VLabel>>();
        _fringe = fringe;
        ArrayList<Vertex<VLabel>> markedVertices
            = new ArrayList<Vertex<VLabel>>();
        _markedVertices = markedVertices;
        ArrayList<Edge<VLabel, ELabel>> markedEdges
            = new ArrayList<Edge<VLabel, ELabel>>();
        _markedEdges = markedEdges;
    }

    /** Edge Comparator class. */
    private class VertexComparator implements Comparator<Vertex<VLabel>> {

        /** Method to compare edge A and B. Returns according
         * to the given comparator. */
        public int compare(Vertex<VLabel> a, Vertex<VLabel> b) {
            return _order.compare(a.getLabel(), b.getLabel());
        }
    }

    /** Perform a traversal of G over all vertices reachable from V.
     *  ORDER determines the ordering in which the successors to the
     *  set of traversed vertices are visited. */
    public void traverse(Graph<VLabel, ELabel> G, Vertex<VLabel> v,
        Comparator<VLabel> order) {
        _graph = G;
        if (_fringe.isEmpty()) {
            _fringe.add(v);
        }
        _order = order;
        while (!_fringe.isEmpty()) {
            Collections.sort(_fringe, new VertexComparator());
            Vertex<VLabel> vert = _fringe.get(0);
            if (!_markedVertices.contains(vert)) {
                _markedVertices.add(vert);
                try {
                    _finalEdge = null;
                    _finalVertex = vert;
                    this.visit(vert);
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
                            continue;
                        }
                        if (!_fringe.contains(nextEdge.getV1())) {
                            _fringe.add(nextEdge.getV1());
                        }
                    }
                }
            }
            _fringe.remove(0);
        }
    }

    /** Continue the previous traversal starting from V.
     *  Continuing a traversal means that we do not traverse
     *  vertices or edges that have been traversed previously. */
    public void continueTraversing(Vertex<VLabel> v) {
        traverse(theGraph(), v, _order);
    }

    /** If the traversal ends prematurely, returns the Vertex argument to
     *  preVisit that caused a Visit routine to return false.  Otherwise,
     *  returns null. */
    public Vertex<VLabel> finalVertex() {
        return _finalVertex;
    }

    /** If the traversal ends prematurely, returns the Edge argument to
     *  preVisit or postVisit that caused a Visit routine to return false.
     *  If it was not an edge that caused termination, returns null. */
    public Edge<VLabel, ELabel> finalEdge() {
        return _finalEdge;
    }

    /** Returns the graph currently being traversed.  Undefined if no traversal
     *  is in progress. */
    protected Graph<VLabel, ELabel> theGraph() {
        return _graph;
    }

    /** Method to be called when adding the node at the other end of E from V0
     *  to the fringe. If this routine throws a StopException,
     *  the traversal ends.  If it throws a RejectException, the edge
     *  E is not traversed. The default does nothing.
     */
    protected void preVisit(Edge<VLabel, ELabel> e, Vertex<VLabel> v0) {
    }

    /** Method to be called when visiting vertex V.  If this routine throws
     *  a StopException, the traversal ends.  If it throws a RejectException,
     *  successors of V do not get visited from V. The default does nothing. */
    protected void visit(Vertex<VLabel> v) {
    }

}
