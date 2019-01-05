package se.kth.helloworld;

import java.io.BufferedReader;
import com.sun.jndi.toolkit.url.Uri;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
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
import se.kth.tracedata.jvm.JVMReturnInstruction;
import se.kth.tracedata.jvm.LockInstruction;
import se.kth.tracedata.jvm.ThreadChoiceFromSet;
import se.kth.tracedata.jvm.VirtualInvocation;
import se.kth.tracedata.Step;

public class RuntimeData {
	/**
	 * We used Singleton pattern to 
	 * 
	 */
	
	
	static  private  RuntimeData instance = null;
	String app="VA4JVM for ClientSocket program";
	
	static String cname= null;
	static String pkgName= null;
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
	static boolean isTerminate=false;
	static ChoiceGenerator<ThreadInfo> cg=null;
	static String cgState;
	//ChoiceGenerator<ThreadInfo> cg1 = new ThreadChoiceFromSet("ROOT", false);
	static Transition tr = null ;
	static Instruction insn;
	static Step step;
	static LinkedList<Step> steplist=new LinkedList<Step>();
	static long threadId;
	static String tStateName= null;
	static String threadName= null;
	static String sourceString;
	static String sourceLocation;
	static int locationNo=0;
	static String lastlockName= null;
	static ThreadInfo thread;
	static long prevThread= Thread.currentThread().getId();
	static Thread eventThread=null;
	static long childThreadId;
	//= new	se.kth.tracedata.jvm.ThreadInfo(0, "ROOT", "main",lastLockRef,lastlockName);

	static int i =0;
	static boolean isFirst=true;
	static boolean threadChange = false;
	static int firstcheck = 0;
	//create the array list
	public Set<Thread> threads = new HashSet<Thread>();
	static int totalTrans = 0;
	static boolean isSync =false;
	static boolean isSynchBlock =false;
	static boolean isUnLock =false;
	public long maxThreadId =0;
	static boolean isTranAdded = false;
	static boolean isUnlockSync=false;
	
	static int eventAdded = 0;
	// constructor is private to make this class cannot instantiate from outside
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
	// we have make it synchronized so it should lock that any allow the other method should execute after fininshing that task
	static synchronized void createInvokeInstruction()
	{	
			ci=new se.kth.tracedata.jvm.ClassInfo(cname);
			mi= new se.kth.tracedata.jvm.MethodInfo(ci,mname,sig,false);				
			getSourceString();

			insn = new JVMInvokeInstruction(cname,mname,sourceString);
			insn.setMethodInfo(mi);
			
			addPreviousStep();	
		
		
	}
	static synchronized void createVirtualInvocationIns()
	{
		ci=new se.kth.tracedata.jvm.ClassInfo(cname);
		mi= new se.kth.tracedata.jvm.MethodInfo(ci,mname,sig,false);
		
		getSourceString();

		insn = new VirtualInvocation(cname,mname,sourceString);
		insn.setMethodInfo(mi);
		
		addPreviousStep();	
	
	}
	static synchronized void createJVMReturnInstr()
	{
		
		ci=new se.kth.tracedata.jvm.ClassInfo(cname);
		mi= new se.kth.tracedata.jvm.MethodInfo(ci,mname,sig,true);
		
		//System.out.println("Class Name "+cname+" IsSynch"+isSync+"\n");	
		//insn = new LockInstruction(lastLockRef);
		insn = new JVMReturnInstruction();
		insn.setMethodInfo(mi);	
		getSourceString();
		addPreviousStep();
		
	
	}
	static synchronized void createLockInstruction()
	{
			lastlockName = fname;
			ci=new se.kth.tracedata.jvm.ClassInfo(cname);
			mi= new se.kth.tracedata.jvm.MethodInfo(ci,mname,sig,false);
			insn = new LockInstruction(lastLockRef, fname);
			insn.setMethodInfo(mi);
			getSourceString();
			addPreviousStep();
		
		
		
	}
	
