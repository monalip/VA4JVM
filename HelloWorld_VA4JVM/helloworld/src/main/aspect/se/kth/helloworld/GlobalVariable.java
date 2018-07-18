package se.kth.helloworld;



import java.util.LinkedList;

import se.kth.tracedata.ChoiceGenerator;
import se.kth.tracedata.Instruction;
import se.kth.tracedata.Path;
import se.kth.tracedata.ThreadInfo;
import se.kth.tracedata.Transition;
import se.kth.tracedata.Step;

public class GlobalVariable {
	/**
	 * We used Singleton pattern to 
	 * 
	 */
	
	// constructor is private to make this class cannot instantiate from outside
	static  private GlobalVariable instance = null;
	String app="Fix it!";;
	LinkedList<Transition> stack=new LinkedList<Transition>();
	ThreadInfo thread = new	se.kth.tracedata.jvm.ThreadInfo(0, "RUNNING", "main");
	ChoiceGenerator<?> cg = new se.kth.tracedata.jvm.ChoiceGenerator<>("ROOT", false);
	Transition tr = new se.kth.tracedata.jvm.Transition(cg,thread);
	Instruction insn ;
	Step s = new se.kth.tracedata.jvm.Step(insn); 
	private GlobalVariable() {
		
	}
	public static GlobalVariable getInstance()
	{
		if(instance == null)
		{
			instance = new GlobalVariable();
		}
		return instance;
		
	}
	public void displayErrorTrace() {  

		se.kth.jpf_visual.ErrorTracePanel gui = new se.kth.jpf_visual.ErrorTracePanel();
		
			stack.add(tr);
			Path p= new se.kth.tracedata.jvm.Path(app,stack);
			
			
		
			gui.drowJVMErrTrace(p, true);
		
		
		

		 }
	

}
