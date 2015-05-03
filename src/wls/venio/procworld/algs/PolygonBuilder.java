package wls.venio.procworld.algs;

import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import be.humphreys.simplevoronoi.GraphEdge;

public class PolygonBuilder{
	
	Polygon[] poly;
	
	int polyCount;
	
	public PolygonBuilder(int polyNum){
		poly=new Polygon[polyNum];
		polyCount=polyNum;
	}
	
	//Build beautiful polygons from the edges outputted by the evil library!
	public Polygon[] buildPolys(List<GraphEdge> edges){
		Polygon[] rawPolys=new Polygon[polyCount];
		for(int i=0;i<polyCount;i++)
			rawPolys[i]=new Polygon();
		
		//Put the vertices in each polygon
		for(GraphEdge edge:edges){
			rawPolys[edge.site1]=addPointWithCheck(rawPolys[edge.site1], new Point((int)edge.x1, (int)edge.y1));
			rawPolys[edge.site1]=addPointWithCheck(rawPolys[edge.site1], new Point((int)edge.x2, (int)edge.y2));
			rawPolys[edge.site2]=addPointWithCheck(rawPolys[edge.site2], new Point((int)edge.x1, (int)edge.y1));
			rawPolys[edge.site2]=addPointWithCheck(rawPolys[edge.site2], new Point((int)edge.x2, (int)edge.y2));
		}
		
		//Sort them
		for(int i=0;i<polyCount;i++)
			poly[i]=sortPoly(rawPolys[i]);
		
		return poly;
	}
	
	//Add the point to the polygon if it's not already in there
	private Polygon addPointWithCheck(Polygon poly, Point p){
		if(!containsPoint(p, getPoints(poly)))
			poly.addPoint(p.x, p.y);
		return poly;
	}
	
	//Sort the points of the polygon
	private Polygon sortPoly(Polygon raw){
		Point centroid = new Point(0, 0);
		Point [] arr=getPoints(raw);
	    ArrayList<Point> verts=new ArrayList<Point>();
	    
	    for(int i=0;i<raw.npoints;i++)
	    	verts.add(arr[i]);

        for (int i=0;i<verts.size();i++) {
            centroid.x+=verts.get(i).x/verts.size();
            centroid.y+=verts.get(i).y/verts.size();
        }
        Collections.sort(verts, new Comparator<Point>(){

            @Override
            public int compare(Point p1, Point p2) {
                return Double.compare(getPolar(p1, centroid), getPolar(p2, centroid));
            }
        });
        
        Polygon sorted=new Polygon();
        for(int i=0;i<verts.size();i++)
        	sorted.addPoint(verts.get(i).x, verts.get(i).y);
        return sorted;
	}
	
	//Get angle between point and centroid
	private static double getPolar(Point point, Point centroid) {
        double dx = point.x-centroid.x;
        double dy = point.y-centroid.y;
        return Math.atan2(dy, dx);
    }
	
	//Get the points of a polygon
	private Point[] getPoints(Polygon p){
		Point[] points=new Point[p.npoints];
		for(int i=0;i<p.npoints;i++)
			points[i]=new Point(p.xpoints[i], p.ypoints[i]);
		return points;
	}
	
	//Does the polygon contain that point?
	private boolean containsPoint(Point p, Point[] points){
		for(Point point:points)
			if(p.x==point.x&&p.y==point.y)
				return true;
		return false;
	}
}
