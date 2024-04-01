package Main;






import Actors.Player;

import Stage.Stage;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.image.BufferedImage;
import java.io.IOException;


import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;

import javax.swing.JPanel;


public class GamePanel extends JPanel implements Runnable{
    
    
    JButton resetButton;
    JButton exitButton;
    
    
    Key keyHandler = new Key();
    Player player = new Player(this, keyHandler);
    
    Stage st = new Stage(player,this,keyHandler);
    
   
    int fps = 120;
   
    public final int orignalTileSize= 20;
    final int scale = 3;
    public final int tileSize = orignalTileSize * scale;
    
    final int maxScreenColumn = 16;
    final int maxScreenRow = 12;
    public final int screenColumn = tileSize * maxScreenColumn;
    public final int screenRow = tileSize * maxScreenRow;
    
   
    
    Thread thread1;
    
    BufferedImage backGroundImage;
  
    //constructor
    public GamePanel(){
        this.setPreferredSize(new Dimension(screenColumn,screenRow));
        this.setBackground(Color.black);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        this.setLayout(null);
        try {
            backGroundImage=ImageIO.read(getClass().getResourceAsStream("/source/player/R.png"));
        } catch (IOException ex) {
            Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Inside the GamePanel constructor

    // Other initialization...

    resetButton = new JButton("Reset");
    resetButton.setBounds(350, 440, 80, 30);
    resetButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            resetGame();
        }
    });

    exitButton = new JButton("Exit");
    exitButton.setBounds(470, 440, 80, 30);
    exitButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    });

    // Initially hide both buttons
    resetButton.setVisible(false);
    exitButton.setVisible(false);

    // Add both buttons to the panel
    this.add(resetButton);
    this.add(exitButton);


        
        
    }
   
    //thread start
    public void gameThreadStart(){
        thread1 = new Thread(this);
        thread1.start();
    }
    
    // gameloop
    @Override
    public void run() {
        double drawInterval = 1000/fps;
        double nextDrawTime = System.currentTimeMillis()+drawInterval;
        
        while(!Thread.currentThread().isInterrupted() && player.isAlive() && !st.isCompleted()){
            
            update();
            
            repaint();
            
            
            try{
            double remain = nextDrawTime - System.currentTimeMillis();
            if(remain < 0){
                remain = 0;
            }
             Thread.sleep((long) remain);
             nextDrawTime = nextDrawTime + drawInterval;
            }
             catch (InterruptedException ex) {
                Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
         
    }
    
    // GameOver
    public void GameOver(Graphics2D g2d){
        
            
        try {
            
            g2d.drawImage(ImageIO.read(getClass().getResourceAsStream("/source/player/GameOver.png")),270,150,400,400,null);
            
        } catch (IOException ex) {
            Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
           resetButton.setVisible(true);
           exitButton.setVisible(true);
            
        
    }
    //Winning
    public void YouWin(Graphics2D g2d){
        try {
            g2d.drawImage(ImageIO.read(getClass().getResourceAsStream("/source/player/YouWin.png")),270,150,400,400,null);
        } catch (IOException ex) {
            Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        exitButton.setVisible(true);
        resetButton.setVisible(true);
    }
    
    // movement
    public void update() {
        
        
        player.update();
        st.update();
        
      
    }

    
//drawing
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(backGroundImage, 0, 100, this);
       
        player.draw(g2d);
        st.draw(g2d);
        
        if(!player.isAlive()){
            GameOver(g2d);
        }
        if(st.isCompleted()){
            YouWin(g2d);
        }
      
       g2d.dispose();
    }
    private void resetGame(){
        player.reset(); 
        st.reset(); 
        gameThreadStart();
        
        resetButton.setVisible(false);
    }

    
   
    
    
    
    
    
    
    
}
