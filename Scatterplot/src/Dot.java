import java.awt.Graphics;
import java.awt.geom.Ellipse2D;

public class Dot {
	// the x and y position for this object
	private double xPos,yPos;
	// the x and y values; 
	private double xVal, yVal;
	
	
	public Dot(double xPos, double yPos, double xVal, double yVal) {
		this.xPos =xPos;
		this.yPos = yPos;
		this.xVal = xVal; 
		this.yVal = yVal;
//		System.out.println("xPos: "+ xPos+" yPos: "+ yPos+" xVal: "+xVal+" yVal: " +yVal);
	}
	
	public void draw(Graphics g) {
		
	}
	

}
