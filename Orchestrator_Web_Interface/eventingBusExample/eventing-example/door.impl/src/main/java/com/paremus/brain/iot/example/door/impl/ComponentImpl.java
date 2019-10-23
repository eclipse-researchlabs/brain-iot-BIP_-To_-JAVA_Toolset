package com.paremus.brain.iot.example.door.impl;

import org.apache.felix.service.command.CommandProcessor;
import org.osgi.service.component.annotations.Component;

import com.paremus.brain.iot.example.orch.api.DoorClose;
import com.paremus.brain.iot.example.orch.api.DoorOpen;
import eu.brain.iot.eventing.annotation.SmartBehaviourDefinition;
import eu.brain.iot.eventing.api.BrainIoTEvent;
import eu.brain.iot.eventing.api.SmartBehaviour;

@Component(
		property = {
				CommandProcessor.COMMAND_SCOPE + "=DOOR", //
				CommandProcessor.COMMAND_FUNCTION + "=door" //
		},service= {SmartBehaviour.class, ComponentImpl.class}
	)

@SmartBehaviourDefinition(consumed = {DoorOpen.class, DoorClose.class})
public class ComponentImpl implements SmartBehaviour<BrainIoTEvent>{

	public static DoorClose close ;	
	
	public static DoorOpen open ;
	
	private Command cmd =new Command();
	
	public void notify(BrainIoTEvent event) {
		if (event instanceof DoorOpen) {
			 open = (DoorOpen) event;
			synchronized (this) {
				
				cmd.writeOpenDoor();

			}
			
		}else {
			if (event instanceof DoorClose) {
				 close = (DoorClose) event;
				synchronized (this) {

					cmd.writeCloseDoor();
				}
				
			}else {
				System.out.println("Argh! Received an unknown event type " + event.getClass());
			}
		}
		
	}
	

    
}
