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
        mygl.listOfPoints();
        
        mygl.drawPoint(0, 1, 1);
        mygl.listOfPoints();
        
        
    }
    
}
