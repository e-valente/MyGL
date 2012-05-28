package mygl;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author emanuel
 */
public final class Matrices {

    public static boolean setIdentity(Float[] m) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                m[i * 4 + j] = i == j ? 1.0f : 0.0f;
            }
        }
        return true;
    }

    public static boolean printMatrix(Float[] m) {
        System.out.println(m[0] + "  " + m[1] + "  " + m[2] + "  " + m[3]);
        System.out.println(m[4] + "  " + m[5] + "  " + m[6] + "  " + m[7]);
        System.out.println(m[8] + "  " + m[9] + "  " + m[10] + "  " + m[11]);
        System.out.println(m[12] + "  " + m[13] + "  " + m[14] + "  " + m[15]);



        return true;
    }

    public static Float[] multiplyMatrix4X4(Float[] mat1, Float[] mat2) {

        Float[] mat3 = new Float[16];



        //linha1
        mat3[0] = mat1[0] * mat2[0] + mat1[1] * mat2[4] + mat1[2] * mat2[8] + mat1[3] * mat2[12];
        mat3[1] = mat1[0] * mat2[1] + mat1[1] * mat2[5] + mat1[2] * mat2[9] + mat1[3] * mat2[13];
        mat3[2] = mat1[0] * mat2[2] + mat1[1] * mat2[6] + mat1[2] * mat2[10] + mat1[3] * mat2[14];
        mat3[3] = mat1[0] * mat2[3] + mat1[1] * mat2[7] + mat1[2] * mat2[11] + mat1[3] * mat2[15];

        //linha 2
        mat3[4] = mat1[4] * mat2[0] + mat1[5] * mat2[4] + mat1[6] * mat2[8] + mat1[7] * mat2[12];
        mat3[5] = mat1[4] * mat2[1] + mat1[5] * mat2[5] + mat1[6] * mat2[9] + mat1[7] * mat2[13];
        mat3[6] = mat1[4] * mat2[2] + mat1[5] * mat2[6] + mat1[6] * mat2[10] + mat1[7] * mat2[14];
        mat3[7] = mat1[4] * mat2[3] + mat1[5] * mat2[7] + mat1[6] * mat2[11] + mat1[7] * mat2[15];

        //linha3
        mat3[8] = mat1[8] * mat2[0] + mat1[9] * mat2[4] + mat1[10] * mat2[8] + mat1[11] * mat2[12];
        mat3[9] = mat1[8] * mat2[1] + mat1[9] * mat2[5] + mat1[10] * mat2[9] + mat1[11] * mat2[13];
        mat3[10] = mat1[8] * mat2[2] + mat1[9] * mat2[6] + mat1[10] * mat2[10] + mat1[11] * mat2[14];
        mat3[11] = mat1[8] * mat2[3] + mat1[9] * mat2[7] + mat1[10] * mat2[11] + mat1[11] * mat2[15];

        //linha4
        mat3[12] = mat1[12] * mat2[0] + mat1[13] * mat2[4] + mat1[14] * mat2[8] + mat1[15] * mat2[12];
        mat3[13] = mat1[12] * mat2[1] + mat1[13] * mat2[5] + mat1[14] * mat2[9] + mat1[15] * mat2[13];
        mat3[14] = mat1[12] * mat2[2] + mat1[13] * mat2[6] + mat1[14] * mat2[10] + mat1[15] * mat2[14];
        mat3[15] = mat1[12] * mat2[3] + mat1[13] * mat2[7] + mat1[14] * mat2[11] + mat1[15] * mat2[15];


        return (mat3);


    }

       public static Float[] multiplyMatrix4X4ByVertex(Float[] mat1, Float[] v) {

        Float[] v_out = new Float[4];
        
        //System.out.print

        v_out[0] = mat1[0] * v[0] + mat1[1] * v[1] + mat1[2] * v[2];
        v_out[1] = mat1[4] * v[0] + mat1[5] * v[1] + mat1[6] * v[2];
        v_out[2] = mat1[8] * v[0] + mat1[9] * v[1] + mat1[10] * v[2];
        v_out[3] = 1f;
        
       // System.out.println("vetor multiplicado: " + v_out[0] + v_out[1] + v_out[2] + v_out[3]);

        return (v_out);


    }
    public static Float[] multiplyMatrix4X4ByVertex2(Float[] mat1, Float[] v) {

        Float[] v_out = new Float[4];
        
        //System.out.print

        v_out[0] = mat1[0] * v[0] + mat1[1] * v[1] + mat1[2] * v[2];
        v_out[1] = mat1[4] * v[0] + mat1[5] * v[1] + mat1[6] * v[2];
        v_out[2] = mat1[8] * v[0] + mat1[9] * v[1] + mat1[10] * v[2];
        //v_out[3] = mat1[12] * v[0] + mat1[13] * v[1] + mat1[14] * v[2];
        v_out[3] = 1f;
        
       // System.out.println("vetor multiplicado: " + v_out[0] + v_out[1] + v_out[2] + v_out[3]);

        return (v_out);


    }
}
