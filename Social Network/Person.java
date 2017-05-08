//@author - Divyanshu Talwar 2015028
//@author - Mridul Gupta 2015061

import java.util.ArrayList;
import java.util.List;

public class Person {
	private String userName;
	private String password;
	private String displayName;
	private int numFriends;
	private List<String> friends = new ArrayList<String>();
	private int numPending;
	private List<String> pendingNames = new ArrayList<String>();
	private String status;
	public String pred;
	Person(String a, String b, String c, int d, String[] e, int f, String[] g, String h){
		this.userName=a;
		this.password=b;
		this.displayName=c;
		this.numFriends=d;
		for(int i = 0;i<d;i++)
			this.friends.add(e[i]);
		this.numPending=f;
		for(int i = 0;i<f;i++)
			this.pendingNames.add(g[i]);
		this.status=h;
		this.pred = "";
	}
//	GETTERS
	public String getUser(){
		return this.userName;
	}
	public String getPassword(){
		return this.password;
	}
	public String getDP(){
		return this.displayName;
	}
	public int getNumFriends(){
		return this.numFriends;
	}
	public List<String> getFriends(){
		return this.friends;
	}
	public int getNumPending(){
		return this.numPending;
	}
	public List<String> getPendingNames(){
		return this.pendingNames;
	}
	public String getStatus(){
		return this.status;
	}
	
//	SETTERS
	public void setUser(String name){
		this.userName=name;
	}
	public void setPassword(String pass){
		this.password=pass;
	}
	public void setDP(String name){
		this.displayName=name;
	}
	public void setNumFriends(int num){
		this.numFriends=num;
	}
	public void setFriends(String f){
		this.friends.add(f);;
	}
	public void setRemoveFriends(String f){
		this.friends.remove(f);
	}
	public void setFriends(String f[]){
		for(int i = 0;i<this.numFriends;i++)
			this.friends.add(f[i]);;
	}
	public void setNumPending(int pending){
		this.numPending=pending;
	}
	public void setPending(String f){
		this.pendingNames.add(f);;
	}
	public void setRemovePending(String f){
		this.pendingNames.remove(f);
	}
	public void setPendingNames(String[] names){
		for(int i = 0;i<this.numPending;i++)
			this.pendingNames.add(names[i]);
	}
	public void setStatus(String stat){
		this.status=stat;
	}
	
//	DISPLAY
	public void display(){
		System.out.println("Name is "+this.userName+"\n Display Name is "+this.displayName+"\n Number of Friends "+ this.numFriends+"\n Pending Requests number "+ this.numPending);
		
	}

}
