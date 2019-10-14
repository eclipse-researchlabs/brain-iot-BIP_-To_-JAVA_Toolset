package org.lib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.lib.Position.Position;
import org.lib.availability.Header;
import org.lib.availability.Robot_Pose;
import org.lib.availability.Stamp;
import org.lib.markersinsight.Markers_in_sight;
import org.lib.reply.gotolocation.AddReply;
import org.lib.request.Request;
import org.lib.request.Door.Door;
import org.lib.request.gotolocation.Goal;
import org.lib.request.gotolocation.GotToAdd;
import org.lib.request.gotolocation.Procedure;
import org.lib.request.gotolocation.Velocity;
import org.lib.request.gotolocation.querystate.GotoQueryState;
import org.lib.request.pickcomponent.PickAdd;
import org.lib.request.pickcomponent.querystate.PickQueryState;
import org.lib.request.placecomponent.PlaceAdd;
import org.lib.request.placecomponent.querystate.PlaceQueryState;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;



public class Command {

	static String IP="10.8.0.6";
	static String PORT="8080";
	


	static ArrayList<ArrayList<String>> Robots =new ArrayList<ArrayList<String>> ();
	
	static void addOperation(int RobotId, String Operation){
		
		if(Robots.size()==0){
			for(int i=0; i< 4; i++){
				Robots.add(new ArrayList<String> ());
			}
		}
		else{
			Robots.get(RobotId).add(Operation);
		}
	}
	
	static void removeOperation(int RobotId, String Operation){
		
		if(Robots.size()==0){
			for(int i=0; i< 4; i++){
				Robots.add(new ArrayList<String> ());
			}
		}
		else{
			for(int i=0; i< Robots.get(RobotId).size(); i++){
				if (Robots.get(RobotId).get(i).compareTo(Operation)==0){
					Robots.get(RobotId).remove(i); i=5;
				}
			}
		}
	}
	
	public static void main(String[] a) 
    { 
		System.out.println(read());


    } 
	
	public static Integer read(){
		return null;
		

	}
	
	public static Integer readOperation(){
		return null;
		

	}
	
	
	public static void writePosition(){
		
		System.out.println("ROBOT : SEND POSITION ");
		

	}
	
	public static void writeMarker(){
		
		System.out.println("ROBOT : SEND MARKER IN SIGHT ");
		
	}
	
	public static void writeAvailability( ){
		
		System.out.println("ROBOT : SEND AVAILABILITY ");
		
		
	}
	
	public static void writeGotoAdd( ){
		
		System.out.println("ROBOT : SEND GOTO ADD ");
	
		
	}
	
	public static void writeGotoCancel( ){
		
		System.out.println("ROBOT : SEND GOTO CANCEL ");

		
	}
	
	public static void writeGotoQueryState( ){
		
		System.out.println("ROBOT : SEND GOTO QUERY STATE ");

		
	}
	
	public static void writePickAdd( ){
		
		System.out.println("ROBOT : SEND PICK ADD ");

		
	}
	
	public static void writePickCancel( ){
		
		System.out.println("ROBOT : SEND PICK CANCEL ");

		
	}
	
	public static void writePickQueryState( ){
		
		System.out.println("ROBOT : SEND PICK QUERY STATE ");

		
	}
	

	public static void writePlaceAdd( ){
		
		System.out.println("ROBOT : SEND PLACE ADD ");

		
	}
	
	public static void writePlaceCancel( ){
		
		System.out.println("ROBOT : SEND PLCAE CANCEL ");

		
	}
	
	public static void writePlaceQueryState( ){
		
		System.out.println("ROBOT : SEND PLACE QUERY STATE ");

		
	}

	

	public static void writeChargeAdd( ){
		
		System.out.println("ROBOT : SEND CHARGE ADD ");

		
	}
	
	public static void writeChargeCancel( ){
		
		System.out.println("ROBOT : SEND CHARGE CANCEL ");
	
		
	}
	
	public static void writeChargeQueryState( ){
		
		System.out.println("ROBOT : SEND CHARGE QUERY STATE ");
	
		
	}
	
	public static void writeUnChargeAdd (){
		System.out.println("ROBOT : SEND RETURN UNCHARGE ADD ");

	}
	public static void writeUnChargeCancel(){
		System.out.println("ROBOT : SEND RETURN UNCHARGE CANCEL ");

	}
	
