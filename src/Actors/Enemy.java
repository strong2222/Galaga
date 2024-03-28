package Actors;

import Main.GamePanel;
import java.awt.Color;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Enemy extends Character {
    
    GamePanel gp;
    BufferedImage[] enemyImages; // Array to hold idle animation frames
    int currentAnimationFrame; // Index of the current animation frame
    long lastFrameChangeTime; // Time of the last frame change
    long frameChangeDelay = 200; // Delay between frame changes in milliseconds
    
    public boolean isDestroyed = false;
    int idleMovement =x+20;
    int idleSpeed = 20;
    int originalY;
    
    public Enemy(GamePanel gp, int Xenemy, int Yenemy) {
        this.gp = gp;
        this.x = Xenemy;
        this.y = Yenemy;
        this.colition = new Rectangle(x, y, gp.tileSize - 10, gp.tileSize - 20);
        this.speed = 10;
        loadImages();
        this.originalY = Yenemy;
    }
    
    protected void loadImages() {
        try {
           
            enemyImages = new BufferedImage[2]; // Assuming two frames for the animation
            enemyImages[0] = ImageIO.read(getClass().getResourceAsStream("/source/player/enemy1.png"));
            enemyImages[1] = ImageIO.read(getClass().getResourceAsStream("/source/player/enemy1idle.png"));
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void draw(Graphics2D g2d) {
        if (!isDestroyed) {
            // Calculate which frame to draw based on elapsed time
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastFrameChangeTime > frameChangeDelay) {
                
                currentAnimationFrame = (currentAnimationFrame + 1) % enemyImages.length;
                
                lastFrameChangeTime = currentTime;
                idleLoop();
            }
           
            // Draw the current frame
            g2d.setColor(new Color(0,0,0,0));
            g2d.drawImage(enemyImages[currentAnimationFrame], x, y, gp.tileSize, gp.tileSize, null);
            
            
            

        }
       
    }
    
    public void update() {
     
    
//        
//        if(t<1){
//            switch(a){
//                case "right":
//                    EmergeRight(t);
//                    
//                    break;
//                case "left":
//                    EmergeLeft(t);
//                    break;
//                case "center":
//                    EmergeCenter(t);
//                    break;
//            }
//            t=t+0.01;
        
        
        if(this.colition != null){
            colition.x = x;
            colition.y = y;
        }
        
        
    }
    
    public boolean checkCollision(Rectangle rect) {
     if(this.colition != null){ 
        if (rect.intersects(this.colition)) {
            isDestroyed = true;
            this.colition = null;
            enemyImages = null;
            return true; // Return true only if the enemy was not previously destroyed and there's a collision
        }
         // Return false if there's no collision
    }
     return false;
    }
   
    
//    public void EmergeLeft(double t){
//        
//        int StartX = 0;
//        int StartY = 200;
//        int EndX = 400;
//        int EndY = 150;
//        int ControlPoint1X = 550,ControlPoint1Y = 300;
//        int ControlPoint2X = 50,ControlPoint2Y = 600;
//        int ControlPoint3X = 10,ControlPoint3Y = 350;
//   
//
//        x = (int) (Math.pow(1-t, js4)*StartX + 4*Math.pow(1-t, 3)*ControlPoint1X*t + 6*Math.pow(1-t, 2)*ControlPoint2X*Math.pow(t,2) + 4*(1-t)*Math.pow(t, 3)*ControlPoint3X + Math.pow(t, 4)*EndX);
//        y = (int) (Math.pow(1-t, 4)*StartY + 4*Math.pow(1-t, 3)*ControlPoint1Y*t + 6*Math.pow(1-t, 2)*ControlPoint2Y*Math.pow(t,2) + 4*(1-t)*Math.pow(t, 3)*ControlPoint3Y + Math.pow(t, 4)*EndY);
//     
//     
//    }
//    public void EmergeRight(double t){
//        
//        int StartX = gp.screenColumn;
//        int StartY = 200;
//        int EndX = 500;
//        int EndY = 150;
//        int ControlPoint1X = 350,ControlPoint1Y = 300;
//        int ControlPoint2X = 910,ControlPoint2Y = 600;
//        int ControlPoint3X = 950,ControlPoint3Y = 350;
//
//        x = (int) (Math.pow(1-t, 4)*StartX + 4*Math.pow(1-t, 3)*ControlPoint1X*t + 6*Math.pow(1-t, 2)*ControlPoint2X*Math.pow(t,2) + 4*(1-t)*Math.pow(t, 3)*ControlPoint3X + Math.pow(t, 4)*EndX);
//        y = (int) (Math.pow(1-t, 4)*StartY + 4*Math.pow(1-t, 3)*ControlPoint1Y*t + 6*Math.pow(1-t, 2)*ControlPoint2Y*Math.pow(t,2) + 4*(1-t)*Math.pow(t, 3)*ControlPoint3Y + Math.pow(t, 4)*EndY);
//    }
//    public void EmergeCenter(double t){
//        
//        int StartX = gp.screenColumn/2;
//        int StartY = 10;
//        int EndX = gp.screenColumn/2;
//        int EndY = 200;
//        int ControlPoint1X = gp.screenColumn/2,ControlPoint1Y = 200;
//        int ControlPoint2X = 200,ControlPoint2Y = 600;
//        int ControlPoint3X = 850,ControlPoint3Y = 600;
//
//         x = (int) (Math.pow(1-t, 4)*StartX + 4*Math.pow(1-t, 3)*ControlPoint1X*t + 6*Math.pow(1-t, 2)*ControlPoint2X*Math.pow(t,2) + 4*(1-t)*Math.pow(t, 3)*ControlPoint3X + Math.pow(t, 4)*EndX);
//         y = (int) (Math.pow(1-t, 4)*StartY + 4*Math.pow(1-t, 3)*ControlPoint1Y*t + 6*Math.pow(1-t, 2)*ControlPoint2Y*Math.pow(t,2) + 4*(1-t)*Math.pow(t, 3)*ControlPoint3Y + Math.pow(t, 4)*EndY);
//    }
    private void idleLoop(){
         x = x + idleSpeed;
         

        if (x > idleMovement || x < 0) { 
            idleSpeed = -idleSpeed;
        }
    }
    public void attack1(){
        y=y+speed;
        if(y<originalY || gp.screenRow-40<y ){
            speed = -speed;
        }
        
    }
    
   
}

    
    

