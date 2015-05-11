package wls.venio.procworld.algs;

import java.util.ArrayList;

/**
 * 
 * Neighbouring stuff implemented as an undirected, unweighted graph (matrix representation used)
 *
 */

public class NeighbourNet{
	private int[][] graph;
	private int size;
	
	public NeighbourNet(int s){
		size=s;
		graph=new int[size][size];
		for(int i=0;i<size;i++)
			for(int j=0;j<size;j++)
				graph[i][j]=0;
	}
	
	public void addNeighbour(int a, int b){
		graph[a][b]=1;
		graph[b][a]=1;
	}
	
	public ArrayList<Integer> getNeighbours(int a){
		ArrayList<Integer> neigh=new ArrayList<Integer>();
		for(int i=0;i<size;i++)
				if(graph[a][i]==1)
					neigh.add(i);
		return neigh;
	}
}
