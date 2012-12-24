package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Comparator;

import org.junit.Test;
import static org.junit.Assert.*;

/** Unit tests for the graph package. */
public class Testing3 {

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

    @Test
    public void edgesUG5() {
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
        edgeList.add(ug.add(v1, v2));
        edgeList.add(ug.add(v1, v3, el1));
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

    /** outEdges(Vertex<VLabel> v). */
    @Test
    public void outedgesUG1() {
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
        Iterator<Edge<VLabel, ELabel>> method = ug.outEdges(v1);
        ArrayList<Edge<VLabel, ELabel>> methodEdges =
            new ArrayList<Edge<VLabel, ELabel>>();
        while (method.hasNext()) {
            methodEdges.add(method.next());
        }
        assertEquals(edgeList.size(), methodEdges.size());
        assertTrue(edgeList.containsAll(methodEdges));
    }

    @Test
    public void outedgesUG2() {
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
        edgeList.add(ug.add(v2, v1, el1));
        edgeList.add(ug.add(v1, v3, el2));
        edgeList.add(ug.add(v4, v1, el3));
        Iterator<Edge<VLabel, ELabel>> iter = edgeList.iterator();
        Iterator<Edge<VLabel, ELabel>> method = ug.outEdges(v1);
        ArrayList<Edge<VLabel, ELabel>> methodEdges =
            new ArrayList<Edge<VLabel, ELabel>>();
        while (method.hasNext()) {
            methodEdges.add(method.next());
        }
        assertEquals(edgeList.size(), methodEdges.size());
        assertTrue(edgeList.containsAll(methodEdges));
    }

    @Test
    public void outedgesUG3() {
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
        Iterator<Edge<VLabel, ELabel>> method = ug.outEdges(v1);
        ArrayList<Edge<VLabel, ELabel>> methodEdges =
            new ArrayList<Edge<VLabel, ELabel>>();
        while (method.hasNext()) {
            methodEdges.add(method.next());
        }
        assertEquals(edgeList.size(), methodEdges.size());
        assertTrue(edgeList.containsAll(methodEdges));
    }

    @Test
    public void outedgesUG4() {
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
        edgeList.add(ug.add(v2, v1));
        edgeList.add(ug.add(v1, v3));
        edgeList.add(ug.add(v4, v1));
        Iterator<Edge<VLabel, ELabel>> iter = edgeList.iterator();
        Iterator<Edge<VLabel, ELabel>> method = ug.outEdges(v1);
        ArrayList<Edge<VLabel, ELabel>> methodEdges =
            new ArrayList<Edge<VLabel, ELabel>>();
        while (method.hasNext()) {
            methodEdges.add(method.next());
        }
        assertEquals(edgeList.size(), methodEdges.size());
        assertTrue(edgeList.containsAll(methodEdges));
    }

    @Test
    public void outedgesUG5() {
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
        Iterator<Edge<VLabel, ELabel>> method = ug.outEdges(v1);
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
    public void outedgesUG6() {
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
        edgeList.add(ug.add(v2, v1, el1));
        edgeList.add(ug.add(v1, v3, el2));
        Iterator<Edge<VLabel, ELabel>> method = ug.outEdges(v1);
        ug.add(v3, v2, el1);
        edgeList.add(ug.add(v4, v1, el3));
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
    public void outedgesUG7() {
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
        Iterator<Edge<VLabel, ELabel>> method = ug.outEdges(v1);
        ug.add(v2, v3);
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

    @Test
    public void outedgesUG8() {
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
        edgeList.add(ug.add(v2, v1));
        edgeList.add(ug.add(v1, v3));
        Iterator<Edge<VLabel, ELabel>> method = ug.outEdges(v1);
        ug.add(v3, v2);
        edgeList.add(ug.add(v4, v1));
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
    public void outedgesUG9() {
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
        edgeList.add(ug.add(v2, v1));
        edgeList.add(ug.add(v1, v3, el2));
        Iterator<Edge<VLabel, ELabel>> method = ug.outEdges(v1);
        ug.add(v3, v2);
        edgeList.add(ug.add(v4, v1, el3));
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
    public void inedgesUG1() {
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
        Iterator<Edge<VLabel, ELabel>> method = ug.outEdges(v1);
        ArrayList<Edge<VLabel, ELabel>> methodEdges =
            new ArrayList<Edge<VLabel, ELabel>>();
        while (method.hasNext()) {
            methodEdges.add(method.next());
        }
        assertEquals(edgeList.size(), methodEdges.size());
        assertTrue(edgeList.containsAll(methodEdges));
    }

    @Test
    public void inedgesUG2() {
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
        edgeList.add(ug.add(v2, v1, el1));
        edgeList.add(ug.add(v1, v3, el2));
        edgeList.add(ug.add(v4, v1, el3));
        Iterator<Edge<VLabel, ELabel>> iter = edgeList.iterator();
        Iterator<Edge<VLabel, ELabel>> method = ug.outEdges(v1);
        ArrayList<Edge<VLabel, ELabel>> methodEdges =
            new ArrayList<Edge<VLabel, ELabel>>();
        while (method.hasNext()) {
            methodEdges.add(method.next());
        }
        assertEquals(edgeList.size(), methodEdges.size());
        assertTrue(edgeList.containsAll(methodEdges));
    }

    @Test
    public void inedgesUG3() {
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
        Iterator<Edge<VLabel, ELabel>> method = ug.outEdges(v1);
        ArrayList<Edge<VLabel, ELabel>> methodEdges =
            new ArrayList<Edge<VLabel, ELabel>>();
        while (method.hasNext()) {
            methodEdges.add(method.next());
        }
        assertEquals(edgeList.size(), methodEdges.size());
        assertTrue(edgeList.containsAll(methodEdges));
    }

    @Test
    public void inedgesUG4() {
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
        edgeList.add(ug.add(v2, v1));
        edgeList.add(ug.add(v1, v3));
        edgeList.add(ug.add(v4, v1));
        Iterator<Edge<VLabel, ELabel>> iter = edgeList.iterator();
        Iterator<Edge<VLabel, ELabel>> method = ug.outEdges(v1);
        ArrayList<Edge<VLabel, ELabel>> methodEdges =
            new ArrayList<Edge<VLabel, ELabel>>();
        while (method.hasNext()) {
            methodEdges.add(method.next());
        }
        assertEquals(edgeList.size(), methodEdges.size());
        assertTrue(edgeList.containsAll(methodEdges));
    }

    @Test
    public void inedgesUG5() {
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
        Iterator<Edge<VLabel, ELabel>> method = ug.outEdges(v1);
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
    public void inedgesUG6() {
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
        edgeList.add(ug.add(v2, v1, el1));
        edgeList.add(ug.add(v1, v3, el2));
        Iterator<Edge<VLabel, ELabel>> method = ug.outEdges(v1);
        ug.add(v3, v2, el1);
        edgeList.add(ug.add(v4, v1, el3));
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
    public void inedgesUG7() {
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
        Iterator<Edge<VLabel, ELabel>> method = ug.outEdges(v1);
        ug.add(v2, v3);
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

    @Test
    public void inedgesUG8() {
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
        edgeList.add(ug.add(v2, v1));
        edgeList.add(ug.add(v1, v3));
        Iterator<Edge<VLabel, ELabel>> method = ug.outEdges(v1);
        ug.add(v3, v2);
        edgeList.add(ug.add(v4, v1));
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
    public void inedgesUG9() {
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
        edgeList.add(ug.add(v2, v1));
        edgeList.add(ug.add(v1, v3, el2));
        Iterator<Edge<VLabel, ELabel>> method = ug.outEdges(v1);
        ug.add(v3, v2);
        edgeList.add(ug.add(v4, v1, el3));
        Iterator<Edge<VLabel, ELabel>> iter = edgeList.iterator();
        ArrayList<Edge<VLabel, ELabel>> methodEdges =
            new ArrayList<Edge<VLabel, ELabel>>();
        while (method.hasNext()) {
            methodEdges.add(method.next());
        }
        assertEquals(edgeList.size(), methodEdges.size());
        assertTrue(edgeList.containsAll(methodEdges));
    }

    /** edges(Vertex<VLabel> v). */
    @Test
    public void edgesVug1() {
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
        Iterator<Edge<VLabel, ELabel>> method = ug.edges(v1);
        ArrayList<Edge<VLabel, ELabel>> methodEdges =
            new ArrayList<Edge<VLabel, ELabel>>();
        while (method.hasNext()) {
            methodEdges.add(method.next());
        }
        assertEquals(edgeList.size(), methodEdges.size());
        assertTrue(edgeList.containsAll(methodEdges));
    }

    @Test
    public void edgesVug2() {
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
        edgeList.add(ug.add(v2, v1, el1));
        edgeList.add(ug.add(v1, v3, el2));
        edgeList.add(ug.add(v4, v1, el3));
        Iterator<Edge<VLabel, ELabel>> iter = edgeList.iterator();
        Iterator<Edge<VLabel, ELabel>> method = ug.edges(v1);
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
    public void orderedgesUG1() {
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
        ELabel el1 = new ELabel(3);
        ELabel el2 = new ELabel(1);
        ELabel el3 = new ELabel(2);
        edgeList.add(ug.add(v3, v1, el2));
        edgeList.add(ug.add(v4, v1, el3));
        edgeList.add(ug.add(v2, v1, el1));
        ug.orderEdges();
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
    public void orderedgesUG2() {
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
        ELabel el1 = new ELabel(3);
        ELabel el2 = new ELabel(2);
        ELabel el3 = new ELabel(1);
        edgeList.add(ug.add(v3, v1, el1));
        edgeList.add(ug.add(v4, v1, el2));
        edgeList.add(ug.add(v2, v1, el3));
        ug.orderEdges();
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
    public void orderedgesUG3() {
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
        ELabel el1 = new ELabel(1);
        ELabel el2 = new ELabel(2);
        ELabel el3 = new ELabel(3);
        edgeList.add(ug.add(v3, v1, el1));
        edgeList.add(ug.add(v4, v1, el2));
        edgeList.add(ug.add(v2, v1, el3));
        ug.orderEdges();
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

    /** orderEdges(Comparator<ELabel> comparator). */
    @Test
    @SuppressWarnings("unchecked")
    public void orderedgesCuG1() {
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
        ELabel el1 = new ELabel(1);
        ELabel el2 = new ELabel(2);
        ELabel el3 = new ELabel(3);
        edgeList.add(ug.add(v3, v1, el1));
        edgeList.add(ug.add(v4, v1, el2));
        edgeList.add(ug.add(v2, v1, el3));
        ug.orderEdges(new EdgeComparatorTesting<ELabel>());
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
    @SuppressWarnings("unchecked")
    public void orderedgesCuG2() {
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
        ELabel el1 = new ELabel(3);
        ELabel el2 = new ELabel(2);
        ELabel el3 = new ELabel(1);
        edgeList.add(ug.add(v3, v1, el1));
        edgeList.add(ug.add(v4, v1, el2));
        edgeList.add(ug.add(v2, v1, el3));
        ug.orderEdges(new EdgeComparatorTesting<ELabel>());
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
    @SuppressWarnings("unchecked")
    public void orderedgesCuG3() {
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
        ELabel el1 = new ELabel(2);
        ELabel el2 = new ELabel(1);
        ELabel el3 = new ELabel(3);
        edgeList.add(ug.add(v3, v1, el1));
        edgeList.add(ug.add(v4, v1, el2));
        edgeList.add(ug.add(v2, v1, el3));
        ug.orderEdges(new EdgeComparatorTesting<ELabel>());
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

    /* TRAVERSAL CLASS. */
    @Test
    public void traverse1() {
        System.out.println("traverse1");
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = dg.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = dg.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = dg.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = dg.add(vl4);
        ELabel el1 = new ELabel(2);
        ELabel el2 = new ELabel(1);
        ELabel el3 = new ELabel(3);
        dg.add(v1, v2, el1);
        dg.add(v1, v3, el2);
        dg.add(v1, v4, el3);
        TraversalTesting<VLabel, ELabel> t
            = new TraversalTesting<VLabel, ELabel>();
        t.traverse(dg, v1, new VertexComparatorTesting<VLabel>());
    }

    @Test
    public void traverse2() {
        System.out.println("traverse2");
        DirectedGraph<VLabel, ELabel> ug = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = ug.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = ug.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = ug.add(vl4);
        ELabel el1 = new ELabel(2);
        ELabel el2 = new ELabel(1);
        ELabel el3 = new ELabel(3);
        ug.add(v1, v2, el1);
        ug.add(v1, v3, el2);
        ug.add(v1, v4, el3);
        TraversalTesting<VLabel, ELabel> t
            = new TraversalTesting<VLabel, ELabel>();
        t.traverse(ug, v1, new VertexComparatorTesting<VLabel>());
    }

    @Test
    public void traverse3() {
        System.out.println("traverse3");
        DirectedGraph<VLabel, ELabel> dg = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl3");
        Vertex<VLabel> v1 = dg.add(vl1);
        VLabel vl2 = new VLabel("vl1");
        Vertex<VLabel> v2 = dg.add(vl2);
        VLabel vl3 = new VLabel("vl4");
        Vertex<VLabel> v3 = dg.add(vl3);
        VLabel vl4 = new VLabel("vl2");
        Vertex<VLabel> v4 = dg.add(vl4);
        ELabel el1 = new ELabel(2);
        ELabel el2 = new ELabel(1);
        ELabel el3 = new ELabel(3);
        dg.add(v1, v2, el1);
        dg.add(v1, v3, el2);
        dg.add(v1, v4, el3);
        TraversalTesting<VLabel, ELabel> t
            = new TraversalTesting<VLabel, ELabel>();
        t.traverse(dg, v1, new VertexComparatorTesting<VLabel>());
    }

    @Test
    public void traverse4() {
        System.out.println("traverse4");
        DirectedGraph<VLabel, ELabel> ug = new DirectedGraph<VLabel, ELabel>();
        VLabel vl1 = new VLabel("vl1");
        Vertex<VLabel> v1 = ug.add(vl1);
        VLabel vl2 = new VLabel("vl2");
        Vertex<VLabel> v2 = ug.add(vl2);
        VLabel vl3 = new VLabel("vl3");
        Vertex<VLabel> v3 = ug.add(vl3);
        VLabel vl4 = new VLabel("vl4");
        Vertex<VLabel> v4 = ug.add(vl4);
        ELabel el1 = new ELabel(2);
        ELabel el2 = new ELabel(1);
        ELabel el3 = new ELabel(3);
        ug.add(v1, v2, el1);
        ug.add(v1, v3, el2);
        ug.add(v1, v4, el3);
        TraversalTesting<VLabel, ELabel> t
            = new TraversalTesting<VLabel, ELabel>();
        t.traverse(ug, v1, new VertexComparatorTesting<VLabel>());
    }
}
