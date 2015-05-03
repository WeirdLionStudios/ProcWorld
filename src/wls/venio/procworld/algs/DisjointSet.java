package wls.venio.procworld.algs;

import java.util.ArrayList;

public class DisjointSet<T>{
	ArrayList<ArrayList<T>> sets=new ArrayList<ArrayList<T>>();
	
	public void makeSet(T element){
		ArrayList<T> singleton=new ArrayList<T>();
		singleton.add(element);
		sets.add(singleton);
	}
	
	public void union(T first, T second){
		ArrayList<T> set1=find(first);
		ArrayList<T> set2=find(second);
		
		sets.remove(set1);
		sets.remove(set2);
		
		try{
			set1.addAll(set2);
		}
		catch(NullPointerException e){
			System.err.println(set1+" "+set2);
			System.exit(1);
		}
		sets.add(set1);
	}
	
	public ArrayList<T> find(T element){
		for(ArrayList<T> list:sets){
			for(T item:list){
				if(item.equals(element))
					return list;
			}
		}
		return null;
	}
	
	public void move(T elem, T second){
		ArrayList<T> set1=find(elem);
		ArrayList<T> set2=find(second);
		
		set1.remove(elem);
		set2.add(elem);
	}
	
	public String toString(){
		return sets.toString();
	}
}