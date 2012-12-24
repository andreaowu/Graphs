package graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Collections;

/* Do not add or remove public or protected members, or modify the signatures of
 * any public methods.  You may add bodies to abstract methods, modify
 * existing bodies, or override inherited methods.  */

/** A directed graph with vertices labeled with VLABEL and edges
 *  labeled with ELABEL.
 *  @author Andrea Wu
 */
public class DirectedGraph<VLabel, ELabel> extends Basic<VLabel, ELabel> {

    /** Stores incoming edges from a vertex. */
    private HashMap<Vertex<VLabel>, ArrayList<Edge<VLabel, ELabel>>> _incoming;
    /** Stores outgoing edges from a vertex. */
    private HashMap<Vertex<VLabel>, ArrayList<Edge<VLabel, ELabel>>> _outgoing;
    /** Stores all vertices in me. */
    private ArrayList<Vertex<VLabel>> _vertices;
    /** Stores all edges in me. */
    private ArrayList<Edge<VLabel, ELabel>> _edges;
    /** Given comparator. */
    private Comparator<ELabel> _comparator;

    /** Returns a new ArrayList with same contents as OLDLIST. */
    private ArrayList<Edge<VLabel, ELabel>>
    returnList(ArrayList<Edge<VLabel, ELabel>> oldList) {
        ArrayList<Edge<VLabel, ELabel>> newList
            = new ArrayList<Edge<VLabel, ELabel>>();
        for (int i = 0; i < oldList.size(); i += 1) {
            newList.add(oldList.get(i));
        }
        return newList;
    }

    /** An empty graph. */
    public DirectedGraph() {
        ArrayList<Vertex<VLabel>> vertices = new ArrayList<Vertex<VLabel>>();
        ArrayList<Edge<VLabel, ELabel>> edges = new ArrayList<Edge<VLabel,
            ELabel>>();
        _vertices = vertices;
        _edges = edges;
        HashMap<Vertex<VLabel>, ArrayList<Edge<VLabel, ELabel>>> incoming
            = new HashMap<Vertex<VLabel>, ArrayList<Edge<VLabel, ELabel>>>();
        HashMap<Vertex<VLabel>, ArrayList<Edge<VLabel, ELabel>>> outgoing
            = new HashMap<Vertex<VLabel>, ArrayList<Edge<VLabel, ELabel>>>();
        _incoming = incoming;
        _outgoing = outgoing;
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
        return true;
    }

    @Override
    public int outDegree(Vertex<VLabel> v) {
        if (_outgoing.get(v) == null) {
            return 0;
        }
        return _outgoing.get(v).size();
    }

    @Override
    public int inDegree(Vertex<VLabel> v) {
        if (_incoming.get(v) == null) {
            return 0;
        }
        return _incoming.get(v).size();
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
        ArrayList<Edge<VLabel, ELabel>> inList = new ArrayList<Edge<VLabel,
            ELabel>>();
        ArrayList<Edge<VLabel, ELabel>> outList = new ArrayList<Edge<VLabel,
        ELabel>>();
        inList.add(edgeNew);
        outList.add(edgeNew);
        if (_outgoing.containsKey(from)) {
            _outgoing.get(from).add(edgeNew);
        } else {
            _outgoing.put(from, outList);
        }
        if (_incoming.containsKey(to)) {
            _incoming.get(to).add(edgeNew);
        } else {
            _incoming.put(to, inList);
        }
        _edges.add(edgeNew);
        return edgeNew;
    }

