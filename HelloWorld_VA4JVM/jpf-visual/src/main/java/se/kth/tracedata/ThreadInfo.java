package se.kth.tracedata;


public interface ThreadInfo {
	public int getId ();
	//public ElementInfo getElementInfo (int objRef); Not used 
	public String getStateName () ;
	public String getName ();
	public String getNameOfLastLock(int lastLockRef);

}
