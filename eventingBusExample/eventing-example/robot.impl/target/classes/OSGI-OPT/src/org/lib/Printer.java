package org.lib;

import java.util.Scanner;

import com.paremus.brain.iot.example.robot.impl.ComponentImpl;

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
	public  static Integer getX( ){
		if(ComponentImpl._X!=null){if(ComponentImpl._X.X!=null){
			X = Integer.parseInt((String)ComponentImpl._X.X);
			ComponentImpl._X=null;
		}}
		
		else{
			X=-1;
		}
		return X;
	}
	public  static Integer getY( ){
		if(ComponentImpl._Y!=null){if(ComponentImpl._Y.Y!=null){
			Y = Integer.parseInt((String)ComponentImpl._Y.Y);
			ComponentImpl._Y=null;
		}}else{
			Y=-1;
		}
		return Y;
	}
	public  static void openDoor (){
		System.out.println(" ------ Open it");
		
		ComponentImpl.sendEventOpenDoor();
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
		ComponentImpl.sendPositionXY(txt,val);	
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
		if(ComponentImpl.movetounloadRequest!=null){if(ComponentImpl.movetounloadRequest.movetounload!=null){
			movetounload = Integer.parseInt((String)ComponentImpl.movetounloadRequest.movetounload);
			ComponentImpl.movetounloadRequest =null;
		}else{
			movetounload =-1;}	}else{
		
			movetounload =-1;
		}

		return movetounload;
	}

	public static Integer getMovetodock() {
		if(ComponentImpl.movetodockRequest!=null){if(ComponentImpl.movetodockRequest.movetodock!=null){
			movetodock = Integer.parseInt((String)ComponentImpl.movetodockRequest.movetodock);
			ComponentImpl.movetodockRequest =null;
		}else{
			movetodock =-1;}
		}else
		
		{
			movetodock =-1;
		}

		return movetodock;
	}

	public static Integer getMovetostorage() {
		if(ComponentImpl.movetostorage!=null){if(ComponentImpl.movetostorage.movetostorage!=null){
			movetostorage = Integer.parseInt((String)ComponentImpl.movetostorage.movetostorage);
			ComponentImpl.movetostorage=null;
		}else{
			movetostorage=-1;
		}
		}	else{
		
			movetostorage=-1;
		}

		return movetostorage;
	}


	public static void listening(){
		//RestDriver.startDriver();
		System.out.println("START LISTENING");
		
		
	}
	
	
	
	
}
