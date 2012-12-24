package graph;

import java.util.Comparator;
import java.util.Iterator;

/* Do not add or remove public or protected members, or modify the signatures of
 * any public methods.  You may add bodies to abstract methods, modify
 * existing bodies, or override inherited methods. */

/** Represents a general graph whose vertices are labeled with a type
 *  VLABEL and whose edges are labeled with a type ELABEL. The
 *  vertices are represented by the type Vertex<VLabel> and edges by
 *  Edge<VLabel, ELabel>.  A graph may be directed or undirected.  For
 *  an undirected graph, outgoing and incoming edges are the same.
 *  The vertices and edges of the graph, the edges incident on a
 *  vertex, and the neighbors of a vertex are all accessible by
 *  iterators.  Changing the graph's structure by adding or deleting
 *  edges or vertices invalidates these iterators (subsequent use of
 *  them is undefined.)
 *  @author Hilfinger
 */
public abstract class Graph<VLabel, ELabel> {

    /** Returns the number of vertices in me. */
    public abstract int vertexSize();

    /** Returns the number of edges in me. */
    public abstract int edgeSize();

    /** Returns true iff I am a directed graph. */
    public abstract boolean isDirected();

    /** Returns the number of outgoing edges incident to V. Assumes V is one of
     *  my vertices.  */
    public abstract int outDegree(Vertex<VLabel> v);

    /** Returns the number of incoming edges incident to V. Assumes V is one of
     *  my vertices. */
    public abstract int inDegree(Vertex<VLabel> v);

    /** Returns outDegree(V). This is simply a synonym, intended for
     *  use in undirected graphs. */
    public final int degree(Vertex<VLabel> v) {
        return outDegree(v);
    }

    /** Returns true iff there is an edge (U, V) in me with any label. */
    public abstract boolean contains(Vertex<VLabel> u, Vertex<VLabel> v);

    /** Returns true iff there is an edge (U, V) in me with label LABEL. */
    public abstract boolean contains(Vertex<VLabel> u, Vertex<VLabel> v,
                                     ELabel label);

    /** Returns a new vertex labeled LABEL, and adds it to me with no
     *  incident edges. */
    public abstract Vertex<VLabel> add(VLabel label);

    /** Returns an edge incident on FROM and TO, labeled with LABEL
     *  and adds it to this graph. If I am directed, the edge is directed
     *  (leaves FROM and enters TO). */
    public abstract Edge<VLabel, ELabel> add(Vertex<VLabel> from,
                                             Vertex<VLabel> to,
                                             ELabel label);

    /** Returns an edge incident on FROM and TO with a null label
     *  and adds it to this graph. If I am directed, the edge is directed
     *  (leaves FROM and enters TO). */
    public abstract Edge<VLabel, ELabel> add(Vertex<VLabel> from,
                                             Vertex<VLabel> to);

    /** Remove V and all adjacent edges, if present. */
    public abstract void remove(Vertex<VLabel> v);

    /** Remove E from me, if present.  E must be between my vertices, or the
     *  result is undefined.  */
    public abstract void remove(Edge<VLabel, ELabel> e);

    /** Remove all edges from V1 to V2 from me, if present.  The result is
     *  undefined if V1 and V2 are not among my vertices.  */
    public abstract void remove(Vertex<VLabel> v1, Vertex<VLabel> v2);

    /** Returns an Iterator over all vertices in arbitrary order. */
    public abstract Iterator<Vertex<VLabel>> vertices();

    /** Returns an iterator over all successors of V. */
    public abstract Iterator<Vertex<VLabel>> successors(Vertex<VLabel> v);

    /** Returns an iterator over all predecessors of V. */
    public abstract Iterator<Vertex<VLabel>> predecessors(Vertex<VLabel> v);

    /** Returns successors(V).  This is a synonym typically used on
     *  undirected graphs. */
    public final Iterator<Vertex<VLabel>> neighbors(Vertex<VLabel> v) {
        return successors(v);
    }

    /** Returns an iterator over all edges in me. */
    public abstract Iterator<Edge<VLabel, ELabel>> edges();

    /** Returns iterator over all outgoing edges from V. */
    public abstract Iterator<Edge<VLabel, ELabel>> outEdges(Vertex<VLabel> v);

    /** Returns iterator over all incoming edges to V. */
    public abstract Iterator<Edge<VLabel, ELabel>> inEdges(Vertex<VLabel> v);

    /** Returns outEdges(V). This is a synonym typically used
     *  on undirected graphs. */
    public final Iterator<Edge<VLabel, ELabel>> edges(Vertex<VLabel> v) {
        return outEdges(v);
    }

    /** Cause subsequent traversals and calls to edges() to visit or deliver
     *  edges in sorted order, according to the natural order on the edge
     *  labels.  Requires that ELabel be a subtype of Comparable<ELabel>.
     *  Subsequent addition of edges may cause the edges to be reordered
     *  arbitrarily. */
    public abstract void orderEdges();

    /** Cause subsequent traversals and calls to edges() to visit or deliver
     *  edges in sorted order, according to COMPARATOR. Subsequent
     *  addition of edges may cause the edges to be reordered
     *  arbitrarily.  */
    public abstract void orderEdges(Comparator<ELabel> comparator);

}
