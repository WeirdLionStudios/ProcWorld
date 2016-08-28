package wls.venio.procworld.world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import wls.venio.procworld.algs.LerpColor;
import wls.venio.procworld.algs.MathUtils;
import wls.venio.procworld.algs.Perlin;

public class Geography{
	int worldWidth;
	int worldHeight;
	double scale;
	double seaLevel;
	
	double[][] heightMap;
	BufferedImage geoMap;
	
	public Geography(int width, int height, double s, double sea){
		worldWidth=width;
		worldHeight=height;
		scale=s;
		seaLevel=sea;
		heightMap=new double[width][height];
		geoMap=new BufferedImage(width,
									height,
									BufferedImage.TYPE_INT_ARGB);
	}
	
	public void generateHeightMap(double seed){
		Perlin perlin=new Perlin(8, worldWidth, worldHeight, scale);
		heightMap=perlin.generateTerrain(seed);
	}
	
	public void renderGeographyMap(int lightDirection, float shadowThickness){
		Graphics g=geoMap.getGraphics();
		int dx=(lightDirection>>1&1)==1?1:-1;
		int dy=(lightDirection&1)==1?1:-1;
		
		//Find lowest point
		double min=1;
		for(int x=0;x<worldWidth;x++)
			for(int y=0;y<worldHeight;y++)
				if(heightMap[x][y]<min)
					min=heightMap[x][y];
			
		LerpColor lerp=new LerpColor(new Color(0, 0, 0), new Color(0, 0, 255));
		
		for (int x = 0; x < worldWidth; ++x) {
            for (int y = 0; y < worldHeight; ++y) {
                if (!(heightMap[x][y]<seaLevel || x == 0 || y == 0 || heightMap[x+dx][y+dy]<=heightMap[x][y])) {
                    g.setColor(new Color(0.0f, 0.0f, 0.0f, shadowThickness));
                    g.fillRect(x, y, 1, 1);
                    continue;
                }
                if (heightMap[x][y]>seaLevel) continue;
                g.setColor(lerp.lerp(heightMap[x][y], min, seaLevel));
                g.fillRect(x, y, 1, 1);
            }
        }
	}
	
	public double[][] getHeightMap(){
		return heightMap;
	}
	
	public BufferedImage getGeographyMap(){
		return geoMap;
	}
}
