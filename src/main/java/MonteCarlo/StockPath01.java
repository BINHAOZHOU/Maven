package GPU_MC;

import java.util.List;
import java.util.ArrayList;

import org.joda.time.DateTime;

public class  StockPath01 implements StockPath {
    private DateTime date;
    private double r,sigma,S0;
    private int duration;
    public StockPath01 (int Year, int Month, int Day,int duration,double S0,double r,double sigma){
        this.date=new DateTime(Year,Month,Day,0,0,0,0);
        this.duration=duration;
        this.S0=S0;
        this.r=r;
        this.sigma=sigma;
    }
    //override
    public List<Pair<DateTime,Double>> getSimulatedPath(){
        List<Pair<DateTime,Double>> path=new ArrayList<Pair<DateTime,Double>>();
        AntiTheticVectorGenerator AT = new AntiTheticVectorGenerator(new NormalGenerator(2000000));
        double[] rv=AT.getVector(duration);
        double S=S0;
        for(int i=0;i<duration;i++){
            S=S*Math.exp((r-0.5*sigma*sigma)+sigma*rv[i]);
            Pair<DateTime,Double> p= new Pair<DateTime,Double>(date.plusDays(i),S);
            path.add(p);
        }
        return path;
    }
    // to get the stock path
    public void outPutPath(){
        List<Pair<DateTime, Double>> path = this.getSimulatedPath();
        for(int i=0;i<duration;i++){
            System.out.println(path.get(i).getFirst()+":"+path.get(i).getSecond());
        }
    }
}

