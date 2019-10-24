package connector;


import org.lib.Printer;
import org.lib.Map;
import port.*;

import type.*;

import task.*;

@Task(time=2)
public class moveToUnload extends Connector { 
public moveToUnload(ReqMoveToUnload m, ReqMoveToUnload s){this.m=m; 

this.s=s; 

start(); 
} 
ReqMoveToUnload m; 

ReqMoveToUnload s; 

@ConnectorMethodName(name="_s_m")
public int _s_m() {
Type<String>  txt= new Type<String> (); 
Type<Integer>  id= new Type<Integer> (); 
 
 int _r=0;
  if(s.isAvailable() && m.isAvailable() ){
s.placeX.setVal(m.placeX.getVal() );
s.placeY.setVal(m.placeY.getVal() );
id.setVal(m.varid.getVal() );
txt.setVal("("  + m.varid.getVal() + ")-->"  + "("  + s.varid.getVal() + ") [Orchestrator] Orchestrator send REQ to MoveToUnload"  );
 Printer.print( txt.getVal() , id.getVal() );
s.setAvailable(false)  ; s.atomNotify()  ; m.setAvailable(false)  ; m.atomNotify()  ; 
 _r =1;}
 return _r;}

}
