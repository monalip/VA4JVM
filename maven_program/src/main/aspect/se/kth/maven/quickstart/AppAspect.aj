package se.kth.maven.quickstart;




public aspect AppAspect {
	pointcut tracecall() : !within(AppAspect);
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
	before(): tracecall()
	{
		// thisJoinPoint object, which contains the text representation of the captured join point.
		//getSourceLocation() gives the source location in the form of line number
		//System.out.println("           before: " + thisJoinPoint);
		 //System.out.println();
		 System.out.println("before "+ thisJoinPoint+" Location:  " + thisJoinPoint.getSourceLocation());
	}
	//before(Thread childThread) : call(public void  java.lang.Thread.start() && within(Java.lang.*)
	//pointcut threadweave():  call(public void  java.lang.Thread.start())  && !within(AppAspect) ;
	//before(Thread childThread) : call(* java.lang.Thread.start(..)) && !within(AppAspect) && target(childThread)
	before(Thread childThread) : call(public void java.lang.Thread.start()) && !within(AppAspect) && target(childThread)
	{
		
		System.out.println();
		System.out.println();
		System.out.println("Thread information:");
		System.out.println();
		System.out.println(Thread.getAllStackTraces());
		System.out.println("Main thread : Id: " + Thread.currentThread().getId() + " Thread name: "+Thread.currentThread().getName()+" Location:" + thisJoinPoint.getSourceLocation());;
		System.out.println("Child thread: Id: " + childThread.getId() + " Thread name: "+ childThread.getName()+" Location:" + thisJoinPoint.getSourceLocation());
		System.out.println();
		System.out.println();
		

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


}
