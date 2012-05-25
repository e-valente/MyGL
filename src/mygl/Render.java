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
        
        mygl.setClippingWindow(-400, 400, -400, 400, -400, 400);
        mygl.setViewPort(0f, 500, 0, 500);
        mygl.lookAt(0f, 0f, 1f, //coordenada camera
                    0, 0, 0, //ponto p/ onde a camera aponta
                    0, 1, 0); //view up
        
       // mygl.drawPoint(0f, 0f, 0f);
       // mygl.drawPoint(410f, 40f, 0f);
       // mygl.drawPoint(410f, 40f, 0f);
         mygl.drawPoint(-399f, 100f, 410f);
        //mygl.drawPoint(500f, -410f, 0f);
         mygl.drawLine(-401f, 0f, 0f, -402f, 0f, 0f);
       // mygl.drawLine(-200f, 0f, 0f, 200f, 0f, 0f);
        mygl.flush(); //multiplica as matrizes e desenha
   
    }
    
}
