package compound;


import org.lib.Printer;
import org.lib.Map;
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
driver= new OrchestratorDriver(new Type<Integer>(2)) ; 

orch= new RemoteDoor() ; 

c2 = new orchToDoor (orch.getreq(), driver.getreq() );

}
  public OrchestratorDriver driver; 
 public RemoteDoor orch; 
 public orchToDoor c2; 

}