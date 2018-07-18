package se.kth.tracedata.jvm;

import se.kth.tracedata.ThreadInfo;

public class ChoiceGenerator<T> implements se.kth.tracedata.ChoiceGenerator<T>{
	String id;
	boolean isCascade;
	 public ChoiceGenerator(String id,boolean isCascade) {
		 this.id = id;
		 this.isCascade = isCascade;
		
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public int getTotalNumberOfChoices() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isInstaceofThreadChoiceFromSet() {
		// TODO Auto-generated method stub
		return true;
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

}
