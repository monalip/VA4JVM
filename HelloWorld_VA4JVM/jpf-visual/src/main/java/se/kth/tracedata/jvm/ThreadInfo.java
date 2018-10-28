package se.kth.tracedata.jvm;

public class ThreadInfo implements se.kth.tracedata.ThreadInfo{
	long id;
	String stateName;
	String name ;
	int lastLockRef;
	String lastLockName;
	public ThreadInfo(long id, String stateName, String name, int lastLockRef,String lastLockName) {
		this.id = id;
		this.stateName = stateName;
		this.name = name;
		this.lastLockRef = lastLockRef;
		this.lastLockName = lastLockName;
		
	}

	@Override
	public int getId() {
		return (int) id;
	}

	@Override
	public String getStateName() {
		return stateName;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getNameOfLastLock(int lastLockRef) {
		return lastLockName;
	}

	@Override
	public String getLastLockName() {
		return lastLockName;
	}

}
