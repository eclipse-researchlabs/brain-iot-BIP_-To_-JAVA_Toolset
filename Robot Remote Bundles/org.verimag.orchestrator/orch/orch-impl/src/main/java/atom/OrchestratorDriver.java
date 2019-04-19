package atom;


import org.lib.Printer;
import org.lib.Map;
import port.*;

import type.*;

import task.*;

// atom definition;

@Task(time=1)
public class OrchestratorDriver extends Atom { 
// data definition;

Type<String> txt= new Type<String> (); 
Type<Integer> varid= new Type<Integer> (0); 
Type<Integer> locationX= new Type<Integer> (0); 
Type<Integer> locationY= new Type<Integer> (0); 
Type<Integer> notvalue= new Type<Integer> (0); 
Type<Integer> var_robotReqToOpenDoor= new Type<Integer> (0); 
public OrchestratorDriver( Type<Integer> id){this.id=id; 
start();
 }

Type<Integer> id= new Type<Integer> (); 




Type<Boolean> initial =new Type<Boolean> (true);
Type<Boolean>CTRLDOOR=new Type<Boolean> (false);
Type<Boolean>CTRLROBOTREQDOOR=new Type<Boolean> (false);
Type<Boolean>CTRLROBOTPOSITION=new Type<Boolean> (false);
Type<Boolean>CTRLMOVEDOCK=new Type<Boolean> (false);
Type<Boolean>CTRLMOVEUNLOAD=new Type<Boolean> (false);
Type<Boolean>CTRLMOVESTORAGE=new Type<Boolean> (false);
ReqToOpenDoor req;
@PortName(name="req") 
 public ReqToOpenDoor getreq ( ) {  
 if (req==null){req= new ReqToOpenDoor(varid ) ;} 
 
 return req;}
 
Position position;
@PortName(name="position") 
 public Position getposition ( ) {  
 if (position==null){position= new Position(varid, locationX, locationY ) ;} 
 
 return position;}
 
public ePort silent_getposition= new ePort ( ); 
RobotReqToOpenDoor robotReqToOpenDoor;
@PortName(name="robotReqToOpenDoor") 
 public RobotReqToOpenDoor getrobotReqToOpenDoor ( ) {  
 if (robotReqToOpenDoor==null){robotReqToOpenDoor= new RobotReqToOpenDoor(varid ) ;} 
 
 return robotReqToOpenDoor;}
 
public ePort silent_getStateRobotRequest= new ePort ( ); 
ReqMoveToDock reqMoveToDock;
@PortName(name="reqMoveToDock") 
 public ReqMoveToDock getreqMoveToDock ( ) {  
 if (reqMoveToDock==null){reqMoveToDock= new ReqMoveToDock(varid, locationX, locationY ) ;} 
 
 return reqMoveToDock;}
 
ReqMoveToUnload reqMoveToUnload;
@PortName(name="reqMoveToUnload") 
 public ReqMoveToUnload getreqMoveToUnload ( ) {  
 if (reqMoveToUnload==null){reqMoveToUnload= new ReqMoveToUnload(varid, locationX, locationY ) ;} 
 
 return reqMoveToUnload;}
 
ReqMoveToStorage reqMoveToStorage;
@PortName(name="reqMoveToStorage") 
 public ReqMoveToStorage getreqMoveToStorage ( ) {  
 if (reqMoveToStorage==null){reqMoveToStorage= new ReqMoveToStorage(varid, locationX, locationY ) ;} 
 
 return reqMoveToStorage;}
 

 @AtomMethodName(name="initial")
 public int initial(){
 int _r=0;
 if( initial.getVal() ==true  ){varid.setVal(id.getVal() );
 Printer.listening( );
_r=1;
 // Activate next states 
CTRLDOOR.setVal(true);
 // Activate next states 
CTRLROBOTREQDOOR.setVal(true);
 // Activate next states 
CTRLROBOTPOSITION.setVal(true);
 // Activate next states 
CTRLMOVEDOCK.setVal(true);
 // Activate next states 
CTRLMOVEUNLOAD.setVal(true);
 // Activate next states 
CTRLMOVESTORAGE.setVal(true);
initial.setVal(false);
 
   } return _r;}
 @AtomMethodName(name="req")  
 public int req(){
 int _r=0;
 if(  CTRLDOOR.getVal( )==true  && req.isAvailable() == false  )
{
req.setAvailable(true);  
}
if(req!=null){  
 if(  CTRLDOOR.getVal( )==true   && req.isNotified()  ){
req.setAvailable(false);
 Printer.openDoor( );

 // Deactivate previous states 
CTRLDOOR.setVal(false);
 // Activate next states 
CTRLDOOR.setVal(true);
 
_r=1;
  req.conceal(); 
  }


 
} return _r; 
}


 @AtomMethodName(name="silent_getStateRobotRequest")  
 public int silent_getStateRobotRequest(){
 int _r=0;
 if(  CTRLROBOTREQDOOR.getVal( )==true  && silent_getStateRobotRequest.isAvailable() == false  && (var_robotReqToOpenDoor.getVal() <= 0 ) )
{
silent_getStateRobotRequest.setAvailable(true);  
}
 if( silent_getStateRobotRequest.isAvailable() ){
silent_getStateRobotRequest.setAvailable(false);
var_robotReqToOpenDoor.setVal( Printer.getRobotReqToOpenDoor( ) );

 // Deactivate previous states 
CTRLROBOTREQDOOR.setVal(false);
 // Activate next states 
CTRLROBOTREQDOOR.setVal(true);
 
_r=1;
  silent_getStateRobotRequest.conceal(); 
  }

 
 return _r;}


