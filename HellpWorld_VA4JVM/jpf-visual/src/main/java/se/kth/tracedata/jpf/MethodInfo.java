package se.kth.tracedata.jpf;

//import gov.nasa.jpf.vm.ClassInfo;
import se.kth.tracedata.jpf.ClassInfo;

public class MethodInfo implements se.kth.tracedata.MethodInfo
{
	static gov.nasa.jpf.vm.MethodInfo jpfMethodinfo;
	public MethodInfo(gov.nasa.jpf.vm.MethodInfo jpfMethodinfo)
	{
		this.jpfMethodinfo = jpfMethodinfo;
	}
	@Override
	public boolean isSynchronized() {
		return jpfMethodinfo.isSynchronized();
	}
	@Override
	public ClassInfo getClassInfo() {
		return new ClassInfo(jpfMethodinfo.getClassInfo());
	}
	@Override
	public String getUniqueName() {
		return jpfMethodinfo.getUniqueName();
	}
	@Override
	public String getFullName() {
		return jpfMethodinfo.getFullName();
	}
	@Override
	public String getClassName() {
		return jpfMethodinfo.getClassName();
	}
	
	public static int getNumberOfLoadedMethods() {
		return jpfMethodinfo.getNumberOfLoadedMethods();
	}

}
