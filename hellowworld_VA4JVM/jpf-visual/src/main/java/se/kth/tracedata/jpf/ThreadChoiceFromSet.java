package se.kth.tracedata.jpf;

public class ThreadChoiceFromSet implements se.kth.tracedata.ThreadChoiceFromSet{
	
	gov.nasa.jpf.vm.choice.ThreadChoiceFromSet jpfThreadchiocef;
	
	public ThreadChoiceFromSet(gov.nasa.jpf.vm.choice.ThreadChoiceFromSet jpfThreadchiocef)
	{
		this.jpfThreadchiocef = jpfThreadchiocef;
	}

	@Override
	public ThreadInfo getChoice(int idx) {
		return new ThreadInfo(jpfThreadchiocef.getChoice(idx));
	}
	

}
