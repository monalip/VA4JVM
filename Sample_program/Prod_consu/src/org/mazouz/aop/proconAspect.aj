package org.mazouz.aop;
import org.aspectj.lang.annotation.Pointcut;

public aspect proconAspect {
	//All method calls *except* the ones made in this Aspect.
	//pointcut tracedCall(): call(* *(..)) && !within(myAspect);
	
	
	pointcut tracedCall(): !within(proconAspect);
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
	

before(): tracedCall() {
	// thisJoinPoint object, which contains the text representation of the captured join point.
	//getSourceLocation() gives the souce location in the form of line number
	//System.out.println("           before: " + thisJoinPoint);
    System.out.println("           before  "+ thisJoinPoint+" Line NoLocation:  " + thisJoinPoint.getSourceLocation());
    
}


//Getting parent and child threads id and name
before(Thread childThread) :
    call(public void Thread+.start()) &&
    target(childThread)
{
	
	System.out.println();
	System.out.println();
	System.out.println("Parent thread : Id: " + Thread.currentThread().getId() + " Thread name: "+Thread.currentThread().getName());
	System.out.println("Child thread: Id: " + childThread.getId() + " Thread name: "+ childThread.getName());
	System.out.println();
	System.out.println();
	
}
after(): tracedCall(){
	//System.out.println("          After: "+ thisJoinPoint);
	//get the source location of all call, initialization 
	System.out.println("          After: "+ thisJoinPoint+" Location: "+thisJoinPoint.getSourceLocation()); 
}
//end of the child thread

}

