// @author Divyanshu Talwar 2015028
// @author Aditya Adhikary 2015007
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Producer3 implements Producer,Runnable {
    private float[][] rainfall = new float[5][100];
    private ArrayList<Consumer> consumers= new ArrayList<>();

    private float minRainfall=-1000;
    private float maxRainfall=-1000;
    private float avRainfall=-1000;
    private float pRainfall=-1000;
    
    ReentrantLock myLock = new ReentrantLock();
    Condition myCondition = myLock.newCondition();
    private ReentrantLock myLockNotify = new ReentrantLock();
    private Condition myConditionNotify = myLockNotify.newCondition();

    public Producer3()
    {
        readData();
    }

    @Override
    public void readData() {
        try{
            FileInputStream file=new FileInputStream("rainfall");
            BufferedReader fileReader =new BufferedReader(new InputStreamReader(file));
            String t;
            int i=0,j=0;
            while ((t = fileReader.readLine())!=null){
                if(t.equals(""))
                {
                    i++;
                    j=0;
                    continue;
                }
                rainfall[i][j++]=Float.parseFloat(t);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addConsumer(Consumer p) {
        consumers.add(p);
        p.addProducer(this);
    }

    @Override
    public void printResult() {
        System.out.println("Rainfall: \n max = "+maxRainfall+"\n min="+minRainfall+" \n average="+avRainfall + "\n prediction 101 day =" + pRainfall);
        minRainfall=-1000;
        maxRainfall=-1000;
        avRainfall=-1000;
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
    public void receiveMinData(float _minTemp, float _minHumidity, float _minRainfall) {
        minRainfall=_minRainfall;
    }

    @Override
    public void receiveMaxData(float _maxTemp, float _maxHumidity, float _maxRainfall) {
        maxRainfall=_maxRainfall;
    }

    @Override
    public void receiveAvData(float _avTemp, float _avHumidity, float _avRainfall) {
        avRainfall=_avRainfall;
    }
    
	@Override
	public void receivePred(float _pTemp, float _pHumidity, float _pRainfall) {
		pRainfall = _pRainfall;
		
	}

    @Override
    public void run() {
        myLock.lock();
        myLockNotify.lock();
        for(int t=0;t<5;t++)
        {
            for(int i=0;i<consumers.size();i++ )
            {
                consumers.get(i).receiveRainfallData(rainfall[t]);
            }
            for(int i=0;i<consumers.size();i++ )
            {
                if(!consumers.get(i).signal()){
                   /* try {
                        myCondition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                }
            }
            try
            {
                Thread.sleep((long) (Math.random()*5000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for(int i=0;i<consumers.size();i++ )
            {
                if(!consumers.get(i).signal()){}
            }
            try
            {
                while(minRainfall==-1000 || maxRainfall==-1000 || avRainfall==-1000) {
                    myCondition.await();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(!isHumidityPrinted) {
                try {
                    myConditionNotify.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                isHumidityPrinted=false;
            }
            printResult();
            System.out.println("\n");
        }
//        System.out.println("looping p3 run");

    }

    private boolean isHumidityPrinted=false;

    public void signalNotify()
    {
        if( myLockNotify.tryLock()) {
            try {
                myConditionNotify.signalAll();
            } finally {
                myLockNotify.unlock();
            }
        }
        else
        {
            isHumidityPrinted=true;
        }
    }

}
