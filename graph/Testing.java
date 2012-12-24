package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Comparator;

import org.junit.Test;
import ucb.junit.textui;
import static org.junit.Assert.*;

/** Unit tests for the graph package. */
public class Testing {

    /** Edge Comparator class for Testing file. */
    class EdgeComparatorTesting<ELabel> implements Comparator<ELabel> {
        @SuppressWarnings("unchecked")
       /** Method to compare edge A and B. Returns -1, 0, 1 for >, =, <. */
        public int compare(ELabel a, ELabel b) {
            return ((Comparable<ELabel>) a).compareTo(b);
        }
    }

    /** Vertex Comparator class for Testing file. */
    class VertexComparatorTesting<VLabel> implements Comparator<VLabel> {
        @SuppressWarnings("unchecked")
       /** Method to compare edge A and B. Returns -1, 0, 1 for >, =, <. */
        public int compare(VLabel a, VLabel b) {
            return ((Comparable<VLabel>) a).compareTo(b);
        }
    }

    /** ELabel class for Testing file. */
    private class ELabel implements Comparable<ELabel>, Weighted {

        /** Private instance variable. */
        private String _s;
        /** Private instance variable. */
        private int _i;

        /** Constructor for ELabel that takes in S. */
        protected ELabel(String s) {
            _s = s;
        }

        /** Constructor for ELabel that takes in S. */
        protected ELabel(int i) {
            _i = i;
        }

        /** Returns _i. */
        private int getI() {
            return _i;
        }

        /** Returns positive, negative, 0 integer for this > E, < E, or = E. */
        public int compareTo(ELabel e) {
            return _i - e.getI();
        }

        @Override
        public double weight() {
            return _i;
        }
    }

    /** VLabel class for Testing file. */
    private class VLabel implements Comparable<VLabel>, Weightable {

        /** Private instance variable. */
        private String _s;
        /** Private instance variable. */
        private double _weight;

        /** Constructor for VLabel that takes in S. */
        protected VLabel(String s) {
            _s = s;
        }

        /** Returns _s. */
        protected String getS() {
            return _s;
        }

        /** Returns positive, negative, 0 integer for this > E, < E, or = E. */
        public int compareTo(VLabel v) {
            return _s.compareTo(v.getS());
        }

        @Override
        public void setWeight(double w) {
            this._weight = w;
        }

        @Override
        public double weight() {
            return _weight;
        }
    }

    /** Weighting VLabel class for Testing file. */
    private class WeightingTesting<Item> implements Weighting<Item> {

        /** Stores incoming edges from a vertex. */
        protected HashMap<Item, Double> _weight;

        @Override
        public double weight(Item v) {
            return _weight.get(v).doubleValue();
        }
    }

    /** Weighter VLabel class for Testing file. */
    private class WeighterTesting<Item> extends WeightingTesting<Item>
        implements Weighter<Item> {

        @Override
        public void setWeight(Item v, double d) {
            _weight.put(v, new Double(d));
        }
    }

    /** Extends Traversal so I can print out pre/post visits. */
    private class TraversalTesting<VLabel, ELabel>
        extends Traversal<VLabel, ELabel> {
        @Override
        protected void preVisit(Edge<VLabel, ELabel> e, Vertex<VLabel> v0) {
            System.out.println("previsit " + e.getLabel() + " "
                + v0.getLabel());
        }

        @Override
        protected void visit(Vertex<VLabel> v) {
            System.out.println("visit " + v.getLabel());
        }
    }

    /** Run all JUnit tests in the graph package. */
    public static void main(String[] ignored) {
        textui.runClasses(graph.Testing.class, graph.Testing2.class,
            graph.Testing3.class);
    }

    @Test
    public void emptyGraph() {
        DirectedGraph g = new DirectedGraph();
        assertEquals("Initial graph has vertices", 0, g.vertexSize());
        assertEquals("Initial graph has edges", 0, g.edgeSize());
    }

    /* DIRECTEDGRAPH CLASS. */

