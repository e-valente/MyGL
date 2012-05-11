/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygl;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author emanuel
 */
public class Draw extends JPanel{
    ArrayList<Float[]> points;
    
    Draw(ArrayList<Float[]> p) {
       points = new ArrayList<Float[]>();
       
       points = p;
    }
     @Override
    public void paint(Graphics g){
         Float[] my_point = new Float[4];
         Float[] my_pointbk = new Float[4];
         
         my_point = points.get(0);
         
         my_pointbk[0] = my_point[0];
         my_pointbk[1] = my_point[1];
         
        Graphics2D g2d = (Graphics2D)g;
        g2d.drawString("vai", 50, 50);
        System.out.println("os valores saooo: " + my_point[0].intValue() + " , " + my_point[1].intValue() + " , " + my_point[2].intValue());
        
        
        //correcao para sistema corrdenada mygl -> java2d
        
        if(my_point[0] > 0.f) my_point[0] = 100 + my_point[0];
        else my_point[0] = 100 +my_point[0];
        
        if(my_point[1] > 0.f) my_point[1] = 100 - my_point[1];
        else my_point[1] = 100 -my_point[1];
        
        System.out.println("os valores sao: " + my_point[0].intValue() + " , " + my_point[1].intValue() + " , " + my_point[2].intValue());
        
        //g2d.drawLine(150, 50, 150, 50);
        g2d.drawLine(my_point[0].intValue(), my_point[1].intValue(), my_point[0].intValue(), my_point[1].intValue());
        
        my_point[0] = my_pointbk[0];
        my_point[1] = my_pointbk[1];
      
        
        
    }



    
    
}
