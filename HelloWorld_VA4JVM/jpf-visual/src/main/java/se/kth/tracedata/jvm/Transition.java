package se.kth.tracedata.jvm;

import java.util.Iterator;

import se.kth.tracedata.ChoiceGenerator;
import se.kth.tracedata.Step;
import se.kth.tracedata.ThreadInfo;

public class Transition implements se.kth.tracedata.Transition,Iterable<Step>{
	 private ChoiceGenerator<?> cg;
	private ThreadInfo ti;

	  private Step first;
	  se.kth.tracedata.jvm.Step last = new se.kth.tracedata.jvm.Step();
	  int nSteps=0;

	public Transition (ChoiceGenerator<?> cg, ThreadInfo ti) {
		    this.cg = cg;
		    this.ti = ti;
		  }

	@Override
	public int getThreadIndex() {
		return ti.getId();
		
	}

	@Override
	public ThreadInfo getThreadInfo() {
		return ti;
	}

	@Override
	public ChoiceGenerator<?> getChoiceGenerator() {
		return cg;
	}

	@Override
	public Step getStep(int index) {
		se.kth.tracedata.jvm.Step s = (se.kth.tracedata.jvm.Step) first;
	    for (int i=0; s != null && i < index; i++) s = s.next;
	    return s;
	}

	@Override
	public int getStepCount() {
		return nSteps;
	}

	@Override
	public String getOutput() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<Step> iterator() {
		return null;
	}
	@Override
	public void addStep(Step step) {
		if (first == null) {
		      first = step;
		      last = (se.kth.tracedata.jvm.Step) step;
		    } 
		else {
			//As the next is the variable of the Step and with the interface object we are not able to access that variable
			//so we have created the last as the object of the implemented class and solve the proble by casting 
		    	
		      last.next = (se.kth.tracedata.jvm.Step) step;
				
		      last = (se.kth.tracedata.jvm.Step) step;
		    }
		    nSteps++;
		
	}
	

}
