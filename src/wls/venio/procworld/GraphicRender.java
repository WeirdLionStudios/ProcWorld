package wls.venio.procworld;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import wls.venio.procworld.algs.NeighbourNet;
import wls.venio.procworld.algs.RenderBuffer;
import wls.venio.procworld.world.Territory;
import be.humphreys.simplevoronoi.GraphEdge;

public class GraphicRender extends JPanel{
	
	private static final long serialVersionUID = 9163581865474010335L;
	
	static int FRAME_WIDTH=800;
	static int FRAME_HEIGHT=700;
	
	JFrame frame=new JFrame("ProcWorld - v1.0");
	Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
	
	int worldWidth, worldHeight;
	double dx, dy;
	
	//DEBUG ONLY!!!!!
	static List<GraphEdge> edges;
	static boolean edgesSet=false;
	
	static Polygon[] polys;
	static boolean polysSet=false;
	
	static Territory[] territories;
	static NeighbourNet terrNeigh;
	static boolean terrSet;
	
	static boolean showTerrain=true;
	
	//Overlayed image to show the terrain
	BufferedImage geoMap;
	//Tells wich poly belongs to which nation for coloring purposes
	RenderBuffer buffer;
	
	public GraphicRender(int w, int h, BufferedImage shadows){
		//Passing dimensions and various boilerplate for the window
		worldWidth=w;
		worldHeight=h;
		geoMap=shadows;
		
		FRAME_WIDTH=w;
		FRAME_HEIGHT=h;
		
		dx=FRAME_WIDTH/w;
		dy=FRAME_HEIGHT/h;
		
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setLocation((screen.width-FRAME_WIDTH)/2,
						  (screen.height-FRAME_HEIGHT)/2);
		frame.setVisible(true);
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
	}
	//Update the buffer
	public void updateBuffer(RenderBuffer newBuffer){
		buffer=newBuffer;
	}
	//DEBUG!!!
	public static void setEdgeList(List<GraphEdge> newEdges){
		edges=newEdges;
		edgesSet=true;
	}
	//DEBUG!!!
	public static void setPolys(Polygon[] newPolys){
		polysSet=true;
		polys=new Polygon[newPolys.length];
		for(int i=0;i<newPolys.length;i++)
			polys[i]=newPolys[i];
	}
	//DEBUG!!
	public static void setTerritories(Territory[] terrs, NeighbourNet a){
		terrSet=true;
		territories=new Territory[terrs.length];
		for(int i=0;i<terrs.length;i++)
			territories[i]=terrs[i];
		terrNeigh=a;
	}
	
	@Override
	protected void paintComponent(Graphics g){
		
		g.setColor(Color.GREEN);
		g.fillRect(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
		g.drawImage(geoMap, 0, 0, FRAME_WIDTH, FRAME_HEIGHT, null);
		
		//DEBUG ONLY!!!
		
		g.setColor(Color.BLACK);
		//Edges
		if(edgesSet){
			for(GraphEdge edge:edges)
				g.drawLine((int)edge.x1, (int)edge.y1, (int)edge.x2, (int)edge.y2);
		}
		//Polygons
		if(polysSet){
			repaint();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//Terrain
				if(showTerrain)
					g.drawImage(geoMap, 0, 0, FRAME_WIDTH, FRAME_HEIGHT, null);
		//Territories & their neighbouring net
		if(terrSet){
			g.setColor(Color.RED);
			for(int i=0;i<territories.length;i++){
				ArrayList<Integer> adjacent=terrNeigh.getNeighbours(i);
				for(int j:adjacent)
					g.drawLine(territories[i].pos.x, territories[i].pos.y, territories[j].pos.x, territories[j].pos.y);
				g.fillRect(territories[i].pos.x, territories[i].pos.y, 5, 5);
				g.setColor(Color.RED);
			}
		}
	}
}
