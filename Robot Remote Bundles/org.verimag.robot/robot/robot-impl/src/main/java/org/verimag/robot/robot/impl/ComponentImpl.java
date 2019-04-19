package org.verimag.robot.robot.impl;

import org.osgi.service.component.annotations.Component;

import compound.sim07;

import org.apache.felix.service.command.CommandProcessor;
@Component(
		property = {
				CommandProcessor.COMMAND_SCOPE + "=ROBOT", //
				CommandProcessor.COMMAND_FUNCTION + "=robot" //
		},service=ComponentImpl.class
	)
public class ComponentImpl {
    
    public void robot() {
		new sim07();
    }
    
}
