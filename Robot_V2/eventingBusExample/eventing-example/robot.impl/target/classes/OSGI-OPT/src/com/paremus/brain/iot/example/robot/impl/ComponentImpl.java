package com.paremus.brain.iot.example.robot.impl;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;


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

import java.util.concurrent.TimeUnit;

import org.apache.felix.service.command.CommandProcessor;

@Component(
		property = {
				CommandProcessor.COMMAND_SCOPE + "=ROBOT", //
				CommandProcessor.COMMAND_FUNCTION + "=robot" //
		},service={SmartBehaviour.class, ComponentImpl.class}
	)

@SmartBehaviourDefinition(consumed = {PositionX.class, PositionY.class, MovetodockRequest.class, Movetostorage.class, MovetounloadRequest.class  })
public class ComponentImpl implements SmartBehaviour<BrainIoTEvent>{
    
	public static PositionX _X ;
	public static PositionY _Y ;
	
	public static PositionX _RX ;
	public static PositionY _RY ;
	
	public static MovetodockRequest movetodockRequest ;
	public static Movetostorage movetostorage ;
	public static MovetounloadRequest movetounloadRequest ;
	
	
	public void notify(BrainIoTEvent event) {
		if (event instanceof PositionX) {
			 _X = (PositionX) event;			
		}else {
			if (event instanceof PositionY) {
				 _Y = (PositionY) event;			
			}
			else {
				if (event instanceof MovetodockRequest) { 
					movetodockRequest = (MovetodockRequest) event;			
				}else {
					if (event instanceof Movetostorage) {
						movetostorage = (Movetostorage) event;			
					}else {
						if (event instanceof MovetounloadRequest) {
							movetounloadRequest = (MovetounloadRequest) event;			
						}		else {
							System.out.println("Argh! Received an unknown event type " + event.getClass());
						}
					}
				}
			}
		}
		
	
		
	}
    public void robot() {

		
    	Thread worker = new Thread(this::updateBulb);
    	worker.start();
		new sim07();
    	
    }
    
    @Reference
    private   EventBus eventBus;

	static RobotDoorRequest robotDoorRequest ;
	static     	PositionXY positionXY ;
    public  static void sendEventOpenDoor() {   
    	robotDoorRequest = new RobotDoorRequest();
    	robotDoorRequest.RobotReqToOpenDoor= "1";
    }
    public static   void sendPositionXY(String txt, String val) {
    
    	if(txt.compareTo("X")==0) {
        	_RX = new PositionX();
        	_RX.X =val;
    	}else {
        	_RY = new PositionY();
        	_RY.Y =val;
    	}



    }
    
    private  boolean running = true;
	private void updateBulb() {
		while(running) {
		//System.out.println("running");
			try {
					if (robotDoorRequest!=null) {
					eventBus.deliver(robotDoorRequest); 
					robotDoorRequest=null;
				}
				if (_RX!=null) {
			    	//System.out.println("send positionX:" + _RX.X);
					eventBus.deliver(_RX);
					_RX=null;
				}	
				
				if (_RY!=null) {
			    //	System.out.println("send positionY:" + _RY.Y);
					eventBus.deliver(_RY);
					_RY=null;
				}				
				TimeUnit.SECONDS.sleep(1);
		


			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		
		
		}

	}
    
}