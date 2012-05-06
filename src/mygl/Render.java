/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygl;

/**
 *
 * @author emanuel
 */
public class Render {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        

        MyGL mygl = new MyGL();
        
        mygl.setClippingWindow(-200, 200, -200, 200, -100, 100);
        mygl.lookAt(1, 1, 1, 0, 0, 0, 0, -1, 0);
        mygl.drawPoint(0f, 1f, 1f);
        mygl.draw(); //multiplica as matrizes e desenha
       
  
        

        
    
        
        
    }
    
}
