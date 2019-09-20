package atom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TrainModel {

	
	static String FILE_LOCATION ="/home/baouyaa/EclipseLunaOSGI/EMALCSA/Photontest/workspace/eventingBusExample/eventing-example/train.impl/src/main/java/atom/train.sh";
	
	public static void run() {
		

	       

	   // ProcessBuilder b = new ProcessBuilder(System.getProperty("user.dir")+"/bip-full/start.sh", f.getParent() ,kill, time);
	    
	    ProcessBuilder b = new ProcessBuilder("bash", "-c", "source "+FILE_LOCATION);
	    
	    
	    Process p;
		try {
			p = b.start();
		
	    int result = p.waitFor();
	    int len;
	    if ((len = p.getErrorStream().available()) > 0) {
	      byte[] buf = new byte[len];
	      p.getErrorStream().read(buf);
	      System.err.println("Command error:\t\""+new String(buf)+"\"");
	    }
    	   System.out.println("Process exit code: " + result);
    	   System.out.println();
    	   System.out.println("Result:");
    	   BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
	       String line=reader.readLine(); 
	       while((line=reader.readLine())!=null) 
	       { 
	       System.out.println(line); 
	      
	       }

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    	
	    	
		   
		
	}

	
	
}
