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

        mygl.ortho(-400, 400, -400, 400, -400, 400);
        //mygl.perspective(60f, 1f, 0f, 100f);
        mygl.setViewPort(0f, 500, 0, 500);
        mygl.lookAt(0f, 0f, 10f, //coordenada camera
                0, 0, 0, //ponto p/ onde a camera aponta
                0, 1, 0); //view up

        //seta a cor de fundo
        mygl.setBackgroundColor(0f, 0f, 1f);
        
        //desenha pontos
        //mygl.drawPoint(10f, 10f, 10f);
        mygl.drawPoint(40f, 40f, 0f);
        
        //ponto colorido (RGB)
        mygl.drawPointRGB(200f, -200f, 0f, 0f, 0f, 0f);
        mygl.drawPointRGB(-40f, 40f, 0f, 1f, 0f, 0f);
  
        //desenha linha colorida
        mygl.drawLineRGB(-100f, 300f, 10f, 200f, 20f, 10f, 1f, 0f, 0f);
      
        //linha que será recortada (ver saída)
         mygl.drawLineRGB(-63f, -100f, 0f, 620f, 10f, 0f, 0f, 1f, 0f);
         mygl.drawLine(200f, -41f, 0f, 210f, -40f, 0f);
         mygl.drawLine(-50f, 0f, 0f, 0f, 50f, 0f);
         
        mygl.flush(); //multiplica as matrizes e desenha

       

    }
}