    /** vertexSize(). */
    @Test
    public void vertexSizeDG1() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        assertEquals(0, dg.vertexSize());
    }

    /** edgeSize(). */
    @Test
    public void edgeSizeDG1() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        assertEquals(0, dg.edgeSize());
    }

    /** isDirected(). */
    @Test
    public void isDirectedDG1() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        assertTrue(dg.isDirected());
    }

    /** outDegree(Vertex<VLabel> v). */
    @Test
    public void outdegreeDG1() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> u = dg.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v = dg.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> w = dg.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> z = dg.add(vl4);
        VLabel vl5 = new VLabel("vl5");
        Vertex<VLabel> a = dg.add(vl5);
        ELabel el1 = new ELabel("el1");
        ELabel el2 = new ELabel("el2");
        ELabel el3 = new ELabel("el3");
        ELabel el4 = new ELabel("el4");
        dg.add(u, v, el1);
        dg.add(u, w, el2);
        dg.add(u, z, el3);
        dg.add(u, a, el4);
        assertEquals(4, dg.outDegree(u));
    }

    /** inDegree(Vertex<VLabel> v). */
    @Test
    public void indegreeDG1() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> u = dg.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v = dg.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> w = dg.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> z = dg.add(vl4);
        VLabel vl5 = new VLabel("vl5");
        Vertex<VLabel> a = dg.add(vl5);
        ELabel el1 = new ELabel("el1");
        ELabel el2 = new ELabel("el2");
        ELabel el3 = new ELabel("el3");
        ELabel el4 = new ELabel("el4");
        dg.add(v, u, el1);
        dg.add(w, u, el2);
        dg.add(z, u, el3);
        dg.add(a, u, el4);
        assertEquals(4, dg.inDegree(u));
    }

    /** contains(Vertex<VLabel> u, Vertex<VLabel> v). */
    @Test
    public void containsUVdg1() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> u = dg.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v = dg.add(vl2);
        ELabel el1 = new ELabel("el1");
        dg.add(u, v, el1);
        assertTrue(dg.contains(u, v));
    }

    @Test
    public void containsUVdg2() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> u = dg.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v = dg.add(vl2);
        ELabel el1 = new ELabel("el1");
        dg.add(u, v);
        assertFalse(dg.contains(v, u));
    }

    /** contains(Vertex<VLabel> u, Vertex<VLabel> v, ELabel label). */
    @Test
    public void containsUVLdg1() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> u = dg.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v = dg.add(vl2);
        ELabel el1 = new ELabel("el1");
        dg.add(u, v, el1);
        assertTrue(dg.contains(u, v, el1));
    }

    @Test
    public void containsUVLdg2() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> u = dg.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v = dg.add(vl2);
        ELabel el1 = new ELabel("el1");
        dg.add(u, v, el1);
        assertFalse(dg.contains(v, u, el1));
    }

    /** add(VLabel label). */
    @Test
    public void addLdg1() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel label = new VLabel("vl1");
        Vertex<VLabel> addThis = dg.add(label);
        assertEquals(1, dg.vertexSize());
    }

    /** add(Vertex<VLabel> from, Vertex<VLabel> to, ELabel label). */
    @Test
    public void addFRLdg1() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> from = dg.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> to = dg.add(vl2);
        ELabel label = new ELabel("edgelabel");
        Edge<VLabel, ELabel> addThis = dg.add(from, to, label);
        assertTrue(dg.contains(from, to, label));
        assertEquals(1, dg.edgeSize());
    }

    /** add(Vertex<VLabel> from, Vertex<VLabel> to). */
    @Test
    public void addFTdG1() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> from = dg.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> to = dg.add(vl2);
        Edge<VLabel, ELabel> addThis = dg.add(from, to, null);
        assertTrue(dg.contains(from, to));
        assertEquals(1, dg.edgeSize());
    }

    /** remove(Vertex<VLabel> v). */
    /** Remove V and all adjacent edges, if present. */
    @Test
    public void removeVdG1() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel label = new VLabel("vl1");
        Vertex<VLabel> addThis = dg.add(label);
        assertEquals(dg.vertexSize(), 1);
        dg.remove(addThis);
        assertEquals(0, dg.vertexSize());
    }

    @Test
    public void removeVdG2() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = dg.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = dg.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = dg.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = dg.add(vl4);
        ELabel el1 = new ELabel("el1");
        ELabel el2 = new ELabel("el2");
        ELabel el3 = new ELabel("el3");
        Edge<VLabel, ELabel> addThis1 = dg.add(v1, v2, el1);
        Edge<VLabel, ELabel> addThis2 = dg.add(v1, v3, el2);
        Edge<VLabel, ELabel> addThis3 = dg.add(v1, v4, el3);
        dg.remove(v1);
        assertEquals(3, dg.vertexSize());
        assertEquals(0, dg.edgeSize());
    }

    @Test
    public void removeVdG3() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = dg.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = dg.add(vl2);
        ELabel el1 = new ELabel("el1");
        Edge<VLabel, ELabel> addThis1 = dg.add(v1, v2, el1);
        dg.remove(v1);
        assertEquals(1, dg.vertexSize());
        assertEquals(0, dg.edgeSize());
    }

    @Test
    public void removeVdG4() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = dg.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = dg.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = dg.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = dg.add(vl4);
        ELabel el1 = new ELabel("el1");
        ELabel el2 = new ELabel("el2");
        ELabel el3 = new ELabel("el3");
        Edge<VLabel, ELabel> addThis1 = dg.add(v1, v2, el1);
        Edge<VLabel, ELabel> addThis2 = dg.add(v1, v3, el2);
        Edge<VLabel, ELabel> addThis3 = dg.add(v1, v4, el3);
        dg.remove(v2);
        assertEquals(3, dg.vertexSize());
        assertEquals(2, dg.edgeSize());
    }

    @Test
    public void removeVdG5() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = dg.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = dg.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = dg.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = dg.add(vl4);
        ELabel el1 = new ELabel("el1");
        ELabel el2 = new ELabel("el2");
        ELabel el3 = new ELabel("el3");
        Edge<VLabel, ELabel> addThis1 = dg.add(v1, v2, el1);
        Edge<VLabel, ELabel> addThis2 = dg.add(v1, v3, el2);
        Edge<VLabel, ELabel> addThis3 = dg.add(v4, v2, el3);
        dg.remove(v2);
        assertEquals(3, dg.vertexSize());
        assertEquals(1, dg.edgeSize());
    }

    @Test
    public void removeVdG6() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = dg.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = dg.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = dg.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = dg.add(vl4);
        ELabel el1 = new ELabel("el1");
        ELabel el2 = new ELabel("el2");
        ELabel el3 = new ELabel("el3");
        Edge<VLabel, ELabel> addThis1 = dg.add(v2, v1, el1);
        Edge<VLabel, ELabel> addThis2 = dg.add(v1, v3, el2);
        Edge<VLabel, ELabel> addThis3 = dg.add(v4, v1, el3);
        dg.remove(v1);
        assertEquals(3, dg.vertexSize());
        assertEquals(0, dg.edgeSize());
    }

    /** remove(Edge<VLabel, ELabel> e). */
    @Test
    public void removeEdG1() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> from = dg.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> to = dg.add(vl2);
        ELabel label = new ELabel("edgelabel");
        Edge<VLabel, ELabel> addThis = dg.add(from, to, label);
        dg.remove(addThis);
        assertEquals(0, dg.edgeSize());
    }

    @Test
    public void removeEdG2() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> from = dg.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> to = dg.add(vl2);
        Edge<VLabel, ELabel> addThis = dg.add(from, to);
        dg.remove(addThis);
        assertEquals(0, dg.edgeSize());
    }

    @Test
    public void removeEdG3() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> from = dg.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> to = dg.add(vl2);
        ELabel label = new ELabel("edgelabel");
        Edge<VLabel, ELabel> addThis = dg.add(from, to, label);
        Edge<VLabel, ELabel> addThis1 = dg.add(from, to, label);
        dg.remove(addThis);
        assertEquals(1, dg.edgeSize());
    }

    @Test
    public void removeEdG4() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> from = dg.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> to = dg.add(vl2);
        Edge<VLabel, ELabel> addThis = dg.add(from, to);
        Edge<VLabel, ELabel> addThis1 = dg.add(from, to);
        dg.remove(addThis);
        assertEquals(1, dg.edgeSize());
    }

    @Test
    public void removeEdG5() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = dg.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = dg.add(vl2);
        ELabel label = new ELabel("edgelabel");
        Edge<VLabel, ELabel> addThis = dg.add(v1, v2, label);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = dg.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = dg.add(vl4);
        ELabel label2 = new ELabel("edgelabel2");
        Edge<VLabel, ELabel> addThis2 = dg.add(v3, v4, label2);
        dg.remove(addThis);
        assertTrue(dg.contains(v3, v4, label2));
        assertEquals(1, dg.edgeSize());
    }

    @Test
    public void removeEdG6() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = dg.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = dg.add(vl2);
        Edge<VLabel, ELabel> addThis = dg.add(v1, v2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = dg.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = dg.add(vl4);
        Edge<VLabel, ELabel> addThis2 = dg.add(v3, v4);
        dg.remove(addThis);
        assertTrue(dg.contains(v3, v4));
        assertEquals(1, dg.edgeSize());
    }

    /** remove(Vertex<VLabel> v1, Vertex<VLabel> v2). */
    /** Remove all edges from V1 to V2 from me, if present.  The result is
     *  undefined if V1 and V2 are not among my vertices.  */

    @Test
    public void removeVVdG1() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = dg.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = dg.add(vl2);
        ELabel el1 = new ELabel("el1");
        Edge<VLabel, ELabel> e1 = dg.add(v1, v2, el1);
        dg.remove(v1, v2);
        assertEquals(2, dg.vertexSize());
        assertEquals(0, dg.edgeSize());
    }

    @Test
    public void removeVVdG2() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = dg.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = dg.add(vl2);
        ELabel el1 = new ELabel("el1");
        Edge<VLabel, ELabel> e1 = dg.add(v1, v2, el1);
        ELabel el2 = new ELabel("el2");
        Edge<VLabel, ELabel> e2 = dg.add(v1, v2, el2);
        ELabel el3 = new ELabel("el3");
        Edge<VLabel, ELabel> e3 = dg.add(v1, v2, el3);
        dg.remove(v1, v2);
        assertEquals(2, dg.vertexSize());
        assertEquals(0, dg.edgeSize());
    }

    /** vertices(). */
    @Test
    public void verticesDG1() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        ArrayList<Vertex<VLabel>> vertices = new ArrayList<Vertex<VLabel>>();
        Iterator<Vertex<VLabel>> iter = vertices.iterator();
        Iterator<Vertex<VLabel>> method = dg.vertices();
        for (int i = 0; i < vertices.size(); i += 1) {
            assertEquals(iter.next(), method.next());
        }
    }

    @Test
    public void verticesDG2() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        ArrayList<Vertex<VLabel>> vertices = new ArrayList<Vertex<VLabel>>();
        VLabel vl1 = new VLabel("vl1");
        VLabel vl2 = new VLabel("vl2");
        vertices.add(dg.add(vl1));
        vertices.add(dg.add(vl2));
        Iterator<Vertex<VLabel>> iter = vertices.iterator();
        Iterator<Vertex<VLabel>> method = dg.vertices();
        ArrayList<Vertex<VLabel>> methodVertices =
            new ArrayList<Vertex<VLabel>>();
        while (method.hasNext()) {
            methodVertices.add(method.next());
        }
        assertTrue(vertices.size() == methodVertices.size());
        assertTrue(vertices.containsAll(methodVertices));
    }

    @Test
    public void verticesDG3() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = dg.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = dg.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = dg.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = dg.add(vl4);
        ArrayList<Vertex<VLabel>> vertexList = new ArrayList<Vertex<VLabel>>();
        vertexList.add(v1);
        vertexList.add(v2);
        vertexList.add(v3);
        vertexList.add(v4);
        Iterator<Vertex<VLabel>> iter = vertexList.iterator();
        Iterator<Vertex<VLabel>> method = dg.vertices();
        ArrayList<Vertex<VLabel>> methodVertices =
            new ArrayList<Vertex<VLabel>>();
        while (method.hasNext()) {
            methodVertices.add(method.next());
        }
        assertTrue(vertexList.size() == methodVertices.size());
        assertTrue(vertexList.containsAll(methodVertices));
    }

    @Test
    public void verticesDG4() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        VLabel vl2 = new VLabel("vl2");
        VLabel vl3 = new VLabel("vl3");
        VLabel vl4 = new VLabel("vl4");
        ArrayList<Vertex<VLabel>> vertexList = new ArrayList<Vertex<VLabel>>();
        vertexList.add(dg.add(vl1));
        vertexList.add(dg.add(vl2));
        vertexList.add(dg.add(vl3));
        vertexList.add(dg.add(vl4));
        Iterator<Vertex<VLabel>> iter = vertexList.iterator();
        Iterator<Vertex<VLabel>> method = dg.vertices();
        ArrayList<Vertex<VLabel>> methodVertices =
            new ArrayList<Vertex<VLabel>>();
        while (method.hasNext()) {
            methodVertices.add(method.next());
        }
        assertTrue(vertexList.size() == methodVertices.size());
        assertTrue(vertexList.containsAll(methodVertices));
    }

    @Test
    public void verticesDG5() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = dg.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = dg.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = dg.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = dg.add(vl4);
        ArrayList<Vertex<VLabel>> vertexList = new ArrayList<Vertex<VLabel>>();
        vertexList.add(v1);
        vertexList.add(v2);
        Iterator<Vertex<VLabel>> iter = vertexList.iterator();
        Iterator<Vertex<VLabel>> method = dg.vertices();
        vertexList.add(v3);
        vertexList.add(v4);
        ArrayList<Vertex<VLabel>> methodVertices =
            new ArrayList<Vertex<VLabel>>();
        while (method.hasNext()) {
            methodVertices.add(method.next());
        }
        assertTrue(vertexList.size() == methodVertices.size());
        assertTrue(vertexList.containsAll(methodVertices));
    }

    @Test
    public void verticesDG6() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        VLabel vl2 = new VLabel("vl2");
        VLabel vl3 = new VLabel("vl3");
        VLabel vl4 = new VLabel("vl4");
        ArrayList<Vertex<VLabel>> vertexList = new ArrayList<Vertex<VLabel>>();
        vertexList.add(dg.add(vl1));
        vertexList.add(dg.add(vl2));
        Iterator<Vertex<VLabel>> iter = vertexList.iterator();
        Iterator<Vertex<VLabel>> method = dg.vertices();
        vertexList.add(dg.add(vl3));
        vertexList.add(dg.add(vl4));
        ArrayList<Vertex<VLabel>> methodVertices =
            new ArrayList<Vertex<VLabel>>();
        while (method.hasNext()) {
            methodVertices.add(method.next());
        }
        assertTrue(vertexList.size() == methodVertices.size());
        assertTrue(vertexList.containsAll(methodVertices));
    }

    /** successors(Vertex<VLabel> v). */
    @Test
    public void successorsDG1() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v = dg.add(vl1);
        ArrayList<Vertex<VLabel>> answer = new ArrayList<Vertex<VLabel>>();
        Iterator<Vertex<VLabel>> iter = answer.iterator();
        Iterator<Vertex<VLabel>> method = dg.successors(v);
        for (int i = 0; i < answer.size(); i += 1) {
            assertEquals(iter.next(), method.next());
        }
    }

    @Test
    public void successorsDG2() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = dg.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = dg.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = dg.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = dg.add(vl4);
        ELabel el1 = new ELabel("el1");
        ELabel el2 = new ELabel("el2");
        ELabel el3 = new ELabel("el3");
        dg.add(v1, v2, el1);
        dg.add(v1, v3, el2);
        dg.add(v1, v4, el3);
        ArrayList<Vertex<VLabel>> vertexList = new ArrayList<Vertex<VLabel>>();
        vertexList.add(v2);
        vertexList.add(v3);
        vertexList.add(v4);
        Iterator<Vertex<VLabel>> iter = vertexList.iterator();
        Iterator<Vertex<VLabel>> method = dg.successors(v1);
        ArrayList<Vertex<VLabel>> methodVertices =
            new ArrayList<Vertex<VLabel>>();
        while (method.hasNext()) {
            methodVertices.add(method.next());
        }
        assertTrue(vertexList.size() == methodVertices.size());
        assertTrue(vertexList.containsAll(methodVertices));
    }

    @Test
    public void successorsDG3() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = dg.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = dg.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = dg.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = dg.add(vl4);
        dg.add(v1, v2);
        dg.add(v1, v3);
        dg.add(v1, v4);
        ArrayList<Vertex<VLabel>> vertexList = new ArrayList<Vertex<VLabel>>();
        vertexList.add(v2);
        vertexList.add(v3);
        vertexList.add(v4);
        Iterator<Vertex<VLabel>> iter = vertexList.iterator();
        Iterator<Vertex<VLabel>> method = dg.successors(v1);
        ArrayList<Vertex<VLabel>> methodVertices =
            new ArrayList<Vertex<VLabel>>();
        while (method.hasNext()) {
            methodVertices.add(method.next());
        }
        assertTrue(vertexList.size() == methodVertices.size());
        assertTrue(vertexList.containsAll(methodVertices));
    }

    @Test
    public void successorsDG4() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = dg.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = dg.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = dg.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = dg.add(vl4);
        ELabel el1 = new ELabel("el1");
        ELabel el2 = new ELabel("el2");
        ELabel el3 = new ELabel("el3");
        dg.add(v1, v2, el1);
        dg.add(v1, v3, el2);
        ArrayList<Vertex<VLabel>> vertexList = new ArrayList<Vertex<VLabel>>();
        vertexList.add(v2);
        vertexList.add(v3);
        vertexList.add(v4);
        Iterator<Vertex<VLabel>> iter = vertexList.iterator();
        Iterator<Vertex<VLabel>> method = dg.successors(v1);
        dg.add(v1, v4, el3);
        ArrayList<Vertex<VLabel>> methodVertices =
            new ArrayList<Vertex<VLabel>>();
        while (method.hasNext()) {
            methodVertices.add(method.next());
        }
        assertTrue(vertexList.size() == methodVertices.size());
        assertTrue(vertexList.containsAll(methodVertices));
    }

    @Test
    public void successorsDG5() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = dg.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = dg.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = dg.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = dg.add(vl4);
        dg.add(v1, v2);
        dg.add(v1, v3);
        ArrayList<Vertex<VLabel>> vertexList = new ArrayList<Vertex<VLabel>>();
        vertexList.add(v2);
        vertexList.add(v3);
        vertexList.add(v4);
        Iterator<Vertex<VLabel>> iter = vertexList.iterator();
        Iterator<Vertex<VLabel>> method = dg.successors(v1);
        dg.add(v1, v4);
        ArrayList<Vertex<VLabel>> methodVertices =
            new ArrayList<Vertex<VLabel>>();
        while (method.hasNext()) {
            methodVertices.add(method.next());
        }
        assertTrue(vertexList.size() == methodVertices.size());
        assertTrue(vertexList.containsAll(methodVertices));
    }

    /** predecessors(Vertex<VLabel> v). */
    @Test
    public void predecessorsDG1() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v = dg.add(vl1);
        ArrayList<Vertex<VLabel>> answer = new ArrayList<Vertex<VLabel>>();
        Iterator<Vertex<VLabel>> iter = answer.iterator();
        Iterator<Vertex<VLabel>> method = dg.predecessors(v);
        for (int i = 0; i < answer.size(); i += 1) {
            assertEquals(iter.next(), method.next());
        }
    }

    @Test
    public void predecessorsDG2() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = dg.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = dg.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = dg.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = dg.add(vl4);
        ELabel el1 = new ELabel("el1");
        ELabel el2 = new ELabel("el2");
        ELabel el3 = new ELabel("el3");
        dg.add(v2, v1, el1);
        dg.add(v3, v1, el2);
        dg.add(v4, v1, el3);
        ArrayList<Vertex<VLabel>> vertexList = new ArrayList<Vertex<VLabel>>();
        vertexList.add(v2);
        vertexList.add(v3);
        vertexList.add(v4);
        Iterator<Vertex<VLabel>> iter = vertexList.iterator();
        Iterator<Vertex<VLabel>> method = dg.predecessors(v1);
        ArrayList<Vertex<VLabel>> methodVertices =
            new ArrayList<Vertex<VLabel>>();
        while (method.hasNext()) {
            methodVertices.add(method.next());
        }
        assertTrue(vertexList.size() == methodVertices.size());
        assertTrue(vertexList.containsAll(methodVertices));
    }

    @Test
    public void predecessorsDG3() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = dg.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = dg.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = dg.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = dg.add(vl4);
        dg.add(v2, v1);
        dg.add(v3, v1);
        dg.add(v4, v1);
        ArrayList<Vertex<VLabel>> vertexList = new ArrayList<Vertex<VLabel>>();
        vertexList.add(v2);
        vertexList.add(v3);
        vertexList.add(v4);
        Iterator<Vertex<VLabel>> iter = vertexList.iterator();
        Iterator<Vertex<VLabel>> method = dg.predecessors(v1);
        ArrayList<Vertex<VLabel>> methodVertices =
            new ArrayList<Vertex<VLabel>>();
        while (method.hasNext()) {
            methodVertices.add(method.next());
        }
        assertTrue(vertexList.size() == methodVertices.size());
        assertTrue(vertexList.containsAll(methodVertices));
    }

    @Test
    public void predecessorsDG4() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = dg.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = dg.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = dg.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = dg.add(vl4);
        ELabel el1 = new ELabel("el1");
        ELabel el2 = new ELabel("el2");
        ELabel el3 = new ELabel("el3");
        dg.add(v2, v1, el1);
        dg.add(v3, v1, el2);
        ArrayList<Vertex<VLabel>> vertexList = new ArrayList<Vertex<VLabel>>();
        vertexList.add(v2);
        vertexList.add(v3);
        vertexList.add(v4);
        Iterator<Vertex<VLabel>> iter = vertexList.iterator();
        Iterator<Vertex<VLabel>> method = dg.predecessors(v1);
        dg.add(v4, v1, el3);
        ArrayList<Vertex<VLabel>> methodVertices =
            new ArrayList<Vertex<VLabel>>();
        while (method.hasNext()) {
            methodVertices.add(method.next());
        }
        assertTrue(vertexList.size() == methodVertices.size());
        assertTrue(vertexList.containsAll(methodVertices));
    }

    @Test
    public void predecessorsDG5() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = dg.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = dg.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = dg.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = dg.add(vl4);
        dg.add(v2, v1);
        dg.add(v3, v1);
        ArrayList<Vertex<VLabel>> vertexList = new ArrayList<Vertex<VLabel>>();
        vertexList.add(v2);
        vertexList.add(v3);
        vertexList.add(v4);
        Iterator<Vertex<VLabel>> iter = vertexList.iterator();
        Iterator<Vertex<VLabel>> method = dg.predecessors(v1);
        dg.add(v4, v1);
        ArrayList<Vertex<VLabel>> methodVertices =
            new ArrayList<Vertex<VLabel>>();
        while (method.hasNext()) {
            methodVertices.add(method.next());
        }
        assertTrue(vertexList.size() == methodVertices.size());
        assertTrue(vertexList.containsAll(methodVertices));
    }

    /** edges(). */
    @Test
    public void edgesDG1() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = dg.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = dg.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = dg.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = dg.add(vl4);
        ArrayList<Edge<VLabel, ELabel>> edgeList = new ArrayList<Edge<VLabel,
            ELabel>>();
        ELabel el1 = new ELabel("el1");
        ELabel el2 = new ELabel("el2");
        ELabel el3 = new ELabel("el3");
        edgeList.add(dg.add(v1, v2, el1));
        edgeList.add(dg.add(v1, v3, el2));
        edgeList.add(dg.add(v1, v4, el3));
        Iterator<Edge<VLabel, ELabel>> iter = edgeList.iterator();
        Iterator<Edge<VLabel, ELabel>> method = dg.edges();
        ArrayList<Edge<VLabel, ELabel>> methodEdges =
            new ArrayList<Edge<VLabel, ELabel>>();
        while (method.hasNext()) {
            methodEdges.add(method.next());
        }
        assertEquals(edgeList.size(), methodEdges.size());
        assertTrue(edgeList.containsAll(methodEdges));
    }

    @Test
    public void edgesDG2() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = dg.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = dg.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = dg.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = dg.add(vl4);
        ArrayList<Edge<VLabel, ELabel>> edgeList = new ArrayList<Edge<VLabel,
            ELabel>>();
        edgeList.add(dg.add(v1, v2));
        edgeList.add(dg.add(v1, v3));
        edgeList.add(dg.add(v1, v4));
        Iterator<Edge<VLabel, ELabel>> iter = edgeList.iterator();
        Iterator<Edge<VLabel, ELabel>> method = dg.edges();
        ArrayList<Edge<VLabel, ELabel>> methodEdges =
            new ArrayList<Edge<VLabel, ELabel>>();
        while (method.hasNext()) {
            methodEdges.add(method.next());
        }
        assertEquals(edgeList.size(), methodEdges.size());
        assertTrue(edgeList.containsAll(methodEdges));
    }

    @Test
    public void edgesDG3() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = dg.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = dg.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = dg.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = dg.add(vl4);
        ArrayList<Edge<VLabel, ELabel>> edgeList = new ArrayList<Edge<VLabel,
            ELabel>>();
        ELabel el1 = new ELabel("el1");
        ELabel el2 = new ELabel("el2");
        ELabel el3 = new ELabel("el3");
        edgeList.add(dg.add(v1, v2, el1));
        edgeList.add(dg.add(v1, v3, el2));
        Iterator<Edge<VLabel, ELabel>> method = dg.edges();
        edgeList.add(dg.add(v1, v4, el3));
        Iterator<Edge<VLabel, ELabel>> iter = edgeList.iterator();
        ArrayList<Edge<VLabel, ELabel>> methodEdges =
            new ArrayList<Edge<VLabel, ELabel>>();
        while (method.hasNext()) {
            methodEdges.add(method.next());
        }
        assertEquals(edgeList.size(), methodEdges.size());
        assertTrue(edgeList.containsAll(methodEdges));
    }

    @Test
    public void edgesDG4() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = dg.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = dg.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = dg.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = dg.add(vl4);
        ArrayList<Edge<VLabel, ELabel>> edgeList = new ArrayList<Edge<VLabel,
            ELabel>>();
        edgeList.add(dg.add(v1, v2));
        edgeList.add(dg.add(v1, v3));
        Iterator<Edge<VLabel, ELabel>> method = dg.edges();
        edgeList.add(dg.add(v1, v4));
        Iterator<Edge<VLabel, ELabel>> iter = edgeList.iterator();
        ArrayList<Edge<VLabel, ELabel>> methodEdges =
            new ArrayList<Edge<VLabel, ELabel>>();
        while (method.hasNext()) {
            methodEdges.add(method.next());
        }
        assertEquals(edgeList.size(), methodEdges.size());
        assertTrue(edgeList.containsAll(methodEdges));
    }

    @Test
    public void edgesDG5() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = dg.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = dg.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = dg.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = dg.add(vl4);
        ArrayList<Edge<VLabel, ELabel>> edgeList = new ArrayList<Edge<VLabel,
            ELabel>>();
        ELabel el1 = new ELabel("el1");
        edgeList.add(dg.add(v1, v2));
        edgeList.add(dg.add(v1, v3, el1));
        Iterator<Edge<VLabel, ELabel>> method = dg.edges();
        edgeList.add(dg.add(v1, v4));
        Iterator<Edge<VLabel, ELabel>> iter = edgeList.iterator();
        ArrayList<Edge<VLabel, ELabel>> methodEdges =
            new ArrayList<Edge<VLabel, ELabel>>();
        while (method.hasNext()) {
            methodEdges.add(method.next());
        }
        assertEquals(edgeList.size(), methodEdges.size());
        assertTrue(edgeList.containsAll(methodEdges));
    }

    /** outEdges(Vertex<VLabel> v). */
    @Test
    public void outedgesDG1() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = dg.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = dg.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = dg.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = dg.add(vl4);
        ArrayList<Edge<VLabel, ELabel>> edgeList = new ArrayList<Edge<VLabel,
            ELabel>>();
        ELabel el1 = new ELabel("el1");
        ELabel el2 = new ELabel("el2");
        ELabel el3 = new ELabel("el3");
        edgeList.add(dg.add(v1, v2, el1));
        edgeList.add(dg.add(v1, v3, el2));
        edgeList.add(dg.add(v1, v4, el3));
        Iterator<Edge<VLabel, ELabel>> iter = edgeList.iterator();
        Iterator<Edge<VLabel, ELabel>> method = dg.outEdges(v1);
        ArrayList<Edge<VLabel, ELabel>> methodEdges =
            new ArrayList<Edge<VLabel, ELabel>>();
        while (method.hasNext()) {
            methodEdges.add(method.next());
        }
        assertEquals(edgeList.size(), methodEdges.size());
        assertTrue(edgeList.containsAll(methodEdges));
    }

    @Test
    public void outedgesDG2() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = dg.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = dg.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = dg.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = dg.add(vl4);
        ArrayList<Edge<VLabel, ELabel>> edgeList = new ArrayList<Edge<VLabel,
            ELabel>>();
        edgeList.add(dg.add(v1, v2));
        edgeList.add(dg.add(v1, v3));
        edgeList.add(dg.add(v1, v4));
        Iterator<Edge<VLabel, ELabel>> iter = edgeList.iterator();
        Iterator<Edge<VLabel, ELabel>> method = dg.outEdges(v1);
        ArrayList<Edge<VLabel, ELabel>> methodEdges =
            new ArrayList<Edge<VLabel, ELabel>>();
        while (method.hasNext()) {
            methodEdges.add(method.next());
        }
        assertEquals(edgeList.size(), methodEdges.size());
        assertTrue(edgeList.containsAll(methodEdges));
    }

    @Test
    public void outedgesDG3() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = dg.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = dg.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = dg.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = dg.add(vl4);
        ArrayList<Edge<VLabel, ELabel>> edgeList = new ArrayList<Edge<VLabel,
            ELabel>>();
        ELabel el1 = new ELabel("el1");
        ELabel el2 = new ELabel("el2");
        ELabel el3 = new ELabel("el3");
        edgeList.add(dg.add(v1, v2, el1));
        edgeList.add(dg.add(v1, v3, el2));
        Iterator<Edge<VLabel, ELabel>> method = dg.outEdges(v1);
        edgeList.add(dg.add(v1, v4, el3));
        Iterator<Edge<VLabel, ELabel>> iter = edgeList.iterator();
        ArrayList<Edge<VLabel, ELabel>> methodEdges =
            new ArrayList<Edge<VLabel, ELabel>>();
        while (method.hasNext()) {
            methodEdges.add(method.next());
        }
        assertEquals(edgeList.size(), methodEdges.size());
        assertTrue(edgeList.containsAll(methodEdges));
    }

    @Test
    public void outedgesDG4() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = dg.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = dg.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = dg.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = dg.add(vl4);
        ArrayList<Edge<VLabel, ELabel>> edgeList = new ArrayList<Edge<VLabel,
            ELabel>>();
        edgeList.add(dg.add(v1, v2));
        edgeList.add(dg.add(v1, v3));
        Iterator<Edge<VLabel, ELabel>> method = dg.outEdges(v1);
        edgeList.add(dg.add(v1, v4));
        Iterator<Edge<VLabel, ELabel>> iter = edgeList.iterator();
        ArrayList<Edge<VLabel, ELabel>> methodEdges =
            new ArrayList<Edge<VLabel, ELabel>>();
        while (method.hasNext()) {
            methodEdges.add(method.next());
        }
        assertEquals(edgeList.size(), methodEdges.size());
        assertTrue(edgeList.containsAll(methodEdges));
    }

    @Test
    public void outedgesDG5() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = dg.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = dg.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = dg.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = dg.add(vl4);
        ArrayList<Edge<VLabel, ELabel>> edgeList = new ArrayList<Edge<VLabel,
            ELabel>>();
        ELabel el1 = new ELabel("el1");
        ELabel el2 = new ELabel("el2");
        ELabel el3 = new ELabel("el3");
        edgeList.add(dg.add(v1, v2, el1));
        edgeList.add(dg.add(v1, v3, el2));
        edgeList.add(dg.add(v1, v4, el3));
        dg.add(v2, v3, el3);
        Iterator<Edge<VLabel, ELabel>> iter = edgeList.iterator();
        Iterator<Edge<VLabel, ELabel>> method = dg.outEdges(v1);
        ArrayList<Edge<VLabel, ELabel>> methodEdges =
            new ArrayList<Edge<VLabel, ELabel>>();
        while (method.hasNext()) {
            methodEdges.add(method.next());
        }
        assertEquals(edgeList.size(), methodEdges.size());
        assertTrue(edgeList.containsAll(methodEdges));
    }

    @Test
    public void outedgesDG6() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = dg.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = dg.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = dg.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = dg.add(vl4);
        ArrayList<Edge<VLabel, ELabel>> edgeList = new ArrayList<Edge<VLabel,
            ELabel>>();
        edgeList.add(dg.add(v1, v2));
        edgeList.add(dg.add(v1, v3));
        edgeList.add(dg.add(v1, v4));
        dg.add(v2, v3);
        Iterator<Edge<VLabel, ELabel>> iter = edgeList.iterator();
        Iterator<Edge<VLabel, ELabel>> method = dg.outEdges(v1);
        ArrayList<Edge<VLabel, ELabel>> methodEdges =
            new ArrayList<Edge<VLabel, ELabel>>();
        while (method.hasNext()) {
            methodEdges.add(method.next());
        }
        assertEquals(edgeList.size(), methodEdges.size());
        assertTrue(edgeList.containsAll(methodEdges));
    }

    @Test
    public void outedgesDG7() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = dg.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = dg.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = dg.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = dg.add(vl4);
        ArrayList<Edge<VLabel, ELabel>> edgeList = new ArrayList<Edge<VLabel,
            ELabel>>();
        ELabel el1 = new ELabel("el1");
        ELabel el2 = new ELabel("el2");
        ELabel el3 = new ELabel("el3");
        edgeList.add(dg.add(v1, v2, el1));
        edgeList.add(dg.add(v1, v3, el2));
        dg.add(v2, v3, el3);
        Iterator<Edge<VLabel, ELabel>> method = dg.outEdges(v1);
        edgeList.add(dg.add(v1, v4, el3));
        Iterator<Edge<VLabel, ELabel>> iter = edgeList.iterator();
        ArrayList<Edge<VLabel, ELabel>> methodEdges =
            new ArrayList<Edge<VLabel, ELabel>>();
        while (method.hasNext()) {
            methodEdges.add(method.next());
        }
        assertEquals(edgeList.size(), methodEdges.size());
        assertTrue(edgeList.containsAll(methodEdges));
    }

    @Test
    public void outedgesDG8() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = dg.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = dg.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = dg.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = dg.add(vl4);
        ArrayList<Edge<VLabel, ELabel>> edgeList = new ArrayList<Edge<VLabel,
            ELabel>>();
        edgeList.add(dg.add(v1, v2));
        edgeList.add(dg.add(v1, v3));
        dg.add(v2, v3);
        Iterator<Edge<VLabel, ELabel>> method = dg.outEdges(v1);
        edgeList.add(dg.add(v1, v4));
        Iterator<Edge<VLabel, ELabel>> iter = edgeList.iterator();
        ArrayList<Edge<VLabel, ELabel>> methodEdges =
            new ArrayList<Edge<VLabel, ELabel>>();
        while (method.hasNext()) {
            methodEdges.add(method.next());
        }
        assertEquals(edgeList.size(), methodEdges.size());
        assertTrue(edgeList.containsAll(methodEdges));
    }

    @Test
    public void outedgesDG9() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = dg.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = dg.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = dg.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = dg.add(vl4);
        ArrayList<Edge<VLabel, ELabel>> edgeList = new ArrayList<Edge<VLabel,
            ELabel>>();
        ELabel el1 = new ELabel("el1");
        edgeList.add(dg.add(v1, v2, el1));
        edgeList.add(dg.add(v1, v3));
        dg.add(v2, v3, el1);
        Iterator<Edge<VLabel, ELabel>> method = dg.outEdges(v1);
        edgeList.add(dg.add(v1, v4));
        Iterator<Edge<VLabel, ELabel>> iter = edgeList.iterator();
        ArrayList<Edge<VLabel, ELabel>> methodEdges =
            new ArrayList<Edge<VLabel, ELabel>>();
        while (method.hasNext()) {
            methodEdges.add(method.next());
        }
        assertEquals(edgeList.size(), methodEdges.size());
        assertTrue(edgeList.containsAll(methodEdges));
    }

    /** inEdges(Vertex<VLabel> v). */
    @Test
    public void inedgesDG1() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = dg.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = dg.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = dg.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = dg.add(vl4);
        ArrayList<Edge<VLabel, ELabel>> edgeList = new ArrayList<Edge<VLabel,
            ELabel>>();
        ELabel el1 = new ELabel("el1");
        ELabel el2 = new ELabel("el2");
        ELabel el3 = new ELabel("el3");
        edgeList.add(dg.add(v2, v1, el1));
        edgeList.add(dg.add(v3, v1, el2));
        edgeList.add(dg.add(v4, v1, el3));
        Iterator<Edge<VLabel, ELabel>> iter = edgeList.iterator();
        Iterator<Edge<VLabel, ELabel>> method = dg.inEdges(v1);
        ArrayList<Edge<VLabel, ELabel>> methodEdges =
            new ArrayList<Edge<VLabel, ELabel>>();
        while (method.hasNext()) {
            methodEdges.add(method.next());
        }
        assertEquals(edgeList.size(), methodEdges.size());
        assertTrue(edgeList.containsAll(methodEdges));
    }

    @Test
    public void inedgesDG2() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = dg.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = dg.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = dg.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = dg.add(vl4);
        ArrayList<Edge<VLabel, ELabel>> edgeList = new ArrayList<Edge<VLabel,
            ELabel>>();
        edgeList.add(dg.add(v2, v1));
        edgeList.add(dg.add(v3, v1));
        edgeList.add(dg.add(v4, v1));
        Iterator<Edge<VLabel, ELabel>> iter = edgeList.iterator();
        Iterator<Edge<VLabel, ELabel>> method = dg.inEdges(v1);
        ArrayList<Edge<VLabel, ELabel>> methodEdges =
            new ArrayList<Edge<VLabel, ELabel>>();
        while (method.hasNext()) {
            methodEdges.add(method.next());
        }
        assertEquals(edgeList.size(), methodEdges.size());
        assertTrue(edgeList.containsAll(methodEdges));
    }

    @Test
    public void inedgesDG3() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = dg.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = dg.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = dg.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = dg.add(vl4);
        ArrayList<Edge<VLabel, ELabel>> edgeList = new ArrayList<Edge<VLabel,
            ELabel>>();
        ELabel el1 = new ELabel("el1");
        ELabel el2 = new ELabel("el2");
        ELabel el3 = new ELabel("el3");
        edgeList.add(dg.add(v2, v1, el1));
        edgeList.add(dg.add(v3, v1, el2));
        Iterator<Edge<VLabel, ELabel>> method = dg.inEdges(v1);
        edgeList.add(dg.add(v4, v1, el3));
        Iterator<Edge<VLabel, ELabel>> iter = edgeList.iterator();
        ArrayList<Edge<VLabel, ELabel>> methodEdges =
            new ArrayList<Edge<VLabel, ELabel>>();
        while (method.hasNext()) {
            methodEdges.add(method.next());
        }
        assertEquals(edgeList.size(), methodEdges.size());
        assertTrue(edgeList.containsAll(methodEdges));
    }

    @Test
    public void inedgesDG4() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = dg.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = dg.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = dg.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = dg.add(vl4);
        ArrayList<Edge<VLabel, ELabel>> edgeList = new ArrayList<Edge<VLabel,
            ELabel>>();
        edgeList.add(dg.add(v2, v1));
        edgeList.add(dg.add(v3, v1));
        Iterator<Edge<VLabel, ELabel>> method = dg.inEdges(v1);
        edgeList.add(dg.add(v4, v1));
        Iterator<Edge<VLabel, ELabel>> iter = edgeList.iterator();
        ArrayList<Edge<VLabel, ELabel>> methodEdges =
            new ArrayList<Edge<VLabel, ELabel>>();
        while (method.hasNext()) {
            methodEdges.add(method.next());
        }
        assertEquals(edgeList.size(), methodEdges.size());
        assertTrue(edgeList.containsAll(methodEdges));
    }

    @Test
    public void inedgesDG5() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = dg.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = dg.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = dg.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = dg.add(vl4);
        ArrayList<Edge<VLabel, ELabel>> edgeList = new ArrayList<Edge<VLabel,
            ELabel>>();
        ELabel el1 = new ELabel("el1");
        ELabel el2 = new ELabel("el2");
        ELabel el3 = new ELabel("el3");
        edgeList.add(dg.add(v2, v1, el1));
        edgeList.add(dg.add(v3, v1, el2));
        edgeList.add(dg.add(v4, v1, el3));
        dg.add(v3, v2, el2);
        Iterator<Edge<VLabel, ELabel>> iter = edgeList.iterator();
        Iterator<Edge<VLabel, ELabel>> method = dg.inEdges(v1);
        ArrayList<Edge<VLabel, ELabel>> methodEdges =
            new ArrayList<Edge<VLabel, ELabel>>();
        while (method.hasNext()) {
            methodEdges.add(method.next());
        }
        assertEquals(edgeList.size(), methodEdges.size());
        assertTrue(edgeList.containsAll(methodEdges));
    }

    @Test
    public void inedgesDG6() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = dg.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = dg.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = dg.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = dg.add(vl4);
        ArrayList<Edge<VLabel, ELabel>> edgeList = new ArrayList<Edge<VLabel,
            ELabel>>();
        edgeList.add(dg.add(v2, v1));
        edgeList.add(dg.add(v3, v1));
        edgeList.add(dg.add(v4, v1));
        dg.add(v3, v2);
        Iterator<Edge<VLabel, ELabel>> iter = edgeList.iterator();
        Iterator<Edge<VLabel, ELabel>> method = dg.inEdges(v1);
        ArrayList<Edge<VLabel, ELabel>> methodEdges =
            new ArrayList<Edge<VLabel, ELabel>>();
        while (method.hasNext()) {
            methodEdges.add(method.next());
        }
        assertEquals(edgeList.size(), methodEdges.size());
        assertTrue(edgeList.containsAll(methodEdges));
    }

    @Test
    public void inedgesDG7() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = dg.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = dg.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = dg.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = dg.add(vl4);
        ArrayList<Edge<VLabel, ELabel>> edgeList = new ArrayList<Edge<VLabel,
            ELabel>>();
        ELabel el1 = new ELabel("el1");
        ELabel el2 = new ELabel("el2");
        ELabel el3 = new ELabel("el3");
        edgeList.add(dg.add(v2, v1, el1));
        edgeList.add(dg.add(v3, v1, el2));
        dg.add(v3, v2, el2);
        Iterator<Edge<VLabel, ELabel>> method = dg.inEdges(v1);
        edgeList.add(dg.add(v4, v1, el3));
        Iterator<Edge<VLabel, ELabel>> iter = edgeList.iterator();
        ArrayList<Edge<VLabel, ELabel>> methodEdges =
            new ArrayList<Edge<VLabel, ELabel>>();
        while (method.hasNext()) {
            methodEdges.add(method.next());
        }
        assertEquals(edgeList.size(), methodEdges.size());
        assertTrue(edgeList.containsAll(methodEdges));
    }

    @Test
    public void inedgesDG8() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = dg.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = dg.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = dg.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = dg.add(vl4);
        ArrayList<Edge<VLabel, ELabel>> edgeList = new ArrayList<Edge<VLabel,
            ELabel>>();
        edgeList.add(dg.add(v2, v1));
        edgeList.add(dg.add(v3, v1));
        dg.add(v3, v2);
        Iterator<Edge<VLabel, ELabel>> method = dg.inEdges(v1);
        dg.add(v4, v3);
        edgeList.add(dg.add(v4, v1));
        Iterator<Edge<VLabel, ELabel>> iter = edgeList.iterator();
        ArrayList<Edge<VLabel, ELabel>> methodEdges =
            new ArrayList<Edge<VLabel, ELabel>>();
        while (method.hasNext()) {
            methodEdges.add(method.next());
        }
        assertEquals(edgeList.size(), methodEdges.size());
        assertTrue(edgeList.containsAll(methodEdges));
    }

    @Test
    public void inedgesDG9() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = dg.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = dg.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = dg.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = dg.add(vl4);
        ArrayList<Edge<VLabel, ELabel>> edgeList = new ArrayList<Edge<VLabel,
            ELabel>>();
        ELabel el1 = new ELabel("el1");
        edgeList.add(dg.add(v2, v1));
        edgeList.add(dg.add(v3, v1, el1));
        dg.add(v3, v2);
        Iterator<Edge<VLabel, ELabel>> method = dg.inEdges(v1);
        dg.add(v4, v3, el1);
        edgeList.add(dg.add(v4, v1));
        Iterator<Edge<VLabel, ELabel>> iter = edgeList.iterator();
        ArrayList<Edge<VLabel, ELabel>> methodEdges =
            new ArrayList<Edge<VLabel, ELabel>>();
        while (method.hasNext()) {
            methodEdges.add(method.next());
        }
        assertEquals(edgeList.size(), methodEdges.size());
        assertTrue(edgeList.containsAll(methodEdges));
    }

    /** orderEdges(). */
    @Test
    public void orderedgesDG1() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = dg.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = dg.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = dg.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = dg.add(vl4);
        ArrayList<Edge<VLabel, ELabel>> edgeList = new ArrayList<Edge<VLabel,
            ELabel>>();
        ELabel el1 = new ELabel(3);
        ELabel el2 = new ELabel(1);
        ELabel el3 = new ELabel(2);
        edgeList.add(dg.add(v3, v1, el2));
        edgeList.add(dg.add(v4, v1, el3));
        edgeList.add(dg.add(v2, v1, el1));
        dg.orderEdges();
        Iterator<Edge<VLabel, ELabel>> iter = edgeList.iterator();
        Iterator<Edge<VLabel, ELabel>> method = dg.edges();
        ArrayList<Edge<VLabel, ELabel>> methodEdges =
            new ArrayList<Edge<VLabel, ELabel>>();
        while (method.hasNext()) {
            methodEdges.add(method.next());
        }
        assertEquals(edgeList.size(), methodEdges.size());
        assertTrue(edgeList.containsAll(methodEdges));
    }

    @Test
    public void orderedgesDG2() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = dg.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = dg.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = dg.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = dg.add(vl4);
        ArrayList<Edge<VLabel, ELabel>> edgeList = new ArrayList<Edge<VLabel,
            ELabel>>();
        ELabel el1 = new ELabel(3);
        ELabel el2 = new ELabel(2);
        ELabel el3 = new ELabel(1);
        edgeList.add(dg.add(v3, v1, el1));
        edgeList.add(dg.add(v4, v1, el2));
        edgeList.add(dg.add(v2, v1, el3));
        dg.orderEdges();
        Iterator<Edge<VLabel, ELabel>> iter = edgeList.iterator();
        Iterator<Edge<VLabel, ELabel>> method = dg.edges();
        ArrayList<Edge<VLabel, ELabel>> methodEdges =
            new ArrayList<Edge<VLabel, ELabel>>();
        while (method.hasNext()) {
            methodEdges.add(method.next());
        }
        assertEquals(edgeList.size(), methodEdges.size());
        assertTrue(edgeList.containsAll(methodEdges));
    }

    @Test
    public void orderedgesDG3() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = dg.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = dg.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = dg.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = dg.add(vl4);
        ArrayList<Edge<VLabel, ELabel>> edgeList = new ArrayList<Edge<VLabel,
            ELabel>>();
        ELabel el1 = new ELabel(1);
        ELabel el2 = new ELabel(2);
        ELabel el3 = new ELabel(3);
        edgeList.add(dg.add(v3, v1, el1));
        edgeList.add(dg.add(v4, v1, el2));
        edgeList.add(dg.add(v2, v1, el3));
        dg.orderEdges();
        Iterator<Edge<VLabel, ELabel>> iter = edgeList.iterator();
        Iterator<Edge<VLabel, ELabel>> method = dg.edges();
        ArrayList<Edge<VLabel, ELabel>> methodEdges =
            new ArrayList<Edge<VLabel, ELabel>>();
        while (method.hasNext()) {
            methodEdges.add(method.next());
        }
        assertEquals(edgeList.size(), methodEdges.size());
        assertTrue(edgeList.containsAll(methodEdges));
    }

    /** orderEdges(Comparator<ELabel> comparator). */
    @Test
    @SuppressWarnings("unchecked")
    public void orderedgesCdG1() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = dg.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = dg.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = dg.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = dg.add(vl4);
        ArrayList<Edge<VLabel, ELabel>> edgeList = new ArrayList<Edge<VLabel,
            ELabel>>();
        ELabel el1 = new ELabel(1);
        ELabel el2 = new ELabel(2);
        ELabel el3 = new ELabel(3);
        edgeList.add(dg.add(v3, v1, el1));
        edgeList.add(dg.add(v4, v1, el2));
        edgeList.add(dg.add(v2, v1, el3));
        dg.orderEdges(new EdgeComparatorTesting<ELabel>());
        Iterator<Edge<VLabel, ELabel>> iter = edgeList.iterator();
        Iterator<Edge<VLabel, ELabel>> method = dg.edges();
        ArrayList<Edge<VLabel, ELabel>> methodEdges =
            new ArrayList<Edge<VLabel, ELabel>>();
        while (method.hasNext()) {
            methodEdges.add(method.next());
        }
        assertEquals(edgeList.size(), methodEdges.size());
        assertTrue(edgeList.containsAll(methodEdges));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void orderedgesCdG2() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = dg.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = dg.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = dg.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = dg.add(vl4);
        ArrayList<Edge<VLabel, ELabel>> edgeList = new ArrayList<Edge<VLabel,
            ELabel>>();
        ELabel el1 = new ELabel(3);
        ELabel el2 = new ELabel(2);
        ELabel el3 = new ELabel(1);
        edgeList.add(dg.add(v3, v1, el1));
        edgeList.add(dg.add(v4, v1, el2));
        edgeList.add(dg.add(v2, v1, el3));
        dg.orderEdges(new EdgeComparatorTesting<ELabel>());
        Iterator<Edge<VLabel, ELabel>> iter = edgeList.iterator();
        Iterator<Edge<VLabel, ELabel>> method = dg.edges();
        ArrayList<Edge<VLabel, ELabel>> methodEdges =
            new ArrayList<Edge<VLabel, ELabel>>();
        while (method.hasNext()) {
            methodEdges.add(method.next());
        }
        assertEquals(edgeList.size(), methodEdges.size());
        assertTrue(edgeList.containsAll(methodEdges));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void orderedgesCdG3() {
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = dg.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = dg.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = dg.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = dg.add(vl4);
        ArrayList<Edge<VLabel, ELabel>> edgeList = new ArrayList<Edge<VLabel,
            ELabel>>();
        ELabel el1 = new ELabel(2);
        ELabel el2 = new ELabel(1);
        ELabel el3 = new ELabel(3);
        edgeList.add(dg.add(v3, v1, el1));
        edgeList.add(dg.add(v4, v1, el2));
        edgeList.add(dg.add(v2, v1, el3));
        dg.orderEdges(new EdgeComparatorTesting<ELabel>());
        Iterator<Edge<VLabel, ELabel>> iter = edgeList.iterator();
        Iterator<Edge<VLabel, ELabel>> method = dg.edges();
        ArrayList<Edge<VLabel, ELabel>> methodEdges =
            new ArrayList<Edge<VLabel, ELabel>>();
        while (method.hasNext()) {
            methodEdges.add(method.next());
        }
        assertEquals(edgeList.size(), methodEdges.size());
        assertTrue(edgeList.containsAll(methodEdges));
    }
}
