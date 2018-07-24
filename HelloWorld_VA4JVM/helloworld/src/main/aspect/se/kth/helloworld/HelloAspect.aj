package se.kth.helloworld;

import org.aspectj.lang.Signature;

public aspect HelloAspect {
	static GlobalVariable global=GlobalVariable.getInstance();
	
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
	
	pointcut traceMethod(): (execution (* *(..))&& !cflow(within(HelloAspect)));
	after(): traceMethod()
	{
		int i=0;
		System.out.println("After method "+ i+" \n");
		Signature sign = thisJoinPointStaticPart.getSignature();
	}
	 
	 
	   after() : mainMethod()
	   {
		   System.out.println("The application has ended...");
		   global.displayErrorTrace();
	   
	   }
	   
	


	   
	   
}
