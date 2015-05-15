package wls.venio.procworld.world;

public class City extends Level{
	public int id;
	public String name;
	public int pop;
	
	int mainTerritory;
	
	public City(int t){
		mainTerritory=t;
		level=2;
	}
}
