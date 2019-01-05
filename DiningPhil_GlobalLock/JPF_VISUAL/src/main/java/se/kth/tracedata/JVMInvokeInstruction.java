package se.kth.tracedata;

public abstract class JVMInvokeInstruction extends Instruction {
	public abstract String getInvokedMethodName();
	public abstract String getInvokedMethodClassName();
}
