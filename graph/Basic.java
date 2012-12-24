package graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Arrays;

/* Do not add public or protected members, or modify their signatures.
 * You may add bodies to abstract methods, modify existing bodies, or
 * override inherited methods.  You may also eliminate this class
 * entirely, as long as Directed and Undirected continue to be
 * subtypes of Graph. */

/** An (unexported) implementation class containing definitions used
 *  both by Undirected<VLABEL,ELABEL> and Directed<VLABEL,ELABEL>.
 *  @author Andrea Wu
 */
class Basic<VLabel, ELabel> extends Graph<VLabel, ELabel> {

    /** Stores all vertices in me. */
    private ArrayList<Vertex<VLabel>> _vertices;
    /** Stores all edges in me. */
    private ArrayList<Edge<VLabel, ELabel>> _edges;

    /** Constructor for this class that is empty. */
    public Basic() {
        ArrayList<Vertex<VLabel>> vertices = new ArrayList<Vertex<VLabel>>();
        ArrayList<Edge<VLabel, ELabel>> edges = new ArrayList<Edge<VLabel,
            ELabel>>();
        _vertices = vertices;
        _edges = edges;
    }

    /** Constructor for this class that takes an initial V and E. */
    public Basic(Vertex<VLabel> v, Edge<VLabel, ELabel> e) {
        ArrayList<Vertex<VLabel>> vertices = new ArrayList<Vertex<VLabel>>();
        ArrayList<Edge<VLabel, ELabel>> edges = new ArrayList<Edge<VLabel,
            ELabel>>();
        vertices.add(v);
        edges.add(e);
        _vertices = vertices;
        _edges = edges;
    }

    @Override
    public int vertexSize() {
        return _vertices.size();
    }

    @Override
    public int edgeSize() {
        return _edges.size();
    }

    @Override
    public boolean isDirected() {
        System.err.println("Error: Basic isDirected()");
        return false;
    }

    @Override
    public int outDegree(Vertex<VLabel> v) {
        System.err.println("Error: Basic outDegree(Vertex<VLabel> v)");
        return -1;
    }

    @Override
    public int inDegree(Vertex<VLabel> v) {
        System.err.println("Error: Basic inDegree(Vertex<VLabel> v)");
        return -1;
    }

    @Override
    public boolean contains(Vertex<VLabel> u, Vertex<VLabel> v) {
        for (int i = 0; i < _edges.size(); i += 1) {
            if (_edges.get(i).getV0() == u && _edges.get(i).getV1() == v) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean contains(Vertex<VLabel> u, Vertex<VLabel> v, ELabel label) {
        for (int i = 0; i < _edges.size(); i += 1) {
            Edge<VLabel, ELabel> e = _edges.get(i);
            if (e.getV0() == u && e.getV1() == v && e.getLabel() == label) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Vertex<VLabel> add(VLabel label) {
        Vertex<VLabel> addThis = new Vertex<VLabel>(label);
        _vertices.add(addThis);
        return addThis;
    }

    @Override
    public Edge<VLabel, ELabel> add(Vertex<VLabel> from, Vertex<VLabel> to,
        ELabel label) {
        Edge<VLabel, ELabel> edgeNew = new Edge<VLabel, ELabel>(from, to,
            label);
        _edges.add(edgeNew);
        return edgeNew;
    }

    @Override
    public Edge<VLabel, ELabel> add(Vertex<VLabel> from, Vertex<VLabel> to) {
        Edge<VLabel, ELabel> edgeN = new Edge<VLabel, ELabel>(from, to, null);
        _edges.add(edgeN);
        return edgeN;
    }

    @Override
    public void remove(Vertex<VLabel> v) {
        _vertices.remove(v);
    }

    @Override
    public void remove(Edge<VLabel, ELabel> e) {
        _edges.remove(e);
    }

    @Override
    public void remove(Vertex<VLabel> v1, Vertex<VLabel> v2) {
        System.err.println("Error: Basic remove(Vertex<VLabel> v1,"
            + "Vertex<VLabel> v2)");
    }

    @Override
    public Iterator<Vertex<VLabel>> vertices() {
        Iterator<Vertex<VLabel>> iter = _vertices.iterator();
        return iter;
    }

    @Override
    public Iterator<Vertex<VLabel>> successors(Vertex<VLabel> v) {
        ArrayList<Vertex<VLabel>> answer = new ArrayList<Vertex<VLabel>>();
        for (int i = 0; i < _edges.size(); i += 1) {
            if (_edges.get(i).getV0() == v) {
                answer.add(_edges.get(i).getV1());
            }
        }
        Iterator<Vertex<VLabel>> iter = answer.iterator();
        return iter;
    }

    @Override
    public Iterator<Vertex<VLabel>> predecessors(Vertex<VLabel> v) {
        ArrayList<Vertex<VLabel>> answer = new ArrayList<Vertex<VLabel>>();
        for (int i = 0; i < _edges.size(); i += 1) {
            if (_edges.get(i).getV1() == v) {
                answer.add(_edges.get(i).getV0());
            }
        }
        Iterator<Vertex<VLabel>> iter = answer.iterator();
        return iter;
    }

    @Override
    public Iterator<Edge<VLabel, ELabel>> edges() {
        Iterator<Edge<VLabel, ELabel>> iter = _edges.iterator();
        return iter;
    }

    @Override
    public Iterator<Edge<VLabel, ELabel>> outEdges(Vertex<VLabel> v) {
        System.err.println("Error: Basic outEdges(Vertex<VLabel> v)");
        return null;
    }

    @Override
    public Iterator<Edge<VLabel, ELabel>> inEdges(Vertex<VLabel> v) {
        System.err.println("Error: Basic inEdges(Vertex<VLabel> v)");
        return null;
    }

    @Override
    public void orderEdges() {
    }

    @Override
    public void orderEdges(Comparator<ELabel> comparator) {
        @SuppressWarnings("unchecked")
        ELabel[] copy = (ELabel[]) new Object[_edges.size()];
        ArrayList<Edge<VLabel, ELabel>> sorted = new ArrayList<Edge<VLabel,
            ELabel>>();
        for (int i = 0; i < _edges.size(); i += 1) {
            copy[i] = _edges.get(i).getLabel();
        }
        Arrays.sort(copy, comparator);
        for (int j = 0; j < _edges.size(); j += 1) {
            boolean tF = true;
            for (int k = 0; k < _edges.size() && tF; k += 1) {
                if (copy[j] == _edges.get(k).getLabel()) {
                    sorted.add(_edges.get(k));
                    _edges.remove(_edges.get(k));
                    tF = false;
                }
            }
        }
        _edges = sorted;
    }
}
