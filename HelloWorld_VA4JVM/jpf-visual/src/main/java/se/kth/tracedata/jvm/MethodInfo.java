package se.kth.tracedata.jvm;


import se.kth.tracedata.ClassInfo;

public class MethodInfo implements se.kth.tracedata.MethodInfo{
	  protected String uniqueName;
	  
	  /** Name of the method */
	  protected String name;

	  /** Signature of the method */
	  protected String signature;
	  
	 /** Class the method belongs to */
	  protected ClassInfo ci;
	  boolean isSync ;
	  //static final int INIT_MTH_SIZE = 4096;
	  //protected static final ArrayList<MethodInfo> mthTable = new ArrayList<MethodInfo>(INIT_MTH_SIZE);
	public MethodInfo(ClassInfo ci, String name, String signature,boolean isSyn) {
		this.ci = ci;
		this.name =name;
		this.signature = signature;
		this.uniqueName =  getUniqueName(name, signature);
		this.isSync = isSyn;
	
	}
	public String getUniqueName(String mname, String signature) {
		  return (signature);
	}
	/**
	   * Returns true if the method is synchronized.
	   */
	@Override
	public boolean isSynchronized() {
		return isSync;
	}

	@Override
	public ClassInfo getClassInfo() {
		return ci;
	}

	@Override
	public String getUniqueName() {
		return uniqueName;
	}

	@Override
	public String getFullName() {
		if (ci != null) {
		      return ci.getName() + '.' + getUniqueName();
		    } else {
		      return getUniqueName();
		    }
	}

	@Override
	public String getClassName() {
		if (ci != null) {
		      return ci.getName();
		    } else {
		      return "[VM]";
		    }
	}

}
