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

orch= new Orchestrator(new Type<Integer>(3)) ; 

c1 = new robotToOrch (orch.getrobotReqToOpenDoor(), driver.getrobotReqToOpenDoor() );

c2 = new orchToDoor (orch.getreq(), driver.getreq() );

c3 = new robotPosition (orch.getposition(), driver.getposition() );

c4 = new moveToDock (orch.getreqMoveToDock(), driver.getreqMoveToDock() );

c5 = new moveToUnload (orch.getreqMoveToUnload(), driver.getreqMoveToUnload() );

c6 = new moveToStorage (orch.getreqMoveToStorage(), driver.getreqMoveToStorage() );

}
  public OrchestratorDriver driver; 
 public Orchestrator orch; 
 public robotToOrch c1; 
 public orchToDoor c2; 
 public robotPosition c3; 
 public moveToDock c4; 
 public moveToUnload c5; 
 public moveToStorage c6; 

}