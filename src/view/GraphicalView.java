package view;

import java.awt.Dimension;
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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import model.Card;
import model.Deck;
import model.Dice;
import model.EasyBot;
import model.HardCpu;
import model.Human;
import model.Map;
import model.MediumBot;
import model.Player;
import model.Territory;

public class GraphicalView extends JFrame implements Serializable
{

    public static void main(String[] args)
    {
        GraphicalView window = new GraphicalView();
        window.setVisible(true);
    }

    private Image map;
    private Image artillery;
    private Map gameMap;
    private MapPanel mapPanel;
    private JTextArea gameInfo;
    private JTextField console;
    private boolean newGameFlag = false;
    private final int imgWidth = (int) (756 * 1.5);
    private final int imgHeight = (int) (554 * 1.5);
    private JMenuBar menuBar;
    private JMenu gameMenu;
    private JMenuItem newGameOption;
    private JMenuItem loadGameOption;
    private JMenuItem changeDiff;
    private JMenu settings;
    private JRadioButtonMenuItem soundOption;
    private JMenuItem setNameOption;
    private JMenuItem getBonusInfo;
    private JMenuItem saveGameOption;
    private JFrame newGameWindow;
    private int numPlayers;
    private int numHumans;
    private Territory countryFrom;
    private Territory countryTo;
    private HashMap<String, JButton> buttons;
    boolean deployFlag = false;
    boolean attackFlag = false;
    boolean fortifyFlag = false;
    private int bonus;
    private Player curr;
    private JPanel mainMenu;
    private int turnsPlayed;
    private int roundsPlayed;
    private boolean gameOver;
    private int handsReedemed = 0;
    private int cardSetValue;
    private ArrayList<Player> players;
    private int currentPID;
    private JScrollPane scrollInfo;
    JPanel d = new DicePopUP();
    private Deck deck;
    ////// Sount stuff
    private static Clip clip;
    private CardSystemView cardView = new CardSystemView();
    private boolean dicePop = true;
    private boolean cardPop = true;
    private JLabel mainLabel = new JLabel("");

