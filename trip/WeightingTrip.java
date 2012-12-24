package trip;
import graph.Weighting;
import java.util.ArrayList;

/** Weighting class for Trip client.
 * @author andreawu */
class WeightingTrip<Item> implements Weighting<Item> {

    /** Returns weight of ELABEL. */
    @SuppressWarnings("unchecked")
    public double weight(Item eLabel) {
        return Double.parseDouble(((ArrayList<String>) eLabel).get(1));
    }
}
