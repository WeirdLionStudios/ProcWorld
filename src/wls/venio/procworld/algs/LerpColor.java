package wls.venio.procworld.algs;

import java.awt.Color;

public class LerpColor {
	private Color color1, color2;
	
	public LerpColor(Color c1, Color c2){
		color1=c1;
		color2=c2;
	}
	
	public Color lerp(double val, double min, double max){
		if(val>max) return Color.BLACK;
		int r=(int)MathUtils.map(val, min, max, color1.getRed(), color2.getRed());
		int g=(int)MathUtils.map(val, min, max, color1.getGreen(), color2.getGreen());
		int b=(int)MathUtils.map(val, min, max, color1.getBlue(), color2.getBlue());
		return new Color(r, g, b);
	}
}
