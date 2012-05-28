/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygl;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 *
 * @author emanuel
 */
public class MyGL {

    //tipo de projecoes suportadas
    boolean orthogonal;
    boolean perspective;
    
    //camera vector
    Float[] p_camera;
    Float[] p_ref;
    Float[] v_view_up;
    //clipping volume
    float xw_min;
    float xw_max;
    float yw_min;
    float yw_max;
    float z_near;
    float z_far;
    //viewport
    float xv_min;
    float yv_min;
    float xv_max;
    float yv_max;
    //variaveis para proj perspectiva
    double theta;
    //main matrices
    Float[] matrix_modelview;
    Float[] matrix_clipping_window;
    Float[] matrix_wc_vc;
    Float[] matrix_norm_sym_pers; 
    //our primitives
    ArrayList<Points> points;
    ArrayList<Lines> lines;
    //total of primitives
    int npoints;
    int nlines;
    //cor de fundo
    Float[] background_color;

    MyGL() {
        
        orthogonal = true;
        perspective = false;
        
        //60graus padrao 
        this.theta = (Math.PI/3);

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
        this.xv_max = 200;  /*
         * width
         */
        this.yv_max = 200;  /*
         * height
         */

        this.npoints = 0;
        this.nlines = 0;
        points = new ArrayList<Points>();
        lines = new ArrayList<Lines>();

        //initialize our matrices
        matrix_modelview = new Float[16];
        matrix_clipping_window = new Float[16];
        matrix_wc_vc = new Float[16];
        matrix_norm_sym_pers = new Float[16];

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
        
        //cor branca é padrao
        background_color = new Float[] {1f, 1f, 1f}; 


    }

    /**
     * Habilita projecão ortogonal
     * @param x_min  Valor mínimo da coordenada no eixo x.
     * @param x_max  Valor máximo da coordenada no eixo x.
     * @param y_min  Valor mínimo da coordenada no eixo y.
     * @param y_max  Valor máximo da coordenada no eixo y.
     * @param z_n    Valor mínimo da coordenada no eixo z.
     * @param z_f    Valor máximo da coordenada no eixo z.
     */
    public void ortho(float x_min, float x_max, float y_min, float y_max, float z_n, float z_f) {
        
        orthogonal = true;
        perspective = false;
        
        setClippingWindow(x_min, x_max, y_min, y_max, z_n, z_f);
        
    }
    
    /**
     * Habilita projecão perspectiva
     * 
     * @param theta Valor do ângulo para o frustum (em graus)
     * @param aspect Razão de aspecto (largura/altura)
     * @param dnear Distância no eixo de onde está a camera
     * @param dfar  Distância no eixo de onde está a camera (dzar > dnear > 0)
     */
    public void perspective(double theta, float aspect, float dnear, float dfar) {
        
        this.theta = Math.toRadians(theta);
        
       
        
        //linha1
        matrix_norm_sym_pers[0] = (float) (1f / (Math.tan(theta/2) * aspect));
        matrix_norm_sym_pers[1] = 0f;
        matrix_norm_sym_pers[2] = 0f;
        matrix_norm_sym_pers[3] = 0f;
        
        //linha2
        matrix_norm_sym_pers[4] = 0f;
        matrix_norm_sym_pers[5] = (float)(1f / Math.tan(theta/2));
        matrix_norm_sym_pers[6] = 0f;
        matrix_norm_sym_pers[7] = 0f;
        
        //linha3
        matrix_norm_sym_pers[8] = 0f;
        matrix_norm_sym_pers[9] = 0f;
        matrix_norm_sym_pers[10] = (-dfar + dnear)/(dfar-dnear);
        matrix_norm_sym_pers[11] = (-2*dfar*dnear)/(dfar - dnear);
        
        //linha4
        matrix_norm_sym_pers[12] = 0f;
        matrix_norm_sym_pers[13] = 0f;
        matrix_norm_sym_pers[14] = -1f;
        matrix_norm_sym_pers[15] = 0f;
        
        this.perspective = true;
        this.orthogonal = false;
        
        System.out.println("Perspective Mode: on");
    }

