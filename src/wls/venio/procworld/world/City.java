package wls.venio.procworld.world;

import java.awt.Point;

public class City{
	public int id;
	public String name;
	public int pop;
	public int[] territories;
	public int[] neigh;
	
	int x;
	int y;
	
	public City(Point p){
		x=p.x;
		y=p.y;
	}
}
