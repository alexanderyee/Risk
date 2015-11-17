package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import model.Game;

public class GraphicalView extends JFrame implements Observer
{

    public static void main(String[] args)
    {
        GraphicalView window = new GraphicalView();
        window.setVisible(true);
    }

    private Game game;
    private BufferedImage map;
    private MapPanel mapPanel;
    private JButton addArmy;
    private JTextArea gameInfo;
    private int X, Y;

    public GraphicalView()
    {
        try
        {
            map = ImageIO.read(new File("./images/map.jpg"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        int imgWidth = map.getWidth();
        int imgHeight = map.getHeight();
        this.setTitle("Risk - Team Rocket Industries");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screensize);
        setLayout(null);
        WindowListener wl = new Save();
        mapPanel = new MapPanel();
        mapPanel.setLayout(null);
        mapPanel.setSize(new Dimension(imgWidth, imgHeight));
        mapPanel.setLocation(0, 0);
       
        this.add(mapPanel);
        repaint();
    }

    private class Save implements WindowListener
    {

        @Override
        public void windowOpened(WindowEvent e)
        {
            JOptionPane jop = new JOptionPane();
            jop.setMessage("Start from earlier save?");
            int n = jop.showConfirmDialog(null, "Start from earlier save?",
                    "Save State", JOptionPane.YES_NO_OPTION);
            if (n == jop.YES_OPTION)
            {
                try
                {
                    FileInputStream fis = new FileInputStream("RiskSave");
                    ObjectInputStream input = new ObjectInputStream(fis);
                    game = new Game(3, 1); // need to add JOptionPane to this in
                                           // the future; for now, defaulted to
                                           // 3 bots and 1 human

                }
                catch (Exception ex)
                {
                    game = new Game(3, 1); // for now, same as try block...
                }
            }
            else
            {
                game = new Game(3, 1); // ^^^

            }

        }

        @Override
        public void windowClosing(WindowEvent e)
        {
            // TODO Auto-generated method stub

        }

        @Override
        public void windowClosed(WindowEvent e)
        {
            // TODO Auto-generated method stub

        }

        @Override
        public void windowIconified(WindowEvent e)
        {
            // TODO Auto-generated method stub

        }

        @Override
        public void windowDeiconified(WindowEvent e)
        {
            // TODO Auto-generated method stub

        }

        @Override
        public void windowActivated(WindowEvent e)
        {
            // TODO Auto-generated method stub

        }

        @Override
        public void windowDeactivated(WindowEvent e)
        {
            // TODO Auto-generated method stub

        }

    }

    @Override
    public void update(Observable o, Object arg)
    {
        repaint();
        gameInfo.setText(o.toString());
    }

    private class MapPanel extends JPanel
    {
        public MapPanel(){
          
        }
        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.drawImage(map, 0, 0, null); // will consider lower resolutions
                                           // and scroll panes

        }
    }
}