    /**
     * Seta a Viewport
     * @param x_min - Mínimo na coordenada x
     * @param x_max - Largura
     * @param y_min - Mínimo na coordenada y
     * @param y_max - altura
     */
    public void setViewPort(float x_min, float x_max, float y_min, float y_max) {
        this.xv_min = x_min;
        this.xv_max = x_max;
        this.yv_min = y_min;
        this.yv_max = y_max;


    }

    /**
     * Posiciona a câmera
     * @param xc coordenada da câmera em x.
     * @param yc coordenada da câmera em y.
     * @param zc coordenada da câmera em y.
     * @param xp coordenada para onde a câmera aponta em x.
     * @param yp coordenada para onde a câmera aponta em y.
     * @param zp coordenada para onde a câmera aponta em z.
     * @param x_vu coordenada do vetor viewup em x.
     * @param y_vu coordenada do vetor viewup em y.
     * @param z_vu coordenada do vetor viewup em z.
     */
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

    /**
     * Desenha um ponto na tela
     * @param p0 coordenada do ponto em x.
     * @param p1 coordenada do ponto em y.
     * @param p2  coordenada do ponto em z.
     */
    public void drawPoint(Float p0, Float p1, Float p2) {
        Points tmp_point = new Points();

        tmp_point.p[0] = p0;
        tmp_point.p[1] = p1;
        tmp_point.p[2] = p2;
        tmp_point.p[3] = 1f;

        points.add(tmp_point);
        npoints++;


    }
    
    /**
     * Desenha um ponto colorido na tela
     * @param p0 coordenada do ponto em x.
     * @param p1 coordenada do ponto em y.
     * @param p2 coordenada do ponto em z.
     * @param c1 coordenada da cor vermelha da componente RGB (0 até 1).
     * @param c2 coordenada da cor verde da componente RGB (0 até 1).
     * @param c3 coordenada da cor azul da componente RGB (0 até 1).
     */
    public void drawPointRGB(Float p0, Float p1, Float p2, float c1, float c2, float c3) {
        Points tmp_point = new Points();

        tmp_point.p[0] = p0;
        tmp_point.p[1] = p1;
        tmp_point.p[2] = p2;
        tmp_point.p[3] = 1f;
        
        tmp_point.color[0] = c1;
        tmp_point.color[1] = c2;
        tmp_point.color[2] = c3;
        

        points.add(tmp_point);
        npoints++;


    }

    /**
     * Desenha uma linha na tela
     * @param p1_x coordenada do ponto 1 em x.
     * @param p1_y coordenada do ponto 1 em y.
     * @param p1_z coordenada do ponto 1 em z.
     * @param p2_x coordenada do ponto 2 em x.
     * @param p2_y coordenada do ponto 2 em y.
     * @param p2_z coordenada do ponto 2 em z.
     */
    public void drawLine(Float p1_x, Float p1_y, Float p1_z, Float p2_x, Float p2_y, Float p2_z) {

        Lines line_tmp = new Lines();

        line_tmp.p1[0] = p1_x;
        line_tmp.p1[1] = p1_y;
        line_tmp.p1[2] = p1_z;
        line_tmp.p1[3] = 1f;
        line_tmp.p2[0] = p2_x;
        line_tmp.p2[1] = p2_y;
        line_tmp.p2[2] = p2_z;
        line_tmp.p2[3] = 1f;


        lines.add(line_tmp);
        nlines++;


    }
    
