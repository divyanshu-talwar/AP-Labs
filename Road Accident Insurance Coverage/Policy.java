// @author Divyanshu Talwar 2015028
// @author Mridul Gupta 2015061


import java.text.SimpleDateFormat;
import java.util.Date;

public class Policy {
//	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	protected String type;
	protected int self;
	protected int opc;
	protected boolean valid;
//	private Date cur=new Date();
	Policy(){
		type=new String();
	}
	public boolean checkValid(){
		return this.valid;
	}
	public void setPolicy(int self,int other){
		this.self=self;
		this.opc=other;
	}
	public void setValid(boolean val){
		this.valid=val;
	}
	public void polDisplay(){
		System.out.print("\t\t"+ this.type);
		if(this.checkValid())
			System.out.print("\t\t Valid");
		else System.out.print("\t\t Expired");
	}
	public String getType (){
		return this.type;
	}
	
}

class ThirdPartyPolicy extends Policy{
	ThirdPartyPolicy (){
		this.type="Third Party\t";
		this.setPolicy(0,80);
	}
}

class PackagePolicy extends Policy{
	PackagePolicy(){
		this.type="Package\t\t";
		this.setPolicy(50,80);
	}
}