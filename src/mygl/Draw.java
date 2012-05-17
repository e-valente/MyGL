/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygl;

import java.awt.Color;
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
    int n_points;
    //coordenadas da viewport para ser feita a correcao
    //das corrdenadas para janela 2d (java).
    Float x_max, x_min, y_max, y_min;
    
    
    Draw(ArrayList<Float[]> p, int npoints, float xv_max, float xv_min, float yv_max, float yv_min) {
       points = new ArrayList<Float[]>();
      
       points = p;
       n_points = npoints;
       x_max = xv_max;
       x_min = xv_min;
       y_max = yv_max;
       y_min = yv_min;
    }
     @Override
    public void paint(Graphics g){
         Float[] my_point = new Float[4];
         Float[] my_pointbk = new Float[4];
         Graphics2D g2d = (Graphics2D)g;
         
          System.out.println("n_points:" + n_points);
         for(int i=0; i< n_points; i++){
                my_point = points.get(i);
         
                my_pointbk[0] = my_point[0];
                my_pointbk[1] = my_point[1];
         
       
        //g2d.drawString("vai", 50, 50);
                 System.out.println("os valores saooo: " + my_point[0].intValue() + " , " + my_point[1].intValue() + " , " + my_point[2].intValue());
        
        
        //correcao para sistema corrdenada mygl -> java2d
        //em relacao a clipping window
        
                 if(my_point[0] > 0.f) my_point[0] = ( (x_max - x_min)/2 ) + my_point[0];
                   else my_point[0] = ( (x_max - x_min)/2 ) + my_point[0];
        
                  if(my_point[1] > 0.f) my_point[1] = ( (y_max - y_min)/2 ) - my_point[1];
                  else my_point[1] = ( (y_max - y_min)/2 ) - my_point[1];
        
                  System.out.println("os valores sao: " + my_point[0].intValue() + " , " + my_point[1].intValue() + " , " + my_point[2].intValue());
        
        //g2d.drawLine(150, 50, 150, 50);
                             
                  g2d.setColor(Color.green);
                  g2d.drawLine(my_point[0].intValue(), my_point[1].intValue(), my_point[0].intValue(), my_point[1].intValue());
        
                  my_point[0] = my_pointbk[0];
                  my_point[1] = my_pointbk[1];
      
    }

 }
             
}
