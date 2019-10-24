package atom;


import org.lib.Printer;
import org.lib.Map;
import port.*;

import type.*;

import task.*;

// atom definition;

@Task(time=1)
public class Robot extends Atom { 
// data definition;

Type<Integer> locationX= new Type<Integer> (0); 
Type<Integer> locationY= new Type<Integer> (0); 
Type<String> txt= new Type<String> (); 
Type<Integer> varid= new Type<Integer> (0); 
public Robot( Type<Integer> id){this.id=id; 
start();
 }

Type<Integer> id= new Type<Integer> (); 




Type<Boolean> initial =new Type<Boolean> (true);
Type<Boolean>STARTR=new Type<Boolean> (false);
Type<Boolean>Ready=new Type<Boolean> (false);
Type<Boolean>OnDock=new Type<Boolean> (false);
Type<Boolean>OnUnload=new Type<Boolean> (false);
Type<Boolean>OnStorage=new Type<Boolean> (false);
Position position;
@PortName(name="position") 
 public Position getposition ( ) {  
 if (position==null){position= new Position(varid, locationX, locationY ) ;} 
 
 return position;}
 
RobotReqToOpenDoor robotReqToOpenDoor;
@PortName(name="robotReqToOpenDoor") 
 public RobotReqToOpenDoor getrobotReqToOpenDoor ( ) {  
 if (robotReqToOpenDoor==null){robotReqToOpenDoor= new RobotReqToOpenDoor(varid ) ;} 
 
 return robotReqToOpenDoor;}
 
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
 
public ePort silentDockStart= new ePort ( ); 
public ePort silentStorageStart= new ePort ( ); 

 @AtomMethodName(name="initial")
 public int initial(){
 int _r=0;
 if( initial.getVal() ==true  ){locationX.setVal( Map.getDockingX( ) );
locationY.setVal( Map.getDockingY( ) );
varid.setVal(id.getVal() );
_r=1;
 // Activate next states 
STARTR.setVal(true);
initial.setVal(false);
 
   } return _r;}
 @AtomMethodName(name="position")  
 public int position(){
 int _r=0;
 if(  STARTR.getVal( )==true  && position.isAvailable() == false  )
{
position.setAvailable(true);  
}
if(position!=null){  
 if(  STARTR.getVal( )==true   && position.isNotified()  ){
position.setAvailable(false);

 // Deactivate previous states 
STARTR.setVal(false);
 // Activate next states 
Ready.setVal(true);
 
_r=1;
  position.conceal(); 
  }


 
} return _r; 
}


 @AtomMethodName(name="reqMoveToUnload")  
 public int reqMoveToUnload(){
 int _r=0;
 if(  Ready.getVal( )==true  && reqMoveToUnload.isAvailable() == false  )
{
reqMoveToUnload.setAvailable(true);  
}
if(reqMoveToUnload!=null){  
 if(  Ready.getVal( )==true   && reqMoveToUnload.isNotified()  ){
reqMoveToUnload.setAvailable(false);
 Printer.moveToUnload( );

 // Deactivate previous states 
Ready.setVal(false);
 // Activate next states 
OnUnload.setVal(true);
 
_r=1;
  reqMoveToUnload.conceal(); 
  }


 
} return _r; 
}


 @AtomMethodName(name="robotReqToOpenDoor")  
 public int robotReqToOpenDoor(){
 int _r=0;
 if(  OnUnload.getVal( )==true  && robotReqToOpenDoor.isAvailable() == false  )
{
robotReqToOpenDoor.setAvailable(true);  
}
if(robotReqToOpenDoor!=null){  
 if(  OnUnload.getVal( )==true   && robotReqToOpenDoor.isNotified()  ){
robotReqToOpenDoor.setAvailable(false);

 // Deactivate previous states 
OnUnload.setVal(false);
 // Activate next states 
STARTR.setVal(true);
 
_r=1;
  robotReqToOpenDoor.conceal(); 
  }


 
} return _r; 
}


 @AtomMethodName(name="reqMoveToDock")  
 public int reqMoveToDock(){
 int _r=0;
 if(  Ready.getVal( )==true  && reqMoveToDock.isAvailable() == false  )
{
reqMoveToDock.setAvailable(true);  
}
if(reqMoveToDock!=null){  
 if(  Ready.getVal( )==true   && reqMoveToDock.isNotified()  ){
reqMoveToDock.setAvailable(false);
 Printer.moveToDock( );

 // Deactivate previous states 
Ready.setVal(false);
 // Activate next states 
OnDock.setVal(true);
 
_r=1;
  reqMoveToDock.conceal(); 
  }


 
} return _r; 
}


 @AtomMethodName(name="silentDockStart")  
 public int silentDockStart(){
 int _r=0;
 if(  OnDock.getVal( )==true  && silentDockStart.isAvailable() == false  )
{
silentDockStart.setAvailable(true);  
}
 if( silentDockStart.isAvailable() ){
silentDockStart.setAvailable(false);

 // Deactivate previous states 
OnDock.setVal(false);
 // Activate next states 
STARTR.setVal(true);
 
_r=1;
  silentDockStart.conceal(); 
  }

 
 return _r;}


 @AtomMethodName(name="reqMoveToStorage")  
 public int reqMoveToStorage(){
 int _r=0;
 if(  Ready.getVal( )==true  && reqMoveToStorage.isAvailable() == false  )
{
reqMoveToStorage.setAvailable(true);  
}
if(reqMoveToStorage!=null){  
 if(  Ready.getVal( )==true   && reqMoveToStorage.isNotified()  ){
reqMoveToStorage.setAvailable(false);
 Printer.moveToStorage( );

 // Deactivate previous states 
Ready.setVal(false);
 // Activate next states 
OnStorage.setVal(true);
 
_r=1;
  reqMoveToStorage.conceal(); 
  }


 
} return _r; 
}


 @AtomMethodName(name="silentStorageStart")  
 public int silentStorageStart(){
 int _r=0;
 if(  OnStorage.getVal( )==true  && silentStorageStart.isAvailable() == false  )
{
silentStorageStart.setAvailable(true);  
}
 if( silentStorageStart.isAvailable() ){
silentStorageStart.setAvailable(false);

 // Deactivate previous states 
OnStorage.setVal(false);
 // Activate next states 
STARTR.setVal(true);
 
_r=1;
  silentStorageStart.conceal(); 
  }

 
 return _r;}

}