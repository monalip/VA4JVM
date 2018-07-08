package se.kth.tracedata.jpf;

import java.util.ArrayList;

//import com.google.common.base.Function;
//import com.google.common.collect.Iterators;
import java.util.Iterator;
import java.lang.Iterable;


//import gov.nasa.jpf.vm.ChoiceGenerator;
import se.kth.tracedata.jpf.ChoiceGenerator;
import se.kth.tracedata.ThreadInfo;
import se.kth.tracedata.Step;

public class Transition implements se.kth.tracedata.Transition, Iterable<Step>{
	Iterator<gov.nasa.jpf.vm.Step> jpfiterator;
	/*Function<gov.nasa.jpf.vm.Step,Step> func = new Function<gov.nasa.jpf.vm.Step,Step>(){

		@Override
		public Step apply(gov.nasa.jpf.vm.Step arg0) {
			// TODO Auto-generated method stub
			return new Step(arg0);
		}
		
	};*/
	
	private Step   first, last;
	gov.nasa.jpf.vm.Transition jpfTransition;
	public Transition(gov.nasa.jpf.vm.Transition jpfTransition) {
		this.jpfTransition =jpfTransition;
		
	}
	
	public Transition(Iterator<gov.nasa.jpf.vm.Step> jpfiterator)
	{
		this.jpfiterator = jpfiterator;
	}
	//ArrayList<Step> mylist = new ArrayList<Step>();
	Iterator<Step> mylist;
	
	
	
	@Override
	public int getThreadIndex() {
		return jpfTransition.getThreadIndex();
	}
	
	
	
	@Override
	public ThreadInfo getThreadInfo() {
		return new se.kth.tracedata.jpf.ThreadInfo(jpfTransition.getThreadInfo());
	}
	@Override
	public ChoiceGenerator<?> getChoiceGenerator() {
		return new ChoiceGenerator(jpfTransition.getChoiceGenerator());
		//return jpfTransition.getChoiceGenerator();
	}
	@Override
	public Step getStep(int index) {
		return new se.kth.tracedata.jpf.Step(jpfTransition.getStep(index));
	}
	@Override
	public int getStepCount() {
		return jpfTransition.getStepCount();
	}

	@Override
	public Iterator<Step> iterator()
	{
		Iterator<gov.nasa.jpf.vm.Step> jpftraniter = jpfTransition.iterator();
	
	Iterator<Step> transiter = new Iterator<Step>() {

		@Override
		public boolean hasNext() {
			
			return(jpftraniter.hasNext());
		}

		@Override
		public Step next() {
			return new se.kth.tracedata.jpf.Step(jpftraniter.next());
		}
	};
	
	
	return transiter;

	}
	/*@Override
	public Iterator<Transition> iterator() {
		//Iterator<gov.nasa.jpf.vm.Step> jpfiterator = jpfTransition.iterator();
		//Iterator<Step> stepIter = Iterators.transform(jpfiterator, func);
		
		return null;
	}*/

	@Override
	public String getOutput() {
		return jpfTransition.getOutput();
	}
	
	

}
