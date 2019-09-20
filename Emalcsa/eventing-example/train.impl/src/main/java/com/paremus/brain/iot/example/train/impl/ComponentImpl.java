package com.paremus.brain.iot.example.train.impl;

import java.util.concurrent.TimeUnit;

import org.apache.felix.service.command.CommandProcessor;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.paremus.brain.iot.example.main.api.TrainRequest;
import com.paremus.brain.iot.example.train.api.TrainQueryResponse;

import compound.sim07;
import eu.brain.iot.eventing.annotation.SmartBehaviourDefinition;
import eu.brain.iot.eventing.api.BrainIoTEvent;
import eu.brain.iot.eventing.api.EventBus;
import eu.brain.iot.eventing.api.SmartBehaviour;

@Component(
		property = {
				CommandProcessor.COMMAND_SCOPE + "=Training", //
				CommandProcessor.COMMAND_FUNCTION + "=train" //
		},service= {SmartBehaviour.class, ComponentImpl.class}
	)

@SmartBehaviourDefinition(consumed = TrainRequest.class)

public class ComponentImpl implements SmartBehaviour<BrainIoTEvent>{

	
	
	public static TrainRequest command ;
	
    @Reference
    private   EventBus eventBus;
	private static TrainQueryResponse queryresponse;
	
	
	public void notify(BrainIoTEvent event) {
		if (event instanceof TrainRequest) {
			 command = (TrainRequest) event;
			
		}else {
			System.out.println("Argh! Received an unknown event type " + event.getClass());
		}
		
	}
    private boolean running = true;
    
    public static void queryResponse() {
    	queryresponse = new TrainQueryResponse();
    }
	static sim07 sim=null;
	private void updateWaterHight() {
		while(running) {
		try {

				
				if (queryresponse!=null) {
					
					eventBus.deliver(queryresponse); 
					
					queryresponse=null;
				}			
				TimeUnit.SECONDS.sleep(1);
			


			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}

	}
    
	   public void train() {
		   
		   System.out.println(" TRAINING BUNDLE LOADED");
	    	Thread worker = new Thread(this::updateWaterHight);
	    	worker.start();
	    	if (sim==null) {sim = new sim07();}
	    	

	   }
    
}
