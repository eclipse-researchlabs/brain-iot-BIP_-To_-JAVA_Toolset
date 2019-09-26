package com.paremus.brain.iot.example.door.impl;

import org.apache.felix.service.command.CommandProcessor;
import org.osgi.service.component.annotations.Component;

import com.paremus.brain.iot.example.orch.api.DoorRequest;

import compound.sim07;
import eu.brain.iot.eventing.annotation.SmartBehaviourDefinition;
import eu.brain.iot.eventing.api.BrainIoTEvent;
import eu.brain.iot.eventing.api.SmartBehaviour;

@Component(
		property = {
				CommandProcessor.COMMAND_SCOPE + "=DOOR", //
				CommandProcessor.COMMAND_FUNCTION + "=door" //
		},service= {SmartBehaviour.class, ComponentImpl.class}
	)

@SmartBehaviourDefinition(consumed = DoorRequest.class)

public class ComponentImpl implements SmartBehaviour<BrainIoTEvent>{

	
	
	public static DoorRequest command ;
	
	public void notify(BrainIoTEvent event) {
		if (event instanceof DoorRequest) {
			 command = (DoorRequest) event;
			
		}else {
			System.out.println("Argh! Received an unknown event type " + event.getClass());
		}
		
	}
	
    
	   public void door() {
			new sim07();
	   }
    
}
