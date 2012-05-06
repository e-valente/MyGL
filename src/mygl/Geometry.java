
package mygl;

/*
 * @author Emanuel Valente - emanuelvalente@gmail.com
 */   
public final class Geometry
{
  public static Float[] createVector (Float[] p0, Float[] p1)
  {
    Float v[] = {p1[0] - p0[0], p1[1] - p0[1], p1[2] - p0[2]};
    return v;
  }
  
  public static Float[] crossProduct (Float[] v0, Float[] v1)
  {
    Float[] crossProduct = new Float[3];
    
    crossProduct[0] = v0[1] * v1[2] - v0[2] * v1[1];
    crossProduct[1] = v0[2] * v1[0] - v0[0] * v1[2];
    crossProduct[2] = v0[0] * v1[1] - v0[1] * v1[0];

    return crossProduct;
  }
  
  public static Float[] normalizeVector (Float[] v)
  {
    Float[] normalized_vector = new Float[3];
    Double norm;
    
    norm = Double.valueOf(v[0]*v[0] + v[1]*v[1] + v[2]*v[2]);
    norm = Math.sqrt(norm);
    
    normalized_vector[0] = v[0]/norm.floatValue();
    normalized_vector[1] = v[1]/norm.floatValue();
    normalized_vector[2] = v[2]/norm.floatValue();
    
    
    return normalized_vector;
  }
  
  public static boolean printVector(Float[] v){
      
      for(int i=0; i < v.length; i++ ){
          System.out.println("v[" + i + "] = " +v[i]);
      }
      
      return true;
  }
 
}