    /**
     * Desenha uma linha colorida na tela
     * 
     * @param p1_x coordenada do ponto 1 em x
     * @param p1_y coordenada do ponto  em y
     * @param p1_z coordenada do ponto 1 em z
     * @param p2_x coordenada do ponto 2 em x
     * @param p2_y coordenada do ponto 2 em y
     * @param p2_z coordenada do ponto 2 em z
     * @param c1 coordenada da cor vermelha da componente RGB (0 até 1).
     * @param c2 coordenada da cor verde da componente RGB (0 até 1).
     * @param c3 coordenada da cor azul da componente RGB (0 até 1).
     */
    public void drawLineRGB(Float p1_x, Float p1_y, Float p1_z, Float p2_x, Float p2_y, Float p2_z, float c1, float c2, float c3) {

        Lines line_tmp = new Lines();

        line_tmp.p1[0] = p1_x;
        line_tmp.p1[1] = p1_y;
        line_tmp.p1[2] = p1_z;
        line_tmp.p1[3] = 1f;
        line_tmp.p2[0] = p2_x;
        line_tmp.p2[1] = p2_y;
        line_tmp.p2[2] = p2_z;
        line_tmp.p2[3] = 1f;
        
        line_tmp.color[0] = c1;
        line_tmp.color[1] = c2;
        line_tmp.color[2] = c3;


        lines.add(line_tmp);
        nlines++;


    }

    
    public void printMatrixModelView() {
        Matrices.printMatrix(matrix_modelview);
    }

