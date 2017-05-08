//@author - Divyanshu Talwar 2015028
//@author - Mridul Gupta 2015061

import java.util.NoSuchElementException;
import java.util.Scanner;

public class RunMain {
	static Operations OP=new Operations();
	static FileOP f = new FileOP();
	
	public static void initial(){
		int index;
		while(!OP.getLogin()){
			Scanner ink = new Scanner(System.in);
			Scanner in = new Scanner(System.in);
			System.out.println("Enter a choice:");
			System.out.println("1. Sign Up\n2. Login");
			int choice=Integer.valueOf(ink.nextLine());
			if(choice==1){
				OP.signUp();
				f.write();
			}
			else if(choice==2){
				index=OP.Login();
//				System.out.println("Index is "+index);
				f.write();
				onceLoggedIn(index);

				}
			}
		
	}
	public static void onceLoggedIn(int index){
		int choice;
		Scanner in = new Scanner(System.in);
		if(OP.getLogin()){
			choice=OP.loginMenu(index);
			switch(choice){
			case 1: OP.listFriends(index);
					f.write();
					onceLoggedIn(index);
			case 2: String src=in.nextLine();
					OP.search(index,src);
					f.write();
					onceLoggedIn(index);
			case 3:OP.updateStatus(index);
					f.write();
					onceLoggedIn(index);
			case 4:OP.listPending(index);
					f.write();
					onceLoggedIn(index);
			case 5:OP.unset();
					f.write();
					initial();
			default:
			}
		}
	}

	public static void main(String[] args) throws NoSuchElementException{
		// TODO Auto-generated method stub
		System.out.println("Reading database file...\nNetwork is ready.");
		f.read();
		f.write();
//		Scanner i = new Scanner(System.in);
		initial();
		}
	}
