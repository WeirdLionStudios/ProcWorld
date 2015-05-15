package wls.venio.procworld.world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

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
		//Water
		for(int x=0;x<worldWidth;x++){
			for(int y=0;y<worldHeight;y++){
				if(heightMap[x][y]<seaLevel){
					g.setColor(Color.BLUE);
					g.fillRect(x, y, 1, 1);
				}
				
			}
		}
		//Shadows
		for(int x=1;x<worldWidth-1;x++){
			for(int y=1;y<worldHeight-1;y++){
				if(heightMap[x][y]>=seaLevel){
					if(heightMap[x+dx][y+dy]>heightMap[x][y])
						g.setColor(new Color(0.0f, 0.0f, 0.0f, shadowThickness));
					else
						g.setColor(new Color(0.0f, 0.0f, 0.0f, 0.0f));
					g.fillRect(x, y, 1, 1);
				}
			}
		}
		//LEGACY, to be examined since it had much better performance (half the execution time!)
		/*for (int x = 0; x < worldWidth; ++x) {
            for (int y = 0; y < worldHeight; ++y) {
                if (!(heightMap[x][y]<seaLevel || x == 0 || y == 0 || heightMap[x - 1][y - 1] <= heightMap[x][y])) {
                    g.setColor(new Color(0.0f, 0.0f, 0.0f, 0.5f));
                    g.fillRect(x, y, 1, 1);
                    continue;
                }
                if (heightMap[x][y]>seaLevel) continue;
                float alpha = (float)MathUtils.map(heightMap[x][y], 0.0, 0.45, 1.0, 0.0);
                alpha = (int)(alpha * 10.0f);
                g.setColor(new Color(0, 0, (int)((1.0f - (alpha/=10.0f)) * 255.0f)));
                g.fillRect(x, y, 1, 1);
            }
        }*/
	}
	
	public double[][] getHeightMap(){
		return heightMap;
	}
	
	public BufferedImage getGeographyMap(){
		return geoMap;
	}
}
