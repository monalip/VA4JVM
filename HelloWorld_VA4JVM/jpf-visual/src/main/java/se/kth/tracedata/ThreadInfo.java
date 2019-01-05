package se.kth.tracedata;


public interface ThreadInfo {
	public int getId ();
	//public ElementInfo getElementInfo (int objRef); Not used 
	public String getStateName () ;
	public String getName ();
	public String getNameOfLastLock(int lastLockRef);
<<<<<<< HEAD
=======
	public String getLastLockName();
>>>>>>> 04a2dc071776c7773dee008f404eb0b1dbecb95d

}
