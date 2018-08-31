package se.kth.tracedata.jvm;
import se.kth.tracedata.ThreadInfo;


public class ThreadChoiceFromSet implements se.kth.tracedata.ThreadChoiceFromSet {
	String id;
	boolean isCascade;
	int threadId;
	 public ThreadChoiceFromSet(String id,boolean isCascade, long threadId) {
		 this.id = id;
		 this.isCascade = isCascade;
		 this.threadId = (int) threadId;
		
	}


	@Override
	public String getId() {
		return id;
	}

	@Override
	public int getTotalNumberOfChoices() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isInstaceofThreadChoiceFromSet() {
		return false;
	}

	@Override
	public ThreadInfo getChoice(int idx) {
		return null;
	}

	@Override
	public ThreadInfo[] getChoices() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int getThreadId() {
		
		return threadId;
	}
	@Override
	//setting the id based on the method name call start join
	public void setId(String newid)
	{
		id = newid;
	}
	

	
}
