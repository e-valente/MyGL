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
    
    //camera vector
    Float[] p_camera;
    Float[] p_ref;
    Float[] v_view_up;
    
   
    //clipping volume
    float xw_min;
    float xw_max;
    float yw_min ;
    float yw_max;
    float z_near;
    float z_far;
    
    //viewport
    float xv_min;
    float yv_min;
    float xv_max;
    float yv_max;
    
    //main matrices
    Float[] matrix_modelview;
    Float[] matrix_clipping_window;
    Float[] matrix_wc_vc;
    
    
    //our primitives
    ArrayList<Float[]> points;
    
    //total of primitives
    int npoints;
    int nlines;
   
    
    MyGL() {
       
        //clipping volume
        this.xw_min = -300;
        this.xw_max = 300;
        this.yw_min = -300;
        this.yw_max = 300;
        this.z_near = 300;
        this.z_far = -300;
    
        //viewport
        this.xv_min = 0;
        this.yv_min = 0;
        this.xv_max = 500;  /*width*/
        this.yv_max = 500;  /*height*/
    
        this.npoints = 0;
        this.nlines = 0;
        points = new ArrayList<Float[]>();
        
        //initialize our matrices
        matrix_modelview = new Float[16];
        matrix_clipping_window = new Float[16];
        matrix_wc_vc = new Float[16];
       
        Matrices.setIdentity(matrix_modelview);
        Matrices.setIdentity(matrix_clipping_window);
        Matrices.setIdentity(matrix_wc_vc);
        
        p_camera = new Float[3];
        p_camera[0] = 1f;
        p_camera[1] = 0f;
        p_camera[2] = 0f;
        
        v_view_up = new Float[3];
        v_view_up[0] = 0f;
        v_view_up[0] = 1f;
        v_view_up[0] = 0f;
        
        p_ref = new Float[3];
        p_ref[0] = 0f;
        p_ref[1] = 0f;
        p_ref[2] = 0f;
        
 
}
    public void setClippingWindow(float x_min, float x_max, float y_min, float y_max, float z_n, float z_f){
        this.xw_min = x_min;
        this.xw_max = x_max;
        this.yw_min = y_min;
        this.yw_max = y_max;
        this.z_near = z_n;
        this.z_far = z_f;
        
        
    }
    
     public void setViewPort(float x_min, float x_max, float y_min, float y_max){
        this.xv_min = x_min;
        this.xv_max = x_max;
        this.yv_min = y_min;
        this.yv_max = y_max;
        
        
    }
     
     public void lookAt(float xc, float yc, float zc, float xp, float yp, float zp, float x_vu, float y_vu, float z_vu) {
         this.p_camera[0] = xc;
         this.p_camera[1] = yc;
         this.p_camera[2] = zc;
         
         this.p_ref[0] = xp;
         this.p_ref[1] = yp;
         this.p_ref[2] = zp;
         
         this.v_view_up[0] = x_vu;
         this.v_view_up[1] = y_vu;
         this.v_view_up[2] = z_vu;
           
         
     }
    
    
    public void drawPoint(Float p0, Float p1, Float p2) {
        Float[] tmp_point = new Float[3];
        
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
    
    public void printMatrixModelView(){
        Matrices.printMatrix(matrix_modelview);
    }
    
    public void draw(){
        //principal método: multiplica as matrizes e desenha
        
        //obtem a matriz wc-vc
        Float[] mat1 = new Float[16];
        Float[] mat2 = new Float[16];
        
       
        //u, v e n serão os versores para a matriz de rotacao
        Float[] u = new Float[3];
        Float[] v = new Float[3];
        Float[] n = new Float[3];
        
        //obtendo matriz de translacao
        mat1[0] = 1f; mat1[1] = 0f; mat1[2] = 0f; mat1[3] = -this.p_ref[0]; 
        mat1[4] = 0f; mat1[5] = 1f; mat1[6] = 0f; mat1[7] = -this.p_ref[1]; 
        mat1[8] = 0f; mat1[9] = 0f; mat1[10] = 1f; mat1[11] = -this.p_ref[2];
        mat1[12] = 0f; mat1[13] = 0f; mat1[14] = 0f; mat1[15] = 1f; 
        
        
        //obtendo a matruz de rotacao
        n = Geometry.createVector(this.p_ref, this.p_camera);
        n = Geometry.normalizeVector(n);
        
        u = Geometry.crossProduct(this.v_view_up, n);
        u = Geometry.normalizeVector(u);
        
        
        v = Geometry.crossProduct(n, u);
        
       
       
        //obtendo matriz de translacao
        mat2[0] = u[0]; mat1[1] = u[1]; mat1[2] = u[2]; mat1[3] = 0f; 
        mat2[4] = v[0]; mat2[5] = v[1]; mat2[6] = v[2]; mat2[7] = 0f;
        mat2[8] = n[0]; mat2[9] = n[1]; mat2[10] = n[2]; mat2[11] = 0f; 
        mat2[12] = 0f; mat2[13] = 0f; mat2[14] = 0f; mat2[15] = 1f; 
        
        //falta criar método para multiplicar matrizes e multiplicar mat1xmat2 para 
        //determinar MATwc,vc
        
        
    }
    
}
