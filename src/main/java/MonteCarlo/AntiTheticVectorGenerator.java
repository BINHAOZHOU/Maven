package GPU_MC;

/**
 * Created by binhaozhou on 12/12/14.
 */
import java.util.Arrays;

public class AntiTheticVectorGenerator implements RandomVectorGenerator{

    private RandomVectorGenerator rvg;
    double[] lastVector;

    public AntiTheticVectorGenerator(RandomVectorGenerator rvg){
        this.rvg = rvg;
    }

    @Override
    public double[] getVector(int n) {
        if ( lastVector == null ){
            lastVector = rvg.getVector(n);
            return lastVector;
        } else {
            double[] tmp =Arrays.copyOf(lastVector, lastVector.length);
            lastVector = null;
            for (int i = 0; i < tmp.length; ++i){ tmp[i] = -tmp[i];}
            return tmp;
        }
    }
}