package se.kth.tracedata.jvm;

import se.kth.tracedata.ThreadInfo;

public class ChoiceGenerator implements se.kth.tracedata.ChoiceGenerator<ThreadInfo>{
	String id;
	boolean isCascade;
	long threadId ;
	int i;
	 public ChoiceGenerator(String id,boolean isCascade, long l, int i) {
		 this.id = id;
		 this.isCascade = isCascade;
		 this.threadId = l;
		 this.i=i;
		 
		
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ThreadInfo[] getChoices() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getIdPriority()
	{
		return 0;
	}
	@Override
	public long getThreadID()
	{
		return threadId;
	}

}
