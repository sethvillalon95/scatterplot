import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class Dot {
	// the x and y position for this object
	private double xPos,yPos;
	// the x and y values; 
	private double xVal, yVal;
	private Ellipse2D.Double theDot;
	
	
	
	
	public Dot(double xPos, double yPos, double xVal, double yVal) {
		this.xPos =xPos;
		this.yPos = yPos;
		this.xVal = xVal; 
		this.yVal = yVal;
		theDot = new Ellipse2D.Double(xPos, yPos, 5, 5);
//		System.out.println("xPos: "+ xPos+" yPos: "+ yPos+" xVal: "+xVal+" yVal: " +yVal);
//		theDot = new Ellipse2D.Double(50, 50, 5, 5);

	}
	
	public void draw(Graphics g1) {
        Graphics2D g = (Graphics2D)g1;
//        g.fillOval((int)xPos, (int)yPos, 5, 5);
        g.setColor(Color.BLACK);
		g.fill(theDot);
		
//		System.out.println("The draw method from the Dot ran");
	}
	
	public boolean isIn(int x, int y) {
		if(theDot.contains(x,y)) {
			return true;
		}else {
			return false;
		}
	}
	
	public String dotValues(String qX, String qY) {
		String output="";
		output=qX+": "+Double.toString(xVal)+" "+qY+": "+Double.toString(yVal);
		return output;
	}
	
	public String  xValue() {
		String s = String.format("%.1f", xVal);
		return s;
	}
	
	public String  yValue() {
		String s = String.format("%.1f", yVal);
		return s;
	}
	

}
