package com.paremus.brain.iot.example.main.api;

import eu.brain.iot.eventing.api.BrainIoTEvent;

public class WaterHightRequest extends BrainIoTEvent {

	public double prammeter01;
	public double prammeter02;
	public double prammeter03;

	public String toString() {
		return "WaterHightRequest [prammeter01=" + prammeter01 + ", prammeter02=" + prammeter02 + ", prammeter03="
				+ prammeter03 + "]";
	}
	
	
	
	
}
