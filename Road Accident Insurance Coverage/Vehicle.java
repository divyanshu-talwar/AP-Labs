// @author Divyanshu Talwar 2015028
// @author Mridul Gupta 2015061


public class Vehicle {
	
	protected int wheels;
	protected String owner;
	protected int damage;
	protected String model;
	protected boolean engine;
	Vehicle(){
		model = new String();
		owner= new String();		
	}
	public void setName(String mod,String own){
		this.model=mod;
		this.owner=own;
	}
	public void setWheel(int wh){
		this.wheels=wh;
	}
	public void setDamage(int dm){
		this.damage=dm;
	}
	public int getDamage(){
		return this.damage;
	}
	public String getModel(){
		return this.model;
		
	}
	public String getOwner(){
		return this.owner;
		
	}
	public int getWheels(){
		return this.wheels;
	}
	static void validate(boolean engine) throws NoPolicyException{
		if(!engine) throw new NoPolicyException("Vehicle has no insurance policy to make claim.");
	}
	public void collision(Vehicle self, Vehicle opp){
		int min=1000,max=10000;
		int sDamage=min + (int)(Math.random() * ((max - min) + 1));
		int oDamage=min + (int)(Math.random() * ((max - min) + 1));
		self.setDamage(sDamage);
		opp.setDamage(oDamage);
		System.out.println("I am Model-"+self.getModel() + " Owner-"+self.getOwner() +", collided with Model-"+opp.getModel() + " Owner-"+opp.getOwner());
		System.out.println("Damages self: "+sDamage);
		System.out.println("Damages oncoming: "+oDamage);
		System.out.println("Settlement Details: ");
		try{
			validate(self.engine);
			double tmp=-2;
			if(self.getModel() == "Hero"){
				tmp=((Hero)self).settlement();
			}
			else if(self.getModel() == "Bajaj"){
				tmp=((Bajaj)self).settlement();
			}
			else if(self.getModel() == "Harley"){
				tmp=((Harley)self).settlement();
			}
			else if(self.getModel() == "Maruti"){
				tmp=((Maruti)self).settlement();
			}
			else if(self.getModel() == "Audi"){
				tmp=((Audi)self).settlement();
			}			
			if(tmp>0.1){
				System.out.println("oncoming vehicle damage status: Amount left to be settled->"+0.2*oDamage);
				System.out.println("self damage status: Amount left to be settled->"+0.5*sDamage);
			}
			else if (tmp==0){
				System.out.println("oncoming vehicle damage status: Amount left to be settled->"+0.2*oDamage);
				System.out.println("self damage status: Amount left to be settled is unchanged, i.e."+sDamage);
			}
			
		
		}
		catch(Exception e){
			System.out.println("Exception Thrown: "+ e);
		}
		
		System.out.println("\n");
		
		
	}
	
	public void display(){
		System.out.print(this.getModel());
		if(this.getModel() == "Lamborghini"){
			System.out.print("\t "+this.getOwner()+"\t");
			}
		else{
		System.out.print("\t\t "+this.getOwner());
		}
		System.out.print("\t\t\t"+this.getWheels()+"\t\t");
		// System.out.println("Model - "+this.getModel());
			if(this.getModel() == "Hero"){
			((Hero)this).p.polDisplay();
			}
			else if(this.getModel() == "Bajaj"){
				((Bajaj)this).p.polDisplay();
			}
			else if(this.getModel() == "Harley"){
				((Harley)this).p.polDisplay();
			}
			else if(this.getModel() == "Maruti"){
				((Maruti)this).p.polDisplay();
			}
			else if(this.getModel() == "Audi"){
				((Audi)this).p.polDisplay();
			}			
			else if (this.engine == false)
			{
				System.out.print("\t\t No Policy \t\t\t --");
			}
	}
}


class EnginePowered extends Vehicle{
	Policy p;
	EnginePowered(){
		this.engine = true;		
	}
}


class TwoWheeler extends EnginePowered{
	TwoWheeler(){
		this.wheels=2;
	}
}