	public static int checkMarkers(){
		String request = "http://"+IP+":"+PORT+"/rb1_base_a/ar_pose_marker";

		String jsonObject = getResponse(request);
		System.out.println(jsonObject);
		Markers_in_sight cmd= (Markers_in_sight) getObject(jsonObject,Markers_in_sight.class);
		if(cmd!=null){
			if(cmd.getMarkers().size()==0){
				return 0;
				}else{
			
				}
		}else{
			return 0;
		}
		return cmd.getMarkers().size();
	}
	
	public static void printState(int RobotId, int mission ){
		
		if(mission==1){
		System.out.println("ROBOT : ROBOT ("+RobotId+") MISSION IS IN STATE FINISHED" );
		}else{
		System.out.println("ROBOT : ROBOT ("+RobotId+") MISSION IS IN STATE QUEUED RUNNING PAUSED UNKNOWN" );
		}
	}	
	
	public static void writeUnChargeQueryState(){
		System.out.println("ROBOT : SEND UNCHARGE QUERY STATE ");

	}
	public static void  writeOpenDoor(){
		String request = "http://"+IP+":"+PORT+"/door1/door_controller/command";
		Door d =new Door(1.0); 
		System.out.println(getJson(d));
		String jsonObject = postResponse(request, getJson(d));
		System.out.println(jsonObject);
	}
	public static void  writeCloseDoor(){
		String request = "http://"+IP+":"+PORT+"/door1/door_controller/command";
		Door d =new Door(0.0); 
		System.out.println(getJson(d));
		String jsonObject = postResponse(request, getJson(d));
		System.out.println(jsonObject);
	}
	public static void  queryStateDoor(){
		String request = "http://"+IP+":"+PORT+"/door1/door_controller/command";
		Door d =new Door(0.0); 
		System.out.println(getJson(d));
		String jsonObject = postResponse(request, getJson(d));
		System.out.println(jsonObject);
	}
	
	public static void writeGOTO(int RobotId, int mission){
		
		if(mission==4){//GOTO STORAGE
			if(RobotId==1){
				String request = "http://"+IP+":"+PORT+"/rb"+RobotId+"_base_a/robot_local_control/NavigationComponent/GoToComponent/add";
				Procedure p =new Procedure();
				Goal g = new Goal ();
				Header header = new Header();
				header.setSeq(0);
				header.setFrame_id("map");
				header.setStamp(new Stamp(0.0,0.0));
				g.setHeader(header);
				g.setPose(new Robot_Pose(8,-3.6,-3.14));
				p.addGoal(g);
				Velocity v =new Velocity(0.0,0.0,0.0);
				p.addVelovity(v);
				GotToAdd gotoadd =new GotToAdd();
				gotoadd.setProcedure(p);
				System.out.println(getJson(gotoadd));
				String jsonObject = postResponse(request, getJson(gotoadd));
				System.out.println(jsonObject);

			}
		}

		if(mission==5){//GOTO UNLOAD
			if(RobotId==1){
				String request = "http://"+IP+":"+PORT+"/rb"+RobotId+"_base_a/robot_local_control/NavigationComponent/GoToComponent/add";
				Procedure p =new Procedure();
				Goal g = new Goal ();
				Header header = new Header();
				header.setSeq(0);
				header.setFrame_id("map");
				header.setStamp(new Stamp(0.0,0.0));
				g.setHeader(header);
				g.setPose(new Robot_Pose(-3.6,8,-3.14));
				p.addGoal(g);
				Velocity v =new Velocity(1.0,1.0,0.0);
				p.addVelovity(v);
				GotToAdd gotoadd =new GotToAdd();
				gotoadd.setProcedure(p);
				System.out.println(getJson(gotoadd));
				String jsonObject = postResponse(request, getJson(gotoadd));
				System.out.println(jsonObject);

			}
		}
		
			
			if(mission==1){//PLACE CENTER
				if(RobotId==1){
					String request = "http://"+IP+":"+PORT+"/rb"+RobotId+"_base_a/robot_local_control/NavigationComponent/GoToComponent/add";
					Procedure p =new Procedure();
					Goal g = new Goal ();
					Header header = new Header();
					header.setSeq(0);
					header.setFrame_id("map");
					header.setStamp(new Stamp(0.0,0.0));
					g.setHeader(header);
					g.setPose(new Robot_Pose(0,0,-3.14));
					p.addGoal(g);
					Velocity v =new Velocity(0.0,0.0,0.0);
					p.addVelovity(v);
					GotToAdd gotoadd =new GotToAdd();
					gotoadd.setProcedure(p);
					System.out.println(getJson(gotoadd));
					String jsonObject = postResponse(request, getJson(gotoadd));
					System.out.println(jsonObject);

				}
			}
			if(mission==2){//PLACE LEFT
				if(RobotId==1){
					String request = "http://"+IP+":"+PORT+"/rb"+RobotId+"_base_a/robot_local_control/NavigationComponent/GoToComponent/add";
					Procedure p =new Procedure();
					Goal g = new Goal ();
					Header header = new Header();
					header.setSeq(0);
					header.setFrame_id("map");
					header.setStamp(new Stamp(0.0,0.0));
					g.setHeader(header);
					g.setPose(new Robot_Pose(-7.75,0,-3.14));
					p.addGoal(g);
					Velocity v =new Velocity(0.0,0.0,0.0);
					p.addVelovity(v);
					GotToAdd gotoadd =new GotToAdd();
					gotoadd.setProcedure(p);
					System.out.println(getJson(gotoadd));
					String jsonObject = postResponse(request, getJson(gotoadd));
					System.out.println(jsonObject);

				}
			}
			
			if(mission==3){//PLACE RIGHT
				if(RobotId==1){
					String request = "http://"+IP+":"+PORT+"/rb"+RobotId+"_base_a/robot_local_control/NavigationComponent/GoToComponent/add";
					Procedure p =new Procedure();
					Goal g = new Goal ();
					Header header = new Header();
					header.setSeq(0);
					header.setFrame_id("map");
					header.setStamp(new Stamp(0.0,0.0));
					g.setHeader(header);
					g.setPose(new Robot_Pose(7.75,0,-3.14));
					p.addGoal(g);
					Velocity v =new Velocity(0.0,0.0,0.0);
					p.addVelovity(v);
					GotToAdd gotoadd =new GotToAdd();
					gotoadd.setProcedure(p);
					System.out.println(getJson(gotoadd));
					String jsonObject = postResponse(request, getJson(gotoadd));
					System.out.println(jsonObject);

				}
			}
		
	}

