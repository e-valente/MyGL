/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygl;

import java.util.ArrayList;

/**
 *
 * @author emanuel
 */
public class MyGL {
    
    int npoints;
    int nlines;
    ArrayList<Double[]> points;
    
    MyGL() {
        this.npoints = 0;
        this.nlines = 0;
        points = new ArrayList<Double[]>();

 
    
}
    public void drawPoint(double p0, double p1, double p2) {
        Double[] tmp_point = new Double[3];
        
        tmp_point[0] = p0;
        tmp_point[1] = p1;
        tmp_point[2] = p2;
        
        points.add(tmp_point);
        npoints++;  
        
        
    }
    
    public void listOfPoints(){
        if(npoints == 0)System.out.println("It has no points");
        else {
            System.out.println("There are: " + npoints + " points");
            
            
        }
    }
}
