package graph;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

/** Graph utility routines.
 * @author Andrea Wu */
public final class Graphs {

    /** Comparator for Graphs' Priority Queue that orders the vertices
     *  according to their weight using a vweighter. */
    private static class VerticesCompareW<VLabel>
        implements Comparator<Vertex<VLabel>> {

        /** Vweighter used to compare VLabels. */
        private Weighter<VLabel> _vweighter;

        /** Constructor that takes in the vweighter and eweighter so
         * that the comparator can compare the VLabels according to
         * VWEIGHTER. */
        public VerticesCompareW(Weighter<VLabel> vweighter) {
            _vweighter = vweighter;
        }

        /** Returns int to compare Vlabel A and B. */
        public int compare(Vertex<VLabel> a, Vertex<VLabel> b) {
            if (_vweighter.weight(a.getLabel())
                < _vweighter.weight(b.getLabel())) {
                return -1;
            } else if (_vweighter.weight(a.getLabel())
                > _vweighter.weight(b.getLabel())) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    /** Comparator for Graphs' Priority Queue that orders the vertices
     *  according to their weight; no vweighter. */
    private static class VerticesCompareNW<VLabel>
        implements Comparator<Vertex<VLabel>> {

        /** Returns int to compare Vlabel A and B. */
        public int compare(Vertex<VLabel> a, Vertex<VLabel> b) {
            if (((Weightable) a.getLabel()).weight()
                < ((Weightable) b.getLabel()).weight()) {
                return -1;
            } else if (((Weightable) a.getLabel()).weight()
                > ((Weightable) b.getLabel()).weight()) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    /** Set the weights of the vertices of G reachable from V0 to the minimum
     *  weighted path lengths from V0.  VWEIGHTER extracts and sets weights of
     *  vertex labels (type VLABEL), and EWEIGHTER extracts weights of
     *  edge labels (type ELABEL). */
    public static <VLabel, ELabel> void
    computeShortestPathLengths(Graph<VLabel, ELabel> G, Vertex<VLabel> V0,
        Weighter<VLabel> vweighter, Weighting<ELabel> eweighter) {
        Iterator<Vertex<VLabel>> itV = G.vertices();
        while (itV.hasNext()) {
            Vertex<VLabel> temp = itV.next();
            vweighter.setWeight(temp.getLabel(), Double.POSITIVE_INFINITY);
            temp.back(null);
        }
        vweighter.setWeight(V0.getLabel(), 0.0);
        PriorityQueue<Vertex<VLabel>> fringe
            = new PriorityQueue<Vertex<VLabel>>(G.vertexSize(),
                new VerticesCompareW<VLabel>(vweighter));
        fringe.add(V0);
        while (fringe.size() != 0) {
            Vertex<VLabel> minV = fringe.poll();
            Iterator<Edge<VLabel, ELabel>> itOEdg = G.outEdges(minV);
            while (G.isDirected() && itOEdg.hasNext()) {
                Edge<VLabel, ELabel> temp = itOEdg.next();
                Vertex<VLabel> v1 = temp.getV1();
                if (vweighter.weight(minV.getLabel())
                    + eweighter.weight(temp.getLabel())
                    < vweighter.weight(v1.getLabel())) {
                    vweighter.setWeight(v1.getLabel(),
                        vweighter.weight(minV.getLabel())
                        + eweighter.weight(temp.getLabel()));
                    v1.back(minV);
                    fringe.add(v1);
                }
            }
            Iterator<Edge<VLabel, ELabel>> itOEug = G.outEdges(minV);
            while (!G.isDirected() && itOEug.hasNext()) {
                Edge<VLabel, ELabel> temp = itOEug.next();
                Vertex<VLabel> v1 = null;
                if (temp.getV0() == minV) {
                    v1 = temp.getV1();
                } else {
                    v1 = temp.getV0();
                }
                if (vweighter.weight(minV.getLabel())
                    + eweighter.weight(temp.getLabel())
                    < vweighter.weight(v1.getLabel())) {
                    vweighter.setWeight(v1.getLabel(),
                        vweighter.weight(minV.getLabel())
                        + eweighter.weight(temp.getLabel()));
                    v1.back(minV);
                    fringe.add(v1);
                }
            }
        }
    }

    /** Set the weights of the vertices of G reachable from V0 to the minimum
     *  weighted path lengths from V0. VLABEL and ELABEL are the
     *  vertex- and edge-label types. */
    public static <VLabel extends Weightable, ELabel extends Weighted>
    void computeShortestPathLengths(Graph<VLabel, ELabel> G,
        Vertex<VLabel> V0) {
        Iterator<Vertex<VLabel>> itV = G.vertices();
        while (itV.hasNext()) {
            Vertex<VLabel> temp = itV.next();
            temp.getLabel().setWeight(Double.POSITIVE_INFINITY);
            temp.back(null);
        }
        V0.getLabel().setWeight(0.0);
        PriorityQueue<Vertex<VLabel>> fringe
            = new PriorityQueue<Vertex<VLabel>>(G.vertexSize(),
                new VerticesCompareNW<VLabel>());
        fringe.add(V0);
        while (fringe.size() != 0) {
            Vertex<VLabel> minV = fringe.poll();
            Iterator<Edge<VLabel, ELabel>> itOEdg = G.outEdges(minV);
            while (G.isDirected() && itOEdg.hasNext()) {
                Edge<VLabel, ELabel> temp = itOEdg.next();
                Vertex<VLabel> v1 = temp.getV1();
                if (minV.getLabel().weight()
                    + temp.getLabel().weight()
                    < v1.getLabel().weight()) {
                    v1.getLabel().setWeight(minV.getLabel().weight()
                        + temp.getLabel().weight());
                    v1.back(minV);
                    fringe.add(v1);
                }
            }
            Iterator<Edge<VLabel, ELabel>> itOEug = G.outEdges(minV);
            while (!G.isDirected() && itOEug.hasNext()) {
                Edge<VLabel, ELabel> temp = itOEug.next();
                Vertex<VLabel> v1 = null;
                if (temp.getV0() == minV) {
                    v1 = temp.getV1();
                } else {
                    v1 = temp.getV0();
                }
                if (minV.getLabel().weight()
                    + temp.getLabel().weight()
                    < v1.getLabel().weight()) {
                    v1.getLabel().setWeight(minV.getLabel().weight()
                        + temp.getLabel().weight());
                    v1.back(minV);
                    fringe.add(v1);
                }
            }
        }
    }

    /** Set the weights of the vertices in G so that vertices
     *  along a path in G from V0 to V1 are weighted with the minimum
     *  path length from V0 and all other vertices are weighted to at least
     *  their minimum distance from V0. VWEIGHTER extracts and sets weights
     *  of vertex labels (type VLABEL), and EWEIGHTER extracts weights of
     *  edge labels (type ELABEL). */
    public static <VLabel, ELabel> void
    computeShortestPathLengths(Graph<VLabel, ELabel> G,
        Vertex<VLabel> V0, Vertex<VLabel> V1, Weighter<VLabel> vweighter,
        Weighting<ELabel> eweighter) {
        Iterator<Vertex<VLabel>> itV = G.vertices();
        while (itV.hasNext()) {
            Vertex<VLabel> temp = itV.next();
            vweighter.setWeight(temp.getLabel(), Double.POSITIVE_INFINITY);
            temp.back(null);
        }
        vweighter.setWeight(V0.getLabel(), 0.0);
        PriorityQueue<Vertex<VLabel>> fringe
            = new PriorityQueue<Vertex<VLabel>>(G.vertexSize(),
                new VerticesCompareW<VLabel>(vweighter));
        fringe.add(V0);
        while (fringe.size() != 0) {
            Vertex<VLabel> minV = fringe.poll();
            if (minV == V1) {
                return;
            }
            Iterator<Edge<VLabel, ELabel>> itOEdg = G.outEdges(minV);
            while (G.isDirected() && itOEdg.hasNext()) {
                Edge<VLabel, ELabel> temp = itOEdg.next();
                Vertex<VLabel> v1 = temp.getV1();
                if (vweighter.weight(minV.getLabel())
                    + eweighter.weight(temp.getLabel())
                    < vweighter.weight(v1.getLabel())) {
                    vweighter.setWeight(v1.getLabel(),
                        vweighter.weight(minV.getLabel())
                        + eweighter.weight(temp.getLabel()));
                    v1.back(minV);
                    fringe.add(v1);
                }
            }
            Iterator<Edge<VLabel, ELabel>> itOEug = G.outEdges(minV);
            while (!G.isDirected() && itOEug.hasNext()) {
                Edge<VLabel, ELabel> temp = itOEug.next();
                Vertex<VLabel> v1 = null;
                if (temp.getV0() == minV) {
                    v1 = temp.getV1();
                } else {
                    v1 = temp.getV0();
                }
                if (vweighter.weight(minV.getLabel())
                    + eweighter.weight(temp.getLabel())
                    < vweighter.weight(v1.getLabel())) {
                    vweighter.setWeight(v1.getLabel(),
                        vweighter.weight(minV.getLabel())
                        + eweighter.weight(temp.getLabel()));
                    v1.back(minV);
                    fringe.add(v1);
                }
            }
        }
    }

    /** Set the weights vertices in G so that vertices along a path in G
     *  from V0 to V1 are weighted with the minimum path length from
     *  V0 and all other vertices are weighted to at least their
     *  minimum distance from V0. VLABEL is the type of vertex label and
     *  ELABEL is the type of edge label. */
    public static <VLabel extends Weightable, ELabel extends Weighted>
    void computeShortestPathLengths(Graph<VLabel, ELabel> G,
        Vertex<VLabel> V0, Vertex<VLabel> V1) {
        Iterator<Vertex<VLabel>> itV = G.vertices();
        while (itV.hasNext()) {
            Vertex<VLabel> temp = itV.next();
            temp.getLabel().setWeight(Double.POSITIVE_INFINITY);
            temp.back(null);
        }
        V0.getLabel().setWeight(0.0);
        PriorityQueue<Vertex<VLabel>> fringe
            = new PriorityQueue<Vertex<VLabel>>(G.vertexSize(),
                new VerticesCompareNW<VLabel>());
        fringe.add(V0);
        while (fringe.size() != 0) {
            Vertex<VLabel> minV = fringe.poll();
            if (minV == V1) {
                return;
            }
            Iterator<Edge<VLabel, ELabel>> itOEdg = G.outEdges(minV);
            while (G.isDirected() && itOEdg.hasNext()) {
                Edge<VLabel, ELabel> temp = itOEdg.next();
                Vertex<VLabel> v1 = temp.getV1();
                if (minV.getLabel().weight()
                    + temp.getLabel().weight()
                    < v1.getLabel().weight()) {
                    v1.getLabel().setWeight(minV.getLabel().weight()
                        + temp.getLabel().weight());
                    v1.back(minV);
                    fringe.add(v1);
                }
            }
            Iterator<Edge<VLabel, ELabel>> itOEug = G.outEdges(minV);
            while (!G.isDirected() && itOEug.hasNext()) {
                Edge<VLabel, ELabel> temp = itOEug.next();
                Vertex<VLabel> v1 = null;
                if (temp.getV0() == minV) {
                    v1 = temp.getV1();
                } else {
                    v1 = temp.getV0();
                }
                if (minV.getLabel().weight()
                    + temp.getLabel().weight()
                    < v1.getLabel().weight()) {
                    v1.getLabel().setWeight(minV.getLabel().weight()
                        + temp.getLabel().weight());
                    v1.back(minV);
                    fringe.add(v1);
                }
            }
        }
    }

    /** Assuming that the vertex weights in the vertex labels have been
     *  set by a call to either computeShortestPathLengths(G, V0, V1...) or
     *  computeShortestPathLengths(G, V0...) return a path through G
     *  beginning with V0 and ending with V1 that has minimum weight,
     *  according to the weight functions EWEIGHTER on edge labels
     *  (type ELABEL) and VWEIGHTER on vertex labels (type VLABEL). */

    public static <VLabel, ELabel> List<Edge<VLabel, ELabel>>
    shortestPath(Graph<VLabel, ELabel> G, Vertex<VLabel> V0,
        Vertex<VLabel> V1, Weighting<VLabel> vweighter,
        Weighting<ELabel> eweighter) {
        LinkedList<Edge<VLabel, ELabel>> path
            = new LinkedList<Edge<VLabel, ELabel>>();
        LinkedList<Edge<VLabel, ELabel>> edgesInG
            = new LinkedList<Edge<VLabel, ELabel>>();
        Iterator<Edge<VLabel, ELabel>> edgeIt = G.edges();
        while (edgeIt.hasNext()) {
            edgesInG.add(edgeIt.next());
        }
        Vertex<VLabel> v = V1;
        Vertex<VLabel> back = v.getBack();
        while (true) {
            LinkedList<Edge<VLabel, ELabel>> multiple
                = new LinkedList<Edge<VLabel, ELabel>>();
            for (int i = 0; i < edgesInG.size(); i += 1) {
                if (edgesInG.get(i).getV0() == back && edgesInG.get(i).getV1()
                    == v) {
                    multiple.add(edgesInG.get(i));
                } else if (!G.isDirected()
                    && edgesInG.get(i).getV1() == back
                    && edgesInG.get(i).getV0() == v) {
                    multiple.add(edgesInG.get(i));
                }
            }
            if (multiple.size() > 1) {
                for (int w = 0; w < multiple.size(); w += 1) {
                    if (vweighter.weight(back.getLabel())
                        + eweighter.weight(multiple.get(w).getLabel())
                        == vweighter.weight(v.getLabel())) {
                        path.add(0, multiple.get(w));
                        break;
                    }
                }
            } else {
                for (int w = 0; w < multiple.size(); w += 1) {
                    path.add(0, multiple.get(w));
                }
            }
            if (back == V0) {
                break;
            }
            v = back;
            back = v.getBack();
        }
        return path;
    }

    /** Assuming that the vertex weights in the vertex labels have been
     *  set by a call to either computeShortestPathLengths(G, V0, V1...) or
     *  computeShortestPathLengths(G, V0...) return a path through G
     *  beginning with V0 and ending with V1 that has minimum weight.
     *  VLABEL is the vertex label type and ELABEL is the edge label type. */

    public static
    <VLabel extends Weightable, ELabel extends Weighted>
    List<Edge<VLabel, ELabel>> shortestPath(Graph<VLabel, ELabel> G,
        Vertex<VLabel> V0, Vertex<VLabel> V1) {
        LinkedList<Edge<VLabel, ELabel>> path
            = new LinkedList<Edge<VLabel, ELabel>>();
        LinkedList<Edge<VLabel, ELabel>> edgesInG
            = new LinkedList<Edge<VLabel, ELabel>>();
        Iterator<Edge<VLabel, ELabel>> edgeIt = G.edges();
        while (edgeIt.hasNext()) {
            edgesInG.add(edgeIt.next());
        }
        Vertex<VLabel> v = V1;
        Vertex<VLabel> back = v.getBack();
        while (true) {
            LinkedList<Edge<VLabel, ELabel>> multiple
                = new LinkedList<Edge<VLabel, ELabel>>();
            for (int i = 0; i < edgesInG.size(); i += 1) {
                if (edgesInG.get(i).getV0() == back && edgesInG.get(i).getV1()
                    == v) {
                    multiple.add(edgesInG.get(i));
                } else if (!G.isDirected()
                    && edgesInG.get(i).getV1() == back
                    && edgesInG.get(i).getV0() == v) {
                    multiple.add(edgesInG.get(i));
                }
            }
            if (multiple.size() > 1) {
                for (int w = 0; w < multiple.size(); w += 1) {
                    if (back.getLabel().weight()
                        + multiple.get(w).getLabel().weight()
                        == v.getLabel().weight()) {
                        path.add(0, multiple.get(w));
                        break;
                    }
                }
            } else {
                for (int w = 0; w < multiple.size(); w += 1) {
                    path.add(0, multiple.get(w));
                }
            }
            if (back == V0) {
                break;
            }
            v = back;
            back = v.getBack();
        }
        return path;
    }
}
