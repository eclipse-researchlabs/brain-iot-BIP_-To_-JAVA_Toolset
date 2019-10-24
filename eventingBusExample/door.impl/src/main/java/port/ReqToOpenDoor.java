package port;

import type.*;

public class ReqToOpenDoor extends Port { 
public ReqToOpenDoor(Type<Integer> varid){  
this.varid=varid; 

}
public Type<Integer> varid= new Type<Integer> (); 
}
