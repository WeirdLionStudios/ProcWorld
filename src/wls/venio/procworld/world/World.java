package wls.venio.procworld.world;

import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import wls.venio.procworld.GraphicRender;
import wls.venio.procworld.algs.MathUtils;
import wls.venio.procworld.algs.NameGen;
import wls.venio.procworld.algs.PointSet;
import wls.venio.procworld.algs.PolygonBuilder;
import wls.venio.procworld.algs.ProcWorldLevelMapping;
import be.humphreys.simplevoronoi.GraphEdge;
import be.humphreys.simplevoronoi.Voronoi;

public class World{
	
	int curYear=0;
	
	Geography worldGeo;
	Polygon[] poligons;
	Territory[] territories;
	City[] cities;
	Nation[] nations;
	
	//Misc values for world generation
	private int numNations, numCities, numTerritories, lightDir;
	private float shadowThick;
	private double allyProb, enemyProb, seaLevel, seed;
	
	//Geography generation results
	public double[][] heightMap;
	public BufferedImage geoMap;
	
	//Political subdivision
	ProcWorldLevelMapping terrToCity, cityToNat;
	
	public World(int cityNum, int natNum, double ally, double enemy, int light, float shadow, double sea, double s){
		allyProb=ally;
		enemyProb=enemy;
		numTerritories=cityNum*10;
		numCities=cityNum;
		numNations=natNum;
		lightDir=light;
		shadowThick=shadow;
		seaLevel=sea;
		seed=s;
		
		cities=new City[cityNum];
		nations=new Nation[natNum];
		
		terrToCity=new ProcWorldLevelMapping(numTerritories);
		cityToNat=new ProcWorldLevelMapping(cityNum);
	}
	
	public void generateWorld(int width, int height, double scale){
		//Generate terrain etc.
		worldGeo=new Geography(width, height, scale, seaLevel);
		long lastTimeRecorded=System.nanoTime();
		worldGeo.generateHeightMap(seed);
		System.out.println("Generated heightmap in "+(System.nanoTime()-lastTimeRecorded)/1000000+"ms");
		//Geomap, for rendering purposes of course...
		lastTimeRecorded=System.nanoTime();
		worldGeo.renderGeographyMap(lightDir, shadowThick);
		heightMap=worldGeo.getHeightMap();
		geoMap=worldGeo.getGeographyMap();
		System.out.println("Generated geomap in "+(System.nanoTime()-lastTimeRecorded)/1000000+"ms");
		//Generate political subdivision in territories
		generateTerritories(width, height);
		generateCities();
		//Subdivide in cities and then nations
		
	}
	
	private void generateCities(){
		ArrayList<Integer> chosen=new ArrayList<Integer>();
		Random rand=new Random((long)(seed*1000000));
		Polygon cur;
		String print="";
		int n;
		for(int i=0;i<numCities;i++){
			n=rand.nextInt(numTerritories);
			cur=territories[n].poly;
			cities[i]=new City(MathUtils.getCentroid(cur));
			chosen.add(n);
			
			cities[i].name=NameGen.generateName();
			cities[i].pop=MathUtils.randomInRange(10000, 2000000);
			
			print+=cities[i].name+" || "+cities[i].pop+"\n";
		}
		//just printing to file to test, removable
		Writer writer = null;

		try {
		    writer = new BufferedWriter(new OutputStreamWriter(
		          new FileOutputStream("logCities.txt"), "utf-8"));
		    writer.write(print);
		} catch (IOException ex) {} 
		finally {try {writer.close();} catch (Exception ex) {}}
	}
	
	//Generate the poly array
	private void generateTerritories(int w, int h){
		int terrNum=numTerritories;
		territories=new Territory[terrNum];
		PointSet points=new PointSet(terrNum);
		//Generate points
		Random rand=new Random((long)(seed*1000000));
		int x, y;
		for(int i=0;i<terrNum;i++){
			x=rand.nextInt(w);
			y=rand.nextInt(h);
			if(heightMap[x][y]<seaLevel||points.contains(x, y)){
				i--;
				continue;
			}
			else
				points.addPoint(x, y);
		}
		//Build polygons with Voronoi
		List<GraphEdge> edges=new Voronoi(0).generateVoronoi(points.getX(), points.getY(),0, w, 0, h);
		PolygonBuilder polyBuilder=new PolygonBuilder(terrNum);
		GraphicRender.setEdgeList(edges);
		poligons=polyBuilder.buildPolys(edges);
		for(int i=0;i<terrNum;i++){
			territories[i]=new Territory(MathUtils.getCentroid(poligons[i]));
			territories[i].poly=poligons[i];
		}
	}
}
