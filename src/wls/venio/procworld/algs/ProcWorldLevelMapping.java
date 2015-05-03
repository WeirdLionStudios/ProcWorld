package wls.venio.procworld.algs;

import java.util.ArrayList;

/**
 * 
 * @author Daniele
 *
 *	Mapping between political subdivision level implemented as a table that points to the objects
 *
 *	.-----------------------.
 *	| low_0		| high_n	|
 *	|			.			|
 *	|			.			|
 *	|			.			|
 *	| low_n		| high_m	|
 *	.-----------------------.
 */

public class ProcWorldLevelMapping{
	int[] map;
	public ProcWorldLevelMapping(int size){
		map=new int[size];
		for(int i=0;i<size;i++){
			map[i]=-1;
		}
	}
	
	void addMapping(int a, int b){
		map[a]=b;
	}
	
	int getMapping(int a){
		return map[a];
	}
	
	void removeMapping(int a){
		map[a]=-1;
	}
	
	ArrayList<Integer> getAllMappings(int b){
		ArrayList<Integer> m=new ArrayList<Integer>();
		for(int i=0;i<map.length;i++)
			if(map[i]==b)
				m.add(i);
		return m;
	}
}
