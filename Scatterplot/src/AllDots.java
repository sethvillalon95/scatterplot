import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class AllDots {
	private ArrayList<Dot> dots;

	public AllDots() {
		dots = new ArrayList<>();
	}
	
	public void newDot(double xPos, double yPos, double xVal, double yVal) {
		dots.add(new Dot(xPos,yPos,xVal,yVal));
	}
	
	public void draw(Graphics g1) {
        Graphics2D g = (Graphics2D)g1;
        
        // draws all the dots
        for(Dot d:dots) {
        	d.draw(g);
        }
	}	

}
