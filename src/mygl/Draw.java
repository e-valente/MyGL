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
public class Draw extends JPanel {

    ArrayList<Points> points;
    ArrayList<Lines> lines;
    int n_points, n_lines;
    //coordenadas da viewport para ser feita a correcao
    //das corrdenadas para janela 2d (java).
    Float x_max, x_min, y_max, y_min;

    Draw(ArrayList<Points> p, ArrayList<Lines> l, int npoints, int nlines, float xv_max, float xv_min, float yv_max, float yv_min) {

        points = new ArrayList<Points>();
        lines = new ArrayList<Lines>();

        points = p;
        lines = l;
        n_points = npoints;
        n_lines = nlines;
        x_max = xv_max;
        x_min = xv_min;
        y_max = yv_max;
        y_min = yv_min;
    }

    @Override
    public void paint(Graphics g) {
        Points _my_point = new Points();
        Float[] my_point = new Float[4];
        Lines my_line = new Lines();
        Float[] my_point2 = new Float[4];


        Graphics2D g2d = (Graphics2D) g;

        for (int i = 0; i < n_points; i++) {

            _my_point = points.get(i);
            my_point = _my_point.p;
            System.out.println("pontos desenhados pela linha " + my_point[0].intValue() + " , " + my_point[1].intValue() + " , " + my_point[2].intValue());
            g2d.drawLine(my_point[0].intValue(), my_point[1].intValue(), my_point[0].intValue(), my_point[1].intValue());

        }


        //desenha as linhas

        for (int i = 0; i < n_lines; i++) {
            my_line = lines.get(i);
            my_point = my_line.p1;
            my_point2 = my_line.p2;
            // my_point[2] = my_line.p1[2];


            System.out.println("pontos desenhados pela linha " + my_point[0].intValue() + " , " + my_point[1].intValue() + " , " + my_point2[0].intValue() + " , " + my_point2[1].intValue());

            g2d.drawLine(my_point[0].intValue(), my_point[1].intValue(), my_point2[0].intValue(), my_point2[1].intValue());

        }
    }
}
