package se.kth.tracedata.jvm;

import se.kth.tracedata.MethodInfo;

public class LockInstruction extends se.kth.tracedata.LockInstruction{
	int lastLockRef;
	MethodInfo mi;
	
	public LockInstruction(int lastLockRef) {
		this.lastLockRef = lastLockRef; 
	}
	

	@Override
	public int getLastLockRef() {
		return lastLockRef;
	}

	@Override
	public MethodInfo getMethodInfo() {
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
	public void setMethodInfo(MethodInfo method) {
		mi = method;
		
	}

}
