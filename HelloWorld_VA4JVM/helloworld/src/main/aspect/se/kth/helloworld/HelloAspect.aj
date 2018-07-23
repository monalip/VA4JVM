package se.kth.helloworld;

public aspect HelloAspect {
	
	static GlobalVariable global=GlobalVariable.getInstance();
	
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
		   global.displayErrorTrace();
	   
	   }
	   
	


	   
	   
}
