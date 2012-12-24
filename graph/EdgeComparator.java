package graph;

import java.util.Comparator;

/** Class EdgeComparator to compare Edges.
* @author andreawu */
class EdgeComparator<VLabel, ELabel>
    implements Comparator<Edge<VLabel, ELabel>> {

    /** Method to compare edge A and B. Returns -1, 0, 1 for >, =, <. */
    @SuppressWarnings("unchecked")
    public int compare(Edge<VLabel, ELabel> a, Edge<VLabel, ELabel> b) {
        return ((Comparable<ELabel>) a.getLabel()).compareTo(b.getLabel());
    }
}
