package se.kth.tracedata.jpf;

public class ElementInfo {
	
	 gov.nasa.jpf.vm.ElementInfo jpfElementInfo;
	 
	 public ElementInfo(gov.nasa.jpf.vm.ElementInfo jpfElementInfo)
	 {
		 this.jpfElementInfo = jpfElementInfo;
	 }
	 /*
	  * Not needed anymore
	  * public String  elementInfotoString(ElementInfo e)
	  {
		 String s =e.jpfElementInfo.toString();
		 return s;
		 
	  }
	 */

}