    /**
     * Multiplica as matrizes e tenta desenhar o mais rápido possível
     */
    public void flush() {
        //principal método: multiplica as matrizes e desenha

        //obtem a matriz wc-vc
        Float[] mat_t = new Float[16];  //matriz translacao
        Float[] mat_r = new Float[16];   //matriz rotacao
        Float[] mat_ortho_norm = new Float[16];   //matriz normalizada cubo lado 2
        Float[] mat_norm_3dscreen = new Float[16];   //matriz normalizada para coordenadas de tela (ultimo passo)


        //u, v e n serão os versores para a matriz de rotacao
        Float[] u = new Float[3];
        Float[] v = new Float[3];
        Float[] n = new Float[3];

        //obtendo matriz de translacao
        mat_t[0] = 1f;
        mat_t[1] = 0f;
        mat_t[2] = 0f;
        mat_t[3] = -this.p_camera[0];
        mat_t[4] = 0f;
        mat_t[5] = 1f;
        mat_t[6] = 0f;
        mat_t[7] = -this.p_camera[1];
        mat_t[8] = 0f;
        mat_t[9] = 0f;
        mat_t[10] = 1f;
        mat_t[11] = -this.p_camera[2];
        mat_t[12] = 0f;
        mat_t[13] = 0f;
        mat_t[14] = 0f;
        mat_t[15] = 1f;


        //obtendo a matruz de rotacao
        n = Geometry.createVector(this.p_ref, this.p_camera);
        n = Geometry.normalizeVector(n);

        u = Geometry.crossProduct(this.v_view_up, n);
        u = Geometry.normalizeVector(u);


        v = Geometry.crossProduct(n, u);



        //obtendo matriz de rotacao
        mat_r[0] = u[0];
        mat_r[1] = u[1];
        mat_r[2] = u[2];
        mat_r[3] = 0f;
        mat_r[4] = v[0];
        mat_r[5] = v[1];
        mat_r[6] = v[2];
        mat_r[7] = 0f;
        mat_r[8] = n[0];
        mat_r[9] = n[1];
        mat_r[10] = n[2];
        mat_r[11] = 0f;
        mat_r[12] = 0f;
        mat_r[13] = 0f;
        mat_r[14] = 0f;
        mat_r[15] = 1f;

        //Mat_wc_vc = R*T
        matrix_wc_vc = Matrices.multiplyMatrix4X4(mat_r, mat_t);

        if (orthogonal) {

            //basta agora normalizar num cubo de lado 2
            //Mat_ortho_norm
            //linha1
            mat_ortho_norm[0] = 2 / ((this.xw_max - this.xw_min));
            mat_ortho_norm[1] = 0f;
            mat_ortho_norm[2] = 0f;
            mat_ortho_norm[3] = -((this.xw_max + this.xw_min) / (this.xw_max - this.xw_min)); //erro estava aqui

            //linha2
            mat_ortho_norm[4] = 0f;
            mat_ortho_norm[5] = 2 / ((this.yw_max - this.yw_min));
            mat_ortho_norm[6] = 0f;
            mat_ortho_norm[7] = -((this.yw_max + this.yw_min) / (this.yw_max - this.yw_min));

            //linha3
            mat_ortho_norm[8] = 0f;
            mat_ortho_norm[9] = 0f;
            mat_ortho_norm[10] = -2f / ((this.z_near - this.z_far));
            mat_ortho_norm[11] = (this.z_near + this.z_far) / (this.z_near - this.z_far);

            //linha4
            mat_ortho_norm[12] = 0f;
            mat_ortho_norm[13] = 0f;
            mat_ortho_norm[14] = 0f;
            mat_ortho_norm[15] = 1f;


            matrix_modelview = Matrices.multiplyMatrix4X4(mat_ortho_norm, matrix_wc_vc);


        } //fim "if orthogonal"
        
        if(perspective) {
            //defineremos a matrix de normalizacao em perspectiva
            //esta deverá ser multiplicada pela Mwc-vc
            matrix_modelview = Matrices.multiplyMatrix4X4(matrix_norm_sym_pers, matrix_wc_vc);
            
            
            
        }
        
        
            /**
             * ***************RECORTE******************************************
             */
            //neste ponto teremos a matriz que normaliza as coordenadas num cubo 
            //de lado 2 (-1 até 1).
            Float[] ppp = new Float[4];
            Float[] ppp1 = new Float[4];
            Points tmp_point = new Points();

            // System.out.println("npoints:" + npoints);

            for (int i = 0; i < npoints; i++) {

                //ppp1 = points.get(i);
                tmp_point = points.get(i);

                ppp1[0] = tmp_point.p[0];
                ppp1[1] = tmp_point.p[1];
                ppp1[2] = tmp_point.p[2];
                ppp1[3] = tmp_point.p[3];

                //System.out.println("ponto que peguei antes do java 2d:" + ppp1[0] + " " + ppp1[1] + " " + ppp1[2]);
                //System.out.println("tamanho da lista: " + points.size());


                if(perspective) {
                    ppp = Matrices.multiplyMatrix4X4ByVertex2(matrix_modelview, ppp1);
                    
                }else{
                    ppp = Matrices.multiplyMatrix4X4ByVertex(matrix_modelview, ppp1);
                }
                
                //printMatrixModelView();

              //  System.out.println("ponto que peguei depois do java2d:" + ppp[0] + " " + ppp[1] + " " + ppp[2]);


                // Geometry.printVector(ppp);
                tmp_point.p[0] = ppp[0];
                tmp_point.p[1] = ppp[1];
                tmp_point.p[2] = ppp[2];
                tmp_point.p[3] = ppp[3];

                points.set(i, tmp_point);


            }

            Lines line_tmp = new Lines();

            for (int i = 0; i < nlines; i++) {

                line_tmp = lines.get(i);

                ppp = line_tmp.p1;
                ppp1 = line_tmp.p2;

                //System.out.println("ponto que peguei da linha antes de multiplicar " + ppp[3]);
                //System.out.println("ponto que peguei da linha antes de multiplicar:" + ppp1[3]);

                if(perspective) {
                     ppp = Matrices.multiplyMatrix4X4ByVertex2(matrix_modelview, ppp);
                     ppp1 = Matrices.multiplyMatrix4X4ByVertex2(matrix_modelview, ppp1);
                    
                }
                else {
                    
                    ppp = Matrices.multiplyMatrix4X4ByVertex(matrix_modelview, ppp);
                    ppp1 = Matrices.multiplyMatrix4X4ByVertex(matrix_modelview, ppp1);
                    
                }
              
               

                //System.out.println("ponto que peguei da linha depois de multiplicar:" + ppp[3]);
                //System.out.println("ponto que peguei da linha depois de mult:" + ppp1[3]);


                line_tmp.p1 = ppp;
                line_tmp.p2 = ppp1;

                lines.set(i, line_tmp);

            }

            clippingPointsNormalized();
            clippingLinesNormalized();





            /**
             * ** FIM RECORTE
             * ****************************************************
             */
            //obtendo mat_norm_3dscreen
            //linha1
            mat_norm_3dscreen[0] = (this.xv_max - this.xv_min) / 2;
            mat_norm_3dscreen[1] = 0f;
            mat_norm_3dscreen[2] = 0f;
            mat_norm_3dscreen[3] = (this.xv_max + this.xv_min) / 2;

            //linha2
            mat_norm_3dscreen[4] = 0f;
            mat_norm_3dscreen[5] = (this.yv_max - this.yv_min) / 2;
            mat_norm_3dscreen[6] = 0f;
            mat_norm_3dscreen[7] = (this.yv_max + this.yv_min) / 2;

            //linha3
            mat_norm_3dscreen[8] = 0f;
            mat_norm_3dscreen[9] = 0f;
            mat_norm_3dscreen[10] = 1f / 2;
            mat_norm_3dscreen[11] = 1f / 2;

            //linha4
            mat_norm_3dscreen[12] = 0f;
            mat_norm_3dscreen[13] = 0f;
            mat_norm_3dscreen[14] = 0f;
            mat_norm_3dscreen[15] = 1f;


            //matrix_modelview = Matrices.multiplyMatrix4X4(matrix_modelview, mat_norm_3dscreen);
            matrix_modelview = Matrices.multiplyMatrix4X4(mat_norm_3dscreen, matrix_modelview);




            Matrices.printMatrix(matrix_modelview);
            
            


            //Transforma pontos e linhas para o sistema de coordenadas do java2d
            //clippingPoints();
            for (int i = 0; i < npoints; i++) {

                //ppp1 = points.get(i);
                tmp_point = points.get(i);

                ppp1[0] = tmp_point.p[0];
                ppp1[1] = tmp_point.p[1];
                ppp1[2] = tmp_point.p[2];
                ppp1[3] = tmp_point.p[3];

                //os pontos ja estao normalizados no cubo, portanto basta colocar
                //nas coordenadas de tela
                ppp = Matrices.multiplyMatrix4X4ByVertex(mat_norm_3dscreen, ppp1);


                // Geometry.printVector(ppp);
                tmp_point.p[0] = ppp[0];
                tmp_point.p[1] = ppp[1];
                tmp_point.p[2] = ppp[2];
                tmp_point.p[3] = ppp[3];
                // System.out.println("ponto que peguei depois de transformar para coordenadas de tela:" + ppp[0] + " " + ppp[1] + " " + ppp[2]);


                points.set(i, tmp_point);


            }


            for (int i = 0; i < nlines; i++) {

                line_tmp = lines.get(i);

                ppp[0] = line_tmp.p1[0];
                ppp[1] = line_tmp.p1[1];
                ppp[2] = line_tmp.p1[2];
                ppp[3] = line_tmp.p1[3];
                
                ppp1[0] = line_tmp.p2[0];
                ppp1[1] = line_tmp.p2[1];
                ppp1[2] = line_tmp.p2[2];
                ppp1[3] = line_tmp.p2[3];
                  
                //System.out.println("x do Ponto1: " + ppp[0]);

                //os pontos ja estao normalizados no cubo, portanto basta colocar
                //nas coordenadas de tela
                ppp = Matrices.multiplyMatrix4X4ByVertex(mat_norm_3dscreen, ppp);
                ppp1 = Matrices.multiplyMatrix4X4ByVertex(mat_norm_3dscreen, ppp1);

                line_tmp.p1[0] = ppp[0];
                line_tmp.p1[1] = ppp[1];
                line_tmp.p1[2] = ppp[2];
                line_tmp.p1[3] = ppp[3];
                line_tmp.p2[0] = ppp1[0];
                line_tmp.p2[1] = ppp1[1];
                line_tmp.p2[2] = ppp1[2];
                line_tmp.p2[3] = ppp1[3];
                
              //  System.out.println("wdo Ponto1: " + line_tmp.p1[3]);

                lines.set(i, line_tmp);

            }




        fixPointsToJava2DCoordinates();
        fixLinesToJava2DCoordinates();
  

        Draw d = new Draw(points, lines, npoints, nlines, xv_max, xv_min, yv_max, yv_min);

        JFrame frame = new JFrame("Testando MyGL");
        frame.setSize((int) (xv_max - xv_min), (int) (yv_max - yv_min)); //largura, altura
        frame.add(d);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        Color c;
        c = new Color(background_color[0], background_color[1],background_color[2]);
      
       // System.out.println("valor de c: \n" + c);
       
        frame.setBackground(c);
        

        //d.paint(g);

    }
    
