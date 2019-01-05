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
		
<<<<<<< HEAD
		//ElementInfo elementInfo = new ElementInfo(jpfThreadinfo.getElementInfo(lastLockRef));
=======
		ElementInfo elementInfo = new ElementInfo(jpfThreadinfo.getElementInfo(lastLockRef));
		if(elementInfo == null)
		{
			return "";
		}
>>>>>>> 04a2dc071776c7773dee008f404eb0b1dbecb95d
		return jpfThreadinfo.getElementInfo(lastLockRef).toString().replace("$", ".").replaceAll("@.*","");
		
		
	}
<<<<<<< HEAD
=======
	@Override
	public String getLastLockName() {
		return "lastLok";
	}
>>>>>>> 04a2dc071776c7773dee008f404eb0b1dbecb95d

}
