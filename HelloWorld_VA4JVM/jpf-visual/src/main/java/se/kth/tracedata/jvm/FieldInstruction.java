package se.kth.tracedata.jvm;

import se.kth.tracedata.MethodInfo;

public class FieldInstruction extends se.kth.tracedata.FieldInstruction{
	MethodInfo mi;
	protected String fname;
	  protected String className;
	  protected String varId;
	  public FieldInstruction(String fname,String fcname) {
		this.fname = fname;
		this.className = fcname;
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
		// TODO Auto-generated method stub
		return null;
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

}
