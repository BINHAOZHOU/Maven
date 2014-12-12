package GPU_MC;

/**
 * Created by binhaozhou on 12/12/14.
 */
import java.util.List;

import org.joda.time.DateTime;


public class PayOut01 implements PayOut{
    public String option;
    public PayOut01(String option){
        this.option=option;
    }

    //override
    public double getPayOut (StockPath01 path){
        double p;
        switch(option){
            case"European":
                p=path.getSimulatedPath().get(251).getSecond();
                if(p>165)
                    return p-165;
                else
                    return 0;
            case"Asian":
                double sum=0;
                List<Pair<DateTime,Double>> price=path.getSimulatedPath();
                for(int i= 0;i<252;i++){
                    sum+=price.get(i).getSecond();
                }
                if(sum/252>165)
                    return sum/252-165;
                else
                    return 0;

            default:
                System.out.println("No such option");
                return 0;
        }
    }


}