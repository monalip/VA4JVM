package se.kth.tracedata;

import se.kth.tracedata.MethodInfo;

public abstract class Instruction {
	public  abstract MethodInfo getMethodInfo() ;
	public  abstract String getFileLocation();
	public  abstract String getInvokedMethodName();
	public  abstract String getInvokedMethodClassName();
	public  abstract boolean isInstanceofJVMInvok();
	public  abstract boolean isInstanceofJVMReturnIns();
	public  abstract boolean isInstanceofLockIns();
	public 	abstract int getLastLockRef();
	public  abstract boolean isInstanceofVirtualInv();
	public  abstract boolean isInstanceofFieldIns();
	public 	abstract String getVariableId();
	
}
