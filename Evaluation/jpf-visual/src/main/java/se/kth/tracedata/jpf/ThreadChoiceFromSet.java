package se.kth.tracedata.jpf;

import se.kth.tracedata.ChoiceGenerator;
import se.kth.tracedata.ThreadInfo;

public class ThreadChoiceFromSet  {
	
	gov.nasa.jpf.vm.choice.ThreadChoiceFromSet jpfThreadchiocef;
	
	public ThreadChoiceFromSet(gov.nasa.jpf.vm.choice.ThreadChoiceFromSet jpfThreadchiocef)
	{
		this.jpfThreadchiocef = jpfThreadchiocef;
	}

	//@Override
	public ThreadInfo getChoice(int idx) {
		//return new ThreadInfo(jpfThreadchiocef.getChoice(idx));
		return null;
	}
	

}
