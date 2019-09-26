package org.lib;

import java.util.Scanner;

import com.paremus.brain.iot.example.orchs.impl.ComponentImpl;

public class Printer {

	public synchronized static void print(String txt, Integer var0, Integer var1){
		//System.out.println(txt +"  ["+var0+ "]  ["+var1+"]");
	}	
	public synchronized static void print(String state, Integer txt){
	//	System.out.println(state);
	}

	public synchronized static void printLoad(String state, Integer txt){
		
		System.err.println("+++++++ "+ state + txt+ " ++++++++");
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
		if(ComponentImpl.positionRX!=null){
			
			X = Integer.valueOf(ComponentImpl.positionRX.X);
		}
		
		else{
			X=-1;
		}
		return X;
	}
	public synchronized static Integer getY( ){
		if(ComponentImpl.positionRY!=null){
				Y = Integer.valueOf(ComponentImpl.positionRY.Y);
			
		}else{
			Y=-1;
		}
		return Y;
	}
	public  static void openDoor (){
		System.out.println(" ORCHESTRATOR ORDERS THE ROBOT TO OPEN THE DOOR ");
		System.out.println();
		ComponentImpl.openDoor();
	}
	
	
	public static void moveToUnload(){
		System.out.println(" ORCHESTRATOR ORDERS THE ROBOT TO MOVE TO THE UNLOAD STATION ");
		System.out.println();
		ComponentImpl.movetounload();
	}
	
	
	public static void	moveToDock(){
		System.out.println(" ORCHESTRATOR ORDERS THE ROBOT TO MOVE TO THE DOCKING STATION !");
		System.out.println();
		ComponentImpl.moveToDock();
	}
	public static void	send(String txt, Integer val){
		//System.out.println(" ------ send");
		if(txt.compareTo("X")==0)  {
			ComponentImpl.positionX(String.valueOf(val));
		}
		if(txt.compareTo("Y")==0)  {
			ComponentImpl.positionY(String.valueOf(val));	
		}		
	}
	public static void	moveToStorage(){
		System.out.println("ORCHESTRATOR ORDERS TO MOVE TO STORAGE STATION ");
		System.out.println();
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
