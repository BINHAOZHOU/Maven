package GPU_MC;
import java.util.Random;
/**
 * Created by binhaozhou on 12/12/14.
 */

    public class UniformGenerator  implements RandomVectorGenerator{


       // generate a vector of uniform number between 0 and 1
        public double[] getVector(int n){
            double[] vector = new double[n];
            Random r = new Random();
            for ( int i = 0; i < vector.length; i++){
                vector[i] = r.nextDouble();
            }
            return vector;
        }
    }
