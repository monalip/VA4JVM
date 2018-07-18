package se.kth.tracedata.jvm;

public class ThreadInfo implements se.kth.tracedata.ThreadInfo{
	int id;
	String stateName;
	String name ;
	public ThreadInfo(int id, String stateName, String name) {
		this.id = id;
		this.stateName = stateName;
		this.name = name;
		
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
		// TODO Auto-generated method stub
		return null;
	}

}
