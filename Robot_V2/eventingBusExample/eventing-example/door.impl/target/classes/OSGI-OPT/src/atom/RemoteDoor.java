package atom;


import org.lib.Printer;
import org.lib.Map;
import port.*;

import type.*;

import task.*;

// atom definition;

@Task(time=1)
public class RemoteDoor extends Atom { 
// data definition;

Type<String> txt= new Type<String> (); 
Type<Integer> varid= new Type<Integer> (0); 
public RemoteDoor( ){start();
 }





Type<Boolean> initial =new Type<Boolean> (true);
Type<Boolean>STARTD=new Type<Boolean> (false);
ReqToOpenDoor req;
@PortName(name="req") 
 public ReqToOpenDoor getreq ( ) {  
 if (req==null){req= new ReqToOpenDoor(varid ) ;} 
 
 return req;}
 

 @AtomMethodName(name="initial")
 public int initial(){
 int _r=0;
 if( initial.getVal() ==true  ){_r=1;
 // Activate next states 
STARTD.setVal(true);
initial.setVal(false);
 
   } return _r;}
 @AtomMethodName(name="req")  
 public int req(){
 int _r=0;
 if(  STARTD.getVal( )==true  && req.isAvailable() == false  )
{
req.setAvailable(true);  
}
if(req!=null){  
 if(  STARTD.getVal( )==true   && req.isNotified()  ){
req.setAvailable(false);
 Printer.openDoor( );

 // Deactivate previous states 
STARTD.setVal(false);
 // Activate next states 
STARTD.setVal(true);
 
_r=1;
  req.conceal(); 
  }


 
} return _r; 
}

}