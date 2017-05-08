//@author - Divyanshu Talwar 2015028
//@author - Mridul Gupta 2015061

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.StringJoiner;

public class FileOP extends Database {
	FileOP(){
		
	}
	public void read(){
		String file="input.txt";
		try {
			BufferedReader in = new BufferedReader(new FileReader(file));
			String str;
			String[] string;
			while ((str = in.readLine()) != null) {
				string=str.split(",");
				String a,b,c,h;
				int d,f,i,j;
				String[] e,g;
				
				a=string[0];
				b=string[1];
				c=string[2];
				d=Integer.parseInt(string[3]);
				e=new String[d];
				for(i=0,j=3+1;i<d;i++,j++){
					e[i]=string[j];
				}
				f=Integer.parseInt(string[4+d]);
				g=new String[f];
				for(i=0,j=4+d+1;i<f;i++,j++){
					g[i]=string[j];
				}
				h=string[j];
				Person p = new Person(a,b,c,d,e,f,g,h);
				data.add(p);
			}
			in.close();
		}
		catch (IOException e) {
			System.out.println("File Read Error");
		}	
	}
	public void write(){
		PrintWriter writer=null;
		try {
//			writer = new PrintWriter("output.txt");
//			writer.close();
			writer = new PrintWriter("input.txt");
			for(int ctr=0;ctr<this.data.size();ctr++){
				String wr="";
				String a="",b="",c="",h="";
				int d,f,i,j;
				String e="",g="";
				a=this.data.get(ctr).getUser();
				b=this.data.get(ctr).getPassword();
				c=this.data.get(ctr).getDP();
				d=this.data.get(ctr).getNumFriends();
				int ctr2=0;
				try{
					for(ctr2=0;ctr2<d-1;ctr2++){
						e+=(this.data.get(ctr).getFriends().get(ctr2))+",";
					}
					e+=(this.data.get(ctr).getFriends().get(ctr2));
				}
				catch(Exception e1){
					
				}
				f=this.data.get(ctr).getNumPending();
				try{
					for(ctr2=0;ctr2<f-1;ctr2++){
						g+=(this.data.get(ctr).getPendingNames().get(ctr2))+",";
					}
					g+=(this.data.get(ctr).getPendingNames().get(ctr2));
				}
				catch(Exception e2){
					
				}
				h=this.data.get(ctr).getStatus();
				if(c=="")
					c="noData";
				if(e=="")
					e="noData";
				if(g=="")
					g="noData";
				if(h=="")
					h="noData";
				StringJoiner joiner = new StringJoiner(",");
				joiner.add(a).add(b).add(c).add(String.valueOf(d)).add(e).add(String.valueOf(f)).add(g).add(h);
				String x = joiner.toString();
//				System.out.println("yuck      " + x);
				writer.println(x);
				
			}
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
//		writer.println("The first line");
//		writer.println("The second line");
		writer.close();
	}
}
