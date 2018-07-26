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

public class GlobalVariable {
	/**
	 * We used Singleton pattern to 
	 * 
	 */
	
	// constructor is private to make this class cannot instantiate from outside
	static  private GlobalVariable instance = null;
	String app="Fix it!";
	String cname= "App";
	String mname = "division(int a , int b)";
	String sig= "sign";
	int lastLockRef = 1;
	String fname= "a";
	String fcname = "App";
	LinkedList<Transition> stack=new LinkedList<Transition>();
	boolean isMethodSync = true;
	ClassInfo ci = new se.kth.tracedata.jvm.ClassInfo(cname);
	MethodInfo mi = new se.kth.tracedata.jvm.MethodInfo(ci,mname,sig);
	MethodInfo mwait = new se.kth.tracedata.jvm.MethodInfo(ci,"waitMethod",sig);
	MethodInfo mLock = new se.kth.tracedata.jvm.MethodInfo(ci,"mwthodLock",sig);
	ThreadInfo thread = new	se.kth.tracedata.jvm.ThreadInfo(0, "RUNNING", "main",lastLockRef);
	ChoiceGenerator<ThreadInfo> cg = new ThreadChoiceFromSet("ROOT", false);
	Transition tr = new se.kth.tracedata.jvm.Transition(cg,thread);
	Instruction insn = new JVMInvokeInstruction(cname,mname);
	Instruction insn1 = new VirtualInvocation(cname, "waitMethod");
	Instruction insnLock = new LockInstruction(lastLockRef);
	Instruction insnField = new FieldInstruction(fname, fcname);
	
	
	
	
	private GlobalVariable()
	{
		
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
			
			insn.setMethodInfo(mi);
			insn1.setMethodInfo(mwait);
			insnLock.setMethodInfo(mLock);
			insnField.setMethodInfo(mLock);
			Step s = new se.kth.tracedata.jvm.Step(insn); 
			Step s1 = new se.kth.tracedata.jvm.Step(insn1);
			Step slock = new se.kth.tracedata.jvm.Step(insnLock);
			Step sfield = new se.kth.tracedata.jvm.Step(insnField);
			tr.addStep(s);
			tr.addStep(s1);
			tr.addStep(slock);
			tr.addStep(sfield);
			stack.add(tr);
			Path p= new se.kth.tracedata.jvm.Path(app,stack);
			gui.drowJVMErrTrace(p, true);
		 }
	

}
