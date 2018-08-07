package se.kth.helloworld;



import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Stack;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.nio.file.Paths;
import java.nio.file.Files;

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
	
	ChoiceGenerator<ThreadInfo> cg = new ThreadChoiceFromSet("ROOT", false);
	//ChoiceGenerator<ThreadInfo> cg1 = new ThreadChoiceFromSet("ROOT", false);
	Transition tr ;
	Instruction insn;
	Step step;
	long threadId;
	String tStateName= null;
	String threadName= null;
	String sourceString;
	String sourceLocation;
	int locationNo=0;
	String lastlockName= "App";
	ThreadInfo thread = new	se.kth.tracedata.jvm.ThreadInfo(0, "ROOT", "main",lastLockRef,lastlockName);

	

	
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
		instance.insn = new FieldInstruction(instance.fname,instance.cname);
		instance.insn.setMethodInfo(instance.mi);
		addPreviousStep();
		
		
	}
	
	static void addPreviousStep() {
	 if (instance.tr == null) {
		 instance.tr = new se.kth.tracedata.jvm.Transition(instance.cg,instance.thread);
		   }
		   assert(instance.insn != null);
		   //code to get the partivular line source code
		   try
			{
			   System.out.println(instance.locationNo-1);
			   instance.sourceString = Files.readAllLines(Paths.get("./helloworld/src/main/java/se/kth/helloworld/App.java")).get(instance.locationNo-1);
				
				System.out.println(instance.sourceString);
				
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		   instance.step = new se.kth.tracedata.jvm.Step(instance.insn,instance.sourceString,instance.sourceLocation);
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
