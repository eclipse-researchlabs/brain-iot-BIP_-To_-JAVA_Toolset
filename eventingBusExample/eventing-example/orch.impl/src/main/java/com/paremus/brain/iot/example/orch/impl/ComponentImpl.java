package com.paremus.brain.iot.example.orch.impl;

import org.apache.felix.service.command.CommandProcessor;
import org.osgi.service.component.annotations.Component;
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

@SmartBehaviourDefinition(consumed = {RobotDoorRequest.class, PositionXY.class})

public class ComponentImpl  implements SmartBehaviour<BrainIoTEvent>{
	public static RobotDoorRequest robotDoorRequest ;
	public static PositionXY positionXY ;
	
    public void orch() {
		new sim07();
    }
    
    @Reference
    private static EventBus eventBus;
 
    public static  void movetounload() {
    	
    	MovetounloadRequest info = new MovetounloadRequest();
    	info.movetounload= "1";
        eventBus.deliver(info);
    }
    
 
    public static void openDoor() {
    	
    	DoorRequest info = new DoorRequest();
    	info.doorevent= "1";
        eventBus.deliver(info);
    }

    public static void moveToDock() {
    	
    	MovetodockRequest info = new MovetodockRequest();
    	info.movetodock= "1";
        eventBus.deliver(info);
    }

    public static void moveToStorage() {
    	
    	Movetostorage info = new Movetostorage();
    	info.movetostorage= "1";
        eventBus.deliver(info);
    }
    public static void positionX(String val) {
    	
    	PositionX info = new PositionX();
    	info.X= val;
        eventBus.deliver(info);
    }  
    public static void positionY(String val) {
    	
    	PositionY info = new PositionY();
    	info.Y= val;
        eventBus.deliver(info);
    } 
    
	@Override
	public void notify(BrainIoTEvent event) {
		if (event instanceof RobotDoorRequest) {
			robotDoorRequest = (RobotDoorRequest) event;			
		}else {
			if (event instanceof PositionXY) {
				positionXY = (PositionXY) event;			
			}
			else {
				System.out.println("Argh! Received an unknown event type " + event.getClass());

			}
		}
		
	}
    
}
