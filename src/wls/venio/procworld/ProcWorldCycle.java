package wls.venio.procworld;

import wls.venio.procworld.world.World;

public class ProcWorldCycle{
	public static boolean run=true;
	World world;
	
	public ProcWorldCycle(World w){
		world=w;
	}
	
	public void beginSimulation(){
		while(true){
			if(run)
				doStep();
		}
	}
	
	public void doStep(){
		
	}
}