	static synchronized void createFieldInstruction()
	{
		getSourceString();
		ci=new se.kth.tracedata.jvm.ClassInfo(cname);
		mi= new se.kth.tracedata.jvm.MethodInfo(ci,mname,sig,false);
			insn = new FieldInstruction(fname,cname,sourceLocation);
			insn.setMethodInfo(mi);
			addPreviousStep();
	}
	static synchronized void addPreviousStep() {
		
		//update the choicegenerator when the particular events is occurred
			cg = updateChoiceGenerator(mname,fname);
				/*if(mname=="start"||mname=="join"||mname=="run"||mname=="main"||mname=="wait"||isFirst||mname=="lock"||mname=="unlock"||isSync||isUnLock||isSynchBlock)
				{
					cg = updateChoiceGenerator(mname,fname);
					System.out.println("Choice generator "+cg.getId()+" method name "+mname+" Thread Id "+ cg.getThreadID()+" IsSyn "+isSync+" IsUnlock "+isUnLock+" isSynblock "+isSynchBlock+"\n");
					   
				}
				else
				{
					cg = new se.kth.tracedata.jvm.ChoiceGenerator("RUNNING", false,eventThread.getId(),0);
				}*/
				//for the first time add previussId step as a currenthreadId
				if(firstcheck == 0)
				{
					prevThread= Thread.currentThread().getId();
					firstcheck++;
					
				}
				/*
				 * If transition is null or there is thread change the add previos transition to the transition list
				 * and create a new transition in witch new step is added
				 * 
				 */
				if (((tr == null) || (prevThread != eventThread.getId())))
				 {
					
					// for other than first transition if there is thread change occur add previous transition to the stack
					if( tr != null)
					   {
						
						 addPreviousTr(tr); 
						 threadChange = true;  
						 prevThread = eventThread.getId();
						 
						 
					   }
					//Crete threadInfo
					 thread = updateThreadInfo();
					 //create new transition
					 tr = new se.kth.tracedata.jvm.Transition(cg,thread);
					 totalTrans++;
					
				 }
				else if((cg.getId()=="START"||cg.getId()=="JOIN"||cg.getId()=="WAIT"||cg.getId()=="LOCK"||cg.getId()=="RELEASE"||cg.getId() == "TERMINATE" ))
				{
					addPreviousTr(tr);
					 //tr.setChoiceGenerator(cg);
					//Crete threadInfo
					 thread = updateThreadInfo();
					 //create new transition
					 tr = new se.kth.tracedata.jvm.Transition(cg,thread);
					 totalTrans++;
					
				}
				
					updateStep();
				
				 //update the boolean variables
				 isSync =false;
				  isUnLock =false;
				  isSynchBlock=false;
				  isTerminate =false;
				  isUnlockSync=false;
				  //isFirst = false;
				  eventAdded++;
				 
				 
				 
		
	}
	
	 static synchronized void updateStep() {
		 assert(insn != null);
		 //if instruction is not null create the new step
		 step = new se.kth.tracedata.jvm.Step(insn,sourceString,sourceLocation,cg);
		 //add the step to transition
		 steplist.add(step);
		 (tr).addJVMStep(steplist);	
		 steplist = new LinkedList<Step>(); // reset the linked list
		
	}
	
