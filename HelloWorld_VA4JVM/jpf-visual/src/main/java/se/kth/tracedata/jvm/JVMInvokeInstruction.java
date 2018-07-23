package se.kth.tracedata.jvm;


import se.kth.tracedata.MethodInfo;

public class JVMInvokeInstruction extends se.kth.tracedata.JVMInvokeInstruction{
	 protected String cname;
	  protected String mname;
	  public JVMInvokeInstruction(String cname, String mname) {
		this.cname = cname;
		this.mname = mname;
	}
	

	@Override
	public MethodInfo getMethodInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFileLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getInvokedMethodName() {
		// TODO Auto-generated method stub
		return cname;
	}

	@Override
	public String getInvokedMethodClassName() {
		// TODO Auto-generated method stub
		return mname;
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

}