    @Override
    public Edge<VLabel, ELabel> add(Vertex<VLabel> from, Vertex<VLabel> to) {
        Edge<VLabel, ELabel> edgeNew = new Edge<VLabel, ELabel>(from, to,
            null);
        ArrayList<Edge<VLabel, ELabel>> inList = new ArrayList<Edge<VLabel,
            ELabel>>();
        ArrayList<Edge<VLabel, ELabel>> outList = new ArrayList<Edge<VLabel,
        ELabel>>();
        inList.add(edgeNew);
        outList.add(edgeNew);
        if (_outgoing.containsKey(from)) {
            _outgoing.get(from).add(edgeNew);
        } else {
            _outgoing.put(from, outList);
        }
        if (_incoming.containsKey(to)) {
            _incoming.get(to).add(edgeNew);
        } else {
            _incoming.put(to, inList);
        }
        _edges.add(edgeNew);
        return edgeNew;
    }

    @Override
    public void remove(Vertex<VLabel> v) {
        if (_incoming.get(v) != null) {
            ArrayList<Edge<VLabel, ELabel>> incomingEdges
                = returnList(_incoming.get(v));
            for (int i = 0; i < incomingEdges.size(); i += 1) {
                _edges.remove(incomingEdges.get(i));
                _outgoing.get(incomingEdges.get(i).getV0()).remove(
                    incomingEdges.get(i));
            }
        }
        if (_outgoing.get(v) != null) {
            ArrayList<Edge<VLabel, ELabel>> outgoingEdges
                = returnList(_outgoing.get(v));
            for (int i = 0; i < outgoingEdges.size(); i += 1) {
                _edges.remove(outgoingEdges.get(i));
                _incoming.get(outgoingEdges.get(i).getV1()).remove(
                    outgoingEdges.get(i));
            }
        }
        _incoming.remove(v);
        _outgoing.remove(v);
        _vertices.remove(v);
    }

/**    @Override
    public void remove(Vertex<VLabel> v) {
        while (_incoming.get(v) != null) {
            _edges.remove(_incoming.get(v).get(0));
            _outgoing.get(_incoming.get(v).get(0).getV0()).remove(
                _incoming.get(v).get(0));
            _incoming.get(v).remove(0);
            if (_incoming.get(v).size() == 0) {
                _incoming.remove(v);
            }
        }
        while (_outgoing.get(v) != null) {
            _edges.remove(_outgoing.get(v).get(0));
            _incoming.get(_outgoing.get(v).get(0).getV1()).remove(
                _outgoing.get(v).get(0));
            _outgoing.get(v).remove(0);
            if (_outgoing.get(v).size() == 0) {
                _outgoing.remove(v);
            }
        }
        _incoming.remove(v);
        _outgoing.remove(v);
        _vertices.remove(v);
    }
*/

    /** Remove E from me, if present.  E must be between my vertices, or the
     *  result is undefined.  */
    public void remove(Edge<VLabel, ELabel> e) {
        if (_edges.remove(e)) {
            _edges.remove(e);
            Vertex<VLabel> v0 = e.getV0();
            Vertex<VLabel> v1 = e.getV1();
            _outgoing.get(v0).remove(e);
            _incoming.get(v1).remove(e);
        }
    }

    @Override
    public void remove(Vertex<VLabel> v1, Vertex<VLabel> v2) {
        ArrayList<Edge<VLabel, ELabel>> edges = returnList(_edges);
        for (int i = 0; i < edges.size(); i += 1) {
            if (edges.get(i).getV0() == v1 && edges.get(i).getV1()
                == v2) {
                _edges.remove(edges.get(i));
                _outgoing.get(v1).remove(edges.get(i));
                _incoming.get(v2).remove(edges.get(i));
            }
        }
    }

    @Override
    public Iterator<Vertex<VLabel>> vertices() {
        return new VerticesIter();
    }

    /** Class for the vertices() iterator. */
    private class VerticesIter implements Iterator<Vertex<VLabel>> {
        /** Keeps track of index. */
        private int index = 0;
        /** Keeps track of current _vertexList size. */
        private int vertexSize = 0;
        /** List of vertices to iterate over. */
        private ArrayList<Vertex<VLabel>> _vertexList;
        /** Last returned Vertex. */
        private Vertex<VLabel> lastReturned;

