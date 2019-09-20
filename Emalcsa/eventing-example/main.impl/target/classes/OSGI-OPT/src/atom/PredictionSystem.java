package atom;


import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.lib.Map;

import com.paremus.brain.iot.example.main.impl.ComponentImpl;

import task.Task;


// atom definition;

@Task(time=1)
public class PredictionSystem extends Atom { 

	static int mutex =1 ;
	public PredictionSystem( ){
		startPredictionSystem();

	}
public void startPredictionSystem( ){
	
	//new Thread( new Runnable() {
		// public void run() {
						//while(true) {
					
					
							
								System.out.println("EMALCSA WATER FLOW PREDICTION SYSTEM");
								System.out.println("PLEASE SELECT 1) TO TRAIN YOUR NN MODEL 2) TO MAKE PREDICTION");	
							
								
								Scanner sc = new Scanner (System.in);
								int choice = sc.nextInt();
								
								switch(choice) {
								  case 1:
										Thread worker = new Thread(this::Train);
										worker.start();
								    break;
								  case 2:
									 
									  compute();
										
								    break;
							
								}
								
								
							
					//	}
		// }
	
	//}
//	).start();
	
 }



private void Train() {
	ProgressBar bar = new ProgressBar();



	//ConsoleHelper consoleHelper = new ConsoleHelper();
	int i=0;
 mutex=1;
	try {
		System.out.println("TRAINING START ... " );
	ComponentImpl.trainModel();
	
	while(mutex==1) {
	if (ComponentImpl.trainqueryresponse!=null) {

		System.out.println("TRAINING COMPLETED. PLEASE TYPE 0 ");
		ComponentImpl.trainqueryresponse=null;
		mutex=0;
	
		Thread.currentThread().interrupt();
		return;
	}else {
		i=i+1;
			TimeUnit.SECONDS.sleep(1);
			
	}
	
	}

	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		

	
}



public void compute(){
	
	
	Thread worker = new Thread(this::updateWaterHight);
	worker.start();


}

private void updateWaterHight() {
	//ConsoleHelper consoleHelper = new ConsoleHelper();
	 mutex=1;
		ComponentImpl.waterhightrequest(1.53, 10.2, 31.12);	
		
		//while(mutex==0) {
		try { 
		while(mutex==1) {
				if (ComponentImpl.waterhightvalue!=null) {
					System.out.println("WAITING WATER HIGHT VALUE ... ");
					System.out.println("ESTIMATED WATER HIGHT : " + ComponentImpl.waterhightvalue.waterhight +" . PLEASE TYPE 0 ");
					ComponentImpl.waterhightvalue=null;
					
					mutex=0;
				}else {
					i=i+1;
				//	 consoleHelper.animate(i + "");
						TimeUnit.SECONDS.sleep(1);
			
				}
		}
			} catch (InterruptedException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//}

	}

}