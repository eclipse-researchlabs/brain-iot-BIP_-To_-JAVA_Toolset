package atom;



import java.util.concurrent.TimeUnit;

import com.paremus.brain.iot.example.predict.api.WaterHightResultRequest;
import com.paremus.brain.iot.example.predict.impl.ComponentImpl;

import task.Task;





// atom definition;

@Task(time=1)
public class WaterHightcomputation extends Atom { 
// data definition;



 

 public void compute(){
 	Thread worker = new Thread(this::updateWaterHight);
 	worker.start();


}
 
 private void updateWaterHight() {
		while(true) {
		try {
				if (ComponentImpl.request!=null) {
					double p01 = ComponentImpl.request.prammeter01;
					double p02 = ComponentImpl.request.prammeter02;
					double p03 = ComponentImpl.request.prammeter03;
					
					ComponentImpl.waterhightvalue =new WaterHightResultRequest();
					ComponentImpl.waterhightvalue.waterhight=PredicModel.run(p01,p02,p03);
					ComponentImpl.request=null;
				}
				TimeUnit.SECONDS.sleep(1);
			


			} catch (InterruptedException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
 

public WaterHightcomputation() {
	compute();
}



}