    /**
     * Seta a cor de fundo da tela
     * @param c vetor das componentes RGB (0 até 1)
     */
    void setBackgroundColor(Float[] c){
        background_color[0] = c[0];
        background_color[1] = c[1];
        background_color[2] = c[2];
        
    }
    /**
     * Seta a cor de fundo da tela
     * @param c0 coordenada vermelha da componente RGB (0 até 1)
     * @param c1 coordenada verde da componente RGB (0 até 1)
     * @param c2 coordenada azul da componente RGB (0 até 1)
     */
    void setBackgroundColor(float c0, float c1, float c2){
        background_color[0] = c0;
        background_color[1] = c1;
        background_color[2] = c2;
        
    }
    /************METODOS PRIVADOS**************************************/
    
    private void setClippingWindow(float x_min, float x_max, float y_min, float y_max, float z_n, float z_f) {
        this.xw_min = x_min;
        this.xw_max = x_max;
        this.yw_min = y_min;
        this.yw_max = y_max;
        this.z_near = z_n;
        this.z_far = z_f;


    }
    

    /**
     * Transforma as coordenadas dos pontos nas corrdenadas de tela do Java2D
     */
    private void fixPointsToJava2DCoordinates() {
        Points _my_point = new Points();
        Float[] my_point = new Float[4];

        for (int i = 0; i < npoints; i++) {
            _my_point = points.get(i);
            // my_point = points.get(i);
            my_point[0] = _my_point.p[0];
            my_point[1] = _my_point.p[1];
            my_point[2] = _my_point.p[2];
            my_point[3] = _my_point.p[3];

           // System.out.println("mmmPonto antes de ser processado para java2d: " + my_point[0] + " " + my_point[1] + " " + my_point[2]);


            if (my_point[0] > 0.f) {
                my_point[0] = ((this.xv_max - this.xv_min) / 2) + my_point[0];
            } else {
                my_point[0] = ((this.xv_max - this.xv_min) / 2) + my_point[0];
            }

            if (my_point[1] > 0.f) {
                my_point[1] = ((this.yv_max - this.yv_min) / 2) - my_point[1];
            } else {
                my_point[1] = ((this.yv_max - this.yv_min) / 2) - my_point[1];
            }

           // System.out.println("ponto que será desenhado" + my_point[0] + " " + my_point[1] + " " + my_point[2]);
            _my_point.p[0] = my_point[0];
            _my_point.p[1] = my_point[1];
            _my_point.p[2] = my_point[2];
            _my_point.p[3] = my_point[3];
            points.set(i, _my_point);

        }

    }

