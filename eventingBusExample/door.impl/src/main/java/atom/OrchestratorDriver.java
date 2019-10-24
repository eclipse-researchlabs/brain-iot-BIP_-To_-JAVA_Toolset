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
Type<Integer> var_ReqToOpenDoor= new Type<Integer> (0); 
public OrchestratorDriver( Type<Integer> id){this.id=id; 
start();
 }

Type<Integer> id= new Type<Integer> (); 




Type<Boolean> initial =new Type<Boolean> (true);
Type<Boolean>CTRLDOOR=new Type<Boolean> (false);
ReqToOpenDoor req;
@PortName(name="req") 
 public ReqToOpenDoor getreq ( ) {  
 if (req==null){req= new ReqToOpenDoor(varid ) ;} 
 
 return req;}
 
public ePort silent_getDoorState= new ePort ( ); 

 @AtomMethodName(name="initial")
 public int initial(){
 int _r=0;
 if( initial.getVal() ==true  ){ Printer.listening( );
_r=1;
 // Activate next states 
CTRLDOOR.setVal(true);
initial.setVal(false);
 
   } return _r;}
 @AtomMethodName(name="silent_getDoorState")  
 public int silent_getDoorState(){
 int _r=0;
 if(  CTRLDOOR.getVal( )==true  && silent_getDoorState.isAvailable() == false  && (var_ReqToOpenDoor.getVal() != 1 ) )
{
silent_getDoorState.setAvailable(true);  
}
 if( silent_getDoorState.isAvailable() ){
silent_getDoorState.setAvailable(false);
var_ReqToOpenDoor.setVal( Printer.getDoorState( ) );

 // Deactivate previous states 
CTRLDOOR.setVal(false);
 // Activate next states 
CTRLDOOR.setVal(true);
 
_r=1;
  silent_getDoorState.conceal(); 
  }

 
 return _r;}


 @AtomMethodName(name="req")  
 public int req(){
 int _r=0;
 if(  CTRLDOOR.getVal( )==true  && req.isAvailable() == false  && (var_ReqToOpenDoor.getVal() == 1 ) )
{
req.setAvailable(true);  
}
if(req!=null){  
 if(  CTRLDOOR.getVal( )==true   && req.isNotified()  ){
req.setAvailable(false);
var_ReqToOpenDoor.setVal(0 );

 // Deactivate previous states 
CTRLDOOR.setVal(false);
 // Activate next states 
CTRLDOOR.setVal(true);
 
_r=1;
  req.conceal(); 
  }


 
} return _r; 
}

}