        /** Returns whether the list has more items. */
        public boolean hasNext() {
            ArrayList<Vertex<VLabel>> vertexList
                = new ArrayList<Vertex<VLabel>>();
            _vertexList = vertexList;
            update();
            return (index < _vertices.size());
        }

        /** Returns next item on the list. */
        public Vertex<VLabel> next() {
            if (hasNext()) {
                lastReturned = _vertices.get(index);
                index += 1;
                return lastReturned;
            }
            throw new IndexOutOfBoundsException("only " + _vertices.size()
                + " elements");
        }

        /** Removes the last returned item from the graph itself. */
        public void remove() {
            update();
            DirectedGraph.this.remove(lastReturned);
        }

        /** Updates the list that the iterator is running through. */
        private void update() {
            if (_vertices != null && vertexSize != _vertices.size()) {
                for (int i = 0; i < _vertices.size(); i += 1) {
                    if (!_vertexList.contains(_vertices.get(i))) {
                        _vertexList.add(_vertices.get(i));
                    }
                }
                vertexSize = _vertices.size();
            }
        }
    }

    @Override
    public Iterator<Vertex<VLabel>> successors(Vertex<VLabel> v) {
        return new SuccessorsIter(v);
    }

    /** Class for the successors() iterator. */
    private class SuccessorsIter implements Iterator<Vertex<VLabel>> {
        /** Keeps track of index. */
        private int index = 0;
        /** Keeps track of current _edgeList size. */
        private int edgeSize = 0;
        /** List of vertices to iterate over. */
        private ArrayList<Vertex<VLabel>> _vertexList;
        /** Last returned Vertex. */
        private Vertex<VLabel> lastReturned;
        /** Given Vertex. */
        private Vertex<VLabel> _v;

        /** Constructor that takes V. */
        public SuccessorsIter(Vertex<VLabel> v) {
            _v = v;
            ArrayList<Vertex<VLabel>> vertexList
                = new ArrayList<Vertex<VLabel>>();
            _vertexList = vertexList;
            update();
            edgeSize = _edges.size();
        }

        /** Returns whether the list has more items. */
        public boolean hasNext() {
            update();
            return (index < _vertexList.size());
        }

        /** Returns next item on the list. */
        public Vertex<VLabel> next() {
            if (hasNext()) {
                lastReturned = _vertexList.get(index);
                index += 1;
                return lastReturned;
            }
            throw new IndexOutOfBoundsException("only " + _vertexList.size()
                + " elements");
        }

        /** Removes the last returned item from the graph itself. */
        public void remove() {
            update();
            DirectedGraph.this.remove(lastReturned);
        }

        /** Updates the list that the iterator is running through. */
        private void update() {
            if (_edges != null && edgeSize != _edges.size()) {
                for (int i = 0; i < _edges.size(); i += 1) {
                    if (_edges.get(i).getV0() == _v
                        && !_vertexList.contains(_edges.get(i).getV1())) {
                        _vertexList.add(_edges.get(i).getV1());
                    }
                }
                edgeSize = _edges.size();
            }
        }
    }

    @Override
    public Iterator<Vertex<VLabel>> predecessors(Vertex<VLabel> v) {
        return new PredecessorsIter(v);
    }

    /** Class for the predecessors() iterator. */
    private class PredecessorsIter implements Iterator<Vertex<VLabel>> {
        /** Keeps track of index. */
        private int index = 0;
        /** Keeps track of current _edgeList size. */
        private int edgeSize = 0;
        /** List of vertices to iterate over. */
        private ArrayList<Vertex<VLabel>> _vertexList;
        /** Last returned Vertex. */
        private Vertex<VLabel> lastReturned;
        /** Given Vertex. */
        private Vertex<VLabel> _v;

        /** Constructor that takes V. */
        public PredecessorsIter(Vertex<VLabel> v) {
            ArrayList<Vertex<VLabel>> vertexList
                = new ArrayList<Vertex<VLabel>>();
            _vertexList = vertexList;
            _v = v;
            update();
            edgeSize = _edges.size();
        }

