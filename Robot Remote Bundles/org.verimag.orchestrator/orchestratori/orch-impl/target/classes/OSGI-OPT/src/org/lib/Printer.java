package org.lib;

import java.util.Scanner;

public class Printer  extends RestDriver{

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
		if(RestDriver.params!=null){if(RestDriver.params.get("X")!=null){
			X = Integer.parseInt((String)RestDriver.params.get("X"));
		}}
		
		else{
			X=-1;
		}
		return X;
	}
	public synchronized static Integer getY( ){
		if(RestDriver.params!=null){if(RestDriver.params.get("Y")!=null){
			Y = Integer.parseInt((String)RestDriver.params.get("Y"));
		}}else{
			Y=-1;
		}
		return Y;
	}
	public  static void openDoor (){
		System.out.println(" ------ Open it");
		RestDriver.getText("http://localhost:8001/robot", "doorevent=1");	
	}
	
	
	public static void moveToUnload(){
		System.out.println(" ------ moveToUnload");
		
		RestDriver.getText("http://localhost:8002/robot", "movetounload=1");	
	}
	
	
	public static void	moveToDock(){
		System.out.println(" ------ moveToDock");
		RestDriver.getText("http://localhost:8002/robot", "movetodock=1");	
	}
	public static void	send(String txt, Integer val){
		System.out.println(" ------ send");
		RestDriver.getText("http://localhost:8000/robot", txt+"="+val);	
	}
	public static void	moveToStorage(){
		System.out.println(" ------ moveToStorage");
		RestDriver.getText("http://localhost:8002/robot", "movetostorage=1");	
	}
	public static Integer getRobotReqToOpenDoor(){ // input coming from outside world
		if(RestDriver.params!=null){
			if(RestDriver.params.get("RobotReqToOpenDoor")!=null){
				RobotReqToOpenDoor = Integer.parseInt((String)RestDriver.params.get("RobotReqToOpenDoor"));
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
		RestDriver.startDriver();
		System.out.println("START LISTENING");

		
	}
	
	
	
	
}
