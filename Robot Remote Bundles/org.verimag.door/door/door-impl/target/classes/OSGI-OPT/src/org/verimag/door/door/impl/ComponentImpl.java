package org.verimag.door.door.impl;

import org.apache.felix.service.command.CommandProcessor;
import org.osgi.service.component.annotations.Component;

import compound.sim07;
@Component(
		property = {
				CommandProcessor.COMMAND_SCOPE + "=DOOR", //
				CommandProcessor.COMMAND_FUNCTION + "=door" //
		},service=ComponentImpl.class
	)

public class ComponentImpl {
    
   public void door() {
		new sim07();
   }
    
}
