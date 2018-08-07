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
		//System.out.println("Thread inforamtion");
		System.out.println(" Thread name: "+ childThread.getName());
		String threadName = childThread.getName();
		//System.out.println("Thread State:"+childThread.getState());
		String threadState = childThread.getState().toString();
		long mainThreadId = Thread.currentThread().getId();
		long childthreadId = childThread.getId() ;
		//threadLocation = thisJoinPoint.getSourceLocation();
		/*System.out.println("Main thread : Id: " + Thread.currentThread().getId() + " Thread name: "+Thread.currentThread().getName()+" Location:" + thisJoinPoint.getSourceLocation());;
		System.out.println("Child thread: Id: " + childThread.getId() + " Thread name: "+ childThread.getName()+" Location:" + thisJoinPoint.getSourceLocation());
		System.out.println();
		System.out.println();*/
		global.threadId = childthreadId;
		global.tStateName=threadState;
		global.threadName= threadName;
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
		String sign = (thisJoinPointStaticPart.getSignature()).toString();
		String locationName = sign.toString();
		String sourceString = locationName.substring(locationName.lastIndexOf('.') + 1);
		String packagename = thisJoinPoint.getSignature().getDeclaringTypeName();
		String className = packagename.substring(packagename.lastIndexOf('.') + 1);
		String methodName = thisJoinPoint.getSignature().getName();
		String sourceLocation = thisJoinPoint.getSourceLocation().toString();
		int lineNo = Integer.parseInt(sourceLocation.substring(sourceLocation.lastIndexOf(':') + 1));
		System.out.println("Method Name "+ sourceString+" \n");
		long thread = Thread.currentThread().getId();
		String fieldName="";
			updateGlobalVar(sign,className,methodName,thread,sourceLocation,lineNo,fieldName);
			global.createInvokeInstruction();
			
	}
	/**
	 * 
	 * field inforamtion
	 * */
    after() returning(Object field) : get( * *) && within(se.kth.helloworld.App)&& !within(HelloAspect){
	String sign = (thisJoinPointStaticPart.getSignature()).toString();
	String packagename = thisJoinPoint.getSignature().getDeclaringTypeName();
	String className = packagename.substring(packagename.lastIndexOf('.') + 1);
	String fieldName = thisJoinPoint.getSignature().getName();
	long thread = Thread.currentThread().getId();
	String sourceLocation = thisJoinPoint.getSourceLocation().toString();
	String locationName = sign.toString();
	int lineNo = Integer.parseInt(sourceLocation.substring(sourceLocation.lastIndexOf(':') + 1));
	String methodName= "";
	System.out.println("Field Name "+ className+" \n");
	updateGlobalVar(sign,className,methodName,thread,sourceLocation,lineNo,fieldName);
		
		global.createFieldInstruction();
	
	
	
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
	   
	   public void updateGlobalVar(String sign,String className,String methodName,long thread,String sourceLocation,int lineNo,String fieldName)
	   {
		   global.sig= sign.toString();	
			global.cname=className;
			global.mname=methodName;
			global.threadId = thread;
			global.sourceLocation=sourceLocation;
			global.locationNo = lineNo;
			global.fname=fieldName;
			
		
	   }


	   
	   
}
