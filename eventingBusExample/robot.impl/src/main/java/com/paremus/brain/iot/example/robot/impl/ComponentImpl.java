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
		new sim07();
    }
    
    @Reference
    private static EventBus eventBus;
 
    public static  void sendEventOpenDoor() {
    	
    	RobotDoorRequest info = new RobotDoorRequest();
    	info.RobotReqToOpenDoor= "1";
        eventBus.deliver(info);
    }
    public static  void sendPositionXY(String txt, int val) {
    	
    	PositionXY info = new PositionXY();
    	info.txt =txt;
    	info.val=val;
        eventBus.deliver(info);
    }
    
}