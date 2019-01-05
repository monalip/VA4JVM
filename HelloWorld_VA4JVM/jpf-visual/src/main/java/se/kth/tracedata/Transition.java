package se.kth.tracedata;

import java.util.Iterator;
<<<<<<< HEAD
=======
import java.util.LinkedList;
>>>>>>> 04a2dc071776c7773dee008f404eb0b1dbecb95d

import se.kth.tracedata.ChoiceGenerator;
import se.kth.tracedata.Step;
import se.kth.tracedata.ThreadInfo;

public interface Transition extends Iterable<Step> {
	public int getThreadIndex ();
	public ThreadInfo getThreadInfo();
	public ChoiceGenerator<?> getChoiceGenerator();
	// don't use this for step iteration - this is very inefficient
	public Step getStep (int index) ;
	public int getStepCount ();
	public String getOutput ();

	public Iterator<Step> iterator();
<<<<<<< HEAD
=======
	public void addStep(Step step);
	public void setChoiceGenerator(ChoiceGenerator<ThreadInfo> cg);
	public void addJVMStep(LinkedList<Step> steplist);
	
>>>>>>> 04a2dc071776c7773dee008f404eb0b1dbecb95d
}