        /** Returns whether the list has more items. */
        public boolean hasNext() {
            update();
            return (index < _vertexList.size());
        }

        /** Returns next item on the list. */
        public Vertex<VLabel> next() {
            if (hasNext()) {
                lastReturned = _vertexList.get(index);
                index += 1;
                return lastReturned;
            }
            throw new IndexOutOfBoundsException("only " + _vertexList.size()
                + " elements");
        }

        /** Removes the last returned item from graph itself. */
        public void remove() {
            update();
            DirectedGraph.this.remove(lastReturned);
        }

        /** Updates the list that the iterator is running through. */
        private void update() {
            if (_edges != null && edgeSize != _edges.size()) {
                for (int i = 0; i < _edges.size(); i += 1) {
                    if (_edges.get(i).getV1() == _v
                        && !_vertexList.contains(_edges.get(i).getV0())) {
                        _vertexList.add(_edges.get(i).getV0());
                    }
                }
                edgeSize = _edges.size();
            }
        }
    }

    @Override
    public Iterator<Edge<VLabel, ELabel>> edges() {
        return new EdgesIter();
    }

    /** Class for the edges() iterator. */
    private class EdgesIter implements Iterator<Edge<VLabel, ELabel>> {
        /** Keeps track of index. */
        private int index = 0;
        /** Keeps track of current _edgeList size. */
        private int edgeSize = 0;
        /** List of edges to iterate over. */
        private ArrayList<Edge<VLabel, ELabel>> _edgeList;
        /** Last returned Edge. */
        private Edge<VLabel, ELabel> lastReturned;

        /** Constructor that takes V. */
        public EdgesIter() {
            ArrayList<Edge<VLabel, ELabel>> edgeList
                = new ArrayList<Edge<VLabel, ELabel>>();
            _edgeList = edgeList;
            update();
            edgeSize = _edges.size();
        }

        /** Returns whether the list has more items. */
        public boolean hasNext() {
            update();
            return (index < _edgeList.size());
        }

        /** Returns next item on the list. */
        public Edge<VLabel, ELabel> next() {
            if (hasNext()) {
                lastReturned = _edgeList.get(index);
                index += 1;
                return lastReturned;
            }
            throw new IndexOutOfBoundsException("only " + _edgeList.size()
                + " elements");
        }

        /** Removes the last returned item from the list
         * and from the graph itself. */
        public void remove() {
            update();
            DirectedGraph.this.remove(lastReturned);
        }

        /** Updates the list that the iterator is running through. */
        private void update() {
            if (_edges != null && edgeSize != _edges.size()) {
                for (int i = 0; i < _edges.size(); i += 1) {
                    if (!_edgeList.contains(_edges.get(i))) {
                        _edgeList.add(_edges.get(i));
                    }
                }
                edgeSize = _edges.size();
            }
        }
    }

    @Override
    public Iterator<Edge<VLabel, ELabel>> outEdges(Vertex<VLabel> v) {
        return new OutEdgesIter(v);
    }

    /** Class for the outEdges() iterator. */
    private class OutEdgesIter implements Iterator<Edge<VLabel, ELabel>> {
        /** Keeps track of index. */
        private int index = 0;
        /** Keeps track of current list size. */
        private int outgoingSize = 0;
        /** List of edges to iterate over. */
        private ArrayList<Edge<VLabel, ELabel>> _outgoingList;
        /** Last returned Edge. */
        private Edge<VLabel, ELabel> lastReturned;
        /** Given Vertex. */
        private Vertex<VLabel> _v;

        /** Constructor that takes V. */
        public OutEdgesIter(Vertex<VLabel> v) {
            ArrayList<Edge<VLabel, ELabel>> outgoingList
                = new ArrayList<Edge<VLabel, ELabel>>();
            _outgoingList = outgoingList;
            _v = v;
            update();
        }

