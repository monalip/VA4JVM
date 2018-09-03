package se.kth.tracedata.jvm;

import se.kth.tracedata.MethodInfo;

public class VirtualInvocation extends se.kth.tracedata.VirtualInvocation{
	 protected String cname;
	  protected String mname;
	  MethodInfo mi;
	  String sourceLocation;
	  public VirtualInvocation(String cname, String mname,String sourceLocation) {
		this.cname = cname;
		this.mname = mname;
		this.sourceLocation = sourceLocation;
	}

	@Override
	public String getInvokedMethodName() {
		// TODO Auto-generated method stub
		return mname;
	}

	@Override
	public String getInvokedMethodClassName() {
		// TODO Auto-generated method stub
		return cname;
	}

	@Override
	public MethodInfo getMethodInfo() {
		return mi;
	}

	@Override
	public String getFileLocation() {
		// TODO Auto-generated method stub
		return sourceLocation;
	}

	@Override
	public boolean isInstanceofJVMInvok() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isInstanceofJVMReturnIns() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isInstanceofLockIns() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getLastLockRef() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isInstanceofVirtualInv() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isInstanceofFieldIns() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getVariableId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMethodInfo(MethodInfo mi) {
		 this.mi = mi;
	}

}
