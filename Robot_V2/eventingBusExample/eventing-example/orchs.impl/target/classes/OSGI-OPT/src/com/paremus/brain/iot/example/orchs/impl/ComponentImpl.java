package com.paremus.brain.iot.example.orchs.impl;


import java.util.concurrent.TimeUnit;

import org.apache.felix.service.command.CommandProcessor;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

import com.paremus.brain.iot.example.orch.api.DoorRequest;
import com.paremus.brain.iot.example.orch.api.MovetodockRequest;
import com.paremus.brain.iot.example.orch.api.Movetostorage;
import com.paremus.brain.iot.example.orch.api.MovetounloadRequest;
import com.paremus.brain.iot.example.orch.api.PositionX;
import com.paremus.brain.iot.example.orch.api.PositionY;
import com.paremus.brain.iot.example.robot.api.PositionXY;
import com.paremus.brain.iot.example.robot.api.RobotDoorRequest;

import compound.sim07;
import eu.brain.iot.eventing.annotation.SmartBehaviourDefinition;
import eu.brain.iot.eventing.api.BrainIoTEvent;
import eu.brain.iot.eventing.api.EventBus;
import eu.brain.iot.eventing.api.SmartBehaviour;

@Component(
		property = {
				CommandProcessor.COMMAND_SCOPE + "=Orchestrator", //
				CommandProcessor.COMMAND_FUNCTION + "=orch" //
		},service={SmartBehaviour.class, ComponentImpl.class}
	)

@SmartBehaviourDefinition(consumed = {RobotDoorRequest.class, PositionX.class, PositionY.class})
public class ComponentImpl  implements SmartBehaviour<BrainIoTEvent>{
	public static RobotDoorRequest robotDoorRequest ;
	//public static PositionXY positionXY ;
	
	
	
	
	@Deactivate
	void stop() {
	//	running=false;
	}
    public void orch() {

    	Thread worker = new Thread(this::updateBulb);
    	worker.start();
    	
    	new sim07();


    }
   // private  static EventBus eventBus;
    
    @Reference
    private   EventBus eventBus;
    
    private   static DoorRequest doorRequest; 
    
    private  static MovetodockRequest movetodockRequest;

    private  static MovetounloadRequest movetounloadRequest;
    
    private  static Movetostorage movetostorage;
    
    private  static PositionX positionX;
    
    private  static PositionY positionY;
    
    public  static PositionX positionRX;
    
    public   static PositionY positionRY;
 
    public static void openDoor() {
    	
    	doorRequest = new DoorRequest();
    	doorRequest.doorevent= "1";
    }

    public static void moveToDock() {
    	
    	movetodockRequest = new MovetodockRequest();
    	movetodockRequest.movetodock= "1";
    }

    public static  void movetounload() {
    	
    	movetounloadRequest= new MovetounloadRequest();
    	movetounloadRequest.movetounload= "1";
    }
    public static void moveToStorage() {
    	
    	movetostorage = new Movetostorage();
    	movetostorage.movetostorage= "1";
    }
    
    public static void positionX(String val) {
    	
    	positionX = new PositionX();
    	positionX.X= val;
    }  
    
    public static void positionY(String val) {
    	
    	positionY = new PositionY();
    	positionY.Y= val;
    } 
 
	
    private boolean running = true;
	private void updateBulb() {
		while(running) {
		try {


				if (doorRequest!=null) {
					eventBus.deliver(doorRequest); doorRequest=null;
				}
				if (movetodockRequest!=null) {
					eventBus.deliver(movetodockRequest); movetodockRequest=null;
				}
				if (movetounloadRequest!=null) {
					eventBus.deliver(movetounloadRequest); movetounloadRequest=null;
				}
				if (movetostorage!=null) {
					eventBus.deliver(movetostorage); movetostorage=null;
				}
				if (positionX!=null) {
					eventBus.deliver(positionX); positionX=null;
				}
				if (positionY!=null) {
					eventBus.deliver(positionY); positionY=null;
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
	//	System.out.println("ORCHESTRATOR Received Event: "+event);
		if (event instanceof RobotDoorRequest) {
			robotDoorRequest = (RobotDoorRequest) event;			
		}else {
			if (event instanceof PositionX) {
				positionRX = (PositionX) event;			
			}
			else {
				if (event instanceof PositionY) {
					positionRY = (PositionY) event;			
				}
				else {
					System.out.println("Argh! Received an unknown event type " + event.getClass());
				}
			}
		}
		
	}

    
}