        /** Returns whether the list has more items. */
        public boolean hasNext() {
            update();
            return (index < _outgoingList.size());
        }

        /** Returns next item on the list. */
        public Edge<VLabel, ELabel> next() {
            if (hasNext()) {
                lastReturned = _outgoingList.get(index);
                index += 1;
                return lastReturned;
            }
            throw new IndexOutOfBoundsException("only "
                + _outgoingList.size() + " elements");
        }

        /** Removes the last returned item from the graph itself. */
        public void remove() {
            update();
            DirectedGraph.this.remove(lastReturned);
        }

        /** Updates the list that the iterator is running through. */
        private void update() {
            if (_outgoing.get(_v) != null && outgoingSize
                != _outgoing.get(_v).size()) {
                for (int i = 0; i < _outgoing.get(_v).size(); i += 1) {
                    if (!_outgoingList.contains(_outgoing.get(_v).get(i))) {
                        _outgoingList.add(_outgoing.get(_v).get(i));
                    }
                }
                outgoingSize = _outgoing.get(_v).size();
            }
        }
    }

    @Override
    public Iterator<Edge<VLabel, ELabel>> inEdges(Vertex<VLabel> v) {
        return new InEdgesIter(v);
    }

    /** Class for the InEdges() iterator. */
    private class InEdgesIter implements Iterator<Edge<VLabel, ELabel>> {
        /** Keeps track of index. */
        private int index = 0;
        /** Keeps track of current list size. */
        private int incomingSize = 0;
        /** List of edges to iterate over. */
        private ArrayList<Edge<VLabel, ELabel>> _incomingList;
        /** Last returned Edge. */
        private Edge<VLabel, ELabel> lastReturned;
        /** Given Vertex. */
        private Vertex<VLabel> _v;

        /** Constructor that takes V. */
        public InEdgesIter(Vertex<VLabel> v) {
            ArrayList<Edge<VLabel, ELabel>> incomingList
                = new ArrayList<Edge<VLabel, ELabel>>();
            _incomingList = incomingList;
            _v = v;
            update();
        }

        /** Returns whether the list has more items. */
        public boolean hasNext() {
            update();
            return (index < _incomingList.size());
        }

        /** Returns next item on the list. */
        public Edge<VLabel, ELabel> next() {
            if (hasNext()) {
                lastReturned = _incomingList.get(index);
                index += 1;
                return lastReturned;
            }
            throw new IndexOutOfBoundsException("only "
                + _incomingList.size() + " elements");
        }

        /** Removes the last returned item from the graph itself. */
        public void remove() {
            update();
            DirectedGraph.this.remove(lastReturned);
        }

        /** Updates the list that the iterator is running through. */
        private void update() {
            if (_incoming.get(_v) != null
                && incomingSize != _incoming.get(_v).size()) {
                for (int i = 0; i < _incoming.get(_v).size(); i += 1) {
                    if (!_incomingList.contains(_incoming.get(_v).get(i))) {
                        _incomingList.add(_incoming.get(_v).get(i));
                    }
                }
                incomingSize = _incoming.get(_v).size();
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void orderEdges() {
        Collections.sort(_edges, new EdgeComparator<VLabel, ELabel>());
    }

    /** Cause subsequent traversals and calls to edges() to visit or deliver
     *  edges in sorted order, according to COMPARATOR. Subsequent
     *  addition of edges may cause the edges to be reordered
     *  arbitrarily.  */
    public void orderEdges(Comparator<ELabel> comparator) {
        _comparator = comparator;
        Collections.sort(_edges, new EdgeComparatorD());
    }

    /** Class for doing comparator methods. */
    private class EdgeComparatorD implements Comparator<Edge<VLabel, ELabel>> {

        /** Method to compare edge A and B. Returns according to
         * given comparator. */
        public int compare(Edge<VLabel, ELabel> a, Edge<VLabel, ELabel> b) {
            return _comparator.compare(a.getLabel(), b.getLabel());
        }
    }
}
