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

public class RuntimeData {
	/**
	 * We used Singleton pattern to 
	 * 
	 */
	
	// constructor is private to make this class cannot instantiate from outside
	static  private  RuntimeData instance = null;
	String app="Visualization";
	String cname= null;
	String mname = null;
	String sig= null;
	int lastLockRef = 1;
	String fname= null;
	String fcname = null;
	LinkedList<Transition> stack=new LinkedList<Transition>();
	boolean isMethodSync = true;
	ClassInfo ci ;
	MethodInfo mi ;
	
	ChoiceGenerator<ThreadInfo> cg;
	String cgState;
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
	ThreadInfo thread;
	//= new	se.kth.tracedata.jvm.ThreadInfo(0, "ROOT", "main",lastLockRef,lastlockName);

	int i =0;

	
	private  RuntimeData()
	{
		
		
	}
	public static  RuntimeData getInstance()
	{
		if(instance == null)
		{
			instance = new  RuntimeData();
		}
		return instance;
		
	}
	static void createInvokeInstruction()
	{	
		
		//System.out.println("Value od i : "+i);
		instance.ci=new se.kth.tracedata.jvm.ClassInfo(instance.cname);
		instance.mi= new se.kth.tracedata.jvm.MethodInfo(instance.ci,instance.mname,instance.sig);
		if(instance.i == 0 || instance.mname == "start")
		{
			instance.cg= updateChoiceGenerator(instance.mname,instance.i);
		}
		instance.i++;
		
		
		
		long currentThread = Thread.currentThread().getId();
		
		//code to get the particular line source code
		   try
			{
			   // get path of current directory System.out.println(new File(".").getAbsoluteFile());
			   //For maven 
			   instance.sourceString = Files.readAllLines(Paths.get("./helloworld/src/main/java/se/kth/helloworld/App.java")).get(instance.locationNo-1);
			   //For eclipse 
			   //instance.sourceString = (Files.readAllLines(Paths.get("./src/main/java/se/kth/helloworld/App.java")).get(instance.locationNo-1)).trim();
			   
				
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		instance.insn = new JVMInvokeInstruction(instance.cname,instance.mname,instance.sourceString);
		instance.insn.setMethodInfo(instance.mi);
		addPreviousStep(instance.cg);
	
		
		
	}
	
	static void createFieldInstruction()
	{
		
		
		instance.insn = new FieldInstruction(instance.fname,instance.cname);
		instance.insn.setMethodInfo(instance.mi);
		if(instance.i == 0 || instance.mname == "start")
		{
			instance.cg= updateChoiceGenerator(instance.mname,instance.i);
		}
		instance.i++;
		addPreviousStep(instance.cg);
		
		
	}
	
	static void addPreviousStep(ChoiceGenerator<ThreadInfo> cg) {
		long currentThreadId = Thread.currentThread().getId();
		long id = instance.threadId;
		
	 if ((instance.tr == null) || (currentThreadId != instance.threadId))
	 {
		
		 instance.thread = updateThreadInfo();
		  //instance.thread = new se.kth.tracedata.jvm.ThreadInfo(instance.threadId, instance.tStateName,instance.threadName,instance.lastLockRef,instance.lastlockName);
		instance.threadId = id;
	
		
		instance.tr = new se.kth.tracedata.jvm.Transition(cg,instance.thread);
	 }
		   assert(instance.insn != null);
		   
		   instance.step = new se.kth.tracedata.jvm.Step(instance.insn,instance.sourceString,instance.sourceLocation);
		   (instance.tr).addStep(instance.step);
		 if((currentThreadId != instance.threadId) )
		 {
		  
			  addPreviousTr(instance.tr); 
		 }
		 else if (instance.stack.size() <= 0 && java.lang.Thread.activeCount() == 1)
		 {
			 addPreviousTr(instance.tr); 
		 }
		   
			 
		   
		   
	}
	static void addPreviousTr(Transition tr) {
		   assert (tr != null);
		   instance.stack.add(tr);
		   tr = new se.kth.tracedata.jvm.Transition(null,null); // reset transition record
		}
	static ThreadInfo updateThreadInfo()
	{
		long currentThreadId = Thread.currentThread().getId();
		 
		 instance.threadId = Thread.currentThread().getId();
		 instance.threadName = Thread.currentThread().getName();
		 instance.tStateName = Thread.currentThread().getState().toString();
		 instance.threadName = instance.threadName.substring(instance.threadName.lastIndexOf('.') + 1);
		 instance.thread = new	se.kth.tracedata.jvm.ThreadInfo(instance.threadId, instance.tStateName, instance.threadName,instance.lastLockRef,instance.lastlockName);
		return (instance.thread);
	}
	static ChoiceGenerator<ThreadInfo> updateChoiceGenerator(String methodName, int i)
	{
		long threadId = Thread.currentThread().getId();
		if(i==0 ) {
			instance.cg = new ThreadChoiceFromSet("ROOT", false,threadId); 
			
		}
		else if(methodName == "start") {
					
			instance.cg = new ThreadChoiceFromSet("START", false,threadId); 		
		}
		return instance.cg;
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

