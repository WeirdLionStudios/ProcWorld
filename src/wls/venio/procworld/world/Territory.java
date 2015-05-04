package wls.venio.procworld.world;

import java.awt.Point;
import java.awt.Polygon;

public class Territory{
	public int neigh[];
	public Polygon poly;
	
	int x;
	int y;
	
	public Territory(Point p){	
		x=p.x;
		y=p.y;	
	}

}
