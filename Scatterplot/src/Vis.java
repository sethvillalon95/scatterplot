import java.awt.Rectangle;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Collections;


import javax.swing.JPanel;


public class Vis extends JPanel implements MouseListener, MouseMotionListener {

    private String textToDisplay;
    private Map<String, Double> data;
    private Map<String, Double> relativeData;
    private Ellipse2D.Double seth;
    private Rectangle box;
    private Point corner;
//    private List<Point2D> scatterData;
    private List<Point2D> relativeScatterData;
    private double [] xCoords;
    private double [] yCoords;

    private AllDots dots;
    String colX;
    String colY;
    
    // values of the window
    int startH =600;
    int startW=800;
    
    // heght and width 
    double winH;
    double winW;
    
    double p1x, p1y, p2x,p2y;
    double rel_p1x, rel_p1y, rel_p2x,rel_p2y;
    // the revversers are to cancel out the max value to get the raw value from the scatterData
    double xValRev,yValRev;
    
    double minX, maxX, minY, maxY;
    double newMinX, newMaxX, newMinY,newMaxY;
    double rangeX, rangeY, newRangeX,newRangeY;
    

    public Vis() {
        super();
        textToDisplay = "There's nothing to see here.";
        
        relativeData = new HashMap<>();
        relativeScatterData = new ArrayList<>();
        
        // 
        xCoords = new double[116];
        yCoords = new double[116];

        
        seth = new Ellipse2D.Double(50, 100, 40, 40);
        
        addMouseListener(this);
        addMouseMotionListener(this);
        
        box = null;
        dots = new AllDots();
        
        colX="";
        colY="";
        
        xValRev=0;
        yValRev=0;
        
        //zoom features vars
        p1x= 0;
        p1y= 0;
        p2x= 0;
        p2y= 0;
        
        //relative pos coordinates 
        rel_p1x= 0;
        rel_p1y= 0;
        rel_p2x= 0;
        rel_p2y= 0;
        
        winH= 0;
        winW= 0;
        
        minX= 0;
        maxX= 0;
        minY= 0;
        maxY= 0;
        
        minX= 0;
        maxX= 0;
        minY= 0;
        maxY= 0;
        newMinX= 0;
        newMaxX= 0;
        newMinY= 0;
        newMaxY= 0;
        rangeX= 0;
        rangeY= 0;
        newRangeX= 0;
        newRangeY= 0;

    }
    
    // this is used for hover effects 
    public void setQuery(String q1, String q2) {
    	colX = q1;
    	colY = q2;
    	dots.setCols(q1,q2);
//    	System.out.println(colX+""+colY);
    }
	public void clearMap() {
		System.out.println("clearMap ran");
//		System.out.println("Relative data is: "+ relativeScatterData.size());
		if(!relativeScatterData.isEmpty()) {
//			scatterData.clear();
			relativeScatterData.clear();
			System.out.println("I cleared the data");
			System.out.println("Relative data is: "+ relativeScatterData.size());
			repaint();
		}else {
			System.out.println("The data is empty");
		}
//		if(!relativeScatterData.isEmpty()&&!scatterData.isEmpty()) {
//			relativeScatterData.clear();
//		}
		
	}

    public void setText(String t) {
        textToDisplay = t;
        repaint();
    }
    
    private void drawLine(Graphics g) {
    	
    	int w = getWidth();
    	int h = getHeight();
        // draw the vertical line on the left
        int xLine =(int)(w*.05);
        int yLine =(int)(h*.96);
        // vertical line;
        g.drawLine(xLine, 0, xLine, yLine);
//        repaint();
        //horizontal line; 
        g.drawLine(xLine,yLine,w,yLine);
    }
    
    private void drawAxVals(Graphics g) {
    	
    	int w = getWidth();
    	int h = getHeight();
        // draw the vertical line on the left
        int xLine =(int)(w*.05);
        int yLine =(int)(h*.96);
        // vertical line;
        g.drawLine(xLine, 0, xLine, yLine);
        repaint();
        //horizontal line; 
        g.drawLine(xLine,yLine,w,yLine);
    }

    public void setData(Map<String, Double> acacia) {
        data = acacia;
        var allValues = data.values();
        double max = 0;
        for (var kaipo : allValues) {
            if (kaipo > max) {
                max = kaipo;
            }
        }
        for (var key : data.keySet()) {
            relativeData.put(key, data.get(key) / max);
        }
        repaint();
    }

