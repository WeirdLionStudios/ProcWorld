package wls.venio.procworld.algs;

import java.awt.Polygon;
import java.util.HashMap;

public class RenderBuffer {
	HashMap<Polygon, Integer> polyMap=new HashMap<Polygon, Integer>();
	
	public void addPoly(Polygon p, int nation){
		polyMap.put(p, nation);
	}
	
	public void movePoly(Polygon p, int newNation){
		polyMap.put(p, newNation);
	}
}
