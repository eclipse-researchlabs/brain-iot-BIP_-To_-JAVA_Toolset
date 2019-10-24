package org.lib;



import com.paremus.brain.iot.example.door.impl.ComponentImpl;

public class Printer{

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
		Integer F = X; X=-1;
		return F;
	}
	public synchronized static Integer getY( ){
		Integer F = Y; Y=-1;
		return F;
	}
	public  static void openDoor (){
		System.out.println(" ------ Open it");
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
