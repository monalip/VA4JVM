package se.kth.tracedata.jpf;

public class ClassLoaderInfo implements se.kth.tracedata.ClassLoaderInfo {
	
	static gov.nasa.jpf.vm.ClassLoaderInfo jpfClassLoadInf;
	public ClassLoaderInfo(gov.nasa.jpf.vm.ClassLoaderInfo jpfClassLoadInf) {
		this.jpfClassLoadInf = jpfClassLoadInf;
		
	}
	
	public static int getNumberOfLoadedClasses ()
	{
		return jpfClassLoadInf.getNumberOfLoadedClasses();
	}

}
