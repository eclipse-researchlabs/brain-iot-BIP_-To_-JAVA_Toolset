package atom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PredicModel {

	
	static String FILE_LOCATION ="/home/baouyaa/EclipseLunaOSGI/EMALCSA/Photontest/workspace/eventingBusExample/eventing-example/predict.impl/src/main/java/atom/predict.sh ";
	
	public static double run(double p01, double p02, double p03) {
		
		double value=0.0;
	       

	   // ProcessBuilder b = new ProcessBuilder(System.getProperty("user.dir")+"/bip-full/start.sh", f.getParent() ,kill, time);
	    
	    ProcessBuilder b = new ProcessBuilder("bash", "-c", "source "+FILE_LOCATION+ String.valueOf(p01)+ " " +String.valueOf(p02)+" "+ String.valueOf(p03));
	    
	    
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
	       String line= ""; 
	       String txt= ""; 
	       while((line=reader.readLine())!=null) 
	       { 

	    	   txt=line;
	       }
	       
	       if(txt.length()!=0) {
	    	   //Processing the returned line from the shell script
	    	   value= Double.valueOf(txt);
	    	   return value; 
	       }

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return 0;
	    	
	    	
		   
		
	}

	
	
}
