package com.paremus.brain.iot.example.main.impl;


import java.util.concurrent.TimeUnit;

import org.apache.felix.service.command.CommandProcessor;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

import com.paremus.brain.iot.example.main.api.TrainRequest;
import com.paremus.brain.iot.example.main.api.WaterHightRequest;
import com.paremus.brain.iot.example.predict.api.WaterHightResultRequest;
import com.paremus.brain.iot.example.train.api.TrainQueryResponse;

import compound.sim07;
import eu.brain.iot.eventing.annotation.SmartBehaviourDefinition;
import eu.brain.iot.eventing.api.BrainIoTEvent;
import eu.brain.iot.eventing.api.EventBus;
import eu.brain.iot.eventing.api.SmartBehaviour;


@Component(
		property = {
				CommandProcessor.COMMAND_SCOPE + "=emalcsawaterdam", //
				CommandProcessor.COMMAND_FUNCTION + "=main" //
		},service={SmartBehaviour.class, ComponentImpl.class}
	)

@SmartBehaviourDefinition(consumed = {WaterHightResultRequest.class, TrainQueryResponse.class})
public class ComponentImpl  implements SmartBehaviour<BrainIoTEvent>{
	
	public static WaterHightResultRequest waterhightvalue ;

	public static TrainQueryResponse trainqueryresponse;
	
	@Deactivate
	void stop() {
		running=false;
	}
	
	static sim07 sim=null;
    public void main() {
		   System.out.println(" MAIN BUNDLE LOADED");
    	Thread worker = new Thread(this::updateWaterHight);
    	worker.start();
    	if (sim==null) {sim = new sim07();}
    	else {
    		sim.driver.startPredictionSystem();
    	}


    }
   // private  static EventBus eventBus;
    
    @Reference
    private   EventBus eventBus;

    private   static WaterHightRequest whRequest; 
 
    private static TrainRequest command ;
    
    public static void waterhightrequest(double prammeter01, double prammeter02, double prammeter03) {
    	
    	whRequest = new WaterHightRequest();
    	whRequest.prammeter01= prammeter01;
    	whRequest.prammeter02= prammeter02;
    	whRequest.prammeter02= prammeter02;
    }

    public static void trainModel() {
    	
    	command = new TrainRequest();

    }
 
	
    private boolean running = true;
    
    
	private void updateWaterHight() {
		while(running) {
		try {
				if (whRequest!=null) {
					
					eventBus.deliver(whRequest); 
					
					whRequest=null;
				}
				
				if (command!=null) {
					
					eventBus.deliver(command); 
					
					command=null;
				}			
				TimeUnit.SECONDS.sleep(1);
			


			} catch (InterruptedException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	@Override
	public void notify(BrainIoTEvent event) {
		if (event instanceof WaterHightResultRequest) {
			waterhightvalue = (WaterHightResultRequest) event;	
			
		}else {
			if (event instanceof TrainQueryResponse) {
				trainqueryresponse = (TrainQueryResponse) event;	
				
				
			}else {
					System.out.println("Argh! Received an unknown event type " + event.getClass());
			}

		}
		
	}

    
}
