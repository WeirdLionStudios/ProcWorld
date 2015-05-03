package wls.venio.procworld.algs;

import java.awt.Point;

public class PointSet {
	public double[] x;
	public double[] y;
	int curIndex=0;
	
	public PointSet(int s){
		x=new double[s];
		y=new double[s];
	}
	
	public void addPoint(double px, double py){
		x[curIndex]=(double)px;
		y[curIndex]=(double)py;
		curIndex++;
	}
	
	public double[] getX(){
		return x;
	}
	
	public double[] getY(){
		return y;
	}
	
	public Point[] getPointArray(){
		Point[] pts=new Point[curIndex];
		for(int i=0;i<curIndex;i++){
			pts[i]=new Point((int)x[i], (int)y[i]);
		}
		return pts;
	}
	
	public boolean contains(int px, int py){
		for(int i=0;i<curIndex;i++)
			if(x[i]==px&&y[i]==py)
				return true;
		return false;
	}
}
