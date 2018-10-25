package se.kth.tracedata;


/**
 * simple const wrapper utility class for 2-tuple 
 */
public class Pair<A,B> {
  
  // final so that we don't need getters
  public final A _1;
  public final B _2;
  
  public Pair(A a, B b) { 
    this._1 = a; 
    this._2 = b; 
  }
  
  @Override
  public final boolean equals(Object o) {
    if (o instanceof Pair){
      Pair p = (Pair)o;
      if ((_1 == null) && (p._1 != null)){
        return false;
      }
      if ((_2 == null) && (p._2 != null)){
        return false;
      }
      if (!_1.equals(p._1)){
        return false;
      }
      if (!_2.equals(p._2)){
        return false;
      }
      
      return true;
      
    } else {
      return false;
    }
  }
  
  @Override
  public final int hashCode() {
    int h1 = (_1 == null) ? 0 : _1.hashCode();
    int h2 = (_2 == null) ? 0 : _2.hashCode();
    
    return h1 ^ h2;
  }
}
