package view;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.sun.javafx.collections.MappingChange.Map;

import model.Card;
import model.CardType;
import model.Countries;
import model.Player;

public class CardSystemView extends JPanel {
  
    private Image artillery;
    private Image riskLogo;
    private Image calvary;
    private Image infantry;
    Card a1 = new Card(CardType.INFANTRY, Countries.ALASKA);
    Card a2 = new Card(CardType.ARTILLERY, Countries.NW_TERRITORY);
    Card a3 = new Card(CardType.CALVARY, Countries.GREENLAND);
    Card a4 = new Card(CardType.INFANTRY, Countries.ALBERTA);
    Card a5 = new Card(CardType.CALVARY, Countries.ONTARIO);

    private ArrayList<Integer> xs= new ArrayList<Integer>();
  
    private ArrayList<Card> cards= new ArrayList<Card>();
  public CardSystemView(){
     setUpArray();
      hand();
      try
      {
          artillery=ImageIO.read(new File("./images/artillery.png"));
          riskLogo = ImageIO.read(new File("./images/riskLogo.png"));
           calvary=ImageIO.read(new File("./images/calvary.png"));
          infantry = ImageIO.read(new File("./images/infantry.png"));
      }
      catch (IOException e)
      {
          e.printStackTrace();
      }
      setSize(800,300);
      setLayout(new BorderLayout(2,2));
      setBackground(Color.BLUE);  // Will show through the gap in the BorderLayout.
      setBorder(BorderFactory.createLineBorder(Color.BLUE,2));
      JPanel dicePanel = new JPanel() {
          public void paintComponent(Graphics g) { 
             super.paintComponent(g);  // fill with background color.
             Graphics2D g2 = (Graphics2D) g;
             
            //the only thing that chages in the rectangle is the irst coordinate
           //for RiskLogo it will be xs + 30
             Shape card1 = new Rectangle2D.Double(10, 0, 140, 260);
             /*g2.draw(card1);
             g2.drawImage(riskLogo, 40, 10, 70, 70, null);
             g2.drawImage(artillery,19,70,null);
             
             Shape card2 = new Rectangle2D.Double(160, 0, 140, 260);
             g2.draw(card2);
             g2.drawImage(riskLogo, 190, 10, 70, 70, null);
             g2.drawImage(calvary,180,70,null);
             
             Shape card3 = new Rectangle2D.Double(310, 0, 140, 260);
             g2.draw(card3);
             g2.drawImage(riskLogo, 340, 10, 70, 70, null);
             g2.drawImage(infantry, 359, 70, null);
             
             Shape card4 = new Rectangle2D.Double(460, 0, 140, 260);
             g2.draw(card4);
             g2.drawImage(riskLogo, 490, 10, 70, 70, null);
            
             Shape card5 = new Rectangle2D.Double(610, 0, 140, 260);
             g2.draw(card5);
             g2.drawImage(riskLogo, 640, 10, 70, 70, null);  */
             int i=0;
             for(Card c : cards){
                card1 = new Rectangle2D.Double(xs.get(i), 0, 140, 260);
               g2.draw(card1);
               g2.setColor(Color.GRAY);
               g2.fill(card1);
               g2.drawImage(riskLogo, xs.get(i)+15, 10, 100, 70, null);
              if(c.getCardType()==CardType.ARTILLERY){
               g2.drawImage(artillery,xs.get(i)+11,90,null);
              }else if(c.getCardType()==CardType.INFANTRY){
                  g2.drawImage(infantry,xs.get(i)+40,90,null);
                 }else{
                     g2.drawImage(calvary,xs.get(i)+15,90,null);
                 }
              g2.setColor(Color.BLACK);
              g2.setFont(new Font("default", Font.BOLD, 16));
              g2.drawString(c.getCountry().toString(),
                      xs.get(i)+5 ,250 );
              i++;
           
             }
            
             
             
            
             
            // g.drawImage(artillery, 100, 100, null);

         
          }
       };
       dicePanel.setPreferredSize( new Dimension(780 ,300) );
       dicePanel.setBackground( /*new Color(200,200,255)*/Color.WHITE );  // light blue
      
       add(dicePanel, BorderLayout.CENTER);
     
     
      setVisible(true);
   
    
  
  
  
  
  
  }
   public void setUpArray(){
       xs.add(10);
       xs.add(160);
       xs.add(310);
       xs.add(460);
       xs.add(610);
   }
   public void hand(){
     cards.add(a1);
     cards.add(a2);
     cards.add(a3);
     cards.add(a4);
     cards.add(a5);
   }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
    
        // Draw background image 100 times
     
       
    }

    public void openWindow(){
        JFrame window = new JFrame();
        CardSystemView content = this;
       
        window.setContentPane(content);
        window.pack();
     
        window.setLocation(100,150);
        window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        window.setResizable(false);  // User can't change the window's size.
        window.setVisible(true);
    }

    public static void main(String[] args){
        CardSystemView test= new CardSystemView();
        test.openWindow();
    }
    
}

