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
		if(graph[a][b]==1) return;
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
	
	//Generate a NeighbourNet for the next level
	public NeighbourNet generateHigherNeighbourNet(ProcWorldLevelMapping mapping){
		NeighbourNet newNet=new NeighbourNet(mapping.getSize());
		int mapA, mapB;
		
		for(int a=0;a<size-1;a++)
			for(int b=a+1;b<size;b++){
				if(graph[a][b]==1){
					mapA=mapping.getMapping(a);
					mapB=mapping.getMapping(b);
					if(mapA!=mapB)
						newNet.addNeighbour(mapA, mapB);
				}
			}
		
		return newNet;
	}
	
	public void printMatrix(){
		System.out.print("  ");
		for(int i=0;i<size;i++)
			System.out.print(" "+i);
		System.out.println("");
		for(int i=0;i<size;i++){
			System.out.print(i+":");
			for(int j=0;j<size;j++)
				System.out.print(" "+graph[i][j]);
			System.out.println("");
		}
	}
}
