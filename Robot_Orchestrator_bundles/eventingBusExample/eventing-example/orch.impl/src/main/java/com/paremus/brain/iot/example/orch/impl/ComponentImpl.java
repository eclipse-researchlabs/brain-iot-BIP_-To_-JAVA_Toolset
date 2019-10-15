package com.paremus.brain.iot.example.orch.impl;

import org.apache.felix.service.command.CommandProcessor;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.paremus.brain.iot.example.orch.api.Cancel;
import com.paremus.brain.iot.example.orch.api.CheckMarker;
import com.paremus.brain.iot.example.orch.api.DoorClose;
import com.paremus.brain.iot.example.orch.api.DoorOpen;
import com.paremus.brain.iot.example.orch.api.PickCart;
import com.paremus.brain.iot.example.orch.api.PlaceCART;
import com.paremus.brain.iot.example.orch.api.QueryState;
import com.paremus.brain.iot.example.orch.api.writeGOTO;
import com.paremus.brain.iot.example.robot.api.CheckValueReturn;
import com.paremus.brain.iot.example.robot.api.QueryStateValueReturn;

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

@SmartBehaviourDefinition(consumed = {CheckValueReturn.class, QueryStateValueReturn.class })

public class ComponentImpl  implements SmartBehaviour<BrainIoTEvent>{

	
    public void  orch() {
    	new sim07();
    }
    
    @Reference
    private static EventBus eventBus;
 
    
    public static CheckValueReturn checkreturnedvalue;
    
    public static QueryStateValueReturn queryreturnedvalue;
    
    
    public static void  writeOpenDoor(){
    	eventBus.deliver(new DoorOpen());
	}
	public static void  writeCloseDoor(){
    	eventBus.deliver(new DoorClose());		 
	}
	public static void  writegoto(int RobotId, int mission){
		writeGOTO wgoto =new com.paremus.brain.iot.example.orch.api.writeGOTO();
		wgoto.mission=mission;
    	eventBus.deliver(wgoto);		 
	}
    
	public static void placeCART(int RobotId, int cart) {
		PlaceCART pc =new PlaceCART();
		pc.cart=cart;
    	eventBus.deliver(pc);	
	}
	
	public static int cancel(int RobotId, int mission){
		Cancel cl =new Cancel();
		
		cl.mission =mission;
    	eventBus.deliver(cl);
		return mission;		
	}
	public static int queryState(int RobotId, int mission){
		QueryState qs =new QueryState();
		
		 qs.mission =mission;
	    	eventBus.deliver(qs);
		 return 0;
		
	}
	
	public static void pickCart(int RobotId, int cart){
		PickCart pc =new PickCart();
		pc.cart=cart;
    	eventBus.deliver(pc);
		
	}
	public static void checkMarkers() {
		CheckMarker cm =new CheckMarker();
    	eventBus.deliver(cm);
	}
	
	@Override
	public void notify(BrainIoTEvent event) {

		if (event instanceof CheckValueReturn) {
			checkreturnedvalue = (CheckValueReturn) event;

			
		}else {
			
			if (event instanceof QueryStateValueReturn) {
				queryreturnedvalue = (QueryStateValueReturn) event;

				
			}else {
				
			}
		}
	}
    
}
