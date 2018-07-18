package se.kth.tracedata.jvm;

import java.util.Iterator;

import se.kth.tracedata.ChoiceGenerator;
import se.kth.tracedata.Step;
import se.kth.tracedata.ThreadInfo;

public class Transition implements se.kth.tracedata.Transition{
	 private ChoiceGenerator<?> cg;
	private ThreadInfo ti;

	public Transition (ChoiceGenerator<?> cg, ThreadInfo ti) {
		    this.cg = cg;
		    this.ti = ti;
		  }

	@Override
	public int getThreadIndex() {
		// TODO Auto-generated method stub
		return ti.getId();
	}

	@Override
	public ThreadInfo getThreadInfo() {
		// TODO Auto-generated method stub
		return ti;
	}

	@Override
	public ChoiceGenerator<?> getChoiceGenerator() {
		return cg;
	}

	@Override
	public Step getStep(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getStepCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getOutput() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<Step> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

}
