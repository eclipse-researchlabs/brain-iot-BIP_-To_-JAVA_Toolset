package com.paremus.brain.iot.example.predict.api;

import eu.brain.iot.eventing.api.BrainIoTEvent;

public class WaterHightResultRequest extends BrainIoTEvent {

	
	@Override
	public String toString() {
		return "WaterHightResultRequest [waterhight=" + waterhight + "]";
	}

	public double waterhight;
}
