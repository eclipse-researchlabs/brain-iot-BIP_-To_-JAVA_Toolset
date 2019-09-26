package connector;


import org.lib.Printer;
import org.lib.Map;
import port.*;

import type.*;

import task.*;

@Task(time=2)
public class orchToDoor extends Connector { 
public orchToDoor(ReqToOpenDoor m, ReqToOpenDoor s){this.m=m; 

this.s=s; 

start(); 
} 
ReqToOpenDoor m; 

ReqToOpenDoor s; 

@ConnectorMethodName(name="_s_m")
public int _s_m() {
Type<String>  txt= new Type<String> (); 
Type<Integer>  id= new Type<Integer> (); 
 
 int _r=0;
  if(s.isAvailable() && m.isAvailable() ){
id.setVal(0 );
s.setAvailable(false)  ; s.atomNotify()  ; m.setAvailable(false)  ; m.atomNotify()  ; 
 _r =1;}
 return _r;}

}