	 static synchronized void addPreviousTr(Transition tr) {
		 
		   assert (tr != null);
		   //System.out.println("Transition added "+tr.getChoiceGenerator().getId()+" method name "+mname+" Thread Id "+ tr.getThreadInfo().getId()+" IsSyn "+isSync+" IsUnlock "+isUnLock+" isSynblock "+isSynchBlock+"\n");
		   
		   stack.add(tr);
		  
		   tr = new se.kth.tracedata.jvm.Transition(null,null); // reset transition record
		   prevThreadId=-1;
		   
		}
	 static synchronized void getSourceString()
	 {
		//code to get the particular line source code
		// get path of current directory System.out.println(new File(".").getAbsoluteFile());
		 
		 String location= null;
		   try
			{
			   pkgName = pkgName.replace('.','/');
			   //location = "src/main/java/"+pkgName+".java";
			   location = "./src/main/java/se/kth/helloworld/ClientSocket.java";
			   File file = new File(location);
				String path = file.getAbsolutePath();
				//System.out.println("Path"+path);
				boolean isFileExist = file.exists();
				//if we are executing script from eclipse the current directory is differenent and same thing for the Maven script
				// Hence the path of the file is depends upon path from which we are running the script
				if(!isFileExist)
				{
					 //For maven 
					//location = "helloworld/"+location;
					location = "clientSocket/src/main/java/se/kth/helloworld/ClientSocket.java";
					file =  new File(location);
					path = file.getAbsolutePath();
				}
				sourceString = (Files.readAllLines(Paths.get(path)).get(locationNo - 1)).trim();
				
			}
			catch (Exception e) {
				
				e.printStackTrace();
			}
			
	 }
	 static synchronized ThreadInfo updateThreadInfo()
	{
		long currentThreadId = Thread.currentThread().getId();
		//lastlockName= fname;
		 threadId = eventThread.getId();
		 threadName = eventThread.getName();
		 tStateName = eventThread.getState().toString();
		 threadName = threadName.substring(threadName.lastIndexOf('.') + 1);
		 thread = new	se.kth.tracedata.jvm.ThreadInfo(threadId, tStateName, threadName,lastLockRef,"JVM");
		return (thread);
	}
	 static synchronized ChoiceGenerator<ThreadInfo> updateChoiceGenerator(String methodName, String fname)
	{
		if(methodName.length() > 0 || fname.length() > 0)
		{
			//System.out.println("Event Added: "+eventAdded+"\n");
			//long threadId = Thread.currentThread().getId();
			if((methodName=="main" && !isTerminate) &&(!(methodName == "start"))) {
				cg = new ThreadChoiceFromSet("ROOT", false,threadId,eventAdded); 
				
			}
			else if(methodName == "start") {
						
				cg = new ThreadChoiceFromSet("START", false,threadId,0); 		
			}
			else if(methodName == "join") {
				
				cg = new ThreadChoiceFromSet("JOIN", false,threadId,eventAdded); 		
			}
			else if(methodName == "wait") {
				
				cg = new ThreadChoiceFromSet("WAIT", false,threadId,eventAdded); 		
			}
			else if((methodName == "lock") || isSync || isSynchBlock) {
				
				cg = new ThreadChoiceFromSet("LOCK", false,threadId,eventAdded); 	
				//isSynchBlock=false;
			}
			else if(methodName == "unlock" || isUnLock || isUnlockSync) {
						
						cg = new ThreadChoiceFromSet("RELEASE", false,threadId,eventAdded); 
						//isUnLock=false;
					}
			else if((methodName == "run" || methodName == "main") && isTerminate) {
				
				cg = new ThreadChoiceFromSet("TERMINATE", false,threadId,eventAdded); 
				//isTerminate =false;
			}
			else{
				
				cg = new ThreadChoiceFromSet("Running", false,threadId,eventAdded); 		
			}
		}
		
		
		return cg;
	}
	

	 static synchronized void singleThreadProg()
	{
		if(stack.size() == 0 && !threadChange )
		 {
			 addPreviousTr(tr); 
		 }
	}
	 /*
	  * 
	  * add the Last transition If it is not added into the stack
	  */
	 static synchronized void addLastTransition()
		{
			if(totalTrans > stack.size() && tr!=null )
			 {
				 addPreviousTr(tr); 
			 }
		}
	 
	
	
public void displayErrorTrace() {	
		singleThreadProg();
		addLastTransition();
		
		
		//Sort the stack based on the transition threadId to get group of same transion inthe main pannel
		//Collections.sort(stack, new SortStackPriority());
		///Collections.sort(stack, new SortStack());
		se.kth.jpf_visual.ErrorTracePanel gui = new se.kth.jpf_visual.ErrorTracePanel();
			Path p= new se.kth.tracedata.jvm.Path(app,stack);
				gui.drowJVMErrTrace(p, true);	
		 }
	

}
//Group the transition based on threadId with the help of sorted stack using Collections.sort() method
class SortStack implements Comparator<Transition>{
	 
    @Override
    public int compare(Transition t1, Transition t2) {
    	
        if((t1.getThreadInfo().getId() > t2.getThreadInfo().getId())){
            return 1;
        } else {
            return -1;
        }
    }
}
//
class SortTransition implements Comparator<Step>{

	@Override
	public int compare(Step o1, Step o2) {
		if(o1.getLineString().contains("start"))
		{
			return 1;
		}
		else
		{
			return -1;
		}
	}
	 
    
}
//sorted based on the choicegenerator threadId priority
class SortStackPriority implements Comparator<Transition>{
	 
    @Override
    public int compare(Transition t1, Transition t2) {
        if(t1.getChoiceGenerator().getIdPriority() <= t2.getChoiceGenerator().getIdPriority()){
            return 1;
        } else {
            return -1;
        }
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
