
package Stage;

import Actors.Bullet;
import Actors.Enemy;
import Actors.Player;
import Main.GamePanel;
import Main.Key;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Stage {
    
    private List<Enemy> enemies;
    GamePanel gp;
    private List<Bullet> bullets;
    Player player;
    private boolean lastFireState = false;
    Key keyHandler;
    
    
    private long lastFireTime; 
    private long fireCooldown = 500;
    
    
     BufferedImage bulletImage;
     
   private int currentAttackerIndex = 0;
    
    public Stage(Player player , GamePanel gp,Key KeyHandler) {
        bullets = new ArrayList<>();
        enemies = new ArrayList<>();
        
        this.player = player;
        this.gp = gp;
        this.initialize();
        this.keyHandler = KeyHandler;
        
    }



    public void update() {
        
      for (Enemy enemy : enemies) {
            enemy.update();
          try {
              player.checkCollisionWithEnemies(enemy);
          } catch (IOException ex) {
              Logger.getLogger(Stage.class.getName()).log(Level.SEVERE, null, ex);
          }
        }
        AttackPattern(enemies);
        if (keyHandler.fire && !lastFireState) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastFireTime >= fireCooldown) {
                fireBullet(); // Fire at all enemies
                lastFireTime = currentTime;
            }
        }
        lastFireState = keyHandler.fire;
       
        Iterator<Bullet> iterator = bullets.iterator();
       
        while (iterator.hasNext()) {
            
            Bullet bullet = iterator.next();
            bullet.colition.y = bullet.y;
            bullet.y -= bullet.speed;
            
            // Check for collision with enemies
            for (Enemy enemy : enemies) {
                if (enemy.colition != null && enemy.checkCollision(bullet.colition) || bullet.y<0) {
                    iterator.remove();
                    break;
                }
            }
            

           
        }
      
    }
    

    private void fireBullet() {
        Bullet bullet = new Bullet(player.x, player.y);
        bullets.add(bullet);
    }
   public void initialize() {
       
        int numEnemies = 5; 

        for(int i = 1; i <= numEnemies; i++) {
            enemies.add(new Enemy(gp, 150+i*100, 100)); 
        }
        for(int i = 1; i <= numEnemies+2; i++) {
            enemies.add(new Enemy(gp, 50+i*102, 200)); 
        }
        for(int i = 1; i <= numEnemies+2; i++) {
            enemies.add(new Enemy(gp, 48+i*100, 300)); 
        }
        for(int i = 1; i <= numEnemies; i++) {
            enemies.add(new Enemy(gp, 150+i*106, 400));
        }
    
        try {
            bulletImage = ImageIO.read(getClass().getResourceAsStream("/source/player/sum.png"));
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
}

   

    public void draw(Graphics2D g2d) {
        
        for(Enemy enemy: enemies){
            enemy.draw(g2d);
        }
        
        
         for (Bullet bullet : bullets) {
           
          g2d.draw(bullet.colition);
          g2d.drawImage(bulletImage, bullet.x,bullet.y-10,gp.tileSize,gp.tileSize,null);
          
         
          }
        
        
    }
   private void AttackPattern(List<Enemy> enemies) {
     
    
    for (Enemy enemy : enemies) {
        
        if (enemies.indexOf(enemy) == currentAttackerIndex) {
            enemy.attack1(); 
            currentAttackerIndex = (currentAttackerIndex + 1) % enemies.size(); 
            break; 
        }
    }
}
   
  
    public boolean isCompleted() {
       if(enemies.isEmpty()){
           System.out.println("suga");
       }
        return false;
    }
}