     /**
     * Transforma as coordenadas das linhas nas corrdenadas de tela do Java2D
     */
    private void fixLinesToJava2DCoordinates() {
        Float[] my_point = new Float[4];
        Lines my_line = new Lines();
        Float[] my_point2 = new Float[4];

        for (int i = 0; i < nlines; i++) {
            my_line = lines.get(i);
            my_point[0] = my_line.p1[0];
            my_point[1] = my_line.p1[1];
            my_point[2] = my_line.p1[2];
            my_point[3] = my_line.p1[3];
            my_point2[0] = my_line.p2[0];
            my_point2[1] = my_line.p2[1];
            my_point2[2] = my_line.p2[2];
            my_point2[3] = my_line.p2[3];
            // my_point = points.get(i);
            
            //System.out.println("Pontos ants de passar para corrdenada do java - total de linhas: \n" + nlines);
            //System.out.println("Ponto1: "+my_point[0] + my_point[1] + my_point[2]);



            if (my_point[0] > 0.f) {
                my_point[0] = ((this.xv_max - this.xv_min) / 2) + my_point[0];
            } else {
                my_point[0] = ((this.xv_max - this.xv_min) / 2) + my_point[0];
            }

            if (my_point[1] > 0.f) {
                my_point[1] = ((this.yv_max - this.yv_min) / 2) - my_point[1];
            } else {
                my_point[1] = ((this.yv_max - this.yv_min) / 2) - my_point[1];
            }


            //ponto2  
            if (my_point2[0] > 0.f) {
                my_point2[0] = ((this.xv_max - this.xv_min) / 2) + my_point2[0];
            } else {
                my_point2[0] = ((this.xv_max - this.xv_min) / 2) + my_point2[0];
            }

            if (my_point2[1] > 0.f) {
                my_point2[1] = ((this.yv_max - this.yv_min) / 2) - my_point2[1];
            } else {
                my_point2[1] = ((this.yv_max - this.yv_min) / 2) - my_point2[1];
            }

            my_line.p1[0] = my_point[0];
            my_line.p1[1] = my_point[1];
            my_line.p1[2] = my_point[2];
            my_line.p1[3] = my_point[3];
            my_line.p2[0] = my_point2[0];
            my_line.p2[1] = my_point2[1];
            my_line.p2[2] = my_point2[2];
            my_line.p2[3] = my_point2[3];
            lines.set(i, my_line);

        }

    }

