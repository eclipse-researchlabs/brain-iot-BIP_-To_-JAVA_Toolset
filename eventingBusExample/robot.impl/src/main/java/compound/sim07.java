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
driver= new RobotDriver(new Type<Integer>(2)) ; 

robot= new Robot(new Type<Integer>(3)) ; 

c1 = new robotToOrch (driver.getrobotReqToOpenDoor(), robot.getrobotReqToOpenDoor() );

c3 = new robotPosition (driver.getposition(), robot.getposition() );

c4 = new moveToDock (driver.getreqMoveToDock(), robot.getreqMoveToDock() );

c5 = new moveToUnload (driver.getreqMoveToUnload(), robot.getreqMoveToUnload() );

c6 = new moveToStorage (driver.getreqMoveToStorage(), robot.getreqMoveToStorage() );

}
  public RobotDriver driver; 
 public Robot robot; 
 public robotToOrch c1; 
 public robotPosition c3; 
 public moveToDock c4; 
 public moveToUnload c5; 
 public moveToStorage c6; 

}