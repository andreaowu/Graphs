package graph;

/** A type of object that attaches a weight to a value of type ITEM.
 *  @author P. N. Hilfinger. */
public interface Weighting<Item> {

    /** Returns the weight of X. */
    double weight(Item x);

}
