package compound;


import org.lib.Printer;

import com.paremus.brain.iot.example.train.impl.ComponentImpl;

import java.util.concurrent.TimeUnit;

import org.lib.Map;
import atom.*;
import port.*;
import type.*;

import task.*;

import connector.*;

// Compound Definition;

@Task(time=1)
public class sim07 extends Compound { 

public sim07( ){ 	
 start();
 } 
@CompoundMethodName(name="init")
public void init( ){
	compute();

}


public void compute(){
	Thread worker = new Thread(this::updateTrain);
	worker.start();


}

private void updateTrain() {
		while(true) {
		try {
				if (ComponentImpl.command!=null) {

					TrainModel.run();
					ComponentImpl.command=null;
					ComponentImpl.queryResponse();
				}
				TimeUnit.SECONDS.sleep(1);
			


			} catch (InterruptedException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}


}