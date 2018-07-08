package se.kth.tracedata;

import java.util.Iterator;

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
}
