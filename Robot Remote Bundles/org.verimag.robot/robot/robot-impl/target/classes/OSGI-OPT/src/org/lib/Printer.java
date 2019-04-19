package org.lib;

import java.util.Scanner;

public class Printer extends RestDriver {

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
	public  static Integer getX( ){
		if(RestDriver.params!=null){if(RestDriver.params.get("X")!=null){
			X = Integer.parseInt((String)RestDriver.params.get("X"));
			RestDriver.params.remove("X");
		}}
		
		else{
			X=-1;
		}
		return X;
	}
	public  static Integer getY( ){
		if(RestDriver.params!=null){if(RestDriver.params.get("Y")!=null){
			Y = Integer.parseInt((String)RestDriver.params.get("Y"));
			RestDriver.params.remove("Y");
		}}else{
			Y=-1;
		}
		return Y;
	}
	public  static void openDoor (){
		System.out.println(" ------ Open it");
		RestDriver.getText("http://localhost:8000/robot", "RobotReqToOpenDoor=1");	
	}
	
	
	public static void moveToUnload(){
		System.out.println(" ------ moveToUnload");
	}
	
	
	public static void	moveToDock(){
		System.out.println(" ------ moveToDock");
	}
	
	public static void	moveToStorage(){
		System.out.println(" ------ moveToStorage");	
	}
	
	public static void	send(String txt, Integer val){
		System.out.println(" ------ send");
		RestDriver.getText("http://localhost:8000/robot", txt+"="+val);	
	}
	

	
	public static Integer getRobotReqToOpenDoor(){ // input coming from outside world
		Integer F = RobotReqToOpenDoor; RobotReqToOpenDoor =-1;
		return F;
	}	
	
    static Integer RobotReqToOpenDoor =new Integer(-1);
    static Integer X =new Integer(-1);
    static Integer Y =new Integer(-1);
 
    static Integer movetounload =new Integer(-1);
    static Integer movetodock =new Integer(-1);
    static Integer movetostorage =new Integer(-1);
    
    
	public static Integer getMovetounload() {
		if(RestDriver.params!=null){if(RestDriver.params.get("movetounload")!=null){
			movetounload = Integer.parseInt((String)RestDriver.params.get("movetounload"));
			 RestDriver.params.remove("movetounload");
		}else{
			movetounload =-1;}	}else{
		
			movetounload =-1;
		}

		return movetounload;
	}

	public static Integer getMovetodock() {
		if(RestDriver.params!=null){if(RestDriver.params.get("movetodock")!=null){
			movetodock = Integer.parseInt((String)RestDriver.params.get("movetodock"));
			RestDriver.params.remove("movetodock");
		}else{
			movetodock =-1;}
		}else
		
		{
			movetodock =-1;
		}

		return movetodock;
	}

	public static Integer getMovetostorage() {
		if(RestDriver.params!=null){if(RestDriver.params.get("movetostorage")!=null){
			movetostorage = Integer.parseInt((String)RestDriver.params.get("movetostorage"));
			RestDriver.params.remove("movetostorage");
		}else{
			movetostorage=-1;
		}
		}	else{
		
			movetostorage=-1;
		}

		return movetostorage;
	}


	public static void listening(){
		RestDriver.startDriver();
		System.out.println("START LISTENING");
		
		
	}
	
	
	
	
}
