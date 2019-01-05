package se.kth.tracedata.jpf;

<<<<<<< HEAD
public class ThreadChoiceFromSet implements se.kth.tracedata.ThreadChoiceFromSet{
=======
import se.kth.tracedata.ChoiceGenerator;
import se.kth.tracedata.ThreadInfo;

public class ThreadChoiceFromSet  {
>>>>>>> 04a2dc071776c7773dee008f404eb0b1dbecb95d
	
	gov.nasa.jpf.vm.choice.ThreadChoiceFromSet jpfThreadchiocef;
	
	public ThreadChoiceFromSet(gov.nasa.jpf.vm.choice.ThreadChoiceFromSet jpfThreadchiocef)
	{
		this.jpfThreadchiocef = jpfThreadchiocef;
	}

<<<<<<< HEAD
	@Override
	public ThreadInfo getChoice(int idx) {
		return new ThreadInfo(jpfThreadchiocef.getChoice(idx));
=======
	//@Override
	public ThreadInfo getChoice(int idx) {
		//return new ThreadInfo(jpfThreadchiocef.getChoice(idx));
		return null;
>>>>>>> 04a2dc071776c7773dee008f404eb0b1dbecb95d
	}
	

}
