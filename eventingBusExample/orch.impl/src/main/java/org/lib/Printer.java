package org.lib;

import java.util.Scanner;

import com.paremus.brain.iot.example.orch.impl.ComponentImpl;

public class Printer {

	public synchronized static void print(String txt, Integer var0, Integer var1){
		System.out.println(txt +"  ["+var0+ "]  ["+var1+"]");
	}	
	public synchronized static void print(String state, Integer txt){
		System.out.println("-- ("+ txt+") : "+state);
	}

	public synchronized static void printLoad(String state, Integer txt){
		System.err.println("++++++++++++++++++++++++++++++++++");
		System.err.println("+++++++ "+ state + txt+ " ++++++++");
		System.err.println("++++++++++++++++++++++++++++++++++");
	}
	
	public synchronized static void print(Integer state){
		System.out.println(state);
	}
	
	public synchronized static Integer add(Integer a ,Integer b){

		return new Integer(a.intValue() + b.intValue());
	}

	public synchronized static Integer mult(Integer a ,Integer b){

		return new Integer(a.intValue() * b.intValue());
	}
	public synchronized static Integer readA( ){
		//Scanner sc =new Scanner(System.in);
		return new Integer(3);
	}
	public synchronized static Integer getX( ){
		if(ComponentImpl.positionXY!=null){if(ComponentImpl.positionXY.txt!=null){
			if(ComponentImpl.positionXY.txt.compareTo("X")==0) {
				X = ComponentImpl.positionXY.val;
			}

		}}
		
		else{
			X=-1;
		}
		return X;
	}
	public synchronized static Integer getY( ){
		if(ComponentImpl.positionXY!=null){if(ComponentImpl.positionXY.txt!=null){
			if(ComponentImpl.positionXY.txt.compareTo("Y")==0) {
				Y = ComponentImpl.positionXY.val;
			}

		}}else{
			Y=-1;
		}
		return Y;
	}
	public  static void openDoor (){
		System.out.println(" ------ Open it");
		ComponentImpl.openDoor();
	}
	
	
	public static void moveToUnload(){
		System.out.println(" ------ moveToUnload");
		ComponentImpl.movetounload();
	}
	
	
	public static void	moveToDock(){
		System.out.println(" ------ moveToDock");
		ComponentImpl.moveToDock();
	}
	public static void	send(String txt, Integer val){
		System.out.println(" ------ send");
		if(txt.compareTo("X")==0)  {
			ComponentImpl.positionX(String.valueOf(val));
		}
		if(txt.compareTo("Y")==0)  {
			ComponentImpl.positionY(String.valueOf(val));	
		}		
	}
	public static void	moveToStorage(){
		System.out.println(" ------ moveToStorage");
		ComponentImpl.moveToStorage();
	}
	public static Integer getRobotReqToOpenDoor(){ // input coming from outside world
		if(ComponentImpl.robotDoorRequest!=null){
			if(ComponentImpl.robotDoorRequest.RobotReqToOpenDoor!=null){
				RobotReqToOpenDoor = Integer.parseInt((String)ComponentImpl.robotDoorRequest.RobotReqToOpenDoor);
				ComponentImpl.robotDoorRequest=null;
			}
		}else{
			RobotReqToOpenDoor=-1;
		}
		return RobotReqToOpenDoor;
	}	
	
    static Integer RobotReqToOpenDoor =new Integer(-1);
    static Integer X =new Integer(-1);
    static Integer Y =new Integer(-1);
    
	public static void listening(){
		//RestDriver.startDriver();
		System.out.println("START LISTENING");

		
	}
	
	
	
	
}
