package se.kth.tracedata.jvm;

import se.kth.tracedata.MethodInfo;

public class FieldInstruction extends se.kth.tracedata.FieldInstruction{
	protected MethodInfo mi;
	protected String fname;
	  protected String className;
	  protected String varId;
	  protected String sourceLocation;
	  public FieldInstruction(String fname,String fcname, String sourceLocation) {
		this.fname = fname;
		this.className = fcname;
		this.sourceLocation =sourceLocation;
	}


	@Override
	public String getVariableId() {
		if (varId == null) {
		      varId = className + '.' + fname;
		    }
		    return varId;
	}

	@Override
	public MethodInfo getMethodInfo() {
		// TODO Auto-generated method stub
		return mi;
	}

	@Override
	public String getFileLocation() {
		return sourceLocation;
	}

	@Override
	public String getInvokedMethodName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getInvokedMethodClassName() {
		// TODO Auto-generated method stub
		return null;
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
	public void setMethodInfo(MethodInfo miInfo) {
		mi = miInfo;
		
	}


	@Override
	public String getLastlockName() {
		// TODO Auto-generated method stub
		return null;
	}

}
