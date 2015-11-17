package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
import javax.swing.JTextField;

import model.Game;

public class GraphicalView extends JFrame implements Observer
{

    public static void main(String[] args)
    {
        GraphicalView window = new GraphicalView();
        window.setVisible(true);
    }

    private Game game;
    private Image map;
    private MapPanel mapPanel;
    private JButton addArmy;
    private JTextArea gameInfo;
    private JTextField console;
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
        int imgWidth = 1600 * 3 / 4;
        int imgHeight = 1062 * 3 / 4;
        map = map.getScaledInstance(imgWidth, imgHeight,
                java.awt.Image.SCALE_SMOOTH);
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
        gameInfo = new JTextArea();
        gameInfo.setSize(new Dimension(screensize.width - imgWidth, imgHeight));
        gameInfo.setLocation(imgWidth, 0);
        console = new JTextField();
        console.setSize(new Dimension(screensize.width - imgWidth, 30));
        console.setLocation(imgWidth, imgHeight);
        console.addActionListener(new ConsoleListener());
        this.add(mapPanel);
        this.add(gameInfo);
        this.add(console);
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
        gameInfo.setText((String) arg); // make sure to send a string whenever
                                        // you update to update the game
                                        // information text area.
    }

    private class MapPanel extends JPanel
    {
        public MapPanel()
        {

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

    private class ConsoleListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e)
        {// TODO: Implement this method
            /*
             * This happens whenever enter button is pressed in the JTextField
             * for the console. I'm not sure what you guys want here since it
             * has to send messages to Game.java
             */

        }

    }
}
