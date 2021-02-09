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

    public Main() {

        JMenuBar mb = setupMenu();
        setJMenuBar(mb);

        mainPanel = new Vis();
        setContentPane(mainPanel);

        setSize(800,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Put the title of your program here");
        setVisible(true);
        
        Dot test = new Dot(1,2,3,4);
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
            Connection c = DriverManager.getConnection("jdbc:derby:scatter");
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
            Connection c = DriverManager.getConnection("jdbc:derby:scatter");
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(q);
            while (rs.next()) {
                double x = rs.getDouble(1);
                double y = rs.getDouble(2);
                var guillaume = new Point2D.Double(x,y);
                results.add(guillaume);
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
        JMenuItem item3 = new JMenuItem("Seth's scatterplot");
        JMenu subMenu = new JMenu("Submenu");
        JMenuItem item2 = new JMenuItem("Item 2");

        item3.addActionListener(e -> {
            var sethQuery = "SELECT CREDITS_ATTEMPTED, CREDITS_PASSED from cis";
            var gilmo = performTwoNumberQuery(sethQuery);
            mainPanel.setData(gilmo);
        });


        //setup action listeners
        item1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Just clicked menu item 1");
                int gilmo = runSimpleCountQuery("SELECT COUNT(*) FROM cis");
                System.out.println("I found " + gilmo + " rows in the table.");
                mainPanel.setText("I found " + gilmo + " rows in the table.");
            }
        });
        item2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Just clicked menu item 2");
                var lauren = performTwoColumnQuery("select count(*), home from cis group by home");
                for (var k : lauren.keySet()) {
                    double num = lauren.get(k);
                    System.out.println(k + " : " + num);
                }
                mainPanel.setData(lauren);
            }
        });

        //now hook them all together
        subMenu.add(item2);
        fileMenu.add(item1);
        fileMenu.add(item3);
        fileMenu.add(subMenu);
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