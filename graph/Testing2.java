package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Comparator;

import org.junit.Test;
import static org.junit.Assert.*;

/** Unit tests for the graph package. */
public class Testing2 {

    /** Comparator class for Testing file. */
    private class EdgeComparatorTesting implements Comparator {

        /** Method to compare edge A and B. Returns -1, 0, 1 for >, =, <. */
        @SuppressWarnings("unchecked")
        public int compare(Object a, Object b) {
            Edge<VLabel, ELabel> edgeA = (Edge<VLabel, ELabel>) a;
            Edge<VLabel, ELabel> edgeB = (Edge<VLabel, ELabel>) b;
            return ((Comparable<ELabel>)
                edgeA.getLabel()).compareTo(edgeB.getLabel());
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

    /* UNDIRECTEDGRAPH CLASS. */

    /** vertexSize(). */
    @Test
    public void vertexSizeUG1() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        assertEquals(0, ug.vertexSize());
    }

    /** edgeSize(). */
    @Test
    public void edgeSizeUG1() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        assertEquals(0, ug.edgeSize());
    }

    /** isDirected(). */
    @Test
    public void isUnDirectedUG1() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        assertFalse(ug.isDirected());
    }

    /** outDegree(Vertex<VLabel> v). */
    @Test
    public void outDegreeUG1() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> u = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v = ug.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> w = ug.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> z = ug.add(vl4);
        VLabel vl5 = new VLabel("vl5");
        Vertex<VLabel> a = ug.add(vl5);
        ELabel el1 = new ELabel("el1");
        ELabel el2 = new ELabel("el2");
        ELabel el3 = new ELabel("el3");
        ELabel el4 = new ELabel("el4");
        ug.add(u, v, el1);
        ug.add(u, w, el2);
        ug.add(u, z, el3);
        ug.add(u, a, el4);
        assertEquals(4, ug.outDegree(u));
    }

    @Test
    public void outDegreeUG2() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> u = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v = ug.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> w = ug.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> z = ug.add(vl4);
        VLabel vl5 = new VLabel("vl5");
        Vertex<VLabel> a = ug.add(vl5);
        ELabel el1 = new ELabel("el1");
        ELabel el2 = new ELabel("el2");
        ELabel el3 = new ELabel("el3");
        ELabel el4 = new ELabel("el4");
        ug.add(u, v, el1);
        ug.add(u, w, el2);
        ug.add(z, u, el3);
        ug.add(a, u, el4);
        assertEquals(4, ug.outDegree(u));
    }

    /** inDegree(Vertex<VLabel> v). */
    @Test
    public void inDegreeUG1() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> u = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v = ug.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> w = ug.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> z = ug.add(vl4);
        VLabel vl5 = new VLabel("vl5");
        Vertex<VLabel> a = ug.add(vl5);
        ELabel el1 = new ELabel("el1");
        ELabel el2 = new ELabel("el2");
        ELabel el3 = new ELabel("el3");
        ELabel el4 = new ELabel("el4");
        ug.add(u, v, el1);
        ug.add(u, w, el2);
        ug.add(u, z, el3);
        ug.add(u, a, el4);
        assertEquals(4, ug.inDegree(u));
    }

    @Test
    public void insdegreeUG2() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> u = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v = ug.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> w = ug.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> z = ug.add(vl4);
        VLabel vl5 = new VLabel("vl5");
        Vertex<VLabel> a = ug.add(vl5);
        ELabel el1 = new ELabel("el1");
        ELabel el2 = new ELabel("el2");
        ELabel el3 = new ELabel("el3");
        ELabel el4 = new ELabel("el4");
        ug.add(u, v, el1);
        ug.add(u, w, el2);
        ug.add(z, u, el3);
        ug.add(a, u, el4);
        assertEquals(4, ug.inDegree(u));
    }

    /** degree(Vertex<VLabel> v). */
    @Test
    public void degreeUG1() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> u = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v = ug.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> w = ug.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> z = ug.add(vl4);
        VLabel vl5 = new VLabel("vl5");
        Vertex<VLabel> a = ug.add(vl5);
        ELabel el1 = new ELabel("el1");
        ELabel el2 = new ELabel("el2");
        ELabel el3 = new ELabel("el3");
        ELabel el4 = new ELabel("el4");
        ug.add(u, v, el1);
        ug.add(u, w, el2);
        ug.add(u, z, el3);
        ug.add(u, a, el4);
        assertEquals(4, ug.degree(u));
    }

    @Test
    public void degreeUG2() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> u = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v = ug.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> w = ug.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> z = ug.add(vl4);
        VLabel vl5 = new VLabel("vl5");
        Vertex<VLabel> a = ug.add(vl5);
        ELabel el1 = new ELabel("el1");
        ELabel el2 = new ELabel("el2");
        ELabel el3 = new ELabel("el3");
        ELabel el4 = new ELabel("el4");
        ug.add(u, v, el1);
        ug.add(u, w, el2);
        ug.add(z, u, el3);
        ug.add(a, u, el4);
        assertEquals(4, ug.degree(u));
    }

    /** contains(Vertex<VLabel> u, Vertex<VLabel> v). */
    @Test
    public void containsUVug1() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> u = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v = ug.add(vl2);
        ELabel el1 = new ELabel("el1");
        ug.add(u, v, el1);
        assertTrue(ug.contains(u, v));
    }

    @Test
    public void containsUVug2() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> u = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v = ug.add(vl2);
        ELabel el1 = new ELabel("el1");
        ug.add(u, v, el1);
        assertTrue(ug.contains(v, u));
    }

    @Test
    public void containsUVug3() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> u = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v = ug.add(vl2);
        ELabel el1 = new ELabel("el1");
        ug.add(u, v);
        assertTrue(ug.contains(u, v));
    }

    @Test
    public void containsUVug4() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> u = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v = ug.add(vl2);
        ELabel el1 = new ELabel("el1");
        ug.add(u, v);
        assertTrue(ug.contains(v, u));
    }

    /** contains(Vertex<VLabel> u, Vertex<VLabel> v, ELabel label). */
    @Test
    public void containsUVLug1() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> u = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v = ug.add(vl2);
        ELabel el1 = new ELabel("el1");
        ug.add(u, v, el1);
        assertTrue(ug.contains(u, v, el1));
    }

    @Test
    public void containsUVLug2() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> u = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v = ug.add(vl2);
        ELabel el1 = new ELabel("el1");
        ug.add(u, v, el1);
        assertTrue(ug.contains(v, u, el1));
    }

    @Test
    public void containsUVLug3() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> u = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v = ug.add(vl2);
        ELabel el1 = new ELabel("el1");
        ug.add(u, v);
        assertTrue(ug.contains(u, v));
    }

    @Test
    public void containsUVLug4() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> u = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v = ug.add(vl2);
        ELabel el1 = new ELabel("el1");
        ug.add(u, v);
        assertTrue(ug.contains(v, u));
    }

    /** add(VLabel label). */
    @Test
    public void addLuG1() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel label = new VLabel("vl1");
        Vertex<VLabel> addThis = ug.add(label);
        assertEquals(1, ug.vertexSize());
    }

    /** add(Vertex<VLabel> from, Vertex<VLabel> to, ELabel label). */
    @Test
    public void addFTLuG1() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> from = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> to = ug.add(vl2);
        ELabel label = new ELabel("edgelabel");
        Edge<VLabel, ELabel> addThis = ug.add(from, to, label);
        assertTrue(ug.contains(from, to, label));
        assertEquals(1, ug.edgeSize());
    }

    /** add(Vertex<VLabel> from, Vertex<VLabel> to). */
    @Test
    public void addFTuG1() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> from = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> to = ug.add(vl2);
        Edge<VLabel, ELabel> addThis = ug.add(from, to, null);
        assertTrue(ug.contains(from, to, null));
        assertEquals(1, ug.edgeSize());
    }

    /** remove(Vertex<VLabel> v). */
    @Test
    public void removeVuG1() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel label = new VLabel("vl1");
        Vertex<VLabel> addThis = ug.add(label);
        ug.remove(addThis);
        assertEquals(0, ug.vertexSize());
    }

    @Test
    public void removeVuG2() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = ug.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = ug.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = ug.add(vl4);
        ELabel el1 = new ELabel("el1");
        ELabel el2 = new ELabel("el2");
        ELabel el3 = new ELabel("el3");
        Edge<VLabel, ELabel> addThis1 = ug.add(v1, v2, el1);
        Edge<VLabel, ELabel> addThis2 = ug.add(v1, v3, el2);
        Edge<VLabel, ELabel> addThis3 = ug.add(v1, v4, el3);
        ug.remove(v1);
        assertEquals(3, ug.vertexSize());
        assertEquals(0, ug.edgeSize());
    }

    @Test
    public void removeVuG3() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = ug.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = ug.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = ug.add(vl4);
        ELabel el1 = new ELabel("el1");
        ELabel el2 = new ELabel("el2");
        ELabel el3 = new ELabel("el3");
        Edge<VLabel, ELabel> addThis1 = ug.add(v2, v1, el1);
        Edge<VLabel, ELabel> addThis2 = ug.add(v1, v3, el2);
        Edge<VLabel, ELabel> addThis3 = ug.add(v4, v1, el3);
        ug.remove(v1);
        assertEquals(3, ug.vertexSize());
        assertEquals(0, ug.edgeSize());
    }

    @Test
    public void removeVuG4() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = ug.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = ug.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = ug.add(vl4);
        ELabel el1 = new ELabel("el1");
        ELabel el2 = new ELabel("el2");
        ELabel el3 = new ELabel("el3");
        Edge<VLabel, ELabel> addThis1 = ug.add(v1, v2, el1);
        Edge<VLabel, ELabel> addThis2 = ug.add(v1, v3, el2);
        Edge<VLabel, ELabel> addThis3 = ug.add(v1, v4, el3);
        ug.remove(v2);
        assertEquals(3, ug.vertexSize());
        assertEquals(2, ug.edgeSize());
    }

    @Test
    public void removeVuG5() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = ug.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = ug.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = ug.add(vl4);
        ELabel el1 = new ELabel("el1");
        ELabel el2 = new ELabel("el2");
        ELabel el3 = new ELabel("el3");
        Edge<VLabel, ELabel> addThis1 = ug.add(v1, v2, el1);
        Edge<VLabel, ELabel> addThis2 = ug.add(v1, v3, el2);
        Edge<VLabel, ELabel> addThis3 = ug.add(v4, v2, el3);
        ug.remove(v2);
        assertEquals(3, ug.vertexSize());
        assertEquals(1, ug.edgeSize());
    }

    /** remove(Edge<VLabel, ELabel> e). */
    @Test
    public void removeEuG1() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> from = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> to = ug.add(vl2);
        ELabel label = new ELabel("edgelabel");
        Edge<VLabel, ELabel> addThis = ug.add(from, to, label);
        ug.remove(addThis);
        assertEquals(0, ug.edgeSize());
    }

    @Test
    public void removeEuG2() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> from = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> to = ug.add(vl2);
        Edge<VLabel, ELabel> addThis = ug.add(from, to);
        ug.remove(addThis);
        assertEquals(0, ug.edgeSize());
    }

    @Test
    public void removeEuG3() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> from = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> to = ug.add(vl2);
        ELabel label = new ELabel("edgelabel");
        Edge<VLabel, ELabel> addThis = ug.add(from, to, label);
        Edge<VLabel, ELabel> addThis1 = ug.add(from, to, label);
        ug.remove(addThis);
        assertEquals(1, ug.edgeSize());
    }

    @Test
    public void removeEuG4() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> from = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> to = ug.add(vl2);
        Edge<VLabel, ELabel> addThis = ug.add(from, to);
        Edge<VLabel, ELabel> addThis1 = ug.add(from, to);
        ug.remove(addThis);
        assertEquals(1, ug.edgeSize());
    }

    @Test
    public void removeEuG5() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = ug.add(vl2);
        ELabel label = new ELabel("edgelabel");
        Edge<VLabel, ELabel> addThis = ug.add(v1, v2, label);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = ug.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = ug.add(vl4);
        ELabel label2 = new ELabel("edgelabel2");
        Edge<VLabel, ELabel> addThis2 = ug.add(v3, v4, label2);
        ug.remove(addThis);
        assertTrue(ug.contains(v3, v4, label2));
        assertEquals(1, ug.edgeSize());
    }

    @Test
    public void removeEuG6() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = ug.add(vl2);
        Edge<VLabel, ELabel> addThis = ug.add(v1, v2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = ug.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = ug.add(vl4);
        Edge<VLabel, ELabel> addThis2 = ug.add(v3, v4);
        ug.remove(addThis);
        assertTrue(ug.contains(v3, v4));
        assertEquals(1, ug.edgeSize());
    }

    /** remove(Vertex<VLabel> v1, Vertex<VLabel> v2). */
    @Test
    public void removeVVuG1() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = ug.add(vl2);
        ELabel el1 = new ELabel("el1");
        Edge<VLabel, ELabel> e1 = ug.add(v1, v2, el1);
        ug.remove(v1, v2);
        assertEquals(2, ug.vertexSize());
        assertEquals(0, ug.edgeSize());
    }

    @Test
    public void removeVVuG2() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = ug.add(vl2);
        ELabel el1 = new ELabel("el1");
        Edge<VLabel, ELabel> e1 = ug.add(v1, v2, el1);
        ELabel el2 = new ELabel("el2");
        Edge<VLabel, ELabel> e2 = ug.add(v1, v2, el2);
        ELabel el3 = new ELabel("el3");
        Edge<VLabel, ELabel> e3 = ug.add(v1, v2, el3);
        ug.remove(v1, v2);
        assertEquals(2, ug.vertexSize());
        assertEquals(0, ug.edgeSize());
    }

    @Test
    public void removeVVuG3() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = ug.add(vl2);
        ELabel el1 = new ELabel("el1");
        Edge<VLabel, ELabel> e1 = ug.add(v1, v2, el1);
        ug.remove(v2, v1);
        assertEquals(2, ug.vertexSize());
        assertEquals(0, ug.edgeSize());
    }

    @Test
    public void removeVVuG4() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = ug.add(vl2);
        ELabel el1 = new ELabel("el1");
        Edge<VLabel, ELabel> e1 = ug.add(v1, v2, el1);
        ELabel el2 = new ELabel("el2");
        Edge<VLabel, ELabel> e2 = ug.add(v1, v2, el2);
        ELabel el3 = new ELabel("el3");
        Edge<VLabel, ELabel> e3 = ug.add(v1, v2, el3);
        ug.remove(v2, v1);
        assertEquals(2, ug.vertexSize());
        assertEquals(0, ug.edgeSize());
    }

    @Test
    public void removeVVuG5() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = ug.add(vl2);
        ELabel el1 = new ELabel("el1");
        Edge<VLabel, ELabel> e1 = ug.add(v1, v2, el1);
        ELabel el2 = new ELabel("el2");
        Edge<VLabel, ELabel> e2 = ug.add(v2, v1, el2);
        ELabel el3 = new ELabel("el3");
        Edge<VLabel, ELabel> e3 = ug.add(v1, v2, el3);
        ug.remove(v2, v1);
        assertEquals(2, ug.vertexSize());
        assertEquals(0, ug.edgeSize());
    }

    /** vertices(). */
    @Test
    public void verticesUG1() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        ArrayList<Vertex<VLabel>> vertices = new ArrayList<Vertex<VLabel>>();
        Iterator<Vertex<VLabel>> iter = vertices.iterator();
        Iterator<Vertex<VLabel>> method = ug.vertices();
        for (int i = 0; i < vertices.size(); i += 1) {
            assertEquals(iter.next(), method.next());
        }
    }

    @Test
    public void verticesUG2() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        ArrayList<Vertex<VLabel>> vertices = new ArrayList<Vertex<VLabel>>();
        VLabel vl1 = new VLabel("vl1");
        VLabel vl2 = new VLabel("vl2");
        vertices.add(ug.add(vl1));
        vertices.add(ug.add(vl2));
        Iterator<Vertex<VLabel>> iter = vertices.iterator();
        Iterator<Vertex<VLabel>> method = ug.vertices();
        ArrayList<Vertex<VLabel>> methodVertices =
            new ArrayList<Vertex<VLabel>>();
        while (method.hasNext()) {
            methodVertices.add(method.next());
        }
        assertTrue(vertices.size() == methodVertices.size());
        assertTrue(vertices.containsAll(methodVertices));
    }

    @Test
    public void verticesUG3() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = ug.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = ug.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = ug.add(vl4);
        ArrayList<Vertex<VLabel>> vertexList = new ArrayList<Vertex<VLabel>>();
        vertexList.add(v1);
        vertexList.add(v2);
        vertexList.add(v3);
        vertexList.add(v4);
        Iterator<Vertex<VLabel>> iter = vertexList.iterator();
        Iterator<Vertex<VLabel>> method = ug.vertices();
        ArrayList<Vertex<VLabel>> methodVertices =
            new ArrayList<Vertex<VLabel>>();
        while (method.hasNext()) {
            methodVertices.add(method.next());
        }
        assertTrue(vertexList.size() == methodVertices.size());
        assertTrue(vertexList.containsAll(methodVertices));
    }

    @Test
    public void verticesUG4() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        VLabel vl2 = new VLabel("vl2");
        VLabel vl3 = new VLabel("vl3");
        VLabel vl4 = new VLabel("vl4");
        ArrayList<Vertex<VLabel>> vertexList = new ArrayList<Vertex<VLabel>>();
        vertexList.add(ug.add(vl1));
        vertexList.add(ug.add(vl2));
        vertexList.add(ug.add(vl3));
        vertexList.add(ug.add(vl4));
        Iterator<Vertex<VLabel>> iter = vertexList.iterator();
        Iterator<Vertex<VLabel>> method = ug.vertices();
        ArrayList<Vertex<VLabel>> methodVertices =
            new ArrayList<Vertex<VLabel>>();
        while (method.hasNext()) {
            methodVertices.add(method.next());
        }
        assertTrue(vertexList.size() == methodVertices.size());
        assertTrue(vertexList.containsAll(methodVertices));
    }

    @Test
    public void verticesUG5() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = ug.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = ug.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = ug.add(vl4);
        ArrayList<Vertex<VLabel>> vertexList = new ArrayList<Vertex<VLabel>>();
        vertexList.add(v1);
        vertexList.add(v2);
        Iterator<Vertex<VLabel>> iter = vertexList.iterator();
        Iterator<Vertex<VLabel>> method = ug.vertices();
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
    public void verticesUG6() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        VLabel vl2 = new VLabel("vl2");
        VLabel vl3 = new VLabel("vl3");
        VLabel vl4 = new VLabel("vl4");
        ArrayList<Vertex<VLabel>> vertexList = new ArrayList<Vertex<VLabel>>();
        vertexList.add(ug.add(vl1));
        vertexList.add(ug.add(vl2));
        Iterator<Vertex<VLabel>> iter = vertexList.iterator();
        Iterator<Vertex<VLabel>> method = ug.vertices();
        vertexList.add(ug.add(vl3));
        vertexList.add(ug.add(vl4));
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
    public void successorsUG1() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v = ug.add(vl1);
        ArrayList<Vertex<VLabel>> answer = new ArrayList<Vertex<VLabel>>();
        Iterator<Vertex<VLabel>> iter = answer.iterator();
        Iterator<Vertex<VLabel>> method = ug.successors(v);
        for (int i = 0; i < answer.size(); i += 1) {
            assertEquals(iter.next(), method.next());
        }
    }

    @Test
    public void successorsUG2() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = ug.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = ug.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = ug.add(vl4);
        ELabel el1 = new ELabel("el1");
        ELabel el2 = new ELabel("el2");
        ELabel el3 = new ELabel("el3");
        ug.add(v1, v2, el1);
        ug.add(v1, v3, el2);
        ug.add(v1, v4, el3);
        ArrayList<Vertex<VLabel>> vertexList = new ArrayList<Vertex<VLabel>>();
        vertexList.add(v2);
        vertexList.add(v3);
        vertexList.add(v4);
        Iterator<Vertex<VLabel>> iter = vertexList.iterator();
        Iterator<Vertex<VLabel>> method = ug.successors(v1);
        ArrayList<Vertex<VLabel>> methodVertices =
            new ArrayList<Vertex<VLabel>>();
        while (method.hasNext()) {
            methodVertices.add(method.next());
        }
        assertTrue(vertexList.size() == methodVertices.size());
        assertTrue(vertexList.containsAll(methodVertices));
    }

    @Test
    public void successorsUG3() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = ug.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = ug.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = ug.add(vl4);
        ELabel el1 = new ELabel("el1");
        ELabel el2 = new ELabel("el2");
        ELabel el3 = new ELabel("el3");
        ug.add(v2, v1, el1);
        ug.add(v1, v3, el2);
        ug.add(v4, v1, el3);
        ArrayList<Vertex<VLabel>> vertexList = new ArrayList<Vertex<VLabel>>();
        vertexList.add(v2);
        vertexList.add(v3);
        vertexList.add(v4);
        Iterator<Vertex<VLabel>> iter = vertexList.iterator();
        Iterator<Vertex<VLabel>> method = ug.successors(v1);
        ArrayList<Vertex<VLabel>> methodVertices =
            new ArrayList<Vertex<VLabel>>();
        while (method.hasNext()) {
            methodVertices.add(method.next());
        }
        assertTrue(vertexList.size() == methodVertices.size());
        assertTrue(vertexList.containsAll(methodVertices));
    }

    @Test
    public void successorsUG4() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = ug.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = ug.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = ug.add(vl4);
        ug.add(v1, v2);
        ug.add(v1, v3);
        ug.add(v1, v4);
        ArrayList<Vertex<VLabel>> vertexList = new ArrayList<Vertex<VLabel>>();
        vertexList.add(v2);
        vertexList.add(v3);
        vertexList.add(v4);
        Iterator<Vertex<VLabel>> iter = vertexList.iterator();
        Iterator<Vertex<VLabel>> method = ug.successors(v1);
        ArrayList<Vertex<VLabel>> methodVertices =
            new ArrayList<Vertex<VLabel>>();
        while (method.hasNext()) {
            methodVertices.add(method.next());
        }
        assertTrue(vertexList.size() == methodVertices.size());
        assertTrue(vertexList.containsAll(methodVertices));
    }

    @Test
    public void successorsUG5() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = ug.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = ug.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = ug.add(vl4);
        ELabel el1 = new ELabel("el1");
        ELabel el2 = new ELabel("el2");
        ELabel el3 = new ELabel("el3");
        ug.add(v2, v1);
        ug.add(v1, v3);
        ug.add(v4, v1);
        ArrayList<Vertex<VLabel>> vertexList = new ArrayList<Vertex<VLabel>>();
        vertexList.add(v2);
        vertexList.add(v3);
        vertexList.add(v4);
        Iterator<Vertex<VLabel>> iter = vertexList.iterator();
        Iterator<Vertex<VLabel>> method = ug.successors(v1);
        ArrayList<Vertex<VLabel>> methodVertices =
            new ArrayList<Vertex<VLabel>>();
        while (method.hasNext()) {
            methodVertices.add(method.next());
        }
        assertTrue(vertexList.size() == methodVertices.size());
        assertTrue(vertexList.containsAll(methodVertices));
    }

    @Test
    public void successorsUG6() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = ug.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = ug.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = ug.add(vl4);
        ELabel el1 = new ELabel("el1");
        ug.add(v2, v1);
        ug.add(v1, v3, el1);
        ug.add(v4, v1);
        ArrayList<Vertex<VLabel>> vertexList = new ArrayList<Vertex<VLabel>>();
        vertexList.add(v2);
        vertexList.add(v3);
        vertexList.add(v4);
        Iterator<Vertex<VLabel>> iter = vertexList.iterator();
        Iterator<Vertex<VLabel>> method = ug.successors(v1);
        ArrayList<Vertex<VLabel>> methodVertices =
            new ArrayList<Vertex<VLabel>>();
        while (method.hasNext()) {
            methodVertices.add(method.next());
        }
        assertTrue(vertexList.size() == methodVertices.size());
        assertTrue(vertexList.containsAll(methodVertices));
    }

    @Test
    public void successorsUG7() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = ug.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = ug.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = ug.add(vl4);
        ELabel el1 = new ELabel("el1");
        ELabel el2 = new ELabel("el2");
        ELabel el3 = new ELabel("el3");
        ug.add(v1, v2, el1);
        ug.add(v1, v3, el2);
        ArrayList<Vertex<VLabel>> vertexList = new ArrayList<Vertex<VLabel>>();
        vertexList.add(v2);
        vertexList.add(v3);
        vertexList.add(v4);
        Iterator<Vertex<VLabel>> iter = vertexList.iterator();
        Iterator<Vertex<VLabel>> method = ug.successors(v1);
        ug.add(v1, v4, el3);
        ArrayList<Vertex<VLabel>> methodVertices =
            new ArrayList<Vertex<VLabel>>();
        while (method.hasNext()) {
            methodVertices.add(method.next());
        }
        assertTrue(vertexList.size() == methodVertices.size());
        assertTrue(vertexList.containsAll(methodVertices));
    }

    @Test
    public void successorsUG8() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = ug.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = ug.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = ug.add(vl4);
        ELabel el1 = new ELabel("el1");
        ELabel el2 = new ELabel("el2");
        ELabel el3 = new ELabel("el3");
        ug.add(v2, v1, el1);
        ug.add(v1, v3, el2);
        ArrayList<Vertex<VLabel>> vertexList = new ArrayList<Vertex<VLabel>>();
        vertexList.add(v2);
        vertexList.add(v3);
        vertexList.add(v4);
        Iterator<Vertex<VLabel>> iter = vertexList.iterator();
        Iterator<Vertex<VLabel>> method = ug.successors(v1);
        ug.add(v4, v1, el3);
        ArrayList<Vertex<VLabel>> methodVertices =
            new ArrayList<Vertex<VLabel>>();
        while (method.hasNext()) {
            methodVertices.add(method.next());
        }
        assertTrue(vertexList.size() == methodVertices.size());
        assertTrue(vertexList.containsAll(methodVertices));
    }

    @Test
    public void successorsUG9() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = ug.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = ug.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = ug.add(vl4);
        ug.add(v1, v2);
        ug.add(v1, v3);
        ArrayList<Vertex<VLabel>> vertexList = new ArrayList<Vertex<VLabel>>();
        vertexList.add(v2);
        vertexList.add(v3);
        vertexList.add(v4);
        Iterator<Vertex<VLabel>> iter = vertexList.iterator();
        Iterator<Vertex<VLabel>> method = ug.successors(v1);
        ug.add(v1, v4);
        ArrayList<Vertex<VLabel>> methodVertices =
            new ArrayList<Vertex<VLabel>>();
        while (method.hasNext()) {
            methodVertices.add(method.next());
        }
        assertTrue(vertexList.size() == methodVertices.size());
        assertTrue(vertexList.containsAll(methodVertices));
    }

    @Test
    public void successorsUG10() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = ug.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = ug.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = ug.add(vl4);
        ELabel el1 = new ELabel("el1");
        ELabel el2 = new ELabel("el2");
        ELabel el3 = new ELabel("el3");
        ug.add(v2, v1);
        ug.add(v1, v3);
        ArrayList<Vertex<VLabel>> vertexList = new ArrayList<Vertex<VLabel>>();
        vertexList.add(v2);
        vertexList.add(v3);
        vertexList.add(v4);
        Iterator<Vertex<VLabel>> iter = vertexList.iterator();
        Iterator<Vertex<VLabel>> method = ug.successors(v1);
        ug.add(v4, v1);
        ArrayList<Vertex<VLabel>> methodVertices =
            new ArrayList<Vertex<VLabel>>();
        while (method.hasNext()) {
            methodVertices.add(method.next());
        }
        assertTrue(vertexList.size() == methodVertices.size());
        assertTrue(vertexList.containsAll(methodVertices));
    }

    @Test
    public void successorsUG11() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = ug.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = ug.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = ug.add(vl4);
        ELabel el1 = new ELabel("el1");
        ug.add(v2, v1);
        ug.add(v1, v3, el1);
        ArrayList<Vertex<VLabel>> vertexList = new ArrayList<Vertex<VLabel>>();
        vertexList.add(v2);
        vertexList.add(v3);
        vertexList.add(v4);
        Iterator<Vertex<VLabel>> iter = vertexList.iterator();
        Iterator<Vertex<VLabel>> method = ug.successors(v1);
        ug.add(v2, v3, el1);
        ug.add(v4, v1);
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
    public void predecessorsUG1() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v = ug.add(vl1);
        ArrayList<Vertex<VLabel>> answer = new ArrayList<Vertex<VLabel>>();
        Iterator<Vertex<VLabel>> iter = answer.iterator();
        Iterator<Vertex<VLabel>> method = ug.predecessors(v);
        for (int i = 0; i < answer.size(); i += 1) {
            assertEquals(iter.next(), method.next());
        }
    }

    @Test
    public void predecessorsUG2() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = ug.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = ug.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = ug.add(vl4);
        ELabel el1 = new ELabel("el1");
        ELabel el2 = new ELabel("el2");
        ELabel el3 = new ELabel("el3");
        ug.add(v1, v2, el1);
        ug.add(v1, v3, el2);
        ug.add(v1, v4, el3);
        ArrayList<Vertex<VLabel>> vertexList = new ArrayList<Vertex<VLabel>>();
        vertexList.add(v2);
        vertexList.add(v3);
        vertexList.add(v4);
        Iterator<Vertex<VLabel>> iter = vertexList.iterator();
        Iterator<Vertex<VLabel>> method = ug.predecessors(v1);
        ArrayList<Vertex<VLabel>> methodVertices =
            new ArrayList<Vertex<VLabel>>();
        while (method.hasNext()) {
            methodVertices.add(method.next());
        }
        assertTrue(vertexList.size() == methodVertices.size());
        assertTrue(vertexList.containsAll(methodVertices));
    }

    @Test
    public void predecessorsUG3() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = ug.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = ug.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = ug.add(vl4);
        ELabel el1 = new ELabel("el1");
        ELabel el2 = new ELabel("el2");
        ELabel el3 = new ELabel("el3");
        ug.add(v2, v1, el1);
        ug.add(v1, v3, el2);
        ug.add(v4, v1, el3);
        ArrayList<Vertex<VLabel>> vertexList = new ArrayList<Vertex<VLabel>>();
        vertexList.add(v2);
        vertexList.add(v3);
        vertexList.add(v4);
        Iterator<Vertex<VLabel>> iter = vertexList.iterator();
        Iterator<Vertex<VLabel>> method = ug.predecessors(v1);
        ArrayList<Vertex<VLabel>> methodVertices =
            new ArrayList<Vertex<VLabel>>();
        while (method.hasNext()) {
            methodVertices.add(method.next());
        }
        assertTrue(vertexList.size() == methodVertices.size());
        assertTrue(vertexList.containsAll(methodVertices));
    }

    @Test
    public void predecessorsUG4() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = ug.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = ug.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = ug.add(vl4);
        ug.add(v1, v2);
        ug.add(v1, v3);
        ug.add(v1, v4);
        ArrayList<Vertex<VLabel>> vertexList = new ArrayList<Vertex<VLabel>>();
        vertexList.add(v2);
        vertexList.add(v3);
        vertexList.add(v4);
        Iterator<Vertex<VLabel>> iter = vertexList.iterator();
        Iterator<Vertex<VLabel>> method = ug.predecessors(v1);
        ArrayList<Vertex<VLabel>> methodVertices =
            new ArrayList<Vertex<VLabel>>();
        while (method.hasNext()) {
            methodVertices.add(method.next());
        }
        assertTrue(vertexList.size() == methodVertices.size());
        assertTrue(vertexList.containsAll(methodVertices));
    }

    @Test
    public void predecessorsUG5() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = ug.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = ug.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = ug.add(vl4);
        ELabel el1 = new ELabel("el1");
        ELabel el2 = new ELabel("el2");
        ELabel el3 = new ELabel("el3");
        ug.add(v2, v1);
        ug.add(v1, v3);
        ug.add(v4, v1);
        ArrayList<Vertex<VLabel>> vertexList = new ArrayList<Vertex<VLabel>>();
        vertexList.add(v2);
        vertexList.add(v3);
        vertexList.add(v4);
        Iterator<Vertex<VLabel>> iter = vertexList.iterator();
        Iterator<Vertex<VLabel>> method = ug.predecessors(v1);
        ArrayList<Vertex<VLabel>> methodVertices =
            new ArrayList<Vertex<VLabel>>();
        while (method.hasNext()) {
            methodVertices.add(method.next());
        }
        assertTrue(vertexList.size() == methodVertices.size());
        assertTrue(vertexList.containsAll(methodVertices));
    }

    @Test
    public void predecessorsUG6() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = ug.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = ug.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = ug.add(vl4);
        ELabel el1 = new ELabel("el1");
        ug.add(v2, v1);
        ug.add(v1, v3, el1);
        ug.add(v4, v1);
        ArrayList<Vertex<VLabel>> vertexList = new ArrayList<Vertex<VLabel>>();
        vertexList.add(v2);
        vertexList.add(v3);
        vertexList.add(v4);
        Iterator<Vertex<VLabel>> iter = vertexList.iterator();
        Iterator<Vertex<VLabel>> method = ug.predecessors(v1);
        ArrayList<Vertex<VLabel>> methodVertices =
            new ArrayList<Vertex<VLabel>>();
        while (method.hasNext()) {
            methodVertices.add(method.next());
        }
        assertTrue(vertexList.size() == methodVertices.size());
        assertTrue(vertexList.containsAll(methodVertices));
    }

    @Test
    public void predecessorsUG7() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = ug.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = ug.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = ug.add(vl4);
        ELabel el1 = new ELabel("el1");
        ELabel el2 = new ELabel("el2");
        ELabel el3 = new ELabel("el3");
        ug.add(v1, v2, el1);
        ug.add(v1, v3, el2);
        ArrayList<Vertex<VLabel>> vertexList = new ArrayList<Vertex<VLabel>>();
        vertexList.add(v2);
        vertexList.add(v3);
        vertexList.add(v4);
        Iterator<Vertex<VLabel>> iter = vertexList.iterator();
        Iterator<Vertex<VLabel>> method = ug.predecessors(v1);
        ug.add(v1, v4, el3);
        ArrayList<Vertex<VLabel>> methodVertices =
            new ArrayList<Vertex<VLabel>>();
        while (method.hasNext()) {
            methodVertices.add(method.next());
        }
        assertTrue(vertexList.size() == methodVertices.size());
        assertTrue(vertexList.containsAll(methodVertices));
    }

    @Test
    public void predecessorsUG8() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = ug.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = ug.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = ug.add(vl4);
        ELabel el1 = new ELabel("el1");
        ELabel el2 = new ELabel("el2");
        ELabel el3 = new ELabel("el3");
        ug.add(v2, v1, el1);
        ug.add(v1, v3, el2);
        ArrayList<Vertex<VLabel>> vertexList = new ArrayList<Vertex<VLabel>>();
        vertexList.add(v2);
        vertexList.add(v3);
        vertexList.add(v4);
        Iterator<Vertex<VLabel>> iter = vertexList.iterator();
        Iterator<Vertex<VLabel>> method = ug.predecessors(v1);
        ug.add(v4, v1, el3);
        ArrayList<Vertex<VLabel>> methodVertices =
            new ArrayList<Vertex<VLabel>>();
        while (method.hasNext()) {
            methodVertices.add(method.next());
        }
        assertTrue(vertexList.size() == methodVertices.size());
        assertTrue(vertexList.containsAll(methodVertices));
    }

    @Test
    public void predecessorsUG9() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = ug.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = ug.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = ug.add(vl4);
        ug.add(v1, v2);
        ug.add(v1, v3);
        ArrayList<Vertex<VLabel>> vertexList = new ArrayList<Vertex<VLabel>>();
        vertexList.add(v2);
        vertexList.add(v3);
        vertexList.add(v4);
        Iterator<Vertex<VLabel>> iter = vertexList.iterator();
        Iterator<Vertex<VLabel>> method = ug.predecessors(v1);
        ug.add(v1, v4);
        ArrayList<Vertex<VLabel>> methodVertices =
            new ArrayList<Vertex<VLabel>>();
        while (method.hasNext()) {
            methodVertices.add(method.next());
        }
        assertTrue(vertexList.size() == methodVertices.size());
        assertTrue(vertexList.containsAll(methodVertices));
    }

    @Test
    public void predecessorsUG10() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = ug.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = ug.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = ug.add(vl4);
        ELabel el1 = new ELabel("el1");
        ELabel el2 = new ELabel("el2");
        ELabel el3 = new ELabel("el3");
        ug.add(v2, v1);
        ug.add(v1, v3);
        ArrayList<Vertex<VLabel>> vertexList = new ArrayList<Vertex<VLabel>>();
        vertexList.add(v2);
        vertexList.add(v3);
        vertexList.add(v4);
        Iterator<Vertex<VLabel>> iter = vertexList.iterator();
        Iterator<Vertex<VLabel>> method = ug.predecessors(v1);
        ug.add(v4, v1);
        ArrayList<Vertex<VLabel>> methodVertices =
            new ArrayList<Vertex<VLabel>>();
        while (method.hasNext()) {
            methodVertices.add(method.next());
        }
        assertTrue(vertexList.size() == methodVertices.size());
        assertTrue(vertexList.containsAll(methodVertices));
    }

    @Test
    public void predecessorsUG11() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = ug.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = ug.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = ug.add(vl4);
        ELabel el1 = new ELabel("el1");
        ug.add(v2, v1);
        ug.add(v1, v3, el1);
        ArrayList<Vertex<VLabel>> vertexList = new ArrayList<Vertex<VLabel>>();
        vertexList.add(v2);
        vertexList.add(v3);
        vertexList.add(v4);
        Iterator<Vertex<VLabel>> iter = vertexList.iterator();
        Iterator<Vertex<VLabel>> method = ug.predecessors(v1);
        ug.add(v2, v3, el1);
        ug.add(v4, v1);
        ArrayList<Vertex<VLabel>> methodVertices =
            new ArrayList<Vertex<VLabel>>();
        while (method.hasNext()) {
            methodVertices.add(method.next());
        }
        assertTrue(vertexList.size() == methodVertices.size());
        assertTrue(vertexList.containsAll(methodVertices));
    }

    /** neighbors(Vertex<VLabel> v). */
    @Test
    public void neighborsUG1() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v = ug.add(vl1);
        ArrayList<Vertex<VLabel>> answer = new ArrayList<Vertex<VLabel>>();
        Iterator<Vertex<VLabel>> iter = answer.iterator();
        Iterator<Vertex<VLabel>> method = ug.neighbors(v);
        for (int i = 0; i < answer.size(); i += 1) {
            assertEquals(iter.next(), method.next());
        }
    }

    @Test
    public void neighborsUG2() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = ug.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = ug.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = ug.add(vl4);
        ELabel el1 = new ELabel("el1");
        ELabel el2 = new ELabel("el2");
        ELabel el3 = new ELabel("el3");
        ug.add(v1, v2, el1);
        ug.add(v1, v3, el2);
        ug.add(v1, v4, el3);
        ArrayList<Vertex<VLabel>> vertexList = new ArrayList<Vertex<VLabel>>();
        vertexList.add(v2);
        vertexList.add(v3);
        vertexList.add(v4);
        Iterator<Vertex<VLabel>> iter = vertexList.iterator();
        Iterator<Vertex<VLabel>> method = ug.neighbors(v1);
        ArrayList<Vertex<VLabel>> methodVertices =
            new ArrayList<Vertex<VLabel>>();
        while (method.hasNext()) {
            methodVertices.add(method.next());
        }
        assertTrue(vertexList.size() == methodVertices.size());
        assertTrue(vertexList.containsAll(methodVertices));
    }

    @Test
    public void neighborsUG3() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = ug.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = ug.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = ug.add(vl4);
        ELabel el1 = new ELabel("el1");
        ELabel el2 = new ELabel("el2");
        ELabel el3 = new ELabel("el3");
        ug.add(v2, v1, el1);
        ug.add(v1, v3, el2);
        ug.add(v4, v1, el3);
        ArrayList<Vertex<VLabel>> vertexList = new ArrayList<Vertex<VLabel>>();
        vertexList.add(v2);
        vertexList.add(v3);
        vertexList.add(v4);
        Iterator<Vertex<VLabel>> iter = vertexList.iterator();
        Iterator<Vertex<VLabel>> method = ug.neighbors(v1);
        ArrayList<Vertex<VLabel>> methodVertices =
            new ArrayList<Vertex<VLabel>>();
        while (method.hasNext()) {
            methodVertices.add(method.next());
        }
        assertTrue(vertexList.size() == methodVertices.size());
        assertTrue(vertexList.containsAll(methodVertices));
    }

    @Test
    public void neighborsUG4() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = ug.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = ug.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = ug.add(vl4);
        ug.add(v1, v2);
        ug.add(v1, v3);
        ug.add(v1, v4);
        ArrayList<Vertex<VLabel>> vertexList = new ArrayList<Vertex<VLabel>>();
        vertexList.add(v2);
        vertexList.add(v3);
        vertexList.add(v4);
        Iterator<Vertex<VLabel>> iter = vertexList.iterator();
        Iterator<Vertex<VLabel>> method = ug.neighbors(v1);
        ArrayList<Vertex<VLabel>> methodVertices =
            new ArrayList<Vertex<VLabel>>();
        while (method.hasNext()) {
            methodVertices.add(method.next());
        }
        assertTrue(vertexList.size() == methodVertices.size());
        assertTrue(vertexList.containsAll(methodVertices));
    }

    @Test
    public void neighborsUG5() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = ug.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = ug.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = ug.add(vl4);
        ELabel el1 = new ELabel("el1");
        ELabel el2 = new ELabel("el2");
        ELabel el3 = new ELabel("el3");
        ug.add(v2, v1);
        ug.add(v1, v3);
        ug.add(v4, v1);
        ArrayList<Vertex<VLabel>> vertexList = new ArrayList<Vertex<VLabel>>();
        vertexList.add(v2);
        vertexList.add(v3);
        vertexList.add(v4);
        Iterator<Vertex<VLabel>> iter = vertexList.iterator();
        Iterator<Vertex<VLabel>> method = ug.neighbors(v1);
        ArrayList<Vertex<VLabel>> methodVertices =
            new ArrayList<Vertex<VLabel>>();
        while (method.hasNext()) {
            methodVertices.add(method.next());
        }
        assertTrue(vertexList.size() == methodVertices.size());
        assertTrue(vertexList.containsAll(methodVertices));
    }

    @Test
    public void neighborsUG6() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = ug.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = ug.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = ug.add(vl4);
        ELabel el1 = new ELabel("el1");
        ug.add(v2, v1);
        ug.add(v1, v3, el1);
        ug.add(v4, v1);
        ArrayList<Vertex<VLabel>> vertexList = new ArrayList<Vertex<VLabel>>();
        vertexList.add(v2);
        vertexList.add(v3);
        vertexList.add(v4);
        Iterator<Vertex<VLabel>> iter = vertexList.iterator();
        Iterator<Vertex<VLabel>> method = ug.neighbors(v1);
        ArrayList<Vertex<VLabel>> methodVertices =
            new ArrayList<Vertex<VLabel>>();
        while (method.hasNext()) {
            methodVertices.add(method.next());
        }
        assertTrue(vertexList.size() == methodVertices.size());
        assertTrue(vertexList.containsAll(methodVertices));
    }

    @Test
    public void neighborsUG7() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = ug.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = ug.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = ug.add(vl4);
        ELabel el1 = new ELabel("el1");
        ELabel el2 = new ELabel("el2");
        ELabel el3 = new ELabel("el3");
        ug.add(v1, v2, el1);
        ug.add(v1, v3, el2);
        ArrayList<Vertex<VLabel>> vertexList = new ArrayList<Vertex<VLabel>>();
        vertexList.add(v2);
        vertexList.add(v3);
        vertexList.add(v4);
        Iterator<Vertex<VLabel>> iter = vertexList.iterator();
        Iterator<Vertex<VLabel>> method = ug.neighbors(v1);
        ug.add(v1, v4, el3);
        ArrayList<Vertex<VLabel>> methodVertices =
            new ArrayList<Vertex<VLabel>>();
        while (method.hasNext()) {
            methodVertices.add(method.next());
        }
        assertTrue(vertexList.size() == methodVertices.size());
        assertTrue(vertexList.containsAll(methodVertices));
    }

    @Test
    public void neighborsUG8() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = ug.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = ug.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = ug.add(vl4);
        ELabel el1 = new ELabel("el1");
        ELabel el2 = new ELabel("el2");
        ELabel el3 = new ELabel("el3");
        ug.add(v2, v1, el1);
        ug.add(v1, v3, el2);
        ArrayList<Vertex<VLabel>> vertexList = new ArrayList<Vertex<VLabel>>();
        vertexList.add(v2);
        vertexList.add(v3);
        vertexList.add(v4);
        Iterator<Vertex<VLabel>> iter = vertexList.iterator();
        Iterator<Vertex<VLabel>> method = ug.neighbors(v1);
        ug.add(v4, v1, el3);
        ArrayList<Vertex<VLabel>> methodVertices =
            new ArrayList<Vertex<VLabel>>();
        while (method.hasNext()) {
            methodVertices.add(method.next());
        }
        assertTrue(vertexList.size() == methodVertices.size());
        assertTrue(vertexList.containsAll(methodVertices));
    }

    @Test
    public void neighborsUG9() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = ug.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = ug.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = ug.add(vl4);
        ug.add(v1, v2);
        ug.add(v1, v3);
        ArrayList<Vertex<VLabel>> vertexList = new ArrayList<Vertex<VLabel>>();
        vertexList.add(v2);
        vertexList.add(v3);
        vertexList.add(v4);
        Iterator<Vertex<VLabel>> iter = vertexList.iterator();
        Iterator<Vertex<VLabel>> method = ug.neighbors(v1);
        ug.add(v1, v4);
        ArrayList<Vertex<VLabel>> methodVertices =
            new ArrayList<Vertex<VLabel>>();
        while (method.hasNext()) {
            methodVertices.add(method.next());
        }
        assertTrue(vertexList.size() == methodVertices.size());
        assertTrue(vertexList.containsAll(methodVertices));
    }

    @Test
    public void neighborsUG10() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = ug.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = ug.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = ug.add(vl4);
        ELabel el1 = new ELabel("el1");
        ELabel el2 = new ELabel("el2");
        ELabel el3 = new ELabel("el3");
        ug.add(v2, v1);
        ug.add(v1, v3);
        ArrayList<Vertex<VLabel>> vertexList = new ArrayList<Vertex<VLabel>>();
        vertexList.add(v2);
        vertexList.add(v3);
        vertexList.add(v4);
        Iterator<Vertex<VLabel>> iter = vertexList.iterator();
        Iterator<Vertex<VLabel>> method = ug.neighbors(v1);
        ug.add(v4, v1);
        ArrayList<Vertex<VLabel>> methodVertices =
            new ArrayList<Vertex<VLabel>>();
        while (method.hasNext()) {
            methodVertices.add(method.next());
        }
        assertTrue(vertexList.size() == methodVertices.size());
        assertTrue(vertexList.containsAll(methodVertices));
    }

    @Test
    public void neighborsUG11() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = ug.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = ug.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = ug.add(vl4);
        ELabel el1 = new ELabel("el1");
        ug.add(v2, v1);
        ug.add(v1, v3, el1);
        ArrayList<Vertex<VLabel>> vertexList = new ArrayList<Vertex<VLabel>>();
        vertexList.add(v2);
        vertexList.add(v3);
        vertexList.add(v4);
        Iterator<Vertex<VLabel>> iter = vertexList.iterator();
        Iterator<Vertex<VLabel>> method = ug.neighbors(v1);
        ug.add(v2, v3, el1);
        ug.add(v4, v1);
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
    public void edgesUG1() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = ug.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = ug.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = ug.add(vl4);
        ArrayList<Edge<VLabel, ELabel>> edgeList = new ArrayList<Edge<VLabel,
            ELabel>>();
        ELabel el1 = new ELabel("el1");
        ELabel el2 = new ELabel("el2");
        ELabel el3 = new ELabel("el3");
        edgeList.add(ug.add(v1, v2, el1));
        edgeList.add(ug.add(v1, v3, el2));
        edgeList.add(ug.add(v1, v4, el3));
        Iterator<Edge<VLabel, ELabel>> iter = edgeList.iterator();
        Iterator<Edge<VLabel, ELabel>> method = ug.edges();
        ArrayList<Edge<VLabel, ELabel>> methodEdges =
            new ArrayList<Edge<VLabel, ELabel>>();
        while (method.hasNext()) {
            methodEdges.add(method.next());
        }
        assertEquals(edgeList.size(), methodEdges.size());
        assertTrue(edgeList.containsAll(methodEdges));
    }

    @Test
    public void edgesUG2() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = ug.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = ug.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = ug.add(vl4);
        ArrayList<Edge<VLabel, ELabel>> edgeList = new ArrayList<Edge<VLabel,
            ELabel>>();
        edgeList.add(ug.add(v1, v2));
        edgeList.add(ug.add(v1, v3));
        edgeList.add(ug.add(v1, v4));
        Iterator<Edge<VLabel, ELabel>> iter = edgeList.iterator();
        Iterator<Edge<VLabel, ELabel>> method = ug.edges();
        ArrayList<Edge<VLabel, ELabel>> methodEdges =
            new ArrayList<Edge<VLabel, ELabel>>();
        while (method.hasNext()) {
            methodEdges.add(method.next());
        }
        assertEquals(edgeList.size(), methodEdges.size());
        assertTrue(edgeList.containsAll(methodEdges));
    }

    @Test
    public void edgesUG3() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = ug.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = ug.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = ug.add(vl4);
        ArrayList<Edge<VLabel, ELabel>> edgeList = new ArrayList<Edge<VLabel,
            ELabel>>();
        ELabel el1 = new ELabel("el1");
        ELabel el2 = new ELabel("el2");
        ELabel el3 = new ELabel("el3");
        edgeList.add(ug.add(v1, v2, el1));
        edgeList.add(ug.add(v1, v3, el2));
        Iterator<Edge<VLabel, ELabel>> method = ug.edges();
        edgeList.add(ug.add(v1, v4, el3));
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
    public void edgesUG4() {
        UndirectedGraph<VLabel, ELabel> ug
            = new UndirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = ug.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = ug.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = ug.add(vl4);
        ArrayList<Edge<VLabel, ELabel>> edgeList = new ArrayList<Edge<VLabel,
            ELabel>>();
        edgeList.add(ug.add(v1, v2));
        edgeList.add(ug.add(v1, v3));
        Iterator<Edge<VLabel, ELabel>> method = ug.edges();
        edgeList.add(ug.add(v1, v4));
        Iterator<Edge<VLabel, ELabel>> iter = edgeList.iterator();
        ArrayList<Edge<VLabel, ELabel>> methodEdges =
            new ArrayList<Edge<VLabel, ELabel>>();
        while (method.hasNext()) {
            methodEdges.add(method.next());
        }
        assertEquals(edgeList.size(), methodEdges.size());
        assertTrue(edgeList.containsAll(methodEdges));
    }
}
