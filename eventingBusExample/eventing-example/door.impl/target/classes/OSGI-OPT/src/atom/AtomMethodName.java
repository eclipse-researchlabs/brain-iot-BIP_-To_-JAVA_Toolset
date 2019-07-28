package atom;

import task.*;

import port.*;


import org.lib.Printer;
import org.lib.Map;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;



@Retention(RetentionPolicy.RUNTIME)
public @interface AtomMethodName {
	
	String name();
}
