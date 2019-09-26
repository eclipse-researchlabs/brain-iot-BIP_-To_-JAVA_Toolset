package atom;


import org.lib.Printer;
import org.lib.Map;
import port.*;

import type.*;

import task.*;

// atom definition;

@Task(time=1)
public class Orchestrator extends Atom { 
// data definition;

Type<Integer> varid= new Type<Integer> (0); 
Type<Integer> locationX= new Type<Integer> (0); 
Type<Integer> locationY= new Type<Integer> (0); 
Type<Integer> dockingX= new Type<Integer> (0); 
Type<Integer> dockingY= new Type<Integer> (0); 
Type<Integer> unloadX= new Type<Integer> (0); 
Type<Integer> unloadY= new Type<Integer> (0); 
Type<Integer> storageX= new Type<Integer> (0); 
Type<Integer> storageY= new Type<Integer> (0); 
Type<Integer> load= new Type<Integer> (0); 
Type<String> txt= new Type<String> (); 
public Orchestrator( Type<Integer> id){this.id=id; 
start();
 }

Type<Integer> id= new Type<Integer> (); 




Type<Boolean> initial =new Type<Boolean> (true);
Type<Boolean>START=new Type<Boolean> (false);
Type<Boolean>DoorOpen=new Type<Boolean> (false);
Type<Boolean>DoorClosed=new Type<Boolean> (false);
Type<Boolean>CheckUnload=new Type<Boolean> (false);
Type<Boolean>MoveToDock=new Type<Boolean> (false);
Type<Boolean>NotMoveToDock=new Type<Boolean> (false);
Type<Boolean>MoveToStorage=new Type<Boolean> (false);
Type<Boolean>MoveToUnload=new Type<Boolean> (false);
Position position;
@PortName(name="position") 
 public Position getposition ( ) {  
 if (position==null){position= new Position(varid, locationX, locationY ) ;} 
 
 return position;}
 
ReqToOpenDoor req;
@PortName(name="req") 
 public ReqToOpenDoor getreq ( ) {  
 if (req==null){req= new ReqToOpenDoor(varid ) ;} 
 
 return req;}
 
RobotReqToOpenDoor robotReqToOpenDoor;
@PortName(name="robotReqToOpenDoor") 
 public RobotReqToOpenDoor getrobotReqToOpenDoor ( ) {  
 if (robotReqToOpenDoor==null){robotReqToOpenDoor= new RobotReqToOpenDoor(varid ) ;} 
 
 return robotReqToOpenDoor;}
 
ReqMoveToDock reqMoveToDock;
@PortName(name="reqMoveToDock") 
 public ReqMoveToDock getreqMoveToDock ( ) {  
 if (reqMoveToDock==null){reqMoveToDock= new ReqMoveToDock(varid, dockingX, dockingY ) ;} 
 
 return reqMoveToDock;}
 
ReqMoveToUnload reqMoveToUnload;
@PortName(name="reqMoveToUnload") 
 public ReqMoveToUnload getreqMoveToUnload ( ) {  
 if (reqMoveToUnload==null){reqMoveToUnload= new ReqMoveToUnload(varid, unloadX, unloadY ) ;} 
 
 return reqMoveToUnload;}
 
ReqMoveToStorage reqMoveToStorage;
@PortName(name="reqMoveToStorage") 
 public ReqMoveToStorage getreqMoveToStorage ( ) {  
 if (reqMoveToStorage==null){reqMoveToStorage= new ReqMoveToStorage(varid, storageX, storageY ) ;} 
 
 return reqMoveToStorage;}
 
public ePort silentLoad0= new ePort ( ); 
public ePort silentLoad1= new ePort ( ); 
public ePort silentToUnload= new ePort ( ); 
public ePort silentToStorage= new ePort ( ); 

