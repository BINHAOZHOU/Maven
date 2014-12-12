package GPU_MC;

/**
 * Created by binhaozhou on 12/12/14.
 */
public class SimulationManager {

    public static void main(String[] args) {
        StatsCollector stats1=new StatsCollector();
      /*
       * instantiate an object of the class PayOut ,
       * input the European to the constructor
       * in order to get the value of a European call
       */
        PayOut01 pay=new PayOut01("European");
        while (true){
    	 /*
    	  * loop until satisfying the termination condition
    	  *  input the stock parameter
    	  *  call the getPayout method to get payoff
    	  *
    	  */
            double payoff=pay.getPayOut(new StockPath01(2014,10,15,252,152.35,0.0001,0.01));
            double value=Math.exp(-0.0001*252)*payoff; //discount the payoff to get the option value
            stats1.addValue(value);
            System.out.println("Simulation times is:"+stats1.getN()+"  "+"Error is :"+stats1.getError(0.96));
            if(stats1.getN()>100&&stats1.getError(0.96)<=0.01)
                break;
        }
        System.out.println("The Value of Option is:"+stats1.getAverage() );

    }
}
