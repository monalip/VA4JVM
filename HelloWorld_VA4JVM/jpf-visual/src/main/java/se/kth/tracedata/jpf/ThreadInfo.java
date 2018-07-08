package se.kth.tracedata.jpf;


public class ThreadInfo implements se.kth.tracedata.ThreadInfo{
	gov.nasa.jpf.vm.ThreadInfo  jpfThreadinfo;
	  public ThreadInfo(gov.nasa.jpf.vm.ThreadInfo  jpfThreadinfo) {
		 this.jpfThreadinfo = jpfThreadinfo;
		 
		  }
	@Override
	public int getId() {
		return jpfThreadinfo.getId();
	}
	@Override
	public String getStateName() {
		return jpfThreadinfo.getStateName();
	}
	@Override
	public String getName() {
		return jpfThreadinfo.getName();
	}
	@Override
	public String getNameOfLastLock(int lastLockRef) {
		
		//ElementInfo elementInfo = new ElementInfo(jpfThreadinfo.getElementInfo(lastLockRef));
		return jpfThreadinfo.getElementInfo(lastLockRef).toString().replace("$", ".").replaceAll("@.*","");
		
		
	}

}
