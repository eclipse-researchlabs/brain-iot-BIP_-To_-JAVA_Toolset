package com.paremus.brain.iot.example.producer.impl;


import org.apache.felix.service.command.CommandProcessor;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.paremus.brain.iot.example.producer.api.InfoDTO;

import eu.brain.iot.eventing.api.EventBus;

@Component(
		property = {
				CommandProcessor.COMMAND_SCOPE + "=PRODUCER", //
				CommandProcessor.COMMAND_FUNCTION + "=prod" //
		},service=ComponentImpl.class
	)
public class ComponentImpl {
    
    @Reference
    private EventBus eventBus;
    
 
    public void prod() {
    	
    	InfoDTO info = new InfoDTO();
    	info.mess = 1;
        eventBus.deliver(info);
    }
    
}