    public GraphicalView()
    {
        mainLabel.setLocation(0, this.getHeight()-70);
        mainLabel.setSize(200, 40);
        deck = new Deck();
        playIntro();
        buttons = new HashMap<>();
        gameMap = new Map();
        mainMenu();
        this.setVisible(false);
        menuBar = new JMenuBar();
        setNewGameWindow();
        this.addWindowListener(new Save());
        gameMenu = new JMenu("Game");
        newGameOption = new JMenuItem("New Game");
        newGameOption.addActionListener(new ActionListener()
        
        {

            @Override
            public void actionPerformed(ActionEvent arg0)
            {

                playClick("click");
                newGameWindow.setVisible(true);
            }
        });
        changeDiff = new JMenuItem("Change difficulty...");
        changeDiff.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                JOptionPane jop = new JOptionPane();
                String[] options = new String[players.size()];
                for (Player p : players)
                {
                    options[p.getPlayerID()] = p.getPlayerName();
                }
                int n = jop.showOptionDialog(null, "Change difficulty",
                        "Change player/difficulty for... ",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                        null, options, options[0]); // add another jop

                String[] options2 = { "Easy", "Medium", "Hard", "Human" };

                int n2 = jop.showOptionDialog(null, "Change difficulty",
                        "Change player/difficulty for" + options[n] + " to... ",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                        null, options2, options2[0]);
                switchDifficulty(players.get(n), options2[n2]); // this won't
                                                                // work if
                                                                // playerID ==
                                                                // index of
                                                                // Player in
                                                                // players
            }

        });
        loadGameOption = new JMenuItem("Load Game");
        loadGameOption.addActionListener(new LoadGameListener());
        settings = new JMenu("Settings");
        soundOption = new JRadioButtonMenuItem("Sound");
        soundOption.setSelected(clip.isActive());
        soundOption.addActionListener(new SoundToggleListener());
        getBonusInfo = new JMenuItem("Get bonus armies for continents");
        getBonusInfo.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                gameInfo.append("Bonus Armies for Complete Continent:\n"
                        + "N. America \t5\n" + "S. America \t2\n"
                        + "Africa \t3\n" + "Asia \t7\n" + "Europe \t5\n"
                        + "Australia \t2\n");
            }

        });
        setNameOption = new JMenuItem("Set current player name...");
        saveGameOption = new JMenuItem("Save Game");
        saveGameOption.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                // LMAOOOO SAVE DOESN'T WORK

            }
        });

        menuBar.add(gameMenu);
        gameMenu.add(newGameOption);
        gameMenu.add(loadGameOption);
        gameMenu.add(saveGameOption);
        gameMenu.add(changeDiff);
        gameMenu.add(getBonusInfo);
        menuBar.add(settings);
        settings.add(soundOption);
        settings.add(setNameOption);
        setNameOption.addActionListener(new NameSetter());
        this.setJMenuBar(menuBar);
        try
        {
            artillery = ImageIO.read(new File("./images/artillery.png"));
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
        mapPanel = new MapPanel();
        mapPanel.setLayout(null);
        mapPanel.setSize(new Dimension(imgWidth, imgHeight));
        mapPanel.setLocation(0, 0);
        gameInfo = new JTextArea();
        gameInfo.setSize(new Dimension(screensize.width - imgWidth, imgHeight));
        gameInfo.setLocation(imgWidth, 0);
        gameInfo.setEditable(false);
        scrollInfo = new JScrollPane(gameInfo,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        console = new JTextField();
        console.setSize(new Dimension(screensize.width - imgWidth, 30));
        console.setLocation(imgWidth, imgHeight);

        setMapButtons();
        // this.add(addArmy);
        this.add(mapPanel);
        this.add(gameInfo);
        // this.add(console);
        this.add(mainLabel);
        this.add(scrollInfo);
        scrollInfo.updateUI();
        repaint();
    }

    private void setMapButtons()
    {
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
        na1.setSize(40, 20);
        na2.setSize(30, 30);
        na3.setSize(30, 30);
        na4.setSize(30, 30);
        na5.setSize(30, 30);
        na6.setSize(30, 30);
        na7.setSize(30, 30);
        na8.setSize(30, 30);
        na9.setSize(30, 30);

        sa1.setSize(30, 30);
        sa2.setSize(30, 30);
        sa3.setSize(30, 30);
        sa4.setSize(30, 30);

        eu1.setSize(30, 30);
        eu2.setSize(30, 30);
        eu3.setSize(30, 30);
        eu4.setSize(30, 30);
        eu5.setSize(30, 30);
        eu6.setSize(30, 30);
        eu7.setSize(30, 30);

        af1.setSize(30, 30);
        af2.setSize(30, 30);
        af3.setSize(30, 30);
        af4.setSize(30, 30);
        af5.setSize(30, 30);
        af6.setSize(30, 30);

        as1.setSize(30, 30);
        as2.setSize(30, 30);
        as3.setSize(30, 30);
        as4.setSize(30, 30);
        as5.setSize(30, 30);
        as6.setSize(30, 30);
        as7.setSize(30, 30);
        as8.setSize(30, 30);
        as9.setSize(30, 30);
        as10.setSize(30, 30);
        as11.setSize(30, 30);
        as12.setSize(30, 30);

        ar1.setSize(30, 30);
        ar2.setSize(30, 30);
        ar3.setSize(30, 30);
        ar4.setSize(30, 30);

        na1.setLocation(80, 150);
        na2.setLocation(170, 150);
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
        as4.setLocation(795, 260);
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
        as12.setActionCommand("as12");

        ar1.setActionCommand("ar1");
        ar2.setActionCommand("ar2");
        ar3.setActionCommand("ar3");
        ar4.setActionCommand("ar4");

        buttons.put("na1", na1);
        buttons.put("na2", na2);
        buttons.put("na3", na3);
        buttons.put("na4", na4);
        buttons.put("na5", na5);
        buttons.put("na6", na6);
        buttons.put("na7", na7);
        buttons.put("na8", na8);
        buttons.put("na9", na9);

        buttons.put("sa1", sa1);
        buttons.put("sa2", sa2);
        buttons.put("sa3", sa3);
        buttons.put("sa4", sa4);

        buttons.put("eu1", eu1);
        buttons.put("eu2", eu2);
        buttons.put("eu3", eu3);
        buttons.put("eu4", eu4);
        buttons.put("eu5", eu5);
        buttons.put("eu6", eu6);
        buttons.put("eu7", eu7);

        buttons.put("af1", af1);
        buttons.put("af2", af2);
        buttons.put("af3", af3);
        buttons.put("af4", af4);
        buttons.put("af5", af5);
        buttons.put("af6", af6);

        buttons.put("as1", as1);
        buttons.put("as2", as2);
        buttons.put("as3", as3);
        buttons.put("as4", as4);
        buttons.put("as5", as5);
        buttons.put("as6", as6);
        buttons.put("as7", as7);
        buttons.put("as8", as8);
        buttons.put("as9", as9);
        buttons.put("as10", as10);
        buttons.put("as11", as11);
        buttons.put("as12", as12);

        buttons.put("ar1", ar1);
        buttons.put("ar2", ar2);
        buttons.put("ar3", ar3);
        buttons.put("ar4", ar4);

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

    private void mainMenu() // sets up the main menu
    {
        mainMenu = new JPanel();
        mainMenu.setLayout(null);
        mainMenu.setLocation(0, 0);
        mainMenu.setSize(this.getMaximumSize());
        JButton toggleSound = new JButton("Toggle Sound");
        toggleSound.addActionListener(new SoundToggleListener());
        JButton newGame = new JButton("New Game");
        newGame.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                playClick("click");
                newGameWindow.setVisible(true);
            }
        });
        JButton loadGame = new JButton("Load Game");
        JButton about = new JButton("About");

        loadGame.addActionListener(new LoadGameListener());
        newGame.setLocation(800, 0);
        newGame.setSize(100, 40);
        loadGame.setLocation(800, 60);
        loadGame.setSize(100, 40);
        toggleSound.setLocation(800, 120);
        toggleSound.setSize(100, 40);

        mainMenu.add(newGame);
        mainMenu.add(loadGame);
        mainMenu.add(toggleSound);
        JTextArea j = new JTextArea("About:\n"
                + "Risk game created by Team Rocket as a CSC 335 final project \n"
                + "Daniel Phillips, Reaper Romero, Ben Shields, and Alex Yee\n"
                + "Start a new game by clicking 'New Game' or 'Load Game' if there save file has not been created.\n"
                + "Click 'Load Game' to load a game from existing save file.");
        j.setLocation(500, 240);
        j.setSize(600, 100);
        j.setOpaque(true);
        mainMenu.add(j);
        mainMenu.add(about);
        mainMenu.setVisible(true);
        this.add(mainMenu);
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
        JLabel setNameMsg = new JLabel("Enter names (one on each line):");
        setNameMsg.setSize(290, 20);
        setNameMsg.setLocation(300, 0);
        JTextField setName = new JTextField();
        setName.setSize(300, 400);
        setName.setLocation(360, 20);

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

        numPlayersOption.add(three);
        numPlayersOption.add(four);
        numPlayersOption.add(five);
        numPlayersOption.add(six);
        newGameWindow.add(three);
        newGameWindow.add(four);
        newGameWindow.add(five);
        newGameWindow.add(six);
        newGameWindow.add(players);
        ActionListener npl = new NumPlayersListener();
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
        startGame.setLocation(340, 420);
        startGame.addActionListener(new NewGameListener());
        newGameWindow.add(startGame);
    }

    private class SoundToggleListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (clip.isActive())
            {
                clip.stop();

            }
            else
            {
                playIntro();
            }

        }

    }

    private class LoadGameListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e)
        {
            try
            { // this part would be a lot easier if we used Game
                playClick("click");
                FileInputStream fis = new FileInputStream("RiskSave");
                ObjectInputStream input = new ObjectInputStream(fis);

                GraphicalView g = (GraphicalView) input.readObject();
                // initializeMemberVariables(numPlayers - numHumans, numHumans);
                turnsPlayed = 0;
                roundsPlayed = 0;
                gameOver = false;
                gameMap = (Map) g.getGameMap();

                players = (ArrayList<Player>) g.getPlayers();
                numPlayers = players.size();
                numHumans = 0;
                for (Player p : players)
                {
                    if (p.getClass() == Human.class) numHumans++;
                }
                // initializePlayers(numPlayers - numHumans, numHumans);
                // setupGame();
                input.close();
                fis.close();
                repaint();
                mainMenu.setVisible(false);
                newGameWindow.setVisible(false);
                beginTurn();
            }
            catch (IOException | ClassNotFoundException gdi)
            {
                System.out.println("No save detected. Loading new game...\n");
                newGameWindow.setVisible(true);
            }

        }

    }

    private class NewGameListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent arg0)
        {
            playClick("click");
            initializeMemberVariables(numPlayers - numHumans, numHumans);
            initializePlayers(numPlayers - numHumans, numHumans);
            setupGame();

            newGameFlag = true;
            for (Player player : getPlayers())
            {
                while (player.getArmies() > 0)
                {
                    for (int j = 1; j <= player.getTerritories().size(); j++)
                    {
                        if (player.getArmies() == 0) break;
                        player.addArmy(j);
                    }
                }
            }

            repaint();
            mainMenu.setVisible(false);
            newGameWindow.setVisible(false);
            newGameWindow.dispose();
            mainMenu.setEnabled(false);
            beginTurn();
        }

    }

    private class MapButtonListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e)
        {
            playClick("menu1");
            for (Territory t : gameMap.getTerritories())
            {
                if (t.getCountry().getButtonTitle()
                        .equals(e.getActionCommand()))
                {
                    if (deployFlag)
                    { // deploying
                        if (!t.getOccupier().equals(curr))
                        {
                            gameInfo.append(
                                    "Please select a valid territory to deploy to \n Your color is: "
                                            + curr.getColorString() + "\n");
                            scrollInfo.updateUI();
                        }
                        else
                        {
                            countryFrom = t;
                            gameInfo.append("You have " + (bonus - 1)
                                    + " armies left to deploy. \n");
                            scrollInfo.updateUI();
                            curr.placeDeployedArmies(countryFrom);
                            bonus--;
                            repaint();
                            if (bonus == 0)
                            {
                                countryFrom = null;
                                deployFlag = false;
                                JOptionPane jop = new JOptionPane();

                                String[] options = new String[] { "Attack",
                                        "Fortify", "Skip turn" };
                                int n = jop.showOptionDialog(null,
                                        "Select your next move",
                                        "Risk: Move for "
                                                + curr.getPlayerName(),
                                        JOptionPane.DEFAULT_OPTION,
                                        JOptionPane.PLAIN_MESSAGE, null,
                                        options, options[0]);
                                if (n == 0)
                                {
                                    attackFlag = true;
                                    gameInfo.append(curr.getPlayerName()
                                            + ", please select a territory to attack from\n");
                                    scrollInfo.updateUI();
                                }
                                else if (n == 1)
                                {
                                    fortifyFlag = true;
                                    gameInfo.append(
                                            "Please select a territory to move armies from\n");
                                    scrollInfo.updateUI();
                                }
                                else if (n == 2)
                                {
                                    currentPID++;
                                    beginTurn();
                                }

                            }
                        }
                    }
                    else if (attackFlag)
                    {
                        if (countryFrom == null)
                        {
                            if (!t.getOccupier().equals(curr))
                            {
                                gameInfo.append(
                                        "Please select a valid territory to attack from\n Your color is: "
                                                + curr.getColorString() + "\n");
                                scrollInfo.updateUI();
                            }
                            else
                            {
                                countryFrom = t;
                                gameInfo.append(
                                        "Please select a territory to attack\n");
                                scrollInfo.updateUI();
                            }

                        }

                        else
                        {
                            if (t.getOccupier().equals(curr) || !countryFrom
                                    .getAdjacentTerritories().contains(t))
                            {
                                gameInfo.append(
                                        "Please select a valid territory to attack\n Your color is: "
                                                + curr.getColorString() + "\n");
                                scrollInfo.updateUI();
                            }

                            else
                            {

                                countryTo = t;
                                int n = 0;
                                boolean canAttack = true;

                                canAttack = !resolveAttack(countryFrom,
                                        countryTo);
                                repaint();
                                JOptionPane jop = new JOptionPane();

                                String[] options = new String[] {
                                        "Attack again", "Fortify",
                                        "Skip turn" };

                                n = jop.showOptionDialog(null,
                                        "Select your next move",
                                        "Risk: Move for "
                                                + curr.getPlayerName(),
                                        JOptionPane.DEFAULT_OPTION,
                                        JOptionPane.PLAIN_MESSAGE, null,
                                        options, options[0]);

                                if (n == 0)
                                {

                                    countryFrom = null;
                                    countryTo = null;
                                    attackFlag = false;
                                    fortifyFlag = false;
                                    attackFlag = true;
                                    gameInfo.append(curr.getPlayerName()
                                            + ", select territory to attack from\n");
                                    scrollInfo.updateUI();
                                }
                                else if (n == 1)
                                {
                                    countryFrom = null;
                                    countryTo = null;
                                    attackFlag = false;
                                    fortifyFlag = true;
                                    gameInfo.append(
                                            "Please select a territory to move armies from\n");
                                    scrollInfo.updateUI();
                                }
                                else if (n == 2)
                                {
                                    countryFrom = null;
                                    countryTo = null;
                                    attackFlag = false;
                                    fortifyFlag = false;
                                    currentPID++;
                                    beginTurn();
                                }
                            }
                        }
                    }
                    else if (fortifyFlag)
                    {
                        if (countryFrom == null)
                        {
                            if (!t.getOccupier().equals(curr))
                            {
                                gameInfo.append(
                                        "Please select a valid territory to transport armies from\n Your color is: "
                                                + curr.getColorString() + "\n");
                                scrollInfo.updateUI();
                            }
                            else
                            {
                                countryFrom = t;
                                gameInfo.append(
                                        "Please select a territory to move armies to\n");
                                scrollInfo.updateUI();
                            }
                        }
                        else
                        {
                            if (!(t.getOccupier().equals(curr) && countryFrom
                                    .getAdjacentTerritories().contains(t)))
                            {
                                gameInfo.append(
                                        "Please select a valid territory to fortify\n Your color is: "
                                                + curr.getColorString() + "\n");
                                scrollInfo.updateUI();
                            }

                            else
                            {
                                countryTo = t;
                                String errorMessage = " ";
                                int move = -1;
                                while (!errorMessage.isEmpty())
                                {
                                    String stringInput = JOptionPane
                                            .showInputDialog(errorMessage
                                                    + "Enter number.");
                                    try
                                    {
                                        int number = Integer
                                                .parseInt(stringInput);
                                        if (number >= countryFrom.getArmies()
                                                || number < 0)
                                        {
                                            errorMessage = "That number is not within the \n"
                                                    + "allowed range!\n";
                                        }
                                        else
                                        {

                                            move = number;
                                            errorMessage = ""; // no more error
                                        }
                                    }
                                    catch (NumberFormatException f)
                                    {
                                        // The typed text was not an integer
                                        errorMessage = "The text you typed is not a number.\n";
                                    }
                                }
                                curr.fortify(countryFrom, countryTo, move);
                                repaint();
                                countryFrom = null;
                                countryTo = null;
                                fortifyFlag = false;
                                currentPID++;
                                beginTurn();
                            }
                        }
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
            String nameInput = JOptionPane.showInputDialog("Enter name for "
                    + getPlayers().get(getCurrPID()).getPlayerName());
            getPlayers().get(getCurrPID()).setPlayerName(nameInput);
            gameInfo.append("Name changed: " + nameInput);

        }
    }

    private class Save implements WindowListener
    {

        @Override
        public void windowOpened(WindowEvent e)
        {
        }

        @Override
        public void windowClosing(WindowEvent e)
        {
            JOptionPane jop = new JOptionPane();
            jop.setMessage("Save?");
            int n = jop.showConfirmDialog(null, "Save?", "Save State",
                    JOptionPane.YES_NO_OPTION);

            if (n == jop.YES_OPTION)
            {
                try
                {
                    FileOutputStream fos = new FileOutputStream("RiskSave");
                    ObjectOutputStream outFile = new ObjectOutputStream(fos);

                    outFile.writeObject(getThis());
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
                for (Player player : getPlayers())
                {
                    // g2.setColor(player.getColor());
                    for (Territory t : player.getTerritories())
                    {

                        buttons.get(t.getCountry().getButtonTitle())
                                .setBackground(player.getColor());
                        buttons.get(t.getCountry().getButtonTitle())
                                .setOpaque(true);
                        buttons.get(t.getCountry().getButtonTitle())
                                .setBorderPainted(false);
                        buttons.get(t.getCountry().getButtonTitle())
                                .setFont(new Font("Arial", Font.BOLD, 8));
                        // buttons.get(t.getCountry().getButtonTitle()).setText(String.valueOf(t.getArmies()));

                        g2.drawString(String.valueOf(t.getArmies()),
                                t.getPointX(), t.getPointY());

                    }
                }
            }

        }
    }

    /** Begin game functionality code **/

    // CONSTRUCTOR HELPER METHODS
    private void initializeMemberVariables(int numBots, int numHumans)
    {
        turnsPlayed = 0;
        roundsPlayed = 0;
        gameOver = false;
        gameMap = new Map();
        this.numPlayers = numBots + numHumans;
        players = new ArrayList<Player>();
    }

    public Object getThis()
    {
        // TODO Auto-generated method stub
        return this;
    }

    public Map getGameMap()
    {
        // TODO Auto-generated method stub
        return this.gameMap;
    }

    private void initializePlayers(int numBots, int numHumans)
    {
        int initArmies = 0;
        if (numPlayers == 3)
            initArmies = 35;
        else if (numPlayers == 4)
            initArmies = 30;
        else if (numPlayers == 5)
            initArmies = 25;
        else if (numPlayers == 6) initArmies = 20;
        for (int ii = 0; ii < numBots; ii++)
        { // instantiate bots

            Player p = new MediumBot(ii, initArmies, gameMap);
            players.add(p);
            p.limitAttacks();
        }
        for (int jj = numBots; jj < numPlayers; jj++)
        { // instantiate
          // humans

            Player p = new Human(jj, initArmies, gameMap);

            players.add(p);
        }
    }

    private void setupGame()
    {
        rollToGoFirst();
        claimTerritories();
        // beginGame();
    }

    private void rollToGoFirst()
    {
        Random r = new Random();
        currentPID = r.nextInt(players.size()); // so this number is 0 to
                                                // (size-1)

    }

    private void claimTerritories()
    {
        System.out.println("Randomly claiming territories.");
        for (int ii = 0; ii < 42; ii++)
        {

            currentPID = currentPID % numPlayers; // to prevent out of bounds
                                                  // exception
            gameMap.giveRandomTerritory(players.get(currentPID));

            currentPID++;

            currentPID = currentPID % numPlayers;
        }

    }

    public void beginTurn()
    {
        playClick("rock");
       
        currentPID = currentPID % numPlayers;
        curr = players.get(currentPID);
        mainLabel.setText(curr.getPlayerName() + ", it is your turn: \n"
                + "Your color is: " + curr.getColorString() + "\n");
        bonus = curr.deploy();
   
   
        gameInfo.setText(curr.getPlayerName() + ", it is your turn: \n" + "Your color is: " + curr.getColorString() + "\n");
        
        gameInfo.append(
               "Please select a territory to deploy a single army to. \n");
        scrollInfo.updateUI();
        if (curr.getClass() != Human.class)
        {
            bonus += gameMap.exchangeCards(curr);
            curr.placeDeployedArmiesRand(bonus);
            attack(); // already takes care of fortify in here
            repaint();
            currentPID++;
            beginTurn();
        }
        else
        {
            bonus += gameMap.exchangeCards(curr);

            cardView.setUp(curr);
            cardView.repaint();
            if (cardPop)
            {

                cardView.openWindow();
                cardPop = false;
            }
            deployFlag = true;
        }
        
    }

    // TODO: check if curr fortifies here

    private void attack()
    {
        // Asks if the player wants to attack or no
        boolean choice = players.get(currentPID).willAttack();
        if (choice == true)
        {
            Player currentPlayer = players.get(currentPID);
            // Determines the current player object
            try
            {
                boolean attackUnresolved = true;
                while (attackUnresolved)
                {
                    // get the two territory choices involved in the battle
                    int attackingTerritoryNumber = currentPlayer.attackFrom();
                    Territory attackingTerritory = currentPlayer
                            .getTerritories().get(attackingTerritoryNumber);

                    int defendingTerritoryNumber = currentPlayer
                            .attackAt(attackingTerritoryNumber);
                    Territory defendingTerritory = attackingTerritory
                            .getAdjacentTerritories()
                            .get(defendingTerritoryNumber);

                    // carry out the dice rolling and army losses
                    resolveAttackBot(attackingTerritory, defendingTerritory);
                    if (!currentPlayer.attackAgain())
                    {
                        attackUnresolved = false;
                        if (currentPlayer.willFortify())
                            currentPlayer.fortify();
                    }
                } // end while

            } // end try
            catch (NullPointerException e)
            {
                e.printStackTrace();
            }
        } // end if
        return;
    }// end method

    ///////////////////// Resolved attack

    public boolean resolveAttackBot(Territory attacking, Territory defending) // handles
                                                                              // attacking
                                                                              // human
    {
        /*
         * Needs error checking to make sure that there are at least 2 armies in
         * the attacking territory
         */

        Dice dice = new Dice();
        JOptionPane jop = new JOptionPane();
        Player attacker = attacking.getOccupier();
        Player defender = defending.getOccupier();
        int attackerRollNumber = attacker.attackDice(attacking.getArmies());
        ArrayList<Integer> attackersRolls;
        int atkPID = attacking.getOccupier().getPID();
        String defStr = defending.toString();
        int atkDice = attackerRollNumber;
        int defenderRollNumber;
        if (defender.getClass() == Human.class)
        {
            String[] options2 = new String[] { "1" };
            if (defending.getArmies() >= 3)
                options2 = new String[]
            { "1", "2" };
            int n2 = jop.showOptionDialog(null, "Select number of dice to roll",
                    "Risk Attack Dice for Defender: "
                            + defender.getPlayerName(),
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                    options2, options2[0]);
            defenderRollNumber = n2 + 1;
        }
        else
        {
            defenderRollNumber = defender.defenseDice(atkPID, defStr, atkDice,
                    defending.getArmies());
        }
        ArrayList<Integer> defendersRolls;
        int min = Math.min(attackerRollNumber, defenderRollNumber);
        attackersRolls = dice.roll2(attackerRollNumber);

        defendersRolls = dice.roll2(defenderRollNumber);

        Collections.sort(attackersRolls, Collections.reverseOrder());
        Collections.sort(defendersRolls, Collections.reverseOrder());

        for (int i = 0; i < min; i++)
        {
            gameInfo.append("Attacker's highest roll: " + attackersRolls.get(i)
                    + "\n" + "Defender's highest roll: " + defendersRolls.get(i)
                    + "\n");
            scrollInfo.updateUI();
            if (attackersRolls.get(i) <= defendersRolls.get(i))
            {
                attacking.addArmies(-1);
            }
            else
            {
                defending.addArmies(-1);
            }
        }

        // if the defending player loses, give the territory to the attacking
        // player
        // and return true, the attack has been resolved
        if (defending.getArmies() <= 0)
        {
            defender.loseTerritory(defending);
            defending.changeOccupier(attacking.getOccupier());

            attacker.addTerritory(defending);
            // TODO: Call method on player to have them invade the territory!!!
            int invadingArmies = attacker.attackInvade(attacking.getArmies());
            defending.addArmies(invadingArmies); // move troops into newly
                                                 // acquired territory
            attacking.addArmies(-1 * invadingArmies); // remove those troops
                                                      // from the attacking terr
            attacker.drawCard();
            return true;
        }
        else if (attacking.getArmies() == 1)
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    // PRIVATE METHODS
    public String getTerritories(int k) // not meant to take in an index, but a
                                        // player number
    {
        return players.get(k).getTerroritories();
    }

    public void placeArmyInPlayerTerritory(int p, int terrNumber)
    {
        players.get(p).addArmy(terrNumber);
    }

    public boolean resolveAttack(Territory attacking, Territory defending)
    {
        /*
         * Needs error checking to make sure that there are at least 2 armies in
         * the attacking territory
         */
        Dice dice = new Dice();

        Player attacker = attacking.getOccupier();
        Player defender = defending.getOccupier();
        JOptionPane jop = new JOptionPane();
        jop.setMessage("Select your next move");
        String[] options = new String[] { "1" };
        if (attacking.getArmies() == 3)
            options = new String[] { "1", "2" };
        else if (attacking.getArmies() > 3)
        {
            options = new String[] { "1", "2", "3" };
        }
        int n = jop.showOptionDialog(null, "Select number of dice to roll",
                "Risk Attack Dice for Attacker: " + curr.getPlayerName(),
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                options, options[0]);
        int attackerRollNumber = n + 1;

        ArrayList<Integer> attackersRolls;
        // attackersRolls.addAll(dice.roll2(attackerRollNumber));

        int atkPID = attacking.getOccupier().getPID();
        String defStr = defending.toString();
        int atkDice = attackerRollNumber;
        int defenderRollNumber = 1;
        if (defender.getClass() == Human.class)
        {
            jop.setMessage("Select your next move");
            String[] options2 = new String[] { "1" };
            if (defending.getArmies() >= 3)
                options2 = new String[]
            { "1", "2" };

            int n2 = jop.showOptionDialog(null, "Select number of dice to roll",
                    "Risk Attack Dice for Defender: "
                            + defender.getPlayerName(),
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                    options2, options2[0]);
            defenderRollNumber = n2 + 1;
        }
        else
        {
            defenderRollNumber = defender.defenseDice(atkPID, defStr, atkDice,
                    defending.getArmies());
        }
        System.out.println(
                attackerRollNumber + "   >>>>   " + defenderRollNumber);

        ArrayList<Integer> defendersRolls;
        ((DicePopUP) d).setDiceNumber(attackerRollNumber, defenderRollNumber);
        attackersRolls = dice.roll2(attackerRollNumber);

        defendersRolls = dice.roll2(defenderRollNumber);
        Collections.sort(attackersRolls, Collections.reverseOrder());
        Collections.sort(defendersRolls, Collections.reverseOrder());
        ((DicePopUP) d).setAttackersRolls(attackersRolls);
        ((DicePopUP) d).setDefendersRolls(defendersRolls);

        ((DicePopUP) d).setDiceNumber(attackerRollNumber, defenderRollNumber);
        if (dicePop)
        {
            ((DicePopUP) d).openWindow();
            dicePop = false;
        }
        ((DicePopUP) d).roll();

        /*
         * int defenderRollNumber = defender.defenseDice(atkPID, defStr,
         * atkDice, defending.getArmies());
         */

        int min = Math.min(attackerRollNumber, defenderRollNumber);

        /*
         * attackersRolls = dice.roll2(attackerRollNumber); defendersRolls =
         * dice.roll2(defenderRollNumber);
         */

        for (int i = 0; i < min; i++)
        {
            // System.out.printf("attacker %d defender %d
            // \n",attackersRolls.get(i),defendersRolls.get(i));

            if (attackersRolls.get(i) <= defendersRolls.get(i))

            {
                gameInfo.append("Attacker's highest roll: "
                        + attackersRolls.get(i) + " \nDefender's highest roll:"
                        + defendersRolls.get(i) + "\n");
                scrollInfo.updateUI();
                attacking.addArmies(-1);
            }
            else if (attackersRolls.get(i) > defendersRolls.get(i))
            {
                gameInfo.append(" Attacker rolled: " + attackersRolls.get(i)
                        + " \nDefender's roll:" + defendersRolls.get(i) + "\n");
                defending.addArmies(-1);
                scrollInfo.updateUI();
            }
        }

        // if the defending player loses, give the territory to the attacking
        // player
        // and return true, the attack has been resolved
        if (defending.getArmies() == 0)
        {
            defender.loseTerritory(defending);
            defending.changeOccupier(attacking.getOccupier());

            attacker.addTerritory(defending);
            attacker.territoryObtained(defending);
            defending.addArmies(1);
            attacking.removeArmies(1);
            attacker.drawCard();

            return true;
        }
        else if (attacking.getArmies() == 1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public Territory getTerritory(String c)
    {
        return gameMap.getTerritory(c);
    }

    public ArrayList<Player> getPlayers()
    {
        return this.players;
    }

    public int getCurrPID()
    {
        return this.currentPID;

    }

    public Player switchDifficulty(Player former, String diff)
    {
        Player result;
        if (diff.equals("Easy"))
        {
            result = new EasyBot(former.getPlayerID(), 0, this.gameMap);
        }
        else if (diff.equals("Medium"))
        {
            result = new MediumBot(former.getPlayerID(), 0, this.gameMap);
        }
        else if (diff.equals("Hard"))
        {
            result = new HardCpu(former.getPlayerID(), 0, this.gameMap);
        }
        else
            result = new Human(former.getPlayerID(), 0, this.gameMap);
        result.swapPlayerInfo(former);
        players.remove(former);
        players.add(result);
        gameInfo.append(
                former.getPlayerName() + " is now " + result.getClass());
        return result;

    }

    public static synchronized void playIntro()
    {
        try
        {
            File yourFile = new File("./images/BecomeALegend.wav");
            AudioInputStream stream;
            AudioFormat format;
            DataLine.Info info;
            // Clip clip;

            stream = AudioSystem.getAudioInputStream(yourFile);
            format = stream.getFormat();
            info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
            clip.start();
            clip.loop(3);
        }
        catch (Exception e)
        {
            // whatevers
        }
    }

    public void stopIntro()
    {
        clip.stop();
        clip.close();
    }

    public static synchronized void playClick(String str1)
    {
        try
        {
            String str = str1;
            File yourFile = new File("./images/" + str + ".wav");
            AudioInputStream stream;
            AudioFormat format;
            DataLine.Info info;
            Clip clip;

            stream = AudioSystem.getAudioInputStream(yourFile);
            format = stream.getFormat();
            info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
            clip.start();
        }
        catch (Exception e)
        {
            // whatevers
        }
    }
   
}
