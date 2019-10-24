package com.paremus.brain.iot.example.consumer.impl;

import org.osgi.service.component.annotations.Component;


import com.paremus.brain.iot.example.producer.api.InfoDTO;

import eu.brain.iot.eventing.annotation.SmartBehaviourDefinition;
import eu.brain.iot.eventing.api.BrainIoTEvent;
import eu.brain.iot.eventing.api.SmartBehaviour;

@Component(service= {SmartBehaviour.class, ComponentImpl.class})
@SmartBehaviourDefinition(consumed = InfoDTO.class)
public class ComponentImpl implements SmartBehaviour<BrainIoTEvent>{

	
	public void notify(BrainIoTEvent event) {
		if (event instanceof InfoDTO) {
			InfoDTO command = (InfoDTO) event;
			synchronized (this) {

				System.out.println("Message Received :"+ command.mess);
			}
			
		}else {
			System.out.println("Argh! Received an unknown event type " + event.getClass());
		}
		
	}
    

    
}
