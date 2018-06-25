package se.kth.maven.quickstart;

import java.util.logging.Level;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.logging.*;

import org.aspectj.lang.Signature;

import se.kth.tracedata.jvm.Path;
import se.kth.jpf_visual.TraceData;
import se.kth.tracedata.Transition;





public aspect AppAspect {
	int transition =0;
	

	Logger log = Logger.getLogger(AppAspect.class.getName());
	
	
	pointcut traceCall() : !within(AppAspect);
	/*
	 * The trace of above pointcut will give following information:
	 *  	
	 *  ■ The output lines that contain staticinitialization() show class-level ini-
		tialization that occurs when a class gets loaded. The <clinit> part of the
		output indicates the class initialization.
		■ The output lines that contain execution() and call() show the execution
		and call join points of a method or a constructor.
			■ The output lines that contain get() and set() show the read and write
			field access join points.
  
	 * */
	
	 
	//before(Thread childThread) : call(public void  java.lang.Thread.start() && within(Java.lang.*)
	//pointcut threadweave():  call(public void  java.lang.Thread.start())  && !within(AppAspect) ;
	//before(Thread childThread) : call(* java.lang.Thread.start(..)) && !within(AppAspect) && target(childThread)
	
	before(Thread childThread) : call(public void java.lang.Thread.start()) && !within(AppAspect) && target(childThread)
	{
		System.out.println("Thread inforamtion");
		System.out.println(" Thread name: "+ childThread.getName());
		System.out.println("Thread State:"+childThread.getState());
		
		System.out.println(Thread.getAllStackTraces());
		System.out.println("Main thread : Id: " + Thread.currentThread().getId() + " Thread name: "+Thread.currentThread().getName()+" Location:" + thisJoinPoint.getSourceLocation());;
		System.out.println("Child thread: Id: " + childThread.getId() + " Thread name: "+ childThread.getName()+" Location:" + thisJoinPoint.getSourceLocation());
		System.out.println();
		System.out.println();
		

	}
	before(): traceCall()
	{
		System.out.println();
		System.out.println();
		 //System.out.println("------------------------------------------------Transition "+ transition+"#");
		 //System.out.println();
		 //System.out.println();
		// thisJoinPoint object, which contains the text representation of the captured join point.
		//getSourceLocation() gives the source location in the form of line number
		//System.out.println("           before: " + thisJoinPoint);
		
		
		// AppAspect.class.getName() gives the full name of the class along with package name
		log.log(Level.INFO,thisJoinPoint+" Location:  " + thisJoinPoint.getSourceLocation());
		 transition ++;
		
	}
	/**
	 * Pointcut to trace the execution of every method in class as long as the control flow isn’t in the current class
	 */
	
	pointcut traceMethod(): (execution (* *(..))&& !cflow(within(AppAspect)));
	
	/**
	 * Advice before(): traceMethod() gives the information about the particular method call along with its method and line number
	 */
	before(): traceMethod()
	{
		int i = 0;
		System.out.println("Before method "+i+" \n");
		Signature sign = thisJoinPointStaticPart.getSignature();
		int lineNumb = thisJoinPointStaticPart.getSourceLocation().getLine(); 
		String sourceName = thisJoinPointStaticPart.getSourceLocation().getWithinType().getCanonicalName();
		log.log(Level.INFO,"Call from " + sourceName + " line number "+lineNumb + " "+ sign.getDeclaringTypeName()+"."+sign.getName());
	}
	
	/*
	 * Information of method exit()
	 * 
	 */
	after(): traceMethod()
	{
		int i=0;
		System.out.println("After method "+ i+" \n");
		Signature sign = thisJoinPointStaticPart.getSignature();
		int lineNumb = thisJoinPointStaticPart.getSourceLocation().getLine(); 
		String sourceName = thisJoinPointStaticPart.getSourceLocation().getWithinType().getCanonicalName();
		log.log(Level.INFO,"Call from " + sourceName + " line number "+lineNumb + " "+ sign.getDeclaringTypeName()+"."+sign.getName());
	}
	
	
	    
	/*before(Thread childThread) :
	    call(public void Thread+.start()) &&
	    target(childThread)
	{
		
		System.out.println("Thread Creation information by monali:");
		System.out.println(Thread.getAllStackTraces());
		System.out.println("Parent thread : Id: " + Thread.currentThread().getId() + " Thread name: "+Thread.currentThread().getName()+" Location:" + thisJoinPoint.getSourceLocation());;
		System.out.println("Child thread: Id: " + childThread.getId() + " Thread name: "+ childThread.getName()+" Location:" + thisJoinPoint.getSourceLocation());
		System.out.println();
		System.out.println();
		
	}*/
	
	/*
	 * 
	 * Instrument the end of main method before start the visualization 
	 * 
	 * 
	 */
	pointcut mainMethod() : execution(public static void main(String[]));
	 
	 
	   after() : mainMethod()
	   {
		   System.out.println("The application has ended...");
		   DisplayErrorTrace();
	   }
	


	   private void DisplayErrorTrace() {
	/**
	
	 * ProcessBuilder take the executable jar of the DiningPhil program which 
	 * generate the error trace and show the output there
	 * 
	 
	*/

	se.kth.jpf_visual.ErrorTracePanel gui = new se.kth.jpf_visual.ErrorTracePanel();
	
	String app ="Fix it!";
	LinkedList<Transition> stack=null;

	Path p =null;
	
	
	gui.drowJVMErrTrace(p,true);
	

	 }
		

}
