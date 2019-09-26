package connector;


import org.lib.Printer;
import org.lib.Map;
import port.*;

import type.*;

import task.*;

@Task(time=2)
public class robotToOrch extends Connector { 
public robotToOrch(RobotReqToOpenDoor m, RobotReqToOpenDoor s){this.m=m; 

this.s=s; 

start(); 
} 
RobotReqToOpenDoor m; 

RobotReqToOpenDoor s; 

@ConnectorMethodName(name="_s_m")
public int _s_m() {
Type<String>  txt= new Type<String> (); 
Type<Integer>  id= new Type<Integer> (); 
 
 int _r=0;
  if(s.isAvailable() && m.isAvailable() ){
id.setVal(s.varid.getVal() );
//txt.setVal("  --- Robot Send REQ to Orchestrator to open the door --- "  );
// Printer.print( txt.getVal() , id.getVal() );
s.setAvailable(false)  ; s.atomNotify()  ; m.setAvailable(false)  ; m.atomNotify()  ; 
 _r =1;}
 return _r;}

}
