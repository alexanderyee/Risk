package view;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.*;

import sun.applet.Main;

/**
 * Shows a pair of dice that are rolled when the user clicks a button
 * that appears below the dice.
 */
public class DicePopUP extends JPanel {
   
   private int die1 = 4;  // The values shown on the dice.
   private int die2 = 3;
   private int die3 = 3;
   private int die4 = 5;
   private int die5 = 6;
   private Timer timer;   // Used to animate rolling of the dice.
   private Image riskLogo;
   private ArrayList<Integer> attackersRolls=new ArrayList<Integer>();
   private ArrayList<Integer> defendersRolls=new ArrayList<Integer>();
   private boolean rollDone=false;
   private int aDiceNumber=3;
   private int dDiceNumber=2;
   /**
    *  The constructor sets up the panel.  It creates the button and
    *  the drawing surface panel on which the dice are drawn and puts
    *  them into a BorderLayout.  It adds an ActionListener to the button
    *  that rolls the dice when the user clicks the button.
    */
   public DicePopUP() {
      setSize(200,200);
      setLayout(new BorderLayout(2,2));
      setBackground(Color.RED);  // Will show through the gap in the BorderLayout.
      setBorder(BorderFactory.createLineBorder(Color.RED,2));
      
      JPanel dicePanel = new JPanel() {
         public void paintComponent(Graphics g) { 
            super.paintComponent(g);  // fill with background color.
   
            //attackers dice
           if(aDiceNumber==3){
            
            drawDie(g, die1, 70, 90); // Just draw the dice.
            drawDie(g,die2,120,90);
            drawDie(g,die3,170,90);
           }else if(aDiceNumber==2){
               drawDie(g, die1, 70, 90); // Just draw the dice.
               drawDie(g,die2,120,90);
           }else{
               drawDie(g, die1, 70, 90); // Just draw the dice.
               
           }
            //Defenders Dice
           if(dDiceNumber==2){
               drawDie(g,die4,70,160);
               drawDie(g, die5, 120, 160);
           }else{
               drawDie(g,die4,70,160);
              
           }
       
   

        
         }
      };
      dicePanel.setPreferredSize( new Dimension(300,300) );
      dicePanel.setBackground( /*new Color(200,200,255)*/Color.GRAY );  // light blue
     
      add(dicePanel, BorderLayout.CENTER);
      
      //Clicking on the roll button
    
     
      setVisible(true);
      try {
        
          riskLogo = ImageIO.read(new File("./images/riskLogo.png"));
         
      } catch (IOException e) {
          e.printStackTrace();
      }
   
   
   
   } // end constructor
   public void setDiceNumber(int attack, int defend){
      aDiceNumber=attack;
      dDiceNumber=defend;
   }
 
   public void setAttackersRolls(ArrayList<Integer> attack){
      attackersRolls=attack;
   }
   public void setDefendersRolls(ArrayList<Integer> defend){
       defendersRolls=defend;
   }
   public void setAttackerDiceNumber(int attackerRollNumber){
       aDiceNumber=attackerRollNumber;
   }
   public void setdefenderDiceNumber(int defenderRollNumber){
       dDiceNumber=defenderRollNumber;
   }
   /**
    * Draw a die with upper left corner at (x,y).  The die is
    * 35 by 35 pixels in size.  The val parameter gives the
    * value showing on the die (that is, the number of dots).
    */
   void drawDie(Graphics g, int val, int x, int y) {
      
       
       
       g.drawImage(riskLogo, 50, 0, null);
       g.setColor(Color.WHITE);
       g.setFont(new Font("default", Font.BOLD, 14));
       g.drawString("attackers dice", 80, 80);
       g.setFont(new Font("default", Font.BOLD, 14));
       g.drawString("Defenders dice", 80, 150);
       
      
       g.setColor(Color.red);
       
      
      
      
      
      g.fillRect(x, y, 35, 35);
      g.setColor(Color.black);
    
      g.drawRect(x, y, 34, 34);
      if (val > 1)  // upper left dot
         g.fillOval(x+3, y+3, 9, 9);
      if (val > 3)  // upper right dot
         g.fillOval(x+23, y+3, 9, 9);
      if (val == 6) // middle left dot
         g.fillOval(x+3, y+13, 9, 9);
      if (val % 2 == 1) // middle dot (for odd-numbered val's)
         g.fillOval(x+13, y+13, 9, 9);
      if (val == 6) // middle right dot
         g.fillOval(x+23, y+13, 9, 9);
      if (val > 3)  // bottom left dot
         g.fillOval(x+3, y+23, 9, 9);
      if (val > 1)  // bottom right dot
         g.fillOval(x+23, y+23, 9,9);
      
  
   
   }
  
   /**
    * Run an animation that randomly changes the values shown on
    * the dice 10 times, every 100 milliseconds.
    */
   public void roll() {
    playClick("dice");
       if (timer != null)
         return;
      timer = new Timer(50, new ActionListener() {
         int frames = 1;
         public void actionPerformed(ActionEvent evt) {
           if(aDiceNumber==3){
            die1 = (int)(Math.random()*6) + 1;
            die2 = (int)(Math.random()*6) + 1;
            die3 = (int)(Math.random()*6) + 1;
           
           }else if(aDiceNumber==2){
               die1 = (int)(Math.random()*6) + 1;
               die2 = (int)(Math.random()*6) + 1;
              
           }else{
               die1 = (int)(Math.random()*6) + 1;
               
           }
            
            if(dDiceNumber==2){
            die4 = (int)(Math.random()*6) + 1;
            die5 = (int)(Math.random()*6) + 1;
           
            
            }else{
                die4 = (int)(Math.random()*6) + 1;
               
            }
            repaint();
            frames++;
            //Stop the timer and add all results to array.
            if (frames == 10) {
               timer.stop();
               timer = null;
               if(aDiceNumber==3){
                   die1 = attackersRolls.get(0);
                   die2 = attackersRolls.get(1);
                   die3 = attackersRolls.get(2);
                 
                   
                  
                  }else if(aDiceNumber==2){
                      die1 = attackersRolls.get(0);
                      die2 = attackersRolls.get(1);
                     
                  }else{
                      die1 = attackersRolls.get(0);
                      
                      
                  }
                   
                   if(dDiceNumber==2){
                   die4 = defendersRolls.get(0);
                   die5 =  defendersRolls.get(1);
                  
                   
                   }else{
                       die4 =  defendersRolls.get(0);
                       
                   }
                 
                   rollDone=true;
                   
            repaint();
            
            
            }
         
       
         
         
         
         }
      });
      timer.start();
   }
   public boolean isDone(){
       return rollDone;
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
   public void openWindow(){
       JFrame window = new JFrame();
       DicePopUP content = this;
       JButton b= new JButton("test");
       window.setContentPane(content);
       window.pack();
    //  window.add(b);
       window.setLocation(100,150);
       window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
       window.setResizable(false);  // User can't change the window's size.
       window.setVisible(true);
   }
   
   
   
   
   
} // end class DicePanelWithButton