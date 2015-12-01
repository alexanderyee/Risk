package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.sun.media.jfxmedia.events.PlayerStateEvent.PlayerState;

import model.Game;
import model.Player;
import model.Territory;

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
    private JButton addArmy = new JButton("addArmy");

    private JButton attack = new JButton("Attack");
    private JTextArea gameInfo;
    private JTextField console;
    private boolean newGameFlag = false;
    private int X, Y;
    private final int imgWidth = (int) (756 * 1.5);
    private final int imgHeight = (int) (554 * 1.5);
    private JMenuBar menuBar;
    private JMenu gameMenu;
    private JMenuItem newGameOption;
    private JMenuItem loadGameOption;
    private JMenu settings;
    private JRadioButtonMenuItem soundOption;
    private JMenuItem setNameOption;
    private JFrame newGameWindow;
    private int numPlayers;
    private int numHumans;

    public GraphicalView()
    {
        menuBar = new JMenuBar();
        setNewGameWindow();

        gameMenu = new JMenu("Game");
        newGameOption = new JMenuItem("New Game");
        newGameOption.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                newGameWindow.setVisible(true);
            }
        });
        loadGameOption = new JMenuItem("Load Game");
        settings = new JMenu("Settings");
        soundOption = new JRadioButtonMenuItem("Sound");
        setNameOption = new JMenuItem("Set current player name...");
        menuBar.add(gameMenu);
        gameMenu.add(newGameOption);
        gameMenu.add(loadGameOption);
        menuBar.add(settings);
        settings.add(soundOption);
        settings.add(setNameOption);
        setNameOption.addActionListener(new NameSetter());
        this.setJMenuBar(menuBar);
        try
        {
            map = ImageIO.read(new File("./images/riskMap5.png"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        map = map.getScaledInstance(imgWidth, imgHeight,
                java.awt.Image.SCALE_SMOOTH);
        this.setTitle("Risk - Team Rocket Industries");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screensize);
        setLayout(null);
        WindowListener wl = new Save();
        // this.addWindowListener(wl);
        mapPanel = new MapPanel();
        mapPanel.setLayout(null);
        mapPanel.setSize(new Dimension(imgWidth, imgHeight));
        mapPanel.setLocation(0, 0);
        gameInfo = new JTextArea("testing to see what happens");
        gameInfo.setSize(new Dimension(screensize.width - imgWidth, imgHeight));
        gameInfo.setLocation(imgWidth, 0);
        console = new JTextField();
        console.setSize(new Dimension(screensize.width - imgWidth, 30));
        console.setLocation(imgWidth, imgHeight);
        console.addActionListener(new ConsoleListener());
        attack.setSize(new Dimension(100, 30));
        attack.setLocation(0, 100);
        attack.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                game.attack();
                repaint();
                gameInfo.append("\n Attack updated");
            }

        });

        this.add(addArmy);
        this.add(mapPanel);
        this.add(gameInfo);
        this.add(console);
        this.add(attack);
        repaint();
    }

    private void setNewGameWindow()
    {
        newGameWindow = new JFrame("New Game Settings");
        newGameWindow.setSize(720, 480);
        newGameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newGameWindow.setLocation(100, 100);
        newGameWindow.setLayout(null);
        ButtonGroup numPlayersOption = new ButtonGroup();
        JLabel players = new JLabel("Players:");
        players.setSize(60, 20);
        players.setLocation(0, 0);
        JRadioButton two = new JRadioButton("2");
        two.setActionCommand("2");
        two.setLocation(0, 20);
        two.setSize(60, 20);
        two.setSelected(true);
        JRadioButton three = new JRadioButton("3");
        three.setActionCommand("3");
        three.setLocation(0, 40);
        three.setSize(60, 20);
        JRadioButton four = new JRadioButton("4");
        four.setActionCommand("4");
        four.setLocation(0, 60);
        four.setSize(60, 20);
        JRadioButton five = new JRadioButton("5");
        five.setActionCommand("5");
        five.setLocation(0, 80);
        five.setSize(60, 20);
        JRadioButton six = new JRadioButton("6");
        six.setActionCommand("6");
        six.setLocation(0, 100);
        six.setSize(60, 20);
        numPlayersOption.add(two);
        numPlayersOption.add(three);
        numPlayersOption.add(four);
        numPlayersOption.add(five);
        numPlayersOption.add(six);
        newGameWindow.add(two);
        newGameWindow.add(three);
        newGameWindow.add(four);
        newGameWindow.add(five);
        newGameWindow.add(six);
        newGameWindow.add(players);
        ActionListener npl = new NumPlayersListener();
        two.addActionListener(npl);
        three.addActionListener(npl);
        four.addActionListener(npl);
        five.addActionListener(npl);
        six.addActionListener(npl);

        ButtonGroup numHumansOption = new ButtonGroup();
        JLabel humans = new JLabel("Humans:");
        humans.setSize(80, 20);
        humans.setLocation(80, 0);
        JRadioButton hZero = new JRadioButton("0");
        hZero.setActionCommand("0");
        hZero.setLocation(80, 20);
        hZero.setSize(80, 20);
        JRadioButton hOne = new JRadioButton("1");
        hOne.setActionCommand("1");
        hOne.setLocation(80, 40);
        hOne.setSize(80, 20);
        hOne.setSelected(true);
        JRadioButton hTwo = new JRadioButton("2");
        hTwo.setActionCommand("2");
        hTwo.setLocation(80, 60);
        hTwo.setSize(80, 20);
        JRadioButton hThree = new JRadioButton("3");
        hThree.setActionCommand("3");
        hThree.setLocation(80, 80);
        hThree.setSize(80, 20);
        JRadioButton hFour = new JRadioButton("4");
        hFour.setActionCommand("4");
        hFour.setLocation(80, 100);
        hFour.setSize(80, 20);
        JRadioButton hFive = new JRadioButton("5");
        hFive.setActionCommand("5");
        hFive.setLocation(80, 120);
        hFive.setSize(80, 20);
        JRadioButton hSix = new JRadioButton("6");
        hSix.setActionCommand("6");
        hSix.setLocation(80, 140);
        hSix.setSize(80, 20);
        ActionListener nhl = new NumHumansListener();
        hZero.addActionListener(nhl);
        hOne.addActionListener(nhl);
        hTwo.addActionListener(nhl);
        hThree.addActionListener(nhl);
        hFour.addActionListener(nhl);
        hFive.addActionListener(nhl);
        hSix.addActionListener(nhl);
        numHumansOption.add(hZero);
        numHumansOption.add(hOne);
        numHumansOption.add(hTwo);
        numHumansOption.add(hThree);
        numHumansOption.add(hFour);
        numHumansOption.add(hFive);
        numHumansOption.add(hSix);
        newGameWindow.add(hZero);
        newGameWindow.add(hOne);
        newGameWindow.add(hTwo);
        newGameWindow.add(hThree);
        newGameWindow.add(hFour);
        newGameWindow.add(hFive);
        newGameWindow.add(hSix);
        newGameWindow.add(humans);

        JButton startGame = new JButton("Start Game");
        startGame.setSize(80, 20);
        startGame.setLocation(340, 440);
        startGame.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                game = new Game(numPlayers - numHumans, numHumans);
                newGameFlag = true;
                for (Player player : game.getPlayers())
                {
                    while (player.getArmies() > 0)
                    {
                        for (int j = 1; j <= player.getTerritories()
                                .size(); j++)
                        {
                            if (player.getArmies() == 0) break;
                            player.addArmy(j);
                        }
                    }
                }
                game.beginGame();
                gameInfo.setText(
                        "Player 1 territories \n" + game.getTerritories(0));
                gameInfo.append(
                        "Player 2 territories \n" + game.getTerritories(1));
                gameInfo.append(
                        "Player 3 territories \n" + game.getTerritories(2));
                gameInfo.append(
                        "Player 4 territories \n" + game.getTerritories(3));
                repaint();
            }

        }

        );
        newGameWindow.add(startGame);
    }

    private class NumPlayersListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e)
        {
            numPlayers = Integer.parseInt(e.getActionCommand());

        }

    }

    private class NumHumansListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e)
        {
            numHumans = Integer.parseInt(e.getActionCommand());
            if (numHumans > numPlayers) numHumans = numPlayers;

        }

    }

    private class NameSetter implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e)
        {

            JFrame jf = new JFrame();
            jf.setSize(400, 200);
            jf.setLocation(0, 0);
            jf.setLayout(new FlowLayout());
            JLabel msg = new JLabel("Please set name for: "
                    + game.getPlayers().get(game.getCurrPID()).getPlayerName());
            JTextField nameInput = new JTextField();
            nameInput.setSize(150, 20);
            jf.add(msg);
            jf.add(nameInput);
            jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            jf.addWindowListener(new WindowListener()
            {

                @Override
                public void windowOpened(WindowEvent e)
                {
                    // TODO Auto-generated method stub

                }

                @Override
                public void windowClosing(WindowEvent e)
                {
                    game.getPlayers().get(game.getCurrPID())
                            .setPlayerName(nameInput.getText());
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

            });
            jf.setVisible(true);
        }

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
                    input.close();
                    fis.close();
                } // End try.....
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
            JOptionPane jop = new JOptionPane();
            jop.setMessage("Save?");
            int n = jop.showConfirmDialog(null, "Save?", "Save State",
                    JOptionPane.YES_NO_CANCEL_OPTION);

            if (n == jop.YES_OPTION)
            {
                try
                {
                    FileOutputStream fos = new FileOutputStream("RiskSave");
                    ObjectOutputStream outFile = new ObjectOutputStream(fos);
                    outFile.writeObject(game);
                    outFile.close();
                    fos.close();
                }
                catch (IOException e1)
                {
                    System.out.println("Save failed");
                    e1.printStackTrace();
                }
                System.exit(0);
            }
            if (n == jop.NO_OPTION) System.exit(0);

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
            g2.drawImage(map, 0, 0, null);
            // grid for map image
            /*
             * for (int i = 0; i < imgWidth; i += 30) { g2.setColor(Color.PINK);
             * g2.drawLine(0, i, imgWidth, i); g2.drawString(String.valueOf(i),
             * 0, i); } for (int i = 0; i < imgWidth; i += 30) { g2.drawLine(i,
             * 0, i, imgHeight); g2.drawString(String.valueOf(i), i, 10); }
             */
            if (newGameFlag)
            {
                g2.setFont(new Font("default", Font.BOLD, 16));
                for (Player player : game.getPlayers())
                {
                    g2.setColor(player.getColor());
                    for (Territory t : player.getTerritories())
                    {
                        g2.drawString(String.valueOf(t.getArmies()),
                                t.getPointX(), t.getPointY());
                    }
                }
            }
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
