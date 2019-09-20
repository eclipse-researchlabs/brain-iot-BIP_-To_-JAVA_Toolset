package compound;



import atom.*;
import task.*;

// Compound Definition;

@Task(time=5)
public class sim07 extends Compound { 

public sim07( ){ 	
 start();
 } 
@CompoundMethodName(name="init")
public void init( ){
driver= new WaterHightcomputation() ; 

}
WaterHightcomputation driver;
}