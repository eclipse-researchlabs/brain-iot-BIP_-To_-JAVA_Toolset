package org.lib;



import com.paremus.brain.iot.example.door.impl.ComponentImpl;

public class Printer{


	public  static void openDoor (){
		System.out.println(" OPENNING THE DOOR ACTION ");
		System.out.println(" ");
	}
	

	
	public static Integer getDoorState(){
		if(ComponentImpl.command!=null){
			if(ComponentImpl.command.doorevent!=null){
				DoorState = Integer.parseInt((String)ComponentImpl.command.doorevent);
				ComponentImpl.command=null;
			}else{
				DoorState=-1;
			}
		}
		else{
			DoorState=-1;
		}
		
		return DoorState;
	}
    static Integer DoorState =new Integer(-1);	
    static Integer RobotReqToOpenDoor =new Integer(-1);
    static Integer X =new Integer(-1);
    static Integer Y =new Integer(-1);
    
	public static void listening(){
		//startDriver();
		System.out.println("START LISTENING");

		
	}
	
	
	
	
}
