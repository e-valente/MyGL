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
        mygl.setViewPort(0f, 400, 0, 400);
        mygl.lookAt(0f, 0f, 1f, //coordenada camera
                0, 0, 0, //ponto p/ onde a camera aponta
                0, 1, 0); //view up

        mygl.setBackgroundColor(0f, 0f, 1f);
        //mygl.drawPoint(0f, 0f, 0f);
        //mygl.drawPoint(400f, 40f, 0f);
        mygl.drawPointRGB(40f, -40f, 0f, 0f, 1f, 1f);
        mygl.drawPointRGB(-40f, 40f, 0f, 1f, 0f, 0f);
        //mygl.drawPoint(-39f, 150f, 400f);
        //mygl.drawPoint(500f, -410f, 0f);
        mygl.drawLineRGB(-43f, 0f, 0f, 42f, 0f, 0f, 1f, 0f, 0f);
        mygl.drawLineRGB(-63f, 100f, 0f, 62f, 10f, 0f, 0f, 1f, 0f);
        mygl.drawLine(200f, -41f, 0f, 210f, -40f, 0f);
        //mygl.drawLine(-50f, 0f, 0f, 0f, 50f, 0f);
        mygl.flush(); //multiplica as matrizes e desenha

    }
}
