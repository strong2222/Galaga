package Main;




import Actors.Bullet;

import Actors.Player;

import Stage.Stage;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import java.util.ArrayList;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

import javax.swing.JPanel;


public class GamePanel extends JPanel implements Runnable{
    
    
    
    
    
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
        try {
            backGroundImage=ImageIO.read(getClass().getResourceAsStream("/source/player/R.png"));
        } catch (IOException ex) {
            Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    //thread start
    public void gameThreadStart(){
        thread1 = new Thread(this);
        thread1.start();
        
    }
    
    // game loop
    @Override
    public void run() {
        double drawInterval = 1000/fps;
        double nextDrawTime = System.currentTimeMillis()+drawInterval;
        
        while(!Thread.currentThread().isInterrupted() && player.isAlive()){
            
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
    
    // movement
    public void update() {
        
        
        player.update();
        st.update();
        
      
//        
//        if (keyHandler.fire && !lastFireState ) {
//saj            fireBullet();
//        }
//        
//        lastFireState = keyHandler.fire;
//
//       Iterator<Bullet> iterator = bullets.iterator();
//       
//        while (iterator.hasNext()) {
//            Bullet bullet =iterator.next();
//            bullet.colition.y = bullet.y;
//            bullet.y -= bullet.speed;
//            
//          boolean hitEnemy = false;
          
//            if(st.colition != null && st.checkCollision(bullet.colition)){  
//           
//                hitEnemy = true;
//            }
//            // Remove bullets when they go off-screen
//            if (bullet.y < 0 || hitEnemy) {
//                iterator.remove();
//            }
//            
//          }  
            
        
        
    }
    // drawing bullet
//    private void fireBullet() {
//        Bullet bullet = new Bullet(player.x, player.y);
//        bullets.add(bullet);
//    }
    
 //checking colision   

    
//drawing
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
         g2d.drawImage(backGroundImage, 0, 100, this);
       
        
        player.draw(g2d);
        st.draw(g2d);
      
       
         
       g2d.dispose();
    }
    
    
    
    
    
    
    
}