    public void setData(List<Point2D> acacia) {
//    	relativeScatterData.clear();

        double maxX = 0;
        double maxY = 0;
        double adjuster = .95;
        for (var kaipo : acacia) {
            if (kaipo.getX() > maxX) {
                maxX = kaipo.getX();
                xValRev =kaipo.getX()/adjuster;
                
            }
            if (kaipo.getY() > maxY) {
                maxY = kaipo.getY();
                yValRev = kaipo.getY()/adjuster;
            }
        }
        for (var kaipo : acacia) {
            var gilmo = new Point2D.Double(kaipo.getX() / maxX*adjuster, kaipo.getY() / maxY*adjuster);
            relativeScatterData.add(gilmo);
        }
        repaint();
    }
    
    private void resetDotsPos(int ht, int wd) {
    	if(startH!=ht||startW!=wd) {
    		dots.resetList();
    	}
    }


    @Override
    public void paintComponent(Graphics g1) {
        Graphics2D g = (Graphics2D)g1;
       

        

        //draw blank background
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        //render visualization
        g.setColor(Color.BLACK);
        g.drawString(textToDisplay, 10, 20);

        final int h = getHeight();
        final int w = getWidth();
        winH=h;
        winW=w;
        resetDotsPos(h,w);
//		System.out.println("Relative data before printing is: "+ relativeScatterData.size());
		int i = 0;
        for (var myData : relativeScatterData) {
            double x = (myData.getX() * w);
            double y = (h - (myData.getY() * h));
            xCoords[i]=x;
            yCoords[i]=y;
            
            
            
            //*********************************need to clear********************************
//            xCoords.add(x);
//            yCoords.add(y);
            
            
            
//            g.fillOval(x, y, 5, 5);
            double xVal = myData.getX()*xValRev;
            double yVal = myData.getY()*yValRev;
//          System.out.println("From the Vis "+xVal+" "+yVal);
//          if(xVal == 0 && yVal==0) {
//              System.out.println("***************Printing the coords of 0: "+x+" "+y);
//      
//
//          }

            dots.newDot(x, y, xVal, yVal);
//            System.out.println("Printing x and y pos: "+x+" y: "+y);
            i++;
        }
//        System.out.println("**********end******************");
//        Collections.sort(xCoords); 
//        Collections.sort(yCoords); 
        // sort the arrray
        Arrays.sort(xCoords);
        Arrays.sort(yCoords);
        
        //this draws the line 
        drawLine(g);
        dots.draw(g);
        
        

        if (box != null) {
            g.setColor(Color.BLUE);
            g.draw(box);
        }
        
//        System.out.println("The newMinx is "+ newMinX+"which shouldnt be 0");
        zoomAux();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        corner = new Point(e.getX(), e.getY());
        //create a new rectangle anchored at "corner"
        p1x=e.getX();
        p1y=e.getY();
        
        rel_p1x=p1x/winW;
        rel_p1y=p1y/winH;
        box = new Rectangle(corner);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        box = null;
        p2x = e.getX();
        p2y = e.getY();
        
        rel_p2x=p2x/winW;
        rel_p2y=p2y/winH;
        
        
        
        
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        box.setFrameFromDiagonal(corner.x, corner.y, x, y);
        zoom();
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        String s =dots.attributes(x,y);
        setToolTipText(s);
    }
    
    

    private void zoom() {
    	// x coordinates
    	minX = xCoords[0];
    	maxX = xCoords[xCoords.length-1];
    	rangeX =maxX-minX;
    	newMinX = (rel_p1x*rangeX)+minX;
    	newMaxX = (rel_p2x*rangeX)+maxX;
    	newRangeX=newMaxX-newMinX;
    
    	//y coordinates
    	minY = yCoords[0];
    	maxY = yCoords[yCoords.length-1];
    	rangeY = maxY-minY;
    	newMinY = (rel_p1y*rangeY)+minY;
    	newMaxY = (rel_p2y*rangeY)+maxY;
    	newRangeY=newMaxY-newMinY;

    	
    }
    
    private void zoomAux() {
    	if(newMinX!=0) {
//    		for(var sd : relativeScatterData) {
////        		double newX = 
//    		}
    		int i=0;
    		for (var myData : relativeScatterData) {
                double x = (myData.getX() * winW);
                double y = (winH - (myData.getY() * winH));
                xCoords[i]=x;
                yCoords[i]=y;
                
                
                
                //*********************************need to clear********************************
//                xCoords.add(x);
//                yCoords.add(y);
                
                
                
//                g.fillOval(x, y, 5, 5);
                double xVal = myData.getX()*xValRev;
                double yVal = myData.getY()*yValRev;
//              System.out.println("From the Vis "+xVal+" "+yVal);
//              if(xVal == 0 && yVal==0) {
//                  System.out.println("***************Printing the coords of 0: "+x+" "+y);
//          
    //
//              }

                dots.newDot(x, y, xVal, yVal);
//                System.out.println("Printing x and y pos: "+x+" y: "+y);
                i++;
            }
    		
    		
    	}
    }
}
