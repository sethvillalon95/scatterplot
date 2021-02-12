import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Main extends JFrame {

    private Vis mainPanel;
    String columnX;
    String columnY;

    public Main() {

        JMenuBar mb = setupMenu();
        setJMenuBar(mb);

        mainPanel = new Vis();
        setContentPane(mainPanel);

        setSize(800,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Put the title of your program here");
        setVisible(true);
        
    }

    //select count(*) from derbyDB
    private int runSimpleCountQuery(String q) {
        try {
            Connection c = DriverManager.getConnection("jdbc:derby:scatter");
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(q);
            rs.next();
            int count = rs.getInt(1);
            return count;
        } catch (SQLException e) {
            System.out.println("could not connect to Derby!");
            return 0;
        }
    }

    private Map<String, Double> performTwoColumnQuery(String q) {
        Map<String, Double> results = new HashMap<>();
        try {
            Connection c = DriverManager.getConnection("jdbc:derby:scatterPlot");
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(q);
            while (rs.next()) {
                double num = rs.getDouble(1);
                String label = rs.getString(2);
                results.put(label, num);
            }
        } catch (SQLException e) {
            System.out.println("could not connect to Derby!");
        }
        return results;
    }

    private List<Point2D> performTwoNumberQuery(String q) {
        List<Point2D> results = new ArrayList<>();
        try {
            Connection c = DriverManager.getConnection("jdbc:derby:scatterPlot");
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(q);
            while (rs.next()) {
                double x = rs.getDouble(1);
                double y = rs.getDouble(2);
//                System.out.println("From main.java"+x+" "+y);
                var guillaume = new Point2D.Double(x,y);
                results.add(guillaume);
//                System.out.println("*********************************************");
            }
        } catch (SQLException e) {
            System.out.println("could not connect to Derby!");
        }
        return results;
    }

    private JMenuBar setupMenu() {
        //instantiate menubar, menus, and menu options
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem item1 = new JMenuItem("Item 1");
        JMenuItem item2 = new JMenuItem("Item 2");
        JMenuItem item3 = new JMenuItem("Credits attempted vs. Credits passed");
        JMenuItem item4 = new JMenuItem("Credits attempted vs. GPA");
        JMenuItem item5 = new JMenuItem("Credits passed vs. GPA");
        JMenuItem item6 = new JMenuItem("Age vs GPA");
        JMenuItem item7 = new JMenuItem("Your choice");

        item3.addActionListener(e -> {
        	mainPanel.clearMap();
        	columnX="CREDITS_ATTEMPTED";
        	columnY="CREDITS_PASSED";
            var sethQuery = "SELECT CREDITS_ATTEMPTED,CREDITS_PASSED from cis2019";
            var gilmo = performTwoNumberQuery(sethQuery);
            mainPanel.setData(gilmo);
            mainPanel.setQuery(columnY, columnX);
        });


        //setup action listeners
        item1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Just clicked menu item 1");
                int gilmo = runSimpleCountQuery("SELECT COUNT(*) FROM cis2019");
                System.out.println("I found " + gilmo + " rows in the table.");
//                mainPanel.setText("I found " + gilmo + " rows in the table.");
            }
        });
        item2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Just clicked menu item 2");
                var lauren = performTwoColumnQuery("select count(*), home from cis2019 group by home");
                for (var k : lauren.keySet()) {
                    double num = lauren.get(k);
                    System.out.println(k + " : " + num);
                }
                mainPanel.setData(lauren);
            }
        });
        
        //Credits attempted vs. GPA
        item4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	mainPanel.clearMap();
            	columnX="CREDITS_ATTEMPTED";
            	columnY="GPA";
                var sethQuery = "SELECT CREDITS_ATTEMPTED,GPA from cis2019";
                var gilmo = performTwoNumberQuery(sethQuery);
                mainPanel.setData(gilmo);
                mainPanel.setQuery(columnY, columnX);

            }
        });
        
        //Credits passed vs. GPA
        item5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	mainPanel.clearMap();
            	columnX="CREDITS_ATTEMPTED";
            	columnY="GPA";
                var sethQuery = "SELECT CREDITS_PASSED,GPA from cis2019";
                var gilmo = performTwoNumberQuery(sethQuery);
                mainPanel.setData(gilmo);
                mainPanel.setQuery(columnY, columnX);

            }
        });
        
        
        //Age vs GPA
        item6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	mainPanel.clearMap();
            	columnX="AGE";
            	columnY="GPA";
                var sethQuery = "SELECT AGE,GPA from cis2019";
                var gilmo = performTwoNumberQuery(sethQuery);
                mainPanel.setData(gilmo); 
                mainPanel.setQuery(columnY, columnX);
            }
        });
        
        
        //# Credits Passed vs AGE
        item7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	mainPanel.clearMap();
            	columnX="GPA";
            	columnY="AGE";
                var sethQuery = "SELECT CREDITS_ATTEMPTED,GPA from cis2019";
                var gilmo = performTwoNumberQuery(sethQuery);
                mainPanel.setData(gilmo);
                mainPanel.setQuery(columnY, columnX);

            }
        });

        //now hook them all together
        fileMenu.add(item1);
        fileMenu.add(item2);
        fileMenu.add(item3);
        fileMenu.add(item4);
        fileMenu.add(item5);
        fileMenu.add(item6);
        fileMenu.add(item7); 
        
        menuBar.add(fileMenu);

        return menuBar;
    }

    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }
}