 @AtomMethodName(name="initial")
 public int initial(){
 int _r=0;
 if( initial.getVal() ==true  ){dockingX.setVal( Map.getDockingX( ) );
dockingY.setVal( Map.getDockingY( ) );
unloadX.setVal( Map.getUnloadX( ) );
unloadY.setVal( Map.getUnloadY( ) );
storageX.setVal( Map.getStorageX( ) );
storageY.setVal( Map.getStorageY( ) );
load.setVal(1 );
varid.setVal(id.getVal() );
_r=1;
 // Activate next states 
START.setVal(true);
initial.setVal(false);
 
   } return _r;}
 @AtomMethodName(name="robotReqToOpenDoor")  
 public int robotReqToOpenDoor(){
 int _r=0;
 if(  DoorClosed.getVal( )==true  && robotReqToOpenDoor.isAvailable() == false  )
{
robotReqToOpenDoor.setAvailable(true);  
}
if(robotReqToOpenDoor!=null){  
 if(  DoorClosed.getVal( )==true   && robotReqToOpenDoor.isNotified()  ){
robotReqToOpenDoor.setAvailable(false);

 // Deactivate previous states 
DoorClosed.setVal(false);
 // Activate next states 
DoorOpen.setVal(true);
 
_r=1;
  robotReqToOpenDoor.conceal(); 
  }


 
} return _r; 
}


 @AtomMethodName(name="req")  
 public int req(){
 int _r=0;
 if(  DoorOpen.getVal( )==true  && req.isAvailable() == false  )
{
req.setAvailable(true);  
}
if(req!=null){  
 if(  DoorOpen.getVal( )==true   && req.isNotified()  ){
req.setAvailable(false);

 // Deactivate previous states 
DoorOpen.setVal(false);
 // Activate next states 
START.setVal(true);
 
_r=1;
  req.conceal(); 
  }


 
} return _r; 
}


 @AtomMethodName(name="position")  
 public int position(){
 int _r=0;
 if(  START.getVal( )==true  && position.isAvailable() == false  )
{
position.setAvailable(true);  
}
if(position!=null){  
 if(  START.getVal( )==true   && position.isNotified()  ){
position.setAvailable(false);

 // Deactivate previous states 
START.setVal(false);
 // Activate next states 
CheckUnload.setVal(true);
 
_r=1;
  position.conceal(); 
  }


 
} return _r; 
}


 @AtomMethodName(name="silentLoad0")  
 public int silentLoad0(){
 int _r=0;
 if(  CheckUnload.getVal( )==true  && silentLoad0.isAvailable() == false  && (load.getVal() <= 0 ) )
{
silentLoad0.setAvailable(true);  
}
 if( silentLoad0.isAvailable() ){
silentLoad0.setAvailable(false);
//txt.setVal(" [Silent][Orchestrator] Orchestrator MoveToDock"  );
 //Printer.print( txt.getVal() , varid.getVal() );

 // Deactivate previous states 
CheckUnload.setVal(false);
 // Activate next states 
MoveToDock.setVal(true);
 
_r=1;
  silentLoad0.conceal(); 
  }

 
 return _r;}


 @AtomMethodName(name="silentLoad1")  
 public int silentLoad1(){
 int _r=0;
 if(  CheckUnload.getVal( )==true  && silentLoad1.isAvailable() == false  && (load.getVal() > 0 ) )
{
silentLoad1.setAvailable(true);  
}
 if( silentLoad1.isAvailable() ){
silentLoad1.setAvailable(false);
//txt.setVal(" [silent][Orchestrator] Orchestrator NotMoveToDock"  );
 //Printer.print( txt.getVal() , varid.getVal() );

 // Deactivate previous states 
CheckUnload.setVal(false);
 // Activate next states 
NotMoveToDock.setVal(true);
 
_r=1;
  silentLoad1.conceal(); 
  }

 
 return _r;}


