package view;

import java.awt.Color;
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
import java.util.HashMap;
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

import model.Game;
import model.Map;
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
    private Map gameMap;
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
    private Territory countryFrom;
    private Territory countryTo;
    private HashMap<String,JButton> buttons;
    public GraphicalView()
    {
        buttons = new HashMap<>();
        gameMap = new Map();
        mainMenu();
        this.setVisible(false);
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
        attack.setLocation(100, 100);
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
        setMapButtons();
        this.add(addArmy);
        this.add(mapPanel);
        this.add(gameInfo);
        this.add(console);
        this.add(attack);
        
        repaint();
    }
    private void setMapButtons(){
        ActionListener mal = new MapButtonListener();
        JButton na1 = new JButton("");
        JButton na2 = new JButton("");
        JButton na3 = new JButton("");
        JButton na4 = new JButton("");
        JButton na5 = new JButton("");
        JButton na6 = new JButton("");
        JButton na7 = new JButton("");
        JButton na8 = new JButton("");
        JButton na9 = new JButton("");

        JButton sa1 = new JButton("");
        JButton sa2 = new JButton("");
        JButton sa3 = new JButton("");
        JButton sa4 = new JButton("");

        JButton eu1 = new JButton("");
        JButton eu2 = new JButton("");
        JButton eu3 = new JButton("");
        JButton eu4 = new JButton("");
        JButton eu5 = new JButton("");
        JButton eu6 = new JButton("");
        JButton eu7 = new JButton("");

        JButton af1 = new JButton("");
        JButton af2 = new JButton("");
        JButton af3 = new JButton("");
        JButton af4 = new JButton("");
        JButton af5 = new JButton("");
        JButton af6 = new JButton("");

        JButton as1 = new JButton("");
        JButton as2 = new JButton("");
        JButton as3 = new JButton("");
        JButton as4 = new JButton("");
        JButton as5 = new JButton("");
        JButton as6 = new JButton("");
        JButton as7 = new JButton("");
        JButton as8 = new JButton("");
        JButton as9 = new JButton("");
        JButton as10 = new JButton("");
        JButton as11 = new JButton("");
        JButton as12 = new JButton("");

        JButton ar1 = new JButton("");
        JButton ar2 = new JButton("");
        JButton ar3 = new JButton("");
        JButton ar4 = new JButton("");
        na1.setSize(30,30);
        na2.setSize(30,30);
        na3.setSize(30,30);
        na4.setSize(30,30);
        na5.setSize(30,30);
        na6.setSize(30,30);
        na7.setSize(30,30);
        na8.setSize(30,30);
        na9.setSize(30,30);

        sa1.setSize(30,30);
        sa2.setSize(30,30);
        sa3.setSize(30,30);
        sa4.setSize(30,30);

        eu1.setSize(30,30);
        eu2.setSize(30,30);
        eu3.setSize(30,30);
        eu4.setSize(30,30);
        eu5.setSize(30,30);
        eu6.setSize(30,30);
        eu7.setSize(30,30);

        af1.setSize(30,30);
        af2.setSize(30,30);
        af3.setSize(30,30);
        af4.setSize(30,30);
        af5.setSize(30,30);
        af6.setSize(30,30);

        as1.setSize(30,30);
        as2.setSize(30,30);
        as3.setSize(30,30);
        as4.setSize(30,30);
        as5.setSize(30,30);
        as6.setSize(30,30);
        as7.setSize(30,30);
        as8.setSize(30,30);
        as9.setSize(30,30);
        as10.setSize(30,30);
        as11.setSize(30,30);
        as12.setSize(30,30);

        ar1.setSize(30,30);
        ar2.setSize(30,30);
        ar3.setSize(30,30);
        ar4.setSize(30,30);
        
        na1.setLocation(80, 150);
        na2.setLocation(170,150);
        na3.setLocation(400, 140);
        na4.setLocation(175, 250);
        na5.setLocation(240, 235);
        na6.setLocation(315, 275);
        na7.setLocation(175, 345);
        na8.setLocation(275, 360);
        na9.setLocation(190, 385);

        sa1.setLocation(270, 495);
        sa2.setLocation(300, 590);
        sa3.setLocation(360, 560);
        sa4.setLocation(300, 680);

        eu1.setLocation(500, 170);
        eu2.setLocation(560, 205);
        eu3.setLocation(685, 270);
        eu4.setLocation(440, 270);
        eu5.setLocation(570, 290);
        eu6.setLocation(510, 360);
        eu7.setLocation(600, 390);

        af1.setLocation(510, 480);
        af2.setLocation(580, 480);
        af3.setLocation(650, 540);
        af4.setLocation(630, 630);
        af5.setLocation(630, 720);
        af6.setLocation(750, 720);

        as1.setLocation(850, 205);
        as2.setLocation(930, 160);
        as3.setLocation(1000, 165);
        as4.setLocation(795,260);
        as5.setLocation(920, 265);
        as6.setLocation(770, 360);
        as7.setLocation(925, 335);
        as8.setLocation(1070, 300);
        as9.setLocation(910, 410);
        as10.setLocation(700, 460);
        as11.setLocation(830, 460);
        as12.setLocation(935, 495);

        ar1.setLocation(870, 600);
        ar2.setLocation(1050, 555);
        ar3.setLocation(990, 660);
        ar4.setLocation(1020, 660);
        
        na1.setActionCommand("na1");
        na2.setActionCommand("na2");
        na3.setActionCommand("na3");
        na4.setActionCommand("na4");
        na5.setActionCommand("na5");
        na6.setActionCommand("na6");
        na7.setActionCommand("na7");
        na8.setActionCommand("na8");
        na9.setActionCommand("na9");

        sa1.setActionCommand("sa1");
        sa2.setActionCommand("sa2");
        sa3.setActionCommand("sa3");
        sa4.setActionCommand("sa4");

        eu1.setActionCommand("eu1");
        eu2.setActionCommand("eu2");
        eu3.setActionCommand("eu3");
        eu4.setActionCommand("eu4");
        eu5.setActionCommand("eu5");
        eu6.setActionCommand("eu6");
        eu7.setActionCommand("eu7");

        af1.setActionCommand("af1");
        af2.setActionCommand("af2");
        af3.setActionCommand("af3");
        af4.setActionCommand("af4");
        af5.setActionCommand("af5");
        af6.setActionCommand("af6");

        as1.setActionCommand("as1");
        as2.setActionCommand("as2");
        as3.setActionCommand("as3");
        as4.setActionCommand("as4");
        as5.setActionCommand("as5");
        as6.setActionCommand("as6");
        as7.setActionCommand("as7");
        as8.setActionCommand("as8");
        as9.setActionCommand("as9");
        as10.setActionCommand("as10");
        as11.setActionCommand("as11");
   
        ar1.setActionCommand("ar1");
        ar2.setActionCommand("ar2");
        ar3.setActionCommand("ar3");
        ar4.setActionCommand("ar4");
        
        buttons.put("na1",na1);
        buttons.put("na2",na2);
        buttons.put("na3",na3);
        buttons.put("na4",na4);
        buttons.put("na5",na5);
        buttons.put("na6",na6);
        buttons.put("na7",na7);
        buttons.put("na8",na8);
        buttons.put("na9",na9);

        buttons.put("sa1",sa1);
        buttons.put("sa2",sa2);
        buttons.put("sa3",sa3);
        buttons.put("sa4",sa4);

        buttons.put("eu1",eu1);
        buttons.put("eu2",eu2);
        buttons.put("eu3",eu3);
        buttons.put("eu4",eu4);
        buttons.put("eu5",eu5);
        buttons.put("eu6",eu6);
        buttons.put("eu7",eu7);

        buttons.put("af1",af1);
        buttons.put("af2",af2);
        buttons.put("af3",af3);
        buttons.put("af4",af4);
        buttons.put("af5",af5);
        buttons.put("af6",af6);

        buttons.put("as1",as1);
        buttons.put("as2",as2);
        buttons.put("as3",as3);
        buttons.put("as4",as4);
        buttons.put("as5",as5);
        buttons.put("as6",as6);
        buttons.put("as7",as7);
        buttons.put("as8",as8);
        buttons.put("as9",as9);
        buttons.put("as10",as10);
        buttons.put("as11",as11);
        buttons.put("as12",as12);

        buttons.put("ar1",ar1);
        buttons.put("ar2",ar2);
        buttons.put("ar3",ar3);
        buttons.put("ar4",ar4);
        
        na1.addActionListener(mal);
        na2.addActionListener(mal);
        na3.addActionListener(mal);
        na4.addActionListener(mal);
        na5.addActionListener(mal);
        na6.addActionListener(mal);
        na7.addActionListener(mal);
        na8.addActionListener(mal);
        na9.addActionListener(mal);

        sa1.addActionListener(mal);
        sa2.addActionListener(mal);
        sa3.addActionListener(mal);
        sa4.addActionListener(mal);

        eu1.addActionListener(mal);
        eu2.addActionListener(mal);
        eu3.addActionListener(mal);
        eu4.addActionListener(mal);
        eu5.addActionListener(mal);
        eu6.addActionListener(mal);
        eu7.addActionListener(mal);

        af1.addActionListener(mal);
        af2.addActionListener(mal);
        af3.addActionListener(mal);
        af4.addActionListener(mal);
        af5.addActionListener(mal);
        af6.addActionListener(mal);

        as1.addActionListener(mal);
        as2.addActionListener(mal);
        as3.addActionListener(mal);
        as4.addActionListener(mal);
        as5.addActionListener(mal);
        as6.addActionListener(mal);
        as7.addActionListener(mal);
        as8.addActionListener(mal);
        as9.addActionListener(mal);
        as10.addActionListener(mal);
        as11.addActionListener(mal);
        as12.addActionListener(mal);

        ar1.addActionListener(mal);
        ar2.addActionListener(mal);
        ar3.addActionListener(mal);
        ar4.addActionListener(mal);

        add(na1);
        add(na2);
        add(na3);
        add(na4);
        add(na5);
        add(na6);
        add(na7);
        add(na8);
        add(na9);

        add(sa1);
        add(sa2);
        add(sa3);
        add(sa4);

        add(eu1);
        add(eu2);
        add(eu3);
        add(eu4);
        add(eu5);
        add(eu6);
        add(eu7);

        add(af1);
        add(af2);
        add(af3);
        add(af4);
        add(af5);
        add(af6);

        add(as1);
        add(as2);
        add(as3);
        add(as4);
        add(as5);
        add(as6);
        add(as7);
        add(as8);
        add(as9);
        add(as10);
        add(as11);
        add(as12);

        add(ar1);
        add(ar2);
        add(ar3);
        add(ar4);
    }
    private void mainMenu(){
        JFrame menu = new JFrame("Risk - Main Menu - Team Rocket");
        menu.setSize(420, 420);
        menu.setLocation(200,200);
        menu.setLayout(null);
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton newGame = new JButton("New Game");
        JButton loadGame = new JButton("Load Game");
                
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
                //game.beginGame();
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
    private class MapButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e)
        {
           for (Territory t: gameMap.getTerritories()){
               if (t.getCountry().getButtonTitle().equals(e.getActionCommand())){
                   if (true){
                   countryFrom = t;
                   System.out.println("countryFrom: " + t.getCountry().toString());
                   }
                   if (true){
                       countryTo = t;
                       System.out.println("countryTo: " + t.getCountry().toString());
                   }
               }
           }
            
        }
        
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
                        System.out.println(t.getCountry().getButtonTitle());
                        buttons.get(t.getCountry().getButtonTitle()).setBackground(player.getColor());  
                        buttons.get(t.getCountry().getButtonTitle()).setForeground(Color.BLACK);     
                        buttons.get(t.getCountry().getButtonTitle()).setText(String.valueOf(t.getArmies()));
                        
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
