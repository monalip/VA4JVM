package se.kth.tracedata.jpf;

public class ClassInfo implements se.kth.tracedata.ClassInfo
{
	gov.nasa.jpf.vm.ClassInfo jpfClassinfo;
	public ClassInfo(gov.nasa.jpf.vm.ClassInfo jpfClassinfo)
	{
		this.jpfClassinfo = jpfClassinfo;
	}
	@Override
	public String getName() {
		return jpfClassinfo.getName();
	}
}