	public static void placeCART(int RobotId, int cart){
		String request = "http://"+IP+":"+PORT+"/rb"+RobotId+"_base_a/robot_local_control/NavigationComponent/PlaceComponent/add";
		PlaceAdd padd =new PlaceAdd();
		padd.getProcedure().setPick_frame_id("");
		String jsonObjectin = getJson(padd);
		String jsonObject = postResponse(request, jsonObjectin);
		
	}
	public static int cancel(int RobotId, int mission){
		if (mission== 7){//pick
			String request = "http://"+IP+":"+PORT+"/rb"+RobotId+"_base_a/robot_local_control/NavigationComponent/PickComponent/cancel";
			

			PickQueryState query =new PickQueryState();
			
			query.getHeader().setId(-1);
			query.getHeader().getStamp().setSecs(0.0);
			query.getHeader().getStamp().setNsecs(0.0);
			
			String jsonObjectin = getJson(query);
			String jsonObject = postResponse(request, jsonObjectin);
			AddReply cmd= (AddReply) getObject(jsonObject,AddReply.class);
			String state = cmd.getState().getCurrent_state();
			System.out.println(state);

			
			if(state.compareTo("finished")==0){return 1;}
		}else{
			if(mission==3){//goto
				String request = "http://"+IP+":"+PORT+"/rb"+RobotId+"_base_a/robot_local_control/NavigationComponent/GoToComponent/cancel";
				
				System.out.println(request);
				GotoQueryState gotoquery =new GotoQueryState();
				
				gotoquery.getHeader().setId(-1);
				gotoquery.getHeader().getStamp().setSecs(0.0);
				gotoquery.getHeader().getStamp().setNsecs(0.0);
				
				String jsonObjectin = getJson(gotoquery);
				String jsonObject = postResponse(request, jsonObjectin);
				AddReply cmd= (AddReply) getObject(jsonObject,AddReply.class);
				String state = cmd.getState().getCurrent_state();
				System.out.println(state);
	
				
				if(state.compareTo("finished")==0){return 1;}
				
			}else{
				if(mission==11){//place
					String request = "http://"+IP+":"+PORT+"/rb"+RobotId+"_base_a/robot_local_control/NavigationComponent/PlaceComponent/cancel";
					

					PlaceQueryState query =new PlaceQueryState();
					
					query.getHeader().setId(-1);
					query.getHeader().getStamp().setSecs(0.0);
					query.getHeader().getStamp().setNsecs(0.0);
					
					String jsonObjectin = getJson(query);
					String jsonObject = postResponse(request, jsonObjectin);
					AddReply cmd= (AddReply) getObject(jsonObject,AddReply.class);
					String state = cmd.getState().getCurrent_state();
					System.out.println(state);
		
					
					if(state.compareTo("finished")==0){return 1;}					
				}
			}
		}
		return 0;
	}
	public static int queryState(int RobotId, int mission){
		
		
		
		if (mission== 7){//pick
			String request = "http://"+IP+":"+PORT+"/rb"+RobotId+"_base_a/robot_local_control/NavigationComponent/PickComponent/query_state";
			

			PickQueryState query =new PickQueryState();
			
			query.getHeader().setId(-1);
			query.getHeader().getStamp().setSecs(0.0);
			query.getHeader().getStamp().setNsecs(0.0);
			
			String jsonObjectin = getJson(query);
			String jsonObject = postResponse(request, jsonObjectin);
			AddReply cmd= (AddReply) getObject(jsonObject,AddReply.class);
			String state = cmd.getState().getCurrent_state();
			System.out.println(state);

			
			if(state.compareTo("finished")==0){return 1;}
		}else{
			if(mission==3){//goto
				String request = "http://"+IP+":"+PORT+"/rb"+RobotId+"_base_a/robot_local_control/NavigationComponent/GoToComponent/query_state";
				
				System.out.println(request);
				GotoQueryState gotoquery =new GotoQueryState();
				
				gotoquery.getHeader().setId(-1);
				gotoquery.getHeader().getStamp().setSecs(0.0);
				gotoquery.getHeader().getStamp().setNsecs(0.0);
				
				String jsonObjectin = getJson(gotoquery);
				String jsonObject = postResponse(request, jsonObjectin);
				AddReply cmd= (AddReply) getObject(jsonObject,AddReply.class);
				String state = cmd.getState().getCurrent_state();
				System.out.println(state);
	
				
				if(state.compareTo("finished")==0){return 1;}
				
			}else{
				if(mission==11){//place
					String request = "http://"+IP+":"+PORT+"/rb"+RobotId+"_base_a/robot_local_control/NavigationComponent/PlaceComponent/query_state";
					

					PlaceQueryState query =new PlaceQueryState();
					
					query.getHeader().setId(-1);
					query.getHeader().getStamp().setSecs(0.0);
					query.getHeader().getStamp().setNsecs(0.0);
					
					String jsonObjectin = getJson(query);
					String jsonObject = postResponse(request, jsonObjectin);
					System.out.println(jsonObject);
					AddReply cmd= (AddReply) getObject(jsonObject,AddReply.class);
					String state = cmd.getState().getCurrent_state();
					System.out.println(state);
		
					
					if(state.compareTo("finished")==0){return 1;}					
				}
			}
		}
		return 0;
		
	}
	