    /**
     * Faz o recorte dos pontos no cubo normalizado
     */
    private void clippingPointsNormalized() {
        boolean remove = false;
        Points _my_point;
        Float[] my_point;
        Float x_min = -1f;
        Float x_max = 1f;
        Float y_min = -1f;
        Float y_max = 1f;
        Float z_min = -1f;
        Float z_max = 1f;


        int excluidos = 0;

        my_point = new Float[4];
        _my_point = new Points();
        
        for (int i = 0; i < npoints; i++) {
            _my_point = points.get(i);
           // _my_point.p[3] = 1f;
            my_point[0] = _my_point.p[0]/_my_point.p[3];
            my_point[1] = _my_point.p[1]/_my_point.p[3];
            my_point[2] = _my_point.p[2]/_my_point.p[3];
            my_point[3] = _my_point.p[3];
            
            //System.out.println("Ponto processado: " + my_point[0] + " " + my_point[1] + " " + my_point[2] + " " + my_point[3]);

            if ((my_point[0] < x_min) || (my_point[0] > x_max)) {

                remove = true;

            }

            if (!remove) {
                if ((my_point[1] < y_min) || (my_point[1] > y_max)) {

                    remove = true;
                }


            }

            if (!remove) {
                if ((my_point[2] < z_min) || (my_point[2] > z_max)) {

                    remove = true;
                }


            }


            if (remove) {
                points.remove(i);
                npoints--;
                i--;
                remove = false;
                excluidos++;

            }



        }

        System.out.println("Pontos excluidos:" + excluidos + "\n\n");


    }

