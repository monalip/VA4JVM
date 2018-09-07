package se.kth.helloworld;

import java.awt.print.PrinterException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.concurrent.locks.ReentrantLock;




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
	static String prevMethod= null;

	
	private static boolean DEBUG = false;
	/**
	 * 
	 * Lock unlock
	 */
	private static final Object lock = new Object();
	pointcut syncJointPoint(): execution(synchronized* *.*(..)) && !within(HelloAspect); // or call()}*/

	after(Object lock): syncJointPoint() && target(lock)
    {
		synchronized (this) {
			sign = (thisJoinPointStaticPart.getSignature()).toString();
			locationName = sign.toString();
			sourceString = locationName.substring(locationName.lastIndexOf('.') + 1);
			packagename = thisJoinPoint.getSignature().getDeclaringTypeName();
			className = packagename.substring(packagename.lastIndexOf('.') + 1);
			methodName = thisJoinPoint.getSignature().getName();
			sourceLocation = thisJoinPoint.getSourceLocation().toString();
			lineNo = Integer.parseInt(sourceLocation.substring(sourceLocation.lastIndexOf(':') + 1));
			threadaspectj = Thread.currentThread();	//System.out.println("Second: " +methodName);
			thread = Thread.currentThread().getId();
			fieldName="";
			global.isSync=true;
			//log.log(Level.INFO,"Trace method methodName "+ methodName+" pointcut:" + thisJoinPoint.getKind()+" Trace method ThreadId "+ Thread.currentThread().getId()+" \n");
			
			updateGlobalVar(sign,className,methodName,thread,sourceLocation,lineNo,fieldName,threadaspectj);
			global.createJVMReturnInstr();
			
			
			}
		methodName = "";
			
		
    }
	/*pointcut lock(ReentrantLock l) : call(* ReentrantLock.lock()) && target(l) ;
	after(Object app): lock() && args(app) {

		System.out.println("around(App) lock: advice running at "+thisJoinPoint.getSourceLocation());

	
	
    pointcut syncJointPoint(): execution( Synchronizes * *.*(..)); // or call()}*/

    /*Object around(): syncJointPoint() {
        synchronized(lock) {
        	System.out.println("around(App) lock: advice running at "+thisJoinPoint.getSourceLocation());

            return proceed();
        }
    }
    after(Object lock):execution( Synchronizes * *.*(..)) && !within(HelloAspect) && target(lock)
    {
    	System.out.println("around(App) lock: advice running at "+thisJoinPoint.getSourceLocation());

    }*/
    /*before() :
        if(!DEBUG) && (
            call(* Thread+.start()) ||
            call(* ExecutorService+.execute(..)) ||
            call(* ExecutorService+.submit(..)) ||
            call(* ExecutorService+.invokeAll(..)) ||
            call(* ExecutorService+.invokeAny(..)) ||
            call(* ScheduledExecutorService.scheduleAtFixedRate(..))
        )
    {
        System.out.println("First:   "+Thread.currentThread());
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
	
	after(Object lock):execution( synchronized* *.*(..)) && !within(HelloAspect) && target(lock)
    {
    	//System.out.println("around(App) lock: advice running at "+thisJoinPoint.getSourceLocation());

    }
	/**
	 * 
	 * thread inforamtion
	 * */
	after(Thread childThread) : call(* Thread+.start()) && !within(HelloAspect) && target(childThread)
	{
		//log.log(Level.INFO, "pointcut:" + thisJoinPoint.getKind()+" Trace method ThreadId "+ childThread.getName()+" \n");
		global.threads.add(childThread);
		
	}

	/*pointcut lock(ReentrantLock l ): call (void ReentrantLock.lock ()) && target(l );
	pointcut unlock(ReentrantLock l ): call (void ReentrantLock.unlock ()) && target(l );
	before(Object l):(lock() && args(l)) || call (void ReentrantLock.lock ()) && target(l )
	{
		//System.out.println("Monali Pande lock: advice running at "+thisJoinPoint.getSourceLocation());
	}
	before(Object l):(unlock() && args(l)) || call (void ReentrantLock.unlock ()) && target(l )
	{
		//System.out.println("Unlock Monali Pande: advice running at "+thisJoinPoint.getSourceLocation());
	}*/
	/**
	 * 
	 * method inforamtion
	 * */
	//pointcut traceMethod(): (execution (* *.*(..)) || execution(*.new(..)) || initialization(*.new(..)) || call(* java..*.*(..)) || call(void java.io.PrintStream.println(..)) || call(public void java.lang.Thread.start()))   && !cflow(within(HelloAspect)) ;
	pointcut traceMethod(): (execution (* *.*(..)) || (execution(*.new(..))) || call(* Thread+.start()) || call(* Thread+.join()) || call(* Thread+.wait()) || call(* java.lang.Object.*(..)))  && !(syncJointPoint()) && !cflow(within(HelloAspect)) ;
	after(): traceMethod() 
	{
		
		
			
		int i=0;	
		
			
			synchronized (this) 
			{
				if(thisJoinPoint.getSignature().getName().length() > 0 && (prevMethod != thisJoinPoint.getSignature().getName()))
				{
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
				fieldName="";
				
				
					
					//log.log(Level.INFO,"Trace method methodName "+ methodName+" pointcut:" + thisJoinPoint.getKind()+" Trace method ThreadId "+ Thread.currentThread().getId()+" \n");
					updateGlobalVar(sign,className,methodName,thread,sourceLocation,lineNo,fieldName,threadaspectj);
					if(methodName=="wait")
					{
						
						global.createVirtualInvocationIns();
					}
					else if(methodName=="lock" || methodName=="unlock")
					{
						//global.createLockInstruction();
					}
					else
					{
						global.createInvokeInstruction();
					}
					
				}
				prevMethod =methodName;
				methodName ="";
			}
			
			
	}

	
	/**
	 *
	 * field inforamtion
	 * */
	pointcut getObject() : get(* *) && within(se.kth.helloworld.App) && !within(HelloAspect);
	after() : getObject()
	{
		
		if(thisJoinPoint.getSignature().getName().length()>0 && thisJoinPoint.getSignature().getName() != "out" )
		{
				synchronized (this) {
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
				
			}
			fieldName="";
	
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
		 
		   
		   
		   for(Thread t:global.threads)
		   {
			  //use try catch as   t.join() throws InterruptedException if it detects that the current thread has its interrupt flag set
			   //main thread is entered in the waiting state until other thread completes its execution.
			   try {
				   t.join();
				
			} catch (Exception e) {
				System.out.println(e);
			}
		   }
		   System.out.println("The application has ended...");
		   //Start GUI
		   global.displayErrorTrace();
		   // clear the threadList
		   global.threads.clear();
	   }
	   
	   public static synchronized void updateGlobalVar(String sign,String className,String methodName,long thread,String sourceLocation,int lineNo,String fieldName, Thread threadaspectj)
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
