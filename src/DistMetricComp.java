import java.util.Comparator;

// -------------------------------------------------------------------------
/**
 * The comparator for the Priority Queue in terms of distance metric
 *
 * @author Danh
 * @version Apr 1, 2014
 */
public class DistMetricComp implements Comparator<State>
{
    @Override
    public int compare(State a, State b)
    {
        return (a.getDistanceMetric() - b.getDistanceMetric());
    }
}
