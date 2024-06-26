/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Actors;


import Main.Key;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import Main.GamePanel;
import java.awt.Color;


/**
 *
 * @author dell
 */
public final class Player extends Character{
    
    GamePanel gp;
    Key keyHandler;
    public static boolean isAlive;
    BufferedImage playerImages;
    public int lives=3;
    private long lastCollisionDetectionTime; 
    private long collisionCooldown = 3000; 
    private boolean isBlinking = false; 
    private long lastHitTime; 
    private long blinkInterval = 3000; 
    
    
    
    
    public Player(GamePanel gp , Key keyhandler){
        this.x = 375;
        this.y = 600;
        this.speed =2;
        this.gp = gp;
        this.keyHandler = keyhandler;
       
        this.colition = new Rectangle(x,y,gp.tileSize,gp.tileSize-10);
        
        getImage();
       
    }
    public void update(){
        
        
        long elapsedTime = System.currentTimeMillis() - lastHitTime;
        if (elapsedTime < blinkInterval) {
            isBlinking = !isBlinking; // Toggle the blinking flag at each blink interval
        } else {
            isBlinking = false; // Stop blinking after the blink interval
        }
        if(keyHandler.up == true){
           y = y-speed;
           colition.y = y;
       } 
       else if(keyHandler.down == true){
           y = y + speed;
           colition.y = y;
       }
       else if(keyHandler.left == true){
           x = x - speed;
           colition.x = x;
           try {
            playerImages = ImageIO.read(getClass().getResourceAsStream("/source/player/PlayerMain2.png"));
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
       }
       else if(keyHandler.right == true){
           x = x + speed;
           colition.x = x;
            try {
            playerImages = ImageIO.read(getClass().getResourceAsStream("/source/player/PlayerMain1.png"));
            }   
            catch (IOException ex) {
             Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
            }
       }
       else{
            try {
            playerImages = ImageIO.read(getClass().getResourceAsStream("/source/player/PlayerMain.png"));
            } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
            }
       }
        
       if(x>gp.getWidth()-50) {
           x = gp.getWidth()-50;
       }
       if(x<0){
           x=0;
       }
       if(y>gp.getHeight()-50){
           y=gp.getHeight()-50;
       }
       if(y<0){
           y=0;
       }
 
       
    }
    public void getImage(){
         try {
            
            playerImages = ImageIO.read(getClass().getResourceAsStream("/source/player/PlayerMain.png"));
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void draw(Graphics2D g2d){
            
        if (!isBlinking) {
           g2d.setColor(new Color(0,0,0,0));
           g2d.drawImage(playerImages, x,y,gp.tileSize,gp.tileSize,null);
        
        }
           
           liveDraw(g2d);
    }
     public void checkCollisionWithEnemies(Enemy enemy) throws IOException {
        long currentTime = System.currentTimeMillis();
        
        if (currentTime - lastCollisionDetectionTime >= collisionCooldown) {
            if (enemy.colition != null && this.colition.intersects(enemy.colition)) {
                
                
//                playerImages = ImageIO.read(getClass().getResourceAsStream("/source/player/explode.png"));
                
                lives = lives - 1;
                lastCollisionDetectionTime = currentTime; // Update the last collision detection time
                startBlinking();
            }
        }
    }
     private void startBlinking() {
        isBlinking = true; // Set the blinking flag to true
        lastHitTime = System.currentTimeMillis(); // Reset the last hit time
    }

    public Key getKeyHandler() {
       return keyHandler;
    }
    public boolean isAlive(){
        
        if(lives ==0){
            return false;
        }
        else{
            return true;
        }
    }
    public void liveDraw(Graphics2D g2d){
        for(int i=0;i<this.lives;i++){
            g2d.drawImage(playerImages, i*50, 0,50,50, null);
        }
        
    }

    public void reset() {
         this.x = 375;
        this.y = 600;
         this.colition = new Rectangle(x,y,gp.tileSize,gp.tileSize-10);
        this.lives= 3;
        getImage();
        
    }
    
    

   
}

