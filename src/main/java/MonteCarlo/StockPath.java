package GPU_MC;

/**
 * Created by binhaozhou on 12/12/14.
 */
import java.util.List;
import org.joda.time.DateTime;

public interface StockPath {
    public List<Pair<DateTime,Double>> getSimulatedPath();
}
