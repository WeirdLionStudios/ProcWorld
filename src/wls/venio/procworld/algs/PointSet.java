package wls.venio.procworld.algs;

import java.awt.Point;

public class PointSet {
	private double[] x;
	private double[] y;
	private int curSize=0;
	
	public PointSet(int s){
		x=new double[s];
		y=new double[s];
	}
	
	public void addPoint(double px, double py){
		x[curSize]=(double)px;
		y[curSize]=(double)py;
		curSize++;
	}
	
	public double[] getX(){
		return x;
	}
	
	public double[] getY(){
		return y;
	}
	
	public int getSize(){
		return curSize;
	}
	
	public Point getPoint(int index){
		if(index>curSize) return null;
		return new Point((int)x[index], (int)y[index]);
	}
	
	public Point[] getPointArray(){
		Point[] pts=new Point[curSize];
		for(int i=0;i<curSize;i++){
			pts[i]=new Point((int)x[i], (int)y[i]);
		}
		return pts;
	}
	
	public boolean contains(int px, int py){
		for(int i=0;i<curSize;i++)
			if(x[i]==px&&y[i]==py)
				return true;
		return false;
	}
	
	public double getMinDist(Point p){
		double min=MathUtils.distance(p.x, p.y, (int)x[0], (int)y[0]);
		for(int i=1;i<curSize;i++){
			double d=MathUtils.distance(p.x, p.y, (int)x[i], (int)y[i]);
			if(d<min)
				min=d;
		}
		return min;
	}
}
