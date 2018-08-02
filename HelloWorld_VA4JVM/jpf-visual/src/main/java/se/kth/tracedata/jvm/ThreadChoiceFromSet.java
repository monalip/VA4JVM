package se.kth.tracedata.jvm;
import se.kth.tracedata.ThreadInfo;


public class ThreadChoiceFromSet implements se.kth.tracedata.ThreadChoiceFromSet {
	String id;
	boolean isCascade;
	 public ThreadChoiceFromSet(String id,boolean isCascade) {
		 this.id = id;
		 this.isCascade = isCascade;
		
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

	
}
