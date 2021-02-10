import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class AllDots {
	private ArrayList<Dot> dots;
	String colX, colY;

	public AllDots() {
		dots = new ArrayList<>();
		colX="";
		colY="";
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
	
	//should return trur or false;
	public String doesContain(int x, int y) {
		String output ="";
        for(Dot d:dots) {
        	if(d.isIn(x,y)) {
        		// print the label
        		output = d.dotValues(colX, colY);
        	}
        }
        
        return output;
	}
	
	// create another method the returns one line String
	
	public void setCols(String col1, String col2) {
		colX=col1;
		colY=col2;
	}
	

}
