/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Actors;

import java.awt.Rectangle;





/**
 *
 * @author dell
 */

    public class Bullet extends Character{
        
    
        
    
    //constructor
        public Bullet(int x , int y){
            this.x = x;
            this.y = y;
            speed = 20;
            
            colition = new Rectangle(this.x+25,this.y+5,14,23);
            
         }
    }

  
    
    
    
    



