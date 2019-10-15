package compound;


import org.lib.Command;
import atom.*;
import port.*;
import type.*;

import task.*;

import connector.*;

// Compound Definition;

@Task(time=5)
public class sim07 extends Compound { 

public sim07( ){ 	
 start();
 } 
@CompoundMethodName(name="init")
public void init( ){
orchestrator= new Orchestrator() ; 

}
  public Orchestrator orchestrator; 

}