package connector;


import org.lib.Printer;
import org.lib.Map;
import port.*;

import type.*;

import task.*;

@Task(time=2)
public class robotPosition extends Connector { 
public robotPosition(Position m, Position s){this.m=m; 

this.s=s; 

start(); 
} 
Position m; 

Position s; 

@ConnectorMethodName(name="_s_m")
public int _s_m() {
Type<String>  txt= new Type<String> (); 
Type<Integer>  id= new Type<Integer> (); 
 
 int _r=0;
  if(s.isAvailable() && m.isAvailable() && s.placeX.getVal() >0 && s.placeY.getVal()>0){
m.placeX.setVal(s.placeX.getVal() );
m.placeY.setVal(s.placeY.getVal() );
id.setVal(s.varid.getVal() );
//txt.setVal("--- Robot send  its positions to Orchestrator ---"  );
 //Printer.print( txt.getVal() , id.getVal() );
s.setAvailable(false)  ; s.atomNotify()  ; m.setAvailable(false)  ; m.atomNotify()  ; 
 _r =1;}
 return _r;}

}
