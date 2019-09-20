package com.paremus.brain.iot.example.predict.impl;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.paremus.brain.iot.example.main.api.WaterHightRequest;
import com.paremus.brain.iot.example.predict.api.WaterHightResultRequest;

import compound.sim07;
import eu.brain.iot.eventing.annotation.SmartBehaviourDefinition;
import eu.brain.iot.eventing.api.BrainIoTEvent;
import eu.brain.iot.eventing.api.EventBus;
import eu.brain.iot.eventing.api.SmartBehaviour;

import java.util.concurrent.TimeUnit;

import org.apache.felix.service.command.CommandProcessor;

@Component(
		property = {
				CommandProcessor.COMMAND_SCOPE + "=predicttion", //
				CommandProcessor.COMMAND_FUNCTION + "=predictor" //
		},service={SmartBehaviour.class, ComponentImpl.class}
	)

@SmartBehaviourDefinition(consumed = {WaterHightRequest.class })
public class ComponentImpl implements SmartBehaviour<BrainIoTEvent>{
    

	public static WaterHightResultRequest waterhightvalue;
	
	static sim07 sim=null;
    public void predictor() {
		System.out.println(" PREDICTION BUNDLE LOADED");
		
    	Thread worker = new Thread(this::updateWaterHight);
    	worker.start();
    	if (sim==null) {sim = new sim07();}
    	
    }
    
    @Reference
    private   EventBus eventBus;

	public static WaterHightRequest request ;

    
    private  boolean running = true;
    
 	private void updateWaterHight() {
 		while(running) {
 		try {
 				if (waterhightvalue!=null) {
 					
 					eventBus.deliver(waterhightvalue); 
 					
 					waterhightvalue=null;
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
 		if (event instanceof WaterHightRequest) {
 			request = (WaterHightRequest) event;	
 			
 			System.out.println(request.toString());
 		}else {
 			
 					System.out.println("Argh! Received an unknown event type " + event.getClass());

 		}
 		
 	}
    
}