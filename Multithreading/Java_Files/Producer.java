// @author Divyanshu Talwar 2015028
// @author Aditya Adhikary 2015007
public interface Producer
{
    public void readData();
    public void addConsumer(Consumer p);
    public void printResult();
    public boolean signal();
    public void receiveMinData(float _minTemp,float _minHumidity,float _minRainfall);
    public void receiveMaxData(float _maxTemp,float _maxHumidity,float _maxRainfall);
    public void receiveAvData(float _avTemp,float _avHumidity,float _avRainfall);
    public void receivePred(float _pTemp, float _pHumidity, float _pRainfall);

}
