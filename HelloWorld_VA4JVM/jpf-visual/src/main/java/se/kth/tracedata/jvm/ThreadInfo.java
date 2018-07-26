package se.kth.tracedata.jvm;

public class ThreadInfo implements se.kth.tracedata.ThreadInfo{
	int id;
	String stateName;
	String name ;
	int lastLockRef;
	public ThreadInfo(int id, String stateName, String name, int lastLockRef) {
		this.id = id;
		this.stateName = stateName;
		this.name = name;
		this.lastLockRef = lastLockRef;
		
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public String getStateName() {
		// TODO Auto-generated method stub
		return stateName;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public String getNameOfLastLock(int lastLockRef) {
		String lock="lastlock";
		return lock.toString().replace("$", ".").replaceAll("@.*","");
	}

}
