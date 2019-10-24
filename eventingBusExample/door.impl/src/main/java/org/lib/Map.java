package org.lib;

public class Map {

	static Integer [][] map ={
			{-1,0,  0, 0, 0},
			{0, 0,  0, 0, 0},
			{0 ,0 , 0, 0, 0},
			{0 ,0 , 0, 0, 0},
			{0 ,0 , 0, 0, 1}
	};
	
	static Integer dockingX=4;
	static Integer dockingY=4;
	
	static Integer unloadX=2;
	static Integer unloadY=2;
	
	static Integer storageX=0;
	static Integer storageY=4;
	
	
	
	
	static Integer unloadArea =5;
	
	Integer dockingArea=5;
	
	static Integer storageArea=0;
	
	
	public synchronized static void unload()
	{
		map[unloadX][unloadY]= map[unloadX][unloadY] -1;
	}
	
	public synchronized static  void store()
	{
		map[storageX][storageY]= map[storageX][storageY] +1;
	}
	
	public synchronized static  void dock()
	{
		map[dockingX][dockingY]= map[dockingX][dockingY] +1;
	}
	
	public synchronized static  void undock()
	{
		map[dockingX][dockingY]= map[dockingX][dockingY] -1;
	}

	public synchronized static Integer getDockingX() {
		return dockingX;
	}



	public synchronized static Integer getDockingY() {
		return dockingY;
	}


	public synchronized static Integer getUnloadX() {
		return unloadX;
	}



	public synchronized static Integer getUnloadY() {
		return unloadY;
	}



	public synchronized static Integer getStorageX() {
		return storageX;
	}



	public synchronized static Integer getStorageY() {
		return storageY;
	}

	
	
	/*public static void main(String[] arg){
	print();
		
		
	}*/
	
	public synchronized static Integer	unloadIsEmpty(){
		if (map[unloadX][unloadY] !=0){
			return 0;
		}
		return 1;
	}
	
	public static void print(){
		for(int i = 0 ; i<5;i++){
			for(int j = 0 ; j<5;j++){
				System.out.print("["+map[i][j]+"]");
			} System.out.println();
		}
		
		
	}
	
	
	
}
