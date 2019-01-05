package se.kth.tracedata.jpf;


public abstract class VirtualInvocation extends se.kth.tracedata.VirtualInvocation{
	gov.nasa.jpf.jvm.bytecode.VirtualInvocation jpfVirtualinvocation;
	
	public VirtualInvocation(gov.nasa.jpf.jvm.bytecode.VirtualInvocation jpfVirtualinvocation)
	{
			
		this.jpfVirtualinvocation = jpfVirtualinvocation;
	}

	
	public String getInvokedMethodName() {
		return jpfVirtualinvocation.getInvokedMethodName();
	}

	
	public String getInvokedMethodClassName() {
		return jpfVirtualinvocation.getInvokedMethodClassName();
	}
}
