package se.kth.tracedata;

import se.kth.tracedata.ClassInfo;

public interface MethodInfo {
	/**
	   * Returns true if the method is synchronized.
	   */
	  public boolean isSynchronized ();
	  /**
	   * Returns the class the method belongs to.
	   */
	  public ClassInfo getClassInfo ();
	  public String getUniqueName ();
	  /**
	   * Returns the full classname (if any) + name + signature.
	   */
	  public String getFullName ();
	  public String getClassName ();
	  
	

}