    /**
     * Faz o recorte das linhas no cubo normalizado
     */
    private void clippingLinesNormalized() {
        Lines _myline = new Lines();
        Float[] my_point1 = new Float[4];
        Float[] my_point2 = new Float[4];
        Float[] test_point1 = new Float[4];
        Float[] test_point2 = new Float[4];
        boolean point1_ok = false;
        boolean point2_ok = false;
        Float delta = 0.00001f;
        int excluidos = 0;

        Float u = 0f;

        //verifica se os 2 pontos estão fora e os exclui
        for (int i = 0; i < nlines; i++) {
            _myline = lines.get(i);
            my_point1[0] = _myline.p1[0]/_myline.p1[3];
            my_point1[1] = _myline.p1[1]/_myline.p1[3];
            my_point1[2] = _myline.p1[2]/_myline.p1[3];
            my_point1[3] = _myline.p1[3];
      
            my_point2[0] = _myline.p2[0]/_myline.p2[3];
            my_point2[1] = _myline.p2[1]/_myline.p2[3];
            my_point2[2] = _myline.p2[2]/_myline.p2[3];
            my_point2[3] = _myline.p2[3];

            test_point1 = my_point1;
            test_point2 = my_point2;
            test_point1[3] = my_point1[3];
            test_point2[3] = my_point2[3];
            
           // System.out.println("ponto da linha processado no recorte: " + my_point1[0] + my_point1[1] + my_point1[2]);

            while (u < 1f) {
                //percorre p1 até p2
                if (!point1_ok) {


                    if (verifyPointIfInsideCube(test_point1)) {
                        my_point1 = test_point1;
                        point1_ok = true;
                    }

                    test_point1[0] = (1 - u) * my_point1[0] + u * my_point2[0];
                    test_point1[1] = (1 - u) * my_point1[1] + u * my_point2[1];
                    test_point1[2] = (1 - u) * my_point1[2] + u * my_point2[2];


                }

                if (!point2_ok) {

                    if (verifyPointIfInsideCube(test_point2)) {
                        my_point2 = test_point2;
                        point2_ok = true;
                    }
                    //percorre p2 até p1
                    test_point2[0] = (1 - u) * my_point2[0] + u * my_point1[0];
                    test_point2[1] = (1 - u) * my_point2[1] + u * my_point1[1];
                    test_point2[2] = (1 - u) * my_point2[2] + u * my_point1[2];

                }
                u += delta;
            }

            if ((!point1_ok) && (!point2_ok)) {
                lines.remove(i);
                nlines--;
                i--;
                excluidos++;

            } else {

                _myline.p1[0] = my_point1[0];
                _myline.p1[1] = my_point1[1];
                _myline.p1[2] = my_point1[2];
                _myline.p1[3] = my_point1[3];
                _myline.p2[0] = my_point2[0];
                _myline.p2[1] = my_point2[1];
                _myline.p2[2] = my_point2[2];
                _myline.p2[3] = my_point2[3];
                lines.set(i, _myline);
               // System.out.println("Pontos da linha depois do recorte  \nPonto1: " + my_point1[0] + " " + my_point1[1] + " " + my_point1[2] + " " +my_point1[3]);
                //System.out.println("Ponto2: " + my_point2[0] + " " + my_point2[1] + " " + my_point2[2] + "\n\n");

            }

            u = 0f;
            point1_ok = false;
            point2_ok = false;




        }

        System.out.println("Total linhas excluidas: " + excluidos + "\n\n");

    }
    
    /**
     * Verifica se o ponto está no interior do cubo normalizado
     * @param p Vetor que representa o ponto
     * @return retorna verdadeiro caso esteja dentro
     */
    private boolean verifyPointIfInsideCube(Float[] p){
        
        if((p[0] < -1.f) ||(p[1] < -1f) || (p[2] < -1f)) return false;
        if((p[0] > 1.f) ||(p[1] > 1f) || (p[2] > 1f)) return false;
    
        return true;
    }
}