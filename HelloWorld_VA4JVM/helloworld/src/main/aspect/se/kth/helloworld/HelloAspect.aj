package se.kth.helloworld;

import org.aspectj.lang.Signature;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.lang.Thread.State;
import java.lang.reflect.Field;


public aspect HelloAspect {
	static GlobalVariables global=GlobalVariables.getInstance();
	/**
	 * 
	 * thread inforamtion
	 * */
	after(Thread childThread) : call(public void java.lang.Thread.start()) && !within(HelloAspect) && target(childThread)
	{
		System.out.println("Thread inforamtion");
		System.out.println(" Thread name: "+ childThread.getName());
		String threadName = childThread.getName();
		System.out.println("Thread State:"+childThread.getState());
		State threadState = childThread.getState();
		long mainThreadId = Thread.currentThread().getId();
		long childthreadId = childThread.getId() ;
		//threadLocation = thisJoinPoint.getSourceLocation();
		System.out.println("Main thread : Id: " + Thread.currentThread().getId() + " Thread name: "+Thread.currentThread().getName()+" Location:" + thisJoinPoint.getSourceLocation());;
		System.out.println("Child thread: Id: " + childThread.getId() + " Thread name: "+ childThread.getName()+" Location:" + thisJoinPoint.getSourceLocation());
		System.out.println();
		System.out.println();
	}
	
	/**
	 * 
	 * method inforamtion
	 * */

	pointcut traceMethod(): (execution (* *(..))&& !cflow(within(HelloAspect)));
	after(): traceMethod()
	{
		int i=0;
		//System.out.println("After method "+ i+" \n");
		Signature sign = thisJoinPointStaticPart.getSignature();
		String packagename = thisJoinPoint.getSignature().getDeclaringTypeName();
		String className = packagename.substring(packagename.lastIndexOf('.') + 1);
		String methodName = thisJoinPoint.getSignature().getName();
		//System.out.println("Method Name "+ sign.toString()+" \n");
		long thread = Thread.currentThread().getId();
		
			global.sig= sign.toString();	
			global.cname=className;
			global.mname=methodName;
			global.createInvokeInstruction();
			global.threadId = thread;
		
			
	}
	/**
	 * 
	 * field inforamtion
	 * */
    after() returning(Object field) : get(* se.kth.helloworld.App.*) && !within(HelloAspect){
	Signature sign = thisJoinPointStaticPart.getSignature();
	String packagename = thisJoinPoint.getSignature().getDeclaringTypeName();
	String className = packagename.substring(packagename.lastIndexOf('.') + 1);
	String fieldName = thisJoinPoint.getSignature().getName();
	//System.out.println("Field Name "+ thisJoinPoint.getSignature().getName()+" \n");
	long thread = Thread.currentThread().getId();
	
		global.sig= sign.toString();	
		global.fcname=className;
		global.fname=fieldName;
		global.createFieldInstruction();
		global.threadId = thread;
	
	
}

	/*
	 * 
	 * Instrument the end of main method before start the visualization 
	 * 
	 * 
	 */
	pointcut mainMethod() : execution(public static void main(String[]));
	/**
	 * Pointcut to trace the execution of every method in class as long as the control flow isn’t in the current class
	 */
	
	   after() : mainMethod()
	   {
		   System.out.println("The application has ended...");
		   global.displayErrorTrace();
	   
	   }
	   
	


	   
	   
}
