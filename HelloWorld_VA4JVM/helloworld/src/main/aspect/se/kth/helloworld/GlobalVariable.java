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
import se.kth.tracedata.jvm.JVMInvokeInstruction;
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
	LinkedList<Transition> stack=new LinkedList<Transition>();
	boolean isMethodSync = false;
	ClassInfo ci = new se.kth.tracedata.jvm.ClassInfo(cname);
	MethodInfo mi = new se.kth.tracedata.jvm.MethodInfo(ci,mname,sig);
	ThreadInfo thread = new	se.kth.tracedata.jvm.ThreadInfo(0, "RUNNING", "main");
	ChoiceGenerator<?> cg = new se.kth.tracedata.jvm.ChoiceGenerator<>("ROOT", false);
	Transition tr = new se.kth.tracedata.jvm.Transition(cg,thread);
	Instruction insn = new JVMInvokeInstruction(cname,mname);
	
	
	
	
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
			System.out.println(insn.getMethodInfo());
			Step s = new se.kth.tracedata.jvm.Step(insn); 
			tr.addStep(s);
			stack.add(tr);
			Path p= new se.kth.tracedata.jvm.Path(app,stack);
			gui.drowJVMErrTrace(p, true);
		 }
	

}
