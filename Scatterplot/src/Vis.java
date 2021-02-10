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
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.swing.JPanel;


public class Vis extends JPanel implements MouseListener, MouseMotionListener {

    private String textToDisplay;
    private Map<String, Double> data;
    private Map<String, Double> relativeData;
    private Ellipse2D.Double seth;
    private Rectangle box;
    private Point corner;
    private List<Point2D> scatterData;
    private List<Point2D> relativeScatterData;
    private AllDots dots;
    String colX;
    String colY;
    // the revversers are to cancel out the max value to get the raw value from the scatterData
    double xValRev,yValRev;

    public Vis() {
        super();
        textToDisplay = "There's nothing to see here.";
        relativeData = new HashMap<>();
        relativeScatterData = new ArrayList<>();
        seth = new Ellipse2D.Double(50, 100, 40, 40);
        addMouseListener(this);
        addMouseMotionListener(this);
        box = null;
        dots = new AllDots();
        colX="";
        colY="";
        xValRev=0;
        yValRev=0;

    }
    
    // this is used for hover effects 
    public void setQuery(String q1, String q2) {
    	colX = q1;
    	colY = q2;
    	dots.setCols(q1,q2);
//    	System.out.println(colX+""+colY);
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
        double maxX = 0;
        double maxY = 0;
        for (var kaipo : acacia) {
            if (kaipo.getX() > maxX) {
                maxX = kaipo.getX();
                xValRev =kaipo.getX();
                
            }
            if (kaipo.getY() > maxY) {
                maxY = kaipo.getY();
                yValRev = kaipo.getY();
            }
        }
        for (var kaipo : acacia) {
            var gilmo = new Point2D.Double(kaipo.getX() / maxX, kaipo.getY() / maxY);
            relativeScatterData.add(gilmo);
        }
        repaint();
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
        for (var data : relativeScatterData) {
            double x = (data.getX() * w*.9);
            double y = (h - (data.getY() * h*.9));
//            g.fillOval(x, y, 5, 5);
            double xVal = data.getX()*xValRev;
            double yVal = data.getX()*yValRev;
            dots.newDot(x, y, xVal, yVal);
//            System.out.println("Printing x and y pos: "+x+" y: "+y);
        }
        //this draws the line 
        drawLine(g);
        dots.draw(g);

/*        int y=h, x;
        int howManyBars = relativeData.keySet().size();
        int[] kaipoY = new int[howManyBars];
        int[] kaipoX = new int[howManyBars];
        int xSpacing = getWidth() / (howManyBars+1);
        x = xSpacing;
        int i=0;
        for (var jerico : relativeData.keySet()) {
            double barWidth = h * relativeData.get(jerico);
            //uncomment this line for bar charts
            //g.drawLine(x,y, x, y-(int)barWidth);
            kaipoY[i] = y-(int)barWidth;
            kaipoX[i] = x;
            x += xSpacing;
            i++;
        }
        //comment out this line for bar charts
        g.drawPolyline(kaipoX, kaipoY, howManyBars);

        g.setColor(Color.RED);
        g.fill(seth);
*/
//        g.fill(seth);
        if (box != null) {
            g.setColor(Color.BLUE);
            g.draw(box);
        }
 
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        corner = new Point(e.getX(), e.getY());
        //create a new rectangle anchored at "corner"
        box = new Rectangle(corner);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        box = null;
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
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (seth.contains(x,y)) {
            //System.out.println("Hello, Seth's ellipse!");
            setToolTipText("Hello from " + x + "," + y);
        } else {
            setToolTipText(null);
        }
    }
}
