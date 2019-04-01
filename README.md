# BIP_To_Java

> This is a first prototype for the resulting BIP to Java Mapping 

BIP To Java Plug-in 
Follow these instructions to install the BIP To Java Plug-in :
1- Download and Uncompress the Eclipse version located in : 
**         Linux 64-bits :http://download.polarsys.org/3p/PolarSysIDE/0.8/org.polarsys.ide.0.8-incubation-linux.gtk.x86_64.tar.gz
**        Windows 64-bits : http://download.polarsys.org/3p/PolarSysIDE/0.8/org.polarsys.ide.0.8-incubation-win32.win32.x86_64.zip
**         Windows 32-bits : http://download.polarsys.org/3p/PolarSysIDE/0.8/org.polarsys.ide.0.8-incubation-win32.win32.x86.zip



        
* 2- Download and Uncompress the developped Plugin in a specefic directory        
* 3- Go To Help | Install New software
* 4- Add the location of the Archive file ( The location where the Plugin was uncompressed)
* 5- Select org.verimag.feature.bip  and click Next
* 6- Select BIP feature and click Next
* 7- Accept the terms of licence and agrements and click Finish (In case of exception click OK)
* 8- Eclipse will restart
* 9- Import the BipToJavaCase10 (The location where the archive was uncompressed).
* 10- Try to remove "Atom package" and make some update on the BIP model -> The "Atom package" will automaticaclly created"

**Experiments :**

The project conains two folders:

**1- The BIP model of the Robot Behavior**
* Robot atomic Component
* Door atomic Component
* Orchestrator atomic Component
* The Main component gluing the BIP atomic components

**2- The Java code related to the BIP Model:**
* Unzip the file in Eclipse Workspace
* Import the Java project
