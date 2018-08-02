package se.kth.helloworld;



import java.util.LinkedList;
import java.util.Stack;

import se.kth.tracedata.ChoiceGenerator;
import se.kth.tracedata.ClassInfo;
import se.kth.tracedata.Instruction;
import se.kth.tracedata.MethodInfo;
import se.kth.tracedata.Path;
import se.kth.tracedata.ThreadInfo;
import se.kth.tracedata.Transition;
import se.kth.tracedata.jvm.FieldInstruction;
import se.kth.tracedata.jvm.JVMInvokeInstruction;
import se.kth.tracedata.jvm.LockInstruction;
import se.kth.tracedata.jvm.ThreadChoiceFromSet;
import se.kth.tracedata.jvm.VirtualInvocation;
import se.kth.tracedata.Step;

public class GlobalVariables {
	/**
	 * We used Singleton pattern to 
	 * 
	 */
	
	// constructor is private to make this class cannot instantiate from outside
	static  private GlobalVariables instance = null;
	String app="Fix it!";
	String cname= null;
	String mname = null;
	String sig= null;
	int lastLockRef = 1;
	String fname= null;
	String fcname = null;
	LinkedList<Transition> stack=new LinkedList<Transition>();
	boolean isMethodSync = true;
	ClassInfo ci = new se.kth.tracedata.jvm.ClassInfo(cname);
	MethodInfo mi = new se.kth.tracedata.jvm.MethodInfo(ci,mname,sig);
	ThreadInfo thread = new	se.kth.tracedata.jvm.ThreadInfo(0, "LOCK", "main",lastLockRef);
	ChoiceGenerator<ThreadInfo> cg = new ThreadChoiceFromSet("ROOT", false);
	//ChoiceGenerator<ThreadInfo> cg1 = new ThreadChoiceFromSet("ROOT", false);
	Transition tr ;
	Instruction insn;
	Step step;
	long threadId;
	
	
	
	
	private GlobalVariables()
	{
		
		
	}
	public static GlobalVariables getInstance()
	{
		if(instance == null)
		{
			instance = new GlobalVariables();
		}
		return instance;
		
	}
	static void createInvokeInstruction()
	{
		long currentThread = Thread.currentThread().getId();
		
		instance.insn = new JVMInvokeInstruction(instance.cname,instance.mname);
		instance.insn.setMethodInfo(instance.mi);
		addPreviousStep();
		
		
	}
	
	static void createFieldInstruction()
	{
		long currentThread = Thread.currentThread().getId();
	
		instance.insn = new FieldInstruction(instance.fname,instance.fcname);
		instance.insn.setMethodInfo(instance.mi);
		addPreviousStep();
		
		
	}
	
	static void addPreviousStep() {
	 if (instance.tr == null) {
		 instance.tr = new se.kth.tracedata.jvm.Transition(instance.cg,instance.thread);
		   }
		   assert(instance.insn != null);
		   instance.step = new se.kth.tracedata.jvm.Step(instance.insn);
		   (instance.tr).addStep(instance.step);
		   addPreviousTr();
	}
	static void addPreviousTr() {
		   assert (instance.tr != null);
		   instance.stack.add(instance.tr);
		   instance.tr = new se.kth.tracedata.jvm.Transition(instance.cg,instance.thread); // reset transition record
		}
	
	


	public void displayErrorTrace() { 
		se.kth.jpf_visual.ErrorTracePanel gui = new se.kth.jpf_visual.ErrorTracePanel();
			Path p= new se.kth.tracedata.jvm.Path(app,stack);
			gui.drowJVMErrTrace(p, true);
		 }
	

}

/** Check if the current transition is still correct; if there was a thread
	switch since the last event, create a new transition. 
Transition checkAndUpdateTransition() {
currentThread = Thread.currentThread();
if ((currentTransition == null) || (currentThread != thread)) {
	currentTransition = new Transition();
	Path.add(currentTransition);
}
thread = currentThread;
return currentTransition;
}*/

