//@author - Divyanshu Talwar 2015028
//@author - Mridul Gupta 2015061

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Operations extends Database{
	private boolean login;
	Operations(){
		login=false;
	}
	
	
	public boolean getLogin(){
		return this.login;
	}
	static void noExist(boolean a,String b) throws DoesNotExistException{
		if(!a) throw new DoesNotExistException(b+" does not exist in the database.");
	}
	static void checkFriendList(String a, boolean b) throws NotInListException{
		if(!b) throw new NotInListException(a+" is not in your friend list.");
	}
	static void validate(String user, String user2) throws DuplicateUsernameException{
		
		if(user2.equals(user)) throw new DuplicateUsernameException("The username already exists. Please choose some other name.");
	}
	
	static void validate(int login) throws WrongCredentialsException{
		if(login==-1) throw new WrongCredentialsException("The username or password is incorrect. Please try again.");
	}
	
	static void validate(boolean b) throws NoMutualException{
		if(!b) throw new NoMutualException("No mutual friends.");
	}
	static void validate(boolean a, boolean b) throws NoFriendException{
		if(!a) throw new NoFriendException("You have no friends!");
	}
	static void validate(boolean a, boolean b,boolean c) throws NoPendingRequestException{
		if(!a) throw new NoPendingRequestException("You have no pending requests!");
	}
	
	public int checklogin(String user, String pass){
		for(int t=0;t<this.data.size();t++){
			if(this.data.get(t).getUser().equals(user) && this.data.get(t).getPassword().equals(pass)){
				return t;
			}
		}
		return -1;
	}
	public void signUp(){
//		username duplication
		String user,pass,dispName;
		System.out.println("Enter Username ");
		Scanner i = new Scanner(System.in);
		user=i.nextLine();
		System.out.println("Enter Password ");
		pass=i.nextLine();
		System.out.println("Enter Display Name ");
		dispName=i.nextLine();
		try{
			for(int ctr=0;ctr<data.size();ctr++){
				String user2=data.get(ctr).getUser();
				validate(user,user2);
				
			}
			String a="",b="",c="",h="";
			int d=0,f=0;
			String[] e=new String[0],g=new String[0];
			a=user;
			b=pass;
			c=dispName;
			Person p=new Person(a,b,c,d,e,f,g,h);
			data.add(p);
			System.out.println("Registration is successful! User "+a+" created.");
		}
		catch(Exception e){
//			redirection function
			System.out.println(e);
			this.signUp();
		}
		
//		i.close();
	}
	
	public int Login(){
		Scanner i=new Scanner(System.in);
		String user, pass;
		System.out.println("Enter Username ");
		user=i.nextLine();
		System.out.println("Enter Password ");
		pass=i.nextLine();
		int log=this.checklogin(user, pass);
//		System.out.println("Log is "+log);
//		implement TRY-CATCH
		try{
			validate(log);
			this.login=true;
//			System.out.println(user+" logged in now. "+this.data.get(log).getStatus()+"\n");
		}
		catch (Exception e){
			System.out.println(e);
			log=this.Login();
		}
		return log;
	}
	public int loginMenu(int log){
		Scanner i = new Scanner(System.in);
		Person USER=data.get(log);
		System.out.println(USER.getUser()+" logged in now.\n"+USER.getStatus());
		System.out.println("Enter a choice:");
		System.out.println("1. List Friends\n2. Search\n3. Update status\n4. Pending request\n5. Logout");
		int ch=i.nextInt();
		return ch;
	}
	
	public void listFriends(int log){
		List<String> fr=data.get(log).getFriends();
		boolean a=false;
		try{
			System.out.print("Your friends are: ");
			for(int ctr=0;ctr<fr.size();ctr++){
				System.out.print(fr.get(ctr)+", ");
				a=true;
			}
			validate(a,a);
			System.out.println();
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
	public void search(int log,String src){
		if(src.equals(data.get(log).getUser()))
		{
			System.out.println("You and "+src+" are friends!\n");
			Person FR=data.get(log);
			System.out.println("Display Name: "+FR.getDP());
			System.out.println("Status: "+FR.getStatus());
			System.out.print("Friends: ");
			List<String> fr=FR.getFriends();
			for(int ctr=0;ctr<fr.size()-1;ctr++)
				System.out.print(fr.get(ctr)+", ");
			System.out.print(fr.get(fr.size()-1));
			System.out.println();
			return;
		}
		Scanner i= new Scanner(System.in);
		List<String> ufr=data.get(log).getFriends();
		boolean listflag=false,frflag=false;
		int index=0,mainIndex=0;
		for(int ctr=0;ctr<data.size();ctr++){
			if(data.get(ctr).getUser().equals(src)){
				listflag=true;
				mainIndex=ctr;
				break;
			}
		}
//		System.out.println("what");
		try{
			noExist(listflag,src);
			if (listflag){
				try{
					for(int ctr=0;ctr<ufr.size();ctr++){
						if(ufr.get(ctr).equals(src)){
							frflag=true;
							index=ctr;
							break;
						}
					}
					checkFriendList(src,frflag);
				}
				catch(Exception e){
					System.out.println(e);
				}
				finally{
					if(frflag){
						System.out.println("You and "+src+" are friends!\n");
						Person FR=data.get(mainIndex);
						System.out.println("Display Name: "+FR.getDP());
						System.out.println("Status: "+FR.getStatus());
						System.out.print("Friends: ");
						List<String> fr=FR.getFriends();
						try{
							for(int ctr=0;ctr<fr.size()-1;ctr++)
								System.out.print(fr.get(ctr)+", ");
							System.out.print(fr.get(fr.size()-1));
							System.out.println();
						}
						catch(Exception e){
							System.out.println();
						}
	//					Mutual Friends
						boolean mf=false;
	//					List<String> ufr=USER.getFriends();
						System.out.print("Mutual Friends: ");
						try{
							for(int ctr1=0;ctr1<ufr.size();ctr1++){
								for(int ctr2=0;ctr2<fr.size();ctr2++){
									if(ufr.get(ctr1).equals(fr.get(ctr2))){
										mf=true;
										System.out.print(ufr.get(ctr1)+", ");
									}
								}
							}
							validate(mf);
							System.out.println();
						}
						catch(Exception e){
							System.out.print("No Mutual Friends\n");
						}
					}
					else if(listflag && !frflag){
	//					System.out.println(src+" is not a friend.");
						Person FR=data.get(mainIndex);
						List<String> fr=FR.getFriends();
						List<String> upr=data.get(log).getPendingNames();
	//					Mutual Friends
						boolean mf=false;
	//					List<String> ufr=USER.getFriends();
						System.out.print("Mutual Friends: ");
						try{
							for(int ctr1=0;ctr1<ufr.size();ctr1++){
								for(int ctr2=0;ctr2<fr.size();ctr2++){
									if(ufr.get(ctr1).equals(fr.get(ctr2))){
										mf=true;
										System.out.print(ufr.get(ctr1)+", ");
									}
								}
							}
							validate(mf);
							System.out.println();
						}
						catch(Exception e){
							System.out.print("No Mutual Friends\n");
						}
						makeGraph();
				    	runBFS(log);
						System.out.print("Shortest path: ");
						findPath(log,mainIndex);
						System.out.println();

						int t=0;
						if(upr.contains(src)){
							System.out.println(src+"'s Request is Pending....");
							t=1;
						}
						if(FR.getPendingNames().contains(data.get(log).getUser())){
							System.out.println("Request Pending.");
							System.out.println("1. Cancel Request.");
						}
						else if(t==0){
							System.out.println("1. Send Request.");
						}
						System.out.println("b. Back");
						char ch=i.nextLine().charAt(0);
						if(ch=='1'){
							if(FR.getPendingNames().contains(data.get(log).getUser()))
								this.cancelRequest(log,mainIndex);
							else
								this.sendRequest(log, mainIndex);
						}
						
					}
					
					}
			}
		}
		catch(Exception e){
			System.out.println(e);
	}
	
	
	}
	
	public void updateStatus(int index)
	{
		Scanner i = new Scanner(System.in);
		System.out.println("Enter the new status ...");
		String status = i.nextLine();

		data.get(index).setStatus(status);
		System.out.println("Status Successfully Changed !!!");
	}
	
	public void listPending(int log){
		List<String> fr=data.get(log).getPendingNames();
		boolean a=false;
		try{
			System.out.println("Pending Requests are: ");
			for(int ctr=0;ctr<fr.size();ctr++){
				System.out.println((ctr+1) + ". " + fr.get(ctr));
				a=true;
			}
			validate(a,a,a);
			Scanner i = new Scanner(System.in);
			System.out.println("Enter the request number you wish to process.");
			int choice = i.nextInt();		
			System.out.println("1. Accept \n2. Reject \n");
			int ch = i.nextInt();
			if (ch ==1){
				acceptRequest(log,fr.get(choice-1));
			}
			else{
				rejectRequest(log,fr.get(choice-1));
			}
			System.out.println();
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
	
	public void acceptRequest(int index, String a){
		data.get(index).setNumFriends(data.get(index).getNumFriends()+1);
		data.get(index).setNumPending(data.get(index).getNumPending()-1);
		data.get(index).setRemovePending(a);
		data.get(index).setFriends(a);
		int ctr;
		for (ctr =0;ctr<data.size();ctr++)
		{
			if(data.get(ctr).getUser().equals(a))
			{
				data.get(ctr).setFriends(data.get(index).getUser());
				data.get(ctr).setNumFriends(data.get(ctr).getNumFriends()+1);
			}
			
		}
		System.out.println("You and "+a+" are now friends.");
		
	}
	
	public void sendRequest(int src, int dest){
		Person SRC=data.get(src),DEST=data.get(dest);
		DEST.setPending(SRC.getUser());
		DEST.setNumPending(DEST.getNumPending()+1);
		System.out.println("Request sent.");
	}
	
	public void cancelRequest(int src,int dest){
		Person SRC=data.get(src),DEST=data.get(dest);
		DEST.setRemovePending(SRC.getUser());
		DEST.setNumPending(DEST.getNumPending()-1);
		System.out.println("Request cancelled.");
	}
	
	public void rejectRequest(int index,String a){
		data.get(index).setNumPending(data.get(index).getNumPending()-1);
		data.get(index).setRemovePending(a);
		System.out.println("Request removed.");
		
	}
	
	public void unset(){
		this.login=false;
		System.out.println("You have successfully logged out!!!");
	}
	
	private ArrayList<List<String>> adj;//Adjacency Lists
	private ArrayList<String> user = new ArrayList<String>();//reference vertex
 
    // Constructor
    public void makeGraph()
    {
    	adj = new ArrayList<List<String>>();
    	user = new ArrayList<String>();
    	
    	for (int i=0; i<data.size(); i++){
            user.add(data.get(i).getUser());
            adj.add(data.get(i).getFriends());
//        	System.out.println(data.get(i).getUser());
//            System.out.println(data.get(i).getFriends());
    	}
    }
    
    public void findPath(int v, int w) {

    	if(v==w)
    	{
    		System.out.print(data.get(v).getUser() + " -> ");
    	}
    	else if (data.get(w).pred == "")
    	{
    		System.out.println("No path from "+ data.get(v).getUser() + " to " + data.get(w).getUser());
    	}
    	else
    	{
    		findPath(v,user.indexOf(data.get(w).pred));
    		System.out.print(data.get(w).getUser() + " -> ");
    	}
    }

    private void runBFS(int v) {
    	boolean[] visited = new boolean[data.size()];
    	Queue<Integer> q = new LinkedList<Integer>();
        for(int ctr =0;ctr<q.size();ctr++)
        {
        	visited[ctr]=false;
        }
    	visited[v]= true;
    	data.get(v).pred="";
    	q.add(v);
        while(q.peek() != null) {
		    v=q.poll();
		    visited[v]=true;
            Iterator<String> vi = adj.get(v).listIterator();
            while(vi.hasNext()) {
                String nextVertex = vi.next();
                int y = user.indexOf(nextVertex);
                if (!visited[y])
                {   visited[y]=true;
	                data.get(y).pred = data.get(v).getUser();
	                q.add(y);
                }
            }
        }
    }
}


class DuplicateUsernameException extends Exception{
	DuplicateUsernameException(String s){
		super(s);
	}
}

class WrongCredentialsException extends Exception{
	WrongCredentialsException(String s){
		super(s);
	}
}

class NoMutualException extends Exception{
	NoMutualException(String s){
		super(s);
	}
}

class NoFriendException extends Exception{
	NoFriendException(String s){
		super(s);
	}
}

class NoPendingRequestException extends Exception{
	NoPendingRequestException(String s){
		super(s);
	}
}

class NotInListException extends Exception{
	NotInListException(String s){
		super(s);
	}
}

class DoesNotExistException extends Exception{
	DoesNotExistException(String s){
		super(s);
	}
}