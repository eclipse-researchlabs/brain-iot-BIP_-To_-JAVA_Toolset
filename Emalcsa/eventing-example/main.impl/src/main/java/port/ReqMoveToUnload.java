package port;

import type.*;

public class ReqMoveToUnload extends Port { 
public ReqMoveToUnload(Type<Integer> varid, Type<Integer> placeX, Type<Integer> placeY){  
this.varid=varid; 
this.placeX=placeX; 
this.placeY=placeY; 

}
public Type<Integer> varid= new Type<Integer> (); 
public Type<Integer> placeX= new Type<Integer> ();
public Type<Integer> placeY= new Type<Integer> ();
}
