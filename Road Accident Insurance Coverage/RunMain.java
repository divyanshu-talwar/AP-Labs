// @author Divyanshu Talwar 2015028
// @author Mridul Gupta 2015061


import java.util.ArrayList;
import java.util.List;

public class RunMain {
	public static void main(String[] args) {
		List<Vehicle> object = new ArrayList<Vehicle>();
		
//		Cycles
		Firefox ff = new Firefox("Syesha");
		Lamborghini lb = new Lamborghini("DT");
		
//		Scooters
		Bajaj bj= new Bajaj("Rishi");
		Hero hero = new Hero("Bhidu");
		Harley hd = new Harley("Mridul");
		
//		4-wheelers
		Maruti mr = new Maruti("Rahul");
		Audi ad = new Audi("Talwar");
		object.add(ff);object.add(lb);object.add(bj);object.add(hero);object.add(hd);object.add(mr);object.add(ad);
		System.out.println("\t\tDetails of Vehicles");
		System.out.println("Model\t\t Owner name\t Number of wheels\t Policy class\t  Policy Expiry");

		for (int i=0;i<object.size(); i++)
		{
			Vehicle x = object.get(i);
			x.display();
			System.out.println("\n");
		}
		System.out.println("==============================================");
		System.out.println("\tCollision loop start message");
		System.out.println("==============================================");
//		int ct=1;
		for(int i=0;i<object.size();i++){
			for(int j=0;j<object.size();j++){
				
				if(i!=j){
//					System.out.println("Collision #"+ct++);
					Vehicle x=object.get(i), y=object.get(j);
//					System.out.println(x.getModel()+" "+y.getModel()+" "+ct);
					x.collision(x, y);
				}
			}
		}
		
		
		
	}

}

class NoPolicyException extends Exception{
	NoPolicyException(String s){
		super(s);
	}
}
class ExpiredPolicyException extends Exception{
	ExpiredPolicyException(String s){
		super(s);
	}
}

