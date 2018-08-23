package se.kth.helloworld;

import java.util.logging.Level;
import java.util.logging.Logger;



aspect HelloAspect {
	 Logger log = Logger.getLogger(HelloAspect.class.getName());
	static  RuntimeData global= RuntimeData.getInstance();
	static String sign= null;
	static String locationName=null;
	static String sourceString=null ;
	static String packagename=null;
	static String className=null;
	static String methodName=null;
	static String sourceLocation=null;
	static int lineNo = 0;
	static long thread=0;
	static String fieldName= null;
	static Thread threadaspectj= null;
	
	private static boolean DEBUG = false;
  /*  before() :
        if(!DEBUG) && (
            call(* Thread+.start()) ||
            call(* ExecutorService+.execute(..)) ||
            call(* ExecutorService+.submit(..)) ||
            call(* ExecutorService+.invokeAll(..)) ||
            call(* ExecutorService+.invokeAny(..)) ||
            call(* ScheduledExecutorService.scheduleAtFixedRate(..))
        )
    {
        System.out.println("First:   "+thisJoinPoint);
    }

    before() :
        if(DEBUG) &&
        within(*) &&
        !within(HelloAspect) &&
        !get(* *) &&
        !call(* println(..))
    {
        System.out.println("Second: " +thisJoinPoint);
    }*/
	
	/**
	 * 
	 * thread inforamtion
	 * */
	/*after(Thread childThread) : call(public void java.lang.Thread.start()) && !within(HelloAspect) && target(childThread)
	{
		//System.out.println("Thread inforamtion");
		//System.out.println(" Thread name: "+ childThread.getName());
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
		/*global.threadId = childthreadId;
		global.tStateName=threadState;
		global.threadName= threadName;
		//System.out.println(" Thread State: "+ threadState);
		
	}*/
	
	/**
	 * 
	 * method inforamtion
	 * */
	/*pointcut traceThreadstart(): (call(* Thread+.start()) )   && !cflow(within(HelloAspect));
	after() : traceThreadstart()
	{
		sign = (thisJoinPointStaticPart.getSignature()).toString();
		locationName = sign.toString();
		sourceString = locationName.substring(locationName.lastIndexOf('.') + 1);
		packagename = thisJoinPoint.getSignature().getDeclaringTypeName();
		className = packagename.substring(packagename.lastIndexOf('.') + 1);
		methodName = thisJoinPoint.getSignature().getName();
		sourceLocation = thisJoinPoint.getSourceLocation().toString();
		lineNo = Integer.parseInt(sourceLocation.substring(sourceLocation.lastIndexOf(':') + 1));
		
			//System.out.println("sourceString "+ sourceString+" \n");
		
		
		System.out.println("traceThreadstart methodName "+ methodName+" ThreadId "+ Thread.currentThread().getId()+" \n");
		thread = Thread.currentThread().getId();
		String fieldName="";
			updateGlobalVar(sign,className,methodName,thread,sourceLocation,lineNo,fieldName);
			global.createInvokeInstruction();
	}*/
	//pointcut traceMethod(): (execution (* *.*(..)) || execution(*.new(..)) || initialization(*.new(..)) || call(void java.io.PrintStream.println(..)) || call(public void java.lang.Thread.start()))   && !cflow(within(HelloAspect)) ;
	pointcut traceMethod(): (execution (* *.*(..)) || execution(*.new(..)) || initialization(*.new(..)) || call(* println(..)) || call(* Thread+.start()) || call(* Thread+.join()) || call(* java..*.*(..)))   && !cflow(within(HelloAspect)) ;
	//pointcut traceMethod(): (execution (* *.*(..)) || call(* Thread+.start()))   && !cflow(within(HelloAspect)) ;

	after(): traceMethod() 
	{
		
			
		int i=0;	
			//System.out.println("After method "+ i+" \n");
			sign = (thisJoinPointStaticPart.getSignature()).toString();
			locationName = sign.toString();
			sourceString = locationName.substring(locationName.lastIndexOf('.') + 1);
			packagename = thisJoinPoint.getSignature().getDeclaringTypeName();
			className = packagename.substring(packagename.lastIndexOf('.') + 1);
			methodName = thisJoinPoint.getSignature().getName();
			sourceLocation = thisJoinPoint.getSourceLocation().toString();
			lineNo = Integer.parseInt(sourceLocation.substring(sourceLocation.lastIndexOf(':') + 1));
			threadaspectj = Thread.currentThread();
				//System.out.println("sourceString "+ sourceString+" \n");
			
			
			thread = Thread.currentThread().getId();
			String fieldName="";
			
			updateGlobalVar(sign,className,methodName,thread,sourceLocation,lineNo,fieldName,threadaspectj);
			//log.log(Level.INFO,"Trace method methodName "+ methodName+" Trace method ThreadId "+ Thread.currentThread().getId()+" \n");
			global.createInvokeInstruction();
		
			
		
			
	}
	
	/**
	 * 
	 * field inforamtion
	 * */
	pointcut getObject() : get(* *) && within(se.kth.helloworld.App) && !within(HelloAspect);
	after() : getObject()
	{
		
		
			 // after() returning(Object field) : get( * *) && within(se.kth.helloworld.App)&& !within(HelloAspect){
			sign = (thisJoinPointStaticPart.getSignature()).toString();
			packagename = thisJoinPoint.getSignature().getDeclaringTypeName();
			className = packagename.substring(packagename.lastIndexOf('.') + 1);
			fieldName = thisJoinPoint.getSignature().getName();
			thread = Thread.currentThread().getId();
			sourceLocation = thisJoinPoint.getSourceLocation().toString();
			locationName = sign.toString();
			lineNo = Integer.parseInt(sourceLocation.substring(sourceLocation.lastIndexOf(':') + 1));
			methodName= "";
			threadaspectj = Thread.currentThread();
			//System.out.println("Field Name "+ className+" \n");
			//System.out.println("ThreadId Name "+ Thread.currentThread().getId()+" \n");
			updateGlobalVar(sign,className,methodName,thread,sourceLocation,lineNo,fieldName,threadaspectj);
			//log.log(Level.INFO,"getObject fieldName "+ fieldName+" Trace method ThreadId "+ Thread.currentThread().getId()+" \n");
	
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
	   
	   public void updateGlobalVar(String sign,String className,String methodName,long thread,String sourceLocation,int lineNo,String fieldName, Thread threadaspectj)
	   {
		   global.sig= sign.toString();	
			global.cname=className;
			global.mname=methodName;
			global.threadId = thread;
			global.sourceLocation=sourceLocation;
			global.locationNo = lineNo;
			global.fname=fieldName;
			global.eventThread =threadaspectj;
			//System.out.println("Aspectj Method Name "+ methodName+" \n");
			//System.out.println("Aspectj Field Name "+ fieldName+" \n");
			//System.out.println("Aspectj ThreadId Name "+ thread+" \n");
			
			
			
			
		
	   }
	   

	   
	   
}
