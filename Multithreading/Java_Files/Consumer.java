// @author Divyanshu Talwar 2015028
// @author Aditya Adhikary 2015007
import java.util.ArrayList;

public interface Consumer
{
    public void receiveTempData(float[] temp);
    public void receiveHumidityData(float[] humidity);
    public void receiveRainfallData(float[] rainfall);
    public boolean signal();
    public void dostuff();
    public void addProducer(Producer p);
}
