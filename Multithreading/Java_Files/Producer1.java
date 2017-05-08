// @author Divyanshu Talwar 2015028
// @author Aditya Adhikary 2015007
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Producer1 implements Producer,Runnable
{
    private ArrayList<Consumer> consumers= new ArrayList<>();

    private float[][] temp = new float[5][100];
    private float minTemp=-1000;
    private float maxTemp=-1000;
    private float avTemp=-1000;
    private float pTemp=-1000;

    ReentrantLock myLock = new ReentrantLock();
    Condition myCondition = myLock.newCondition();
    private Producer2 notifyto;

    public Producer1(Producer2 p)
    {
        readData();
        notifyto=p;
    }

    @Override
    public void readData() {
        try{
            FileInputStream file=new FileInputStream("temperature");
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
                temp[i][j++]=Float.parseFloat(t);

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
        System.out.println("Temperature: \n max="+maxTemp+" \n min="+minTemp+" \n average="+avTemp+ "\n prediction 101 day =" + pTemp);
        minTemp=-1000;
        maxTemp=-1000;
        avTemp=-1000;
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
        minTemp=_minTemp;
    }

    @Override
    public void receiveMaxData(float _maxTemp, float _maxHumidity, float _maxRainfall) {
        maxTemp=_maxTemp;
    }

    @Override
    public void receiveAvData(float _avTemp, float _avHumidity, float _avRainfall) {
        avTemp=_avTemp;
    }
    
	public void receivePred(float _pTemp, float _pHumidity, float _pRainfall) {
		pTemp = _pTemp;
		
	}

    @Override
    public void run() {
        myLock.lock();
       // System.out.println("lock taken by p1 run");
        for(int t=0;t<5;t++)
        {
            for(int i=0;i<consumers.size();i++ )
            {
                consumers.get(i).receiveTempData(temp[t]);
            }
            for(int i=0;i<consumers.size();i++ )
            {
                if(!consumers.get(i).signal()){
                   // System.out.println("signalling consumer "+(i+1)+" from p1run failed");
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
                while(minTemp==-1000 || maxTemp==-1000 || avTemp==-1000) {
                   // System.out.println("p1 run in conditional wait");
                    myCondition.await();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            printResult();
            notifyto.signalNotify();
        }
//        System.out.println("looping p1 run");

    }
    

}