 @AtomMethodName(name="robotReqToOpenDoor")  
 public int robotReqToOpenDoor(){
 int _r=0;
 if(  CTRLROBOTREQDOOR.getVal( )==true  && robotReqToOpenDoor.isAvailable() == false  && (var_robotReqToOpenDoor.getVal() > 0 ) )
{
robotReqToOpenDoor.setAvailable(true);  
}
if(robotReqToOpenDoor!=null){  
 if(  CTRLROBOTREQDOOR.getVal( )==true   && robotReqToOpenDoor.isNotified()  ){
robotReqToOpenDoor.setAvailable(false);
var_robotReqToOpenDoor.setVal(0 );

 // Deactivate previous states 
CTRLROBOTREQDOOR.setVal(false);
 // Activate next states 
CTRLROBOTREQDOOR.setVal(true);
 
_r=1;
  robotReqToOpenDoor.conceal(); 
  }


 
} return _r; 
}


 @AtomMethodName(name="silent_getposition")  
 public int silent_getposition(){
 int _r=0;
 if(  CTRLROBOTPOSITION.getVal( )==true  && silent_getposition.isAvailable() == false  )
{
silent_getposition.setAvailable(true);  
}
 if( silent_getposition.isAvailable() ){
silent_getposition.setAvailable(false);
locationX.setVal( Printer.getX( ) );
locationY.setVal( Printer.getY( ) );

 // Deactivate previous states 
CTRLROBOTPOSITION.setVal(false);
 // Activate next states 
CTRLROBOTPOSITION.setVal(true);
 
_r=1;
  silent_getposition.conceal(); 
  }

 
 return _r;}


 @AtomMethodName(name="position")  
 public int position(){
 int _r=0;
 if(  CTRLROBOTPOSITION.getVal( )==true  && position.isAvailable() == false  && (locationX.getVal() >= 0 && locationY.getVal() >= 0 ) )
{
position.setAvailable(true);  
}
if(position!=null){  
 if(  CTRLROBOTPOSITION.getVal( )==true   && position.isNotified()  ){
position.setAvailable(false);
locationX.setVal(0 );
locationY.setVal(0 );

 // Deactivate previous states 
CTRLROBOTPOSITION.setVal(false);
 // Activate next states 
CTRLROBOTPOSITION.setVal(true);
 
_r=1;
  position.conceal(); 
  }


 
} return _r; 
}


 @AtomMethodName(name="reqMoveToDock")  
 public int reqMoveToDock(){
 int _r=0;
 if(  CTRLMOVEDOCK.getVal( )==true  && reqMoveToDock.isAvailable() == false  )
{
reqMoveToDock.setAvailable(true);  
}
if(reqMoveToDock!=null){  
 if(  CTRLMOVEDOCK.getVal( )==true   && reqMoveToDock.isNotified()  ){
reqMoveToDock.setAvailable(false);
txt.setVal("X"  );
 Printer.send( txt.getVal() , locationX.getVal() );
txt.setVal("Y"  );
 Printer.send( txt.getVal() , locationY.getVal() );
 Printer.moveToDock( );

 // Deactivate previous states 
CTRLMOVEDOCK.setVal(false);
 // Activate next states 
CTRLMOVEDOCK.setVal(true);
 
_r=1;
  reqMoveToDock.conceal(); 
  }


 
} return _r; 
}


 @AtomMethodName(name="reqMoveToUnload")  
 public int reqMoveToUnload(){
 int _r=0;
 if(  CTRLMOVEUNLOAD.getVal( )==true  && reqMoveToUnload.isAvailable() == false  )
{
reqMoveToUnload.setAvailable(true);  
}
if(reqMoveToUnload!=null){  
 if(  CTRLMOVEUNLOAD.getVal( )==true   && reqMoveToUnload.isNotified()  ){
reqMoveToUnload.setAvailable(false);
txt.setVal("X"  );
 Printer.send( txt.getVal() , locationX.getVal() );
txt.setVal("Y"  );
 Printer.send( txt.getVal() , locationY.getVal() );
 Printer.moveToUnload( );

 // Deactivate previous states 
CTRLMOVEUNLOAD.setVal(false);
 // Activate next states 
CTRLMOVEUNLOAD.setVal(true);
 
_r=1;
  reqMoveToUnload.conceal(); 
  }


 
} return _r; 
}


 @AtomMethodName(name="reqMoveToStorage")  
 public int reqMoveToStorage(){
 int _r=0;
 if(  CTRLMOVESTORAGE.getVal( )==true  && reqMoveToStorage.isAvailable() == false  )
{
reqMoveToStorage.setAvailable(true);  
}
if(reqMoveToStorage!=null){  
 if(  CTRLMOVESTORAGE.getVal( )==true   && reqMoveToStorage.isNotified()  ){
reqMoveToStorage.setAvailable(false);
txt.setVal("X"  );
 Printer.send( txt.getVal() , locationX.getVal() );
txt.setVal("Y"  );
 Printer.send( txt.getVal() , locationY.getVal() );
 Printer.moveToStorage( );

 // Deactivate previous states 
CTRLMOVESTORAGE.setVal(false);
 // Activate next states 
CTRLMOVESTORAGE.setVal(true);
 
_r=1;
  reqMoveToStorage.conceal(); 
  }


 
} return _r; 
}

}