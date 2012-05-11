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
        
        mygl.setClippingWindow(-100, 100, -100, 100, -100, 100);
        mygl.lookAt(0, 0, 1, //coordenada camera
                    0, 0, 0, //ponto p/ onde a camera aponta
                    0, 1, 0); //view up
        
        mygl.drawPoint(-50f, 0f, 0f);
        mygl.draw(); //multiplica as matrizes e desenha
       
  
        

        
    
        
        
    }
    
}
