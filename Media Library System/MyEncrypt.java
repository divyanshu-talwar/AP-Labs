// @author Divyanshu Talwar 2015028
// @author Mridul Gupta 2015061

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;




public class MyEncrypt extends FilterOutputStream{

	protected MyEncrypt(OutputStream out) {
		super(out);
		// TODO Auto-generated constructor stub
	}
	public void new_write(String a){
		
		try {
			for (int i=0; i<a.length(); i++){
				char c = a.charAt(i);
				if(c >= 'a' && c <='z')
		            c = (char) ('z' - c + 'a');

		        if(c >= 'A' && c <='Z')
		            c = (char) ('Z' - c + 'A');
		        
		        if(c >= '0' && c <='9')
		            c = (char) ('9' - c + '0');
		        out.write(c);	          
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
}	
