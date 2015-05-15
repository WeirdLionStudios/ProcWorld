package wls.venio.procworld.world;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import wls.venio.procworld.GraphicRender;
import wls.venio.procworld.algs.MathUtils;
import wls.venio.procworld.algs.NameGen;
import wls.venio.procworld.algs.NeighbourNet;
import wls.venio.procworld.algs.PointSet;
import wls.venio.procworld.algs.PolygonBuilder;
import wls.venio.procworld.algs.ProcWorldLevelMapping;
import be.humphreys.simplevoronoi.GraphEdge;
import be.humphreys.simplevoronoi.Voronoi;

public class World{
	
	int curYear=0;
	
	Geography worldGeo;
	//L0, just raw and uninteresting data
	Polygon[] polygons;
	//L1, abstraction from L0 (plus some info)
	Territory[] territories;
	NeighbourNet adjTerrs;
	//L2, grouping of L1 members
	City[] cities;
	NeighbourNet adjCities;
	//L3, grouping of L2 members
	Nation[] nations;
	NeighbourNet adjNations;
	
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
		
		adjTerrs=new NeighbourNet(cityNum*10);
		
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
		//Subdivide in cities and then nations
		//generateCities();
	}
	
	private void generateCities(){
		ArrayList<Integer> chosen=new ArrayList<Integer>();
		Random rand=new Random((long)(seed*1000000));
		Polygon cur;
		NameGen.setSeed(seed);
		int n;
		for(int i=0;i<numCities;i++){
			//Find random territory
			do
				n=rand.nextInt(numTerritories);
			while(chosen.contains(n));
			//Generate a city on it
			cur=polygons[n];
			cities[i]=new City(n);
			chosen.add(n);
			
			cities[i].name=NameGen.generateName();
			cities[i].pop=MathUtils.randomInRange(10000, 2000000);
			
			System.out.println(cities[i].name+" || "+cities[i].pop);
		}
	}
	
	//Generate the poly array and territories
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
		polygons=polyBuilder.buildPolys(edges);
		
		//territory[i] has polygon polygon[i]
		for(int i=0;i<terrNum;i++)
			territories[i]=new Territory(MathUtils.getCentroid(polygons[i]));
		//Doing some neighbouring between territories
		adjTerrs=new NeighbourNet(terrNum);
		for(GraphEdge edge:edges)
			adjTerrs.addNeighbour(edge.site1, edge.site2);
		//Display the stuff!
		GraphicRender.setTerritories(territories, adjTerrs);
	}
	
	public Point getCityPos(int n){
		return territories[cities[n].mainTerritory].pos;
	}
}
