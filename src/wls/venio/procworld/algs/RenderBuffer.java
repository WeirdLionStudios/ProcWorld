package wls.venio.procworld.algs;


//Keeps all the data that is needed for the rendering that isn't constant.
//This is needed because the main game thread is separated from the rendering thread,
//so when the stuff is to be rendered it might have changed in the meantime (nasty!)
//All the other data are either read from the World class or copied directly in the render.
public class RenderBuffer {
	ProcWorldLevelMapping cityToNat;
	
	public RenderBuffer(ProcWorldLevelMapping map){
		cityToNat=map;
	}
}
