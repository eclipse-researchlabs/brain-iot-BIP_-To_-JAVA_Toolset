package org.verimag.orch.orch.impl;

import org.osgi.service.component.annotations.Component;

import org.apache.felix.service.command.CommandProcessor;
import org.osgi.service.component.annotations.Component;

import compound.sim07;
@Component(
		property = {
				CommandProcessor.COMMAND_SCOPE + "=ORCH", //
				CommandProcessor.COMMAND_FUNCTION + "=orch" //
		},service=ComponentImpl.class
	)
public class ComponentImpl {
    
    public void orch() {
		new sim07();
    }
    
}