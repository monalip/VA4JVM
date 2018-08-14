package se.kth.tracedata.jvm;


import se.kth.tracedata.MethodInfo;

public class JVMInvokeInstruction extends se.kth.tracedata.JVMInvokeInstruction{
	 protected String cname;
	  protected String mname;
	  protected String sourceLocation;
	  MethodInfo mi;
	  public JVMInvokeInstruction(String cname, String mname,String sourceLocation) {
		this.cname = cname;
		this.mname = mname;
		this.sourceLocation = sourceLocation;
	}
	

	@Override
	public MethodInfo getMethodInfo() {
		return mi;
	}

	@Override
	public String getFileLocation() {
		return sourceLocation;
	}

	@Override
	public String getInvokedMethodName() {
		return mname;
	}

	@Override
	public String getInvokedMethodClassName() {
		return cname;
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