class FourWheeler extends EnginePowered{
	FourWheeler(){
		this.wheels=4;
	}
}

class NoEngine extends Vehicle{
	NoEngine(){
		this.wheels=2;
		this.engine = false;	
	}
}

class Bajaj extends TwoWheeler{
	protected boolean tmp;
	Bajaj(String own){
		this.setName("Bajaj",own);
		this.p = new PackagePolicy();
		this.p.setValid(true);
		this.tmp =true;
	}
//	protected boolean tmp = this.p.checkValid();
	static void validate2(boolean tmp) throws ExpiredPolicyException{
		if(!tmp) throw new ExpiredPolicyException("This policy is expired. You cannot claim.");
	}
	public double settlement(){
		try{
			validate2(tmp);
			if(this.p.getType()=="Package Policy"){
				return 0.5;
			}
			else return 0.0;
		}
		catch(Exception e){
			System.out.println("Exception thrown: "+e);
			return -1;
		}
	}

}

class Harley extends TwoWheeler{
	protected boolean tmp;
	Harley(String own){
		this.setName("Harley", own);
		this.p=new ThirdPartyPolicy();
		this.p.setValid(true);
		this.tmp=true;
	}
//	protected boolean tmp=this.p.checkValid();
	static void validate2(boolean tmp) throws ExpiredPolicyException{
		if(!tmp) throw new ExpiredPolicyException("This policy is expired. You cannot claim.");
	}
	public double settlement(){
		try{
			validate2(tmp);
			if(this.p.getType()=="Package Policy"){
				return 0.5;
			}
			else return 0.0;
		}
		catch(Exception e){
			System.out.println("Exception thrown: "+e);
			return -1;
		}
	}

}

class Hero extends TwoWheeler{
	protected boolean tmp;
	Hero(String own){
		this.setName("Hero", own);
		this.p=new ThirdPartyPolicy();
		this.p.setValid(false);
		this.tmp=false;
	}
//	protected boolean tmp=this.p.checkValid();
	static void validate2(boolean tmp) throws ExpiredPolicyException{
		if(!tmp) throw new ExpiredPolicyException("This policy is expired. You cannot claim.");
	}
	public double settlement(){
		try{
			validate2(tmp);
			if(this.p.getType()=="Package Policy"){
				return 0.5;
			}
			else return 0.0;
		}
		catch(Exception e){
			System.out.println("Exception thrown: "+e);
			return -1;
		}
	}

}

class Maruti extends FourWheeler{
	protected boolean tmp;
	Maruti(String own){
		this.setName("Maruti",own);
		this.p=new PackagePolicy();
		this.p.setValid(false);
		this.tmp=false;
	}
//	protected boolean tmp=this.p.checkValid();
	static void validate2(boolean tmp) throws ExpiredPolicyException{
		if(!tmp) throw new ExpiredPolicyException("This policy is expired. You cannot claim.");
	}
	public double settlement(){
		try{
			validate2(tmp);
			if(this.p.getType()=="Package Policy"){
				return 0.5;
			}
			else return 0.0;
		}
		catch(Exception e){
			System.out.println("Exception thrown: "+e);
			return -1;
		}
	}

}

class Audi extends FourWheeler{
	protected boolean tmp;
	Audi(String own){
		this.setName("Audi", own);
		this.p=new PackagePolicy();
		this.p.setValid(true);
		this.tmp=true;
	}
//	protected boolean tmp=this.p.checkValid();
	static void validate2(boolean tmp) throws ExpiredPolicyException{
		if(!tmp) throw new ExpiredPolicyException("This policy is expired. You cannot claim.");
	}
	public double settlement(){
		try{
			validate2(tmp);
			if(this.p.getType()=="Package Policy"){
				return 0.5;
			}
			else return 0.0;
		}
		catch(Exception e){
			System.out.println("Exception thrown: "+e);
			return -1;
		}
	}

}

class Firefox extends NoEngine{
	Firefox(String own){
		this.setName("Firefox",own);
	}
}

class Lamborghini extends NoEngine{
	Lamborghini(String own){
		this.setName("Lamborghini",own);
	}
}