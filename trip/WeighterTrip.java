package trip;
import graph.Weighter;
import java.util.HashMap;

/** Weighter class for Trip client.
 * @author andreawu */
class WeighterTrip<Item> extends WeightingTrip<Item>
    implements Weighter<Item> {

    /** Stores incoming edges from a vertex. */
    protected HashMap<String, Double> _weight;

    /** Constructor for this class. */
    public WeighterTrip() {
        HashMap<String, Double> weight
            = new HashMap<String, Double>();
        _weight = weight;
    }

    /** Sets weight of V to be D. */
    @SuppressWarnings("unchecked")
    public void setWeight(Item v, double d) {
        _weight.put((String) v, new Double(d));
    }

    @Override
    public double weight(Item vLabel) {
        return _weight.get(vLabel);
    }
}
