package wls.venio.procworld;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;

import wls.venio.procworld.world.World;

public class ProcWorldMain{
	
	public static int worldWidth;
	public static int worldHeight;
	public static double scale;
	public static double seed;
	
	public static int numCities;
	public static int numNations;
	
	public static double allyProb;
	public static double enemyProb;
	
	public static double seaLevel;
	public static int lightDir;
	public static float shadowThickness;
	
	public static void main(String[] args){
		ProcWorldMain main=new ProcWorldMain();
		//Load config
		try{
			main.loadConfig();
		}
		catch(IOException e){
			System.err.println("Unable to load config properly!");
			System.exit(1);
		}
		if(seed==-1){
			Random rand=new Random();
			rand.setSeed(System.currentTimeMillis());
			seed=rand.nextDouble()*1000;
		}
		System.out.println("seed="+seed);
		if(numCities<=numNations){
			System.out.println("WTF!");
			System.exit(0);
		}
		//Init world and generate
		World world=new World(numCities, numNations, allyProb, enemyProb, lightDir, shadowThickness, seaLevel, seed);
		System.out.println("Starting world generation...");
		world.generateWorld(worldWidth, worldHeight, scale);
		System.out.println("World generated!");
		//Init graphics
		@SuppressWarnings("unused")
		GraphicRender render=new GraphicRender(worldWidth, worldHeight, world.geoMap);
	}
	/**
	 * Load config file
	 * @throws IOException
	 */
	private void loadConfig() throws IOException{
		Properties prop = new Properties();
		String propFileName = "procworld_config.properties";
 
		InputStream inputStream =getClass().getClassLoader().getResourceAsStream(propFileName);
 
		if (inputStream != null){
			prop.load(inputStream);
		}
		else{
			throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
		}
		
		worldWidth=Integer.parseInt(prop.getProperty("world_width"));
		worldHeight=Integer.parseInt(prop.getProperty("world_height"));
		seed=Double.parseDouble(prop.getProperty("seed"));
		scale=Double.parseDouble(prop.getProperty("scale"));
		numCities=Integer.parseInt(prop.getProperty("num_cities"));
		numNations=Integer.parseInt(prop.getProperty("num_nations"));
		allyProb=Double.parseDouble(prop.getProperty("ally_prob"));
		enemyProb=Double.parseDouble(prop.getProperty("enemy_prob"));
		seaLevel=Double.parseDouble(prop.getProperty("sea_level"));
		lightDir=Integer.parseInt(prop.getProperty("light_dir"));
		shadowThickness=Float.parseFloat(prop.getProperty("shadow_thick"));
	}
}
