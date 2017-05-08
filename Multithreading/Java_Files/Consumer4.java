// @author Divyanshu Talwar 2015028
// @author Aditya Adhikary 2015007
import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Consumer4 implements Consumer,Runnable {
    private ArrayList<Producer> producers= new ArrayList<>();

    private float pTemp;
    private float pHumidity;
    private float pRainfall;
    private float[] temp;
    private float[] humidity;
    private float[] rainfall;


    ReentrantLock myLock = new ReentrantLock();
    Condition myCondition = myLock.newCondition();
    @Override
    public void receiveTempData(float[] temp) {
        this.temp=temp;

    }

    @Override
    public void receiveHumidityData(float[] humidity) {
        this.humidity=humidity;

    }

    @Override
    public void receiveRainfallData(float[] rainfall) {
        this.rainfall=rainfall;


    }

    @Override
    public boolean signal() {
        if( myLock.tryLock()) {
            try {
                myCondition.signalAll();
            } finally {
                myLock.unlock();
                return true;
            }
        }else
        {
            return false;
        }
    }

    @Override
    public void dostuff() {

        pTemp =0;
        pHumidity =0;
        pRainfall =0;
        
        int MAXN = 100;
        float[] x = new float[MAXN];
        float[] y = new float[MAXN];

        float sumx = 0.0f, sumy = 0.0f, sumx2 = 0.0f;
        for (int i = 0; i < 100 ; i++){
            x[i] = i+1;
            y[i] = temp[i];
            sumx  += x[i];
            sumx2 += x[i] * x[i];
            sumy  += y[i];
        }
        float xbar = sumx / 100;
        float ybar = sumy / 100;

        float xxbar = 0.0f, yybar = 0.0f, xybar = 0.0f;
        for (int i = 0; i < 100; i++) {
            xxbar += (x[i] - xbar) * (x[i] - xbar);
            yybar += (y[i] - ybar) * (y[i] - ybar);
            xybar += (x[i] - xbar) * (y[i] - ybar);
        }
        float beta1 = xybar / xxbar;
        float beta0 = ybar - beta1 * xbar;
        pTemp = (beta1)*101 + beta0;
        
        MAXN = 100;
        sumx = 0.0f;
        sumy = 0.0f;
        sumx2 = 0.0f;
        for (int i = 0; i < 100 ; i++){
            x[i] = i+1;
            y[i] = humidity[i];
            sumx  += x[i];
            sumx2 += x[i] * x[i];
            sumy  += y[i];
        }
        xbar = sumx / 100;
        ybar = sumy / 100;

        xxbar = 0.0f;
        yybar = 0.0f;
        xybar = 0.0f;
        for (int i = 0; i < 100; i++) {
            xxbar += (x[i] - xbar) * (x[i] - xbar);
            yybar += (y[i] - ybar) * (y[i] - ybar);
            xybar += (x[i] - xbar) * (y[i] - ybar);
        }
        beta1 = xybar / xxbar;
        beta0 = ybar - beta1 * xbar;
        pHumidity = (beta1)*101 + beta0;
        
        MAXN = 100;
        sumx = 0.0f;
        sumy = 0.0f;
        sumx2 = 0.0f;
        for (int i = 0; i < 100 ; i++){
            x[i] = i+1;
            y[i] = rainfall[i];
            sumx  += x[i];
            sumx2 += x[i] * x[i];
            sumy  += y[i];
        }
        xbar = sumx / 100;
        ybar = sumy / 100;

        xxbar = 0.0f;
        yybar = 0.0f;
        xybar = 0.0f;
        for (int i = 0; i < 100; i++) {
            xxbar += (x[i] - xbar) * (x[i] - xbar);
            yybar += (y[i] - ybar) * (y[i] - ybar);
            xybar += (x[i] - xbar) * (y[i] - ybar);
        }
        beta1 = xybar / xxbar;
        beta0 = ybar - beta1 * xbar;
        pRainfall = (beta1)*101 + beta0;
        

        for(int i=0;i<producers.size();i++)
        {
            producers.get(i).receivePred(pTemp,pHumidity,pRainfall);
        }
        temp=null;
        humidity=null;
        rainfall=null;
    }

    @Override
    public void addProducer(Producer p) {
        producers.add(p);
    }

    @Override
    public void run() {
        myLock.lock();
        while(true)
        {
            try
            {
                Thread.sleep((long) (Math.random()*2000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for(int i=0;i<producers.size();i++) {
                if (!producers.get(i).signal()) {
                }
            }
            try
            {
                while(temp==null || humidity==null || rainfall==null) {
                    myCondition.await();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            dostuff();
            for(int i=0;i<producers.size();i++)
            {
                if(!producers.get(i).signal()){
                    /* try {
                        myCondition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                }//wait};
            }
        }
    }
}
