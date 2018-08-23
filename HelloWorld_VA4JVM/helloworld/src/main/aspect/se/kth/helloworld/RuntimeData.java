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
	static String cname= null;
	static String mname = null;
	static String sig= null;
	static int lastLockRef = 1;
	static String fname= null;
	static String fcname = null;
	static LinkedList<Transition> stack=new LinkedList<Transition>();
	static boolean isMethodSync = true;
	static ClassInfo ci ;
	static MethodInfo mi ;
	static int prevThreadId = 0;
	
	static ChoiceGenerator<ThreadInfo> cg;
	static String cgState;
	//ChoiceGenerator<ThreadInfo> cg1 = new ThreadChoiceFromSet("ROOT", false);
	static Transition tr ;
	static Instruction insn;
	static Step step;
	static long threadId;
	static String tStateName= null;
	static String threadName= null;
	static String sourceString;
	static String sourceLocation;
	static int locationNo=0;
	static String lastlockName= "App";
	static ThreadInfo thread;
	static long prevThread= Thread.currentThread().getId();
	static Thread eventThread=null;
	//= new	se.kth.tracedata.jvm.ThreadInfo(0, "ROOT", "main",lastLockRef,lastlockName);

	static int i =0;
	static boolean threadChange = false;
	static int firstcheck = 0;
	
	
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
		ci=new se.kth.tracedata.jvm.ClassInfo(cname);
		mi= new se.kth.tracedata.jvm.MethodInfo(ci,mname,sig);
		if(i == 0 || mname == "start")
		{
			cg= updateChoiceGenerator(mname,i);
		}
		i++;
		
		
		
		//code to get the particular line source code
		   try
			{
			   // get path of current directory System.out.println(new File(".").getAbsoluteFile());
			   //For maven 
			   	sourceString = Files.readAllLines(Paths.get("./helloworld/src/main/java/se/kth/helloworld/App.java")).get(locationNo-1);
			   //For eclipse 
			   	//sourceString = (Files.readAllLines(Paths.get("./src/main/java/se/kth/helloworld/App.java")).get(locationNo - 1)).trim();
			   
				
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
			
		
		insn = new JVMInvokeInstruction(cname,mname,sourceString);
		insn.setMethodInfo(mi);
		addPreviousStep(cg);
		
	
		
		
	}
	
	static void createFieldInstruction()
	{
		
		insn = new FieldInstruction(fname,cname);
		insn.setMethodInfo(mi);
		if(i == 0 || mname == "start")
		{
			cg= updateChoiceGenerator(mname,i);
		}
		i++;
		addPreviousStep(cg);
		
		
	}
	
	static void addPreviousStep(ChoiceGenerator<ThreadInfo> cg) {
		long currentThreadId = Thread.currentThread().getId();
		if(firstcheck == 0)
		{
			prevThread= currentThreadId;
			firstcheck++;
		}
		
		//System.out.println("Method Name "+ mname+" ThreadId Name "+ threadId+"  Location "+ sourceString+"  Current threadId  "+prevThread+" \n");
		
		long id = threadId;
		
	 if ((tr == null) || (prevThread != threadId))
	 {
		 
		 if( tr != null)
		   {
			 tr =addPreviousTr(tr); 
			 threadChange = true;  
			 prevThread = threadId;
		   }
		 
		 thread = updateThreadInfo();
		  //thread = new se.kth.tracedata.jvm.ThreadInfo(threadId, tStateName,threadName,lastLockRef,lastlockName);
		
		tr = new se.kth.tracedata.jvm.Transition(cg,thread);
   
	 }
	
	 assert(insn != null);
	 step = new se.kth.tracedata.jvm.Step(insn,sourceString,sourceLocation);
	 (tr).addStep(step); 
	 //System.out.println("Thread count"+java.lang.Thread.activeCount());
	 //System.out.println("Thread changee"+threadChange);
	
		   
	}
	static Transition addPreviousTr(Transition tr) {
		   assert (tr != null);
		   
		   stack.add(tr);
		   tr = new se.kth.tracedata.jvm.Transition(null,null); // reset transition record
		   prevThreadId=0;
		   return tr;
		}
	static ThreadInfo updateThreadInfo()
	{
		long currentThreadId = Thread.currentThread().getId();
		 
		 threadId = eventThread.getId();
		 threadName = eventThread.getName();
		 tStateName = eventThread.getState().toString();
		 threadName = threadName.substring(threadName.lastIndexOf('.') + 1);
		 thread = new	se.kth.tracedata.jvm.ThreadInfo(threadId, tStateName, threadName,lastLockRef,lastlockName);
		return (thread);
	}
	static ChoiceGenerator<ThreadInfo> updateChoiceGenerator(String methodName, int i)
	{
		long threadId = Thread.currentThread().getId();
		if(i==0 ) {
			cg = new ThreadChoiceFromSet("ROOT", false,threadId); 
			
		}
		else if(methodName == "start") {
					
			cg = new ThreadChoiceFromSet("START", false,threadId); 		
		}
		return cg;
	}
	static void singleThreadProg()
	{
		if(stack.size() == 0 && !threadChange )
		 {
			 addPreviousTr(tr); 
		 }
	}
	
	
	
public void displayErrorTrace() {	
		singleThreadProg();
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
