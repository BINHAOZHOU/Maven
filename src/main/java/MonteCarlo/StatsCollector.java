package GPU_MC;

/**
 * Created by binhaozhou on 12/12/14.
 */
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class StatsCollector {
    /*
     * the DescriptiveStatistics class is used to handle the  mean ,standard deviation
     *  and other statistical indicators
     */
    public DescriptiveStatistics  stats = new DescriptiveStatistics();
    private double average;
    private double sigma;
    //Simulation times
    private int N;
    private double error;
    double sum = 0.; //temporary variable for calculation
    double sumSq = 0.; //temporary variable for calculation

    public StatsCollector() {
        this.average = 0.;
        this.sigma = 0.;
        this.N = 0;
    }

    public void addValue(Double value) {
        N += 1;
        sum += value;
        average = sum/N;
        sumSq = sumSq + value*value;
        sigma = Math.sqrt((sumSq/N- average*average));

    }

    public double getAverage() {
        return average;
    }

    public double getSigma() {
        return sigma;
    }

    public int getN() {
        return N;
    }

    public double getError( double prob ) {
        NormalDistribution Y = new NormalDistribution();
        double y = Y.inverseCumulativeProbability(prob); // to get the value when the probability is given
        error = y * sigma/Math.sqrt(N);
        return error;
    }
}
