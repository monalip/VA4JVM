package se.kth.tracedata.jvm;

public class ClassInfo implements se.kth.tracedata.ClassInfo{
	//name of class
	protected String name;
	public ClassInfo(String cname) {
		this.name = cname;
	}

	@Override
	public String getName() {
		return name;
	}

}
