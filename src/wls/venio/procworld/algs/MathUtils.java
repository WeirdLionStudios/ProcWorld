package wls.venio.procworld.algs;

import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Random;

public class MathUtils {
	public static double map(double val, double initMin, double initMax, double finalMin, double finalMax) {
        double r = (val - initMin) / (initMax - initMin);
        return finalMin + r * (finalMax - finalMin);
    }

    public static double distance(int AX, int AY, int BX, int BY) {
        return Math.sqrt((AX - BX) * (AX - BX) + (AY - BY) * (AY - BY));
    }

    public static double gaussianArea(ArrayList<Point> verts) {
        double area = 0.0;
        for (int i = 0; i < verts.size(); ++i) {
            area+=0.5 * (double)Math.abs(verts.get((int)(i % verts.size())).x * verts.get((int)((i + 1) % verts.size())).y - verts.get((int)(i % verts.size())).y * verts.get((int)((i + 1) % verts.size())).x);
        }
        return area;
    }
    
    public static Point getCentroid(Polygon poly){
    	double x=0, y=0;
    	for(int i=0;i<poly.npoints;i++){
    		x+=poly.xpoints[i];
    		y+=poly.ypoints[i];
    	}
    	x/=poly.npoints;
    	y/=poly.npoints;
    	return new Point((int)x, (int)y);
    }
    
    public static int randomInRange(int min, int max){	
    	return new Random().nextInt(max-min)+min;	
    }
}
