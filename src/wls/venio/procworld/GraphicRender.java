package wls.venio.procworld;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import wls.venio.procworld.algs.RenderBuffer;
import be.humphreys.simplevoronoi.GraphEdge;

public class GraphicRender extends JPanel{
	
	private static final long serialVersionUID = 9163581865474010335L;
	
	static final int FRAME_WIDTH=800;
	static final int FRAME_HEIGHT=700;
	
	JFrame frame=new JFrame("ProcWorld - v1.0");
	Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
	
	int worldWidth, worldHeight;
	double dx, dy;
	
	//DEBUG ONLY!!!!!
	static List<GraphEdge> edges;
	static Polygon[] polys;
	static boolean polysSet=false;
	
	//Overlayed image to show the terrain
	BufferedImage geoMap;
	//Tells wich poly belongs to which nation for coloring purposes
	RenderBuffer buffer;
	
	public GraphicRender(int w, int h, BufferedImage shadows){
		//Passing dimensions and various boilerplate for the window
		worldWidth=w;
		worldHeight=h;
		geoMap=shadows;
		
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
	}
	//DEBUG!!!
	public static void setPolys(Polygon[] newPolys){
		polysSet=true;
		polys=new Polygon[newPolys.length];
		for(int i=0;i<newPolys.length;i++)
			polys[i]=newPolys[i];
	}
	
	@Override
	protected void paintComponent(Graphics g){
		/**
		g.setColor(Color.GREEN);
		g.fillRect(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
		g.drawImage(geoMap, 0, 0, FRAME_WIDTH, FRAME_HEIGHT, null);
		**/
		//DEBUG ONLY!!!
		g.setColor(Color.BLACK);
		if(polysSet){
			repaint();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			System.out.println("Hello!");
			g.drawImage(geoMap, 0, 0, FRAME_WIDTH, FRAME_HEIGHT, null);
		}
	}
}