	static double DOCKINGAX;static double DOCKINGAY;	static double DOCKINGBX;static double DOCKINGBY;
	static double theta;
	
	static double UNLOADBX;	static double UNLOADBY;		static double UNLOADAX;	static double UNLOADAY;	
	
	static double  STORAGEAX=8;	static double  STORAGEAY=-3.6;	static double  STORAGEBX=6.427;	static double  STORAGEBY=-5.627; static double  STORAGECX=6.489;	static double  STORAGECY=-7.801;	
	
	
	static double  FRONTDOORX=5.429;	static double  FRONTDOORY=-0.102;	
	
	public static void pickCart(int RobotId, int cart){
		if(cart==2){//pick cart2
			if(RobotId==1){
				String request = "http://"+IP+":"+PORT+"/rb"+RobotId+"_base_a/robot_local_control/NavigationComponent/PickComponent/add";
				PickAdd p =new PickAdd();
				
				p.getProcedure().setPick_frame_id("rb"+RobotId+"_base_a_cart"+cart+"_contact");
				System.out.println(getJson(p));
				String jsonObject = postResponse(request, getJson(p));
				System.out.println(jsonObject);

			}
		}
	}
	
	public static int Isposition (int RobotId, int place){
		String request = "http://"+IP+":"+PORT+"/rb"+RobotId+"_base_a/robot_local_control/LocalizationComponent/status";
		
		String jsonObject = getResponse(request);
		
		//System.out.println(jsonObject);
		
		Position cmd= (Position) getObject(jsonObject,Position.class);
		
		double position_x = cmd.getPose().getPose().getX();
		
		double position_y = cmd.getPose().getPose().getY();
		
		System.out.println(cmd.toString());
		
		
		if (place== 3){//Docking
			if(position_x==DOCKINGAX && position_y== DOCKINGAY){
				return 1;
			}
			if(position_x==DOCKINGBX && position_y== DOCKINGBY){
				return 1;
			}			
			
		}
		if (place== 1){//door
			if(position_x==FRONTDOORX && position_y== FRONTDOORX){
				return 1;
			}
			
			
		}
		if (place== 3){//UNLOAD
			if(position_x==UNLOADBX && position_y== UNLOADBY){
				return 1;
			}
			if(position_x==UNLOADAX && position_y== UNLOADAY){
				return 1;
			}	
			
			
		}
		if (place== 2){//STORAGE
			
			if(position_x==STORAGEAX && position_y== STORAGEAX){
				return 1;
			}
			if(position_x==STORAGEBX && position_y== STORAGEBX){
				return 1;
			}	
			if(position_x==STORAGECX && position_y== STORAGECX){
				return 1;
			}	
			
		}	
			
		
		//send commands
		//wait response
		//getresponse
		
		return 0;
		
	}
	
	
	public static void printPosition (int RobotId, int place){
		
		
		if (place== 0){//Docking
			
		System.out.println("ORCHESTRATOR : ROBOT is in DOCKING PLACE");	
			
		}
		if (place== 1){//FRONTdoor
			
		System.out.println("ORCHESTRATOR : ROBOT is in FRONT OF THE DOOR");				
			
		}
		if (place== 2){//UNLOAD
			
		System.out.println("ORCHESTRATOR : ROBOT is in UNLOAD PLACE");					
			
		}
		if (place== 3){//CART
			
	    System.out.println("ORCHESTRATOR : ROBOT is in STORAGE PLACE");					
			
		}	
		//send commands
		//wait response
		//getresponse
		
		
		
	}
	
	
	public static String getResponse(String request) {
		String jsonObject ="";
		
		// Step2: Now pass JSON File Data to REST Service
		try {
			URL url = new URL(request);
			URLConnection connection = url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);
			//OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
			//out.write(jsonObject.toString());
			//out.close();

			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			jsonObject="";
			String str="";
			while ((str=in.readLine()) != null) {
				jsonObject+=str;
			}
			System.out.println("\nRobot REST Service Invoked Successfully..");
			in.close();
		} catch (Exception e) {
			System.out.println("\nError while calling ROBOT REST Service");
			System.out.println(e);
		}
		
		return jsonObject;

	}
	
	private static String postResponse(String request, String jsonObjectin) {
		String jsonObject ="";
		
		// Step2: Now pass JSON File Data to REST Service
		try {
			URL url = new URL(request);
			URLConnection connection = url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
			out.write(jsonObjectin.toString());
			out.close();

			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			jsonObject="";
			String str="";
			while ((str=in.readLine()) != null) {
				jsonObject+=str;
			}
			System.out.println("\nRobot REST Service Invoked Successfully..");
			in.close();
		} catch (Exception e) {
			System.out.println("\nError while calling ROBOT REST Service");
			System.out.println(e);
		}
		
		return jsonObject;
	}
	
	public static Object getObject(String txt, Class c){
		 ObjectMapper mapper = new ObjectMapper();
		 Object cmd = null;
			try {

				 cmd = mapper.readValue(txt, c);
				return cmd;

			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return cmd;
	}
	
	public static String getJson(Object c){	

	  
		ObjectMapper mapper = new ObjectMapper();
  
	try {
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

		
		//Convert object to JSON string
		String 			jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(c);
		return jsonInString;
		
		
	} catch (JsonGenerationException e) {
		e.printStackTrace();
	} catch (JsonMappingException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
	return "NULL";
	
	}
	
}
