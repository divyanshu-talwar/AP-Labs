// @author Divyanshu Talwar 2015028
// @author Mridul Gupta 2015061

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MyDecrypt extends FilterInputStream{

	protected MyDecrypt(InputStream is) {
		super(is);
		// TODO Auto-generated constructor stub
	}
	public String new_read(){
		int str;
		StringBuilder sb = new StringBuilder();
		String a = null;
		try {
			while((str=in.read())!= -1){
				char c = (char)str;
				if(c >= 'a' && c <='z')
		            c = (char) ('z' - c + 'a');

		        if(c >= 'A' && c <='Z')
		            c = (char) ('Z' - c + 'A');
		        
		        if(c >= '0' && c <='9')
		            c = (char) ('9' - c + '0');
				sb.append(c);
			}
			a = sb.toString();
			return a;
		} catch (IOException e) {
			e.printStackTrace();
			return a;
		}
	}	
}	