 @AtomMethodName(name="reqMoveToDock")  
 public int reqMoveToDock(){
 int _r=0;
 if(  MoveToDock.getVal( )==true  && reqMoveToDock.isAvailable() == false  )
{
reqMoveToDock.setAvailable(true);  
}
if(reqMoveToDock!=null){  
 if(  MoveToDock.getVal( )==true   && reqMoveToDock.isNotified()  ){
reqMoveToDock.setAvailable(false);

 // Deactivate previous states 
MoveToDock.setVal(false);
 // Activate next states 
START.setVal(true);
 
_r=1;
  reqMoveToDock.conceal(); 
  }


 
} return _r; 
}


 @AtomMethodName(name="silentToUnload")  
 public int silentToUnload(){
 int _r=0;
 if(  NotMoveToDock.getVal( )==true  && silentToUnload.isAvailable() == false  && (locationX.getVal() == storageX.getVal() && locationY.getVal() == storageY.getVal() || locationX.getVal() == dockingX.getVal() && locationY.getVal() == dockingY.getVal() ) )
{
silentToUnload.setAvailable(true);  
}
 if( silentToUnload.isAvailable() ){
silentToUnload.setAvailable(false);
//txt.setVal(" THE UNLOADING CHARGE LEVEL  : "  );
 //Printer.printLoad( txt.getVal() , load.getVal() );
load.setVal(load.getVal() - 1 );
//txt.setVal(" [silent][Orchestrator] Orchestrator MoveToUnload"  );
// Printer.print( txt.getVal() , varid.getVal() );

 // Deactivate previous states 
NotMoveToDock.setVal(false);
 // Activate next states 
MoveToUnload.setVal(true);
 
_r=1;
  silentToUnload.conceal(); 
  }

 
 return _r;}


 @AtomMethodName(name="silentToStorage")  
 public int silentToStorage(){
 int _r=0;
 if(  NotMoveToDock.getVal( )==true  && silentToStorage.isAvailable() == false  && ( !(locationX.getVal() == storageX.getVal() && locationY.getVal() == storageY.getVal() || locationX.getVal() == dockingX.getVal() && locationY.getVal() == dockingY.getVal()) ) )
{
silentToStorage.setAvailable(true);  
}
 if( silentToStorage.isAvailable() ){
silentToStorage.setAvailable(false);
//txt.setVal(" [silent][Orchestrator] Orchestrator  MoveToStorage"  );
 //Printer.print( txt.getVal() , varid.getVal() );

 // Deactivate previous states 
NotMoveToDock.setVal(false);
 // Activate next states 
MoveToStorage.setVal(true);
 
_r=1;
  silentToStorage.conceal(); 
  }

 
 return _r;}


 @AtomMethodName(name="reqMoveToUnload")  
 public int reqMoveToUnload(){
 int _r=0;
 if(  MoveToUnload.getVal( )==true  && reqMoveToUnload.isAvailable() == false  )
{
reqMoveToUnload.setAvailable(true);  
}
if(reqMoveToUnload!=null){  
 if(  MoveToUnload.getVal( )==true   && reqMoveToUnload.isNotified()  ){
reqMoveToUnload.setAvailable(false);

 // Deactivate previous states 
MoveToUnload.setVal(false);
 // Activate next states 
DoorClosed.setVal(true);
 
_r=1;
  reqMoveToUnload.conceal(); 
  }


 
} return _r; 
}


 @AtomMethodName(name="reqMoveToStorage")  
 public int reqMoveToStorage(){
 int _r=0;
 if(  MoveToStorage.getVal( )==true  && reqMoveToStorage.isAvailable() == false  )
{
reqMoveToStorage.setAvailable(true);  
}
if(reqMoveToStorage!=null){  
 if(  MoveToStorage.getVal( )==true   && reqMoveToStorage.isNotified()  ){
reqMoveToStorage.setAvailable(false);

 // Deactivate previous states 
MoveToStorage.setVal(false);
 // Activate next states 
START.setVal(true);
 
_r=1;
  reqMoveToStorage.conceal(); 
  }


 
} return _r; 
}

}