package se.kth.helloworld;

import java.util.LinkedList;

import se.kth.tracedata.Path;
import se.kth.tracedata.Transition;

public class GlobalVariable {
	/**
	 * We used Singleton pattern to 
	 * 
	 */
	
	// constructor is private to make this class cannot instantiate from outside
	static  private GlobalVariable instance = null;
	String app="Fix it!";;
	LinkedList<Transition> stack=null;
	private GlobalVariable() {
		
	}
	public static GlobalVariable getInstance()
	{
		if(instance == null)
		{
			instance = new GlobalVariable();
		}
		return instance;
		
	}
	public void displayErrorTrace() {  

		se.kth.jpf_visual.ErrorTracePanel gui = new se.kth.jpf_visual.ErrorTracePanel();

			Path p= new se.kth.tracedata.jvm.Path(app);
		
			gui.drowJVMErrTrace(p, true);
		
		

		 }
	

}
